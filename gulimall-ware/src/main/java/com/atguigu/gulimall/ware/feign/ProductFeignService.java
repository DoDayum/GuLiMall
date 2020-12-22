package com.atguigu.gulimall.ware.feign;

import com.atguigu.common.utils.R;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient("gulimall-product")
public interface ProductFeignService {

    /**
     * 不过网关请求(直接让后台指定服务处理):@FeignClient("gulimall-product")
     *                              product/skuinfo/info/{skuId}
     * @param skuId
     * @return
     */
    @RequestMapping("/product/skuinfo/info/{skuId}")
//    @RequiresPermissions("product:skuinfo:info")
    public R info(@PathVariable("skuId") Long skuId);

}
