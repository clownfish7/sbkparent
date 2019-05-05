package com.sbk.sso.controller;

import com.sbk.sso.service.LoginService;
import com.sbk.utils.CookieUtils;
import com.sbk.utils.SbkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author You
 * @create 2018-11-09 15:07
 */

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    /*@RequestMapping("/page/login")
    public String showLogin(String redirect, Model model) {
        model.addAttribute("redirect", redirect);
        return "login";
    }*/

    @RequestMapping("/page/login")
    public String showLogin() {
        return "login";
    }

    @RequestMapping(value="/user/login", method=RequestMethod.POST)
    @ResponseBody
    public SbkResult login(String username, String password,
                           HttpServletRequest request, HttpServletResponse response) {
        SbkResult result = loginService.login(username, password);
        //判断是否登录成功
        if(result.getStatus() == 200) {
            String token = result.getData().toString();
            //如果登录成功需要把token写入cookie
            CookieUtils.setCookie(request, response, "token", token);
        }
        //返回结果
        return result;
    }

    @RequestMapping("/logout")
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.setCookie(request, response, "token", "");
        return "redirect:http://localhost:8083";
    }
}
