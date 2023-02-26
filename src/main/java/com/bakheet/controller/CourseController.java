package com.bakheet.controller;


import com.bakheet.entiy.Course;
import com.bakheet.entiy.Instructor;
import com.bakheet.entiy.User;
import com.bakheet.service.CourseService;
import com.bakheet.service.InstructorService;
import com.bakheet.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static com.bakheet.constants.AppConstants.*;

@Controller
@RequestMapping(
        value = "/courses"
)
public class CourseController {

    private CourseService courseService;

    private InstructorService instructorService;

    private UserService userService;

    public CourseController(CourseService courseService, InstructorService instructorService, UserService userService) {
        this.courseService = courseService;
        this.instructorService = instructorService;
        this.userService = userService;
    }

    @GetMapping(
            value = "/list"
    )
    @PreAuthorize("hasAuthority('Admin')")
    public String viewCourses(
            Model model,
            @RequestParam(name = KEYWORD, defaultValue = "") String keyword){

        List<Course> courses = courseService.findCoursesByCourseName(keyword);

        model.addAttribute(LIST_COURSES, courses);
        model.addAttribute(KEYWORD, keyword);

        return "course_views/courses";
    }

    @GetMapping(
            value = "/delete"
    )
    @PreAuthorize("hasAuthority('Admin')")
    public String deleteCourse(Long courseId, String keyword){
        courseService.removeCourse(courseId);
        return "redirect:/courses/list?keyword=" + keyword;
    }

    @GetMapping(
            value = "/update"
    )
    @PreAuthorize("hasAnyAuthority('Admin','Instructor')")
    public String updateCourse(Model model, Long courseId, Principal principal){

        if (userService.doseCurrentUserHasRole("Instructor")){
            Instructor instructor =
                    instructorService.loadInstructorByEmail(principal.getName());
            model.addAttribute(CURRENT_INSTRUCTOR, instructor);
        }

        Course course = courseService.loadCourseById(courseId);
        List<Instructor> instructors = instructorService.fetchInstructor();

        model.addAttribute(COURSE, course);
        model.addAttribute(LIST_INSTRUCTORS, instructors);

        return "course_views/update";
    }

    @PostMapping(
            value = "/edit"
    )
    @PreAuthorize("hasAnyAuthority('Admin','Instructor')")
    public String saveCourse(Course course){
        courseService.createOrUpdateCourse(course);
        return userService.doseCurrentUserHasRole("Instructor") ? "redirect:/courses/list/instructor" : "redirect:/courses/list";
    }

    @GetMapping(
            value = "/create"
    )
    public String createCourse(Model model){
        List<Instructor> instructors = instructorService.fetchInstructor();
        model.addAttribute(LIST_INSTRUCTORS, instructors);
        model.addAttribute(COURSE, new Course());

        return "course_views/create";
    }

    @GetMapping(
            value = "/list/student"
    )
    public String couresForCurrentStudent(Model model){

        Long studentId = 1L;// current student
        List<Course> subscribedCourses = courseService.fetchCoursesForStudent(studentId);
        List<Course> otherCourses = courseService.fetchAllCourses().stream()
                .filter(course -> !subscribedCourses.contains(course))
                .collect(Collectors.toList());
        model.addAttribute(LIST_COURSES, subscribedCourses);
        model.addAttribute(OTHER_COURSES, otherCourses);

        return "course_views/student_courses";
    }

    @GetMapping(
            value = "/enrollStudent"
    )
    public String enrollCurrentStudentInCourse(Long courseId){
        Long studentId = 1L;
        courseService.assignStudentToCourse(courseId, studentId);
        return "redirect:/courses/list/student";
    }

    @GetMapping(
            value = "/list/instructor"
    )
    @PreAuthorize("hasAuthority('Instructor')")
    public String coursesForCurrentInstructor(Model model, Principal principal){
        User user = userService.loadUserByEmail(principal.getName());
        Instructor instructor = instructorService.loadInstructorById(
                user.getInstructor().getInstructorId()
        );
        model.addAttribute(LIST_COURSES, instructor.getCourses());
        model.addAttribute(FIRST_NAME, instructor.getFirstName());
        model.addAttribute(LAST_NAME, instructor.getLastName());

        return "course_views/instrucor_courses";
    }

    @GetMapping(
            value = "/instructor"
    )
    public String coursesByInstructorId(Model model, Long instructorId){

        Instructor instructor = instructorService.loadInstructorById(instructorId);
        model.addAttribute(LIST_COURSES, instructor.getCourses());

        return "course_views/instrucor_courses";
    }
}
