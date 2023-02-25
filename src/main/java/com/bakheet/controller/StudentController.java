package com.bakheet.controller;

import com.bakheet.entiy.Student;
import com.bakheet.entiy.User;
import com.bakheet.service.StudentService;
import com.bakheet.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.bakheet.constants.AppConstants.*;

@Controller
@RequestMapping(
        value = "/student"
)
public class StudentController {

    private StudentService studentService;

    private UserService userService;

    public StudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping(
            value = "/list"
    )
    public String studentList(Model model, @RequestParam(name = KEYWORD, defaultValue = "") String keyword){
        List<Student> students = studentService.findStudentsByName(keyword);
        model.addAttribute(LIST_STUDENTS, students);
        model.addAttribute(KEYWORD, keyword);

        return "student_views/students";
    }

    @GetMapping(
            value = "/delete"
    )
    public String deleteStudent(Long studentId, String keyword){
        studentService.removeStudent(studentId);
        return "redirect:/student/list?keyword=" + keyword;
    }

    @GetMapping(
            value = "/update"
    )
    public String updateStudent(Model model, Long studentId){
        Student student = studentService.loadStudentById(studentId);
        model.addAttribute(STUDENT, student);
        return "student_views/update";
    }

    @PostMapping(
            value = "/edit"
    )
    public String editStudent(Student student){
        studentService.updateStudent(student);
        return "redirect:/student/list";
    }

    @GetMapping(
            value = "/create"
    )
    public String createStudent(Model model){
        model.addAttribute(STUDENT, new Student());
        return "student_views/create";
    }

    @PostMapping(
            value = "/save"
    )
    public String saveStudent(Student student, BindingResult bindingResult){
        User user = userService.loadUserByEmail(student.getUser().getEmail());
        if (user != null)
            bindingResult.rejectValue("user.email", null, "Student Email Already Exist");
        if (bindingResult.hasErrors()) return "student_views/create";
        studentService.createStudent(
                student.getFirstName(),
                student.getLastName(),
                student.getLevel(),
                student.getUser().getEmail(),
                student.getUser().getPassword()
        );

        return "redirect:/student/list";
    }


}
