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
@TableName("category")
@Schema(description = "商品分类实体")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    @Schema(description = "分类ID", example = "1")
    private Integer id;

    /** 分类名称 */
    @Schema(description = "分类名称", example = "电子产品")
    private String name;

    /** 分类描述 */
    @Schema(description = "分类描述", example = "包含各类电子设备和配件")
    private String description;

    /** 分类图标 */
    @Schema(description = "分类图标URL", example = "https://example.com/icons/electronics.png")
    private String img;
}