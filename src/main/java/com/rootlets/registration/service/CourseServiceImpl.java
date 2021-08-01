package com.rootlets.registration.service;

import com.rootlets.registration.dao.repository.CourseRepository;
import com.rootlets.registration.dao.repository.StudentRepository;
import com.rootlets.registration.entity.Course;
import com.rootlets.registration.entity.Student;
import com.rootlets.registration.exception.StudentCourseIllegalStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private final static Logger LOG = LoggerFactory.getLogger(CourseServiceImpl.class);

    private CourseRepository courseRepository;
    private StudentRepository studentRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public Long addCourse(Course course) {
        if (!courseRepository.findCourseByCourseName(course.getCourseName()).isPresent()) {
            course = courseRepository.save(course);
            LOG.info("Course: {} has been successfully added. ", course.getCourseId());
            return course.getCourseId();
        }
        throw new StudentCourseIllegalStateException("Course with this name already exist");
    }

    public Long updateCourse(Course course) {
        course = courseRepository.save(course);
        LOG.info("Course: {} has been successfully added. ", course.getCourseId());
        return course.getCourseId();
    }

    public void removeCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (!course.isPresent()) {
            throw new StudentCourseIllegalStateException("Failed to remove Course. Invalid CourseId :: " + courseId);
        }
        courseRepository.delete(course.get());
    }

    public Set<Student> registerStudentToCourse(List<Long> courseId, Set<Student> students) {
        LOG.info("CourseId :: {} , Student :: {}", courseId, students);
        Set<Course> courses = courseRepository.findByCourseIdIn(courseId);
        if (!courses.isEmpty()) {
            for (Student student : students) {
                Optional<Course> notallowed = courses.stream().filter(course -> course.getStudents().size() > 49).findAny();
                if (notallowed.isPresent()) {
                    throw new StudentCourseIllegalStateException("Upto 50 students can enroll to the course " + notallowed.get().getCourseName());
                }else {
                    student.setCourses(courses);
                    studentRepository.save(student);
                }
            }
        } else {
            throw new StudentCourseIllegalStateException("Failed to register Student. Invalid CourseId :: " + courseId);
        }
        List<Long> stdIds = students.stream().map(student -> student.getStudentId()).collect(Collectors.toList());
        return studentRepository.findByStudentIdIn(stdIds);
    }

    public Optional<Course> getCourseByCourseName(String courseName) {
        return courseRepository.findCourseByCourseName(courseName);
    }

    public Optional<Course> courseById(Long id) {
        return courseRepository.findById(id);
    }

    public Set<Course> courseByIds(List<Long> ids) {
        return courseRepository.findByCourseIdIn(ids);
    }

    @Override
    public Set<Course> getCourseWithoutStudents() {
        return courseRepository.findByStudentsIsNull();
    }

    public List<Course> getAll() {
        return courseRepository.findAll();
    }
}
