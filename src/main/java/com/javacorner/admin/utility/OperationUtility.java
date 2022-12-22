package com.javacorner.admin.utility;

import com.javacorner.admin.dao.InstructorDao;
import com.javacorner.admin.dao.RoleDao;
import com.javacorner.admin.dao.UserDao;
import com.javacorner.admin.entiy.Instructor;
import com.javacorner.admin.entiy.Role;
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
        updateInstructors(userDao, instructorDao, roleDao);
        deleteInstructor(instructorDao);
        fetchInstructors(instructorDao);
    }

    private static void fetchInstructors(InstructorDao instructorDao) {
        instructorDao.findAll()
                .forEach(instructor -> System.out.println(instructor.toString()));
    }

    private static void deleteInstructor(InstructorDao instructorDao) {
        instructorDao.deleteById(1L);
    }

    private static void updateInstructors(UserDao userDao, InstructorDao instructorDao, RoleDao roleDao) {
        Instructor instructor = instructorDao.findById(1L).orElseThrow(()-> new EntityNotFoundException("Instructor NOT FOUND"));
        instructor.setSummary("Certified Instructor");
        instructorDao.save(instructor);
    }

    private static void createInstructors(UserDao userDao, InstructorDao instructorDao, RoleDao roleDao) {
        Role role = roleDao.findByName("Instructor");
        if (role == null) throw new EntityNotFoundException("Role NOT FOUND");
        User user1 = new User("instructorUser1@gmail.com", "pass1");
        userDao.save(user1);
        user1.assignRoleToUser(role);
        Instructor instructor1 = new Instructor("instructor1FN", "instructor1FLN", "Experienced Instructor", user1);
        instructorDao.save(instructor1);

        User user2 = new User("instructorUser2@gmail.com", "pass2");
        userDao.save(user2);
        user2.assignRoleToUser(role);
        Instructor instructor2 = new Instructor("instructor2FN", "instructor2FLN", "Senior Instructor", user2);
        instructorDao.save(instructor2);

    }


    private static void fetchUsers(UserDao userDao) {
        userDao.findAll().forEach(user -> System.out.println(user.toString()));
    }

    private static void deleteUser(UserDao userDao) {
        User user = userDao.findById(3L).orElseThrow(() -> new EntityNotFoundException("User NOT FOUND"));
        userDao.delete(user);
    }

    private static void updateUsers(UserDao userDao) {
        User user = userDao.findById(2L).orElseThrow(() -> new EntityNotFoundException("User NOT FOUND"));
        user.setEmail("newEmail@gmail.com");
        userDao.save(user);

    }

    private static void createUsers(UserDao userDao) {
        User user1 = new User("user1@email.com", "pass1");
        userDao.save(user1);

        User user2 = new User("user2@email.com", "pass2");
        userDao.save(user2);

        User user3 = new User("user3@email.com", "pass3");
        userDao.save(user3);

        User user4 = new User("user4@email.com", "pass4");
        userDao.save(user4);
    }

    private static void fetchRoles(RoleDao roleDao) {
        roleDao.findAll().forEach(role -> System.out.println(role.toString()));
    }

    private static void deleteRoles(RoleDao roleDao) {
        roleDao.deleteById(2L);
    }

    private static void updateRoles(RoleDao roleDao) {
        Role role = roleDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Role Not Found"));
        role.setName("NewAdmin");
        roleDao.save(role);
    }

    private static void createRoles(RoleDao roleDao) {
        Role role1 = new Role("Admin");
        roleDao.save(role1);

        Role role2 = new Role("Instructor");
        roleDao.save(role2);

        Role role3 = new Role("Student");
        roleDao.save(role3);

    }

    public static void assignRolesToUsers(UserDao userDao, RoleDao roleDao) {
        Role role = roleDao.findByName("Admin");
        if (role == null) throw new EntityNotFoundException("Role NOT FOUND");
        List<User> users = userDao.findAll();
        users.forEach(user -> {
            user.assignRoleToUser(role);
            userDao.save(user);
        });
    }

}
