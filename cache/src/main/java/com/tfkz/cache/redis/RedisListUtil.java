package com.tfkz.cache.redis;

import java.util.List;

import redis.clients.jedis.ShardedJedis;

public class RedisListUtil  extends RedisBaseUtil{
	
	public static void lpush(String list, String... values){
		boolean broken = false;//标记：该操作是否被异常打断而没有正常结束
		
		ShardedJedis jedis = null; //
		
		try {
			jedis =  getJedis();
			if(jedis == null){
				broken = true;
				return ;
			}
			jedis.lpush(list, values);
		} catch (Exception e) {
			 broken = true;
		}finally{
			returnJedis(jedis, broken);
		}
	}
	
	/**
     * 从左边（首部）加入列表
     * 并指定列表缓存过期时间
     */
    public static void lpush(String list, int expire, String... values){
        boolean broken = false;//标记：该操作是否被异常打断而没有正常结束
        ShardedJedis jedis = null;
        try {
            jedis = getJedis();//获取jedis实例
            if(jedis==null){
                broken = true;
                return;
            }
            /*
             * lpush(String key, String... strings);
             * 返回push之后的list中包含的元素个数
             */
            jedis.lpush(list, values);
            jedis.expire(list, expire);//为该list设置缓存过期时间
        } catch (Exception e) {
            broken = true;
        }finally{
            returnJedis(jedis, broken);
        }
    }
    
    /**
     * 从右边（尾部）加入列表
     */
    public static void rpush(String list, String... values){
        boolean broken = false;//标记：该操作是否被异常打断而没有正常结束
        ShardedJedis jedis = null;
        try {
            jedis = getJedis();//获取jedis实例
            if(jedis==null){
                broken = true;
                return;
            }
            jedis.rpush(list, values);
        } catch (Exception e) {
            broken = true;
        }finally{
            returnJedis(jedis, broken);
        }
    }
    
    /**
     * 从右边（尾部）加入列表
     * 并设置缓存过期时间
     */
    public static void rpush(String list, int expire, String... values){
        boolean broken = false;//标记：该操作是否被异常打断而没有正常结束
        ShardedJedis jedis = null;
        try {
            jedis = getJedis();//获取jedis实例
            if(jedis==null){
                broken = true;
                return;
            }
            jedis.rpush(list, values);
            jedis.expire(list, expire);//设置缓存过期时间
        } catch (Exception e) {
            broken = true;
        }finally{
            returnJedis(jedis, broken);
        }
    }
    
    /**
     * 设置list中index位置的元素
     * index==-1表示最后一个元素
     */
    public static void lSetIndex(String list, long index, String value){
        boolean broken = false;//标记：该操作是否被异常打断而没有正常结束
        ShardedJedis jedis = null;
        try {
            jedis = getJedis();//获取jedis实例
            if(jedis==null){
                broken = true;
                return;
            }
            jedis.lset(list, index, value);
        } catch (Exception e) {
            broken = true;
        }finally{
            returnJedis(jedis, broken);
        }
    }
    
    /***************************获取缓存*****************************/
    /**
     * 从左边（首部）出列表
     */
    public static String lpop(String list){
        boolean broken = false;//标记：该操作是否被异常打断而没有正常结束
        ShardedJedis jedis = null;
        try {
            jedis = getJedis();//获取jedis实例
            if(jedis==null){
                broken = true;
                return null;
            }
            return jedis.lpop(list);
        } catch (Exception e) {
            broken = true;
        }finally{
            returnJedis(jedis, broken);
        }
        return null;
    }
    
    /**
     * 从右边出列表
     */
    public static String rpop(String list){
        boolean broken = false;//标记：该操作是否被异常打断而没有正常结束
        ShardedJedis jedis = null;
        try {
            jedis = getJedis();//获取jedis实例
            if(jedis==null){
                broken = true;
                return null;
            }
            return jedis.rpop(list);
        } catch (Exception e) {
            broken = true;
        }finally{
            returnJedis(jedis, broken);
        }
        return null;
    }
    
