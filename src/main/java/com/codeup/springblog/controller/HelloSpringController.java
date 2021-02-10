package com.codeup.springblog.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloSpringController {

    @GetMapping("/hello")
    public String helloSpring() {
        return "hello";
    }

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    @RequestMapping(path = "/increment/{number}")
    @ResponseBody
    public String adOne(@PathVariable int number) {
        return "num + 1 is "+ (number + 1);
    }
}
