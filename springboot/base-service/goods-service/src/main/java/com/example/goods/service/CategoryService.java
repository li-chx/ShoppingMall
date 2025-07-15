package com.example.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Category;
import com.example.entity.Goods;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryService extends IService<Category> {

    List<Category> selectAll(Category category);
    PageInfo<Category> selectPage(Category category, Integer pageNum, Integer pageSize);
} 