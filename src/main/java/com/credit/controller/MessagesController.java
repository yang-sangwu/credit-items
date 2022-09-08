package com.credit.controller;

import com.credit.pojo.Messages;
import com.credit.service.MessagesService;
import com.credit.util.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Api(tags = "组织信息")
@RestController
@RequestMapping("/messages")
public class MessagesController {
    @Resource
    private MessagesService messagesService;

    /**
     * 导入或修改组织信息
     */
    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "导入或修改组织信息", notes = "获取地址", httpMethod = "POST")
    @PostMapping("/addOrUpdateMess")
    @ResponseBody
    public Messages de(@RequestBody Messages messages) {
        return messagesService.addMessages(messages);
    }

    /**
     * 查询指定组织的基本信息
     */
    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "查询指定组织的基本信息", notes = "获取地址", httpMethod = "GET")
    @GetMapping("/findMessagesOr")
    @ResponseBody
    public Messages d(String organize) {
        return messagesService.findMessagesByOrganize(organize);
    }

    /**
     * 查询学分类型（通过查询的指标遍历其下的全部附属指标）
     */
    @PreAuthorize("hasAnyAuthority('2','1','12','0')")
    @ApiOperation(value = "查询学分类型（通过查询的指标遍历其下的全部附属指标）")
    @GetMapping("/queryDownStarName")
    @ResponseBody
    public Response queryDownStarName(String staName) {
        return messagesService.queryDownStarName(staName);
    }

    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "修改申请表通过状态+认证学分")
    @PutMapping("/updateAppPassAndAcademic")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appPass", value = "1为普管通过，2为超管通过，-1为未经普管通过，-2为未经超管通过", required = true),
            @ApiImplicitParam(name = "appId", value = "id", required = true),
            @ApiImplicitParam(name = "appAcademicScore", value = "认证学分", required = true),
    })
    public Response a2(Integer appPass, BigDecimal appAcademicScore, Integer appId) {
        return messagesService.updateAppPassAndScore(appPass, appAcademicScore, appId);
    }

    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "删除已经添加过的指标信息")
    @DeleteMapping("/deleteStaNameBath")
    public Response a3(@RequestParam("List<Integer> list") List<Integer> list) {
        return messagesService.deleteStaNameBath(list);
    }

    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "导出通过审核的学分认定信息汇总统计表（导出通过审核的用户信息）")
    @GetMapping("/queryUserByPassTwo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pages", value = "第几页"),
            @ApiImplicitParam(name = "num", value = "查询条数"),
    })
    public Response a2(Integer pages, Integer num) {
        return messagesService.queryUserByPassTwo(pages, num);
    }

    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "通过学号获取此用户的各学分类型学分")
    @GetMapping("/queryScoreHe")
    @ApiImplicitParam(name = "appStuID", value = "学号", required = true)
    public Response queryScoreHe(String appStuID) {
        return messagesService.queryScoreHe(appStuID);
    }

    /**
     * 导入组织信息（升级版）
     */
    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "导入组织信息（升级版）", notes = "获取地址", httpMethod = "POST")
    @PostMapping("/addOrUpdateMessUp")
    @ResponseBody
    public Response addMessagesUp(@RequestBody Messages messages) {
        return messagesService.addMessagesUp(messages);
    }
}
