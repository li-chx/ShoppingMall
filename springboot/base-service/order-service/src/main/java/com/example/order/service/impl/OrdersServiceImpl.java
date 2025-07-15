package com.example.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Orders;
import com.example.mapper.OrdersMapper;
import com.example.order.service.OrdersService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper,Orders> implements OrdersService {

    @Resource
    private OrdersMapper ordersMapper;

    @Override
    public List<Orders> selectAll(Orders orders) {
        QueryWrapper<Orders> queryWrapper = new QueryWrapper<>();
        if (orders != null) {
            if (orders.getUserId() != null) {
                queryWrapper.eq("user_id", orders.getUserId());
            }
//            if (orders.getOrderNo() != null) {
//                queryWrapper.like("order_no", orders.getOrderNo());
//            }
            if (orders.getStatus() != null) {
                queryWrapper.eq("status", orders.getStatus());
            }
        }
        return ordersMapper.selectList(queryWrapper);
    }

    @Override
    public PageInfo<Orders> selectPage(Orders orders, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Orders> list = selectAll(orders);
        return PageInfo.of(list);
    }
} 