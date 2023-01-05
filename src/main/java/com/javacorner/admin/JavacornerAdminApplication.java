package com.javacorner.admin;

import com.javacorner.admin.dao.*;
import com.javacorner.admin.entiy.Role;
import com.javacorner.admin.entiy.User;
import com.javacorner.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JavacornerAdminApplication {

    public static final String ADMIN = "Admin";
    public static final String INSTRUCTOR = "Instructor";
    public static final String STUDENT = "Student";
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private InstructorDao instructorDao;

    public static void main(String[] args) {
        SpringApplication.run(JavacornerAdminApplication.class, args);
    }

    @Bean
    CommandLineRunner start(UserService userService, RoleService roleService,
                            StudentService service, InstructorService instructorService,
                            CourseService courseService){

        return args -> {
          User user1 = userService.createUser("user1@gmail.com","pass1");
          User user2 = userService.createUser("user2@gmail.com","pass2");

            roleService.createRole(ADMIN);
            roleService.createRole(INSTRUCTOR);
            roleService.createRole(STUDENT);

            userService.assignRoleToUser(user1.getEmail(), ADMIN);
            userService.assignRoleToUser(user1.getEmail(), INSTRUCTOR);
            userService.assignRoleToUser(user2.getEmail(), STUDENT);

        };
    }
}
