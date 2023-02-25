package com.bakheet.controller;

import com.bakheet.entiy.Student;
import com.bakheet.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.bakheet.constants.AppConstants.KEYWORD;
import static com.bakheet.constants.AppConstants.LIST_STUDENTS;

@Controller
@RequestMapping(
        value = "/student"
)
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    public String studentList(Model model, @RequestParam(name = KEYWORD) String keyword){

        List<Student> students = studentService.findStudentsByName(keyword);
        model.addAttribute(LIST_STUDENTS, students);
        model.addAttribute(KEYWORD, keyword);

        return "student_views/students";

    }
}
