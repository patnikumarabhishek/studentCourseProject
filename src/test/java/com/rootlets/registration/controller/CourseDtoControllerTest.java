package com.rootlets.registration.controller;

import com.rootlets.registration.dto.CourseDto;
import com.rootlets.registration.dto.RegistrationRequest;
import com.rootlets.registration.dto.StudentDto;
import com.rootlets.registration.service.CourseServiceImpl;
import com.rootlets.registration.service.StudentServiceImpl;
import com.rootlets.registration.utils.PojoConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class CourseDtoControllerTest {
    private final static Logger LOG = LoggerFactory.getLogger(StudentController.class);

    @Mock
    private StudentServiceImpl studentServiceImplMock;
    @Mock
    private CourseServiceImpl courseServiceImplMock;

    @InjectMocks
    private StudentController studentController;
    @InjectMocks
    private CourseController courseController;
    @Mock
    private PojoConverter pojoConverter;
    @InjectMocks
    private StudentCourseRegisterController enrollStudentToCourse;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddCourse() {
        CourseDto course = new CourseDto();
        course.setCourseName("MBA");
        LOG.info("Course  ::Course Name {}", course.getCourseName());
        String result = courseController.addCourse(course);
        Assert.assertEquals(result, "Course with Name: MBA has been Added.");
    }

    @Test
    public void testAddStudent() {
        StudentDto student = new StudentDto();
        student.setStudentName("Nick");
        studentController.addStudent(student);
    }

    @Test
    public void testEnrollStudentToCourse() {
        Long courseId = 1l;
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setCourseIds(Collections.singletonList(courseId));
        registrationRequest.setStudentIds(Collections.singletonList(1l));
        enrollStudentToCourse.enrollStudentToCourse(registrationRequest);
    }

    @Test
    public void testRemoveCourse() {
        Long courseId = 1l;
        String result = courseController.removeCourse(courseId);
        Assert.assertEquals(result, "Course with Id: 1 has been removed.");
    }




}
