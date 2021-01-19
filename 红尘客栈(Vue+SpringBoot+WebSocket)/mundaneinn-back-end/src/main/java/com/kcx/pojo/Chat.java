package com.kcx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    private Integer id;
    private String from;
    private String to;
    private String content;
    private Date time;
}
