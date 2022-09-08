package com.credit.controller;

import com.credit.service.StandardService;
import com.credit.util.ReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@Api(tags = "学分指标管理")
@PreAuthorize("hasAnyAuthority('2','0','1','12')")
public class StandardController {

    @Autowired
    private StandardService standardService;

    @ApiOperation(value = "单条添加某级指标")
    @PostMapping("/addOne")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "staName", value = "指标信息", required = true),
            @ApiImplicitParam(name = "staGrade", value = "指标等级,要添加第几级就填几", required = true),
            @ApiImplicitParam(name = "staFatherid", value = "父级id,required = false"),
            @ApiImplicitParam(name = "staCredit", value = "学分", required = false),
            @ApiImplicitParam(name = "staRemark", value = "备注", required = false)
    })

    public ReturnUtil addOne(String staName, Integer staGrade, Integer staFatherid, BigDecimal staCredit, String staRemark) {
        return standardService.addOne(staName, staGrade, staFatherid, staCredit, staRemark);
    }

    @ApiOperation(value = "查询所有一级指标")
    @GetMapping("/getAllFirst")
    public ReturnUtil getAllFirst() {
        return standardService.getAllFirst();
    }

    @ApiOperation(value = "修改一级指标信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "staId", value = "id", required = true),
            @ApiImplicitParam(name = "staName", value = "指标信息", required = true)
    })
    @PutMapping("/updateFirst")
    public ReturnUtil updateFirst(Integer staId, String staName) {
        return standardService.updateFirst(staId, staName);
    }

    @ApiOperation(value = "删除一级指标")
    @ApiImplicitParam(name = "staId", value = "指标id", required = true)
    @DeleteMapping("/deleteFirst")
    public ReturnUtil deleteFirst(Integer staId) {
        return standardService.deleteFirst(staId);
    }

    @ApiOperation(value = "批量添加二级指标")
    @PostMapping("/addSecondMore")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "staName", value = "指标信息", required = true),
            @ApiImplicitParam(name = "staGrade", value = "指标等级", required = true),
            @ApiImplicitParam(name = "staFatherid", value = "父级id", required = true)
    })
    public ReturnUtil addSecondMore(String staName, Integer staGrade, Integer staFatherid) {
        return standardService.addSecondMore(staName, staGrade, staFatherid);
    }


    @ApiOperation(value = "批量添加三级指标")
    @PostMapping("/addThirdMore")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "staName", value = "指标信息", required = true),
            @ApiImplicitParam(name = "staGrade", value = "指标等级", required = true),
            @ApiImplicitParam(name = "staFatherid", value = "父级id", required = true),
            @ApiImplicitParam(name = "staCredit", value = "学分", required = true),
            @ApiImplicitParam(name = "staRemark", value = "备注", required = false)
    })
    ReturnUtil addThirdMore(String staName, Integer staGrade, Integer staFatherid, BigDecimal staCredit, String staRemark) {
        return standardService.addThirdMore(staName, staGrade, staFatherid, staCredit, staRemark);
    }

//    @ApiOperation(value = "查询一级指标（包括一级指标下的二级以及二级下的三级）")
//    @GetMapping("/findStandardOne")
//    public ReturnUtil findStandardOne(String staName){
//        return standardService.findStandardOne(staName);
//    }
}
