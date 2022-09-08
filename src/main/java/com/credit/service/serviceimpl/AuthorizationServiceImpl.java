package com.credit.service.serviceimpl;

import com.credit.pojo.Authorization;
import com.credit.pojo.Page;
import com.credit.pojo.User;
import com.credit.repository.AuthorizationRepository;
import com.credit.service.AuthorizationService;
import com.credit.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    @Autowired
    private AuthorizationRepository authorizationRepository;

    @Override
    public ReturnUtil deleteMessage(Integer authId) {
        Integer integer = authorizationRepository.deleteMessage(authId);
        if (integer == null || integer < 1) {
            return ReturnUtil.error("删除失败！");
        }
        return ReturnUtil.success("删除成功！");
    }

    @Override
    public Page<Authorization> findAllMessage(Integer pageNo, Integer pageSize) {
        List<Authorization> list = new ArrayList<>();
        String meg = "";
        Integer count = authorizationRepository.countAllMessage();
//        log.info("count:" + count);
        Integer pageTotal = 0;
        if (count == 0) {
            return Page.error(0, "无相关数据！");
        }
        if (count % pageSize != 0)
            pageTotal++;
        pageTotal += authorizationRepository.countAllMessage() / pageSize;
        if (pageNo > pageTotal) {
            meg = "查询失败,超出最大页码";
        } else if (pageNo <= 0) {
            meg = "查询失败,页码不存在";
        } else {
            int index = (pageNo - 1) * pageSize;
            list = authorizationRepository.findAllMessage(index, pageSize);
            meg = "查询成功";
            return new Page(1, meg, pageNo, pageSize, pageTotal, count, list);
        }
        return Page.error(0, meg);
    }
}
