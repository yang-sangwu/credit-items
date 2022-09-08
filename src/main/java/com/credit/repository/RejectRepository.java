package com.credit.repository;

import com.credit.pojo.Reject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface RejectRepository extends JpaRepository<Reject, Integer>, JpaSpecificationExecutor {
    /**
     * 分页查询指定组织的基本信息
     */
    @Query(value = "select * from reject where code=?1 limit ?2,?3", nativeQuery = true)
    List<Reject> findRejectPages(String code,int pages, int num);

    /**
     * 分页查询指定组织的基本信息数量
     */
    @Query(value = "select count(*) from reject where code=?", nativeQuery = true)
    int findRejectPagesCounts(String code);

    /**
     * 根据appId查询驳回信息
     */
    @Query(value = "select * from reject where app_id=?", nativeQuery = true)
    Reject findRejectByAppID(int appId);

    /**
     * 根据学号修改申请表表通过状态
     **/
    @Transactional
    @Modifying
    @Query(value = "update reject set code=?1 ,reason=?3 ,times=?4 where app_id=?2", nativeQuery = true)
    void updateAppPassById(String code, int appId, String reason, String times);

    /**
     * 根据学号修改申请表表通过状态
     **/
    @Transactional
    @Modifying
    @Query(value = "delete from reject where app_id=?", nativeQuery = true)
    void deleteReject(int appId);

}
