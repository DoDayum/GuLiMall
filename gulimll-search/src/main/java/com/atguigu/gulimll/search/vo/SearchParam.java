package com.atguigu.gulimll.search.vo;

import lombok.Data;

import java.util.List;

/**
 * 封装页面所有可能传递过来的数据
 */
@Data
public class SearchParam {

    /*全文匹配关键字*/
    private String keyword;

    /*三级分类ID*/
    private Long catalog3Id;

    /*排序条件*/
    //soleCount_asc/desc
    //skuPrice_asc/desc
    //hotScore_asc/desc
    private String sort;

    /*只显示有货*/
    private Integer hasStock;//0无，1有

    /*价格区间*/
    private String skuPrice;//1~500:1_500,0~500:_500,500~0：500_

    /*品牌选择*/
    private List<Long> brandId;

    /*按照属性*/
    private List<String> attrs;

    /*页码*/
    private Integer pageNum = 1;

    private String _queryString;//原生的查询条件

}
