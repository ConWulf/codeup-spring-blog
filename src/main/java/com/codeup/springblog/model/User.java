package com.codeup.springblog.model;

import com.codeup.springblog.ValidPassword;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable=false, unique = true)
    private String username;

    @Column(nullable=false, unique = true)
    private String email;

    @Column(nullable=false)
    @NotBlank(message = "must have a password")
    @ValidPassword
    private String password;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Column
    private boolean Enabled;

    @Column
    private String profileImagePath;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "user"
    )
    private List<Post> posts;

    @OneToOne(cascade = CascadeType.ALL)
    private PasswordResetToken prt;

    public User() { }

    public User(User copy) {
        this.id = copy.id;
        this.username = copy.username;
        this.email = copy.email;
        this.password = copy.password;
    }

    public long getId() { return id; }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public PasswordResetToken getPrt() {
        return prt;
    }

    public void setPrt(PasswordResetToken prt) {
        this.prt = prt;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public boolean isEnabled() {
        return Enabled;
    }

    public void setEnabled(boolean enabled) {
        Enabled = enabled;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}
