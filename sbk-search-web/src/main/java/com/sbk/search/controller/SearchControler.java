package com.sbk.search.controller;

import com.sbk.pojo.SearchItem;
import com.sbk.pojo.SearchResult;
import com.sbk.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author You
 * @create 2018-11-05 17:17
 */

@Controller
public class SearchControler {

    @Autowired
    private SearchService searchService;

    @Value("${SEARCH_RESULT_ROWS}")
    private Integer SEARCH_RESULT_ROWS;

    @RequestMapping("/search")
    public String searchItemList(String keyword, @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception {

        keyword = new String(keyword.getBytes("iso-8859-1"), "utf-8");
        SearchResult searchResult = searchService.search(keyword, page, SEARCH_RESULT_ROWS);
        model.addAttribute("query", keyword);
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("recourdCount", searchResult.getRecordCount());
        model.addAttribute("itemList", searchResult.getItemList());

       /* List<SearchItem> itemList = searchResult.getItemList();
        for (SearchItem item : itemList) {
            System.out.println(item.getId()+"-"+item.getName());
        }

        System.out.println(keyword);
        System.out.println(searchResult.getTotalPages());
        System.out.println(page);
        System.out.println(searchResult.getRecordCount());
        System.out.println(keyword);*/

//       int a = 1/0;int a = 1/0;
        //返回逻辑视图
        return "search";

    }
}
