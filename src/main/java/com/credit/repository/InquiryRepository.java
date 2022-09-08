package com.credit.repository;

import com.credit.pojo.Applications;
import com.credit.pojo.Inquiry;
import com.credit.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Integer>, JpaSpecificationExecutor {

    /**
     * 根据申请表类型或学分构成查询表（普通管理员审核申请表时应该填写的）
     * */
    @Query(value="select * from inquiry where in_type=?", nativeQuery = true)
    List<Inquiry> findInByType(String inType);

    /**
     * 根据认定范围查询表（普通管理员审核申请表时应该填写的）
     * */
    @Query(value="select * from inquiry where in_scope=?", nativeQuery = true)
    List<Inquiry>findInByScope(String inScope);

    /**
     * 根据学分查询表（普通管理员审核申请表时应该填写的）
     * */
    @Query(value="select * from inquiry where in_score=?", nativeQuery = true)
    List<Inquiry>findInByScore(BigDecimal inScore);

    /**
     * 根据学分和申请表类型查询表
     * */
    @Query(value="select * from inquiry where in_type=?1 and in_score=?2", nativeQuery = true)
    List<Inquiry>findInByScoreAndType(String inType,BigDecimal inScore);

    /**
     * 根据申请表id查询inquiry
     * */
    @Query(value="select * from inquiry where app_id=?", nativeQuery = true)
    Inquiry findInByAppId(Integer appId);

    /**
     * 修改inquiry
     **/
    @Transactional
    @Modifying
    @Query(value = "update inquiry set in_type=?1,in_scope=?2,in_score=?3 where app_id=?4", nativeQuery = true)
    void updateInByAppId(String inType,String inScope,BigDecimal inScore,Integer appId);
}
