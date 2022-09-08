package com.credit.controller;

import com.credit.pojo.Authorization;
import com.credit.pojo.Page;
import com.credit.pojo.User;
import com.credit.repository.AuthorizationRepository;
import com.credit.repository.UserRepository;
import com.credit.service.AuthorizationService;
import com.credit.service.RecordService;
import com.credit.service.UserService;
import com.credit.service.serviceimpl.SecurityUserServiceImpl;
import com.credit.util.RecordUtil;
import com.credit.util.Response;
import com.credit.util.ReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Api(tags = "普通用户-管理员-超级管理员")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;


    /**
     * 修改用户
     */
    @PreAuthorize("hasAnyAuthority('2','1','12')")
    @ApiOperation("修改用户")
    @PutMapping("/updateUser")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true),
            @ApiImplicitParam(name = "userSex", value = "性别", required = true),
            @ApiImplicitParam(name = "userCode", value = "学号", required = true),
            @ApiImplicitParam(name = "userFaculty", value = "学院", required = true),
            @ApiImplicitParam(name = "userGrade", value = "年级", required = true),
            @ApiImplicitParam(name = "userMajor", value = "专业", required = true),
            @ApiImplicitParam(name = "userClass", value = "班级", required = true),
            @ApiImplicitParam(name = "userStatus", value = "状态码", required = true),
            @ApiImplicitParam(name = "userRemark", value = "备注", required = false),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    public ReturnUtil updateUser(String userName, String userSex, String userCode, String userFaculty, String userGrade, String userMajor, String userClass, Integer userStatus, String userRemark, Integer userId) {
        return userService.updateUser(userName, userSex, userCode, userFaculty, userGrade, userMajor, userClass, userStatus, userRemark, userId);
    }

    /**
     * 删除用户
     */
    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "删除用户", notes = "获取地址", httpMethod = "DELETE")
    @DeleteMapping("/deleteUserById")
    @ResponseBody
    public Response deleteUser(int userId) {
        userService.deleteUser(userId);
        return Response.ok("删除成功！");
    }

    /**
     * 通过分页查询指定等级的用户
     */
    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "通过分页查询指定等级的用户")
    @GetMapping("/findUserPages")
    @ResponseBody
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "userStatus", value = "普通用户为0，普通管理员为1，超级为2"),
                    @ApiImplicitParam(name = "pages", value = "第几页"),
                    @ApiImplicitParam(name = "num", value = "查询数量"),
            }
    )
    public Response find(int userStatus, int pages, int num) {
        return Response.ok(userService.findAllUser(userStatus, pages, num));
    }

    /**
     * id查用户
     */
    @PreAuthorize("hasAnyAuthority('1','2','0','12')")
    @ApiOperation(value = "id查用户")
    @GetMapping("/findUserByID")
    @ResponseBody
    public User findbyId(int id) {
        return userService.findUserById(id);
    }

    /**
     * 普通管理员查询本院系所有普通用户
     */
    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "普通管理员查询本院系所有普通用户")
    @GetMapping("/findByUserFacultyAndUserStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userFaculty", value = "所在学院", required = true),
            @ApiImplicitParam(name = "userStatus", value = "想要查的用户等级（0）", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页显示的数据条数", required = true)
    })
    public Page<User> findByUserFacultyAndUserStatus(String userFaculty, Integer userStatus, Integer pageNo, Integer pageSize) {
        return userService.findByUserFacultyAndUserStatus(userFaculty, userStatus, pageNo, pageSize);
    }

    /**
     * 超级管理员修改用户权限等级
     */
    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "超级管理员修改用户权限等级")
    @PutMapping("/modifyUserStatus")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userStatus", value = "用户权限等级(默认为0，普通管理员为1，超级管理员为2)"),
            @ApiImplicitParam(name = "userId", value = "用户ID"),
    })
    public Response a(Integer userStatus, Integer userId) {
        List<String> list = new LinkedList<>();
        String a = "空值异常！";
        if (userStatus == null || userId == null) {
            list.add(a);
            return Response.error(list);
        } else {
            return Response.ok(userService.modifyUserStatus(userStatus, userId));
        }
    }

    /**
     * 后台增加用户
     */
    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "后台增加用户")
    @PostMapping("/addUserBeh")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "学号", required = true),
            @ApiImplicitParam(name = "userFaculty", value = "学院", required = true),
            @ApiImplicitParam(name = "userStatus", value = "用户权限等级(默认为0，普通管理员为1，超级管理员为2)", required = true),
            @ApiImplicitParam(name = "userName", value = "名字", required = true),
            @ApiImplicitParam(name = "userSex", value = "性别", required = true),
            @ApiImplicitParam(name = "userGrade", value = "年级", required = true),
            @ApiImplicitParam(name = "userMajor", value = "专业", required = true),
            @ApiImplicitParam(name = "userClass", value = "班级", required = true),
            @ApiImplicitParam(name = "userId", value = "使用本接口的用户id", required = true)
    })
    public Response a1(String userName, String userSex, String userCode, String userFaculty, String userGrade, String userMajor, String userClass, Integer userStatus, Integer userId) {
        return userService.addUserBeh(userName, userSex, userCode, userFaculty, userGrade, userMajor, userClass, userStatus, userId);
    }

    @PreAuthorize("hasAnyAuthority('1','2','0','12')")
    @ApiOperation(value = "修改用户个人密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "beforePassword", value = "用户原密码", required = true),
            @ApiImplicitParam(name = "afterPassword", value = "要修改的密码", required = true),
            @ApiImplicitParam(name = "againPassword", value = "要修改的密码（第二次确认）", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    })
    @PutMapping("/updatePassword")
    public ReturnUtil updatePassword(String beforePassword, String afterPassword, String againPassword, Integer userId) {
        return userService.updatePassword(beforePassword, afterPassword, againPassword, userId);
    }

    /**
     * 根据院系，班级，专业，年级，权限查询用户
     */
    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "根据院系，班级，专业，年级，权限查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userFaculty", value = "院系"),
            @ApiImplicitParam(name = "userGrade", value = "年级"),
            @ApiImplicitParam(name = "userMajor", value = "专业"),
            @ApiImplicitParam(name = "userClass", value = "班级"),
            @ApiImplicitParam(name = "pages", value = "第几页"),
            @ApiImplicitParam(name = "num", value = "查询数量"),
            @ApiImplicitParam(name = "userStatus", value = "权限查询用户")
    })
    @GetMapping("/findUserRequirement")
    public Response a2(String userFaculty, String userGrade, String userMajor, String userClass, Integer userStatus, Integer pages, Integer num) {
        return Response.ok(userService.findUserRequirement(userFaculty, userGrade, userMajor, userClass, userStatus, pages, num));
    }

    /**
     * 根据年级，权限等级，组织分页查询用户
     */
    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "根据年级，权限等级，组织分页查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userFaculty", value = "院系"),
            @ApiImplicitParam(name = "userGrade", value = "班级"),
            @ApiImplicitParam(name = "pages", value = "第几页"),
            @ApiImplicitParam(name = "num", value = "查询数量"),
            @ApiImplicitParam(name = "userStatus", value = "权限查询用户")
    })
    @GetMapping("/findUserByFacultyAndGrade")
    public Response a3(String userGrade, Integer userStatus, String userFaculty, Integer pages, Integer num) {
        return Response.ok(userService.findUserByGradeAndFaculty(userGrade, userStatus, userFaculty, pages, num));
    }

    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "年级，姓名，专业，班级模糊查询普通用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userFaculty", value = "学院", required = true),
            @ApiImplicitParam(name = "search", value = "查询内容", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数据条数", required = true)
    })
    @GetMapping("/findUserLike")
    public Page<User> findUserLike(String userFaculty, String search, Integer pageNo, Integer pageSize) {
        return userService.findUserLike(userFaculty, search, pageNo, pageSize);
    }

    /**
     * 根据 年级+专业+权限等级 分页查询用户
     */
    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "根据 年级+专业+权限等级 分页查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userMajor", value = "专业"),
            @ApiImplicitParam(name = "userGrade", value = "班级"),
            @ApiImplicitParam(name = "pages", value = "第几页"),
            @ApiImplicitParam(name = "num", value = "查询数量"),
            @ApiImplicitParam(name = "userStatus", value = "权限查询用户")
    })
    @GetMapping("/findUserByMajorAndGrade")
    public Response a4(String userGrade, Integer userStatus, String userMajor, Integer pages, Integer num) {
        return Response.ok(userService.findUserByGradeAndMajor(userGrade, userStatus, userMajor, pages, num));
    }

    @Autowired
    private SecurityUserServiceImpl userDetailsService;

    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "usercode", value = "学号", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })
    @PostMapping("/login")
    public @ResponseBody
    UserDetails login(String usercode, String password) {
        return userDetailsService.loadUserByUsername(usercode);
    }

    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "学号，学院判断用户是否存在")
    @GetMapping("/getUserByUserFacultyAndAndUserCode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "学号", required = true),
            @ApiImplicitParam(name = "userFaculty", value = "学院", required = true)

    })
    public ReturnUtil getUserByUserNameAndUserFacultyAndAndUserCode(String userCode, String userFaculty) {
        return userService.getUserByUserNameAndUserFacultyAndAndUserCode(userCode, userFaculty);
    }

    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "通过学校审核的学院学生认定信息汇总统计表信息（所有）")
    @GetMapping("/getResultTable")
    public ReturnUtil getResultTable() {
        return userService.getResultTable();
    }

    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "通过学校审核的学院学生认定信息汇总统计表信息某个用户的最终申请总学分")
    @GetMapping("/getOneUserResultTable")
    @ApiImplicitParam(name = "appStuID", value = "学号", required = true)
    public ReturnUtil getOneUserResultTable(String appStuID) {
        return userService.getOneUserResultTable(appStuID);
    }

    @PreAuthorize("hasAnyAuthority('1','2','12')")
    @ApiOperation(value = "重置用户密码")
    @PutMapping("/resetPassword")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    public ReturnUtil resetPassword(Integer userId) {
        return userService.resetPassword(userId);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorizationRepository authorizationRepository;

    @PreAuthorize("hasAnyAuthority('2','12')")
    @ApiOperation(value = "通过管理员申请调用添加用户接口")
    @PutMapping("/passManagerApp")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "申请的用户id", required = true),
            @ApiImplicitParam(name = "authId", value = "申请信息id", required = true)
    })
    public ReturnUtil passManagerApp(Integer userId, Integer authId) {
        authorizationRepository.modifyAuthStatus(authId);
        Integer integer = userRepository.modifyUserStatus(12, userId);
        if (integer == null || integer < 1) {
            return ReturnUtil.error("审核通过失败！");
        }
        return ReturnUtil.success("审核通过成功！");
    }

    @Autowired
    private AuthorizationService authorizationService;

    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "删除管理员申请调用添加用户接口信息")
    @DeleteMapping("/deleteManagerApp")
    @ApiImplicitParam(name = "authId", value = "申请信息id", required = true)
    public ReturnUtil deleteManagerApp(Integer authId) {
        return authorizationService.deleteMessage(authId);
    }

    @PreAuthorize("hasAnyAuthority('2')")
    @ApiOperation(value = "分页查询所有管理员申请调用添加用户接口信息")
    @GetMapping("/findAllManagerApp")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页信息条数", required = true)
    })
    public Page<Authorization> findAllManagerApp(Integer pageNo, Integer pageSize) {
        return authorizationService.findAllMessage(pageNo, pageSize);
    }

    @Autowired
    private RecordService recordService;

    @PreAuthorize("hasAnyAuthority('2','1','0','12')")
    @ApiOperation(value = "添加提交记录")
    @PostMapping("/addRecord")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = true),
            @ApiImplicitParam(name = "recordAppid", value = "申请表id", required = true)
    })
    public ReturnUtil addRecord(String userName, Integer recordAppid) {
        return recordService.addRecord(userName, recordAppid);
    }

    @PreAuthorize("hasAnyAuthority('2','1','0','12')")
    @ApiOperation(value = "删除提交记录")
    @DeleteMapping("/deleteRecord")
    @ApiImplicitParam(name = "recordId", value = "记录id", required = true)
    public ReturnUtil deleteRecord(Integer recordId) {
        return recordService.deleteRecord(recordId);
    }

    @PreAuthorize("hasAnyAuthority('2','1','0','12')")
    @ApiOperation(value = "模糊查询提交记录")
    @GetMapping("/getLikeAppRecord")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appAcademic", value = "学院", required = true),
            @ApiImplicitParam(name = "search", value = "查询信息", required = true),
            @ApiImplicitParam(name = "pageNo", value = "当前页", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true),
    })
    public Page<RecordUtil> getLikeAppRecord(String appAcademic, String search, Integer pageNo, Integer pageSize) {
        return recordService.getLikeAppRecord(appAcademic, search, pageNo, pageSize);
    }
}




