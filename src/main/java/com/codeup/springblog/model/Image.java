package com.codeup.springblog.model;

import javax.persistence.*;

@Entity
@Table(name="images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String path;

    @ManyToOne
    @JoinColumn(name = "img_id")
    private Post post;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
