package com.example.goods.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
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
    @SentinelResource(value = "goods_add")
    @PostMapping("/add")
    public R add(@RequestBody Goods goods) {
        goodsService.save(goods);
        return R.success();
    }

    /**
     * 删除
     */
    @SentinelResource(value = "goods_delete")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        goodsService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除
     */
    @SentinelResource(value = "goods_delete_batch")
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        goodsService.removeBatchByIds(ids);
        return R.success();
    }

    /**
     * 修改
     */
    @SentinelResource(value = "goods_update")
    @PutMapping("/update")
    public R updateById(@RequestBody Goods goods) {
        goodsService.updateById(goods);
        return R.success();
    }

    /**
     * 根据ID查询
     */
    @SentinelResource(value = "goods_select_by_id")
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
    @SentinelResource(value = "goods_select_all")
    @GetMapping("/selectAll")
    public R selectAll(Goods goods ) {
        List<Goods> list = goodsService.selectAll(goods);
        return R.success(list);
    }

    /**
     * 分页查询
     */
    @SentinelResource(value = "goods_select_page")
    @GetMapping("/selectPage")
    public R selectPage(Goods goods,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize,
                        @RequestParam(defaultValue = "") String name,
                        @RequestParam(defaultValue = "-1") Integer businessId) {
        log.info(businessId.toString());
        log.info("goodsBusinessId"+goods.getBusinessId());
        goods.setName(name);
        if(businessId != -1)
            goods.setBusinessId(businessId);
        PageInfo<Goods> page = goodsService.selectPage(goods, pageNum, pageSize);

//        page.getList().forEach(item->{
//            log.info("商品信息: {}", item);
//        });

        List<GoodsDTO> goodsDTOList=page.getList().stream()
                .map(GoodsDTO::new)
                .toList();

        goodsDTOList.forEach(item -> {
            var business = businessFeignClient.getBusinessById(item.getBusinessId()).getData();
            item.setBusinessName(business.getName());
            var category = categoryService.getById(item.getCategoryId());
            item.setCategoryName(category.getName());
        });
        var goodsDTOPage =  new PageInfo<>(goodsDTOList);
        goodsDTOPage.setTotal(page.getTotal());

//        goodsDTOPage.getList().forEach(item->{
//            log.info("商品DTO信息: {}", item.getUnit());
//        });

        return R.success(goodsDTOPage);
    }

    /** 查询前5个商品展示*/
    @SentinelResource(value = "goods_select_top5")
    @GetMapping("/selectTop5")
    public R selectTop5() {
        List<Goods> list = goodsService.selectTop5();
        return R.success(list);
    }

    /** 根据分类ID查询*/
    @SentinelResource(value = "goods_select_by_category_id")
    @GetMapping("/selectByCategoryId/{id}")
    public R selectByCategoryId(@PathVariable Integer id) {
        List<Goods> list = goodsService.selectByCategoryId(id);
        return R.success(list);
    }

    /** 根据商品名字进行模糊查询*/
    @SentinelResource(value = "goods_select_by_name")
    @GetMapping("/selectPageByName")
    public R<PageInfo<Goods>> selectPageByName(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "") String goodsName) {
        PageInfo<Goods> page = goodsService.selectPageByName(goodsName, pageNum, pageSize);
        return R.success(page);
    }
} 