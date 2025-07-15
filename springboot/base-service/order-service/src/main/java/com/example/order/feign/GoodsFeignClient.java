package com.example.order.feign;

import com.example.common.R;
import com.example.dto.GoodsDTO;
import com.example.entity.Goods;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "goods-service", fallback = GoodsFeignFallback.class)
public interface GoodsFeignClient {

    @GetMapping("/goods/selectById/{id}")
    R<GoodsDTO> selectGoodsById(@PathVariable Integer id);

    @GetMapping("/goods/selectPageByName")
    R<PageInfo<Goods>> selectPageByName(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(defaultValue = "") String goodsName);
}