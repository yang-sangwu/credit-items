package com.credit.repository;

import com.credit.pojo.Standard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface StandardRepository extends JpaRepository<Standard, Integer>, JpaSpecificationExecutor {
    /**
     *查询学分类型（通过查询的指标遍历其下的全部附属指标）
     */
    @Query(value="select * from standard where sta_name=?",nativeQuery = true)
    Standard queryDownStarName(String staName);

    /**
     *根据父级id查询
     */
    @Query(value="select * from standard where sta_fatherid=?",nativeQuery = true)
    List<Standard> queryByFatherID(Integer staFatherid);

    /**
     *根据sta_fatherid查询学分
     */
    @Query(value="select sta_credit from standard where sta_fatherid=?",nativeQuery = true)
    List<BigDecimal> queryCreditById(Integer id);

    /**
     * 删除已经添加过的指标信息
     */
    @Query(value="delete from standard where sta_fatherid=?", nativeQuery = true)
    void deleteStaByFatherId(int staFatherId);

    Standard findByStaName(String staName);

    /**
     * 根据id修改
     **/
    @Transactional
    @Modifying
    @Query(value = "update standard set sta_name=?1 ,sta_credit=?3,sta_remark=?4 where sta_id=?2", nativeQuery = true)
    void updateStaById(String staName,Integer staId,BigDecimal staCredit,String staRemark);

    /**
     *根据id查询
     */
    @Query(value="select * from standard where sta_id=?",nativeQuery = true)
    Standard queryByID(Integer staId);

    /**
     *根据等级查询
     */
    @Query(value="select * from standard where sta_grade=?",nativeQuery = true)
    List<Standard> queryByGrade(Integer staGrade);
}
