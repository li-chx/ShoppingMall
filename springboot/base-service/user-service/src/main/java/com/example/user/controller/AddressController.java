package com.example.user.controller;

import com.example.common.R;
import com.example.entity.Address;
import com.example.user.service.AddressService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public R add(@RequestBody Address address) {
        addressService.save(address);
        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        addressService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        addressService.removeBatchByIds(ids);
        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R updateById(@RequestBody Address address) {
        addressService.updateById(address);
        return R.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public R selectById(@PathVariable Integer id) {
        Address address = addressService.getById(id);
        return R.success(address);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public R selectAll(Address address ) {
        List<Address> list = addressService.selectAll(address);
        return R.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public R selectPage(Address address,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Address> page = addressService.selectPage(address, pageNum, pageSize);
        return R.success(page);
    }

} 