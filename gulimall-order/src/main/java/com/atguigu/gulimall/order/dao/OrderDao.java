package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author bsiyu
 * @email 1830274250@gmail.com
 * @date 2020-10-20 01:29:19
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
