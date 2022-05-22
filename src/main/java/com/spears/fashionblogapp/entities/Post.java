package com.spears.fashionblogapp.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String postTitle;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String postContent;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postDate;
    @DateTimeFormat(pattern = "HH:mm")
    private Time time;

    @ManyToOne
    private BlogUser blogUser;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;

    @OneToMany
    private List<Comment> postComments;

    @OneToMany
    private List<Likes> postLikes;

    private int numberOfLikes;

    private int numberOfComments;
}
