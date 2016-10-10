package com.tfkz.cache.util;

/**
 * 创建一些redis使用的自定义的map/set/list/soretd set名
 */
public enum RedisCacheConstant {
    USER_MANAGEMENT_MAP,    //人员管理业务类缓存map
    HOTEL_MANAGEMENT_MAP;    //酒店管理业务类缓存map
}
/**
 * 这一块儿与业务有关
在我们的API的设计过程中，可以直接将自己封装的方法中的list/set/sorted set/map参数的类型有String改为RedisCacheConstant，
而在方法内部你调用ShardJedis的API的时候，使用String.valueOf(RedisCacheConstant)这样的方式，这样就会减少在实际开发中的代码量。
 * 
 * 
 * 
 */
