package com.example.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Goods;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface GoodsService extends IService<Goods> {

    List<Goods> selectAll(Goods goods);
    PageInfo<Goods> selectPage(Goods goods, Integer pageNum, Integer pageSize);
    // 新增
    List<Goods> selectTop5();
    List<Goods> selectByCategoryId(Integer categoryId);
    PageInfo<Goods> selectPageByName(String goodsName, Integer pageNum, Integer pageSize);
    boolean updateGainCount(Integer id, Integer status);

    List<Goods> selectByKeyword(String keyword);

}