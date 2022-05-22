package com.spears.fashionblogapp.dto;

import com.spears.fashionblogapp.entities.Likes;
import com.spears.fashionblogapp.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogUSerDto {

    private Long id;
    private String username;
    private String email;
    private String password;
    private String name;

}
