package com.jasi.sga.controller;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jasi.sga.model.Course;
import com.jasi.sga.service.CourseService;
import com.jasi.sga.util.ResponseBuilder;
import com.jasi.sga.util.SgaErrorCode;
import com.jasi.sga.util.SgaErrorMessage;

@RestController
@RequestMapping(path = "/sga/api/course", method = { RequestMethod.GET })
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping(path = "/{courseId:^[0-9]*$}")
    public ResponseEntity<?> getCourseById(@PathVariable Long courseId) {        
        Course currentCourse;
        try {
            currentCourse = courseService.getCourseById(courseId);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(ResponseBuilder.buildErrorResponse(ex.getMessage(), SgaErrorCode.ELEMENT_NOT_FOUND, SgaErrorMessage.ELEMENT_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ResponseBuilder.buildSuccessResponse(HttpStatus.OK.toString(), currentCourse), HttpStatus.OK);
    }
}