package com.credit.service;

import com.credit.pojo.User;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DownloadScore {
    /**
     * 导出通过审核的学分认定信息汇总统计表
     */
    void excelDownloadScore();
}
