package com.example.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.entity.Admin;
import com.example.user.service.AdminService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    /**
     * 新增
     */
    @SentinelResource(value = "adminAdd")
    @PostMapping("/add")
    public R add(@RequestBody Admin admin) {
        adminService.save(admin);
        return R.success();
    }

    /**
     * 删除
     */
    @SentinelResource(value = "adminDelete")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        adminService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除
     */
    @SentinelResource(value = "adminDeleteBatch")
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        adminService.removeBatchByIds(ids);
        return R.success();
    }

    /**
     * 修改
     */
    @SentinelResource(value = "adminUpdate")
    @PutMapping("/update")
    public R updateById(@RequestBody Admin admin) {
        adminService.updateById(admin);
        return R.success();
    }

    /**
     * 根据ID查询
     */
    @SentinelResource(value = "adminSelectById")
    @GetMapping("/selectById/{id}")
    public R selectById(@PathVariable Integer id) {
        Admin admin = adminService.getById(id);
        return R.success(admin);
    }

    /**
     * 查询所有
     */
    @SentinelResource(value = "adminSelectAll")
    @GetMapping("/selectAll")
    public R selectAll(Admin admin ) {
        List<Admin> list = adminService.selectAll(admin);
        return R.success(list);
    }

    /**
     * 分页查询
     */
    @SentinelResource(value = "adminSelectPage")
    @GetMapping("/selectPage")
    public R selectPage(Admin admin,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Admin> page = adminService.selectPage(admin, pageNum, pageSize);
        return R.success(page);
    }

} 