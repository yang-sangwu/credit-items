package com.credit.repository;

import com.credit.pojo.Paths;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PathsRepository extends JpaRepository<Paths, Integer>, JpaSpecificationExecutor {

    /**
     * 根据申请表id删除附件
     */
    @Transactional
    @Modifying
    @Query(value = "delete from paths where app_id=?", nativeQuery = true)
    void deletePathByAppId(int appId);

    /**
     * 根据申请表id查询此申请表下的附件
     */
    @Query(value = "select * from paths where app_id=?", nativeQuery = true)
    List<Paths> queryPathByAppId(int appId);

    @Transactional
    @Modifying
    @Query(value = "delete from paths where path = ?",nativeQuery = true)
    Integer deleteByPath(String path);

    @Transactional
    @Modifying
    Integer deleteByAppId(Integer appId);
}
