package com.sbk.service;

import com.sbk.pojo.EasyUIDataGridResult;
import com.sbk.pojo.TbItem;
import com.sbk.pojo.TbItemDesc;
import com.sbk.utils.SbkResult;

/**
 * @author You
 * @create 2018-10-15 15:59
 */
public interface ItemService {
    TbItem getItemById(long id);
    EasyUIDataGridResult getItemList(int page, int rows);
    SbkResult addItem(TbItem item,String desc);
    SbkResult deleteItem(String ids);
    SbkResult instockItem(String ids);
    SbkResult reshelfItem(String ids);
    TbItemDesc getItemDescById(long itemId);
}
