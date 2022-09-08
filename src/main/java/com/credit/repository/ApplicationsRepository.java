package com.credit.repository;

import com.credit.pojo.Applications;
import com.credit.pojo.Inquiry;
import com.credit.pojo.User;
import com.credit.util.RecordUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface ApplicationsRepository extends JpaRepository<Applications, Integer>, JpaSpecificationExecutor {


    /**
     * 根据院系查询对应等级的申请表
     **/
    @Query(value = "select * from applications where app_academic=?1 and app_pass=?2 limit ?3,?4", nativeQuery = true)
    List<Applications> queryAppByAcademic(String appAcademic, int appPass, int pages, int num);

    /**
     * 根据院系查询对应通过等级的申请表数量
     **/
    @Query(value = "select count(*) from applications where app_academic=?1 and app_pass=?2", nativeQuery = true)
    int queryAppByAcademicCounts(String appAcademic, int appPass);

    /**
     * 根据id查询通过等级的申请表
     **/
    @Query(value = "select * from applications where app_id=?1 and app_pass=?2", nativeQuery = true)
    Applications queryAppByIdAndPass(int appId, int appPass);

    /**
     * 根据id查询通过等级的申请表数量
     **/
    @Query(value = "select count(*) from applications where app_id=?1 and app_pass=?2 ", nativeQuery = true)
    int queryAppByIdAndPassCounts(int appId, int appPass);

//    /**
//     *根据年级和院系查询通过的申请表
//     **/
//    @Query(value="select * from applications where app_grade=?1 and app_academic=?2 and app_pass=1 limit ?3,?4",nativeQuery = true)
//    List<Applications>queryByGradeAndAndAppAcademic(String appGrade,String appAcademic,int pages,int num);
//
//    /**
//     *根据年级和院系查询通过的申请表数量
//     **/
//    @Query(value="select count(*) from applications where app_grade=?1 and app_academic=?2 and app_pass=1",nativeQuery = true)
//    int queryByGradeAndAndAppAcademicCounts(String appGrade,String appAcademic);


    /**
     * 修改申请表通过状态
     **/
    @Transactional
    @Modifying
    @Query(value = "update applications set app_pass=?1 where app_id=?2", nativeQuery = true)
    void updateAppPass(int appPass, int appId);


    /**
     * 查询某个学院的所有未审核申请表
     *
     * @param index
     * @param pageSize
     * @return
     */
    @Query(value = "SELECT a.* FROM applications AS a LEFT JOIN USER AS b ON a.app_stuID = b.user_code WHERE app_pass = 0 and b.user_status = 0 AND app_academic = ?1  limit ?2 , ?3", nativeQuery = true)
    List<Applications> getAppAllWait(String appAcademic, Integer index, Integer pageSize);

    @Query(value = "SELECT count(*)FROM applications AS a LEFT JOIN USER AS b ON a.app_stuID = b.user_code WHERE app_pass = 0 and b.user_status = 0 AND app_academic = ?", nativeQuery = true)
    Integer countAppAllWait(String appAcademic);

    /**
     * 查询某学院所有审核通过申请表
     *
     * @param appAcademic
     * @param index
     * @param pageSize
     * @return
     */
    @Query(value = "SELECT a.* FROM applications AS a LEFT JOIN USER AS b ON a.app_stuID = b.user_code WHERE app_pass > 0 and b.user_status = 0 AND app_academic = ?1  limit ?2 , ?3", nativeQuery = true)
    List<Applications> getAppAllPass(String appAcademic, Integer index, Integer pageSize);

    @Query(value = "SELECT count(*)FROM applications AS a LEFT JOIN USER AS b ON a.app_stuID = b.user_code WHERE app_pass > 0 and b.user_status = 0 AND app_academic = ?", nativeQuery = true)
    Integer countAppAllPass(String appAcademic);

    /**
     * 查询某个学院所有未通过申请表
     *
     * @param appAcademic
     * @param index
     * @param pageSize
     * @return
     */
    @Query(value = "SELECT a.* FROM applications AS a LEFT JOIN USER AS b ON a.app_stuID = b.user_code WHERE app_pass < 0 and b.user_status = 0 AND app_academic = ?1  limit ?2 , ?3", nativeQuery = true)
    List<Applications> getAppAllNoPass(String appAcademic, Integer index, Integer pageSize);

    @Query(value = "SELECT count(*)FROM applications AS a LEFT JOIN USER AS b ON a.app_stuID = b.user_code WHERE app_pass < 0 and b.user_status = 0 AND app_academic = ?", nativeQuery = true)
    Integer countAppAllNoPass(String appAcademic);


    /**
     * 导出通过审核的学分认定信息汇总统计表
     */
    @Query(value = "select * from applications where app_pass=2", nativeQuery = true)
    List<Applications> findAllAppTwo();

    /**
     * 导出通过审核的学分认定信息汇总统计表
     */
    @Query(value = "select * from applications where app_pass=2 and concat(app_major,app_class,app_academic) like concat('%',?,'%')", nativeQuery = true)
    List<Applications> findAllAppTwoConcat(String thing);

    /**
     * 通过学号获取某个学分类型的和
     */
    @Query(value = "select app_academic_score from applications where app_stuID=?1 and app_type=?2 and app_pass=2", nativeQuery = true)
    List<BigDecimal> queryScoreHe();

    @Query(value = "select a.user_id,a.user_name,a.user_sex,a.user_code,b.* from user as a left join applications as b on a.user_name = b.app_name where b.app_academic=:appAcademic and concat(b.app_name,b.app_major,b.app_class,b.app_type,b.app_group,b.app_pass) like concat('%',:search,'%') limit :index,:pageSize", nativeQuery = true)
    List<Applications> getApplicationsLike(@Param("appAcademic") String appAcademic, @Param("search") String search, @Param("index") Integer index, @Param("pageSize") Integer pageSize);

    @Query(value = "select count(*) from user as a left join applications as b on a.user_name = b.app_name where b.app_academic=:appAcademic and concat(b.app_name,b.app_major,b.app_class,b.app_type,b.app_group,b.app_pass) like concat('%',:search,'%')", nativeQuery = true)
    Integer countApplicationsLike(@Param("appAcademic") String appAcademic, @Param("search") String search);

    @Query(value = "select * from applications where app_stuID=?1 and app_pass=2", nativeQuery = true)
    List<Applications> queryScoreHe(String appStuID);

    /**
     * 根据学号修改申请表表通过状态
     **/
    @Transactional
    @Modifying
    @Query(value = "update applications set app_pass=?1 ,app_academic_name=?3 ,app_recognize=?4 ,app_academic_score=?5 where app_id=?2", nativeQuery = true)
    void updateAppPassById(int appPass, int appId, String appAcademicName, String appRecognize, BigDecimal appAcademicScore);

    /**
     * 模糊查询申请表 concat(user_name,user_code,user_grade,user_class,user_major) like concat('%',?,'%')
     */
    @Query(value = "select * from applications where app_pass=?4 and concat(app_name,app_stuID,app_major,app_class,app_academic,app_type) like concat('%',?1,'%') limit ?2,?3", nativeQuery = true)
    List<Applications> queryAppConcat(@Param("thing") String thing, @Param("pages") int pages, @Param("num") int num, @Param("appPass") int appPass);

    /**
     * 模糊查询申请表数量
     */
    @Query(value = "select count(*) from applications where app_pass=?2 and concat(app_name,app_stuID,app_major,app_class,app_academic,app_type) like concat('%',?1,'%')", nativeQuery = true)
    int queryAppConcatCounts(@Param("thing") String thing, @Param("appPass") int appPass);

    /**
     * 根据学号删除申请表
     */
    @Transactional
    @Modifying
    @Query(value = "delete from applications where app_stuID=?", nativeQuery = true)
    int deleteAppByStuID(String appStuID);

    @Transactional
    @Modifying
    @Query(value = "delete a,b,c from applications as a left join paths as b on a.app_id = b.id left join record as c on a.app_id = c.record_appid where a.app_id=?", nativeQuery = true)
    void deleteApplications(Integer appId);

    /**
     * 根据id修改申请表类型
     **/
    @Transactional
    @Modifying
    @Query(value = "update applications set app_type=?1 where app_id=?2", nativeQuery = true)
    void updateAppTypeById(String appType, int appId);

    //根据学号查询某个用户下某个权限的申请表
    @Query(value = "select * from applications where app_stuID=?1 and app_pass=?2", nativeQuery = true)
    List<Applications> queryAppByCodeAndPass(String code, int appPass);

    /**
     * nativeQuery : 使用本地sql的方式查询
     * 动态遍历自定义审核级别的申请表
     */
    @Query(value = "select * from applications where app_pass=?1 limit ?2,?3 ", nativeQuery = true)
    List<Applications> findUserByAppPass(int appPass, int pages, int num);

    /**
     * 动态查询审核级别的申请表的数量
     */
    @Query(value = "select count(*) from applications where app_pass=?", nativeQuery = true)
    int queryAppAppPassCounts(int appPass);

    /**
     * 根据时间查询通过或未通过的申请表
     */
    @Query(value = "select * from applications where app_time=?1 and app_pass=?2 limit ?3 , ?4", nativeQuery = true)
    List<Applications> queryAppByTime(String appTime, Integer appPass, Integer pages, Integer num);

    /**
     * 根据时间查询通过或未通过的申请表数量
     */
    @Query(value = "select count(*) from applications where app_time=?1 and app_pass=?2", nativeQuery = true)
    int queryAppByTimeCounts(String appTime, Integer appPass);
}
