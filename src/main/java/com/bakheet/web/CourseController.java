package com.bakheet.web;


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
            value = "/index"
    )
    public String showCoursPage(){
        return "course-views/demo";
    }


    @GetMapping(
            value = "/list"
    )
    public String viewCourses(
            Model model,
            @RequestParam(name = "keyword", defaultValue = "") String keyword){

        List<Course> courses = courseService.findCoursesByCourseName(keyword);

        model.addAttribute("listCourses", courses);
        model.addAttribute("keyword", keyword);

        return "course-views/courses";
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

        model.addAttribute("course", course);
        model.addAttribute("listInstructor", instructors);
        return "course-views/update";
    }

    @PostMapping(
            value = "/edit"
    )
    public String saveCourse(Course course){
        courseService.createOrUpdateCourse(course);
        return "redirect:/courses/list";
    }
}
