package com.credit.repository;

import com.credit.pojo.User;

import com.credit.util.Result;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor {
    /**
     * 修改用户密码
     *
     * @param userPassword
     * @param userId
     * @return
     */
    @Modifying
    @Transactional
    @Query(value = "update user set user_password = ?1 where user_id = ?2", nativeQuery = true)
    Integer updatePassword(String userPassword, Integer userId);

    /**
     * @param userName
     * @param userSex
     * @param userCode
     * @param userFaculty
     * @param userGrade
     * @param userMajor
     * @param userClass
     * @param userStatus
     * @param userRemark
     * @param userId
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update user set user_name = ?1 ,user_sex=?2, user_code = ?3 , user_faculty = ?4 ,user_grade = ?5 , user_major = ?6 , user_class= ?7,user_status = ?8 , user_remark = ?9 where user_id = ?10", nativeQuery = true)
    Integer updateUser(String userName, String userSex, String userCode, String userFaculty, String userGrade, String userMajor, String userClass, Integer userStatus, String userRemark, Integer userId);

    @Query(value = "select user_password from user where user_id = ?1", nativeQuery = true)
    String getPassword(Integer userId);

    /**
     * 普通管理员
     * 根据院系和权限等级查询某个院系下的所有普通成员
     *
     * @param userFaculty
     * @param userStatus
     * @return
     */
    @Transactional
    @Query(value = "select * from user where user_faculty = ?1 and user_status = ?2  limit ?3 , ?4", nativeQuery = true)
    List<User> findByUserFacultyAndUserStatus(String userFaculty, Integer userStatus, Integer index, Integer pageSize);

    Integer countByUserFacultyAndUserStatus(String userFaculty, Integer userStatus);

    /**
     * 通过分页查询指定等级的用户
     */
    @Query(value = "select * from user where user_status=?1 order by user_id desc limit ?2,?3", nativeQuery = true)
    List<User> findAllUserPages(int userStatus, int pages, int num);

    /**
     * 查询指定等级用户数量
     */
    @Query(value = "select count(*) from user where user_status=?", nativeQuery = true)
    Integer queryUserStatusCounts(int userStatus);

    /**
     * 超级管理员修改用户权限等级
     */
    @Transactional
    @Modifying
    @Query(value = "update user set user_status=?1 where user_id=?2", nativeQuery = true)
    Integer modifyUserStatus(int userStatus, int userID);

    /**
     * 根据id查询用户
     */
    @Query(value = "select  * from user where user_id=?", nativeQuery = true)
    User findUserById(int userID);

    /**
     * 根据学号查询用户
     */
    @Query(value = "select  * from user where user_code=?", nativeQuery = true)
    User findUserByCode(String userCode);

//    /**
//     * 根据学号模糊查询用户
//     */
//    @Query(value = "select  * from user where user_code=?1 and concat(user_name,user_code,user_grade,user_class,user_major) like concat('%',?2,'%')", nativeQuery = true)
//    User findUserByCodeConcat(String userCode,String thing);

    /**
     * 根据院系查询用户
     */
    @Query(value = "select  * from user where user_faculty=?1 and user_status=?2 limit ?3,?4", nativeQuery = true)
    List<User> findUserByFaculty(String userFaculty, int userStatus, int pages, int num);

    /**
     * 根据院系查询用户数量
     */
    @Query(value = "select  Count(*) from user where user_faculty=?1 and user_status=?2", nativeQuery = true)
    Integer findUserByFacultyCounts(String userFaculty, int userStatus);

    /**
     * 根据年级查询用户
     */
    @Query(value = "select  * from user where user_grade=?1 and user_status=?2 limit ?3,?4", nativeQuery = true)
    List<User> findUserByGrade(String userGrade, int userStatus, int pages, int num);

    /**
     * 根据年级查询用户数量
     */
    @Query(value = "select  count(*) from user where user_grade=?1 and user_status=?2", nativeQuery = true)
    Integer findUserByGradeCounts(String userGrade, int userStatus);

    /**
     * 根据专业查询用户
     */
    @Query(value = "select  * from user where user_major=?1 and user_status=?2 limit ?3,?4", nativeQuery = true)
    List<User> findUserByMajor(String userMajor, int userStatus, int pages, int num);

    /**
     * 根据专业查询用户数量
     */
    @Query(value = "select  count(*) from user where user_major=?1 and user_status=?2", nativeQuery = true)
    Integer findUserByMajorCounts(String userMajor, int userStatus);

    /**
     * 根据班级查询用户
     */
    @Query(value = "select  * from user where user_class=?1 and user_status=?2 limit ?3,?4", nativeQuery = true)
    List<User> findUserByClass(String userClass, int userStatus, int pages, int num);

    /**
     * 根据班级查询用户数量
     */
    @Query(value = "select  count(*) from user where user_class=?1 and user_status=?2", nativeQuery = true)
    Integer findUserByClassCounts(String userClass, int userStatus);

    /**
     * 根据年级，权限等级，组织查询用户
     */
    @Query(value = "select  * from user where user_grade=?1 and user_status=?2 and user_faculty=?3 limit ?4,?5", nativeQuery = true)
    List<User> findUserByGradeAndFaculty(String userGrade, int userStatus, String userFaculty, int pages, int num);

    /**
     * 根据年级，权限等级，组织查询用户数量
     */
    @Query(value = "select  count(*) from user where user_grade=?1 and user_status=?2 and user_faculty=?3", nativeQuery = true)
    Integer findUserByGradeAndFacultyCounts(String userGrade, int userStatus, String userFaculty);

    /**
     * 根据年级，权限等级，组织分页查询用户
     *
     * @param search
     * @param index
     * @param pageSize
     * @return
     */
    @Query(value = "select * from user where user_status =0 and user_faculty=:userFaculty and concat(user_name,user_grade,user_class,user_major) like concat('%',:search,'%') limit :index,:pageSize", nativeQuery = true)
    List<User> getUserLike(@Param("userFaculty") String userFaculty, @Param("search") String search, @Param("index") Integer index, @Param("pageSize") Integer pageSize);

    @Query(value = "select count(*) from user where user_status =0 and user_faculty=:userFaculty and concat(user_name,user_grade,user_class,user_major) like concat('%',:search,'%')", nativeQuery = true)
    Integer countUserLike(@Param("userFaculty") String userFaculty, @Param("search") String search);

    /**
     * 根据 年级+专业+权限等级 分页查询用户
     */
    @Query(value = "select  * from user where user_grade=?1 and user_status=?2 and user_major=?3 limit ?4,?5", nativeQuery = true)
    List<User> findUserByGradeAndMajor(String userGrade, int userStatus, String userMajor, int pages, int num);

    /**
     * 根据 年级+专业+权限等级 分页查询用户Counts
     */
    @Query(value = "select  count(*) from user where user_grade=?1 and user_status=?2 and user_major=?3", nativeQuery = true)
    Integer findUserByGradeAndMajorCounts(String userGrade, int userStatus, String userMajor);

    /**
     * 导出通过审核的学分认定信息汇总统计表
     */
    @Query(value = "select * from user where user_status=2", nativeQuery = true)
    List<User> findAllUserTwo();


    /**
     * 根据用户名，学号，学院判断用户是否存在
     *
     * @param userCode
     * @return
     */
    User getUserByUserCode(String userCode);

    User getUserByUserName(String userName);
    /**
     * 通过学校审核的学院学生认定信息汇总统计表信息（所有）
     *
     * @return
     */
    @Query(value = "SELECT\n" +
            "\ta.app_name,\n" +
            "\ta.app_stuID,\n" +
            "\tb.user_grade,\n" +
            "\ta.app_major,\n" +
            "\ta.app_class,\n" +
            "\tGROUP_CONCAT( a.app_type SEPARATOR ',' ) AS app_type,\n" +
            "\tgroup_concat( a.app_score SEPARATOR ',' ) AS app_score,\n" +
            "\ta.app_recognize,\n" +
            "\tb.user_remark \n" +
            "FROM\n" +
            "\tapplications AS a\n" +
            "\tRIGHT JOIN USER AS b ON b.user_name = a.app_name \n" +
            "WHERE\n" +
            "\ta.app_pass = 2 \n" +
            "GROUP BY\n" +
            "\ta.app_name", nativeQuery = true)
    List<Map<String, Object>> getResultTable();

    /**
     * 模糊查询用户
     */
    @Query(value = "select * from user where concat(user_name,user_sex,user_code,user_grade,user_class,user_major) like concat('%',?1,'%') limit ?2,?3", nativeQuery = true)
    List<User> queryUserConcat(@Param("thing") String thing, @Param("pages") int pages, @Param("num") int num);

    /**
     * 通过学校审核的学院学生认定信息汇总统计表信息某个用户的最终申请总学分
     *
     * @param appStuID
     * @return
     */
    @Query(value = "SELECT sum(app_academic_score) FROM applications WHERE app_pass = 2 and app_stuID =  ?", nativeQuery = true)
    BigDecimal getOneUserResultTable(String appStuID);

    /**
     * 模糊查询用户数量
     */
    @Query(value = "select count(*) from user where concat(user_name,user_code,user_grade,user_class,user_major) like concat('%',?,'%')", nativeQuery = true)
    int queryUserConcatCounts(@Param("thing") String thing);

    /**
     * 根据学号修改申请表表通过状态和对用户的认定结果
     **/
    @Transactional
    @Modifying
    @Query(value = "update user set user_recognize=?1 where user_code=?2", nativeQuery = true)
    void updateUserPassAndUserRecognize(String userRecognize, String userCode);

    /**
     * 重置用户密码
     *
     * @param userId
     * @return
     */
    @Transactional
    @Modifying
    @Query(value = "update user set user_password = ?1 where user_id = ?2", nativeQuery = true)
    Integer resetPassword(String userPassword, Integer userId);

    @Query(value = "select count(*) from user", nativeQuery = true)
    Integer countUser();
}
