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
@TableName("goods")
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 商品名称 */
    private String name;
    /** 商品描述 */
    private String description;
    /** 商品价格 */
    private Double price;
    /** 商品单位 */
    private String unit;
    /** 商品图片 */
    private String img;
//    /** 商品库存 */
//    private Integer stock;
//    /** 商品状态 */
//    private String status;
    /** 商品销量*/
    private Integer count;
    /** 商品库存*/
    private Integer inventory;
    /** 分类ID*/
    private Integer categoryId;
    /** 店铺ID*/
    private Integer businessId;

    public Goods(Goods goods){
        this.id = goods.getId();
        this.name = goods.getName();
        this.description = goods.getDescription();
        this.price = goods.getPrice();
        this.img = goods.getImg();
        this.count = goods.getCount();
        this.inventory = goods.getInventory();
        this.categoryId = goods.getCategoryId();
        this.businessId = goods.getBusinessId();
        this.unit = goods.getUnit();
    }
} 