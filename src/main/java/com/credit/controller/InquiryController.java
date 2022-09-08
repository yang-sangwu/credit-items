package com.credit.controller;

import com.credit.pojo.Inquiry;
import com.credit.service.InquiryService;
import com.credit.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.math.BigDecimal;

@Api(tags = "通过申请表的类型、学分构成、认定范围、学分、申请人的院系等查询通过的申请表")
@RestController
@RequestMapping("/inquiry")
public class InquiryController {
    @Resource
    private InquiryService inquiryService;

    /**
     * 增加表（普通管理员审核申请表时应该填写的）（接口）
     */
    @PreAuthorize("hasAnyAuthority('1','12')")
    @ApiOperation(value = "增加（普通管理员审核申请表时应该填写的）（接口）")
    @PostMapping("/insertOrUpdateIn")
    @ResponseBody
    public Response a1(Inquiry inquiry) {
        return inquiryService.insertInquiry(inquiry);
    }

    /**
     * 修改表（普通管理员审核申请表时应该填写的）（接口）
     */
    @PreAuthorize("hasAnyAuthority('1','12')")
    @ApiOperation(value = "修改表（普通管理员审核申请表时应该填写的）（接口）")
    @PutMapping("/UpdateIn")
    @ResponseBody
    public Inquiry a2(Inquiry inquiry) {
        return inquiryService.updateInquiry(inquiry);
    }

    /**
     * 根据id查询表（普通管理员审核申请表时应该填写的）（接口）
     */
    @PreAuthorize("hasAnyAuthority('1','12')")
    @ApiOperation(value = "根据id查询表（普通管理员审核申请表时应该填写的）（接口）")
    @GetMapping("/findInById")
    @ResponseBody
    public Inquiry a2(int inId) {
        return inquiryService.queryInquiryByID(inId);
    }

    /**
     * 通过申请表的类型、学分构成、认定范围、学分、申请人的院系等查询通过的申请表
     */
    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "通过申请表的类型、学分构成、认定范围、学分、申请人的院系等查询通过的申请表")
    @GetMapping("/queryRequirement")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "inType", value = "申请表的类型"),
            @ApiImplicitParam(name = "inScope", value = "认定范围"),
            @ApiImplicitParam(name = "inScore", value = "学分"),
            @ApiImplicitParam(name = "appAcademic", value = "申请人的院系"),
            @ApiImplicitParam(name = "pages", value = "第几页", required = true),
            @ApiImplicitParam(name = "num", value = "查询数量", required = true),
            @ApiImplicitParam(name = "appPass", value = "申请表通过权限等级", required = true),
    })
    public Response a3(String inType, String inScope, BigDecimal inScore, String appAcademic,Integer pages,Integer num,int appPass) {
        if(pages<=0||num<=0){
            return Response.error("请输入合理的页数！");
        }else{
            return inquiryService.queryRequirement(inType,inScope,inScore,appAcademic,pages,num,appPass);
        }
    }

    /**
     * 根据院系查询对应等级的申请表
     */
    @PreAuthorize("hasAnyAuthority('1','2','0','12')")
    @ApiOperation(value = "根据院系查询对应等级的申请表", notes = "获取地址", httpMethod = "GET")
    @GetMapping("/queryAppByAcademic")
    @ResponseBody
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "appAcademic", value = "院系"),
                    @ApiImplicitParam(name = "appPass", value = "状态"),
                    @ApiImplicitParam(name = "pages", value = "第几页"),
                    @ApiImplicitParam(name = "num", value = "查询数量"),
            }
    )
    public Response a3(String appAcademic, Integer appPass, Integer pages, Integer num) {
      //  System.out.println("controller");
        return inquiryService.queryAppByAcademicAndPass(appAcademic,appPass,pages,num);
    }

    /**
     * 模糊查询用户
     */
    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "模糊查询用户")
    @GetMapping("/queryUserConcat")
    @ResponseBody
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "thing", value = "模糊查询内容", required = true),
                    @ApiImplicitParam(name = "pages", value = "第几页", required = true),
                    @ApiImplicitParam(name = "num", value = "查询数量", required = true),
            }
    )
    public Response queryUserConcat(String thing,Integer pages,Integer num) {
        return inquiryService.queryUserConcat(thing,pages,num);
    }

    /**
     * 根据学号修改申请表表通过状态和对用户的认定结果
     */
    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "修改申请表表通过状态和对用户的认定结果")
    @PutMapping("/updateUserPassAndUserRecognize")
    @ResponseBody
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "appPass", value = "权限等级（申请表）", required = true),
                    @ApiImplicitParam(name = "appRecognize", value = "教务处认定结果（用户表）", required = true),
                   // @ApiImplicitParam(name = "userCode", value = "学号", required = true),
                    @ApiImplicitParam(name = "appAcademicName", value = "替换备注（用户表）（没用的）", required = true),
                    @ApiImplicitParam(name = "appId", value = "要修改权限等级的申请表id", required = true),
                    @ApiImplicitParam(name = "appAcademicScore", value = "认定学分（申请表）", required = true),
            }
    )
    public Response updateUserPassAndUserRecognize(int appPass, String appRecognize,String appAcademicName,int appId,BigDecimal appAcademicScore) {
        return inquiryService.updateUserPassAndUserRecognize(appPass,appRecognize,appAcademicName,appId,appAcademicScore);
    }

    /**
     * 根据申请表id查询inquery
     */
    @PreAuthorize("hasAnyAuthority('1','2','0','12')")
    @ApiOperation(value = "根据申请表id查询inquiry", notes = "获取地址", httpMethod = "GET")
    @GetMapping("/queryInByAppId")
    @ResponseBody
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "appId", value = "申请表id"),
            }
    )
    public Inquiry a3(Integer appId) {
        return inquiryService.findInByAppId(appId);
    }
}
