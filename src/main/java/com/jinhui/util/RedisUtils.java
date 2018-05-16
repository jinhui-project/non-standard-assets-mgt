package com.jinhui.util;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;



public class RedisUtils {

	/**
	 * 
	 * 
	 * 
	 * @param 
	 * @param 
	 * @param 
	 *            数据库
	 * @throws Exception
	 */
	public static void saveAttributeForHashDb(String key, Serializable obj,
			int dbnum) {
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		redisTemplate.opsForValue().set(key, obj);
	}

	/**
	 * 
	 * 
	 * 
	 * @param 
	 * @param 
	 *            
	 * @return
	 * @throws Exception
	 */
	public static Object getAttributeForHashDb(String key, int dbnum)
			throws Exception {
		// SpringContextHolder.getApplicationContext().getBean(JedisConnectionFactory.class).setDatabase(dbnum);
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Object getAttributeForHash(String key, Object hashKey)
			throws Exception {
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	/**
	 *
	 * 
	 * @param 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public static Object setAttributeForHash(String key, Object hashKey,
			Object value) throws Exception {
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		redisTemplate.opsForHash().put(key, hashKey, value);
		return value;
	}

	/**
	 * 
	 * 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public static boolean removeAttributeForHash(String key, Object hashKey)
			throws Exception {
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		try {
			redisTemplate.opsForHash().delete(key, hashKey);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public static boolean removeAttributeForHash(String key) throws Exception {
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		try {
			Set<Object> objs = redisTemplate.opsForHash().keys(key);
			if (null == objs)
				return true;
			for (Object object : objs) {
				redisTemplate.opsForHash().delete(key, object);
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @param 
	 *            
	 * @return
	 * @throws Exception
	 */
	public static Set<Object> getHashKeyListForHash(String key)
			throws Exception {
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		return redisTemplate.opsForHash().keys(key);
	}

	/**
	 *
	 * 
	 * @param 
	 *          
	 * @param 
	 *           
	 * @throws Exception
	 */
	public static void removeValue(String key, String groupId) throws Exception {
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		redisTemplate.delete(groupId + key);
	}

	public static void delete(String key){
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		redisTemplate.delete(key);
	}

	/**
	 * 
	 * 
	 * @param 
	 *           
	 * @param 
	 *            
	 * @return
	 * @throws Exception
	 */
	public static Serializable getValue(String key, String groupId) {
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		return redisTemplate.opsForValue().get(groupId + key);
	}

	public static Serializable getValue(String key){
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 
	 * 
	 * 
	 * @param 
	 *            
	 * @param 
	 *            
	 * @param 
	 *           
	 *           
	 * @throws Exception
	 */
	public static void setValue(String key, String groupId, Serializable value,
			long timeout) throws Exception {
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		redisTemplate.opsForValue().set(groupId + key, value, timeout,
				TimeUnit.MINUTES);
	}

	public static void setValue(String key, String groupId, Serializable value,
								long timeout, TimeUnit timeUnit) {
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		redisTemplate.opsForValue().set(groupId + key, value, timeout, timeUnit);
	}

	public static void setValue(String key, Serializable value, long timeout,
								TimeUnit timeUnit){
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
	}

	/**
	 * 
	 * 
	 * @param 
	 * @param 
	 * @param 
	 * @throws Exception
	 */
	public static void setValue(String key, String groupId, Serializable value) {
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		redisTemplate.opsForValue().set(groupId + key, value);
	}

	/**
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public static List<Serializable> getValues(String groupId) throws Exception {
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		List<Serializable> serializables = new ArrayList<Serializable>();
		Set<Serializable> keys = (Set<Serializable>) redisTemplate.keys(groupId
				+ "*");
		if (keys == null || keys.isEmpty()) {
			return null;
		}
		Iterator<Serializable> iterator = keys.iterator();
		while (iterator.hasNext()) {
			serializables.add(redisTemplate.opsForValue().get(iterator.next()));
		}
		return serializables;
	}

	public static Set keys(String groupId){
		RedisTemplate<Serializable, Serializable> redisTemplate = SpringContextHolder
				.getBean("redisTemplate");
		Set<Serializable> keys = redisTemplate.keys(groupId+"*");
		return keys;
	}

	public static Set extractAttributeKey(String sessionId){
		Set<String> keys = keys(sessionId);
		Set attributeKeys = new HashSet();
		for(String key:keys){
			attributeKeys.add(key.substring(sessionId.length()));
		}
		return attributeKeys;
	}
}
