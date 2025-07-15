package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        this.id = orders.getId();  //订单在数据库中的id
        this.userId = orders.getUserId();
        this.businessId = orders.getBusinessId();
        this.goodsId = orders.getGoodsId();
        this.addressId = orders.getAddressId();
        this.orderId= orders.getOrderId(); //订单自身的ID，给用户和商家
        this.num = orders.getNum();  //订单总价
        this.price = orders.getPrice();
        this.status = orders.getStatus();
    }
    // 生成订单号的方法
    public String generateOrderId() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return now.format(formatter);
    }
} 