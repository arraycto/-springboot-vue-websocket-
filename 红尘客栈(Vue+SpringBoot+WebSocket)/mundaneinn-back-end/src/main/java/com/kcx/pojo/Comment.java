package com.kcx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer id;
    private String userId;
    private String username;
    private String title;
    private String describe;
    private Date commentTime;
    private Integer score;
    private String headImg;
}
