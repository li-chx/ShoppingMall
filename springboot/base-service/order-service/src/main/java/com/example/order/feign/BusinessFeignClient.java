package com.example.order.feign;

import com.example.common.R;
import com.example.entity.Business;
import com.example.order.dto.BusinessDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "business-service", fallback = BusinessFeignClientFallback.class)
public interface BusinessFeignClient {
    @GetMapping("/business/selectById/{id}")
    R<Business> getBusinessById(@PathVariable("id") Integer id);
}