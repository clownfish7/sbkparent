package com.sbk.service.impl;

import com.sbk.mapper.TbItemMapper;
import com.sbk.pojo.TbItem;
import com.sbk.pojo.TbItemExample;
import com.sbk.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author You
 * @create 2018-10-15 16:08
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public TbItem getItemById(long id) {

//        根据主键查询
//        TbItem tbItem = itemMapper.selectByPrimaryKey(id);

        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<TbItem> tbItems = itemMapper.selectByExample(example);

        if(tbItems != null && tbItems.size() > 0){
            return tbItems.get(0);
        }

        return null;
    }
}
