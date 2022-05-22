package com.spears.fashionblogapp.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    @Column(columnDefinition = "TEXT", length = 5000, nullable = false)
    private String commentContent;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date commentDate;
    @DateTimeFormat(pattern = "HH:mm")
    private Time commentTime;

    @ManyToOne
    private BlogUser blogUser;

}

