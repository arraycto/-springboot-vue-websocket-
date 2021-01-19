package com.kcx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;
    private String password;
    private String code;
    private String alias;
    private String headImg;
    private String idNumber;
    private String sex;
    private Integer age;
    private String city;
    private String isOnline;
    private String isCertified;
    private String role;
    private Integer balance;
}
