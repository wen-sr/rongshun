package com.rongshun.controller.wechat;

import com.rongshun.common.ServerResponse;
import com.rongshun.pojo.wechat.Sku;
import com.rongshun.service.wechat.ISkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    ISkuService skuService;

//    @RequestMapping("/skuTips")
//    public List<Sku> skuTips(Sku sku){
//        return skuService.skuTips(sku);
//    }

    @RequestMapping("/skuTips")
    public ServerResponse skuTips(Sku sku){
        return ServerResponse.createBySuccess("查询成功",skuService.skuTips(sku));
    }

    @RequestMapping("/build")
    public ServerResponse build(@RequestParam("skuId") Integer skuId, @RequestParam("skuName") String skuName, @RequestParam("qty") Integer qty){
        return skuService.build(skuId, skuName, qty);
    }

    @RequestMapping("/buildDetail")
    public ServerResponse buildDetail( @RequestParam("skuName") String skuName){
        return skuService.buildDetail(skuName);
    }

    @RequestMapping("/add")
    public ServerResponse add(String name){
        return skuService.add(name);
    }

}
