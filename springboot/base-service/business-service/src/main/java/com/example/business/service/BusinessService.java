package com.example.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Business;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BusinessService extends IService<Business> {

    List<Business> selectAll(Business business);
    PageInfo<Business> selectPage(Business business, Integer pageNum, Integer pageSize);
    boolean updatePassword(Integer id, String newPassword);
} 