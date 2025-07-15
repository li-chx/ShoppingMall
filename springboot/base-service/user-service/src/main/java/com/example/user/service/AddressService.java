package com.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Address;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AddressService extends IService<Address> {
    List<Address> selectAll(Address address);
    PageInfo<Address> selectPage(Address address, Integer pageNum, Integer pageSize);
} 