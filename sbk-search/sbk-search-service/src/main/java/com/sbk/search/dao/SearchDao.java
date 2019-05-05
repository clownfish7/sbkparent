package com.sbk.search.dao;

import com.sbk.pojo.SearchItem;
import com.sbk.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author You
 * @create 2018-11-05 16:50
 */

@Repository
public class SearchDao {

    @Autowired
    private SolrServer solrServer;

    public SearchResult search(SolrQuery query) throws SolrServerException {

        QueryResponse queryResponse = solrServer.query(query);

        SolrDocumentList results = queryResponse.getResults();

        Long numFound = results.getNumFound();

        SearchResult searchResult = new SearchResult();

        searchResult.setRecordCount(numFound);

        List<SearchItem> itemList = new ArrayList<>();
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        for (SolrDocument result : results) {
            SearchItem item = new SearchItem();
            item.setId(result.getFieldValue("id").toString());
            item.setTitle(result.getFieldValue("item_title").toString());
            item.setName((String) result.get("item_category_name"));
            item.setImage((String) result.get("item_image"));
            item.setPrice( Long.parseLong(result.get("item_price").toString().substring(0, result.get("item_price").toString().length()-2)));
            item.setSell_point((String) result.get("item_sell_point"));

            //取高亮显示
            List<String> list = highlighting.get(result.get("id")).get("item_title");
            String title = "";
            if (list != null && list.size() > 0) {
                title = list.get(0);
            } else {
                title = (String) result.get("item_title");
            }
            item.setTitle(title);
            //添加到商品列表
            itemList.add(item);
        }


        searchResult.setItemList(itemList);
        //返回结果
        return searchResult;

    }

}
