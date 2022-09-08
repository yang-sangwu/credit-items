package com.credit.dao;

import com.credit.pojo.Standard;
import com.credit.util.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Mapper
public interface StandardDao {
    //    查询一级学分类型
    List<Type> getAllFirst();

    //    单条添加某级学分类型
    Integer addOne(@Param("staName") String staName, @Param("staGrade") Integer staGrade, @Param("staFatherid") Integer staFatherid, @Param("staCredit") BigDecimal staCredit, @Param("staRemark") String staRemark);

    List<Standard> existName(String staName);

    //    批量添加子集学分类型
    Integer addSecondMore(@Param("staName") String staName, @Param("staGrade") Integer staGrade, @Param("staFatherid") Integer staFatherid);

    Integer addThirdMore(@Param("staName") String staName, @Param("staGrade") Integer staGrade, @Param("staFatherid") Integer staFatherid,@Param("staCredit")BigDecimal staCredit,@Param("staRemark") String staRemark);

    //    修改一级学分类型
    Integer updateFirst(@Param("staId") Integer staId, @Param("staName") String staName);

    //    删除一级学分类型
    Integer deleteFirst(@Param("staId") Integer staId);

    Integer getGrade(@Param("staId") Integer staId);
}
