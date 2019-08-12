package com.hanergy.out.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanergy.out.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName IdRestController
 * @Description TODO
 * @Author DURONGHONG
 * @DATE 2018/10/18 14:14
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = "/api/v1/id")
public class IdRestController {

    @Autowired
    private IdentityService identityService;

    @RequestMapping(value = "/unique")
    @ResponseBody
    public JSONObject getId(
            @RequestParam(value = "number", defaultValue = "1") Integer number
    ) {

        JSONObject result = new JSONObject();
        result.put("data", identityService.buildIds(number));
        result.put("msg", "请求成功,获得ID数量:" + number);
        result.put("status", 0);
        return result;
    }
   @RequestMapping(value = "/uniqueOne")
   @ResponseBody
   public JSONObject getTheSingleId() {
       JSONObject result = new JSONObject();
       result.put("data", identityService.buildOneId());
       result.put("msg", "请求成功,获取单一id");
       result.put("status", 0);
       return result;
   }

}
