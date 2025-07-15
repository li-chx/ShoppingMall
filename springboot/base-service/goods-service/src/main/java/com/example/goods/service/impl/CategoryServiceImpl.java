package com.example.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Category;
import com.example.mapper.CategoryMapper;
import com.example.goods.service.CategoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> selectAll(Category category) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (category != null) {
            if (category.getName() != null) {
                queryWrapper.like("name", category.getName());
            }
        }
        return categoryMapper.selectList(queryWrapper);
    }

    @Override
    public PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Category> list = selectAll(category);
        return PageInfo.of(list);
    }
} 