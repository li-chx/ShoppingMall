package com.example.order.feign;

import com.example.common.R;
import com.example.common.enums.ResultCodeEnum;
import com.example.dto.GoodsDTO;
import com.example.entity.Goods;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class GoodsFeignFallback implements GoodsFeignClient {

    @Override
    public R<GoodsDTO> selectGoodsById(Integer id) {
        return R.error(ResultCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public R<PageInfo<Goods>> selectPageByName(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(defaultValue = "") String goodsName) {
        return  R.error(ResultCodeEnum.SYSTEM_ERROR);
    }
}