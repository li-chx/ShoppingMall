package com.example.order.feign;

import com.example.common.R;
import com.example.common.enums.ResultCodeEnum;
import com.example.dto.AddressDTO;
import org.springframework.stereotype.Component;

@Component
public class UserFeignFallback implements UserFeignClient {
    
    @Override
    public R selectById(Integer id) {
        return R.error(ResultCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public AddressDTO getAddressById(Integer id) {
        return null;
    }
} 