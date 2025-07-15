package com.example.goods.controller;

import com.example.common.R;
import com.example.entity.Business;
import com.example.entity.Category;
import com.example.entity.Goods;
import com.example.dto.GoodsDTO;
import com.example.goods.feign.BusinessFeignClient;
import com.example.goods.service.CategoryService;
import com.example.goods.service.GoodsService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsService goodsService;

    @Resource
    private CategoryService categoryService;

    @Resource
    private BusinessFeignClient businessFeignClient;

    /**
     * 新增
     */
    @PostMapping("/add")
    public R add(@RequestBody Goods goods) {
        goodsService.save(goods);
        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        goodsService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        goodsService.removeBatchByIds(ids);
        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R updateById(@RequestBody Goods goods) {
        goodsService.updateById(goods);
        return R.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public R selectById(@PathVariable Integer id) {
        Goods goods = goodsService.getById(id);
        Business business = businessFeignClient.getBusinessById(goods.getBusinessId()).getData();
        Category category = categoryService.getById(goods.getCategoryId());
        GoodsDTO goodsDTO = new GoodsDTO(goods);
        goodsDTO.setBusinessName(business.getName());
        goodsDTO.setCategoryName(category.getName());
        return R.success(goodsDTO);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public R selectAll(Goods goods ) {
        List<Goods> list = goodsService.selectAll(goods);
        return R.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public R selectPage(Goods goods,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Goods> page = goodsService.selectPage(goods, pageNum, pageSize);
        return R.success(page);
    }

    /** 查询前5个商品展示*/
    @GetMapping("/selectTop5")
    public R selectTop5() {
        List<Goods> list = goodsService.selectTop5();
        return R.success(list);
    }

    /** 根据分类ID查询*/
    @GetMapping("/selectByCategoryId/{id}")
    public R selectByCategoryId(@PathVariable Integer id) {
        List<Goods> list = goodsService.selectByCategoryId(id);
        return R.success(list);
    }
} 