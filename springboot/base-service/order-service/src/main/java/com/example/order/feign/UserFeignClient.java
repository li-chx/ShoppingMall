package com.example.order.feign;

import com.example.common.R;
import com.example.order.dto.AddressDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", fallback = UserFeignFallback.class)
public interface UserFeignClient {
    
    @GetMapping("/user/selectById/{id}")
    R selectById(@PathVariable("id") Integer id);

    @GetMapping("/address/{id}")
    AddressDTO getAddressById(@PathVariable("id") Integer id);
} 