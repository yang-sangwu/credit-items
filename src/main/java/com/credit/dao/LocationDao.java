package com.credit.dao;

import com.credit.pojo.Location;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface LocationDao {
    /**
     * 添加学院,专业或班级信息
     *
     * @param locationFatherid
     * @param locationGrade
     * @return
     */
    Integer addLocation(@Param("locationName") String locationName, @Param("locationFatherid") Integer locationFatherid, @Param("locationGrade") Integer locationGrade);

    /**
     * 修改学院,专业或班级信息
     *
     * @param locationId
     * @param locationName
     * @return
     */
    Integer updateLocation(@Param("locationId") Integer locationId, @Param("locationName") String locationName);

    /**
     * 删除学院,专业或班级信息
     *
     * @param locationId
     * @return
     */
    Integer deleteLocation(@Param("locationId") Integer locationId);

    /**
     * 查询学院
     *
     * @return
     */
    List<Location> getFirstLocation();

    /**
     * 查询专业或班级信息
     *
     * @param search
     * @return
     */
    List<Location> getSecondOrThirdLocation(@Param("search") String search);

    Location existLocation(@Param("locationName") String locationName);


}
