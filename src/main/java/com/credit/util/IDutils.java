package com.credit.util;


import com.credit.pojo.Applications;
import com.credit.pojo.User;
import org.junit.Test;

import java.util.*;

@SuppressWarnings("all")
public class IDutils {

    public static String getUUID32(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * (获取指定长度uuid)
     */
    public static String getUUID(int len)
    {
        if(len<=0) {
            return null;
        }
        String uuid = getUUID32();
    //    System.out.println(uuid);
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < len; i++) {
            str.append(uuid.charAt(i));
        }
        return str.toString();
    }

    @Test
    public void test(){
        System.out.println("密码："+IDutils.getUUID(12));
        System.out.println("密码："+IDutils.getUUID(16));
        System.out.println("密码："+IDutils.getUUID(24));
    }
}