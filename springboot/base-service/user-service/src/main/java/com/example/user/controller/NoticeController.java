package com.example.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.entity.Notice;
import com.example.user.service.NoticeService;
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

@Tag(name = "通知接口", description = "系统通知管理相关接口")
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @Operation(summary = "新增通知", description = "添加新的系统通知信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "添加成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "noticeAdd")
    @PostMapping("/add")
    public R add(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "通知信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = Notice.class)))
                 @RequestBody Notice notice) {
        noticeService.save(notice);
        return R.success();
    }

    @Operation(summary = "删除通知", description = "根据ID删除系统通知信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "noticeDelete")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@Parameter(description = "通知ID", required = true, in = ParameterIn.PATH)
                        @PathVariable Integer id) {
        noticeService.removeById(id);
        return R.success();
    }

    @Operation(summary = "批量删除通知", description = "根据ID列表批量删除系统通知信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "批量删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "noticeDeleteBatch")
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "通知ID列表",
            required = true)
                         @RequestBody List<Integer> ids) {
        noticeService.removeBatchByIds(ids);
        return R.success();
    }

    @Operation(summary = "修改通知", description = "根据ID更新系统通知信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "noticeUpdate")
    @PutMapping("/update")
    public R updateById(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "通知信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = Notice.class)))
                        @RequestBody Notice notice) {
        noticeService.updateById(notice);
        return R.success();
    }

    @Operation(summary = "查询通知", description = "根据ID获取系统通知信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Notice.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "noticeSelectById")
    @GetMapping("/selectById/{id}")
    public R selectById(@Parameter(description = "通知ID", required = true, in = ParameterIn.PATH)
                        @PathVariable Integer id) {
        Notice notice = noticeService.getById(id);
        return R.success(notice);
    }

    @Operation(summary = "查询所有通知", description = "根据条件获取所有系统通知信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = Notice.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "noticeSelectAll")
    @GetMapping("/selectAll")
    public R selectAll(@Parameter(description = "通知查询条件", in = ParameterIn.QUERY)
                       Notice notice) {
        List<Notice> list = noticeService.selectAll(notice);
        return R.success(list);
    }

    @Operation(summary = "分页查询通知", description = "根据条件分页获取系统通知信息")
    @Parameters({
            @Parameter(name = "pageNum", description = "页码", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "1")),
            @Parameter(name = "pageSize", description = "每页记录数", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = PageInfo.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "noticeSelectPage")
    @GetMapping("/selectPage")
    public R selectPage(@Parameter(description = "通知查询条件", in = ParameterIn.QUERY)
                        Notice notice,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Notice> page = noticeService.selectPage(notice, pageNum, pageSize);
        return R.success(page);
    }
}