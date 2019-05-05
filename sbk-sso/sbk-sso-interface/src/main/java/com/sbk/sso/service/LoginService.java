package com.sbk.sso.service;

import com.sbk.utils.SbkResult;

/**
 * @author You
 * @create 2018-11-10 13:25
 */
public interface LoginService {

    SbkResult login(String username, String password);
}
