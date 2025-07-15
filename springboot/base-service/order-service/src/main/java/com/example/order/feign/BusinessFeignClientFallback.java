package com.example.order.feign;

import com.example.common.R;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.Business;
import org.springframework.stereotype.Component;

@Component
public class BusinessFeignClientFallback implements BusinessFeignClient {
    @Override
    public R<Business> getBusinessById(Integer id) {
        return R.error(ResultCodeEnum.SYSTEM_ERROR);
    }
}
