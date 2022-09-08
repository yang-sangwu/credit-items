package com.credit.controller;

import com.credit.service.DownloadScore;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;


/**
 *excel表格功能编写
 */
@Api(tags = "下载（停用）")
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Resource
    private DownloadScore downloadScore;


    /**
     * 下载文件
     *
     * @return
     */
    @GetMapping({"/download"})
    public void download(){
        downloadScore.excelDownloadScore();
    }
}
