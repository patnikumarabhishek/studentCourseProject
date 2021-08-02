package com.rootlets.registration.controller;

import com.rootlets.registration.dto.RegistrationRequest;
import com.rootlets.registration.dto.StudentDto;
import com.rootlets.registration.entity.Student;
import com.rootlets.registration.service.CourseService;
import com.rootlets.registration.service.StudentService;
import com.rootlets.registration.utils.PojoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/register")
public class StudentCourseRegisterController {
    private final static Logger LOG = LoggerFactory.getLogger(StudentCourseRegisterController.class);

    private CourseService courseServiceImpl;
    private StudentService studentService;
    private PojoConverter pojoConverter;

    @Autowired
    public StudentCourseRegisterController(CourseService courseServiceImpl, StudentService studentService, PojoConverter pojoConverter) {
        this.courseServiceImpl = courseServiceImpl;
        this.studentService = studentService;
        this.pojoConverter = pojoConverter;
    }


    @PutMapping("/registerStudentsToCourse/{courseId}")
    public List<StudentDto> enrollStudentToCourse(@RequestBody RegistrationRequest registrationRequest) {
        Set<Student> students = courseServiceImpl.registerStudentToCourse(registrationRequest.getCourseIds(), studentService.getStudentByIds(registrationRequest.getStudentIds()));
        return pojoConverter.studentListToDto(students);
    }
}
