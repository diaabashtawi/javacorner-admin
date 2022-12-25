package com.javacorner.admin.utility;

import com.javacorner.admin.dao.*;
import com.javacorner.admin.entiy.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class OperationUtility {

    public static void usersOperations(UserDao userDao) {
        createUsers(userDao);
        updateUsers(userDao);
        deleteUser(userDao);
        fetchUsers(userDao);
    }

    public static void rolesOperations(RoleDao roleDao) {
        createRoles(roleDao);
        updateRoles(roleDao);
        deleteRoles(roleDao);
        fetchRoles(roleDao);
    }

    public static void instructorsOperation(UserDao userDao, InstructorDao instructorDao, RoleDao roleDao) {
        createInstructors(userDao, instructorDao, roleDao);
        updateInstructor(instructorDao);
        deleteInstructor(instructorDao);
        fetchInstructors(instructorDao);

    }

    public static void studentOperation(UserDao userDao, StudentDao studentDao, RoleDao roleDao) {
        createStudent(userDao, studentDao, roleDao);
        updateStudent(studentDao);
        deleteStudent(studentDao);
        fetchStudent(studentDao);
    }

    public static void courseOperation(CourseDao courseDao, InstructorDao instructorDao, StudentDao studentDao) {
        createCourses(courseDao, instructorDao, studentDao);
        updateCourse(courseDao);
        deleteCourse(courseDao);
        fetchCourses(courseDao);
        assignStudentToCourse(courseDao, studentDao);
        fetchCoursesForStudent(courseDao);

    }

    private static void createCourses(CourseDao courseDao, InstructorDao instructorDao, StudentDao studentDao) {
        Instructor instructor =
                instructorDao.findById(1L)
                        .orElseThrow(() -> new EntityNotFoundException("Instructor NOT FOUND"));

        Course course1 =
                new Course("Hibernate", "5 Hours", "Introduction to Hibernate", instructor);
        courseDao.save(course1);

        Course course2 =
                new Course("Spring Data JPA", "10 Hours", "Master Spring Data JPA", instructor);
        courseDao.save(course2);

    }

    private static void updateCourse(CourseDao courseDao) {
        Course course =
                courseDao.findById(1L)
                        .orElseThrow(() -> new EntityNotFoundException("Course NOT FOUND"));
        course.setCourseDuration("20 Hours");
        courseDao.save(course);
    }

    private static void deleteCourse(CourseDao courseDao) {
        courseDao.deleteById(2L);
    }

    private static void fetchCourses(CourseDao courseDao) {
        courseDao.findAll()
                .forEach(course -> System.out.println(course.toString()));
    }

    private static void assignStudentToCourse(CourseDao courseDao, StudentDao studentDao) {
        Optional<Student> student1 = studentDao.findById(1L);
        Optional<Student> student2 = studentDao.findById(2L);
        Course course = courseDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Course NOT FOUND"));

        student1.ifPresent(course::assignStudentToCourse);
        student2.ifPresent(course::assignStudentToCourse);

        courseDao.save(course);
    }

    private static void fetchCoursesForStudent(CourseDao courseDao) {
        courseDao.getCourseByStudentId(1L).forEach(course -> System.out.println(course.toString()));
    }


    private static void createStudent(UserDao userDao, StudentDao studentDao, RoleDao roleDao) {
        Role role =
                roleDao.findByName("Student");
        if (role == null) throw new EntityNotFoundException("Role NOT FOUND");
        User user1 =
                new User("stdUser1@gmail.com", "pass1");
        userDao.save(user1);
        user1.assignRoleToUser(role);
        Student student1 =
                new Student("student1FN", "student1LN", "master", user1);
        studentDao.save(student1);

        User user2 =
                new User("stdUser2@gmail.com", "pass2");
        userDao.save(user2);
        user2.assignRoleToUser(role);
        Student student2 =
                new Student("student2FN", "student2LN", "Phd", user2);
        studentDao.save(student2);
    }

    private static void updateStudent(StudentDao studentDao) {
        Student student =
                studentDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Student NOT FOUND"));
        student.setFirstName("updatedStdFN");
        student.setLastName("updatedStdLN");
        studentDao.save(student);
    }

    private static void deleteStudent(StudentDao studentDao) {
        studentDao.deleteById(1L);
    }

    private static void fetchStudent(StudentDao studentDao) {
        studentDao.findAll()
                .forEach(
                        student -> System.out.println(student.toString())
                );
    }

    /* User Operation Method */
    private static void createUsers(UserDao userDao) {
        User user1 =
                new User(
                        "user1@email.com",
                        "pass1"
                );
        userDao.save(user1);

        User user2 =
                new User(
                        "user2@email.com",
                        "pass2"
                );
        userDao.save(user2);

        User user3 =
                new User(
                        "user3@email.com",
                        "pass3"
                );
        userDao.save(user3);

        User user4 =
                new User(
                        "user4@email.com",
                        "pass4"
                );
        userDao.save(user4);
    }

    private static void updateUsers(UserDao userDao) {
        User user = userDao.findById(2L).orElseThrow(() -> new EntityNotFoundException("User NOT FOUND"));
        user.setEmail("newEmail@gmail.com");
        userDao.save(user);

    }

    private static void deleteUser(UserDao userDao) {
        User user = userDao.findById(3L).orElseThrow(() -> new EntityNotFoundException("User NOT FOUND"));
        userDao.delete(user);
    }

    private static void fetchUsers(UserDao userDao) {
        userDao.findAll().forEach(
                user -> System.out.println(user.toString())
        );
    }
    /* User Operation Method */

    /* Role Operation Method */
    private static void createRoles(RoleDao roleDao) {
        Role role1 =
                new Role(
                        "Admin"
                );
        roleDao.save(role1);

        Role role2 =
                new Role(
                        "Instructor"
                );
        roleDao.save(role2);

        Role role3 =
                new Role(
                        "Student"
                );
        roleDao.save(role3);

    }

    public static void assignRolesToUsers(UserDao userDao, RoleDao roleDao) {
        Role role = roleDao.findByName("Admin");
        if (role == null) throw new EntityNotFoundException("Role NOT FOUND");
        List<User> users = userDao.findAll();
        users.forEach(
                user -> {
                    user.assignRoleToUser(role);
                    userDao.save(user);
                }
        );
    }

    private static void updateRoles(RoleDao roleDao) {
        Role role =
                roleDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Role Not Found"));
        role.setName("NewAdmin");
        roleDao.save(role);
    }

    private static void deleteRoles(RoleDao roleDao) {
        roleDao.deleteById(2L);
    }

    private static void fetchRoles(RoleDao roleDao) {
        roleDao.findAll().forEach(
                role -> System.out.println(role.toString())
        );
    }
    /* Role Operation Method */

    /* Instructor Operation Method */
    private static void createInstructors(UserDao userDao, InstructorDao instructorDao, RoleDao roleDao) {
        Role role = roleDao.findByName("Instructor");
        if (role == null) throw new EntityNotFoundException("Role NOT FOUND");
        User user1 =
                new User(
                        "instructorUser1@gmail.com",
                        "pass1"
                );
        userDao.save(user1);
        user1.assignRoleToUser(role);
        Instructor instructor1 =
                new Instructor(
                        "instructor1FN",
                        "instructor1FLN",
                        "Experienced Instructor",
                        user1
                );
        instructorDao.save(instructor1);

        User user2 =
                new User(
                        "instructorUser2@gmail.com",
                        "pass2"
                );
        userDao.save(user2);
        user2.assignRoleToUser(role);
        Instructor instructor2 =
                new Instructor(
                        "instructor2FN",
                        "instructor2FLN",
                        "Senior Instructor",
                        user2
                );
        instructorDao.save(instructor2);

    }

    private static void updateInstructor(InstructorDao instructorDao) {
        Instructor instructor =
                instructorDao.findById(1L)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Instructor NOT FOUND")
                        );
        instructor.setSummary("Certified Instructor");
        instructorDao.save(instructor);
    }

    private static void deleteInstructor(InstructorDao instructorDao) {
        instructorDao.deleteById(2L);
    }

    private static void fetchInstructors(InstructorDao instructorDao) {
        instructorDao.findAll()
                .forEach(
                        instructor -> System.out.println(instructor.toString())
                );
    }
    /* Instructor Operation Method */


}
