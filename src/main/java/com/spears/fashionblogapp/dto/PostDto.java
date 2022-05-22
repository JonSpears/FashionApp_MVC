package com.spears.fashionblogapp.dto;

import com.spears.fashionblogapp.entities.BlogUser;
import lombok.*;

import javax.persistence.Id;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String content;

}
