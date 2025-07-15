package com.example.order.dto;

import com.example.entity.Cart;
import lombok.Data;

@Data
public class CartDTO extends Cart {
    private String goodsImg;
    private String goodsName;
    private String businessName;
    private Double goodsPrice;
    public CartDTO(Cart cart) {
        super(cart);
    }
}