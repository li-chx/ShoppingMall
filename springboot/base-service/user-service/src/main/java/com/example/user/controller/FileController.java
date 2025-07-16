package com.example.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.user.utils.COSUtil;
import jakarta.annotation.Resource;
import com.example.exception.RedisKeyNotFoundException;
import com.example.user.utils.COSUtil;
import com.example.user.utils.RedisCache;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@RestController
public class FileController {
    @Resource
    private COSUtil cosio;
    @Resource
    private RedisCache cache;

    @SentinelResource(value = "file_upload")
    @PostMapping("/files/upload")
    public R upload(MultipartFile multipartFile) throws IOException {
        var file = File.createTempFile("upload-", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        var fileName = cosio.UploadFile(file);
        file.delete();
        return R.success(fileName);
    }

    @SentinelResource(value = "file_download")
    @GetMapping("/files/getFileLink/{fileName}")
    public R<String> getFileLink(@PathVariable String fileName) {
        try {
            var link = cache.getOrThrow(fileName, String.class);
            return R.success(link);
        } catch (RedisKeyNotFoundException ex) {
        }
        URL url = cosio.GetFileLink(fileName);
        cache.set(fileName, url.toString(), 10L * 60, TimeUnit.SECONDS);
        return R.success(url.toString());
    }
} 