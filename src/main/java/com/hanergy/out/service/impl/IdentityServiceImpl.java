package com.hanergy.out.service.impl;


import com.hanergy.out.controller.IdRestController;
import com.hanergy.out.controller.RedisIdController;
import com.hanergy.out.service.IRedisService;
import com.hanergy.out.service.IdentityService;
import com.hanergy.out.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author DURONGHONG
 * @version 1.0
 * @className IdentityServiceImpl
 * @description TODO
 * @date 2019-4-15 16:24
 **/
@Service
public class IdentityServiceImpl implements IdentityService {


    private final ReentrantLock reentrantLock  = new ReentrantLock(true);
    private static Integer DEFALUT_CAPACITY = 100000;
    private static Logger logger = LoggerFactory.getLogger(RedisIdController.class);
    private static String BACK_GROUND_ID_ORDER = "BACK_GROUND_ID_ORDER";
    private static Long initial_number = 100000000L;
    private static Long max = 999999999L;

    @Autowired
    private IRedisService redisService;


    @Override
    public List<Long> buildIds(int number) {
        logger.info("[ID生成器]===>>>批量获取ID");
        if (number > DEFALUT_CAPACITY) {
            number = DEFALUT_CAPACITY;
        }
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            ids.add(getSingleId());
        }
        return ids;
    }

    public Long getSingleId() {
        reentrantLock.lock();
        String dateStr = DateUtils.format(new Date(), "yyMMdd");
        String machineCode = "1";

        String stringGet = redisService.stringGet(BACK_GROUND_ID_ORDER);
        if (StringUtils.isEmpty(stringGet)) {
            redisService.expire(BACK_GROUND_ID_ORDER, getDate(), initial_number + "", TimeUnit.MILLISECONDS);
        }

        Long incr = redisService.incr(BACK_GROUND_ID_ORDER, 1L);
        if (incr >= max) {
            redisService.expire(BACK_GROUND_ID_ORDER, getDate(), initial_number + "", TimeUnit.MILLISECONDS);
        }
        long id = Long.parseLong(dateStr + machineCode + incr);
        reentrantLock.unlock();
        return id;
    }

    private Long getDate() {
        long l = System.currentTimeMillis();
        Date endOfDay = DateUtils.getEndOfDay(new Date());
        return endOfDay.getTime() - l;

    }

}
