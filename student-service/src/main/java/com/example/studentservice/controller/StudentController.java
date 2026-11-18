package com.example.studentservice.controller;

import com.example.studentservice.dto.req.CourseEnrollRequest;
import com.example.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/enroll")
    public ResponseEntity<String> enrollCourse(@RequestBody CourseEnrollRequest request) {
        studentService.enroll(request);
        return ResponseEntity.ok("Đăng ký khóa học thành công.");
    }
}
