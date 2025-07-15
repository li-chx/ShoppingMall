package com.example.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@RestController
public class FileController {
    @Resource
    private COSUtil cosio;

    @SentinelResource(value = "file_upload")
    @PostMapping("/files/upload")
    public R upload(MultipartFile multipartFile) throws IOException {

        var file = File.createTempFile("upload-", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        var fileName = cosio.UploadFile(file);
        file.delete();
        // var fileURL = cosio.GetFileLink(fileName);
        return R.success(fileName);
    }
    @SentinelResource(value = "file_download")
    @GetMapping("/files/getFileLink/{fileName}")
    public R<String> getFileLink(@PathVariable String fileName) {
        URL url = cosio.GetFileLink(fileName);
        return R.success(url.toString());
    }
} 