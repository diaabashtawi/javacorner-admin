package com.javacorner.admin.utility;

import com.javacorner.admin.dao.*;
import com.javacorner.admin.entiy.Instructor;
import com.javacorner.admin.entiy.Role;
import com.javacorner.admin.entiy.Student;
import com.javacorner.admin.entiy.User;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

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

    public static void studentOperation(UserDao userDao, StudentDao studentDao, RoleDao roleDao){
        createStudent(userDao, studentDao, roleDao);
        updateStudent(studentDao);
        deleteStudent(studentDao);
        fetchStudent(studentDao);
    }

    public static void courseOperation(CourseDao courseDao, InstructorDao instructorDao, StudentDao studentDao){
        createCourses(courseDao, instructorDao, studentDao);
        updateCourse(courseDao);
        deleteCourse(courseDao);
        fetchCourse(courseDao);
    }

    private static void createCourses(CourseDao courseDao, InstructorDao instructorDao, StudentDao studentDao) {
    }

    private static void updateCourse(CourseDao courseDao) {
    }

    private static void deleteCourse(CourseDao courseDao) {
    }

    private static void fetchCourse(CourseDao courseDao) {
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
                studentDao.findById(1L).orElseThrow( ()-> new EntityNotFoundException("Student NOT FOUND") );
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
                                ()-> new EntityNotFoundException("Instructor NOT FOUND")
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
