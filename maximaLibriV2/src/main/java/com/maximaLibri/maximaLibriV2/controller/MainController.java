package com.maximaLibri.maximaLibriV2.controller;


import com.maximaLibri.maximaLibriV2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }

    @GetMapping("/home")
    public String home() {return "home";}

    @GetMapping("/index")
    public String showHomePage(Model model) {
        model.addAttribute("bookList", bookService.getTop10());
        return "index";
    }
}
