package com.sbk.controller;

import com.sbk.search.service.SearchItemService;
import com.sbk.utils.SbkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author You
 * @create 2018-11-05 14:41
 */

@Controller
public class SearchItemController {

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/item/import")
    @ResponseBody
    public SbkResult importItemList(){
        SbkResult result = searchItemService.importAllItems();
        return result;
    }
}
