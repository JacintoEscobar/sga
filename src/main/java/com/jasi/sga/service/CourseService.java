package com.jasi.sga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasi.sga.model.Course;
import com.jasi.sga.repository.CourseRespository;

@Service
public class CourseService {
    @Autowired
    private CourseRespository courseRepository;

    public Course getCourseById(Long id) {
        return courseRepository.findById(id).get();
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    };

    public Course create(String name, String description) {
        Course course = new Course(name, description);
        return courseRepository.save(course);
    }
}
