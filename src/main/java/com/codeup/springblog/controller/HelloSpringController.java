package com.codeup.springblog.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HelloSpringController {

    @GetMapping("/hello")
    @ResponseBody
    public String helloSpring() {
        return "Hello From Spring";
    }

    @GetMapping("/hello/{name}")
    @ResponseBody
    public String sayHello(@PathVariable String name) {
        return "hello "+name;
    }

    @RequestMapping(path = "/increment/{number}")
    @ResponseBody
    public String adOne(@PathVariable int number) {
        return "num + 1 is "+ (number + 1);
    }
}
