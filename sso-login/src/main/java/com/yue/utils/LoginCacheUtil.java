package com.yue.utils;

import com.yue.Pojo.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 岳贺伟
 * @description  模拟缓存 并存储user
 * @data
 */
public class LoginCacheUtil {
    public static Map<String, User> loginMap= new HashMap<String,User>();
}
