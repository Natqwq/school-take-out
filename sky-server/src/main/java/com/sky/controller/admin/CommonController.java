package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传:{}",file);
        try {
            String fileName = file.getOriginalFilename();
            // 获取文件后缀
            String extension = fileName.substring(fileName.lastIndexOf("."));
            // UUID构造新文件名称
            String ObjectName =UUID.randomUUID().toString() + extension;

            // 文件请求路径
            String filePath = aliOssUtil.upload(file.getBytes(), ObjectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败:{}",e);

        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
