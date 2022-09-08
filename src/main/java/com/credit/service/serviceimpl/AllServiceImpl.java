package com.credit.service.serviceimpl;

import com.credit.pojo.*;
import com.credit.repository.*;
import com.credit.service.AllService;
import com.credit.util.RecordUtil;
import com.credit.util.Response;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.credit.util.PageUtils.pagesUtils;

@Service
public class AllServiceImpl implements AllService {
    @Resource
    private MessagesRepository messagesRepository;

    @Resource
    private StandardRepository standardRepository;

    @Resource
    private ApplicationsRepository applicationsRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private InquiryRepository inquiryRepository;

    @Resource
    private PathsRepository pathsRepository;

    @Resource
    private RejectRepository rejectRepository;

    @Override
    public User findUserByCode(String userCode) {
        return userRepository.findUserByCode
                (userCode);
    }

    @Override
    public Response queryAppConcat(String thing, int pages, int num, int appPass) {
        if (pages <= 0 || num <= 0) {
            return Response.error("请输入合理的页数！");
        } else {
            int count = applicationsRepository.queryAppConcatCounts(thing, appPass);
            //     System.out.println("1111111"+count);
            Map<String, Integer> map = pagesUtils(count, pages, num);
            Map<String, List> total = new HashMap<>();
            int totalPages = map.get("总共的页数");
            int thePage = map.get("处理过后的limit第一个数字");
            List<Integer> listTotal = new LinkedList<>();
            listTotal.add(totalPages);
            List<Integer> listThe = new LinkedList<>();
            listThe.add(count);
            List<Applications> list = applicationsRepository.queryAppConcat(thing, thePage, num, appPass);
            //     System.out.println("333333"+list);
            total.put("查询信息", list);
            total.put("总共的页数", listTotal);
            total.put("总共的条数", listThe);
            //     System.out.println("22222222"+total);
            return Response.ok(total);
        }
    }

