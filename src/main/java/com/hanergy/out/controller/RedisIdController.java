package com.hanergy.out.controller;

import com.hanergy.out.utils.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RedisIdController
 * @Description
 * @Auto HANLIDONG
 * @Date 2019-6-28 13:52)
 */
@RestController
@RequestMapping(value = "/api/v1/redis")
public class RedisIdController {

    @RequestMapping("id")
    public R redisId(){

        return  null;
    }
}
