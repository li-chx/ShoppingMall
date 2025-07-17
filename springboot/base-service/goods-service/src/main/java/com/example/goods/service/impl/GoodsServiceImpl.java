package com.example.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Goods;
import com.example.mapper.GoodsMapper;
import com.example.goods.service.GoodsService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper,Goods> implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Transactional
    @Override
    public List<Goods> selectAll(Goods goods) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (goods != null) {
            if (goods.getName() != null) {
                queryWrapper.like("name", goods.getName());
            }
            if (goods.getCategoryId() != null) {
                queryWrapper.eq("category_id", goods.getCategoryId());
            }
            if (goods.getBusinessId() != null) {
                queryWrapper.eq("business_id", goods.getBusinessId());
            }
//            if (goods.getStatus() != null) {
//                queryWrapper.eq("status", goods.getStatus());
            // }
        }
        return goodsMapper.selectList(queryWrapper);
    }

    @Transactional
    @Override
    public PageInfo<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Goods> list = selectAll(goods);
        return PageInfo.of(list);
    }

    @Transactional
    @Override
    public List<Goods> selectTop5() {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.last("limit 5");
        return goodsMapper.selectList(queryWrapper);
    }

    @Transactional
    @Override
    public List<Goods> selectByCategoryId(Integer categoryId) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", categoryId);
        return goodsMapper.selectList(queryWrapper);
    }


    @Transactional
    @Override
    public boolean updateGainCount(Integer id, Integer gainCount) {
        Goods goods= goodsMapper.selectById(id);
        if (goods == null) {
            return false; // 商品不存在
        }
        goods.setCount(goods.getCount() + gainCount);
        int rows = goodsMapper.updateById(goods);
        return rows > 0; // 返回更新是否成功
    }

    @Transactional
    @Override
    public PageInfo<Goods> selectPageByName(String goodsName, Integer pageNum, Integer pageSize) {
        Page<Goods> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        if (goodsName != null && !goodsName.isEmpty()) {
            wrapper.like("name", goodsName);
        }
        var pagination = goodsMapper.selectPage(page, wrapper);
        PageInfo<Goods> pageInfo = new PageInfo<>(pagination.getRecords());
        pageInfo.setPageNum((int) pagination.getCurrent());
        pageInfo.setPageSize((int) pagination.getSize());
        pageInfo.setTotal(pagination.getTotal());
        pageInfo.setPages((int) pagination.getPages());
        return pageInfo;
    }

    @Transactional
    @Override
    public List<Goods> selectByKeyword(String keyword) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            queryWrapper.like("name", keyword);
        }
        // 按ID降序排列，展示最新商品
        queryWrapper.orderByDesc("id");
        return goodsMapper.selectList(queryWrapper);
    }
} 