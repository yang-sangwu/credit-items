package com.credit.controller;

import com.credit.pojo.Applications;
import com.credit.pojo.Page;
import com.credit.service.ApplicationsService;
import com.credit.util.RecordUtil;
import com.credit.util.Response;
import com.credit.util.ReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

@Api(tags = "申请表")
@RestController
@Slf4j
@RequestMapping("/applications")
public class ApplicationsController {
    @Resource
    private ApplicationsService applicationsService;

    /**
     * 增加申请表
     */
    @PreAuthorize("hasAnyAuthority('1','2','0','12')")
    @ApiOperation(value = "增加申请表", notes = "获取地址", httpMethod = "POST")
    @PostMapping("/addApp")
    @ResponseBody
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "appName", value = "姓名", required = true),
                    @ApiImplicitParam(name = "appSex", value = "性别", required = true),
                    @ApiImplicitParam(name = "appStuID", value = "学号", required = true),
                    @ApiImplicitParam(name = "appMajor", value = "专业", required = true),
                    @ApiImplicitParam(name = "appClass", value = "班级", required = true),
                    @ApiImplicitParam(name = "appAcademic", value = "所属学院", required = true),
                    @ApiImplicitParam(name = "appType", value = "申请类型", required = true),
                    @ApiImplicitParam(name = "appGroup", value = "是否为集体项目(填是否，是填那两个，否的话不填，有默认值)", required = true),
                    @ApiImplicitParam(name = "appGroupCount", value = "共多少人（num）"),
                    @ApiImplicitParam(name = "appGroupGrade", value = "本人排名(num)"),
                    @ApiImplicitParam(name = "appScore", value = "申请学分(num)", required = true),
                    @ApiImplicitParam(name = "appResult", value = "学院审核结果(num)"),
                    @ApiImplicitParam(name = "appRecognize", value = "教务处认定结果"),
                    @ApiImplicitParam(name = "appContent", value = "实践内容说明", required = true),
                    @ApiImplicitParam(name = "appUserName", value = "申请学生签字"),
                    @ApiImplicitParam(name = "appUserTime", value = "学生填写年-月-日"),
                    @ApiImplicitParam(name = "appFacultyName", value = "学院负责人签字"),
                    @ApiImplicitParam(name = "appFacultyTime", value = "学院负责人年-月-日"),
                    @ApiImplicitParam(name = "appClassName", value = "相关部门负责人签字"),
                    @ApiImplicitParam(name = "appClassTime", value = "相关部门负责人年-月-日"),
                    @ApiImplicitParam(name = "appAcademicScore", value = "认定学分(num)"),
                    @ApiImplicitParam(name = "appAcademicName", value = "教务处负责人签字"),
                    @ApiImplicitParam(name = "appAcademicTime", value = "教务处负责人年-月-日"),
            }
    )
    public ReturnUtil addApp(String appName, String appSex, String appStuID, String appMajor, String appClass, String appAcademic, String appType, String appGroup, Integer appGroupCount, Integer appGroupGrade, BigDecimal appScore, BigDecimal appResult, String appRecognize, String appContent, String appUserName, String appUserTime, String appFacultyName, String appFacultyTime, String appClassName, String appClassTime, BigDecimal appAcademicScore, String appAcademicName, String appAcademicTime) {
        if (appGroupCount == null && appGroupGrade == null) {
            appGroupCount = 1;
            appGroupGrade = 1;
        }
        return applicationsService.insertApplications(appName, appSex, appStuID, appMajor, appClass, appAcademic, appType, appGroup, appGroupCount, appGroupGrade, appScore, appResult, appRecognize, appContent, appUserName, appUserTime, appFacultyName, appFacultyTime, appClassName, appClassTime, appAcademicScore, appAcademicName, appAcademicTime);
    }

    /**
     * 修改申请表
     */
    @PreAuthorize("hasAnyAuthority('1','2','0','12')")
    @ApiOperation(value = "修改申请表", notes = "获取地址", httpMethod = "PUT")
    @PutMapping("/updateApp")
    @ResponseBody
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "appId", value = "申请表id", required = true),
                    @ApiImplicitParam(name = "appName", value = "姓名"),
                    @ApiImplicitParam(name = "appSex", value = "性别"),
                    @ApiImplicitParam(name = "appStuID", value = "学号"),
                    @ApiImplicitParam(name = "appMajor", value = "专业"),
                    @ApiImplicitParam(name = "appClass", value = "班级"),
                    @ApiImplicitParam(name = "appAcademic", value = "所属学院"),
                    @ApiImplicitParam(name = "appType", value = "申请类型"),
                    @ApiImplicitParam(name = "appGroup", value = "是否为集体项目(填是否)"),
                    @ApiImplicitParam(name = "appGroupCount", value = "共多少人（num）", required = true),
                    @ApiImplicitParam(name = "appGroupGrade", value = "本人排名(num)", required = true),
                    @ApiImplicitParam(name = "appScore", value = "申请学分(num)"),
                    @ApiImplicitParam(name = "appResult", value = "学院审核结果(num)"),
                    @ApiImplicitParam(name = "appRecognize", value = "教务处认定结果"),
                    @ApiImplicitParam(name = "appContent", value = "实践内容说明"),
                    @ApiImplicitParam(name = "appUserName", value = "申请学生签字"),
                    @ApiImplicitParam(name = "appUserTime", value = "学生填写年-月-日"),
                    @ApiImplicitParam(name = "appFacultyName", value = "学院负责人签字"),
                    @ApiImplicitParam(name = "appFacultyTime", value = "学院负责人年-月-日"),
                    @ApiImplicitParam(name = "appClassName", value = "相关部门负责人签字"),
                    @ApiImplicitParam(name = "appClassTime", value = "相关部门负责人年-月-日"),
                    @ApiImplicitParam(name = "appAcademicScore", value = "认定学分(num)"),
                    @ApiImplicitParam(name = "appAcademicName", value = "教务处负责人签字"),
                    @ApiImplicitParam(name = "appAcademicTime", value = "教务处负责人年-月-日"),
            }
    )
    public Response updateApp(Integer appId, String appName, String appSex, String appStuID, String appMajor, String appClass, String appAcademic, String appType, String appGroup, Integer appGroupCount, Integer appGroupGrade, BigDecimal appScore, BigDecimal appResult, String appRecognize, String appContent, String appUserName, String appUserTime, String appFacultyName, String appFacultyTime, String appClassName, String appClassTime, BigDecimal appAcademicScore, String appAcademicName, String appAcademicTime) {
        return applicationsService.updateApplications(appId, appName, appSex, appStuID, appMajor, appClass, appAcademic, appType, appGroup, appGroupCount, appGroupGrade, appScore, appResult, appRecognize, appContent, appUserName, appUserTime, appFacultyName, appFacultyTime, appClassName, appClassTime, appAcademicScore, appAcademicName, appAcademicTime);
    }


    /**
     * 通过id删除对应申请表
     */
    @PreAuthorize("hasAnyAuthority('1','2','0','12')")
    @ApiOperation(value = "通过id删除对应申请表", notes = "获取地址", httpMethod = "DELETE")
    @DeleteMapping("/deleteAppById")
    @ResponseBody
    public Response deleteApp(int appId) {
        applicationsService.deleteApplications(appId);
        return Response.ok("删除成功！");
    }


    /**
     * 查询所有申请表
     */
    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "查询所有申请表", notes = "获取地址", httpMethod = "GET")
    @GetMapping("/findAllApp")
    @ResponseBody
    public List<Applications> findAll() {
        return applicationsService.findAllApplications();
    }

    /**
     * 通过id查询对应申请表
     */
    @PreAuthorize("hasAnyAuthority('1','2','0','12')")
    @ApiOperation(value = "通过id查询对应申请表", notes = "获取地址", httpMethod = "GET")
    @GetMapping("/findUserByID")
    @ResponseBody
    public Applications findbyId(int id) {
        return applicationsService.findApplicationsById(id);
    }

    /**
     * 动态遍历自定义审核级别的申请表
     */
    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "动态遍历自定义审核级别的申请表(提交记录)", notes = "获取地址", httpMethod = "GET")
    @GetMapping("/findUserByAppPass")
    @ResponseBody
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "appPass", value = "审核通过级别（0为默认值，1为经普通管理员审核通过，2为超级）"),
                    @ApiImplicitParam(name = "pages", value = "第几页"),
                    @ApiImplicitParam(name = "num", value = "查询数量"),
            }
    )
    public Response findAll(int appPass, int pages, int num) {
        return Response.ok(applicationsService.findUserByAppPass(appPass, pages, num));
    }

    /**
     * 动态查询审核级别的申请表的数量
     */
    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "动态查询审核级别的申请表的数量", notes = "获取地址", httpMethod = "GET")
    @GetMapping("/findAppPassCounts")
    @ResponseBody
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "appPass", value = "审核通过级别（0为默认值，1为经普通管理员审核通过，2为超级）"),
            }
    )
    public int find(int appPass) {
        return applicationsService.queryAppAppPassCounts(appPass);
    }

    @PreAuthorize("hasAnyAuthority('1','2','0','12')")
    @ApiOperation(value = "普通管理员分页遍历该学院下所有提交记录")
    @GetMapping("/getAppRecord")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appAcademic", value = "所在院系", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数据数目", required = true)
    })
    public Page<RecordUtil> getAppRecord(String appAcademic, Integer pageNo, Integer pageSize) {
        return applicationsService.getAppRecord(appAcademic, pageNo, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('1','2','0','12')")
    @ApiOperation(value = "查某用户所有提交记录")
    @GetMapping("/getByAppName")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appName", value = "用户姓名", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数据数目", required = true)
    })
    public Page<RecordUtil> getByAppName(String appName, Integer pageNo, Integer pageSize) {
        return applicationsService.getByAppName(appName, pageNo, pageSize);
    }

    @ApiOperation(value = "修改申请表通过状态")
    @PutMapping("/updateAppPass")
    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appPass", value = "1为普管通过，2为超管通过，-1为未经普管通过，-2为未经超管通过", required = true),
            @ApiImplicitParam(name = "appId", value = "id", required = true),
    })
    public Response a2(Integer appPass, Integer appId) {
        applicationsService.updateAppPass(appPass, appId);
        return Response.ok("修改成功！");
    }

    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "根据时间查询通过或未通过的申请表(提交记录)")
    @GetMapping("/queryAppByTime")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appPass", value = "1为普管通过，2为超管通过，-1为未经普管通过，-2为未经超管通过", required = true),
            @ApiImplicitParam(name = "appTime", value = "申请表提交时间", required = true),
            @ApiImplicitParam(name = "pages", value = "第几页", required = true),
            @ApiImplicitParam(name = "num", value = "查询数量", required = true),
    })
    public Response a2(String appTime, Integer appPass, Integer pages, Integer num) {
        return applicationsService.queryAppByTime(appTime, appPass, pages, num);
    }

    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "根据时间倒序遍历所有的申请表(提交记录)")
    @GetMapping("/queryAppAllTime")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pages", value = "第几页", required = true),
            @ApiImplicitParam(name = "num", value = "查询数量", required = true),
    })
    public Response a3(int pages, int num) {
        return applicationsService.queryAllAppTime(pages, num);
    }

    @PreAuthorize("hasAnyAuthority('0','2')")
    @ApiOperation(value = "普通用户查询已通过申请表(提交记录)")
    @GetMapping("/getAppByPass")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appName", value = "用户名称", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true)
    })
    public Page<RecordUtil> getAppByPass(String appName, Integer pageNo, Integer pageSize) {

        return applicationsService.getAppByPass(appName, pageNo, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('0','2')")
    @ApiOperation(value = "普通用户查询未通过申请表(提交记录)")
    @GetMapping("/getAppByNoPass")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appName", value = "用户名称", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true)
    })
    public Page<RecordUtil> getAppByNoPass(String appName, Integer pageNo, Integer pageSize) {
        return applicationsService.getAppByNoPass(appName, pageNo, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('0','2','1')")
    @ApiOperation(value = "普通用户查询待审核的申请表信息(提交记录)")
    @GetMapping("/getAppWait")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appName", value = "用户名称", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true)
    })
    public Page<RecordUtil> getAppWait(String appName, Integer pageNo, Integer pageSize) {
        return applicationsService.getAppByWait(appName, pageNo, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('1','12')")
    @ApiOperation(value = "查询某个院系所有待审核的申请表信息")
    @GetMapping("/getAppAllWait")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appAcademic", value = "学院", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true)
    })
    public Page<Applications> getAppAllWait(String appAcademic, Integer pageNo, Integer pageSize) {
        return applicationsService.getAppAllWait(appAcademic, pageNo, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('1','12')")
    @ApiOperation(value = "查询某个院系所有通过的申请表信息")
    @GetMapping("/getAppAllPass")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appAcademic", value = "学院", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true)
    })
    public Page<Applications> getAppAllPass(String appAcademic, Integer pageNo, Integer pageSize) {
        return applicationsService.getAppAllPass(appAcademic, pageNo, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('1','12')")
    @ApiOperation(value = "查询某个院系所有未通过的申请表信息")
    @GetMapping("/getAppAllNoPass")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appAcademic", value = "学院", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true)
    })
    public Page<Applications> getAppAllNoPass(String appAcademic, Integer pageNo, Integer pageSize) {
        return applicationsService.getAppAllNoPass(appAcademic, pageNo, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('0','2')")
    @ApiOperation(value = "普通用户根据提交时间来查询申请表(提交记录)")
    @GetMapping("/getAppByTime")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appName", value = "用户名", required = true),
            @ApiImplicitParam(name = "appTime", value = "提交时间", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true)
    })
    public Page<RecordUtil> getAppByTime(String appName, String appTime, Integer pageNo, Integer pageSize) throws ParseException {
        return applicationsService.getAppByTime(appName, appTime, pageNo, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('1','2','0','12')")
    @ApiOperation(value = "模糊查询申请表(名字,专业,班级,学院,通过状态,申请类型,是否为集体项目)")
    @GetMapping("/getApplicationsLike")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appAcademic", value = "学院", required = true),
            @ApiImplicitParam(name = "search", value = "查询内容", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数据条数", required = true)
    })
    public Page<Applications> getApplicationsLike(String appAcademic, String search, Integer pageNo, Integer pageSize) {
        return applicationsService.getApplicationsLike(appAcademic, search, pageNo, pageSize);
    }

    @PreAuthorize("hasAnyAuthority('0','2','1','12')")
    @ApiOperation(value = "上传附件")
    @PostMapping(value = "/upload")
    @ApiImplicitParam(name = "appId", value = "申请表id", required = true)
    public ReturnUtil upload(@RequestParam("file") MultipartFile[] file, int appId) {
        return applicationsService.uplode(file, appId);
    }

    @PreAuthorize("hasAnyAuthority('0','2','1','12')")
    @ApiOperation(value = "删除附件")
    @DeleteMapping("/deleteFile")
    @ApiImplicitParam(name = "path", value = "在服务器的存储路径", required = true)
    public ReturnUtil deleteFile(String path) {
        return applicationsService.deleteFile(path);
    }

    @PreAuthorize("hasAnyAuthority('0','2')")
    @ApiOperation(value = "下载附件")
    @GetMapping("/download")

    @ApiImplicitParam(name = "path", value = "在服务器的存储路径", required = true)
    public ReturnUtil download(String path, HttpServletResponse response) {
        return applicationsService.download(path, response);
    }



}
