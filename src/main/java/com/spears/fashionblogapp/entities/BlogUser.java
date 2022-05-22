package com.spears.fashionblogapp.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class BlogUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "blogUser", cascade = CascadeType.ALL)
    private List<Likes> likes;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany
    private List<Post> posts = new ArrayList<>();

    @OneToMany
    private List<Category> categories = new ArrayList<>();

}
