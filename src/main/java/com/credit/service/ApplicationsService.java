package com.credit.service;

import com.credit.pojo.Applications;
import com.credit.pojo.Page;
import com.credit.pojo.User;
import com.credit.util.RecordUtil;
import com.credit.util.Response;
import com.credit.util.ReturnUtil;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

public interface ApplicationsService {
    //增加申请表
    ReturnUtil insertApplications(String appName, String appSex, String appStuID, String appMajor, String appClass, String appAcademic, String appType, String appGroup, int appGroupCount, int appGroupGrade, BigDecimal appScore, BigDecimal appResult, String appRecognize, String appContent, String appUserName, String appUserTime, String appFacultyName, String appFacultyTime, String appClassName, String appClassTime, BigDecimal appAcademicScore, String appAcademicName, String appAcademicTime);

    //删除申请表
    void deleteApplications(int appId);

    //修改申请表
    Response updateApplications(int appId, String appName, String appSex, String appStuID, String appMajor, String appClass, String appAcademic, String appType, String appGroup, Integer appGroupCount, Integer appGroupGrade, BigDecimal appScore, BigDecimal appResult, String appRecognize, String appContent, String appUserName, String appUserTime, String appFacultyName, String appFacultyTime, String appClassName, String appClassTime, BigDecimal appAcademicScore, String appAcademicName, String appAcademicTime);

    //查询所有申请表
    List<Applications> findAllApplications();

    //通过id查询对应申请表
    Applications findApplicationsById(int appId);

    //动态遍历自定义审核级别的申请表
    Response findUserByAppPass(int appPass, int pages, int num);

    //动态查询审核级别的申请表的数量
    int queryAppAppPassCounts(int appPass);

    Page<RecordUtil> getAppRecord(String appAcademic, Integer pageNo, Integer pageSize);

    Page<RecordUtil> getByAppName(String appName, Integer pageNo, Integer pageSize);

    //修改申请表通过状态
    void updateAppPass(int appPass, int appId);

    //根据时间查询通过或未通过的申请表
    Response queryAppByTime(String appTime, Integer appPass, Integer pages, Integer num);


    //根据时间倒序遍历所有的申请表
    Response queryAllAppTime(int pages, int num);

    Page<RecordUtil> getAppByPass(String appName, Integer pageNo, Integer pageSize);

    Page<RecordUtil> getAppByWait(String appName, Integer pageNo, Integer pageSize);

    Page<Applications> getAppAllWait(String appAcademic, Integer pageNo, Integer pageSize);

    Page<Applications> getAppAllPass(String appAcademic, Integer pageNo, Integer pageSize);

    Page<Applications> getAppAllNoPass(String appAcademic, Integer pageNo, Integer pageSize);

    Page<RecordUtil> getAppByNoPass(String appName, Integer pageNo, Integer pageSize);

    Page<RecordUtil> getAppByTime(String appName, String appTime, Integer pageNo, Integer pageSize);

    Page<Applications> getApplicationsLike(String appAcademic, String search, Integer pageNo, Integer pageSize);

    ReturnUtil deleteFile(String path);

    ReturnUtil uplode(MultipartFile[] file, Integer appId);

    ReturnUtil download(String path, HttpServletResponse response);
}
