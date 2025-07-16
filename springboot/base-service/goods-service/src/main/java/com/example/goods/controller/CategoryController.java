package com.example.goods.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.entity.Category;
import com.example.goods.service.CategoryService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "分类接口", description = "商品分类信息管理相关接口")
@RestController
@RequestMapping("/category")
public class CategoryController {

    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    @Resource
    private CategoryService categoryService;

    @Operation(summary = "新增分类", description = "添加新的商品分类信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "添加成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "category_add")
    @PostMapping("/add")
    public R add(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "分类信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = Category.class)))
                 @RequestBody Category category) {
        log.info("category: {}", category);
        categoryService.save(category);
        return R.success();
    }

    @Operation(summary = "删除分类", description = "根据ID删除商品分类信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "category_delete")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@Parameter(description = "分类ID", required = true, in = ParameterIn.PATH)
                        @PathVariable Integer id) {
        categoryService.removeById(id);
        return R.success();
    }

    @Operation(summary = "批量删除分类", description = "根据ID列表批量删除商品分类信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "批量删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "category_delete_batch")
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "分类ID列表",
            required = true)
                         @RequestBody List<Integer> ids) {
        categoryService.removeBatchByIds(ids);
        return R.success();
    }

    @Operation(summary = "修改分类", description = "根据ID更新商品分类信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "category_update")
    @PutMapping("/update")
    public R updateById(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "分类信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = Category.class)))
                        @RequestBody Category category) {
        log.info("Updating category: {}", category);
        categoryService.updateById(category);
        return R.success();
    }

    @Operation(summary = "查询分类", description = "根据ID获取商品分类信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "category_select_by_id")
    @GetMapping("/selectById/{id}")
    public R selectById(@Parameter(description = "分类ID", required = true, in = ParameterIn.PATH)
                        @PathVariable Integer id) {
        Category category = categoryService.getById(id);
        return R.success(category);
    }

    @Operation(summary = "查询所有分类", description = "获取所有商品分类信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Category.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "category_select_all")
    @GetMapping("/selectAll")
    public R selectAll(@Parameter(description = "分类查询条件", in = ParameterIn.QUERY)
                       Category category) {
        List<Category> list = categoryService.selectAll(category);
        return R.success(list);
    }

    @Operation(summary = "分页查询分类", description = "根据条件分页获取商品分类信息")
    @Parameters({
            @Parameter(name = "pageNum", description = "页码", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "1")),
            @Parameter(name = "pageSize", description = "每页记录数", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = PageInfo.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "category_select_page")
    @GetMapping("/selectPage")
    public R selectPage(@Parameter(description = "分类查询条件", in = ParameterIn.QUERY) Category category,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Category> page = categoryService.selectPage(category, pageNum, pageSize);
        return R.success(page);
    }
}