package com.example.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.entity.Notice;
import com.example.user.service.NoticeService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     * 新增
     */
    @SentinelResource(value = "noticeAdd")
    @PostMapping("/add")
    public R add(@RequestBody Notice notice) {
        noticeService.save(notice);
        return R.success();
    }

    /**
     * 删除
     */
    @SentinelResource(value = "noticeDelete")
    @DeleteMapping("/delete/{id}")
    public R deleteById(@PathVariable Integer id) {
        noticeService.removeById(id);
        return R.success();
    }

    /**
     * 批量删除
     */
    @SentinelResource(value = "noticeDeleteBatch")
    @DeleteMapping("/delete/batch")
    public R deleteBatch(@RequestBody List<Integer> ids) {
        noticeService.removeBatchByIds(ids);
        return R.success();
    }

    /**
     * 修改
     */
    @SentinelResource(value = "noticeUpdate")
    @PutMapping("/update")
    public R updateById(@RequestBody Notice notice) {
        noticeService.updateById(notice);
        return R.success();
    }

    /**
     * 根据ID查询
     */
    @SentinelResource(value = "noticeSelectById")
    @GetMapping("/selectById/{id}")
    public R selectById(@PathVariable Integer id) {
        Notice notice = noticeService.getById(id);
        return R.success(notice);
    }

    /**
     * 查询所有
     */
    @SentinelResource(value = "noticeSelectAll")
    @GetMapping("/selectAll")
    public R selectAll(Notice notice ) {
        List<Notice> list = noticeService.selectAll(notice);
        return R.success(list);
    }

    /**
     * 分页查询
     */
    @SentinelResource(value = "noticeSelectPage")
    @GetMapping("/selectPage")
    public R selectPage(Notice notice,
                        @RequestParam(defaultValue = "1") Integer pageNum,
                        @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Notice> page = noticeService.selectPage(notice, pageNum, pageSize);
        return R.success(page);
    }

} 