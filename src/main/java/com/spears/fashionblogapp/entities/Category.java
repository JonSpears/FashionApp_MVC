package com.spears.fashionblogapp.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @ManyToOne
    private BlogUser blogUser;

    @OneToMany
    private List<Post> post;

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String categoryName;

    @Column(columnDefinition = "TEXT")
    private String categoryDescription;
}
