package com.example.order.controller;

import com.example.common.R;
import com.example.entity.Orders;
import com.example.order.service.OrdersService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Resource
    private OrdersService ordersService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public R add(@RequestBody Orders orders) {
        ordersService.save(orders);
        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R removeById(@PathVariable Integer id) {
        ordersService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        ordersService.removeBatchByIds(ids);
        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R updateById(@RequestBody Orders orders) {
        ordersService.updateById(orders);
        return R.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public R selectById(@PathVariable Integer id) {
        Orders orders = ordersService.getById(id);
        return R.success(orders);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public R selectAll(Orders orders) {
        List<Orders> list = ordersService.selectAll(orders);
        return R.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public R selectPage(Orders orders,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Orders> page = ordersService.selectPage(orders, pageNum, pageSize);
        return R.success(page);
    }

} 