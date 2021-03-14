package com.codeup.springblog.controller;

import com.codeup.springblog.model.Post;
import com.codeup.springblog.model.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.services.EmailService;
import com.codeup.springblog.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PostController {

    private final PostRepository postDao;
    private final UserService userService;
    private final EmailService emailService;

    public PostController(PostRepository postDao, UserService userService, EmailService mailService) {
        this.postDao = postDao;
        this.userService = userService;
        this.emailService = mailService;
    }


    @GetMapping("/posts")
    public String getPosts(Model model) {
        List<Post> posts = postDao.findAll();
        model.addAttribute("posts", posts);
        return "posts/index";
    }
    @GetMapping("/posts/{id}")
    public String viewPost(Model model, @PathVariable long id) {
        Post post = postDao.getOne(id);
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    public String viewCreatePost(Model model) {
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping("/posts/create")
    public String createPost(@ModelAttribute Post post) {
        User user = userService.loggedInUser();
        post.setUser(user);
        Post savedPost = postDao.save(post);
        emailService.prepareAndASend(savedPost, "post created", String.format("you have recently created a post\n%s %s", savedPost.getTitle(), savedPost.getBody()));
        return "redirect:/posts";
    }

    @GetMapping("/posts/edit/{id}")
    public String editPost(@PathVariable long id, Model model) {
        Post post = postDao.getOne(id);
        model.addAttribute("post", post);
        return "posts/create";
    }
    @PostMapping("/posts/edit/{id}")
    public String editPost(@ModelAttribute Post post) {
        postDao.update(post.getTitle(), post.getBody(), post.getId());
        return "redirect:/posts";
    }
    @PostMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable long id) {
        postDao.deleteById(id);
        return "redirect:/posts";
    }
}



