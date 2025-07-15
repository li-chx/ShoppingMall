package com.example.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Orders;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface OrdersService extends IService<Orders> {

    List<Orders> selectAll(Orders orders);
    PageInfo<Orders> selectPage(Orders orders, Integer pageNum, Integer pageSize);
} 