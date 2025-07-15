package com.example.user.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.Headers;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.ResponseHeaderOverrides;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.DateUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Component
public class COSUtil {
    private COSClient cosClient;

    @Value("${cos.secretId}")
    private String secretId;

    @Value("${cos.secretKey}")
    private String secretKey;

    @Value("${cos.bucketName}")
    private String bucketName;

    @Value("${cos.baseDIR}")
    private String baseDir;

    @PostConstruct
    public void Init() {
        var cred = new BasicCOSCredentials(secretId, secretKey);
        var region = new Region("ap-shanghai");
        var clientConfig = new ClientConfig(region);
        cosClient = new COSClient(cred, clientConfig);
    }


    public String UploadFile(File file) {
        String fileName = UUID.randomUUID().toString();
        String key = baseDir + fileName;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        cosClient.putObject(putObjectRequest);
        return fileName;
    }

    public URL GetFileLink(String fileName) {
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, baseDir + fileName, HttpMethodName.GET);
        ResponseHeaderOverrides responseHeaderOverrides = new ResponseHeaderOverrides();
        String responseCacheControl = "no-cache";
        String cacheExpireStr =
                DateUtils.formatRFC822Date(new Date(System.currentTimeMillis() + 10L * 60L * 1000L));
        responseHeaderOverrides.setCacheControl(responseCacheControl);
        responseHeaderOverrides.setExpires(cacheExpireStr);
        request.setResponseHeaders(responseHeaderOverrides);
        Date expirationDate = new Date(System.currentTimeMillis() + 10L * 60L * 1000L);
        request.setExpiration(expirationDate);
        request.putCustomRequestHeader(Headers.HOST, cosClient.getClientConfig().getEndpointBuilder().buildGeneralApiEndpoint(bucketName));
        return cosClient.generatePresignedUrl(request);
    }
}