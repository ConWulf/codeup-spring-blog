package com.codeup.springblog.controller;

import com.codeup.springblog.model.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PostController {

    @GetMapping("/posts")
    public String getPosts(Model model) {
        List<Post> posts = new ArrayList<>();
        posts.add(
                new Post(1L,"post1", "this is a post", Arrays.asList("happy", "java", "bean"))
        );
        posts.add(
                new Post(2L,"post2", "this is a second post", Arrays.asList("happy", "html", "tag"))
        );
        model.addAttribute("posts", posts);
        return "posts/index";
    }
    @GetMapping("/posts/{id}")
    public String viewPost(Model model, @PathVariable long id) {
        Post post =  new Post(id,"post", "this is a post", Arrays.asList("happy", "java", "bean"));
        model.addAttribute("post", post);
        return "posts/show";
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String viewCreateAdd() {
        return "view the form for creating a post";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String createAdd() {
        return "create a new post";
    }
}
