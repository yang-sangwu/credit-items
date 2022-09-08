package com.credit.service;

import com.credit.pojo.Authorization;
import com.credit.pojo.Page;
import com.credit.util.ReturnUtil;

public interface AuthorizationService {

    ReturnUtil deleteMessage(Integer authId);

    Page<Authorization> findAllMessage(Integer pageNo, Integer pageSize);
}
