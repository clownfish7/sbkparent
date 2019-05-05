package com.sbk.sso.service;


import com.sbk.utils.SbkResult;


public interface TokenService {

	SbkResult getUserByToken(String token);
}
