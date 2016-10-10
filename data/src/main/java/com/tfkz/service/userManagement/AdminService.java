package com.tfkz.service.userManagement;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.tfkz.cache.redis.RedisHashUtil;
import com.tfkz.cache.redis.RedisStringUtil;
import com.tfkz.cache.util.CachePrefix;
import com.tfkz.cache.util.RedisCacheConstant;
import com.tfkz.dao.userManagement.AdminDao;
import com.tfkz.model.userManagement.Admin;
import com.tfkz.vo.userManagement.AdminCacheKey;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	public boolean register(Admin admin) {
		return adminDao.register(admin);
	}

	public Admin login(String username, String password) {
		return adminDao.login(username, password);
	}

	/*********** 以下方法是为了测试mybatis中使用xml **********/
	public List<Admin> findAdmin(String username, String password, int start,
			int limit) {
		return adminDao.findAdmin(username, password, start, limit);
	}

	public Admin insertAdminWithBackId(Admin admin) {
		int record = adminDao.insertAdminWithBackId(admin);
		if (record == 1) {
			return admin;// 这时的admin已经被赋予主键了
		}
		return null;
	}

	/*************************** guava cache ********************************/

	/************ 单条件的查询，key为String ***********/
	LoadingCache<String, List<Admin>> adminListCache = CacheBuilder
			.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES)// 缓存20分钟
			.maximumSize(1000)// 最多缓存1000个对象
			.build(new CacheLoader<String, List<Admin>>() {
				public List<Admin> load(String username) throws Exception {
					List<Admin> admin = adminDao.getUserByName(username);
					System.out.println(admin);
					return admin;
				}
			});

	/************ 多条件的查询，key为Object（封装了多个条件的VO类） ***********/
	LoadingCache<AdminCacheKey, List<Admin>> adminsCache = CacheBuilder
			.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES) // 缓存项在给定时间内（60min）没有被写访问（创建或覆盖），则回收
			.maximumSize(100) // 最多缓存100项
			.build(new CacheLoader<AdminCacheKey, List<Admin>>() {
				public List<Admin> load(AdminCacheKey key) throws Exception {
					return adminDao.findAdmin(key.getUsername(),
							key.getPassword(), key.getStart(), key.getLimit());
				}
			});

	public List<Admin> findByUsername(String username) {
		List<Admin> adminList = null;

		try {
			adminList = adminListCache.get(username);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return adminList;
	}

	public List<Admin> findAdminList(String username, String password,
			int start, int limit) {
		/*
		 * 注意：
		 * 如果以一个新建的对象做为key的话，因为每次都是新建一个对象，所以这样的话，实际上每次访问key都是不同的，即每次访问都是重新进行缓存;
		 * 但是实际上，我们想要根据对象的属性来判断对象是否相等，只需要根据这些属性重写对象的hashCode与equals方法即可，
		 * 所以重写了AdminCacheKey类的hashCode和equals方法
		 * ，这样，每次访问的话，就会以每个条件是否相等来判断对象（即key）是否相等了，这一块儿的缓存就会起作用了
		 */
		AdminCacheKey cacheKey = new AdminCacheKey(username, password, start,
				limit);
		List<Admin> adminList = null;
		try {
			System.out.println(cacheKey);
			adminList = adminsCache.get(cacheKey);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return adminList;
	}
	
	/*********************redis********************/
    public Admin findAdminByIdFromRedis(int id) {
        //从缓存中获取数据
        String adminStr = RedisStringUtil.get(CachePrefix.USER_MANAGEMENT, String.valueOf(id));
        //若缓存中有，直接返回
        if(StringUtils.isNoneBlank(adminStr)){
            return Admin.parseJsonToAdmin(adminStr);
        }
        //若缓存中没有，从数据库查询
        Admin admin = adminDao.getUserById(id);
        //若查询出的数据不为null
        if(admin!=null){
            //将数据存入缓存
            RedisStringUtil.set(CachePrefix.USER_MANAGEMENT, String.valueOf(id), admin.toJson());
        }
        //返回从数据库查询的admin（当然也可能数据库中也没有，就是null）
        return admin;
    }
    
    public Admin findAdminByIdFromRedisHash(int id){
    	String adminStr =  RedisHashUtil.hget(String.valueOf(RedisCacheConstant.USER_MANAGEMENT_MAP), String.valueOf(id));
    	
    	if(StringUtils.isNoneBlank(adminStr)){
    		return Admin.parseJsonToAdmin(adminStr);
    	}
    	//若缓存中没有，从数据库查询
    	Admin admin = adminDao.getUserById(id);
    	if(admin!=null){
    		RedisHashUtil.hset(String.valueOf(RedisCacheConstant.USER_MANAGEMENT_MAP), String.valueOf(id), admin.toJson());
    	}
    	
    	return admin;
    	
    }

}
