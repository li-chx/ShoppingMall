package com.example.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Goods;
import com.example.entity.Orders;
import com.example.mapper.OrdersMapper;
import com.example.order.service.OrdersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper,Orders> implements OrdersService {

    @Resource
    private OrdersMapper ordersMapper;

    @Transactional
    @Override
    public List<Orders> selectAll(Orders orders) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        if (orders != null) {
            if (orders.getUserId() != 0) {
                queryWrapper.eq("user_id", orders.getUserId());
            }
//            if (orders.getOrderNo() != null) {
//                queryWrapper.like("order_no", orders.getOrderNo());
//            }
            if (orders.getStatus() != null) {
                queryWrapper.eq("status", orders.getStatus());
            }
            if(orders.getBusinessId() != 0) {
                queryWrapper.eq("business_id", orders.getBusinessId());
            }
        }
        return ordersMapper.selectList(queryWrapper);
    }

    @Transactional
    @Override
    public PageInfo<Orders> selectPage(Orders orders, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Orders> list = selectAll(orders);
        return PageInfo.of(list);
    }

    @Transactional
    @Override
    public PageInfo<Orders> selectPageByGoodIds(List<Integer> ids, Integer pageNum, Integer pageSize) {
        Page<Orders> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        if (ids != null && !ids.isEmpty()) {
            wrapper.in("goods_id", ids);
        }
        var pagination = ordersMapper.selectPage(page, wrapper);
        PageInfo<Orders> pageInfo = new PageInfo<>(pagination.getRecords());
        pageInfo.setPageNum((int) pagination.getCurrent());
        pageInfo.setPageSize((int) pagination.getSize());
        pageInfo.setTotal(pagination.getTotal());
        pageInfo.setPages((int) pagination.getPages());
        return pageInfo;
    }
} 