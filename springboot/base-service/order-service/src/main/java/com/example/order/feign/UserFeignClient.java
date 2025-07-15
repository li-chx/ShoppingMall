package com.example.order.feign;

import com.example.common.R;
import com.example.dto.AddressDTO;
import com.example.entity.Address;
import com.example.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", fallback = UserFeignFallback.class)
public interface UserFeignClient {
    
    @GetMapping("/user/selectById/{id}")
    R<User> selectById(@PathVariable("id") Integer id);

    @GetMapping("/address/selectById/{id}")
    R<Address> getAddressById(@PathVariable Integer id);
} 