package com.example.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Cart;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CartService extends IService<Cart> {
    List<Cart> selectAll(Cart cart);
    PageInfo<Cart> selectPage(Cart cart, Integer pageNum, Integer pageSize);
} 