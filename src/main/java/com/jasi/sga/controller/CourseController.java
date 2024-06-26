package com.jasi.sga.controller;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jasi.sga.dto.CreateUpdateCourseRequest;
import com.jasi.sga.model.Course;
import com.jasi.sga.service.CourseService;
import com.jasi.sga.util.RequestValidator;
import com.jasi.sga.util.ResponseBuilder;
import com.jasi.sga.util.SgaErrorCode;
import com.jasi.sga.util.SgaErrorMessage;
import com.jasi.sga.util.SgaResponseMessage;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping(path = "/sga/api/course", method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
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
    public ResponseEntity<?> createCourse(@RequestBody @Valid CreateUpdateCourseRequest createCourseRequest, BindingResult validation) {
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

    @PostMapping("/delete/{courseId:^[0-9]*$}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long courseId) {
        Course currentCourse = null;
        try {
            currentCourse = courseService.getCourseById(courseId);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(ResponseBuilder.buildErrorResponse(SgaErrorMessage.ELEMENT_NOT_FOUND, SgaErrorCode.ELEMENT_NOT_FOUND, SgaResponseMessage.NO_ELEMENT_FOUND), HttpStatus.NOT_FOUND);
        }
        courseService.delete(currentCourse);
        return new ResponseEntity<>(ResponseBuilder.buildSuccessResponse(SgaResponseMessage.ELEMENT_DELETED, currentCourse), HttpStatus.OK);
    }

    @PutMapping("/update/{courseId:^[0-9]*$}")
    public ResponseEntity<?> putUpdateCourse(@PathVariable Long courseId, @RequestBody @Valid CreateUpdateCourseRequest updateCourseRequest, BindingResult validation) {
        if (RequestValidator.isInvalid(validation)) {
            return new ResponseEntity<>(ResponseBuilder.buildErrorResponse(SgaErrorMessage.INVALID_INFORMATION, SgaErrorCode.INVALID_INFORMATION, RequestValidator.getAllErrors(validation)), HttpStatus.BAD_REQUEST);
        }

        Course currentCourse = null;
        try {
            currentCourse = courseService.getCourseById(courseId);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(ResponseBuilder.buildErrorResponse(SgaErrorMessage.ELEMENT_NOT_FOUND, SgaErrorCode.ELEMENT_NOT_FOUND, SgaResponseMessage.NO_ELEMENT_FOUND), HttpStatus.NOT_FOUND);
        }
        
        Course modifiedCourse = courseService.putUpdate(currentCourse, updateCourseRequest);
        return new ResponseEntity<>(ResponseBuilder.buildSuccessResponse(SgaResponseMessage.COMPLETE_ELEMENT_UPDATE, modifiedCourse), HttpStatus.OK);
    }

    @PatchMapping("/update/{courseId:^[0-9]*$}")
    public ResponseEntity<?> pathUpdate(@PathVariable Long courseId, @RequestBody HashMap<String, String> fieldsToUpdate) {
        if (fieldsToUpdate == null) {
            return new ResponseEntity<>(ResponseBuilder.buildErrorResponse(SgaErrorMessage.INVALID_INFORMATION, SgaErrorCode.INVALID_INFORMATION, SgaErrorMessage.INVALID_INFORMATION), HttpStatus.BAD_REQUEST);
        }

        Course currentCourse = null;
        try {
            currentCourse = courseService.getCourseById(courseId);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(ResponseBuilder.buildErrorResponse(SgaErrorMessage.ELEMENT_NOT_FOUND, SgaErrorCode.ELEMENT_NOT_FOUND, SgaResponseMessage.NO_ELEMENT_FOUND), HttpStatus.NOT_FOUND);
        }

        Course modifiedCourse = courseService.patchUpdate(currentCourse, fieldsToUpdate);
        return new ResponseEntity<>(ResponseBuilder.buildSuccessResponse(SgaResponseMessage.COMPLETE_ELEMENT_UPDATE, modifiedCourse), HttpStatus.OK);
    }
}
