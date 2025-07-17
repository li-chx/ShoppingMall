package com.example.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.common.R;
import com.example.user.utils.COSUtil;
import jakarta.annotation.Resource;
import com.example.exception.RedisKeyNotFoundException;
import com.example.user.utils.RedisCache;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "上传文件", description = "上传文件到云存储并返回文件名")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "上传成功",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "file_upload")
    @PostMapping("/files/upload")
    public R upload(@Parameter(description = "上传的文件", required = true, content = @Content(mediaType = "multipart/form-data"))
                    @RequestParam MultipartFile multipartFile) throws IOException {
        var file = File.createTempFile("upload-", multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        var fileName = cosio.UploadFile(file);
        file.delete();
        return R.success(fileName);
    }

    @Operation(summary = "获取文件链接", description = "根据文件名获取文件的下载链接")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "获取成功",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "文件未找到"),
            @ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @SentinelResource(value = "file_download")
    @GetMapping("/files/getFileLink/{fileName}")
    public R<String> getFileLink(@Parameter(description = "文件名", required = true, in = ParameterIn.PATH)
                                 @PathVariable String fileName) {
        try {
            var link = cache.getOrThrow(fileName, String.class);
            return R.success(link);
        } catch (RedisKeyNotFoundException ex) {
            URL url = cosio.GetFileLink(fileName);
            cache.set(fileName, url.toString(), 10L * 60, TimeUnit.SECONDS);
            return R.success(url.toString());
        }
    }
}