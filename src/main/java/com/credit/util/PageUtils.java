package com.credit.util;

import org.junit.Test;

import java.util.*;

public class PageUtils {

    /**
     * count为所要求分页的数据的总条数
     * pages为第几页
     * num为查询数量
     * */
    public static Map pagesUtils(int count,int pages,int num){
        Map<String, Integer> map = new HashMap<>();
        int totalPages;
        int total;
        if (count % num == 0) {
            totalPages = count / num;
        } else {
            total = count / num;
            totalPages = total + 1;
        }
        map.put("总共的页数", totalPages);
        map.put("总条数", count);
        int thePage = (pages - 1) * num;
        map.put("处理过后的limit第一个数字", thePage);
        return map;
    }

    /**
     * 集合分页
     * pages为第几页
     * num为查询数量
     * */
//    public static Map pagesList(ArrayList<com.credit.pojo.Test>list, int pages, int num){
//        Map<String, List> map = new HashMap<>();
//        List<com.credit.pojo.Test>list1=new LinkedList<>();
//        int count=list.size();
//        int totalPages;
//        int total;
//        if (count % num == 0) {
//            totalPages = count / num;
//        } else {
//            total = count / num;
//            totalPages = total + 1;
//        }
//        List<Integer>list2=new LinkedList<>();
//        list2.add(totalPages);
//        map.put("总共的页数为",list2);
//        int thePage = (pages - 1) * num;
//        for(int i=thePage;i<num;i++){
//            list1.add(list.get(i));
//        }
//        map.put("查询信息",list1);
//        return map;
//    }

    @Test
    public void test(){
        int count=9,pages=4,num=3;
        Map<String,Integer>map=pagesUtils(count,pages,num);
        int totalPages=map.get("总共的页数");
        int thePage=map.get("处理过后的limit第一个数字");
        System.out.println("总共的页数"+totalPages);
        System.out.println("处理过后的limit第一个数字"+thePage);
    }

//    @Test
//    public void test1(){
//        com.credit.pojo.Test test=new com.credit.pojo.Test(1,"d",1);
//        com.credit.pojo.Test test1=new com.credit.pojo.Test(2,"d",1);
//        com.credit.pojo.Test test2=new com.credit.pojo.Test(3,"d",1);
//        com.credit.pojo.Test test3=new com.credit.pojo.Test(4,"d",1);
//        com.credit.pojo.Test test4=new com.credit.pojo.Test(5,"d",1);
//        com.credit.pojo.Test test5=new com.credit.pojo.Test(6,"d",1);
//        List<com.credit.pojo.Test>list=new ArrayList<>();
//        list.add(test);
//        list.add(test1);
//        list.add(test2);
//        list.add(test3);
//        list.add(test4);
//        list.add(test5);
//     //   Map<String,List>map=pagesList(list,1,3);
//      //  System.out.println(map);
//    }
}
