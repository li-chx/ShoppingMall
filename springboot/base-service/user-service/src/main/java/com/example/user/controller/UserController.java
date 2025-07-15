package com.example.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.entity.User;
import com.example.user.service.UserService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 用户前端操作接口
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 新增
     */
    @SentinelResource(value = "userAdd")
    @PostMapping("/add")
    public R add(@RequestBody User user) {
        userService.save(user);
        return R.success();
    }

    /**
     * 删除
     */
    @SentinelResource(value = "userDelete")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        userService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除
     */
    @SentinelResource(value = "userDeleteBatch")
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        userService.removeBatchByIds(ids);
        return R.success();
    }

    /**
     * 修改
     */
    @SentinelResource(value = "userUpdate")
    @PutMapping("/update")
    public R updateById(@RequestBody User user) {
        userService.updateById(user);
        return R.success();
    }

    /**
     * 根据ID查询
     */
    @SentinelResource(value = "userSelectById")
    @GetMapping("/selectById/{id}")
    public R<User> selectById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return R.success(user);
    }

    /**
     * 查询所有
     */
    @SentinelResource(value = "userSelectAll")
    @GetMapping("/selectAll")
    public R selectAll(User user ) {
        List<User> list = userService.selectAll(user);
        return R.success(list);
    }

    /**
     * 分页查询
     */
    @SentinelResource(value = "userSelectPage")
    @GetMapping("/selectPage")
    public R selectPage(User user,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<User> page = userService.selectPage(user, pageNum, pageSize);
        return R.success(page);
    }

} 