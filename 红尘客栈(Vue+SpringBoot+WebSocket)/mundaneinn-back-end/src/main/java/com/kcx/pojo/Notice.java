package com.kcx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {
    private Integer id;
    private String username;
    private String noticeType;
    private Date datetime;
    private String describe;
    private String url;
}
