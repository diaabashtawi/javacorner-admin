package com.javacorner.admin.service.implemention;

import com.javacorner.admin.dao.StudentDao;
import com.javacorner.admin.entiy.Course;
import com.javacorner.admin.entiy.Student;
import com.javacorner.admin.entiy.User;
import com.javacorner.admin.service.StudentService;
import com.javacorner.admin.service.UserService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private StudentDao studentDao;

    private UserService userService;

    public StudentServiceImpl(StudentDao studentDao, UserService userService) {
        this.studentDao = studentDao;
        this.userService = userService;
    }

    @Override
    public Student loadStudentById(Long studentId) {
        return studentDao.findById(studentId).orElseThrow(
                ()-> new EntityNotFoundException("Student with ID " + studentId + " NOT FOUND")
        );
    }

    @Override
    public List<Student> findStudentsByName(String name) {
        return studentDao.findStudentByName(name);
    }

    @Override
    public Student loadStudentByEmail(String email) {
        return studentDao.findStudentByEmail(email);
    }

    @Override
    public Student createStudent(String firstNme, String lastName, String level, String email, String password) {
        User user =
                userService.createUser(email, password);
        userService.assignRoleToUser(email, "Student");
        return studentDao.save(new Student(firstNme, lastName, level, user));
    }

    @Override
    public Student updateStudent(Student student) {
        return studentDao.save(student);
    }

    @Override
    public List<Student> fetchStudents() {
        return studentDao.findAll();
    }

    @Override
    public void removeStudent(Long studentId) {
        Student student =
                loadStudentById(studentId);
        Iterator<Course> courseIterator =
                student.getCourses().iterator();
        if (courseIterator.hasNext()){
            Course course = courseIterator.next();
            course.removeStudentFromCourse(student);
        }
        studentDao.deleteById(studentId);
    }
}
