package com.example.user.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.R;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.entity.Business;
import com.example.entity.User;
import com.example.user.service.AdminService;
import com.example.user.service.BusinessService;
import com.example.user.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * 基础前端接口
 */
@RestController
public class WebController {

    @Resource
    private AdminService adminService;
    @Resource
    private BusinessService businessService;
    @Resource
    private UserService userService;

    @GetMapping("/")
    public R hello() {
        return R.success("访问成功");
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public R login(@RequestBody Account account) {
        if (ObjectUtil.isEmpty(account.getUsername()) || ObjectUtil.isEmpty(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return R.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            Admin admin = new Admin();
            admin.setUsername(account.getUsername());
            admin.setPassword(account.getPassword());
            Admin loginAdmin = adminService.login(admin);
            if (loginAdmin == null) return R.error(ResultCodeEnum.USER_ACCOUNT_ERROR);
            account.setId(loginAdmin.getId()); // 补充id
            account.setName(loginAdmin.getName());
            account.setRole(loginAdmin.getRole());
            account.setAvatar(loginAdmin.getAvatar());
            return R.success(account);
        }
        if (RoleEnum.BUSINESS.name().equals(account.getRole())) {
            Business business = new Business();
            business.setUsername(account.getUsername());
            business.setPassword(account.getPassword());
            Business loginBusiness = businessService.login(business);
            if (loginBusiness == null) return R.error(ResultCodeEnum.USER_ACCOUNT_ERROR);
            account.setId(loginBusiness.getId()); // 补充id
            account.setName(loginBusiness.getName());
            account.setRole(loginBusiness.getRole());
            account.setAvatar(loginBusiness.getAvatar());
            return R.success(account);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            User user = new User();
            user.setUsername(account.getUsername());
            user.setPassword(account.getPassword());
            User loginUser = userService.login(user);
            if (loginUser == null) return R.error(ResultCodeEnum.USER_ACCOUNT_ERROR);
            account.setId(loginUser.getId()); // 补充id
            account.setName(loginUser.getName());
            account.setRole(loginUser.getRole());
            account.setAvatar(loginUser.getAvatar());
//            account.setPhone(loginUser.getPhone());
//            account.setEmail(loginUser.getEmail());
            return R.success(account);
        }
        return R.error(ResultCodeEnum.USER_ACCOUNT_ERROR);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public R register(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return R.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            Admin admin = new Admin();
            admin.setUsername(account.getUsername());
            admin.setPassword(account.getPassword());
            admin.setName(account.getName());
            admin.setRole(account.getRole());
            admin.setAvatar(account.getAvatar());
//            admin.setPhone(account.getPhone());
//            admin.setEmail(account.getEmail());
            adminService.save(admin);
        }
        if (RoleEnum.BUSINESS.name().equals(account.getRole())) {
            Business business = new Business();
            business.setUsername(account.getUsername());
            business.setPassword(account.getPassword());
            business.setName(account.getName());
            business.setRole(account.getRole());
            business.setAvatar(account.getAvatar());
            businessService.save(business);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            User user = new User();
            user.setUsername(account.getUsername());
            user.setPassword(account.getPassword());
            user.setName(account.getName());
            user.setRole(account.getRole());
            user.setAvatar(account.getAvatar());
            userService.save(user);
        }
        return R.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public R updatePassword(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getNewPassword())) {
            return R.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            Admin admin = new Admin();
            admin.setUsername(account.getUsername());
            admin.setPassword(account.getNewPassword());
            adminService.updateById(admin);
        }
        if (RoleEnum.BUSINESS.name().equals(account.getRole())) {

            Business business = new Business();
            business.setUsername(account.getUsername());
            business.setPassword(account.getNewPassword());
            businessService.updateById(business);

        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            User user = new User();
            user.setUsername(account.getUsername());
            user.setPassword(account.getNewPassword());
            userService.updateById(user);
        }
        return R.success();
    }

} 