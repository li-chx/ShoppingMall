package com.example.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.entity.Address;
import com.example.user.service.AddressService;
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

@Tag(name = "地址接口", description = "用户地址管理相关接口")
@RestController
@RequestMapping("/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    @Operation(summary = "新增地址", description = "添加新的用户地址信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "添加成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "address_add")
    @PostMapping("/add")
    public R add(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "地址信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = Address.class)))
                 @RequestBody Address address) {
        addressService.save(address);
        return R.success();
    }

    @Operation(summary = "删除地址", description = "根据ID删除用户地址信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "address_delete")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@Parameter(description = "地址ID", required = true, in = ParameterIn.PATH)
                        @PathVariable Integer id) {
        addressService.removeById(id);
        return R.success();
    }

    @Operation(summary = "批量删除地址", description = "根据ID列表批量删除用户地址信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "批量删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "address_delete_batch")
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "地址ID列表",
            required = true)
                         @RequestBody List<Integer> ids) {
        addressService.removeBatchByIds(ids);
        return R.success();
    }

    @Operation(summary = "修改地址", description = "根据ID更新用户地址信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "address_update")
    @PutMapping("/update")
    public R updateById(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "地址信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = Address.class)))
                        @RequestBody Address address) {
        addressService.updateById(address);
        return R.success();
    }

    @Operation(summary = "查询地址", description = "根据ID获取用户地址信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Address.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "address_select_by_id")
    @GetMapping("/selectById/{id}")
    public R<Address> selectById(@Parameter(description = "地址ID", required = true, in = ParameterIn.PATH)
                                 @PathVariable Integer id) {
        Address address = addressService.getById(id);
        return R.success(address);
    }

    @Operation(summary = "查询所有地址", description = "根据条件获取所有用户地址信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Address.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "address_select_all")
    @GetMapping("/selectAll")
    public R selectAll(@Parameter(description = "地址查询条件", in = ParameterIn.QUERY)
                       Address address) {
        List<Address> list = addressService.selectAll(address);
        return R.success(list);
    }

    @Operation(summary = "分页查询地址", description = "根据条件分页获取用户地址信息")
    @Parameters({
            @Parameter(name = "pageNum", description = "页码", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "1")),
            @Parameter(name = "pageSize", description = "每页记录数", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = PageInfo.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "address_select_page")
    @GetMapping("/selectPage")
    public R selectPage(@Parameter(description = "地址查询条件", in = ParameterIn.QUERY)
                        Address address,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Address> page = addressService.selectPage(address, pageNum, pageSize);
        return R.success(page);
    }
}