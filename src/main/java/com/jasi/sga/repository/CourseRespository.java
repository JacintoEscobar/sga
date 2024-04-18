package com.jasi.sga.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jasi.sga.model.Course;

@Repository
public interface CourseRespository extends JpaRepository<Course, Long> {

}
