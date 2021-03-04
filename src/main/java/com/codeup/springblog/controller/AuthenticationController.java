package com.codeup.springblog.controller;

import com.codeup.springblog.model.PasswordResetToken;
import com.codeup.springblog.model.User;
import com.codeup.springblog.repositories.ResetTokenRepository;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@Controller
public class AuthenticationController {
    private final UserRepository userDao;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final ResetTokenRepository tokenDao;

    public AuthenticationController(UserRepository userDao, PasswordEncoder passwordEncoder,
                                    EmailService emailService, ResetTokenRepository tokenDao) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.tokenDao = tokenDao;
    }

    @GetMapping("/sign-up")
    public String showSignUpPage(Model model) {
        model.addAttribute("user", new User());
        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(@Valid @ModelAttribute User user, Errors e, Model model, @RequestParam(name = "confirm-password") String confirmPassword) {
        if(e.hasErrors()) {
            model.addAttribute("errors", e);
            return "users/sign-up";
        } else if(userDao.findByUsername(user.getUsername()) != null) {
            model.addAttribute("username", user.getUsername());
            return "users/sign-up";
        } else if(userDao.findByEmail(user.getEmail()) != null) {
            model.addAttribute("email", user.getEmail());
            return "users/sign-up";
        } else if(!confirmPassword.equals(user.getPassword())) {
            model.addAttribute("mismatch", 0);
            return "users/sign-up";
        }

        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userDao.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "users/login";
    }

    @GetMapping("/forgot")
    public String showPwReset(Model model) {
        model.addAttribute("user", new User());
        return "users/forgot-password";
    }

    @PostMapping("/forgot")
    public String resetPw(@ModelAttribute User user, Model model, RedirectAttributes ra) {
        if(userDao.findByEmail(user.getEmail()) == null) {
            model.addAttribute("notExists", user.getEmail());
            return "users/forgot-password";
        }
        String token = UUID.randomUUID().toString();
        PasswordResetToken prt = new PasswordResetToken(token);
        prt.setExpirationDate(new Date());
        prt.setUser(userDao.findByEmail(user.getEmail()));
        tokenDao.save(prt);
        String link = "http://localhost:8080/reset?token="+prt.getToken();
        String body = String.format("You have asked to reset your password. To reset your password, Follow this link: <a href=%s>reset</a>", link);
        emailService.prepareAndASend(user, "Reset Password", body);
        ra.addFlashAttribute("sent", user.getEmail());
        return "redirect:/login";
    }

    @GetMapping("/reset")
    public String showPwReset(@RequestParam String token, Model model) {
        PasswordResetToken prt = tokenDao.findByToken(token);
        if(prt == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", new User());
        model.addAttribute("token", prt.getToken());
        return "users/forgot-password";
    }

    @PostMapping("/reset")
    public String resetPassword(@RequestParam(name = "password") String password, @RequestParam(name = "confirm-password") String confirmPassword, @RequestParam(name = "token") String token) {
        PasswordResetToken prt = new PasswordResetToken(token);
        User user = prt.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userDao.save(user);
        tokenDao.delete(prt);
        return "redirect:/login";
    }

}