    /**
     * 申请表的类型=学分构成、申请表的类型(inType)+学分(inScore)、申请表的类型(inType)+申请人的院系(appAcademic),
     * 申请人的院系(appAcademic)+学分(inScore),申请人的院系(appAcademic)+认定范围(inScope)  查询申请表
     */
    @Override
    public Response queryAppByRe(String inType, String inScope, BigDecimal inScore, String appAcademic, int pages, int num, int appPass) {
        //申请表的类型(inType)+学分(inScore)
        if (inType != null && inScore != null && inScope == null && appAcademic == null) {
            List<Inquiry> inquiryList = inquiryRepository.findInByScoreAndType(inType, inScore);
            List<Applications> applicationsList = new LinkedList<>();
            for (Inquiry i : inquiryList) {
                int appId = i.getAppId();
                Applications applications = applicationsRepository.queryAppByIdAndPass(appId, appPass);
                if (applications != null) {
                    applicationsList.add(applications);
                }
            }
            Map<String, List> map = new HashMap<>();
            if (applicationsList.size() > num) {
                List<Applications> applicationsList1 = new LinkedList<>();
                int count = applicationsList.size();
                int totalPages;
                int total;
                if (count % num == 0) {
                    totalPages = count / num;
                } else {
                    total = count / num;
                    totalPages = total + 1;
                }
                List<Integer> list2 = new LinkedList<>();
                list2.add(totalPages);
                map.put("总共的页数为", list2);
                int thePage = (pages - 1) * num;
                for (int i = thePage; i < num; i++) {
                    applicationsList1.add(applicationsList.get(i));
                }
                map.put("查询信息", applicationsList1);
                List<Integer> listCounts = new LinkedList<>();
                listCounts.add(applicationsList1.size());
                map.put("总条数为", listCounts);
                return Response.ok(map);
            } else {
                List<Integer> stringList = new LinkedList<>();
                stringList.add(applicationsList.size());
                List<Integer> s = new LinkedList<>();
                s.add(1);
                map.put("总页数为", s);
                map.put("总条数为", stringList);
                map.put("查询信息", applicationsList);
                return Response.ok(map);
            }
        }
        //申请表的类型(inType)+申请人的院系(appAcademic)
        if (inType != null && inScore == null && inScope == null && appAcademic != null) {
            List<Inquiry> inquiryList = inquiryRepository.findInByType(inType);
            List<Applications> applicationsList = new LinkedList<>();
            for (Inquiry i : inquiryList) {
                int appId = i.getAppId();
                Applications applications = applicationsRepository.queryAppByIdAndPass(appId, appPass);
                if (applications != null) {
                    String appAcademic1 = applications.getAppAcademic();
                    if (appAcademic1.equals(appAcademic)) {
                        applicationsList.add(applications);
                    }
                }
            }
            Map<String, List> map = new HashMap<>();
            if (applicationsList.size() > num) {
                List<Applications> applicationsList1 = new LinkedList<>();
                int count = applicationsList.size();
                int totalPages;
                int total;
                if (count % num == 0) {
                    totalPages = count / num;
                } else {
                    total = count / num;
                    totalPages = total + 1;
                }
                List<Integer> list2 = new LinkedList<>();
                list2.add(totalPages);
                map.put("总共的页数为", list2);
                int thePage = (pages - 1) * num;
                for (int i = thePage; i < num; i++) {
                    applicationsList1.add(applicationsList.get(i));
                }
                map.put("查询信息", applicationsList1);
                List<Integer> listCounts = new LinkedList<>();
                listCounts.add(applicationsList1.size());
                map.put("总条数为", listCounts);
                return Response.ok(map);
            } else {
                List<Integer> stringList = new LinkedList<>();
                stringList.add(applicationsList.size());
                List<Integer> s = new LinkedList<>();
                s.add(1);
                map.put("总页数为", s);
                map.put("总条数为", stringList);
                map.put("查询信息", applicationsList);
                return Response.ok(map);
            }
        }
        //申请人的院系(appAcademic)+学分(inScore)
        if (inType == null && inScore != null && inScope == null && appAcademic != null) {
            List<Inquiry> inquiryList = inquiryRepository.findInByScore(inScore);
            List<Applications> applicationsList = new LinkedList<>();
            for (Inquiry i : inquiryList) {
                int appId = i.getAppId();
                Applications applications = applicationsRepository.queryAppByIdAndPass(appId, appPass);
                if (applications != null) {
                    String appAcademic1 = applications.getAppAcademic();
                    if (appAcademic1.equals(appAcademic)) {
                        applicationsList.add(applications);
                    }
                }
            }
            Map<String, List> map = new HashMap<>();
            if (applicationsList.size() > num) {
                List<Applications> applicationsList1 = new LinkedList<>();
                int count = applicationsList.size();
                int totalPages;
                int total;
                if (count % num == 0) {
                    totalPages = count / num;
                } else {
                    total = count / num;
                    totalPages = total + 1;
                }
                List<Integer> list2 = new LinkedList<>();
                list2.add(totalPages);
                map.put("总共的页数为", list2);
                int thePage = (pages - 1) * num;
                for (int i = thePage; i < num; i++) {
                    applicationsList1.add(applicationsList.get(i));
                }
                map.put("查询信息", applicationsList1);
                List<Integer> listCounts = new LinkedList<>();
                listCounts.add(applicationsList1.size());
                map.put("总条数为", listCounts);
                return Response.ok(map);
            } else {
                List<Integer> stringList = new LinkedList<>();
                stringList.add(applicationsList.size());
                List<Integer> s = new LinkedList<>();
                s.add(1);
                map.put("总页数为", s);
                map.put("总条数为", stringList);
                map.put("查询信息", applicationsList);
                return Response.ok(map);
            }
        }
        //申请人的院系(appAcademic)+认定范围(inScope)
        if (inType == null && inScore == null && inScope != null && appAcademic != null) {
            List<Inquiry> inquiryList = inquiryRepository.findInByScope(inScope);
            List<Applications> applicationsList = new LinkedList<>();
            for (Inquiry i : inquiryList) {
                int appId = i.getAppId();
                Applications applications = applicationsRepository.queryAppByIdAndPass(appId, appPass);
                if (applications != null) {
                    String appAcademic1 = applications.getAppAcademic();
                    if (appAcademic1.equals(appAcademic)) {
                        applicationsList.add(applications);
                    }
                }
            }
            Map<String, List> map = new HashMap<>();
            if (applicationsList.size() > num) {
                List<Applications> applicationsList1 = new LinkedList<>();
                int count = applicationsList.size();
                int totalPages;
                int total;
                if (count % num == 0) {
                    totalPages = count / num;
                } else {
                    total = count / num;
                    totalPages = total + 1;
                }
                List<Integer> list2 = new LinkedList<>();
                list2.add(totalPages);
                map.put("总共的页数为", list2);
                int thePage = (pages - 1) * num;
                for (int i = thePage; i < num; i++) {
                    applicationsList1.add(applicationsList.get(i));
                }
                map.put("查询信息", applicationsList1);
                List<Integer> listCounts = new LinkedList<>();
                listCounts.add(applicationsList1.size());
                map.put("总条数为", listCounts);
                return Response.ok(map);
            } else {
                List<Integer> stringList = new LinkedList<>();
                stringList.add(applicationsList.size());
                List<Integer> s = new LinkedList<>();
                s.add(1);
                map.put("总页数为", s);
                map.put("总条数为", stringList);
                map.put("查询信息", applicationsList);
                return Response.ok(map);
            }
        } else {
            return Response.error("此功能尚未开发！");
        }
    }

