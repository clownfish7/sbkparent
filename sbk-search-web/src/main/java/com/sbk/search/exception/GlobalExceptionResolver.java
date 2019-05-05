package com.sbk.search.exception;


import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author You
 * @create 2018-11-06 21:12
 * 全局异常处理器!
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    public static final Logger LOGGER = Logger.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception e) {

        e.printStackTrace();
        LOGGER.error("error!",e);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");

        return modelAndView;
    }
}
