package com.aluracursos.foro.controller;

import com.aluracursos.foro.domain.course.Course;
import com.aluracursos.foro.domain.course.CourseRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseRepository courseRepository;

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody @Valid Course course) {
        if (courseRepository.existsByName(course.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Course with this name already exists");
        }
        courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }
    @GetMapping
    public ResponseEntity<List<Course>> listCourses() {
        List<Course> courses = courseRepository.findAll();
        return ResponseEntity.ok(courses);
    }
}
