package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.user.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Transactional
    @Override
    public List<User> selectAll(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (user != null) {
            if (user.getName() != null) {
                queryWrapper.like("name", user.getName());
            }
            if (user.getUsername() != null) {
                queryWrapper.like("username", user.getUsername());
            }
        }
        return userMapper.selectList(queryWrapper);
    }

    @Transactional
    @Override
    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = selectAll(user);
        return PageInfo.of(list);
    }

    @Transactional
    @Override
    public User login(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername())
                   .eq("password", user.getPassword());
        return userMapper.selectOne(queryWrapper);
    }

    @Transactional
    @Override
    public boolean resetPassword(User request)
    {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", request.getUsername())
                .eq("email", request.getEmail());
        User user = userMapper.selectOne(queryWrapper);
        if(user == null)
            return false;
        else {
            user.setPassword(request.getPassword());
            int result = userMapper.updateById(user);
            return result > 0;
        }
    }

    @Transactional
    @Override
    public boolean updatePassword(Integer id, String newPassword) {
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",id);
        User user = userMapper.selectOne(queryWrapper);
        if(user==null)
            return false;
        else{
            user.setPassword(newPassword);
            int result = userMapper.updateById(user);
            return result > 0;
        }
    }
}