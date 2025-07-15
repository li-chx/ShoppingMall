package com.example.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Notice;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface NoticeService extends IService<Notice> {
    List<Notice> selectAll(Notice notice);
    PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize);
} 