    @Override
    public List<Paths> queryPathByAppId(int appId) {
        return pathsRepository.queryPathByAppId(appId);
    }

    @Override
    public Response deletePathById(int id) {
        pathsRepository.deleteById(id);
        return Response.ok("删除成功！");
    }

    @Override
    public Response deletePathByAppId(int appId) {
        pathsRepository.deletePathByAppId(appId);
        return Response.ok("删除成功！");
    }

    @Override
    public Response updateStarById(String staName, Integer staId, BigDecimal staCredit, String staRemark) {
        BigDecimal b = new BigDecimal(0);
        if (staCredit.compareTo(b) < 1) return Response.error("学分不准为负数！");
        else {
            standardRepository.updateStaById(staName, staId, staCredit, staRemark);
            return Response.ok(standardRepository.queryByID(staId));
        }
    }

    @Override
    public Response queryUserByPassTwoConcat(Integer pages, Integer num, String thing) {
        // 查找全部用户
        List<Applications> appList = applicationsRepository.findAllAppTwoConcat(thing);
        Set<String> appSet = new HashSet<>();
        for (int i = 0; i < appList.size(); i++) {
            appSet.add(appList.get(i).getAppStuID());
        }
        List<User> userList = new LinkedList<>();
        for (String b : appSet) {
            User user = userRepository.findUserByCode(b);
            if (user != null) {
                userList.add(user);
            }
        }
        Map<String, List> map = new HashMap<>();
        if (userList.size() > num) {
            List<User> listIn = new LinkedList<>();
            int count = userList.size();
            int totalPages;
            int total;
            if (count % num == 0) {
                totalPages = count / num;
            } else {
                total = count / num;
                totalPages = total + 1;
            }
            List<Integer> list2 = new LinkedList<>();
            list2.add(totalPages);
            map.put("总共的页数为", list2);
            int thePage = (pages - 1) * num;
            for (int i = thePage; i < num; i++) {
                listIn.add(userList.get(i));
            }
            map.put("查询信息", listIn);
            List<Integer> listCounts = new LinkedList<>();
            listCounts.add(userList.size());
            map.put("总条数为", listCounts);
            return Response.ok(map);
        } else {
            List<Integer> stringList = new LinkedList<>();
            stringList.add(userList.size());
            map.put("总条数为", stringList);
            map.put("查询信息", userList);
            List<Integer> list2 = new LinkedList<>();
            list2.add(1);
            map.put("总共的页数为", list2);
            return Response.ok(map);
        }
    }

    @Override
    public Response findMessagesPages(int pages, int num) {
        int count = messagesRepository.findMessagesCounts();
        Map<String, Integer> map = pagesUtils(count, pages, num);
        Map<String, List> total = new HashMap<>();
        int totalPages = map.get("总共的页数");
        int thePage = map.get("处理过后的limit第一个数字");
        List<Integer> listTotal = new LinkedList<>();
        listTotal.add(totalPages);
        List<Integer> listThe = new LinkedList<>();
        listThe.add(count);
        List<Messages> list = messagesRepository.findMessagesPages(thePage, num);
        total.put("查询信息", list);
        total.put("总共的页数", listTotal);
        total.put("总共的条数", listThe);
        return Response.ok(total);
    }

    @Override
    public Response updateAppTypeById(String appType, int appId) {
        applicationsRepository.updateAppTypeById(appType, appId);
        return Response.ok("修改成功！");
    }

