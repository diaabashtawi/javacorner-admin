package com.bakheet.service.implementation;

import com.bakheet.dao.InstructorDao;
import com.bakheet.entiy.Course;
import com.bakheet.entiy.Instructor;
import com.bakheet.entiy.User;
import com.bakheet.service.CourseService;
import com.bakheet.service.InstructorService;
import com.bakheet.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.List;

@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {

    private InstructorDao instructorDao;

    private CourseService courseService;
    private UserService userService;

    public InstructorServiceImpl(InstructorDao instructorDao, CourseService courseService, UserService userService) {
        this.instructorDao = instructorDao;
        this.courseService = courseService;
        this.userService = userService;
    }

    @Override
    public Instructor loadInstructorById(Long instructorId) {
        return instructorDao.findById(instructorId).orElseThrow(
                ()-> new EntityNotFoundException("Instructo with ID " + instructorId + "NOT FOUND")
        );
    }

    @Override
    public List<Instructor> findInstructorByName(String name) {
        return instructorDao.findInstructorsByName(name);
    }

    @Override
    public Instructor loadInstructorByEmail(String email) {
        return instructorDao.findInstructorByEmail(email);
    }

    @Override
    public Instructor createInstructor(String firstName, String lastName, String summary, String email, String password) {
        User user =
                userService.createUser(email, password);
        userService.assignRoleToUser(email, "Instructor");
        return instructorDao.save(new Instructor(firstName, lastName, summary, user));
    }

    @Override
    public Instructor updateInstructor(Instructor instructor) {
        return instructorDao.save(instructor);
    }

    @Override
    public List<Instructor> fetchInstructor() {
        return instructorDao.findAll();
    }

    @Override
    public void deleteInstructor(Long instructorId) {
        Instructor instructor =
                loadInstructorById(instructorId);
        for (Course course : instructor.getCourses()){
            courseService.removeCourse(course.getCourseId());
        }
        instructorDao.deleteById(instructorId);
    }
}
