package com.codeup.springblog.services;

import com.codeup.springblog.model.Post;
import com.codeup.springblog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service("mailService")
public class EmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Value("${spring.mail.from}")
    private String from;

    public void prepareAndASend(Post post, String subject, String body) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(post.getUser().getEmail());
        msg.setSubject(subject);
        msg.setText(body);

        try {
            this.emailSender.send(msg);
        } catch (MailException mex) {
            System.err.println(mex.getMessage());
        }
    }

    public void prepareAndASend(User user, String subject, String body) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mh = new MimeMessageHelper(message);
        try {
            mh.setText(body, true);
            mh.setFrom(from);
            mh.setTo(user.getEmail());
            mh.setSubject(subject);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        try {
            this.emailSender.send(message);
        } catch (MailException mex) {
            System.err.println(mex.getMessage());
        }
    }
}
