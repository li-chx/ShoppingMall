package com.example.goods.feign;


import com.example.common.R;
import com.example.entity.Business;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="business-service")
public interface BusinessFeignClient {


    @GetMapping("/business/selectById/{id}")
    R<Business> getBusinessById(@PathVariable Integer id);

}
