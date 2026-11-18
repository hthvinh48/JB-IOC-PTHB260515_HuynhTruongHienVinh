package com.example.studentservice.service;

import com.example.studentservice.client.CourseClient;
import com.example.studentservice.dto.req.CourseEnrollRequest;
import com.example.studentservice.dto.res.CourseResponse;
import com.example.studentservice.entity.Student;
import com.example.studentservice.repository.StudentRepository;
import feign.Feign;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseClient courseClient;

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy khóa học.")
        );
    }

    @Transactional
    public void enroll(CourseEnrollRequest request) {
        if (request.getStudentId() == null || request.getCourseId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "studentId/courseId không hợp lệ.");
        }

        Student student = getStudentById(request.getStudentId());
        if (student.getEnrolledCourses().contains(request.getCourseId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Học viên đã đăng ký khóa học này.");
        }

        CourseResponse course;
        try {
            course = courseClient.getCourseById(request.getCourseId());
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy khóa học.");
        } catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Dịch vụ không có sẳn.");
        }

        if (course.getCapacity() <= 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Khóa học này đã đủ số lượng học viên.");
        }

        try {
            courseClient.updateCapacityByCourseId(request.getCourseId());
        } catch (FeignException.Conflict e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Khóa học này đã đủ số lượng học viên.");
        }
        catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy khóa học.");
        }
        catch (FeignException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Dịch vụ không có sẳn.");
        }

        student.getEnrolledCourses().add(request.getCourseId());
        studentRepository.save(student);
    }
}
