package com.sbk.service;


import com.sbk.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {
    List<EasyUITreeNode> getItemCatlist(long parentId);
}
