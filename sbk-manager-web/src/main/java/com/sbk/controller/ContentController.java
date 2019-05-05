package com.sbk.controller;

import com.sbk.content.service.ContentService;
import com.sbk.pojo.EasyUIDataGridResult;
import com.sbk.pojo.TbContent;
import com.sbk.utils.SbkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping(value = "/content/save" , method = RequestMethod.POST)
    @ResponseBody
    public SbkResult addContent(TbContent content){
        SbkResult result = contentService.addContent(content);
        return result;
    }

    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows){
        EasyUIDataGridResult result = contentService.getContentList(categoryId, page, rows);
        return result;
    }

    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public SbkResult editContent(TbContent content){
        SbkResult result = contentService.editContent(content);
        return result;
    }

    @RequestMapping("/content/delete")
    @ResponseBody
    public SbkResult deleteContent(String ids){
        SbkResult result = contentService.deleteContent(ids);
        return result;
    }

}
