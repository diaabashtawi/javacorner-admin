package com.bakheet.controller;


import com.bakheet.entiy.Instructor;
import com.bakheet.service.InstructorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(
        value = "/instructor"
)
public class InstructorController {

    private InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping(
            value = "/list"
    )
    public String instructorsList(Model model, @RequestParam(name = "keyword", defaultValue = "")String keyword){
        List<Instructor> instructors = instructorService.findInstructorByName(keyword);
        model.addAttribute("listInstructors", instructors);
        model.addAttribute("keyword", keyword);
        return "instructor_views/instructor";
    }

    @GetMapping(
            value = "/delete"
    )
    public String deleteInstructor(Long instructorId, String keyword){
        instructorService.deleteInstructor(instructorId);
        return "redirect:/instructor/list";
    }

    @GetMapping(
            value = "/update"
    )
    public String updateInstructor(Model model, Long instructorId){
        Instructor instructor = instructorService.loadInstructorById(instructorId);
        model.addAttribute("instructor", instructor);
        return "instructor_views/update";
    }
}
