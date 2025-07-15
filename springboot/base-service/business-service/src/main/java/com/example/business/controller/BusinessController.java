package com.example.business.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.entity.Business;
import com.example.business.service.BusinessService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Tag(name = "商家接口")
@RestController
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private BusinessService businessService;


    /**
     * 新增
     */
    @Operation(summary = "新增商家", description = "添加新的商家信息")
    @SentinelResource(value="business_add")
    @PostMapping("/add")
    @Parameter(name = "business", description = "商家信息对象", required = true)
    public R add(@RequestBody Business business) {
        businessService.save(business);
        return R.success();
    }

    /**
     * 删除
     */
    @Operation(summary = "删除商家", description = "根据ID删除商家信息")
    @SentinelResource(value="business_delete")
    @DeleteMapping("/delete/{id}")
    @Parameter(name = "id", description = "商家ID", required = true)
    public R deleteById(@PathVariable Integer id) {
        businessService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除
     */
    @Operation(summary = "批量删除商家", description = "根据ID列表批量删除商家信息")
    @SentinelResource(value="business_delete_batch")
    @DeleteMapping("/delete/batch")
    @Parameter(name = "ids", description = "商家ID列表", required = true)
    public R deleteBatch(@RequestBody List<Integer> ids) {
        businessService.removeBatchByIds(ids);
        return R.success();
    }

    /**
     * 修改
     */
    @Operation(summary = "修改商家", description = "根据ID更新商家信息")
    @SentinelResource(value="business_update")
    @PutMapping("/update")
    @Parameter(name = "business", description = "商家信息对象", required = true)
    public R updateById(@RequestBody Business business) {
        businessService.updateById(business);
        return R.success();
    }

    /**
     * 根据ID查询
     */
    @Operation(summary = "查询商家", description = "根据ID获取商家信息")
    @SentinelResource(value="business_select_by_id")
    @GetMapping("/selectById/{id}")
    @Parameter(name = "id", description = "商家ID", required = true)
    public R selectById(@PathVariable Integer id) {
        Business business = businessService.getById(id);
        return R.success(business);
    }

    /**
     * 查询所有
     */
    @Operation(summary = "查询所有商家", description = "获取所有商家信息")
    @SentinelResource(value="business_select_all")
    @GetMapping("/selectAll")
    @Parameter(name = "business", description = "商家查询条件", required = false)
    public R selectAll(Business business ) {
        List<Business> list = businessService.selectAll(business);
        return R.success(list);
    }

    /**
     * 分页查询
     */
    @Operation(summary = "分页查询商家", description = "根据条件分页获取商家信息")
    @SentinelResource(value="business_select_page")
    @GetMapping("/selectPage")
    public R selectPage(Business business,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Business> page = businessService.selectPage(business, pageNum, pageSize);
        return R.success(page);
    }

} 