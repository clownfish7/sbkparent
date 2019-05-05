package com.sbk.sso.controller;

import com.sbk.pojo.TbUser;
import com.sbk.sso.service.RegisterService;
import com.sbk.utils.EmailUtils;
import com.sbk.utils.SbkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author You
 * @create 2018-11-09 15:07
 */

@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/page/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public SbkResult checkData(@PathVariable String param, @PathVariable Integer type){
        SbkResult result = registerService.checkData(param, type);
        return result;
    }

    @RequestMapping("/user/register")
    @ResponseBody
    public SbkResult register(TbUser user){
        SbkResult result = registerService.register(user);
        try {
            EmailUtils.sendQQEmail(user.getEmail(), "Hi", "Welcome to SBK!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
