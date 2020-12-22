package com.atguigu.gulimll.search.vo;

import com.atguigu.common.to.es.SkuEsModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回给页面的所有信息
 */
@Data
public class SearchResult {

    /*从ES查询所有商品属性*/
    private List<SkuEsModel> product;

    /*分页信息*/

    /*当前页码*/
    private Integer pageNum;

    /*总记录数*/
    private Long total;

    /*总页码*/
    private Integer totalPages;
    private List<Integer> pageNavs;

    /*当前查询到的结果,所有涉及到的品牌*/
    private List<BrandVo> brands;

    /*当前查询到的结果,所有涉及到的属性*/
    private List<AttrVo> attrs;

    /*当前查询到的结果,所有涉及到的分类*/
    private List<CatalogVo> catalogs;

    /*面包屑导航数据*/
    private List<NavVo> navs = new ArrayList<>();
    private List<Long> attrIds = new ArrayList<>();

    @Data
    public static class NavVo{
        private String navName;
        private String navValue;
        private String link;
    }

    @Data
    public static class BrandVo{

        /*品牌ID*/
        private Long brandId;

        /*品牌名族*/
        private String brandName;

        /*品牌图片*/
        private String brandImg;

    }

    @Data
    public static class CatalogVo{

        /*分类ID*/
        private Long catalogId;

        /*分类名族*/
        private String catalogName;

    }

    @Data
    public static class AttrVo{

        /*属性ID*/
        private Long attrId;

        /*属性名字*/
        private String attrName;

        /*属性价格*/
        private List<String> attrValue;

    }

}
