package com.credit.service.serviceimpl;

import com.credit.dao.LocationDao;
import com.credit.pojo.Location;
import com.credit.repository.LocationRepository;
import com.credit.service.LocationService;
import com.credit.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    @Resource
    private LocationRepository locationRepository;

    @Override
    public ReturnUtil addLocation(String locationName, Integer locationFatherid, Integer locationGrade) {
        if (locationFatherid == null) {
            locationFatherid = 0;
        }
        if (locationDao.existLocation(locationName) != null) {
            return ReturnUtil.error("该信息已存在！");
        }
//        Integer integer = locationDao.addLocation(locationName, locationFatherid, locationGrade);
        Location location = new Location(null, locationName, locationFatherid, locationGrade);
        locationRepository.save(location);
//        if (integer != 1) {
//            return ReturnUtil.error("添加失败!");
//        }
        return ReturnUtil.success("添加成功!", location);
    }

    @Override
    public ReturnUtil updateLocation(Integer locationId, String locationName) {
        Integer integer = locationDao.updateLocation(locationId, locationName);
        if (integer == 1) {
            return ReturnUtil.success("修改成功!");
        }
        return ReturnUtil.error("修改失败!");
    }

    @Override
    public ReturnUtil deleteLocation(Integer locationId) {
        Integer integer = locationDao.deleteLocation(locationId);
        if (integer == null || integer == 0) {
            return ReturnUtil.error("删除失败!");
        }
        return ReturnUtil.success("删除成功!");
    }

    @Override
    public ReturnUtil getFirstLocation() {
        List<Location> allLoaction = locationDao.getFirstLocation();
        if (allLoaction.size() == 0) {
            return ReturnUtil.error("无数据!");
        }
        return ReturnUtil.success("查询成功!", allLoaction);
    }

    @Override
    public ReturnUtil getSecondOrThirdLocation(String search) {
        List<Location> secondOrThirdLocation = locationDao.getSecondOrThirdLocation(search);
        if (secondOrThirdLocation.isEmpty()) {
            return ReturnUtil.error("无数据!");
        }
        return ReturnUtil.success("查询成功!", secondOrThirdLocation);
    }

}
