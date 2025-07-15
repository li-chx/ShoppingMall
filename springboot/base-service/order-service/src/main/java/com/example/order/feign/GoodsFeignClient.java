package com.example.order.feign;

import com.example.common.R;
import com.example.dto.GoodsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "goods-service", fallback = GoodsFeignFallback.class)
public interface GoodsFeignClient {
    
    @GetMapping("/goods/selectById/{id}")
    R<GoodsDTO> selectGoodsById(@PathVariable Integer id);
}