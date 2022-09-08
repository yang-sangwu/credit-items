package com.credit.service;

import com.credit.pojo.Page;
import com.credit.util.RecordUtil;
import com.credit.util.ReturnUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordService {
    ReturnUtil addRecord(String userName, Integer recordAppid);

    ReturnUtil deleteRecord(Integer redocdId);

    Page<RecordUtil> getLikeAppRecord(String appAcademic, String search, Integer pageNo, Integer pageSize);


}
