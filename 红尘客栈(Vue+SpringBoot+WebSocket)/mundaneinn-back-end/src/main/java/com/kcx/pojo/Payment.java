package com.kcx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private Integer id;
    private Date time;
    private Integer money;
    private String customer;
    private String title;
    private String timeSlot;
    private String username;
}
