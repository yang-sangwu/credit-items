package com.credit.service;

import com.credit.pojo.Applications;
import com.credit.pojo.Messages;
import com.credit.pojo.User;
import com.credit.util.Response;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface MessagesService {
    //导入或修改组织信息
    Messages addMessages(Messages messages);

    //查询指定组织的基本信息
    Messages findMessagesByOrganize(String organize);

    //查询学分类型（通过查询的指标遍历其下的全部附属指标）
    Response queryDownStarName(String staName);

    //修改申请表通过状态+学分认证
    Response updateAppPassAndScore(Integer appPass, BigDecimal appAcademicScore, Integer appId);

    //删除已经添加过的指标信息
    Response deleteStaNameBath(List<Integer>list);

    //导出通过审核的学分认定信息汇总统计表（导出通过审核的用户信息）
    Response queryUserByPassTwo(Integer pages,Integer num);


    //通过学号获取某个学分类型
    Response queryScoreHe(String appStuID);

    //导入组织信息（升级版）
    Response addMessagesUp(Messages messages);
}
