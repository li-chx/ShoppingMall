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
@TableName("address")
@Schema(description = "用户地址实体")
public class Address implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    @Schema(description = "地址ID", example = "1")
    private Integer id;

    @Schema(description = "用户ID", example = "1001")
    private Integer userId;

    @Schema(description = "收件人姓名", example = "张三")
    private String username;

    @Schema(description = "详细地址", example = "北京市海淀区中关村南大街5号")
    private String useraddress;

    @Schema(description = "联系电话", example = "13812345678")
    private String phone;
}