package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("orders")
public class Orders implements Serializable {
    /** ID */
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer businessId;
    private Integer goodsId;
    private String orderId;
    private Integer addressId;
    private Integer num;
    private Double price;
    private String status;
    public Orders(Orders orders) {
        this.id = orders.getId();
        this.userId = orders.getUserId();
        this.businessId = orders.getBusinessId();
        this.goodsId = orders.getGoodsId();
        this.orderId = orders.getOrderId();
        this.addressId = orders.getAddressId();
        this.num = orders.getNum();
        this.price = orders.getPrice();
        this.status = orders.getStatus();
    }
} 