package com.codeup.springblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RollDice {

    @GetMapping("/roll-dice/{n}")
    public String rollDice(@PathVariable int n, Model model) {
        double randomNum = Math.ceil(Math.random() * 6);
        model.addAttribute("n", n);
        model.addAttribute("random", randomNum);

        return "guess";
    }

    @GetMapping("/roll-dice")
    public String rollDice() {
        return "roll-dice";
    }

}
