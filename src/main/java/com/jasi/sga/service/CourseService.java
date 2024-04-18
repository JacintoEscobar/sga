package com.jasi.sga.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jasi.sga.dto.CreateUpdateCourseRequest;
import com.jasi.sga.model.Course;
import com.jasi.sga.repository.CourseRespository;
import com.jasi.sga.util.CourseFields;

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

    public void delete(Course course) {
        courseRepository.delete(course);
    }

    public Course putUpdate(Course course, CreateUpdateCourseRequest updateCourseRequest) {
        course.setName(updateCourseRequest.getName());
        course.setDescription(updateCourseRequest.getDescription());
        course.setUpdatedAt(LocalDateTime.now());
        return courseRepository.save(course);
    }

    public Course patchUpdate(Course course, HashMap<String, String> fieldsToUpdate) {
        fieldsToUpdate.forEach((String field, String value) -> {
            if (field.equals(CourseFields.NAME)) {
                course.setName(value);
            }

            if (field.equals(CourseFields.DESCRIPTION)) {
                course.setDescription(value);
            }
        });
        course.setUpdatedAt(LocalDateTime.now());
        return courseRepository.save(course);
    }
}
