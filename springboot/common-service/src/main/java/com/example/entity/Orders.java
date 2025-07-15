package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "订单实体")
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    @Schema(description = "订单数据库ID", example = "1")
    private Integer id;

    @Schema(description = "用户ID", example = "10")
    private Integer userId;

    @Schema(description = "商家ID", example = "5")
    private Integer businessId;

    @Schema(description = "商品ID", example = "20")
    private Integer goodsId;

    @Schema(description = "订单编号", example = "20231015142536")
    private String orderId;

    @Schema(description = "收货地址ID", example = "15")
    private Integer addressId;

    @Schema(description = "购买数量", example = "2")
    private Integer num;

    @Schema(description = "订单总价", example = "59.90")
    private Double price;

    @Schema(description = "订单状态", example = "PAID")
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