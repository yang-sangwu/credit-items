package com.credit.service.serviceimpl;

import com.credit.pojo.Applications;
import com.credit.pojo.Messages;
import com.credit.pojo.Standard;
import com.credit.pojo.User;
import com.credit.repository.ApplicationsRepository;
import com.credit.repository.MessagesRepository;
import com.credit.repository.StandardRepository;
import com.credit.repository.UserRepository;
import com.credit.service.MessagesService;
import com.credit.util.Response;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class MessagesServiceImpl implements MessagesService {

    @Resource
    private MessagesRepository messagesRepository;

    @Resource
    private StandardRepository standardRepository;

    @Resource
    private ApplicationsRepository applicationsRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    public Messages addMessages(Messages messages) {
        return messagesRepository.save(messages);
    }

    @Override
    public Messages findMessagesByOrganize(String organize) {
        return messagesRepository.findMessagesByOrganize(organize);
    }

    @Override
    public Response queryDownStarName(String staName) {
        Map<String,List>map=new HashMap<>();
        Standard staOne=standardRepository.queryDownStarName(staName);
        if(staOne != null) {
            List<Standard> list = new LinkedList<>();
            list.add(staOne);
            map.put("1", list);
            int num = 1;
            Integer id = staOne.getStaId();
            //    System.out.println("1111111111"+id);
            List<Standard> standardList = standardRepository.queryByFatherID(id);
            //      System.out.println("2222222222"+standardList);
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
                //      System.out.println("333333333"+integerList);
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
                    //      System.out.println("4444444444" + standardList1);
                    standardList = standardList1;
                } else {
                    standardList = null;
                }
                //      standardList=standardList1;
            }
            return Response.ok(map);
        }else{
            return Response.error("不存在此学分类型！");
        }
    }

    @Override
    public Response updateAppPassAndScore(Integer appPass, BigDecimal appAcademicScore, Integer appId) {
        messagesRepository.updateAppPassAndScore(appPass,appAcademicScore,appId);
        return Response.ok("修改成功！");
    }

    @Override
    public Response deleteStaNameBath(List<Integer> list) {
        for(int i=0;i<list.size();i++){
            int one=list.get(i);  //一级   one=29
            System.out.println("one"+one);
            List<Standard>list1=standardRepository.queryByFatherID(one);  //list1为从一级那里获取到的二级 list1中是41
            while(! list1.isEmpty()){
                List<Integer>list2=new LinkedList<>();
                for(int a=0;a<list1.size();a++){
                    int three=list1.get(a).getStaId();
                    list2.add(three);
                }                   //list2为从二级那里获取的id 即附属指标的父id  list2中是41  list2中是42
                List<Standard>list4=new LinkedList<>();
                for(int a=0;a<list2.size();a++){
                    int four=list2.get(a);
                    List<Standard>list3=standardRepository.queryByFatherID(four); //检测是否还存在三级指标
                    System.out.println("2222222222222"+list3);   //list3中是42
                    if(! list3.isEmpty()){  //存在三级指标
                        for(int b=0;b<list3.size();b++){
                            Standard standard=list3.get(b);
                            list4.add(standard);
                        }
                    }
                }
                if(! list1.isEmpty()) {
                    for (int d = 0; d < list1.size(); d++) {
                        int id=list1.get(d).getStaId();
                        System.out.println("id"+id);
                        standardRepository.deleteById(id);
                    }
                }    //list1中的41删了
                if(!list4.isEmpty()){
                    list1=list4;
                }if(list4.isEmpty()){
                    List<Standard>boss=new LinkedList<>();
                    list1=boss;
                }
            }
            standardRepository.deleteById(one);
        }
        return Response.ok("删除成功！");
    }

    @Override
    public Response queryUserByPassTwo(Integer pages, Integer num) {
        if(pages==null||num==null){
            return Response.error("空值异常！");
        }
        if(pages<=0||num<=0){
            return Response.error("请输入合理的页数！");
        }else {
            // 查找全部用户
            List<Applications> appList = applicationsRepository.findAllAppTwo();
            Set<String> appSet = new HashSet<>();
            for (int i = 0; i < appList.size(); i++) {
                appSet.add(appList.get(i).getAppStuID());
            }
            List<User> userList = new LinkedList<>();
            for (String b : appSet) {
                userList.add(userRepository.findUserByCode(b));
            }
            Map<String, List> map = new HashMap<>();
            if(userList.size()>num) {
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
            }else{
                List<Integer>stringList=new LinkedList<>();
                stringList.add(userList.size());
                map.put("总条数为",stringList);
                map.put("查询信息",userList);
                List<Integer> list2 = new LinkedList<>();
                list2.add(1);
                map.put("总共的页数为", list2);
                return Response.ok(map);
            }
        }
    }

    @Override
    public Response queryScoreHe(String appStuID) {
        List<Applications>appList=applicationsRepository.queryScoreHe(appStuID);
        Map<String,BigDecimal>map=new HashMap<>();
        for(Applications a : appList){
            String appType=a.getAppType();
            BigDecimal appAcademicScore=a.getAppAcademicScore();
            if(map.containsKey(appType)){
                BigDecimal value=map.get(appType);
                BigDecimal v=value.add(appAcademicScore);
                map.put(appType,v);
            }else{
                map.put(appType,appAcademicScore);
            }
        }
        return Response.ok(map);
    }

    @Override
    public Response addMessagesUp(Messages messages) {
        String organize=messages.getOrganize();
        Messages messages1=messagesRepository.findMessagesByOrganize(organize);
        if(messages1 ==null){
            return Response.ok(messagesRepository.save(messages));
        }else{
            return Response.error("此组织已存在！");
        }
    }
}
