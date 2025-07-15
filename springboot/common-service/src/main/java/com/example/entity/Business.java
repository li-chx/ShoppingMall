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
@TableName("business")
public class Business implements Serializable {

    /** ID */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /** 商家登陆用户名 */
    private String username;
    /** 商家登陆密码 */
    private String password;
    /** 商家名称 */
    private String name;
    /** 商家头像文件地址 */
    private String avatar;
    /** 商家身份 */
    private String role;
    /** 商家电话 */
    private String phone;
    /** 邮箱 */
    private String email;
    /** 商家描述 */
    private String description;
    /** 商家状态 */
    private String status;
  
} 