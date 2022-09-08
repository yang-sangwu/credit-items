package com.credit.service.serviceimpl;

import com.credit.pojo.Applications;
import com.credit.pojo.Inquiry;
import com.credit.pojo.User;
import com.credit.repository.ApplicationsRepository;
import com.credit.repository.InquiryRepository;
import com.credit.repository.UserRepository;
import com.credit.service.InquiryService;
import com.credit.util.Response;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.credit.util.PageUtils.pagesUtils;

@Service
public class InquiryServiceImpl implements InquiryService {
    @Resource
    private InquiryRepository inquiryRepository;

    @Resource
    private ApplicationsRepository applicationsRepository;

    @Resource
    private UserRepository userRepository;


    @Override
    public Response insertInquiry(Inquiry inquiry) {
        int appId=inquiry.getAppId();
        Inquiry inquiry1=inquiryRepository.findInByAppId(appId);
        if(inquiry1 != null){
            String inType=inquiry.getInType();
            String inScope=inquiry.getInScope();
            BigDecimal inScore=inquiry.getInScore();
            inquiryRepository.updateInByAppId(inType,inScope,inScore,appId);
            return Response.ok("success!");
        }else {
            inquiryRepository.save(inquiry);
            return Response.ok("success!");
        }
    }

    @Override
    public Inquiry updateInquiry(Inquiry inquiry) {
        return inquiryRepository.save(inquiry);
    }

    @Override
    public Inquiry queryInquiryByID(int inId) {
        return inquiryRepository.findById(inId).orElse(null);
    }

