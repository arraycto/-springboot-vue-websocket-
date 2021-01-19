package com.kcx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class House {
    private Integer id;
    private String title;
    private String rentalType;
    private Integer bedNumber;
    private Integer peopleNumber;
    private Integer houseType;
    private BigDecimal score;
    private Integer commentsNumber;
    private String img;
    private String headImg;
    private String username;
    private Integer housePrice;
    private String cityAndRegion;
    private String matchingList;
    private String houseList;
    private String chooseDate;
    private String describe;
    private Integer state;
}
