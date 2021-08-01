package com.rootlets.registration.controller;

import com.rootlets.registration.dto.StudentDto;
import com.rootlets.registration.service.CourseServiceImpl;
import com.rootlets.registration.service.StudentServiceImpl;
import com.rootlets.registration.utils.PojoConverter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentDtoControllerTest {
	private final static Logger LOG = LoggerFactory.getLogger(StudentController.class);

	@Mock
	private StudentServiceImpl studentServiceImplMock;
	@Mock
	private CourseServiceImpl courseServiceImplMock;
	@Mock
	private PojoConverter pojoConverter;


	@InjectMocks
	private StudentController studentController;
	@InjectMocks
	private CourseController courseController;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddStudent() {
		StudentDto student = new StudentDto();
		student.setStudentName("Nick");
		studentController.addStudent(student);
	}

	@Test
	public void testRemoveStudent() {
		Long studentId = 1l;
		studentController.removeStudent(studentId);
	}


	@Test
	public void testGetStudentsByCourseName() {
		String courseName = "DevOps";
		studentController.getStudentsByCourseName(courseName);
	}

}
