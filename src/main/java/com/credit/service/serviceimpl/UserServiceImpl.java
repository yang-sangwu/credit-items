    package com.credit.service.serviceimpl;

    import com.credit.pojo.Applications;
    import com.credit.pojo.Authorization;
    import com.credit.pojo.Page;
    import com.credit.pojo.User;
    import com.credit.repository.ApplicationsRepository;
    import com.credit.repository.AuthorizationRepository;
    import com.credit.repository.RejectRepository;
    import com.credit.repository.UserRepository;
    import com.credit.service.UserService;
    import com.credit.util.IDutils;
    import com.credit.util.Response;
    import com.credit.util.Result;
    import com.credit.util.ReturnUtil;
    import lombok.extern.slf4j.Slf4j;
    //import org.springframework.security.core.GrantedAuthority;
    //import org.springframework.security.core.authority.AuthorityUtils;
    //import org.springframework.security.core.userdetails.UserDetails;
    //import org.springframework.security.core.userdetails.UserDetailsService;
    //import org.springframework.security.core.userdetails.UsernameNotFoundException;
    //import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Service;

    import javax.annotation.Resource;
    import java.math.BigDecimal;
    import java.util.*;

    @Service
    @Slf4j
    public class UserServiceImpl implements UserService {
        @Resource
        private UserRepository userRepository;

        @Resource
        private ApplicationsRepository applicationsRepository;

        @Autowired
        private AuthorizationRepository authorizationRepository;

        @Autowired
        private RejectRepository rejectRepository;

        @Override
        public ReturnUtil updateUser(String userName, String userSex, String userCode, String userFaculty, String userGrade, String userMajor, String userClass, Integer userStatus, String userRemark, Integer userId) {
            Integer integer = userRepository.updateUser(userName, userSex, userCode, userFaculty, userGrade, userMajor, userClass, userStatus, userRemark, userId);
            log.info(String.valueOf(integer));
            if (integer == null || integer < 1) {
                return ReturnUtil.error("修改失败！");
            }
            return ReturnUtil.success("修改成功！");
        }

        @Override
        public void deleteUser(int userId) {
            String stuID = userRepository.findUserById(userId).getUserCode();
            applicationsRepository.deleteAppByStuID(stuID);
            userRepository.deleteById(userId);
        }

        @Override
        public Response findAllUser(int userStatus, int pages, int num) {
            Map<String, List> map = new HashMap<>();
            int totalPages;
            int total;
            if (pages <= 0 || num <= 0) {
                List<String> list = new LinkedList<>();
                list.add("请输入合理的页数或查询数量！");
                map.put("error", list);
                return Response.error(map);
            } else {
                int count = userRepository.queryUserStatusCounts(userStatus);
                if (count % num == 0) {
                    totalPages = count / num;
                } else {
                    total = count / num;
                    totalPages = total + 1;
                }
                List<Integer> list = new LinkedList<>();
                list.add(totalPages);
                map.put("总共的页数", list);
                List<Integer> list1 = new LinkedList<>();
                list1.add(count);
                map.put("总条数", list1);
                int thePage = (pages - 1) * num;
                List<User> list2 = userRepository.findAllUserPages(userStatus, thePage, num);
                map.put("查询信息", list2);
                return Response.ok(map);
            }
        }

        @Override
        public User findUserById(int userId) {
            return userRepository.findById(userId).orElse(null);
        }

        @Override
        public Page<User> findByUserFacultyAndUserStatus(String userFaculty, Integer userStatus, Integer pageNo, Integer pageSize) {
            List<User> list = new ArrayList<>();
            String meg = "";
            Integer count = userRepository.countByUserFacultyAndUserStatus(userFaculty, userStatus);
    //        log.info("count:" + count);
            Integer pageTotal = 0;
            if (count % pageSize != 0)
                pageTotal++;
            pageTotal += userRepository.countByUserFacultyAndUserStatus(userFaculty, userStatus) / pageSize;
            if (pageNo > pageTotal) {
                meg = "查询失败,超出最大页码";
            } else if (pageNo <= 0) {
                meg = "查询失败,页码不存在";
            } else {
                int index = (pageNo - 1) * pageSize;
                list = userRepository.findByUserFacultyAndUserStatus(userFaculty, userStatus, index, pageSize);
                meg = "查询成功";
                return new Page(1, meg, pageNo, pageSize, pageTotal, count, list);
            }
            return Page.error(0, meg);
        }

        @Override
        public Response modifyUserStatus(Integer userStatus, Integer userID) {
            User user = userRepository.findUserById(userID);
            String b = "没有此用户！";
            List<String> list = new LinkedList<>();
            if (user == null) {
                list.add(b);
                return Response.ok(list);
            } else {
                return Response.ok(userRepository.modifyUserStatus(userStatus, userID));
            }
        }

        @Override
        public ReturnUtil updatePassword(String beforePassword, String afterPassword, String againPassword, Integer userId) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (beforePassword == null || afterPassword == null || againPassword == null) {
                return ReturnUtil.error("密码不能为空！");
            } else if (!bCryptPasswordEncoder.matches(beforePassword, userRepository.getPassword(userId))) {
                return ReturnUtil.error("原密码错误！");
            } else if (!afterPassword.equals(againPassword)) {
                return ReturnUtil.error("两次输入的密码不一致！");
            }else if (beforePassword.equals(againPassword)){
                return ReturnUtil.error("修改的密码不能为原密码！");
            }

            String encode = bCryptPasswordEncoder.encode(afterPassword);
            userRepository.updatePassword(encode, userId);
            return ReturnUtil.success("修改成功！");
        }

        /**
         * 后台增加用户
         */
        @Override
        public Response addUserBeh(String userName, String userSex, String userCode, String userFaculty, String userGrade, String userMajor, String userClass, Integer userStatus, Integer userId) {
            User one = userRepository.getOne(userId);
            Integer status = one.getUserStatus();
            if (status == 1) {
                Authorization auth = authorizationRepository.getAuthorizationByAuthUseridAAndAndAuthStatus(userId, 0);
                if (auth !=null){
                    return Response.error("已经提交申请,请等待审核!");
                }
                String message = "管理员" + one.getUserName() + "申请调用添加用户接口";
                Authorization authorization = new Authorization(null,userId,message,0);
                authorizationRepository.save(authorization);
                return new Response(403, "你还没有权限调用此接口,已经自动替你向超级管理员申请调用", null);
            } else {
                BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                String userPassword = bCryptPasswordEncoder.encode("123456");
                User user1 = userRepository.findUserByCode(userCode);

                if (userCode == null || userStatus == null || userName == null || userSex == null || userGrade == null || userMajor == null || userClass == null) {
                    return Response.error("空值异常！");
                }
                if (user1 != null) {
                    return Response.error("请检查学号！此用户已存在！");
                } else {
                    User user = new User(userName, userSex, userCode, userPassword, userFaculty,
                            userGrade, userMajor, userClass, userStatus);
                    userRepository.save(user);
                    if (status == 12) {
                        userRepository.modifyUserStatus(1, userId);
                    }
                    return Response.ok(user);
                }
            }
        }

        /**
         * 根据院系，班级，专业，年级，权限查询用户
         */
        @Override
        public Response findUserRequirement(String userFaculty, String userGrade, String userMajor, String userClass, Integer userStatus, Integer pages, Integer num) {
            Map<String, List> map = new HashMap<>();
            int totalPages;
            int total;
            if (userStatus == null || pages == null || num == null) {
                String a = "权限等级或分页数据不准为空！";
                return Response.error(a);
            }
            if (userFaculty != null && userGrade != null || userFaculty != null && userMajor != null || userFaculty != null && userClass != null || userGrade != null && userMajor != null || userGrade != null && userClass != null || userMajor != null && userClass != null || userFaculty != null && userGrade != null && userMajor != null || userFaculty != null && userGrade != null && userClass != null || userGrade != null && userMajor != null && userClass != null || userFaculty != null && userMajor != null && userClass != null || userFaculty != null && userGrade != null && userMajor != null && userClass != null) {
                String b = "除权限等级与分页数据外的请挑选一个！";
                return Response.error(b);
            }
            if (pages <= 0 || num <= 0) {
                return Response.error("请输入合理的分页数据！");
            } else {
                if (userFaculty != null) {
                    int count = userRepository.findUserByFacultyCounts(userFaculty, userStatus);
                    if (count % num == 0) {
                        totalPages = count / num;
                    } else {
                        total = count / num;
                        totalPages = total + 1;
                    }
                    List<Integer> list = new LinkedList<>();
                    list.add(totalPages);
                    map.put("总共的页数", list);
                    List<Integer> list1 = new LinkedList<>();
                    list1.add(count);
                    map.put("总条数", list1);
                    int thePage = (pages - 1) * num;
                    List<User> list2 = userRepository.findUserByFaculty(userFaculty, userStatus, thePage, num);
                    map.put("查询信息", list2);
                    return Response.ok(map);
                }
                if (userClass != null) {
                    int count = userRepository.findUserByClassCounts(userClass, userStatus);
                    if (count % num == 0) {
                        totalPages = count / num;
                    } else {
                        total = count / num;
                        totalPages = total + 1;
                    }
                    List<Integer> list = new LinkedList<>();
                    list.add(totalPages);
                    map.put("总共的页数", list);
                    List<Integer> list1 = new LinkedList<>();
                    list1.add(count);
                    map.put("总条数", list1);
                    int thePage = (pages - 1) * num;
                    List<User> list2 = userRepository.findUserByClass(userClass, userStatus, thePage, num);
                    map.put("查询信息", list2);
                    return Response.ok(map);
                }
                if (userGrade != null) {
                    int count = userRepository.findUserByGradeCounts(userGrade, userStatus);
                    if (count % num == 0) {
                        totalPages = count / num;
                    } else {
                        total = count / num;
                        totalPages = total + 1;
                    }
                    List<Integer> list = new LinkedList<>();
                    list.add(totalPages);
                    map.put("总共的页数", list);
                    List<Integer> list1 = new LinkedList<>();
                    list1.add(count);
                    map.put("总条数", list1);
                    int thePage = (pages - 1) * num;
                    List<User> list2 = userRepository.findUserByGrade(userGrade, userStatus, thePage, num);
                    map.put("查询信息", list2);
                    return Response.ok(map);
                }
                if (userMajor != null) {
                    int count = userRepository.findUserByMajorCounts(userMajor, userStatus);
                    if (count % num == 0) {
                        totalPages = count / num;
                    } else {
                        total = count / num;
                        totalPages = total + 1;
                    }
                    List<Integer> list = new LinkedList<>();
                    list.add(totalPages);
                    map.put("总共的页数", list);
                    List<Integer> list1 = new LinkedList<>();
                    list1.add(count);
                    map.put("总条数", list1);
                    int thePage = (pages - 1) * num;
                    List<User> list2 = userRepository.findUserByMajor(userMajor, userStatus, thePage, num);
                    map.put("查询信息", list2);
                    return Response.ok(map);
                } else return Response.error("请重新输入！");

            }
        }

        @Override
        public Response findUserByGradeAndFaculty(String userGrade, Integer userStatus, String userFaculty, Integer pages, Integer num) {
            Map<String, List> map = new HashMap<>();
            int totalPages;
            int total;
            if (userGrade == null || userStatus == null || userFaculty == null || pages == null || num == null) {
                return Response.error("空值异常！");
            }
            if (pages > 0 && num > 0) {
                int count = userRepository.findUserByGradeAndFacultyCounts(userGrade, userStatus, userFaculty);
                if (count % num == 0) {
                    totalPages = count / num;
                } else {
                    total = count / num;
                    totalPages = total + 1;
                }
                List<Integer> list = new LinkedList<>();
                list.add(totalPages);
                map.put("总共的页数", list);
                List<Integer> list1 = new LinkedList<>();
                list1.add(count);
                map.put("总条数", list1);
                int thePage = (pages - 1) * num;
                List<User> list2 = userRepository.findUserByGradeAndFaculty(userGrade, userStatus, userFaculty, thePage, num);
                map.put("查询信息", list2);
                return Response.ok(map);
            } else {
                return Response.error("请输入合理的数据！");
            }
        }

        @Override
        public Page<User> findUserLike(String userFaculty, String search, Integer pageNo, Integer pageSize) {
            List<User> list = new ArrayList<>();
            String meg = "";
            Integer count = userRepository.countUserLike(userFaculty, search);
    //        log.info("count:" + count);
            Integer pageTotal = 0;
            if (count == 0) {
                return Page.error(0, "无相关数据！");
            }
            if (count % pageSize != 0)
                pageTotal++;
            pageTotal += userRepository.countUserLike(userFaculty, search) / pageSize;
            if (pageNo > pageTotal) {
                meg = "查询失败,超出最大页码";
            } else if (pageNo <= 0) {
                meg = "查询失败,页码不存在";
            } else {
                int index = (pageNo - 1) * pageSize;
                list = userRepository.getUserLike(userFaculty, search, index, pageSize);
                meg = "查询成功";
                return new Page(1, meg, pageNo, pageSize, pageTotal, count, list);
            }
            return Page.error(0, meg);

        }


        @Override
        public Response findUserByGradeAndMajor(String userGrade, Integer userStatus, String userMajor, Integer pages, Integer num) {
            Map<String, List> map = new HashMap<>();
            int totalPages;
            int total;
            if (userGrade == null || userStatus == null || userMajor == null || pages == null || num == null) {
                return Response.error("空值异常！");
            }
            if (pages > 0 && num > 0) {
                int count = userRepository.findUserByGradeAndMajorCounts(userGrade, userStatus, userMajor);
                if (count % num == 0) {
                    totalPages = count / num;
                } else {
                    total = count / num;
                    totalPages = total + 1;
                }
                List<Integer> list = new LinkedList<>();
                list.add(totalPages);
                map.put("总共的页数", list);
                List<Integer> list1 = new LinkedList<>();
                list1.add(count);
                map.put("总条数", list1);
                int thePage = (pages - 1) * num;
                List<User> list2 = userRepository.findUserByGradeAndMajor(userGrade, userStatus, userMajor, thePage, num);
                map.put("查询信息", list2);
                return Response.ok(map);
            } else {
                return Response.error("请输入合理的数据！");
            }
        }

        @Override
        public ReturnUtil getResultTable() {
            List<Map<String, Object>> resultTable = userRepository.getResultTable();
            if (resultTable == null || resultTable.size() < 1) {
                return ReturnUtil.error("不存在符合条件的信息！");
            }
            return ReturnUtil.success("查询成功！", resultTable);
        }

        @Override
        public ReturnUtil getOneUserResultTable(String appStuID) {
            BigDecimal oneUserResultTable = userRepository.getOneUserResultTable(appStuID);
            if (oneUserResultTable == null) {
                return ReturnUtil.error("不存在符合条件的信息！");
            }
            return ReturnUtil.success("查询成功！", oneUserResultTable);
        }

        @Override
        public ReturnUtil getUserByUserNameAndUserFacultyAndAndUserCode(String userCode, String userFaculty) {
            User user = userRepository.getUserByUserCode(userCode);
            if (user == null) {
                return ReturnUtil.error("该用户不存在，请先添加该用户！");
            } else if (!(user.getUserFaculty().equals(userFaculty))) {
                return ReturnUtil.error("该用户已在" + user.getUserFaculty() + "存在！");
            }
            return ReturnUtil.success("该用户在本院存在存在！");
        }

        @Override
        public ReturnUtil resetPassword( Integer userId) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encode = bCryptPasswordEncoder.encode("123456");
            Integer integer = userRepository.resetPassword(encode,userId);
            if (integer == null || integer < 1) {
                return ReturnUtil.error("重置失败！");
            }
            return ReturnUtil.success("重置成功！");
        }
    }
