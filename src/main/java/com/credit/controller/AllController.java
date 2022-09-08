package com.credit.controller;

import com.credit.pojo.Paths;
import com.credit.pojo.Reject;
import com.credit.pojo.Standard;
import com.credit.pojo.User;
import com.credit.service.AllService;
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
import java.util.Map;

@Api(tags = "All")
@RestController
@RequestMapping("/all")
public class AllController {

    @Resource
    private AllService allService;

    /**
     * 根据学号查询用户
     */
    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "根据学号查询用户")
    @GetMapping("/findUserByCode")
    @ResponseBody
    @ApiImplicitParam(name = "userCode", value = "学号", required = true)
    public User findUserByCode(String userCode) {
        return allService.findUserByCode(userCode);
    }

    /**
     * 模糊查询申请表
     */
    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "模糊查询申请表")
    @GetMapping("/queryAppConcat")
    @ResponseBody
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "thing", value = "模糊查询内容", required = true),
                    @ApiImplicitParam(name = "pages", value = "第几页", required = true),
                    @ApiImplicitParam(name = "num", value = "查询数量", required = true),
            }
    )
    public Response queryAppConcat(String thing, Integer pages, Integer num,int appPass) {
        return allService.queryAppConcat(thing,pages,num,appPass);
    }

    /**
     * 申请表的类型=学分构成、申请表的类型(inType)+学分(inScore)、申请表的类型(inType)+申请人的院系(appAcademic),
     * 申请人的院系(appAcademic)+学分(inScore),申请人的院系(appAcademic)+认定范围(inScope)等查询申请表
     */
    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "申请表的类型(inType)+学分(inScore)，申请表的类型(inType)+申请人的院系(appAcademic),申请人的院系(appAcademic)+学分(inScore),申请人的院系(appAcademic)+认定范围(inScope)等查询申请表，申请表的类型=学分构成")
    @GetMapping("/queryAppByRe")
    @ResponseBody
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "inType", value = "申请表的类型(inType)"),
                    @ApiImplicitParam(name = "inScope", value = "认定范围(inScope)"),
                    @ApiImplicitParam(name = "inScore", value = "认定学分"),
                    @ApiImplicitParam(name = "appAcademic", value = "申请人的院系(appAcademic)"),
                    @ApiImplicitParam(name = "pages", value = "第几页", required = true),
                    @ApiImplicitParam(name = "num", value = "查询数量", required = true),
                    @ApiImplicitParam(name = "appPass", value = "申请表通过等级（1或2）", required = true),
            }
    )
    public Response a3(String inType, String inScope, BigDecimal inScore, String appAcademic,int pages,int num,int appPass) {
        if(pages<=0||num<=0){
            return Response.error("请输入合理的页数！");
        }else{
            return allService.queryAppByRe(inType,inScope,inScore,appAcademic,pages,num,appPass);
        }
    }

    /**
     * 根据申请表id查询此申请表下的附件
     * */
    @PreAuthorize("hasAnyAuthority('1','2','0','12')")
    @ApiOperation(value = "根据申请表id查询此申请表下的附件")
    @GetMapping("/queryPathByAppId")
    @ResponseBody
    @ApiImplicitParam(name = "appId", value = "申请表id", required = true)
    public List<Paths> queryPathByAppId(int appId) {
        return allService.queryPathByAppId(appId);
    }

    /**
     * 根据附件id删除此附件
     */
//    @ApiOperation(value = "根据附件id删除此附件")
//    @DeleteMapping("/deletePathById")
//    @ResponseBody
//    @ApiImplicitParam(name = "id", value = "id", required = true)
//    public Response deletePathById(int id) {
//        return allService.deletePathById(id);
//    }

    /**
     * 根据申请表id删除附件
     */
