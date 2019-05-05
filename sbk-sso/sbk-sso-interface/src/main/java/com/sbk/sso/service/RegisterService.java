package com.sbk.sso.service;

import com.sbk.pojo.TbUser;
import com.sbk.utils.SbkResult;

/**
 * @author You
 * @create 2018-11-10 13:25
 */
public interface RegisterService {

    SbkResult checkData(String param, int type);
    SbkResult register(TbUser user);
}
