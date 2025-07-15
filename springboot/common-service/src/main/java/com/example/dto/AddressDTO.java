package com.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Schema(description = "用户收货地址数据传输对象")
@Data
public class AddressDTO {
    private Integer id;
    private String username;
    private String useraddress;
    private String phone;
} 