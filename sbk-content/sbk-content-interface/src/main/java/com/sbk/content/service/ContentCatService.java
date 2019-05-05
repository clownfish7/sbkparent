package com.sbk.content.service;


import com.sbk.pojo.EasyUITreeNode;
import com.sbk.utils.SbkResult;

import java.util.List;

public interface ContentCatService {

    List<EasyUITreeNode> getCOntentCatList(long parentId);
    SbkResult addContentCategory(long parentId, String name);
    void updateContentCategory(long id, String name);
    void deleteContentCategory(long id);
}
