package com.spears.fashionblogapp.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "likes")
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @ManyToOne
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private BlogUser blogUser;

}
