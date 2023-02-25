package com.bakheet.controller;


import com.bakheet.entiy.Course;
import com.bakheet.entiy.Instructor;
import com.bakheet.service.CourseService;
import com.bakheet.service.InstructorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    public CourseController(CourseService courseService, InstructorService instructorService) {
        this.courseService = courseService;
        this.instructorService = instructorService;
    }

    @GetMapping(
            value = "/list"
    )
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
    public String deleteCourse(Long courseId, String keyword){
        courseService.removeCourse(courseId);
        return "redirect:/courses/list?keyword=" + keyword;
    }

    @GetMapping(
            value = "/update"
    )
    public String updateCourse(Model model, Long courseId){
        Course course = courseService.loadCourseById(courseId);
        List<Instructor> instructors = instructorService.fetchInstructor();

        model.addAttribute(COURSE, course);
        model.addAttribute(LIST_INSTRUCTORS, instructors);
        return "course_views/update";
    }

    @PostMapping(
            value = "/edit"
    )
    public String saveCourse(Course course){
        courseService.createOrUpdateCourse(course);
        return "redirect:/courses/list";
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
    public String coursesForCurrentInstructor(Model model){
        Long instructorId = 1L;
        Instructor instructor = instructorService.loadInstructorById(instructorId);
        model.addAttribute(LIST_COURSES, instructor.getCourses());

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
