package com.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> selectAll(User user);
    PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize);
    User login(User user);
    boolean resetPassword(User request);
} 