package com.jasi.sga.service;

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
}