    @Override
    public Response queryRequirement(String inType, String inScope, BigDecimal inScore, String appAcademic, Integer pages, Integer num,int appPass) {
        if(inType==null&&inScope==null&&inScore==null&&appAcademic==null){
            return Response.error("空值异常！");
        }
        //通过院系分页查询通过的申请表
        if(inType==null&&inScope==null&&inScore==null&&appAcademic!=null){
            int count=applicationsRepository.queryAppByAcademicCounts(appAcademic,appPass);
            Map<String,Integer> map=pagesUtils(count,pages,num);
            Map<String,List>total=new HashMap<>();
            int totalPages=map.get("总共的页数");
            int thePage=map.get("处理过后的limit第一个数字");
            List<Integer>listTotal=new LinkedList<>();
            listTotal.add(totalPages);
            List<Integer>listThe=new LinkedList<>();
            listThe.add(count);
            List<Applications>list=applicationsRepository.queryAppByAcademic(appAcademic,appPass,thePage,num);
            total.put("查询信息",list);
            total.put("总共的页数",listTotal);
            total.put("总共的条数",listThe);
            return Response.ok(total);
        }//通过申请表类型分页查询
        if(inType!=null&&inScope==null&&inScore==null&&appAcademic==null){
            List<Inquiry>list=inquiryRepository.findInByType(inType);
            List<Applications>list1=new LinkedList<>();
            for(int i=0;i<list.size();i++){
                Inquiry inquiry=list.get(i);
                int appId=inquiry.getAppId();
                Applications applications=applicationsRepository.queryAppByIdAndPass(appId,appPass);
                if(applications != null) {
                    list1.add(applications);
                }
            }
        //    System.out.println(list1);
            if(list1.size()>num) {
                Map<String, List> map = new HashMap<>();
                List<Applications> listIn = new LinkedList<>();
                int count = list1.size();
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
                    listIn.add(list1.get(i));
                }
                map.put("查询信息", listIn);
                List<Integer> listCounts = new LinkedList<>();
                listCounts.add(list1.size());
                map.put("总条数为", listCounts);
                return Response.ok(map);
            }else{
                Map<String, List> map = new HashMap<>();
                List<Integer>stringList=new LinkedList<>();
                stringList.add(list1.size());
                map.put("总条数为",stringList);
                map.put("查询信息",list1);
                List<Integer>stringList1=new LinkedList<>();
                stringList1.add(1);
                map.put("总页数为",stringList1);
                return Response.ok(map);
            }

        }if(inType==null&&inScope!=null&&inScore==null&&appAcademic==null){
            List<Inquiry>list=inquiryRepository.findInByScope(inScope);
            List<Applications>list1=new LinkedList<>();
            for(int i=0;i<list.size();i++){
                Inquiry inquiry=list.get(i);
                int appId=inquiry.getAppId();
                Applications applications=applicationsRepository.queryAppByIdAndPass(appId,appPass);
                if(applications != null) {
                    list1.add(applications);
                }
            }
            if(list1.size()>num) {
                Map<String, List> map = new HashMap<>();
                List<Applications> listIn = new LinkedList<>();
                int count = list1.size();
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
                    listIn.add(list1.get(i));
                }
                map.put("查询信息", listIn);
                List<Integer> listCounts = new LinkedList<>();
                listCounts.add(list1.size());
                map.put("总条数为", listCounts);
                return Response.ok(map);
            }else{
                Map<String, List> map = new HashMap<>();
                List<Integer>stringList=new LinkedList<>();
                stringList.add(list1.size());
                map.put("总条数为",stringList);
                map.put("查询信息",list1);
                List<Integer>stringList1=new LinkedList<>();
                stringList1.add(1);
                map.put("总页数为",stringList1);
                return Response.ok(map);
            }
        }
        if(inType==null&&inScope==null&&inScore!=null&&appAcademic==null){
            List<Inquiry>list=inquiryRepository.findInByScore(inScore);
            List<Applications>list1=new LinkedList<>();
            for(int i=0;i<list.size();i++){
                Inquiry inquiry=list.get(i);
                int appId=inquiry.getAppId();
                Applications applications=applicationsRepository.queryAppByIdAndPass(appId,appPass);
                if(applications != null) {
                    list1.add(applications);
                }
            }
            if(list1.size()>num) {
                Map<String, List> map = new HashMap<>();
                List<Applications> listIn = new LinkedList<>();
                int count = list1.size();
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
                    listIn.add(list1.get(i));
                }
                map.put("查询信息", listIn);
                List<Integer> listCounts = new LinkedList<>();
                listCounts.add(list1.size());
                map.put("总条数为", listCounts);
                return Response.ok(map);
            }else{
                Map<String, List> map = new HashMap<>();
                List<Integer>stringList=new LinkedList<>();
                stringList.add(list1.size());
                map.put("总条数为",stringList);
                map.put("查询信息",list1);
                List<Integer>stringList1=new LinkedList<>();
                stringList1.add(1);
                map.put("总页数为",stringList1);
                return Response.ok(map);
            }
        }
        else{
            return Response.ok("当前功能尚未开发！");
        }
    }

    @Override
    public Response queryAppByAcademicAndPass(String appAcademic, Integer appPass, Integer pages, Integer num) {
        if(appAcademic==null||appPass==null||pages==null||num==null){
            return Response.error("空值异常！");
        }
        if(pages<=0||num<=0){
            return Response.error("请输入合理的页数！");
        }else {
            int count = applicationsRepository.queryAppByAcademicCounts(appAcademic, appPass);
            Map<String, Integer> map = pagesUtils(count, pages, num);
            Map<String, List> total = new HashMap<>();
            int totalPages = map.get("总共的页数");
            int thePage = map.get("处理过后的limit第一个数字");
            List<Integer> listTotal = new LinkedList<>();
            listTotal.add(totalPages);
            List<Integer> listThe = new LinkedList<>();
            listThe.add(count);
            List<Applications> list = applicationsRepository.queryAppByAcademic(appAcademic, appPass, thePage, num);
     //       System.out.println("111111"+list);
            total.put("查询信息", list);
            total.put("总共的页数", listTotal);
            total.put("总共的条数", listThe);
            return Response.ok(total);
        }
    }

    @Override
    public Response queryUserConcat(String thing,Integer pages,Integer num) {
        if(pages<=0||num<=0){
            return Response.error("请输入合理的页数！");
        }else {
            int count = userRepository.queryUserConcatCounts(thing);
            Map<String, Integer> map = pagesUtils(count, pages, num);
            Map<String, List> total = new HashMap<>();
            int totalPages = map.get("总共的页数");
            int thePage = map.get("处理过后的limit第一个数字");
            List<Integer> listTotal = new LinkedList<>();
            listTotal.add(totalPages);
            List<Integer> listThe = new LinkedList<>();
            listThe.add(count);
            List<User> list = userRepository.queryUserConcat(thing,thePage,num);
            total.put("查询信息", list);
            total.put("总共的页数", listTotal);
            total.put("总共的条数", listThe);
            return Response.ok(total);
        }
    }

    @Override
    public Response updateUserPassAndUserRecognize(int appPass, String userRecognize,String userRemark,int appId,BigDecimal appAcademicScore) {
        applicationsRepository.updateAppPassById(appPass,appId,userRemark,userRecognize,appAcademicScore);
    //    userRepository.updateUserPassAndUserRecognize(userCode,appAcademicScore);
        return Response.ok("修改成功！");
    }

    @Override
    public Inquiry findInByAppId(Integer appID) {
        return inquiryRepository.findInByAppId(appID);
    }

}
