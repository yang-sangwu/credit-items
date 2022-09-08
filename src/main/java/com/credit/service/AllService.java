package com.credit.service;

import com.credit.pojo.*;
import com.credit.util.Response;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AllService {

    //根据学号查询用户
    User findUserByCode(String userCode);

    //模糊查询申请表
    Response queryAppConcat(String thing, int pages, int num,int appPass);

    /**
     * 申请表的类型=学分构成、申请表的类型(inType)+学分(inScore)、申请表的类型(inType)+申请人的院系(appAcademic),
     * 申请人的院系(appAcademic)+学分(inScore),申请人的院系(appAcademic)+认定范围(inScope)等查询申请表
     */
    Response queryAppByRe(String inType, String inScope, BigDecimal inScore, String appAcademic,int pages,int num,int appPass);

    /**
     * 根据申请表id查询此申请表下的附件
     * */
    List<Paths> queryPathByAppId(int appId);

    /**
     * 根据附件id删除此附件
     */
    Response deletePathById(int id);

    /**
     * 根据申请表id删除附件
     */
    Response deletePathByAppId(int appId);

    /**
     * 根据指标id修改指标
     */
    Response updateStarById(String staName, Integer staId, BigDecimal staCredit, String staRemark);

    //导出通过审核的学分认定信息汇总统计表（导出通过审核的用户信息）
    Response queryUserByPassTwoConcat(Integer pages,Integer num,String thing);

    /**
     * 分页查询指定组织的基本信息
     */
    Response findMessagesPages(int pages, int num);

    /**
     * 根据id修改申请表类型
     **/
    Response updateAppTypeById(String appType,int appId);

    //通过学号获取最高学分类型
    Response queryScoreMaxByStuId(String appStuID);

    //根据学分类型查询指标中最高分
    Response queryStaMaxCredit(String type);

    //根据学号分页查询驳回信息
    Map findRejectPages(String code, int pages, int num);

    //增加或修改驳回信息（）
    Reject addReject(Integer appId,String code,String reason);

    //根据学号查询某个用户下某个权限的申请表
    Map queryAppByCodeAndPass(String code, Integer appPass, int pages, int num);

    //根据ID查询指标
    Standard queryStaById(Integer id);

    //根据申请表id删除驳回信息
    Response deleteReject(int appId);

    //动态遍历自定义审核级别的申请表
    Response findUserByAppPass(int appPass, int pages, int num);

    //根据时间查询通过或未通过的申请表
    Response queryAppByTime(String appTime, Integer appPass, Integer pages, Integer num);

    //批量删除申请表
    Response deleteAppByList(List<Integer>list);

    //分页模糊查询组织信息
    Response queryMessagesByOrganizeMo(String thing,int pages,int num);
}
