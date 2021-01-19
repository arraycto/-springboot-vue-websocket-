package com.kcx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Integer id;
    private String timeSlot;
    private Integer money;
    private String title;
    private String username;
    private String customerId;
    private Integer state;
}
