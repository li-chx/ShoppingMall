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
@TableName("goods")
@Schema(description = "商品实体")
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    @Schema(description = "商品ID", example = "1")
    private Integer id;

    /** 商品名称 */
    @Schema(description = "商品名称", example = "有机草莓")
    private String name;

    /** 商品描述 */
    @Schema(description = "商品描述", example = "新鲜采摘的有机草莓，酸甜可口")
    private String description;

    /** 商品价格 */
    @Schema(description = "商品价格", example = "15.8")
    private Double price;

    /** 商品单位 */
    @Schema(description = "商品单位", example = "盒")
    private String unit;

    /** 商品图片 */
    @Schema(description = "商品图片URL", example = "https://example.com/images/strawberry.jpg")
    private String img;

    /** 商品销量*/
    @Schema(description = "商品销量", example = "256")
    private Integer count;

    /** 商品库存*/
    @Schema(description = "商品库存", example = "100")
    private Integer inventory;

    /** 分类ID*/
    @Schema(description = "分类ID", example = "3")
    private Integer categoryId;

    /** 店铺ID*/
    @Schema(description = "店铺ID", example = "5")
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