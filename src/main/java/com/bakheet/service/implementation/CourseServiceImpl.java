package com.bakheet.service.implementation;

import com.bakheet.dao.CourseDao;
import com.bakheet.dao.InstructorDao;
import com.bakheet.dao.StudentDao;
import com.bakheet.entiy.Course;
import com.bakheet.entiy.Instructor;
import com.bakheet.entiy.Student;
import com.bakheet.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private CourseDao courseDao;

    private InstructorDao instructorDao;

    private StudentDao studentDao;

    public CourseServiceImpl(CourseDao courseDao, InstructorDao instructorDao, StudentDao studentDao) {
        this.courseDao = courseDao;
        this.instructorDao = instructorDao;
        this.studentDao = studentDao;
    }

    @Override
    public Course loadCourseById(Long courseId) {
        return courseDao.findById(courseId).orElseThrow(() -> new EntityNotFoundException("Course with ID " + courseId + " NOT FOUND"));
    }

    @Override
    public Course createCourse(String courseName, String courseDuration, String courseDescription, Long instructorId) {
        Instructor instructor =
                instructorDao.findById(instructorId).orElseThrow(
                        () -> new EntityNotFoundException("Instructor with ID " + instructorId + " NOT FOUND")
                );
        return courseDao.save(
                new Course(
                        courseName,
                        courseDuration,
                        courseDescription,
                        instructor
                )
        );
    }

    @Override
    public Course createOrUpdateCourse(Course course) {
        return courseDao.save(course);
    }

    @Override
    public List<Course> findCoursesByCourseName(String keyword) {
        return courseDao.findCoursesByCourseNameContains(keyword);
    }

    @Override
    public void assignStudentToCourse(Long courseId, Long studentId) {
        Student student =
                studentDao.findById(studentId).orElseThrow(
                        () -> new EntityNotFoundException("Student with ID " + studentId + "NOT FOUND")
                );
        Course course =
                courseDao.findById(courseId).orElseThrow(
                        ()-> new EntityNotFoundException("Course ")
                );
        course.assignStudentToCourse(student);
    }

    @Override
    public List<Course> fetchAllCourses() {
        return courseDao.findAll();
    }

    @Override
    public List<Course> fetchCoursesForStudent(Long studentId) {
        return courseDao.getCourseByStudentId(studentId);
    }

    @Override
    public void removeCourse(Long courseId) {
        courseDao.deleteById(courseId);
    }
}
