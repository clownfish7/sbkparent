package com.sbk.search.mapper;

import com.sbk.pojo.SearchItem;

import java.util.List;

/**
 * @author You
 * @create 2018-11-02 16:15
 */
public interface ItemMapper {

    List<SearchItem> getItemList();

    SearchItem getItemById(long id);
}
