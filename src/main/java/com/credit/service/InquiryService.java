package com.credit.service;

import com.credit.pojo.Inquiry;
import com.credit.util.Response;
import java.math.BigDecimal;

public interface InquiryService {
    //增加或修改表（普通管理员审核申请表时应该填写的）（接口）
    Response insertInquiry(Inquiry inquiry);

    //增加或修改表（普通管理员审核申请表时应该填写的）（接口）
    Inquiry updateInquiry(Inquiry inquiry);

    //根据id查询表（普通管理员审核申请表时应该填写的）（接口）
    Inquiry queryInquiryByID(int inId);

//    //根据申请表类型或学分构成查询表（普通管理员审核申请表时应该填写的）
//    List<Inquiry>findInByType(String inType);
//
//    //根据认定范围查询表（普通管理员审核申请表时应该填写的）
//    List<Inquiry>findInByScope(String inScope);
//
//    //根据学分查询表（普通管理员审核申请表时应该填写的）
//    List<Inquiry>findInByScore(BigDecimal inScope);

    //通过申请表的类型、学分构成、认定范围、学分、申请人的院系等查询通过的申请表
    Response queryRequirement(String inType,String inScope,BigDecimal inScore,String appAcademic,Integer pages,Integer num,int appPass);


    //根据院系查询对应通过等级的申请表
    Response queryAppByAcademicAndPass(String appAcademic, Integer appPass, Integer pages, Integer num);

    //模糊查询用户
    Response queryUserConcat(String thing,Integer pages,Integer num);

    //根据学号修改申请表表通过状态和对用户的认定结果
    Response updateUserPassAndUserRecognize(int appPass, String userRecognize,String userRemark,int appId,BigDecimal appAcademicScore);

    Inquiry findInByAppId(Integer appID);
}
