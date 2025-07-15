package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.Notice;
import com.example.mapper.NoticeMapper;
import com.example.user.service.NoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> selectAll(Notice notice) {
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();
        if (notice != null) {
            if (notice.getTitle() != null) {
                queryWrapper.like("title", notice.getTitle());
            }
        }
        return noticeMapper.selectList(queryWrapper);
    }

    @Override
    public PageInfo<Notice> selectPage(Notice notice, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = selectAll(notice);
        return PageInfo.of(list);
    }
} 