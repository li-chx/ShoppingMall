package com.example.order.feign;

import com.example.common.R;
import com.example.common.enums.ResultCodeEnum;
import com.example.dto.GoodsDTO;
import org.springframework.stereotype.Component;

@Component
public class GoodsFeignFallback implements GoodsFeignClient {

    @Override
    public R<GoodsDTO> selectGoodsById(Integer id) {
        return R.error(ResultCodeEnum.SYSTEM_ERROR);
    }
}