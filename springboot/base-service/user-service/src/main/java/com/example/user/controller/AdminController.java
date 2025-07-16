package com.example.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.entity.Admin;
import com.example.user.service.AdminService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "管理员接口", description = "管理员信息管理相关接口")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminService adminService;

    @Operation(summary = "新增管理员", description = "添加新的管理员信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "添加成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "adminAdd")
    @PostMapping("/add")
    public R add(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "管理员信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = Admin.class)))
                 @RequestBody Admin admin) {
        adminService.save(admin);
        return R.success();
    }

    @Operation(summary = "删除管理员", description = "根据ID删除管理员信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "adminDelete")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@Parameter(description = "管理员ID", required = true, in = ParameterIn.PATH)
                        @PathVariable Integer id) {
        adminService.removeById(id);
        return R.success();
    }

    @Operation(summary = "批量删除管理员", description = "根据ID列表批量删除管理员信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "批量删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "adminDeleteBatch")
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "管理员ID列表",
            required = true)
                         @RequestBody List<Integer> ids) {
        adminService.removeBatchByIds(ids);
        return R.success();
    }

    @Operation(summary = "修改管理员", description = "根据ID更新管理员信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "adminUpdate")
    @PutMapping("/update")
    public R updateById(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "管理员信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = Admin.class)))
                        @RequestBody Admin admin) {
        adminService.updateById(admin);
        return R.success();
    }

    @Operation(summary = "查询管理员", description = "根据ID获取管理员信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Admin.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "adminSelectById")
    @GetMapping("/selectById/{id}")
    public R selectById(@Parameter(description = "管理员ID", required = true, in = ParameterIn.PATH)
                        @PathVariable Integer id) {
        Admin admin = adminService.getById(id);
        return R.success(admin);
    }

    @Operation(summary = "查询所有管理员", description = "根据条件获取所有管理员信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Admin.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "adminSelectAll")
    @GetMapping("/selectAll")
    public R selectAll(@Parameter(description = "管理员查询条件", in = ParameterIn.QUERY)
                       Admin admin) {
        List<Admin> list = adminService.selectAll(admin);
        return R.success(list);
    }

    @Operation(summary = "分页查询管理员", description = "根据条件分页获取管理员信息")
    @Parameters({
            @Parameter(name = "pageNum", description = "页码", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "1")),
            @Parameter(name = "pageSize", description = "每页记录数", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = PageInfo.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "adminSelectPage")
    @GetMapping("/selectPage")
    public R selectPage(@Parameter(description = "管理员查询条件", in = ParameterIn.QUERY)
                        Admin admin,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Admin> page = adminService.selectPage(admin, pageNum, pageSize);
        page.setList(page.getList().stream().peek(x -> x.setPassword(null)).toList());
        return R.success(page);
    }
}