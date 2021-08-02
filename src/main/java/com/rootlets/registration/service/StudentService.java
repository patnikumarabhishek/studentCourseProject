package com.rootlets.registration.service;

import com.rootlets.registration.entity.Course;
import com.rootlets.registration.entity.Student;
import javassist.NotFoundException;

import java.util.List;
import java.util.Set;

public interface StudentService {

    public Long addStudent(Student student, List<Long> courses);

    public void removeStudent(Long studentId);

    public void registerCourse(Long studentId, Set<Course> courses);

    public Set<Student> getStudentsByCourseName(String courseName);

    public Student getStudentById(Long id) throws NotFoundException;

    public List<Student> getStudentList();

    public Set<Student> getStudentByIds(List<Long> ids);
    }
