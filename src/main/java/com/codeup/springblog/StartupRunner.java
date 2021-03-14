package com.codeup.springblog;


import com.codeup.springblog.model.Post;
import com.codeup.springblog.model.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final UserRepository userDao;
    private final PostRepository postDao;
    private final PasswordEncoder passwordEncoder;

    public StartupRunner(UserRepository userDao, PostRepository postDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.postDao = postDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (userDao.count() != 0) {
            return;
        }
        User user = new User();
        user.setUsername("cody");
        user.setEmail("cody@codeup.com");
        user.setPassword(passwordEncoder.encode("codeuprocks"));
        userDao.save(user);

        Post post = new Post();
        post.setTitle("Demo title");
        post.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post.setUser(user);
        postDao.save(post);
        Post post1 = new Post();
        post1.setTitle("Demo title");
        post1.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post1.setUser(user);
        postDao.save(post1);
        Post post2 = new Post();
        post2.setTitle("Demo title");
        post2.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post2.setUser(user);
        postDao.save(post2);
        Post post3 = new Post();
        post3.setTitle("Demo title");
        post3.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post3.setUser(user);
        postDao.save(post3);
        Post post4 = new Post();
        post4.setTitle("Demo title");
        post4.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post4.setUser(user);
        postDao.save(post4);
        Post post5 = new Post();
        post5.setTitle("Demo title");
        post5.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post5.setUser(user);
        postDao.save(post5);
        Post post6 = new Post();
        post6.setTitle("Demo title");
        post6.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post6.setUser(user);
        postDao.save(post6);
        Post post7 = new Post();
        post7.setTitle("Demo title");
        post7.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post7.setUser(user);
        postDao.save(post7);
        Post post8 = new Post();
        post8.setTitle("Demo title");
        post8.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post8.setUser(user);
        postDao.save(post8);
        Post post9 = new Post();
        post9.setTitle("Demo title");
        post9.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post9.setUser(user);
        postDao.save(post9);
        Post post10 = new Post();
        post10.setTitle("Demo title");
        post10.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post10.setUser(user);
        postDao.save(post10);
        Post post11 = new Post();
        post11.setTitle("Demo title");
        post11.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post11.setUser(user);
        postDao.save(post11);
        Post post12 = new Post();
        post12.setTitle("Demo title");
        post12.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post12.setUser(user);
        postDao.save(post12);
        Post post13 = new Post();
        post13.setTitle("Demo title");
        post13.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post13.setUser(user);
        postDao.save(post13);
        Post post14 = new Post();
        post14.setTitle("Demo title");
        post14.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post14.setUser(user);
        postDao.save(post14);
        Post post15 = new Post();
        post15.setTitle("Demo title");
        post15.setBody("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Aliquam amet culpa distinctio error harum, ipsa iste laboriosam officia ullam vel! Asperiores atque corporis illum recusandae rem repudiandae sit tenetur velit.");
        post15.setUser(user);
        postDao.save(post15);





    }
}
