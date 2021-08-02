package com.rootlets.registration.service;

import com.rootlets.registration.dao.repository.CourseRepository;
import com.rootlets.registration.dao.repository.StudentRepository;
import com.rootlets.registration.entity.Course;
import com.rootlets.registration.entity.Student;
import com.rootlets.registration.exception.StudentCourseIllegalStateException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {
    private final static Logger LOG = LoggerFactory.getLogger(StudentServiceImpl.class);

    private StudentRepository studentRepository;
    private CourseServiceImpl courseServiceImpl;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    public StudentServiceImpl(CourseServiceImpl courseServiceImpl, StudentRepository studentRepository) {
        this.courseServiceImpl = courseServiceImpl;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public Long addStudent(Student student, List<Long> courseIds) {
        Set<Course> courses = courseServiceImpl.courseByIds(courseIds);
        student.setCourses(courses);
        student = studentRepository.save(student);
        LOG.info("Student {} Successfully added", student.getStudentId());
        return student.getStudentId();
    }

    public Student getStudentById(Long id) throws NotFoundException {
        Optional<Student> byId = studentRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new NotFoundException("No student found for id "+id);
        }
    }

    public Set<Student> getStudentByIds(List<Long> ids) {
        return studentRepository.findByStudentIdIn(ids);
    }

    public List<Student> getStudentList() {
        return studentRepository.findAll();
    }

    public void removeStudent(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent()) {
            throw new StudentCourseIllegalStateException("Failed to remove Student. Invalid StudentId :: " + studentId);
        }
        studentRepository.delete(student.get());
    }

    public void registerCourse(Long studentId, Set<Course> courses) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (!studentOptional.isPresent()) {
            throw new StudentCourseIllegalStateException("Failed to register course. Invalid CourseId :: " + studentId);
        }
        Student student = studentOptional.get();
        courses.addAll(student.getCourses());
        student.setCourses(courses);
        studentRepository.save(student);
    }

    public Set<Student> getStudentsByCourseName(String courseName) {
        Optional<Course> course = courseServiceImpl.getCourseByCourseName(courseName);
        if (!course.isPresent()) {
            throw new StudentCourseIllegalStateException("Failed to get Students. Invalid courseName :: " + courseName);
        }
        Comparator<Student> studentByName = (Student student1, Student student2) -> student1.getStudentName()
                .compareTo(student2.getStudentName());
        TreeSet<Student> sortedStudents = new TreeSet<>(studentByName);

        Set<Student> students = course.get().getStudents();
        students.forEach(student -> student.setCourses(null));
        sortedStudents.addAll(students);
        LOG.debug("Actual Students :: {} and Sorted Students by Name:: {}", students, sortedStudents);
        return sortedStudents;
    }

}
