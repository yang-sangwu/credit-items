package com.credit.service.serviceimpl;

import com.credit.dao.StandardDao;
import com.credit.repository.StandardRepository;
import com.credit.service.StandardService;
import com.credit.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class StandardServiceImpl implements StandardService {
    @Autowired
    private StandardDao standardDao;

    @Autowired
    private StandardRepository standardRepository;

    @Override
    public ReturnUtil addOne(String staName, Integer staGrade, Integer staFatherid, BigDecimal staCredit, String staRemark) {
        if (staCredit != null) {
            if (staCredit.compareTo(BigDecimal.valueOf(0)) < 1) {
                return ReturnUtil.error("学分数据不能为负数,请重新输入！");
            }
        }
        if (standardDao.existName(staName).size() > 0) {
            return ReturnUtil.error("该指标已存在!");
        } else {
            Integer integer = standardDao.addOne(staName, staGrade, staFatherid, staCredit, staRemark);
            if (integer == null || integer < 1) {
                return ReturnUtil.error("添加失败！");
            }
            return ReturnUtil.success("添加成功!");
        }
    }

    @Override
    public ReturnUtil getAllFirst() {
        List<Type> allFirst = standardDao.getAllFirst();
        if (allFirst != null && allFirst.size() > 0) {
            return ReturnUtil.success("查询成功！", allFirst);
        }
        return ReturnUtil.error("查询失败！");
    }

    @Override
    public ReturnUtil updateFirst(Integer staId, String staName) {
        if (standardRepository.findByStaName(staName) != null) {
            return ReturnUtil.error("该指标已存在，修改失败！");
        }
        standardDao.updateFirst(staId, staName);
        return ReturnUtil.success("修改成功！");
    }

    @Override
    public ReturnUtil deleteFirst(Integer staId) {
        Integer integer = standardDao.deleteFirst(staId);
        if (!(integer > 0) || standardDao.getGrade(staId) != 1) {
            return ReturnUtil.error("删除失败,只能删除一级指标!");
        }
        return ReturnUtil.success("删除成功！");
    }

    @Override
    public ReturnUtil addSecondMore(String staName, Integer staGrade, Integer staFatherid) {

        Integer integer = standardDao.addSecondMore(staName, staGrade, staFatherid);
        if (integer > 0) {
            return ReturnUtil.success("添加成功！");
        }
        return ReturnUtil.error("添加失败！");
    }

    @Override
    public ReturnUtil addThirdMore(String staName, Integer staGrade, Integer staFatherid, BigDecimal staCredit, String staRemark) {
        if (staCredit.compareTo(BigDecimal.valueOf(0)) < 1) {
            return ReturnUtil.error("学分数据不能为负数,请重新输入！");
        }
        Integer integer = standardDao.addThirdMore(staName, staGrade, staFatherid, staCredit, staRemark);
        if (integer > 0) {
            return ReturnUtil.success("添加成功！");
        }
        return ReturnUtil.error("添加失败！");
    }


}
