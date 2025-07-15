package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Address;
import com.example.mapper.AddressMapper;
import com.example.user.service.AddressService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Resource
    private AddressMapper addressMapper;

    @Override
    public List<Address> selectAll(Address address) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        if (address != null) {
            if (address.getUserId() != null) {
                queryWrapper.eq("user_id", address.getUserId());
            }
            if (address.getUsername() != null) {
                queryWrapper.like("name", address.getUsername());
            }
        }
        return addressMapper.selectList(queryWrapper);
    }

    @Override
    public PageInfo<Address> selectPage(Address address, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Address> list = selectAll(address);
        return PageInfo.of(list);
    }
} 