package com.example.dto;

import com.example.entity.Orders;
import lombok.Data;

@Data
public class OrdersDTO extends Orders {
    public OrdersDTO(Orders orders) {
        super(orders);
    }
    public OrdersDTO() {}
    private String goodsName;
    private Double goodsPrice;
    private String goodsImg;
    private String businessName;
    private String username;
    private String useraddress;
    private String phone;

}
