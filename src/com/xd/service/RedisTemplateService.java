package com.xd.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;


public class RedisTemplateService {
	
	@Autowired
	RedisTemplate<Object, Object> redisTemplate;
	   
	/**
	 * 添加键值，以字符串类型存储（可传入对象类型）
	 * @param key
	 * @param value
	 */
    public void set(String key, Object value) {  
        ValueOperations<Object, Object> valueOperations = redisTemplate.opsForValue();  
        valueOperations.set(key, value);  
  
        //BoundValueOperations的理解对保存的值做一些细微的操作  
//        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key);  
    }  
    
    /**
     * 添加键值，以字符串类型存储（可传入对象类型）  并设置有效时间 （单位：秒）
     * @param key
     * @param value
     * @param timeout
     */
    public void set(String key, Object value, long timeout){
    	redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }
  
    /**
     * 获取字符串
     * @param key
     * @return
     */
    public String get(String key) {  
        return (String)redisTemplate.opsForValue().get(key);  
    }  
    /**
     * 获取对象
     * @param key
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
	public <T>T get(String key, Class<T> type) {  
    	return (T)redisTemplate.opsForValue().get(key);  
    }  
  
    /**
     * 添加
     * @param key
     * @param value
     */
    public void setList(String key, List<?> value) {  
        ListOperations<Object, Object> listOperations = redisTemplate.opsForList();  
        listOperations.leftPush(key, value);  
    }  
  
    
   /* public Object getList(String key) {  
        return redisTemplate.opsForList().leftPop(key);  
    }  */
    
    /**
     * 弹出key对应列表中第一个元素
     * @param key
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
	public <T>T leftPop(String key, Class<T> type) {  
    	return (T)redisTemplate.opsForList().leftPop(key);  
    }  
    /**
     * 弹出key对应列表中最后一个元素
     * @param key
     * @param type
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T>T rightPop(String key, Class<T> type) {  
    	return (T)redisTemplate.opsForList().rightPop(key);  
    }  
  
    
    /**
     * 添加键值，存储set集合类型的数据
     * @param key
     * @param value
     */
    public void setSet(String key, Set<?> value) {  
        SetOperations<Object, Object> setOperations = redisTemplate.opsForSet();  
        setOperations.add(key, value);  
    }  
  
    /**
     * 获取key对应的set集合
     * @param key
     * @return
     */
    public Set<Object> getSet(String key) {  
        return redisTemplate.opsForSet().members(key);  
    }  
    
    
  
  
    /**
     * 存贮map类型的数据
     * @param key
     * @param value
     */
    public void setHash(String key, Map<String, ?> value) {  
        HashOperations<Object, Object, Object> hashOperations = redisTemplate.opsForHash();  
        hashOperations.putAll(key, value);  
    }  
  
    /**
     * 获取key对应的Map集合
     * @param key
     * @return
     */
    public Map<Object, Object> getHash(String key) {  
    	return redisTemplate.opsForHash().entries(key);  
    }  
  
  
    public void delete(String key) {  
        redisTemplate.delete(key);  
    }

}