//    @ApiOperation(value = "根据申请表id删除附件")
//    @DeleteMapping("/deletePathByAppId")
//    @ResponseBody
//    @ApiImplicitParam(name = "appId", value = "申请表id", required = true)
//    public Response deletePathByAppId(int appId) {
//        return allService.deletePathByAppId(appId);
//    }

    /**
     * 根据指标id修改指标
     */
    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "根据指标id修改指标")
    @PutMapping("/updateStarById")
    @ResponseBody
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "staId", value = "id", required = true),
                    @ApiImplicitParam(name = "staName", value = "学分构成", required = true),
                    @ApiImplicitParam(name = "staCredit", value = "学分", required = true),
                    @ApiImplicitParam(name = "staRemark", value = "备注"),
            }
    )
    public Response updateStarById(String staName, Integer staId, BigDecimal staCredit, String staRemark) {
        return allService.updateStarById(staName,staId,staCredit,staRemark);
    }

    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "导出通过审核的学分认定信息汇总统计表（导出通过审核的用户信息）模糊查询")
    @GetMapping("/queryUserByPassTwo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pages", value = "第几页", required = true),
            @ApiImplicitParam(name = "num", value = "查询条数", required = true),
            @ApiImplicitParam(name = "thing", value = "查询内容", required = true),
    })
    public Response a2(Integer pages, Integer num,String thing) {
        return allService.queryUserByPassTwoConcat(pages, num,thing);
    }

    /**
     * 分页查询指定组织的基本信息
     */
    @PreAuthorize("hasAnyAuthority('2','1','12')")
    @ApiOperation(value = "分页查询指定组织的基本信息")
    @GetMapping("/findMessagesPages")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pages", value = "第几页", required = true),
            @ApiImplicitParam(name = "num", value = "查询条数", required = true),
    })
    public Response findMessagesPages(int pages, int num) {
        return allService.findMessagesPages(pages,num);
    }

    /**
     * 根据id修改申请表类型
     **/
    @ApiOperation(value = "根据id修改申请表类型")
    @PutMapping("/updateAppTypeById")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appType", value = "申请表类型", required = true),
            @ApiImplicitParam(name = "appId", value = "申请表id", required = true),
    })
    public Response updateAppTypeById(String appType,int appId) {
        return allService.updateAppTypeById(appType,appId);
    }

    /**
     * 通过学号获取最高学分类型
     */
    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "通过学号获取最高学分类型")
    @GetMapping("/queryScoreMaxByStuId")
    @ApiImplicitParam(name = "appStuID", value = "学号", required = true)
    public Response queryScoreHe(String appStuID) {
        return allService.queryScoreMaxByStuId(appStuID);
    }


    /**
     * 根据学分类型查询指标中最高分
     */
    @PreAuthorize("hasAnyAuthority('2','0','1','12')")
    @ApiOperation(value = "根据学分类型查询指标中最高分（方方）")
    @GetMapping("/queryStaMaxCredit")
    @ApiImplicitParam(name = "type", value = "学分类型", required = true)
    public Response queryStaMaxCredit(String type) {
        return allService.queryStaMaxCredit(type);
    }

    /**
     * 根据学号分页查询驳回信息
     **/
    @ApiOperation(value = "根据学号分页查询驳回信息")
    @GetMapping("/findRejectPages")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "学号", required = true),
            @ApiImplicitParam(name = "pages", value = "第几页", required = true),
            @ApiImplicitParam(name = "num", value = "查询数量", required = true),
    })
    public Map findRejectPages(String code, int pages, int num) {
        return allService.findRejectPages(code, pages, num);
    }

    /**
     * 增加或修改驳回信息（）
     **/
    @ApiOperation(value = "增加或修改驳回信息（）")
    @GetMapping("/addReject")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "申请表id", required = true),
            @ApiImplicitParam(name = "code", value = "学号", required = true),
            @ApiImplicitParam(name = "reason", value = "驳回理由", required = true),
    })
    public Reject addReject(int appId,String code,String reason) {
        return allService.addReject(appId,code,reason);
    }

    /**
     * 根据学号查询某个用户下某个权限的申请表
     **/
    @ApiOperation(value = "根据学号查询某个用户下某个权限的申请表")
    @GetMapping("/queryAppByCodeAndPass")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "学号", required = true),
            @ApiImplicitParam(name = "appPass", value = "申请表权限", required = true),
            @ApiImplicitParam(name = "pages", value = "第几页", required = true),
            @ApiImplicitParam(name = "num", value = "查询数量", required = true),
    })
    public Map queryAppByCodeAndPass(String code, Integer appPass, int pages, int num) {
        return allService.queryAppByCodeAndPass(code,appPass,pages,num);
    }

    /**
     * 根据ID查询指标
     **/
    @ApiOperation(value = "根据ID查询指标")
    @GetMapping("/queryStaById")
    @ApiImplicitParam(name = "id", value = "id", required = true)
    public Standard queryStaById(Integer id) {
        return allService.queryStaById(id);
    }

    /**
     * 根据申请表id删除驳回信息
     **/
    @ApiOperation(value = "根据申请表id删除驳回信息")
    @DeleteMapping("/deleteReject")
    @ApiImplicitParam(name = "appId", value = "申请表id", required = true)
    public Response deleteReject(int appId) {
        return allService.deleteReject(appId);
    }

    /**
     * 动态遍历自定义审核级别的申请表
     */
    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "动态遍历自定义审核级别的申请表", notes = "获取地址", httpMethod = "GET")
    @GetMapping("/findUserByAppPass")
    @ResponseBody
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "appPass", value = "审核通过级别（0为默认值，1为经普通管理员审核通过，2为超级）", required = true),
                    @ApiImplicitParam(name = "pages", value = "第几页", required = true),
                    @ApiImplicitParam(name = "num", value = "查询数量", required = true),
            }
    )
    public Response findAll(int appPass, int pages, int num) {
        return Response.ok(allService.findUserByAppPass(appPass, pages, num));
    }

    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "根据时间查询通过或未通过的申请表")
    @GetMapping("/queryAppByTime")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appPass", value = "1为普管通过，2为超管通过，-1为未经普管通过，-2为未经超管通过", required = true),
            @ApiImplicitParam(name = "appTime", value = "申请表提交时间", required = true),
            @ApiImplicitParam(name = "pages", value = "第几页", required = true),
            @ApiImplicitParam(name = "num", value = "查询数量", required = true),
    })
    public Response a2(String appTime, Integer appPass, Integer pages, Integer num) {
        return allService.queryAppByTime(appTime, appPass, pages, num);
    }

    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "批量删除申请表")
    @DeleteMapping("/deleteAppByList")
    public Response a3(@RequestParam("List<Integer> list") List<Integer> list) {
        return allService.deleteAppByList(list);
    }

    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "分页模糊查询组织信息")
    @GetMapping("/queryMessagesByOrganizeMo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "thing", value = "模糊查询内容", required = true),
            @ApiImplicitParam(name = "pages", value = "第几页", required = true),
            @ApiImplicitParam(name = "num", value = "查询数量", required = true),
    })
    public Response queryMessagesByOrganizeMo(String thing,int pages,int num) {
        return allService.queryMessagesByOrganizeMo(thing,pages,num);
    }
}
