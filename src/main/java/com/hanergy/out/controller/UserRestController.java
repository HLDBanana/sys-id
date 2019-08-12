package com.hanergy.out.controller;

import ch.qos.logback.core.util.FileUtil;
import com.alibaba.fastjson.JSONObject;
import com.hanergy.out.service.SysUserService;
import com.hanergy.out.utils.RSAUtils;
import com.hanergy.out.vo.EsourcingQueryVo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserRestController
 * @Description TODO
 * @Author DURONGHONG
 * @DATE 2018/10/23 9:09
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = "/api/v1/user")
public class UserRestController {

    @Autowired
    private SysUserService userService;

    private static List<String> CONDITION_KEY_LIST = new ArrayList<>();
    static {
        CONDITION_KEY_LIST.add("T1");
        CONDITION_KEY_LIST.add("T2");
        CONDITION_KEY_LIST.add("T3");
        CONDITION_KEY_LIST.add("T4");
        CONDITION_KEY_LIST.add("T5");
        CONDITION_KEY_LIST.add("T6");
        CONDITION_KEY_LIST.add("T7");
        CONDITION_KEY_LIST.add("T8");
    }

    /**
     * 加密返回数据
     *
     * @return
     */
    @RequestMapping(value = "/getEncrpyUserListByCondition", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getEncryptUserListByCondition(
            @RequestBody EsourcingQueryVo queryVo
    ) throws Exception {
        JSONObject result = new JSONObject();
        if (queryVo.getSearchParams()!=null) {
            String eqOrLike = queryVo.getSearchParams().getEqOrLike();
            if (!CONDITION_KEY_LIST.contains(eqOrLike)) {
                result.put("status", 1);
                result.put("msg", "参数有误!");
                return result;
            }
        }
        JSONObject jsonObject = userService.getUserListByCondition(queryVo);
        if(queryVo.getEncrypt()){
            result.put("data", encrptyDouble(jsonObject));
        }else{
            result.put("data", jsonObject);
        }
        result.put("status", 0);
        result.put("msg", "请求成功!");
        return result;
    }

    /**
     * 双重加密返回数据
     *
     * @param jsonObject
     * @return
     */
    private String encrptyDouble(JSONObject jsonObject) throws Exception {
        if (jsonObject != null && jsonObject.size() > 0) {
            String encryptByPublicKey = RSAUtils.encryptByPublicKey(jsonObject.toJSONString(), RSAUtils.ESOURCING_PUBLIC_KEY);
//            return AESUtil.encrypt(encryptByPublicKey, AESUtil.ECRYPT_PASSWORD);
            return encryptByPublicKey;
        }
        return "";
    }
}

