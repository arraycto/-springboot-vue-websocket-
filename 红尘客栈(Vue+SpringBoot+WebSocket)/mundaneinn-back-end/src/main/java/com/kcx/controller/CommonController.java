package com.kcx.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
//公共类，在本项目没有用到，可忽略
@RestController
@RequestMapping("common")
public class CommonController {
    //文件上传
    @RequestMapping("/uploadfile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        // 文件名
        String fileName = file.getOriginalFilename();
        // 在file文件夹中创建名为fileName的文件
        assert fileName != null;
        int split = fileName.lastIndexOf(".");
        // 文件后缀，用于判断上传的文件是否是合法的
        String suffix = fileName.substring(split+1,fileName.length());
        //判断文件类型，因为我这边是图片，所以只设置三种合法格式
        String url = "";
        if("jpg".equals(suffix) || "jpeg".equals(suffix) || "png".equals(suffix)) {
            // 正确的类型，保存文件
            try {
                /*File path = new File(ResourceUtils.getURL("classpath:").getPath());
                File upload = new File(path.getAbsolutePath(), "/upload");*/
                File upload = new File("C:/upload/");
                if(!upload.exists()) {
                    upload.mkdirs();
                }
                String newName = System.currentTimeMillis() + "."+suffix;
                File savedFile = new File(upload+ "/" + newName);
                file.transferTo(savedFile);
                url = savedFile.getAbsolutePath();
                System.out.println("图片上传完毕，存储地址为："+ url);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }else {
            //错误的类型，返回错误提示

        }
        return  url;
    }

}
