package com.example.goods.controller;

import com.example.common.R;
import com.example.entity.Category;
import com.example.goods.service.CategoryService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public R add(@RequestBody Category category) {
        categoryService.save(category);
        return R.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        categoryService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        categoryService.removeBatchByIds(ids);
        return R.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R updateById(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success();
    }

    /**
     * 根据ID查询
     */
    @GetMapping("/selectById/{id}")
    public R selectById(@PathVariable Integer id) {
        Category category = categoryService.getById(id);
        return R.success(category);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public R selectAll(Category category ) {
        List<Category> list = categoryService.selectAll(category);
        return R.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public R selectPage(Category category,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Category> page = categoryService.selectPage(category, pageNum, pageSize);
        return R.success(page);
    }

} 