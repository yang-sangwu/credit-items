package com.credit.controller;

import com.credit.service.LocationService;
import com.credit.util.ReturnUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "学生所属信息")
@RequestMapping("/location")
@PreAuthorize("hasAnyAuthority('1','2','0','12')")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping("/addLocation")
    @ApiOperation("添加学院,专业或班级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "locationName", value = "学院,专业或班级信息", required = true),
            @ApiImplicitParam(name = "locationFatherid", value = "父级id", required = false),
            @ApiImplicitParam(name = "locationGrade", value = "等级(1,2,3)", required = true)
    })
    public ReturnUtil addLocation(String locationName, Integer locationFatherid, Integer locationGrade) {
        return locationService.addLocation(locationName, locationFatherid, locationGrade);
    }

    @ApiOperation("修改学院,专业或班级信息")
    @PutMapping("/updateLocation")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "locationName", value = "学院,专业或班级信息", required = true),
            @ApiImplicitParam(name = "locationId", value = "id", required = true)
    })
    public ReturnUtil updateLocation(Integer locationId, String locationName) {
        return locationService.updateLocation(locationId, locationName);
    }

    @ApiImplicitParam(name = "locationId", value = "id", required = true)
    @ApiOperation("删除学院,专业或班级信息")
    @DeleteMapping("/deleteLocation")
    public ReturnUtil deleteLocation(Integer locationId) {
        return locationService.deleteLocation(locationId);
    }

    @GetMapping("/getFirstLocation")
    @ApiOperation("查询学院")
    public ReturnUtil getFirstLocation() {
        return locationService.getFirstLocation();
    }

    @ApiOperation("查询专业或班级信息")
    @GetMapping("/getSecondOrThirdLocation")
    @ApiImplicitParam(name = "search", value = "查询信息(填写你想要查询的学院或专业下的子级信息)", required = true)
    public ReturnUtil getSecondOrThirdLocation(String search) {
        return locationService.getSecondOrThirdLocation(search);
    }
}
