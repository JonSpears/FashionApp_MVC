package com.spears.fashionblogapp.dto;

import lombok.*;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikesDto {

    @Id
    @NotNull
    private Long id;

}
