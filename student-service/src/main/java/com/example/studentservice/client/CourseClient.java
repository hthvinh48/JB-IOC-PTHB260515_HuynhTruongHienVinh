package com.example.studentservice.client;

import com.example.studentservice.dto.res.CourseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "course-service")
public interface CourseClient {
    @GetMapping("/api/courses/{courseId}")
    CourseResponse getCourseById(@PathVariable Long courseId);

    @PostMapping("/api/courses/{courseId}/decrease")
    void updateCapacityByCourseId(@PathVariable Long courseId);
}
