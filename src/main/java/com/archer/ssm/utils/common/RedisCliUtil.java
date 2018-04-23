package com.archer.ssm.utils.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis client工具类
 */
@Component
public class RedisCliUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 写入key-value
     * @param key
     * @param value
     * @param timeout 分钟
     */
    public void set(String key, String value, Long timeout){
        stringRedisTemplate.opsForValue().set(key,value,timeout, TimeUnit.MINUTES);
    }

    /**
     * 根据可以读value
     * @param key
     * @return
     */
    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 是否存在key值
     * @param key
     * @return
     */
    public boolean isExist(String key){
        return stringRedisTemplate.hasKey(key);
    }

    /**
     * 删除key值
     * @param key
     */
    public void delet(String key){
        stringRedisTemplate.delete(key);
    }

}
