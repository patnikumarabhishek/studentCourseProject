package com.rootlets.registration.controller;

import com.rootlets.registration.dto.ReportResponse;
import com.rootlets.registration.dto.StudentDto;
import com.rootlets.registration.entity.Course;
import com.rootlets.registration.entity.Student;
import com.rootlets.registration.service.CourseService;
import com.rootlets.registration.service.StudentService;
import com.rootlets.registration.utils.PojoConverter;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/report")
public class ReportApiController {
    private final static Logger LOG = LoggerFactory.getLogger(ReportApiController.class);

    private StudentService studentServiceImpl;
    private PojoConverter pojoConverter;
    private CourseService courseService;


    @Autowired
    public ReportApiController(StudentService studentServiceImpl, PojoConverter pojoConverter, CourseService courseService) {
        this.studentServiceImpl = studentServiceImpl;
        this.pojoConverter = pojoConverter;
        this.courseService = courseService;
    }
    @GetMapping
    public Object report(@RequestParam(required = false) String search, @RequestParam(required = false) String value) throws NotFoundException {

        ReportResponse reportResponse = new ReportResponse();
        List<Student> studentList = null;
        if (!StringUtils.isEmpty(search) && search.contains("course")) {
            Optional<Course> course = courseService.courseById(Long.valueOf(value));
            reportResponse.setCourses(Arrays.asList(pojoConverter.convertCourseResponseToDto(course.get())));
            List<Long> studentIds = course.get().getStudents().stream().map(student -> student.getStudentId()).collect(Collectors.toList());
            studentList = new LinkedList<>(studentServiceImpl.getStudentByIds(studentIds));
            reportResponse.setStudents(pojoConverter.studentListToDto(studentList));
            return reportResponse;
        }  else if (!StringUtils.isEmpty(search) && search.contains("nostudent")) {
            reportResponse.setCourses(pojoConverter.convertCourseResponseToDto(new LinkedList(courseService.getCourseWithoutStudents())));
            return reportResponse;
        }else if (!StringUtils.isEmpty(search) && search.contains("student")) {
            Student studentById = studentServiceImpl.getStudentById(Long.valueOf(value));
            reportResponse.setStudents(Collections.singletonList(pojoConverter.convertStudentToDto(studentById)));
            reportResponse.setCourses(pojoConverter.convertCourseResponseToDto(new LinkedList(studentById.getCourses())));
            return reportResponse;
        }

        studentList = studentServiceImpl.getStudentList();
        List<Course> all = courseService.getAll();
        reportResponse.setStudents(pojoConverter.studentListToDto(studentList));
        reportResponse.setCourses(pojoConverter.convertCourseResponseToDto(all));
        return reportResponse;
    }

}
