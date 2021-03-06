package com.codeup.springblog.controller;

import com.codeup.springblog.ValidPassword;
import com.codeup.springblog.model.PasswordResetToken;
import com.codeup.springblog.model.User;
import com.codeup.springblog.repositories.ResetTokenRepository;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import com.codeup.springblog.services.PasswordConstraintValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
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
    public String resetPw(@RequestParam(name = "email") String email, Model model, RedirectAttributes ra) {
        User userResetting = userDao.findByEmail(email);
        if(userResetting == null) {
            model.addAttribute("notExists", email);
            return "users/forgot-password";
        }
        String token = UUID.randomUUID().toString();
        PasswordResetToken prt = new PasswordResetToken(token);
        prt.setExpirationDate(new Date());
        userResetting.setPrt(prt);
        tokenDao.save(prt);
        userDao.save(userResetting);
        String link = "http://localhost:8080/reset?token="+prt.getToken();
        String body = String.format("You have asked to your password. To reset your password, click <a href=%s>here</a>", link);
        emailService.prepareAndASend(userResetting, "Reset Password", body);
        ra.addFlashAttribute("sent", email);
        return "redirect:/login";
    }

    @GetMapping("/reset")
    public String showPwReset(@RequestParam String token, Model model) {
        PasswordResetToken prt = tokenDao.findByToken(token);
        if(prt == null) {
            return "redirect:/login";
        }
        model.addAttribute("token", prt.getToken());
        return "users/forgot-password";
    }

    @PostMapping("/reset")
    public String resetPassword(@RequestParam(name = "password") @ValidPassword String password, @RequestParam(name = "confirm-password") String confirmPassword,
                                @RequestParam(name = "token") String token, Model model, RedirectAttributes ra) {
        if(!password.equals(confirmPassword)) {
            model.addAttribute("token", token);
            ra.addFlashAttribute("noMatch", "passwords do not match");
            return "redirect:/reset?token="+token;
        }
        if(!PasswordConstraintValidator.isValid(password)) {
            ra.addFlashAttribute("errors", PasswordConstraintValidator.errorMessages(password));
            return "redirect:/reset?token="+token;
        }
        PasswordResetToken prt = tokenDao.findByToken(token);
        User user = userDao.findByPrt(prt);
        user.setPassword(passwordEncoder.encode(password));
        tokenDao.deleteById(prt.getId());
        userDao.save(user);
        return "redirect:/login";
    }

}
