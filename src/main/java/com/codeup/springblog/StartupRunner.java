package com.codeup.springblog;


import com.codeup.springblog.model.Post;
import com.codeup.springblog.model.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final UserRepository userDao;
    private final PostRepository postDao;

    public StartupRunner(UserRepository userDao, PostRepository postDao) {
        this.userDao = userDao;
        this.postDao = postDao;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userDao.count() != 0) {
            return;
        }
        User user = new User();
        user.setUsername("cody");
        user.setEmail("cody@codeup.com");
        user.setPassword("codeuprocks");
        userDao.save(user);

        Post post = new Post();
        post.setTitle("Demo title");
        post.setBody("Demo body");
        post.setUser(user);
        postDao.save(post);
    }
}
