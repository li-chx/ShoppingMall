package com.example.business.controller;

import com.example.common.R;
import com.example.entity.Business;
import com.example.business.service.BusinessService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private BusinessService businessService;


    /**
     * 新增
     */
    @PostMapping("/add")
    public R add(@RequestBody Business business) {
        businessService.save(business);
        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        businessService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        businessService.removeBatchByIds(ids);
        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R updateById(@RequestBody Business business) {
        businessService.updateById(business);
        return R.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public R selectById(@PathVariable Integer id) {
        Business business = businessService.getById(id);
        return R.success(business);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public R selectAll(Business business ) {
        List<Business> list = businessService.selectAll(business);
        return R.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public R selectPage(Business business,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Business> page = businessService.selectPage(business, pageNum, pageSize);
        return R.success(page);
    }

} 