    /**
     * 返回list中index位置的元素
     */
    public static String lGetIndex(String list, long index){
        boolean broken = false;//标记：该操作是否被异常打断而没有正常结束
        ShardedJedis jedis = null;
        try {
            jedis = getJedis();//获取jedis实例
            if(jedis==null){
                broken = true;
                return null;
            }
            return jedis.lindex(list, index);
        } catch (Exception e) {
            broken = true;
        }finally{
            returnJedis(jedis, broken);
        }
        return null;
    }
    
    /**
     * 返回list指定区间[start,end]内的元素 
     */
    public static List<String> lrange(String list, long start, long end){
        boolean broken = false;//标记：该操作是否被异常打断而没有正常结束
        ShardedJedis jedis = null;
        try {
            jedis = getJedis();//获取jedis实例
            if(jedis==null){
                broken = true;
                return null;
            }
            return jedis.lrange(list, start, end);//
        } catch (Exception e) {
            broken = true;
        }finally{
            returnJedis(jedis, broken);
        }
        return null;
    }
    
    /**
     * 返回list内的全部元素 
     */
    public static List<String> lrange(String list){
        return lrange(list, 0, -1);
    }
    
    /*** 删除缓存（删除整个list，直接用RedisStringUtil的delete就好）******/
    /**
     * 让list只保留指定区间[start,end]内的元素，不在指定区间内的元素都将被删除 
     */
    public static void ltrim(String list, long start, long end){
        boolean broken = false;//标记：该操作是否被异常打断而没有正常结束
        ShardedJedis jedis = null;
        try {
            jedis = getJedis();//获取jedis实例
            if(jedis==null){
                broken = true;
                return;
            }
            jedis.ltrim(list, start, end);
        } catch (Exception e) {
            broken = true;
        }finally{
            returnJedis(jedis, broken);
        }
    }
    
    /**
     * 删除list中所有与value相等的元素
     * 注意：
     * count
     * ==0 ：删除表中所有与value相等的元素
     * >0：从表头开始向表尾搜索，移除count个与value相等的元素
     * <0：从表尾开始向表头搜索，移除count个与value相等的元素
     */
    public static void lremove(String list, long count, String value){
        boolean broken = false;//标记：该操作是否被异常打断而没有正常结束
        ShardedJedis jedis = null;
        try {
            jedis = getJedis();//获取jedis实例
            if(jedis==null){
                broken = true;
                return;
            }
            jedis.lrem(list, count, value);//返回删除了多少个元素
        } catch (Exception e) {
            broken = true;
        }finally{
            returnJedis(jedis, broken);
        }
    }
    
    /**
     * 删除list中所有与value相等的元素
     */
    public static void lremove(String list, String value){
        lremove(list, 0, value);
    }
    
    /***************************其他*****************************/
    /**
     * 返回list中共有多少个元素
     */
    public static long llength(String list){
        boolean broken = false;//标记：该操作是否被异常打断而没有正常结束
        ShardedJedis jedis = null;
        try {
            jedis = getJedis();//获取jedis实例
            if(jedis==null){
                broken = true;
                return 0;
            }
            return jedis.llen(list);
        } catch (Exception e) {
            broken = true;
        }finally{
            returnJedis(jedis, broken);
        }
        return 0;
    }
    
    public static void main(String[] args) {
        lpush("adminList", "jigang");
        lpush("adminList", "nana");//头部
        System.out.println(llength("adminList"));
        System.out.println(lrange("adminList"));
        //lpop("adminList");
        //System.out.println(llength("adminList"));
        //ltrim("adminList", 0, 1);
        //System.out.println(lrange("adminList"));
        //System.out.println(lpop("adminList"));//左边进左边出，栈（后进先出）
        //System.out.println(rpop("adminList"));//左边进右边出，队列（先进先出）
        System.out.println(lGetIndex("adminList",1));
        
    }
    

}
