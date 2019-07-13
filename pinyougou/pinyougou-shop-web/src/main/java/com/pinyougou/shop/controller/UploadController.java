package com.pinyougou.shop.controller;

import com.pinyougou.common.util.FastDFSClient;
import com.pinyougou.vo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/upload")
@RestController
public class UploadController {

    /**
     * 接收图片并保存图片到fastDFS并返回图片地址
     * @param file 图片文件
     * @return 操作结果
     */
    @PostMapping
    public Result upload(MultipartFile file){
        Result result = Result.fail("上传图片失败");

        try {
            //保存图片到fastDFS
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastdfs/tracker.conf");

            //上传图片文件的后缀
            String file_ext_name = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);

            String url = fastDFSClient.uploadFile(file.getBytes(), file_ext_name);

            result = Result.ok(url);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
