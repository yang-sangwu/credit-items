package com.credit.service;

import com.credit.util.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface StandardService {
    ReturnUtil addOne(String staName, Integer staGrade, Integer staFatherid, BigDecimal staCredit, String staRemark);

    ReturnUtil getAllFirst();

    ReturnUtil updateFirst(@Param("staId") Integer staId, @Param("staName") String staName);

    ReturnUtil deleteFirst(@Param("staId") Integer staId);

    ReturnUtil addSecondMore(@Param("staName") String staName, @Param("staGrade") Integer staGrade, @Param("staFatherid") Integer staFatherid);

    ReturnUtil addThirdMore(@Param("staName") String staName, @Param("staGrade") Integer staGrade, @Param("staFatherid") Integer staFatherid,@Param("staCredit")BigDecimal staCredit,@Param("staRemark") String staRemark);


//    ReturnUtil findStandardOne(String staName);
}
