package com.sbk.controller;

import com.sbk.pojo.EasyUIDataGridResult;
import com.sbk.pojo.TbItem;
import com.sbk.service.ItemService;
import com.sbk.utils.SbkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author You
 * @create 2018-10-15 16:17
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/item/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){

        TbItem item = itemService.getItemById(itemId);
        System.out.println(item.toString());
        return item;
    }

    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows){
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    @RequestMapping(value = "/item/save" , method = RequestMethod.POST)
    @ResponseBody
    public SbkResult addItem(TbItem item, String desc){
        SbkResult result = itemService.addItem(item, desc);
        return result;
    }

    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public SbkResult deleteItem(String ids){
        itemService.deleteItem(ids);
        return SbkResult.ok();
    }

    /**
     * 下架
     */
    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public SbkResult instockItem(String ids){
        itemService.instockItem(ids);
        return SbkResult.ok();
    }

    /**
     * 上架
     */
    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public SbkResult reshelfItem(String ids){
        itemService.reshelfItem(ids);
        return SbkResult.ok();
    }


    /*@RequestMapping("/rest/page/item-edit?_=1540791636795")
    @ResponseBody
    public TbItem ed3*/


}
