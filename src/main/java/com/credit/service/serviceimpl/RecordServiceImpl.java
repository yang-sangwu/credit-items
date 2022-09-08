package com.credit.service.serviceimpl;

import com.credit.dao.RecordDao;
import com.credit.pojo.Page;
import com.credit.pojo.Record;
import com.credit.pojo.User;
import com.credit.repository.RecordRepository;
import com.credit.repository.UserRepository;
import com.credit.service.RecordService;
import com.credit.util.RecordUtil;
import com.credit.util.ReturnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecordDao recordDao;

    @Override
    public ReturnUtil addRecord(String userName, Integer recordAppid) {
        User userByUserName = userRepository.getUserByUserName(userName);
        if (userByUserName == null) {
            return ReturnUtil.error("该用户不存在！");
        }
        Record record = new Record(null, userByUserName.getUserId(), recordAppid);
        recordRepository.save(record);
        return ReturnUtil.success("添加成功!", record);
    }

    @Override
    public ReturnUtil deleteRecord(Integer recordId) {
        Integer integer = recordDao.deleteByRecordId(recordId);
        if (integer == 0) {
            return ReturnUtil.error("删除失败！");
        }
        return ReturnUtil.success("删除成功！");
    }

    @Override
    public Page<RecordUtil> getLikeAppRecord(String appAcademic, String search, Integer pageNo, Integer pageSize) {
        List<RecordUtil> list = new ArrayList<>();
        String meg = "";
        Integer count = recordDao.countLikeAppRecord(appAcademic, search);
//        log.info("count:" + count);
        Integer pageTotal = 0;
        int index = (pageNo - 1) * pageSize;

        if (count % pageSize != 0)
            pageTotal++;
        pageTotal += recordDao.countLikeAppRecord(appAcademic, search) / pageSize;
        if (recordDao.getLikeAppRecord(appAcademic, search, index, pageSize).size() < 1) {
            meg = "未查到你想要的数据！";
        } else if (pageNo > pageTotal) {
            meg = "查询失败,超出最大页码";
        } else if (pageNo <= 0) {
            meg = "查询失败,页码不存在";
        } else {
            list = recordDao.getLikeAppRecord(appAcademic, search, index, pageSize);
            meg = "查询成功";
            return new Page(1, meg, pageNo, pageSize, pageTotal, count, list);
        }
        return Page.error(0, meg);
    }
}
