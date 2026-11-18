package com.example.courseservice.service;

import com.example.courseservice.entity.Course;
import com.example.courseservice.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy khóa học với ID: " + courseId)
        );
    }

    @Transactional
    public void updateCapacityByCourseId(Long courseId) {
        if (courseId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "courseId không hợp lệ.");
        }

        Course course = getCourseById(courseId);
        if (course.getCapacity() <= 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Khóa học này đã đủ số lượng học viên.");
        }

        course.setCapacity(course.getCapacity() - 1);
        courseRepository.save(course);
    }
}
