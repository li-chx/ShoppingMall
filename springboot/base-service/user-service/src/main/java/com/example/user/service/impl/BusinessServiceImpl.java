package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.entity.Business;
import com.example.mapper.BusinessMapper;
import com.example.user.service.BusinessService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {

    @Resource
    private BusinessMapper businessMapper;

    @Transactional
    @Override
    public List<Business> selectAll(Business business) {
        QueryWrapper<Business> queryWrapper = new QueryWrapper<>();
        if (business != null) {
            if (business.getName() != null) {
                queryWrapper.like("name", business.getName());
            }
            if (business.getUsername() != null) {
                queryWrapper.like("username", business.getUsername());
            }
        }
        return businessMapper.selectList(queryWrapper);
    }

    @Transactional
    @Override
    public PageInfo<Business> selectPage(Business business, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Business> list = selectAll(business);
        return PageInfo.of(list);
    }

//    @Override
//    public Account login(Account account) {
//        return businessMapper.login(account);
//    }
//
//    @Override
//    public void register(Account account) {
//        businessMapper.insert(account);
//    }
//
//    @Override
//    public void updatePassword(Account account) {
//        businessMapper.updatePassword(account);
//    }

    @Transactional
    @Override
    public Business login(Business business) {
        QueryWrapper<Business> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", business.getUsername())
                .eq("password", business.getPassword());
        return businessMapper.selectOne(queryWrapper);
    }

    @Transactional
    @Override
    public boolean resetPassword(Business request)
    {
        QueryWrapper<Business> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", request.getUsername())
                .eq("email", request.getEmail());
        Business business = businessMapper.selectOne(queryWrapper);
        if(business == null)
            return false;
        else {
            business.setPassword(request.getPassword());
            int result = businessMapper.updateById(business);
            return result > 0;
        }
    }
} 