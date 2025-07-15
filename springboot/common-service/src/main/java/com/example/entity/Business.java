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
@TableName("business")
@Schema(description = "商家实体")
public class Business implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    @Schema(description = "商家ID", example = "1")
    private Integer id;

    /** 商家登陆用户名 */
    @Schema(description = "商家登录用户名", example = "business123")
    private String username;

    /** 商家登陆密码 */
    @Schema(description = "商家登录密码", example = "password123")
    private String password;

    /** 商家名称 */
    @Schema(description = "商家名称", example = "优品生鲜超市")
    private String name;

    /** 商家头像文件地址 */
    @Schema(description = "商家头像文件地址", example = "https://example.com/business/avatar.jpg")
    private String avatar;

    /** 商家身份 */
    @Schema(description = "商家身份", example = "BUSINESS")
    private String role;

    /** 商家电话 */
    @Schema(description = "商家电话", example = "13900001111")
    private String phone;

    /** 邮箱 */
    @Schema(description = "邮箱", example = "business@example.com")
    private String email;

    /** 商家描述 */
    @Schema(description = "商家描述", example = "专营各类生鲜水果和有机蔬菜")
    private String description;

    /** 商家状态 */
    @Schema(description = "商家状态", example = "ACTIVE")
    private String status;
}