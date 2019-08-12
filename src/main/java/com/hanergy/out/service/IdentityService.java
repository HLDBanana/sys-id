package com.hanergy.out.service;

import java.util.List;

/**
 * @author DURONGHONG
 * @version 1.0
 * @className IdentityService
 * @description TODO
 * @date 2019-4-15 16:24
 **/
public interface IdentityService {

    List<Long> buildIds(int number);

    default Long buildOneId() {
        return buildIds(1).get(0);
    }
}
