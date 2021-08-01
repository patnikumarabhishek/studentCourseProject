package com.rootlets.registration.service;

import com.rootlets.registration.dao.repository.CourseRepository;
import com.rootlets.registration.dao.repository.StudentRepository;
import com.rootlets.registration.entity.Course;
import com.rootlets.registration.entity.Student;
import com.rootlets.registration.exception.StudentCourseIllegalStateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class CourseDtoServiceImplTest {

	@Mock
	private CourseRepository courseRepositoryMock;
	@Mock
	private StudentRepository studentRepository;

	@InjectMocks
	private CourseServiceImpl courseServiceImpl;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddCourse() {
		Course course = new Course();
		course.setCourseId(2l);
		Mockito.when(courseRepositoryMock.save(Mockito.any(Course.class))).thenReturn(course);
		Long courseId = courseServiceImpl.addCourse(course);
		Assert.assertEquals(2, courseId.longValue());
	}

	@Test
	public void testRemoveCourse() {
		Long courseId = 1l;
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("Spring");
		Mockito.when(courseRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(course));
		courseServiceImpl.removeCourse(courseId);
	}
	
	@Test(expected=StudentCourseIllegalStateException.class)
	public void testRemoveCourseWithEmptyCourse() {
		Long courseId = 1l;
		Mockito.when(courseRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		courseServiceImpl.removeCourse(courseId);
	}

	@Test
	public void testRegisterStudentToCourse() {
		Long courseId = 1l;
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("Spring");
		course.setStudents(Collections.emptySet());
		Mockito.when(courseRepositoryMock.findByCourseIdIn(Mockito.any())).thenReturn(Collections.singleton(course));
		Mockito.when(courseRepositoryMock.save(Mockito.any())).thenReturn(course);
		Student student = new Student();
		student.setStudentId(1l);
		student.setStudentName("student name");
		student.setAddress("Noida");
		student.setMobileNumber("7844424524");
		courseServiceImpl.registerStudentToCourse(Collections.singletonList(courseId), Collections.singleton(student));

	}
	
	@Test(expected=StudentCourseIllegalStateException.class)
	public void testRegisterStudentToCoursEmptyCourse() {
		Long courseId = 1l;
		Mockito.when(courseRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		Set<Student> students = Collections.emptySet();
		courseServiceImpl.registerStudentToCourse(Collections.singletonList(courseId), students);

	}

	@Test
	public void testGetCourseByCourseName() {
		String courseName = "AIML";
		courseServiceImpl.getCourseByCourseName(courseName);
	}

}
