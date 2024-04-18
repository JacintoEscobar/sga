package com.jasi.sga.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jasi.sga.dto.CreateCourseRequest;
import com.jasi.sga.model.Course;
import com.jasi.sga.service.CourseService;
import com.jasi.sga.util.RequestValidator;
import com.jasi.sga.util.ResponseBuilder;
import com.jasi.sga.util.SgaErrorCode;
import com.jasi.sga.util.SgaErrorMessage;
import com.jasi.sga.util.SgaResponseMessage;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/sga/api/course", method = { RequestMethod.GET, RequestMethod.POST })
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping(path = "/{courseId:^[0-9]*$}")
    public ResponseEntity<?> getCourseById(@PathVariable Long courseId) {        
        Course course;
        try {
            course = courseService.getCourseById(courseId);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(ResponseBuilder.buildErrorResponse(SgaResponseMessage.NO_ELEMENT_FOUND, SgaErrorCode.ELEMENT_NOT_FOUND, SgaErrorMessage.ELEMENT_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ResponseBuilder.buildSuccessResponse(SgaResponseMessage.ELEMENT_FOUND, course), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            return new ResponseEntity<>(ResponseBuilder.buildSuccessResponse(SgaResponseMessage.NO_ELEMENTS_FOUND, courses), HttpStatus.OK);
        }
        return new ResponseEntity<>(ResponseBuilder.buildSuccessResponse(SgaResponseMessage.ELEMENTS_FOUND, courses), HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> createCourse(@RequestBody @Valid CreateCourseRequest createCourseRequest, BindingResult validation) {
        if (RequestValidator.isInvalid(validation)) {
            return new ResponseEntity<>(ResponseBuilder.buildErrorResponse(SgaErrorMessage.INVALID_INFORMATION, SgaErrorCode.INVALID_INFORMATION, RequestValidator.getAllErrors(validation)), HttpStatus.BAD_REQUEST);
        }
        
        Course createdCourse = null;
        try {
            createdCourse = courseService.create(createCourseRequest.getName(), createCourseRequest.getDescription());
        } catch (Exception ex) {
            return new ResponseEntity<>(ResponseBuilder.buildErrorResponse(ex.getMessage(), SgaErrorCode.INTERNAL_ERROR, ex), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }
}