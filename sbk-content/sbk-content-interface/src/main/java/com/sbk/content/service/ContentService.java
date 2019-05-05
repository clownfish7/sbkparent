package com.sbk.content.service;

import com.sbk.pojo.EasyUIDataGridResult;
import com.sbk.pojo.TbContent;
import com.sbk.utils.SbkResult;

import java.util.List;

public interface ContentService {

    SbkResult addContent(TbContent content);

    List<TbContent> getContentByCid(long cid);

    EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows);

    SbkResult editContent(TbContent content);

    SbkResult deleteContent(String ids);


}
