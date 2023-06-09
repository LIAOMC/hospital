package com.lwz.hospitalsystem.controller;

import com.lwz.hospitalsystem.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${hospital.path}")
    private String basePath;

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
//        log.info(file.toString());
        //获得原始文件名
        String originalFilename = file.getOriginalFilename();
        //获得文件后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //使用UUID重新生成文件名，防止文件名重复出现覆盖现象
        String fileName = UUID.randomUUID().toString() + suffix;
        File dir=new File(basePath);
        if(!dir.exists()){
            //判断目录是否不存在，不存在就创建一个
            dir.mkdirs();
        }
        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return R.success(fileName);
    }

    /**
     * 文件下载
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream f=new FileInputStream(new File(basePath+name));

            //输出流，通过输出流将文件写回到浏览器，在浏览器展示图片
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");
            int len;
            byte[] bytes=new byte[1024];
            while ((len=f.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            f.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
