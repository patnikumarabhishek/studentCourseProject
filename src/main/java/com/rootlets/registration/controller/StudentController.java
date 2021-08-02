package com.rootlets.registration.controller;

import com.rootlets.registration.dto.StudentDto;
import com.rootlets.registration.service.CourseService;
import com.rootlets.registration.service.StudentService;
import com.rootlets.registration.utils.PojoConverter;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final static Logger LOG = LoggerFactory.getLogger(StudentController.class);

    private StudentService studentServiceImpl;
    private PojoConverter pojoConverter;
    private CourseService courseService;


    @Autowired
    public StudentController(StudentService studentServiceImpl, PojoConverter pojoConverter, CourseService courseService) {
        this.studentServiceImpl = studentServiceImpl;
        this.pojoConverter = pojoConverter;
        this.courseService = courseService;
    }

    @PostMapping()
    public String addStudent(@Valid @RequestBody StudentDto studentDto) {
        LOG.info("Student :: Student Name {}", studentDto.getStudentName());
        Long id = studentServiceImpl.addStudent(pojoConverter.convertStudentToDto(studentDto), studentDto.getCourseId());
        return "Student with Name: " + studentDto.getStudentName() + " and Id " + id + " has been Added.";
    }

    @GetMapping("/{id}")
    public StudentDto getById(@PathVariable Long id) throws NotFoundException {
        return pojoConverter.convertStudentToDto(studentServiceImpl.getStudentById(id));
    }

    @GetMapping
    public List<StudentDto> getList() {
        return pojoConverter.studentListToDto(studentServiceImpl.getStudentList());
    }

    @DeleteMapping("/{studentId}")
    public String removeStudent(Long studentId) {
        studentServiceImpl.removeStudent(studentId);
        return "Student with Id:" + studentId + " has been removed.";
    }

    @GetMapping("/studentsByCourseName/{courseName}")
    public List<StudentDto> getStudentsByCourseName(@PathVariable String courseName) {
        return pojoConverter.studentListToDto(new LinkedList(studentServiceImpl.getStudentsByCourseName(courseName)));
    }

}
