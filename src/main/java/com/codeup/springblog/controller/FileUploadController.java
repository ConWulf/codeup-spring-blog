package com.codeup.springblog.controller;

import com.codeup.springblog.model.User;
import com.codeup.springblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

@Controller
public class FileUploadController {

    @Value("${file-upload-path}")
    private String uploadPath;

    private final UserRepository userDao;

     public FileUploadController(UserRepository userDao) {
         this.userDao = userDao;
     }



    @PostMapping("/fileUpload")
    public String saveFile(Model model, @RequestParam(name = "file") MultipartFile uploadFile, @RequestParam(name = "userU=Id") long userId) {
        String filename = uploadFile.getOriginalFilename();
        String filepath = Paths.get(uploadPath, filename).toString();
        File destinationFile = new File(filepath);
        try {
            User user = userDao.getOne(userId);
            user.setProfileImagePath(filepath);
            uploadFile.transferTo(destinationFile);
            userDao.save(user);
            model.addAttribute("message", "File successfully uploaded!");
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("message", "Oops! Something went wrong! " + e);
        }
        return "redirect:/profile";
    }

}
