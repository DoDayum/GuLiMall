package com.atguigu.gulimll.search.controller;

import com.atguigu.gulimll.search.service.MallSearchService;
import com.atguigu.gulimll.search.vo.SearchParam;
import com.atguigu.gulimll.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SearchController {

    @Autowired
    MallSearchService mallSearchService;

    /**
     * 将页面提交过来的所有请求查询参数封装成指定对象
     * @param param
     * @return
     */
    @GetMapping("/list.html")
    public String ListPage(SearchParam param, Model model, HttpServletRequest request){

        param.set_queryString(request.getQueryString());
        //根据传递来的页面的查询参数,去es中检索商品
        SearchResult result =  mallSearchService.search(param);
        model.addAttribute("result",result);
        return "list";
    }

}
