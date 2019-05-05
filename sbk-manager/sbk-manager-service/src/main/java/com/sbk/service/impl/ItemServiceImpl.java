package com.sbk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sbk.mapper.TbItemDescMapper;
import com.sbk.mapper.TbItemMapper;
import com.sbk.pojo.EasyUIDataGridResult;
import com.sbk.pojo.TbItem;
import com.sbk.pojo.TbItemDesc;
import com.sbk.pojo.TbItemExample;
import com.sbk.redis.JedisClient;
import com.sbk.service.ItemService;
import com.sbk.utils.IDUtils;
import com.sbk.utils.JsonUtils;
import com.sbk.utils.SbkResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Date;
import java.util.List;

/**
 * @author You
 * @create 2018-10-15 16:08
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private JmsTemplate jmsTemplate;
    @Resource
    private Destination topicDestination;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public TbItem getItemById(long id) {

        try {
            String s = jedisClient.get("Item_Info:" + id + ":BASE");
            if(StringUtils.isNotBlank(s)){
                return JsonUtils.jsonToPojo(s, TbItem.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        根据主键查询
//        TbItem tbItem = itemMapper.selectByPrimaryKey(id);

        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<TbItem> tbItems = itemMapper.selectByExample(example);

        if(tbItems != null && tbItems.size() > 0){
            try {
                jedisClient.set("Item_Info:"+id+":BASE",JsonUtils.objectToJson(tbItems.get(0)));
                jedisClient.expire("Item_Info:"+id+":BASE", 1800);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return tbItems.get(0);
        }




        return null;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows){

        PageHelper.startPage(page, rows);
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }

    @Override
    public SbkResult addItem(TbItem item,String desc){
        long id = IDUtils.genItemId();
        item.setId(id);
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());

        itemMapper.insert(item);
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemDesc(desc);
        itemDesc.setItemId(id);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);

        try {
            jmsTemplate.send(topicDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(id+"");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return SbkResult.ok();
    }

    @Override
    public SbkResult deleteItem(String ids){
        String[] split = ids.split(",");
        for (String id : split) {
//            itemMapper.deleteByPrimaryKey(Long.parseLong(id));
            TbItem item = itemMapper.selectByPrimaryKey(Long.parseLong(id));
            item.setStatus(Byte.parseByte("3"));
            itemMapper.updateByPrimaryKey(item);
        }
        return SbkResult.ok();
    }

    @Override
    public SbkResult instockItem(String ids) {
        String[] split = ids.split(",");
        for (String id : split) {
            TbItem item = itemMapper.selectByPrimaryKey(Long.parseLong(id));
            item.setStatus(Byte.parseByte("2"));
            itemMapper.updateByPrimaryKey(item);
        }
        return SbkResult.ok();
    }

    @Override
    public SbkResult reshelfItem(String ids) {
        String[] split = ids.split(",");
        for (String id : split) {
            TbItem item = itemMapper.selectByPrimaryKey(Long.parseLong(id));
            item.setStatus(Byte.parseByte("1"));
            itemMapper.updateByPrimaryKey(item);
        }
        return SbkResult.ok();
    }

    @Override
    public TbItemDesc getItemDescById(long id) {

        try {
            String s = jedisClient.get("Item_Info:" + id + ":DESC");
            if(StringUtils.isNotBlank(s)){
                return JsonUtils.jsonToPojo(s, TbItemDesc.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(id);
        try {
            jedisClient.set("Item_Info:"+id+":DESC",JsonUtils.objectToJson(tbItemDesc));
            jedisClient.expire("Item_Info:"+id+":DESC", 1800);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItemDesc;
    }
}
