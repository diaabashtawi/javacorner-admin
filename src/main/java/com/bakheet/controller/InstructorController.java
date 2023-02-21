package com.bakheet.controller;


import com.bakheet.entiy.Instructor;
import com.bakheet.entiy.User;
import com.bakheet.service.InstructorService;
import com.bakheet.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(
        value = "/instructor"
)
public class InstructorController {

    private InstructorService instructorService;

    private UserService userService;

    public InstructorController(InstructorService instructorService, UserService userService) {
        this.instructorService = instructorService;
        this.userService = userService;
    }

    @GetMapping(
            value = "/list"
    )
    public String instructorsList(Model model, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        List<Instructor> instructors = instructorService.findInstructorByName(keyword);
        model.addAttribute("listInstructors", instructors);
        model.addAttribute("keyword", keyword);
        return "instructor_views/instructor";
    }

    @GetMapping(
            value = "/delete"
    )
    public String deleteInstructor(Long instructorId, String keyword) {
        instructorService.deleteInstructor(instructorId);
        return "redirect:/instructor/list";
    }

    @GetMapping(
            value = "/update"
    )
    public String updateInstructor(Model model, Long instructorId) {
        Instructor instructor = instructorService.loadInstructorById(instructorId);
        model.addAttribute("instructor", instructor);
        return "instructor_views/update";
    }

    @PostMapping(
            value = "/edit"
    )
    public String editInstructor(Instructor instructor) {
        instructorService.updateInstructor(instructor);
        return "redirect:/instructor/list";
    }

    @GetMapping(
            value = "/create"
    )
    public String createInstructor(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructor_views/create";
    }

    @PostMapping(
            value = "/save"
    )
    public String saveInstructor(@Validated Instructor instructor, BindingResult bindingResult) {
        User user = userService.loadUserByEmail(instructor.getUser().getEmail());
        if (user != null) {
            bindingResult.rejectValue("user.email", null, "Instructor Email Already Exist");
        }
        if (bindingResult.hasErrors()) {
            return "instructor_views/create";
        }

        instructorService.createInstructor(
                instructor.getFirstName(),
                instructor.getLastName(),
                instructor.getSummary(),
                instructor.getUser().getEmail(),
                instructor.getUser().getPassword()
        );
        return "redirect:/instructor/list";
    }


}
