package com.rootlets.registration.dao.repository;

import com.rootlets.registration.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {

    public Optional<Course> findCourseByCourseName(String courseName);

    public Set<Course> findByCourseIdIn(List<Long> ids);

    public Set<Course> findByStudentsIsNull();

}
