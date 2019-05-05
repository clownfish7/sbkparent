package com.sbk.search.service.impl;

import com.sbk.pojo.SearchItem;
import com.sbk.search.mapper.ItemMapper;
import com.sbk.search.service.SearchItemService;
import com.sbk.utils.SbkResult;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author You
 * @create 2018-11-05 14:21
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {


    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrServer solrServer;

    @Override
    public SbkResult importAllItems() {

        try {
            List<SearchItem> itemList = itemMapper.getItemList();
            for (SearchItem item : itemList) {
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", item.getId());
                document.addField("item_title", item.getTitle());
                document.addField("item_sell_point", item.getSell_point());
                document.addField("item_price", item.getPrice());
                document.addField("item_image", item.getImage());
                document.addField("item_category_name", item.getName());
                solrServer.add(document);
            }
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
//            return SbkResult.build(500, "err");
        }

        return SbkResult.ok();
    }

}
