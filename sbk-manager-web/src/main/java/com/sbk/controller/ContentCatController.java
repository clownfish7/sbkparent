package com.sbk.controller;


import com.sbk.content.service.ContentCatService;
import com.sbk.pojo.EasyUITreeNode;
import com.sbk.utils.SbkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author You
 * @create 2018-10-30 15:37
 */

@Controller
public class ContentCatController {

    @Autowired
    private ContentCatService contentCatService;

    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(@RequestParam(name = "id" , defaultValue = "0") Long parentId){
        List<EasyUITreeNode> list = contentCatService.getCOntentCatList(parentId);
        return list;
    }

    @RequestMapping(value = "/content/category/create" , method = RequestMethod.POST)
    @ResponseBody
    public SbkResult createContentCategory(Long parentId, String name){
        SbkResult result = contentCatService.addContentCategory(parentId, name);
        return result;
    }

    @RequestMapping(value = "/content/category/update" , method = RequestMethod.POST)
    @ResponseBody
    public void updateContentCategory(Long id, String name){
        contentCatService.updateContentCategory(id, name);
    }

    @RequestMapping(value = "/content/category/delete" , method = RequestMethod.POST)
    @ResponseBody
    public void deleteContentCategory(Long id){
        contentCatService.deleteContentCategory(id);
    }
}
