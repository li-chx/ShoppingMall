package com.example.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户账号实体")
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "账号ID", example = "1")
    private Integer id;

    @Schema(description = "用户名", example = "johndoe")
    private String username;

    @Schema(description = "名称", example = "John Doe")
    private String name;

    @Schema(description = "密码", example = "password123")
    private String password;

    @Schema(description = "角色标识", example = "ADMIN")
    private String role;

    @Schema(description = "新密码", example = "newpassword123")
    private String newPassword;

    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;

    @Schema(description = "认证令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}