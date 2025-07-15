package com.example.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Cart;
import com.example.mapper.CartMapper;
import com.example.order.service.CartService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper,Cart> implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Override
    public List<Cart> selectAll(Cart cart) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        if (cart != null) {
            if (cart.getUserId() != null) {
                queryWrapper.eq("user_id", cart.getUserId());
            }
            if (cart.getGoodsId() != null) {
                queryWrapper.eq("goods_id", cart.getGoodsId());
            }
        }
        return cartMapper.selectList(queryWrapper);
    }

    @Override
    public PageInfo<Cart> selectPage(Cart cart, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Cart> list = selectAll(cart);
        return PageInfo.of(list);
    }
} 