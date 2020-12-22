package com.atguigu.gulimall.order;

import com.atguigu.gulimall.order.service.OrderReturnReasonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GulimallOrderApplicationTests {

	@Autowired
	private OrderReturnReasonService orderReturnReasonService;

	@Test
	void contextLoads() {

		String s = orderReturnReasonService.toString();
		System.out.println(s);

	}

}
