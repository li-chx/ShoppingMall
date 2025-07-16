package com.example.business.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.entity.Business;
import com.example.business.service.BusinessService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.refresh.RefreshScopeLifecycle;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "商家接口", description = "商家信息管理相关接口")
@RestController
@RequestMapping("/business")
public class BusinessController {

    @Resource
    private BusinessService businessService;
    @Autowired
    private RefreshScopeLifecycle refreshScopeLifecycle;

    @Operation(summary = "新增商家", description = "添加新的商家信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "添加成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value="business_add")
    @PostMapping("/add")
    public R add(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "商家信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = Business.class)))
                 @RequestBody Business business) {
        businessService.save(business);
        return R.success();
    }

    @Operation(summary = "删除商家", description = "根据ID删除商家信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value="business_delete")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@Parameter(description = "商家ID", required = true, in = ParameterIn.PATH)
                        @PathVariable Integer id) {
        businessService.removeById(id);
        return R.success();
    }

    @Operation(summary = "批量删除商家", description = "根据ID列表批量删除商家信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "批量删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value="business_delete_batch")
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "商家ID列表",
            required = true)
                         @RequestBody List<Integer> ids) {
        businessService.removeBatchByIds(ids);
        return R.success();
    }

    @PostMapping("/updatePassword")
    public R updatePassword(@RequestParam Integer id,
                            @RequestParam String newPassword){
        if(businessService.updatePassword(id, newPassword)){
            return R.success();
        }else{
            R result = R.error();
            result.setMsg("更新密码失败，参数错误！");
            return result;
        }
    }

    @Operation(summary = "修改商家", description = "根据ID更新商家信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value="business_update")
    @PutMapping("/update")
    public R updateById(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "商家信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = Business.class)))
                        @RequestBody Business business) {
        businessService.updateById(business);
        return R.success();
    }

    @Operation(summary = "查询商家", description = "根据ID获取商家信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Business.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value="business_select_by_id")
    @GetMapping("/selectById/{id}")
    public R selectById(@Parameter(description = "商家ID", required = true, in = ParameterIn.PATH)
                        @PathVariable Integer id) {
        Business business = businessService.getById(id);
        return R.success(business);
    }

    @Operation(summary = "查询所有商家", description = "获取所有商家信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Business.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value="business_select_all")
    @GetMapping("/selectAll")
    public R selectAll(@Parameter(description = "商家查询条件", in = ParameterIn.QUERY)
                       Business business) {
        List<Business> list = businessService.selectAll(business);
        return R.success(list);
    }

    @Operation(summary = "分页查询商家", description = "根据条件分页获取商家信息")
    @Parameters({
            @Parameter(name = "pageNum", description = "页码", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "1")),
            @Parameter(name = "pageSize", description = "每页记录数", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = PageInfo.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value="business_select_page")
    @GetMapping("/selectPage")
    public R selectPage(@Parameter(description = "商家查询条件", in = ParameterIn.QUERY) Business business,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Business> page = businessService.selectPage(business, pageNum, pageSize);
        return R.success(page);
    }
}