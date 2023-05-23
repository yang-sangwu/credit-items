package com.credit;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;


@SpringBootTest
class CreditApplicationTests {

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void testPassword() {
//        spring.datasource.url=jdbc:mysql://110.40.212.128/credit?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
//        spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
//        spring.datasource.username=root
//        spring.datasource.password=123456
        String username = stringEncryptor.encrypt("root");
        String password = stringEncryptor.encrypt("123456");

        System.out.println("username:"+username);
        System.out.println("password:"+password);
    }

}
