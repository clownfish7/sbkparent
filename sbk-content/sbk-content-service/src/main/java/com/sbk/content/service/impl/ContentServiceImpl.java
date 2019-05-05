package com.sbk.content.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sbk.content.service.ContentService;
import com.sbk.mapper.TbContentMapper;
import com.sbk.pojo.EasyUIDataGridResult;
import com.sbk.pojo.TbContent;
import com.sbk.pojo.TbContentExample;
import com.sbk.redis.JedisClient;
import com.sbk.utils.JsonUtils;
import com.sbk.utils.SbkResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;

    @Override
    public SbkResult addContent(TbContent content) {
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);
        jedisClient.hdel("CONTENT_LIST",content.getCategoryId().toString());
        return SbkResult.ok();
    }

    @Override
    public List<TbContent> getContentByCid(long cid) {

        try {
            String json = jedisClient.hget("CONTENT_LIST", cid + "");
            if (StringUtils.isNotBlank(json)){
                return JsonUtils.jsonToList(json, TbContent.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        --------------------------------------------------------------------
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
//        ----------------------------------------------------------------------

        try {
            jedisClient.hset("CONTENT_LIST", cid+"", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {

        PageHelper.startPage(page, rows);
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> list = contentMapper.selectByExample(example);
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }

    @Override
    public SbkResult editContent(TbContent content) {
        Date created = contentMapper.selectByPrimaryKey(content.getId()).getCreated();
        content.setCreated(created);
        content.setUpdated(new Date());
        contentMapper.updateByPrimaryKey(content);
        jedisClient.hdel("CONTENT_LIST",content.getCategoryId().toString());
        return SbkResult.ok();
    }

    @Override
    public SbkResult deleteContent(String ids) {
        String[] split = ids.split(",");
        for (String id : split) {
            contentMapper.deleteByPrimaryKey(Long.parseLong(id));
        }
        return SbkResult.ok();
    }


}