    @Override
    public Response queryScoreMaxByStuId(String appStuID) {
        List<Applications> appList = applicationsRepository.queryScoreHe(appStuID);
        Map<String, BigDecimal> map = new HashMap<>();
        for (Applications a : appList) {
            String appType = a.getAppType();
            BigDecimal appAcademicScore = a.getAppAcademicScore();
            if (map.containsKey(appType)) {
                BigDecimal value = map.get(appType);
                BigDecimal v = value.add(appAcademicScore);
                map.put(appType, v);
            } else {
                map.put(appType, appAcademicScore);
            }
        }
        if (map != null) {
            Map<String, BigDecimal> map1 = new HashMap<>();
            String str = "";
            BigDecimal b = new BigDecimal(0);
            int length = map.size();
            String[] strings = new String[length];
            int a = 0;
            for (String key : map.keySet()) {
                strings[a] = key;
                a++;
            }
            for (int i = 0; i < length; i++) {
                if (b.compareTo(map.get(strings[i])) < 0) {
                    b = map.get(strings[i]);
                    str = strings[i];
                }
            }
            map1.put(str, b);
            return Response.ok(map1);
        } else {
            return Response.ok(map);
        }
    }

    @Override
    public Response queryStaMaxCredit(String type) {
        List<Standard> list=standardRepository.queryByGrade(1);
        int flag=0;
        for(int o=0;o<list.size();o++){
            if(type.equals(list.get(o).getStaName()))flag=1;
        }
        if(flag==0)return Response.error("不存在此一级指标！");
        else {
            Map<String, List> map = queryDownStarName(type);
            List<BigDecimal> bigDecimals = new LinkedList<>();
            for (String key : map.keySet()) {
                List<Standard> standardList = map.get(key);
                if (!standardList.isEmpty()) {
                    for (int i = 0; i < standardList.size(); i++) {
                        if (standardList.get(i).getStaCredit() != null) {
                            bigDecimals.add(standardList.get(i).getStaCredit());
                        }
                    }
                }
            }
            BigDecimal b = new BigDecimal(0);
            for (int a = 0; a < bigDecimals.size(); a++) {
                if (b.compareTo(bigDecimals.get(a)) < 0) {
                    b = bigDecimals.get(a);
                }
            }
            return Response.ok(b);
        }
    }

    public Map queryDownStarName(String staName) {
        Map<String,List>map=new HashMap<>();
        Standard staOne=standardRepository.queryDownStarName(staName);
        if(staOne != null) {
            int num = 1;
            Integer id = staOne.getStaId();
            List<Standard> standardList = standardRepository.queryByFatherID(id);
            if (!standardList.isEmpty()) {
                num++;
                map.put("" + num, standardList);
            }
            while (!standardList.isEmpty()) {
                List<Integer> integerList = new LinkedList<>();
                for (int i = 0; i < standardList.size(); i++) {
                    Standard standard = standardList.get(i);
                    int sId = standard.getStaId();
                    integerList.add(sId);
                }
                if (!integerList.isEmpty()) {
                    List<Standard> standardList1 = new LinkedList<>();
                    for (int i = 0; i < integerList.size(); i++) {
                        int sId = integerList.get(i);
                        List<Standard> standardList3 = standardRepository.queryByFatherID(sId);
                        if (!standardList3.isEmpty()) {
                            for (int a = 0; a < standardList3.size(); a++) {
                                Standard standard = standardList3.get(a);
                                standardList1.add(standard);
                            }
                        }
                    }
                    num++;
                    map.put("" + num, standardList1);
                    standardList = standardList1;
                } else {
                    standardList = null;
                }
            }
            return map;
        }else{
            return map;
        }
    }

