package com.credit.repository;

import com.credit.pojo.Authorization;
import com.credit.pojo.Page;
import com.credit.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AuthorizationRepository extends JpaRepository<Authorization, Integer>, JpaSpecificationExecutor {

    @Transactional
    @Modifying
    @Query(value = "update authorization set auth_status=1 where auth_id=?", nativeQuery = true)
    Integer modifyAuthStatus(Integer authId);

    @Query(value = "delete from authorization where auth_id = ?", nativeQuery = true)
    Integer deleteMessage(Integer authId);

    @Query(value = "select * from authorization where auth_status = 0 limit ?1,?2", nativeQuery = true)
    List<Authorization> findAllMessage(Integer index, Integer pageSize);

    @Query(value = "select count(*) from authorization where auth_status = 0", nativeQuery = true)
    Integer countAllMessage();

    @Query(value = "select * from  authorization where auth_userid= ?1 and auth_status = ?2",nativeQuery = true)
    Authorization getAuthorizationByAuthUseridAAndAndAuthStatus(Integer authUserId,Integer authStatus);
}
