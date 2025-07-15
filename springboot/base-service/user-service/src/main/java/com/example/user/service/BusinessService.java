package com.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Account;
import com.example.entity.Business;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BusinessService extends IService<Business> {
    List<Business> selectAll(Business business);
    PageInfo<Business> selectPage(Business business, Integer pageNum, Integer pageSize);
    Business login(Business account);
} 