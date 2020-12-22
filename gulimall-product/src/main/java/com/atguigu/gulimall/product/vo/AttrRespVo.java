package com.atguigu.gulimall.product.vo;

import lombok.Data;

@Data
public class AttrRespVo extends AttrVo{

    private String catelogName;//所属分类的名字
    private String groupName;//所属分组的名字
    private Long [] catelogPath;
}
