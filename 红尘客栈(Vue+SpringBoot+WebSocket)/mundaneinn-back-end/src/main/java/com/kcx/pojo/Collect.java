package com.kcx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collect {
    private Integer id;
    private String userId;
    private String username;
    private String title;
}
