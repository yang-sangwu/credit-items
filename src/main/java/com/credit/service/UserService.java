package com.credit.service;

import com.credit.pojo.Applications;
import com.credit.pojo.Page;
import com.credit.pojo.User;
import com.credit.util.Response;
import com.credit.util.ReturnUtil;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserService {

    //修改用户
    ReturnUtil updateUser(String userName, String userSex, String userCode, String userFaculty, String userGrade, String userMajor, String userClass, Integer userStatus, String userRemark, Integer userId);

    //删除用户
    void deleteUser(int userId);

    //后台增加用户
    Response addUserBeh(String userName,String userSex,String userCode,String userFaculty,String userGrade,String userMajor,String userClass,Integer userStatus,Integer userId);

    //通过分页查询指定等级的用户
    Response findAllUser(int userStatus,int pages,int num);

    //通过id查询用户
    User findUserById(int userId);

    Page<User> findByUserFacultyAndUserStatus(String userFaculty, Integer userStatus, Integer index, Integer pageSize);

    //超级管理员修改用户权限等级
    Response modifyUserStatus(Integer userStatus,Integer userID);

    ReturnUtil updatePassword(String beforePassword,String afterPassword, String againPassword,Integer userId);

    //根据院系，班级，专业，年级，权限查询用户
    Response findUserRequirement(String userFaculty, String userGrade,
                             String userMajor, String userClass, Integer userStatus,Integer pages,Integer num);

    //根据年级，权限等级，组织查询用户
    Response findUserByGradeAndFaculty(String userGrade, Integer userStatus, String userFaculty, Integer pages, Integer num);

    Page<User> findUserLike(String userFaculty,String search ,Integer pageNo,Integer pageSize);

    //根据 年级+专业+权限等级 分页查询用户
    Response findUserByGradeAndMajor(String userGrade, Integer userStatus, String userMajor, Integer pages, Integer num);

    ReturnUtil  getUserByUserNameAndUserFacultyAndAndUserCode(String userCode,String userFaculty );

    ReturnUtil  getResultTable();

    ReturnUtil getOneUserResultTable(String appStuID);

    ReturnUtil resetPassword(Integer userId);

}
