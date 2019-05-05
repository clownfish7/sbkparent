package com.sbk.enter.controller;

import com.sbk.content.service.ContentService;
import com.sbk.pojo.TbContent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author You
 * @create 2018-10-30 14:31
 */
@Controller
public class PageController {

    @Value("${CONTENT_LUNBO_ID}")
    private long CONTENT_LUNBO_ID;

    @Autowired
    private ContentService contentService;


    @RequestMapping("/{page}")
    public String showPage(@PathVariable String page){
        return page;
    }

    @RequestMapping("/index")
    public String showIndex(Model model){
        List<TbContent> ad1List = contentService.getContentByCid(CONTENT_LUNBO_ID);
        model.addAttribute("ad1List", ad1List);
        return "index";
    }

}
