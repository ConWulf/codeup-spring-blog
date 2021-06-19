package com.codeup.springblog.repositories;

import com.codeup.springblog.model.PasswordResetToken;
import com.codeup.springblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    User findByPrt(PasswordResetToken Id);
//    @Modifying
//    @Transactional
//    @Query("UPDATE User u SET u.prt = :prtId WHERE u.id = :id")
//    void update(@Param("token") long id, @Param("prtId") long prtId);
}
