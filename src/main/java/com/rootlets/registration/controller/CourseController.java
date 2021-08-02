package com.rootlets.registration.controller;

import com.rootlets.registration.dto.CourseDto;
import com.rootlets.registration.dto.CourseResponseDto;
import com.rootlets.registration.entity.Course;
import com.rootlets.registration.entity.Student;
import com.rootlets.registration.exception.EntityNotFoundException;
import com.rootlets.registration.service.CourseService;
import com.rootlets.registration.service.StudentService;
import com.rootlets.registration.utils.PojoConverter;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    private final static Logger LOG = LoggerFactory.getLogger(CourseController.class);

    private CourseService courseServiceImpl;
    private PojoConverter pojoConverter;
    private StudentService studentService;

    @Autowired
    public CourseController(CourseService courseServiceImpl, PojoConverter pojoConverter, StudentService studentService) {
        this.courseServiceImpl = courseServiceImpl;
        this.pojoConverter = pojoConverter;
        this.studentService = studentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addCourse(@Valid @RequestBody CourseDto course) {
        LOG.info("Course  ::Course Name {}", course.getCourseName());
        courseServiceImpl.addCourse(pojoConverter.convertCourseToEntity(course));
        return "Course with Name: " + course.getCourseName() + " has been Added.";
    }

    @PutMapping
    public String updateCourse(@RequestBody Course course) {
        LOG.info("Course  ::Course Name {}", course.getCourseName());
        courseServiceImpl.addCourse(course);
        return "Course with Name:" + course.getCourseName() + " has been updated.";
    }

    @GetMapping("/{courseId}")
    public CourseResponseDto courseById(@PathVariable Long courseId) {
        Optional<Course> course = courseServiceImpl.courseById(courseId);
        if (course.isPresent()) {
            return pojoConverter.convertCourseResponseToDto(course.get());
        } else {
            throw new EntityNotFoundException("Invalid course id " + courseId);
        }
    }

    @GetMapping
    public List<CourseResponseDto> getAllCourseList() {
        return pojoConverter.convertCourseResponseToDto(courseServiceImpl.getAll());
    }

    @DeleteMapping("/{courseId}")
    public String removeCourse(Long courseId) {
        courseServiceImpl.removeCourse(courseId);
        return "Course with Id: " + courseId + " has been removed.";
    }

    @GetMapping("/cousesByStudent/{id}")
    public List<CourseResponseDto> getAllCoursesOfStudent(@PathVariable Long id) throws NotFoundException {
        Student studentById = studentService.getStudentById(id);
        return pojoConverter.convertCourseResponseToDto(new LinkedList(studentById.getCourses()));
    }

    @GetMapping("/cousesWithoutStudent}")
    public List<CourseResponseDto> getAllCoursesWithOutStudent() throws NotFoundException {
        ;
        return pojoConverter.convertCourseResponseToDto(new LinkedList(courseServiceImpl.getCourseWithoutStudents()));
    }
}
