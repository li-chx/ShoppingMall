package com.example.user.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.entity.Business;
import com.example.entity.User;
import com.example.user.config.MailProperties;
import com.example.user.service.AdminService;
import com.example.user.service.BusinessService;
import com.example.user.service.IdentifyingCodeService;
import com.example.user.service.UserService;
import com.example.user.utils.MailUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.mail.MessagingException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 基础前端接口
 */
@Tag(name = "基础接口", description = "系统基础功能相关接口")
@RestController
public class WebController {
    @Resource
    MailProperties mailProperties;
    @Resource
    private AdminService adminService;
    @Resource
    private BusinessService businessService;
    @Resource
    private UserService userService;
    @Resource
    private IdentifyingCodeService identifyingCode;
    @Resource
    private MailUtil mailUtil;
    @Autowired
    private IdentifyingCodeService identifyingCodeService;

    @Operation(summary = "欢迎页面", description = "系统访问测试接口")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "访问成功",
                    content = @Content(schema = @Schema(implementation = R.class)))
    })
    @GetMapping("/")
    public R hello() {
        return R.success("访问成功");
    }

    /**
     * 登录
     */
    @Operation(summary = "用户登录", description = "根据用户名、密码和角色进行登录验证")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "登录成功",
                    content = @Content(schema = @Schema(implementation = Account.class))),
            @ApiResponse(responseCode = "500", description = "用户名或密码错误")
    })
    @SentinelResource(value = "login")
    @PostMapping("/login")
    public R login(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "账号登录信息",
            required = true,
            content = @Content(schema = @Schema(implementation = Account.class)))
                   @RequestBody Account account) {
        System.out.println(mailProperties.getMailTemplate());
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
            return R.success(admin);
        }
        if (RoleEnum.BUSINESS.name().equals(account.getRole())) {
            Business business = new Business();
            business.setUsername(account.getUsername());
            business.setPassword(account.getPassword());
            Business loginBusiness = businessService.login(business);
            if (loginBusiness == null) return R.error(ResultCodeEnum.USER_ACCOUNT_ERROR);
            return R.success(loginBusiness);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            User user = new User();
            user.setUsername(account.getUsername());
            user.setPassword(account.getPassword());
            User loginUser = userService.login(user);
            if (loginUser == null) return R.error(ResultCodeEnum.USER_ACCOUNT_ERROR);
            return R.success(loginUser);
        }
        return R.error(ResultCodeEnum.USER_ACCOUNT_ERROR);
    }

    /**
     * 注册
     */
    @Operation(summary = "用户注册", description = "创建新的用户账号")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "注册成功"),
            @ApiResponse(responseCode = "500", description = "注册失败，参数缺失")
    })
    @SentinelResource(value = "register")
    @PostMapping("/register")
    public R register(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "账号注册信息",
            required = true,
            content = @Content(schema = @Schema(implementation = Account.class)))
                      @RequestBody Account account,
                      @RequestParam String email,
                      @RequestParam String identifyingCode
    ) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return R.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if(!identifyingCodeService.testIdentifyingCode(email, IdentifyingCodeService.IdentifyingCodeType.REGISTER,identifyingCode))
        {
            return R.error(ResultCodeEnum.PARAM_IDENTIFYING_CODE_ERROR);
        }
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            Admin admin = new Admin();
            admin.setUsername(account.getUsername());
            admin.setPassword(account.getPassword());
            if(account.getName() == null || account.getName().isEmpty())
                admin.setName(account.getUsername());
            else
                admin.setName(account.getName());
            admin.setRole(account.getRole());
            admin.setAvatar(account.getAvatar());
            admin.setEmail(email);
            adminService.save(admin);
        }
        if (RoleEnum.BUSINESS.name().equals(account.getRole())) {
            Business business = new Business();
            business.setUsername(account.getUsername());
            business.setPassword(account.getPassword());
            if(business.getName() == null || business.getName().isEmpty())
                business.setName(account.getUsername());
            else
                business.setName(account.getName());
            business.setName(account.getName());
            business.setRole(account.getRole());
            business.setAvatar(account.getAvatar());
            business.setEmail(email);
            businessService.save(business);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            User user = new User();
            user.setUsername(account.getUsername());
            user.setPassword(account.getPassword());
            user.setName(account.getName());
            user.setRole(account.getRole());
            user.setAvatar(account.getAvatar());
            user.setEmail(email);
            userService.save(user);
        }
        return R.success();
    }

    /**
     * 修改密码
     */
    @Operation(summary = "修改密码", description = "更新用户账号密码")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "密码修改成功"),
            @ApiResponse(responseCode = "500", description = "密码修改失败，参数缺失")
    })
    @SentinelResource(value = "updatePassword")
    @PutMapping("/updatePassword")
    public R updatePassword(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "账号密码更新信息",
            required = true,
            content = @Content(schema = @Schema(implementation = Account.class)))
                            @RequestBody Account account) {
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

    @GetMapping("/sendCode/{codeType}")
    public R<?> sendCode(@PathVariable String codeType,String email) throws MessagingException
    {
        if(Objects.equals(codeType, IdentifyingCodeService.IdentifyingCodeType.REGISTER.name()))
        {
            var captcha = identifyingCode.getIdentifyingCode(email, IdentifyingCodeService.IdentifyingCodeType.REGISTER, 15 * 60);
            if (captcha == null) {
                return R.error(ResultCodeEnum.PARAM_ERROR.code, "验证码请求过于频繁，请稍后再试");
            }
            String mailTemplate = mailProperties.getMailTemplate();
            String tip = "您正在进行注册操作，这是您验证帐户所需的令牌验证码";
            return sendMail(email, captcha, mailTemplate, tip);
        } else if (Objects.equals(codeType, IdentifyingCodeService.IdentifyingCodeType.PASSWORD_RESET.name())) {
            var captcha = identifyingCode.getIdentifyingCode(email, IdentifyingCodeService.IdentifyingCodeType.PASSWORD_RESET, 15 * 60);
            if (captcha == null) {
                return R.error(ResultCodeEnum.PARAM_ERROR.code,"验证码请求过于频繁，请稍后再试");
            }
            String mailTemplate = mailProperties.getMailTemplate();
            String tip = "您正在进行密码重置操作，这是您验证帐户所需的令牌验证码";
            return sendMail(email, captcha, mailTemplate, tip);
        }
        return R.error(ResultCodeEnum.PARAM_LOST_ERROR);
    }

    @GetMapping("/resetPassword")
    public R<?> resetPassword(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String role,
            @RequestParam String identifyingCode,
            @RequestParam String password
    ) {
        if (StrUtil.isBlank(username) || StrUtil.isBlank(email) || StrUtil.isBlank(role)
                || StrUtil.isBlank(identifyingCode) || StrUtil.isBlank(password)) {
            return R.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }
        if (!identifyingCodeService.testIdentifyingCode(email, IdentifyingCodeService.IdentifyingCodeType.PASSWORD_RESET, identifyingCode)) {
            return R.error(ResultCodeEnum.PARAM_IDENTIFYING_CODE_ERROR);
        }
        boolean success = false;
        if (RoleEnum.ADMIN.name().equals(role)) {
            Admin admin = new Admin();
            admin.setEmail(email);
            admin.setUsername(username);
            admin.setPassword(password);
            success = adminService.resetPassword(admin);
        } else if (RoleEnum.BUSINESS.name().equals(role)) {
            Business business = new Business();
            business.setEmail(email);
            business.setUsername(username);
            business.setPassword(password);
            success = businessService.resetPassword(business);
        } else if (RoleEnum.USER.name().equals(role)) {
            User user = new User();
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            success = userService.resetPassword(user);
        } else {
            return R.error(ResultCodeEnum.PARAM_ERROR.code, "未知角色");
        }
        if(!success)
            return R.error(ResultCodeEnum.PARAM_LOST_ERROR.code, "密码重置失败");
        return R.success("密码重置成功");
    }

    @NotNull
    private R<String> sendMail(String email, String captcha, String mailTemplate, String tip) throws MessagingException {
        String expireTime = "15";
        mailTemplate = mailTemplate.replace("{tip}", tip);
        mailTemplate = mailTemplate.replace("{Captcha}", captcha);
        mailTemplate = mailTemplate.replace("{ExpireTime}", expireTime);
        mailUtil.sendHtmlEmail(
                email,
                "FOE's internship project",
                mailTemplate);
        return R.success("验证码已发送到您的邮箱，请注意查收");
    }
}