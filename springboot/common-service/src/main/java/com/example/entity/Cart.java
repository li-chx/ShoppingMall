package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("cart")
@Schema(description = "购物车实体")
public class Cart implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "购物车ID", example = "1")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户ID", example = "1001")
    private Integer userId;

    @Schema(description = "商家ID", example = "101")
    private Integer businessId;

    @Schema(description = "商品ID", example = "2001")
    private Integer goodsId;

    @Schema(description = "商品数量", example = "2")
    private Integer num;

    public Cart(Cart cart) {
        this.id = cart.id;
        this.userId = cart.userId;
        this.businessId = cart.businessId;
        this.goodsId = cart.goodsId;
        this.num = cart.num;
    }
}