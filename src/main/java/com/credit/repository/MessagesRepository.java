package com.credit.repository;

import com.credit.pojo.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

public interface MessagesRepository extends JpaRepository<Messages, Integer>, JpaSpecificationExecutor {
    /**
     * 查询指定组织的基本信息
     */
    @Query(value = "select * from messages where organize=?1", nativeQuery = true)
    Messages findMessagesByOrganize(String organize);

    /**
     * 修改申请表通过状态+学分认证
     **/
    @Transactional
    @Modifying
    @Query(value = "update applications set app_pass=?1 ,app_academic_score=?2 where app_id=?3", nativeQuery = true)
    void updateAppPassAndScore(int appPass, BigDecimal appAcademicScore,int appId);

    /**
     * 查询指定组织的基本信息Count
     */
    @Query(value = "select count(*) from messages", nativeQuery = true)
    int findMessagesCounts();

    /**
     * 分页查询指定组织的基本信息
     */
    @Query(value = "select * from messages limit ?1,?2", nativeQuery = true)
    List<Messages> findMessagesPages(int pages,int num);

    /**
    分页模糊查询组织信息
    * **/
    @Query(value = "select * from messages where concat(organize) like concat('%',?1,'%') limit ?2,?3", nativeQuery = true)
    List<Messages>queryMessagesByOrganizeMo(String thing,int pages,int num);

    /**
     分页模糊查询组织信息counts
     * **/
    @Query(value = "select count(*) from messages where concat(organize) like concat('%',?,'%')", nativeQuery = true)
    int queryMessagesByOrganizeMoCounts(String thing);
}
