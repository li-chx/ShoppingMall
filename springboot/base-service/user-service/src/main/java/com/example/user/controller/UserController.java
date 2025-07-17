package com.example.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.common.enums.ResultCodeEnum;
import com.example.entity.User;
import com.example.user.service.UserService;
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

@Tag(name = "用户接口", description = "用户信息管理相关接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Operation(summary = "新增用户", description = "添加新的用户信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "添加成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "userAdd")
    @PostMapping("/add")
    public R add(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "用户信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = User.class)))
                 @RequestBody User user) {
        userService.save(user);
        return R.success();
    }

    @Operation(summary = "删除用户", description = "根据ID删除用户信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "userDelete")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@Parameter(description = "用户ID", required = true, in = ParameterIn.PATH)
                        @PathVariable Integer id) {
        userService.removeById(id);
        return R.success();
    }

    @Operation(summary = "批量删除用户", description = "根据ID列表批量删除用户信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "批量删除成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "userDeleteBatch")
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "用户ID列表",
            required = true)
                         @RequestBody List<Integer> ids) {
        userService.removeBatchByIds(ids);
        return R.success();
    }

    @Operation(summary = "修改用户", description = "根据ID更新用户信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "userUpdate")
    @PutMapping("/update")
    public R updateById(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "用户信息对象",
            required = true,
            content = @Content(schema = @Schema(implementation = User.class)))
                        @RequestBody User user) {
        userService.updateById(user);
        return R.success();
    }

    @Operation(summary = "查询用户", description = "根据ID获取用户信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "userSelectById")
    @GetMapping("/selectById/{id}")
    public R<User> selectById(@Parameter(description = "用户ID", required = true, in = ParameterIn.PATH)
                              @PathVariable Integer id) {
        User user = userService.getById(id);
        return R.success(user);
    }

    @Operation(summary = "查询所有用户", description = "根据条件获取所有用户信息")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "userSelectAll")
    @GetMapping("/selectAll")
    public R selectAll(@Parameter(description = "用户查询条件", in = ParameterIn.QUERY)
                       User user) {
        List<User> list = userService.selectAll(user);
        return R.success(list);
    }

    @Operation(summary = "更新用户密码", description = "根据用户ID更新用户的密码")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "更新成功"),
            @ApiResponse(responseCode = "400", description = "参数错误"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "user_update_password")
    @PostMapping("/updatePassword")
    public R updatePassword(@Parameter(description = "用户ID", required = true, in = ParameterIn.QUERY)
                            @RequestParam Integer id,
                            @Parameter(description = "新密码", required = true, in = ParameterIn.QUERY)
                            @RequestParam String newPassword) {
        if (userService.updatePassword(id, newPassword)) {
            return R.success();
        } else {
            R result = R.error(ResultCodeEnum.PARAM_ERROR);
            result.setMsg("更新密码失败，参数错误");
            return result;
        }
    }

    @Operation(summary = "分页查询用户", description = "根据条件分页获取用户信息")
    @Parameters({
            @Parameter(name = "pageNum", description = "页码", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "1")),
            @Parameter(name = "pageSize", description = "每页记录数", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10"))
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "查询成功",
                    content = @Content(schema = @Schema(implementation = PageInfo.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "userSelectPage")
    @GetMapping("/selectPage")
    public R selectPage(@Parameter(description = "用户查询条件", in = ParameterIn.QUERY)
                        User user,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<User> page = userService.selectPage(user, pageNum, pageSize);
        return R.success(page);
    }
}