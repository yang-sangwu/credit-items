package com.credit.dao;

import com.credit.pojo.Applications;
import com.credit.util.RecordUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RecordDao {
    /**
     * 查某用户的所有提交记录
     *
     * @param appName
     * @return
     */

    List<RecordUtil> getByAppName(@Param("appName") String appName, @Param("index") Integer index, @Param("pageSize") Integer pageSize);

    Integer countByAppName(@Param("appName") String appName);

    List<RecordUtil> getAppRecord(@Param("appAcademic") String appAcademic, @Param("index") Integer index, @Param("pageSize") Integer pageSize);

    Integer countAppRecord(@Param("appAcademic") String appAcademic);

    List<RecordUtil> getLikeAppRecord(@Param("appAcademic") String appAcademic, @Param("search") String search, @Param("index") Integer index, @Param("pageSize") Integer pageSize);

    Integer countLikeAppRecord(@Param("appAcademic") String appAcademic, @Param("search") String search);

    /**
     * 普通用户查询已通过的申请表信息
     *
     * @param appName
     * @param index
     * @param pageSize
     * @return
     */
    List<RecordUtil> getAppByPass(@Param("appName") String appName, @Param("index") Integer index, @Param("pageSize") Integer pageSize);

    Integer countAppByPass(@Param("appName") String appName);

    /**
     * 普通用户查询待审核的申请表信息
     *
     * @param appName
     * @param index
     * @param pageSize
     * @return
     */
    List<RecordUtil> getAppByWait(@Param("appName") String appName, @Param("index") Integer index, @Param("pageSize") Integer pageSize);

    Integer countAppByWait(@Param("appName") String appName);

    /**
     * 普通用户查询没通过的申请表信息
     *
     * @param appName
     * @param index
     * @param pageSize
     * @return
     */
    List<RecordUtil> getAppByNoPass(@Param("appName") String appName, @Param("index") Integer index, @Param("pageSize") Integer pageSize);

    Integer countAppByNoPass(@Param("appName") String appName);

    /**
     * 普通用户根据提交时间来查询申请表
     *
     * @param appName
     * @param appTime
     * @param index
     * @param pageSize
     * @return
     */
    List<RecordUtil> getAppByTime(@Param("appName") String appName, @Param("appTime") String appTime, @Param("index") Integer index, @Param("pageSize") Integer pageSize);

    Integer countAppByTime(@Param("appName") String appName, @Param("appTime") String appTime);

    /**
     * nativeQuery : 使用本地sql的方式查询
     * 动态遍历自定义审核级别的申请表
     */
    List<RecordUtil> findUserByAppPass(@Param("appPass") Integer appPass, @Param("pages") Integer pages, @Param("num") Integer num);


    Integer queryAppAppPassCounts(@Param("appPass") Integer appPass);

    /**
     * 根据时间查询通过或未通过的申请表
     */
    List<RecordUtil> queryAppByTime(@Param("appTime") String appTime, @Param("appPass") Integer appPass, @Param("pages") Integer pages, @Param("num") Integer num);


    Integer queryAppByTimeCounts(@Param("appTime") String appTime, @Param("appPass") Integer appPass);

    /**
     * 根据时间倒序遍历所有的申请表
     */
    List<RecordUtil> queryAllAppTime(@Param("pages") Integer pages, @Param("num") Integer num);

    Integer queryAllAppTimeCounts();

    Integer deleteByRecordId(@Param("recordId") Integer recordId);
}
