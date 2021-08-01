package com.rootlets.registration.dao.repository;

import com.rootlets.registration.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Set<Student> findByStudentIdIn(List<Long> ids);

}
