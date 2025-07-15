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
@TableName("admin")
@Schema(description = "管理员实体")
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    @Schema(description = "管理员ID", example = "1")
    private Integer id;

    /** 用户名 */
    @Schema(description = "用户名", example = "admin")
    private String username;

    /** 密码 */
    @Schema(description = "密码", example = "password123")
    private String password;

    /** 管理员昵称 */
    @Schema(description = "管理员昵称", example = "系统管理员")
    private String name;

    /** 电话 */
    @Schema(description = "电话", example = "13800138000")
    private String phone;

    /** 邮箱 */
    @Schema(description = "邮箱", example = "admin@example.com")
    private String email;

    /** 头像 */
    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;

    /** 角色标识 */
    @Schema(description = "角色标识", example = "ADMIN")
    private String role;
}