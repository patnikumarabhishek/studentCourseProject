package com.rootlets.registration.service;

import com.rootlets.registration.entity.Course;
import com.rootlets.registration.entity.Student;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseService {

    public Long addCourse(Course course);

    public void removeCourse(Long courseId);

    public Set<Student> registerStudentToCourse(List<Long> courseId, Set<Student> students);

    public Optional<Course> getCourseByCourseName(String courseName);

    public Optional<Course> courseById(Long id);

    public List<Course> getAll();

    public Set<Course> courseByIds(List<Long> ids);

    Set<Course> getCourseWithoutStudents();
}
