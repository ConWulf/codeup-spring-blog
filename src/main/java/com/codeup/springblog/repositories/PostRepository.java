package com.codeup.springblog.repositories;

import com.codeup.springblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value="UPDATE posts p SET p.title = :title, p.body = :body WHERE p.id = :id")
    void update(@Param("title") String title, @Param("body") String body, @Param("id") long id);
}
