package com.sbk.sso.service.impl;

import com.sbk.mapper.TbUserMapper;
import com.sbk.pojo.TbUser;
import com.sbk.pojo.TbUserExample;
import com.sbk.sso.service.RegisterService;
import com.sbk.utils.SbkResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @author You
 * @create 2018-11-10 13:25
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    TbUserMapper userMapper;

    @Override
    public SbkResult checkData(String param, int type) {

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        if (type == 1) {
            criteria.andUsernameEqualTo(param);
        } else if(type == 2) {
            criteria.andPhoneEqualTo(param);
        } else if(type == 3) {
            criteria.andEmailEqualTo(param);
        } else {
            return SbkResult.build( 400, "type error");
        }

        List<TbUser> users = userMapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            return SbkResult.ok(false);
        }

        return SbkResult.ok(true);

    }

    @Override
    public SbkResult register(TbUser user) {

        //数据有效性校验
        if (StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())
                || StringUtils.isBlank(user.getPhone())) {
            return SbkResult.build(400, "用户数据不完整，注册失败");
        }
        //1：用户名 2：手机号 3：邮箱
        SbkResult result = checkData(user.getUsername(), 1);
        if (!(boolean)result.getData()) {
            return SbkResult.build(400, "此用户名已经被占用");
        }
        result = checkData(user.getPhone(), 2);
        if (!(boolean)result.getData()) {
            return SbkResult.build(400, "手机号已经被占用");
        }
        //补全pojo的属性
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //对密码进行md5加密
        String md5 = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5);
        userMapper.insert(user);
        return SbkResult.ok();
    }
}
