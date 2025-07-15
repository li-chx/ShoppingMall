package com.example.order.feign;

import com.example.common.R;
import com.example.common.enums.ResultCodeEnum;
import com.example.dto.AddressDTO;
import com.example.entity.Address;
import com.example.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class UserFeignFallback implements UserFeignClient {

    @Override
    public R<User> selectById(Integer id) {
        return R.error(ResultCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public R<Address> getAddressById(@PathVariable Integer id) {
        return R.error(ResultCodeEnum.SYSTEM_ERROR);
    }

} 