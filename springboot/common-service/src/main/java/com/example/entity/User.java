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
@TableName("user")
@Schema(description = "用户实体")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    @Schema(description = "用户ID", example = "1")
    private Integer id;

    /** 用户名 */
    @Schema(description = "用户名", example = "user123")
    private String username;

    /** 密码 */
    @Schema(description = "密码", example = "password123")
    private String password;

    /** 用户昵称 */
    @Schema(description = "用户昵称", example = "张三")
    private String name;

    /** 电话 */
    @Schema(description = "电话", example = "13800138000")
    private String phone;

    /** 邮箱 */
    @Schema(description = "邮箱", example = "user@example.com")
    private String email;

    /** 头像 */
    @Schema(description = "头像URL", example = "https://example.com/avatars/user.jpg")
    private String avatar;

    /** 地址 */
    // private String address;

    /** 角色标识 */
    @Schema(description = "角色标识", example = "USER")
    private String role;
}