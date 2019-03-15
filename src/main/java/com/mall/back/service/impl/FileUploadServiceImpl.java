package com.mall.back.service.impl;

import com.google.common.collect.Lists;
import com.mall.back.service.FileUploadService;
import com.mall.utils.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("fileUploadService")
public class FileUploadServiceImpl implements FileUploadService {
    private static final Logger loger= LoggerFactory.getLogger(FileUploadServiceImpl.class);
    @Override
    public String uploadFile(String path, MultipartFile file) {
        //获取图片的原始名称
        String originalFilename = file.getOriginalFilename();
        //获取文件扩展名
        String ExtendName=originalFilename.substring(originalFilename.lastIndexOf(".")+1);
        String fileName= UUID.randomUUID().toString().replaceAll("-","")+ExtendName;
        loger.info("开始上传文件,文件原始名{},文件扩展名{},新文件名{}",originalFilename,ExtendName,fileName);
        File fileDir=new File(path);
        if (!fileDir.exists()){
            fileDir.setWritable(true);//设置允许写入的访问权限
            fileDir.mkdirs();
        }
        File fileTarget=new File(path,fileName);
        try {
            file.transferTo(fileTarget);//文件上传成功
            FtpUtil.uploadFile(Lists.<File>newArrayList(fileTarget));//将文件上传到ftp服务器
            fileTarget.delete();//删除本地的文件
        } catch (IOException e) {
            loger.error("上传文件异常");
            e.printStackTrace();
        }
        return fileTarget.getName();
    }
}