    @Override
    public Map findRejectPages(String code, int pages, int num) {
        Map<String, List> map = new HashMap<>();
        int totalPages;//总共的页数
        int total;
        int count = rejectRepository.findRejectPagesCounts(code);//查询总共的数量
        if (count % num == 0) {
            totalPages = count / num;
        }else {
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
        List<Reject> list2 = rejectRepository.findRejectPages(code,thePage,num);
        map.put("查询信息", list2);
        return map;
    }

    @Override
    public Reject addReject(Integer appId,String code,String reason) {
        //使用Date创建日期对象
        Date date = new Date();
        /**
         * 创建格式化时间日期类
         *构造入参String类型就是我们想要转换成的时间形式
         */
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        //      System.out.println("格式化后的时间------->"+format.format(date));
        Reject reject=rejectRepository.findRejectByAppID(appId);
        if(reject==null) {
            Reject reject1 = new Reject(appId, code, reason, format.format(date));
            return rejectRepository.save(reject1);
        }else{
            rejectRepository.updateAppPassById(code,appId,reason,format.format(date));
            return rejectRepository.findRejectByAppID(appId);
        }
    }

    @Override
    public Map queryAppByCodeAndPass(String code, Integer appPass, int pages, int num) {
        // 查找全部用户,使用集合来接收
        List<Applications> userList = applicationsRepository.queryAppByCodeAndPass(code,appPass);
        Map<String, List> map = new HashMap<>();
        if (userList.size() > num) {
            List<Applications> listIn = new LinkedList<>();//用来存放分页后获取的数据
            int count = userList.size();//集合中数据总数量
            int totalPages;
            int total;
            if (count % num == 0) {
                totalPages = count / num;
            } else {
                total = count / num;
                totalPages = total + 1;
            }
            List<Integer> list2 = new LinkedList<>();
            list2.add(totalPages);
            map.put("总共的页数为", list2);
            int thePage = (pages - 1) * num;
            //使用listIn来存放分页查询数据
            for (int i = thePage; i < num; i++) {
                listIn.add(userList.get(i));
            }
            map.put("查询信息", listIn);
            List<Integer> listCounts = new LinkedList<>();
            listCounts.add(userList.size());
            map.put("总条数为", listCounts);
            return map;
        } else {
            List<Integer> integerList = new LinkedList<>();
            integerList.add(userList.size());
            map.put("总条数为", integerList);
            map.put("查询信息", userList);
            List<Integer> list2 = new LinkedList<>();
            list2.add(1);
            map.put("总共的页数为", list2);
            return map;
        }
    }

    @Override
    public Standard queryStaById(Integer id) {
        return standardRepository.findById(id).orElse(null);
    }

    @Override
    public Response deleteReject(int appId) {
        rejectRepository.deleteReject(appId);
        return Response.ok("success!");
    }

    @Override
    public Response findUserByAppPass(int appPass, int pages, int num) {
        Map<String, List> map = new HashMap<>();
        int totalPages;
        int total;
        int count = applicationsRepository.queryAppAppPassCounts(appPass);
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
        List<Applications> list2 = applicationsRepository.findUserByAppPass(appPass, thePage, num);
        map.put("查询信息", list2);
        return Response.ok(map);
    }

    @Override
    public Response queryAppByTime(String appTime, Integer appPass, Integer pages, Integer num) {
        if (pages <= 0 || num <= 0) {
            return Response.error("请输入合理的页数！");
        } else {
            int count = applicationsRepository.queryAppByTimeCounts(appTime, appPass);
            Map<String, Integer> map = pagesUtils(count, pages, num);
            int totalPages = map.get("总共的页数");
            int thePage = map.get("处理过后的limit第一个数字");
            Map<String, List> map1 = new HashMap<>();
            List<Applications> list = applicationsRepository.queryAppByTime(appTime, appPass, thePage, num);
            List<Integer> list1 = new LinkedList<>();
            list1.add(totalPages);
            List<Integer> list2 = new LinkedList<>();
            list2.add(count);
            map1.put("查询信息为", list);
            map1.put("总共的页数为", list1);
            map1.put("总条数为", list2);
            return Response.ok(map1);
        }
    }

    @Override
    public Response deleteAppByList(List<Integer> list) {
        for(int appId:list){
            pathsRepository.deleteByAppId(appId);
            applicationsRepository.deleteApplications(appId);
            rejectRepository.deleteReject(appId);
        }
        return Response.ok("success!");
    }

    @Override
    public Response queryMessagesByOrganizeMo(String thing, int pages, int num) {
        if(pages<=0||num<=0){
            return Response.error("请输入合理的页数1");
        }else {
            Map<String, List> map = new HashMap<>();
            int totalPages;//总共的页数
            int total;
            int count = messagesRepository.queryMessagesByOrganizeMoCounts(thing);//查询总共的数量
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
            List<Messages> list2 = messagesRepository.queryMessagesByOrganizeMo(thing, thePage, num);//@Query(value = "select * from applications limit ?2,?3 ", nativeQuery = true)
            map.put("查询信息", list2);
            return Response.ok(map);
        }
    }
}
