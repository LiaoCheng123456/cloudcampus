package com.rh.cloudcampus.edu.util.redis;

import com.alibaba.fastjson.JSON;
import com.rh.cloudcampus.edu.common.BaseData;
import com.rh.cloudcampus.edu.util.redis.RedisPool;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

import java.util.*;

@Service
public class RedisUtil extends BaseData {

	/**
	 * 获取 -根据key获取value
	 * @param key
	 * @return object
	 */
	@SuppressWarnings("static-access")


    private static final String SNXD = "sdm_";

	public static Object getKey(String key) {
		ShardedJedis jedisShardInfo = com.rh.cloudcampus.edu.util.redis.RedisPool.getJedisShardInfo();
		String value = "";
		try {
		    key=SNXD+key;
			value = jedisShardInfo.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用RedisUtil.getKey失败，key:"+key+",报错原因："+e);
		} finally {
			jedisShardInfo.close();
		}
		return JSON.parseObject(value, Object.class);
	}

	/**
	 * 存入-默认不设置有效期
	 * @param key 
	 * @param valueobj
	 */
	public static void setKey(String key, Object valueobj) {
	    setKey(key, valueobj, 0);
	}

	/**
	 * 存入-将value转成json
	 * @param expire 时间为天
	 * @param key
	 * @param valueobj
	 */
	@SuppressWarnings("static-access")
	public static void setKey(String key, Object valueobj, int expire) {
        key=SNXD+key;
		String value = json.toJSONString(valueobj);
		ShardedJedis jedisShardInfo = com.rh.cloudcampus.edu.util.redis.RedisPool.getJedisShardInfo();
		try {
			if (expire > 0) {
				jedisShardInfo.setex(key, expire * 86400, value);
			} else {
				jedisShardInfo.set(key, value);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用RedisUtil.setKey失败，key:"+key+",报错原因："+e);
		} finally {
			jedisShardInfo.close();
		}
	}
	
	
	
	/**
	 * 存入 时间为秒  
	 * @param key
	 * @param value
	 * @param expire
	 */
	public static String setKeyBySecond(String key, String value, int expire) {
        key=SNXD+key;
		String result = "";
		ShardedJedis jedisShardInfo = com.rh.cloudcampus.edu.util.redis.RedisPool.getJedisShardInfo();
		try {
			result =jedisShardInfo.setex(key, expire, value);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用RedisUtil.setKeyBySecond失败，key:"+key+",报错原因："+e);
		} finally {
			jedisShardInfo.close();
		}
		return result;
	}


	/**
	 * 设置过期时间
	 * @param key
	 * @param expire 过期时间（天） 传0则不过期
	 */
	public static void setExpire(String key, int expire) {
        key=SNXD+key;
		ShardedJedis jedisShardInfo = com.rh.cloudcampus.edu.util.redis.RedisPool.getJedisShardInfo();
		try {
			if (expire != 0) {
				jedisShardInfo.expire(key, expire * 86400);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用RedisUtil.setExpire失败，key:"+key+",报错原因："+e);
		} finally {
			jedisShardInfo.close();
		}
	}

	/**
	 * 删除
	 * @param key
	 */
	public static String remove(String key) {
        key=SNXD+key;
		ShardedJedis jedisShardInfo = com.rh.cloudcampus.edu.util.redis.RedisPool.getJedisShardInfo();
		String result="";
		try {
			result=jedisShardInfo.del(key).toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用RedisUtil.remove失败，key:"+key+",报错原因："+e);
		} finally {
			jedisShardInfo.close();
		}
		return result;
	}
	
/**
 * ----------------------分布式锁相关-------------------------------------	
 */
	
	/**
	 * 获取分布式锁
	 * @param key
	 * @param value
	 * @param expire
	 */
	public static String tryGetDistributedLock(String key, String value, int expire) {
        key=SNXD+key;
		String result = "";
		ShardedJedis jedisShardInfo = com.rh.cloudcampus.edu.util.redis.RedisPool.getJedisShardInfo();
		try {
			result = jedisShardInfo.set(key, value, "NX", "EX", expire);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("调用RedisUtil.tryGetDistributedLock失败，key:"+key+",报错原因："+e);
		} finally {
			jedisShardInfo.close();
		}
		return result;
	}
	
/**
 * 	----------消息队列相关------------
 */
	/**
     * 存储队列 顺序存储
     *
     */
	public static void lpush(byte[] key, byte[] value) {

		ShardedJedis jedisShardInfo = com.rh.cloudcampus.edu.util.redis.RedisPool.getJedisShardInfo();
        try {
            jedisShardInfo.lpush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	jedisShardInfo.close();
        }
    }
	
	/**
     * 获取队列数据
     * @return
     */
    public static byte[] rpop(byte[] key) {
 
        byte[] bytes = null;
        ShardedJedis jedisShardInfo = com.rh.cloudcampus.edu.util.redis.RedisPool.getJedisShardInfo();
        try {
            bytes = jedisShardInfo.rpop(key);
        } catch (Exception e) {
            e.printStackTrace(); 
        } finally {
        	jedisShardInfo.close();
        }
        return bytes;
    }
    /**
     * 分片下模糊查询，测试demo方法
     * @param pattern
     * @return
     */
	public static Set<String> getPatternKeys(String pattern){
        pattern=SNXD+pattern;
		Set<String> stringSet = new HashSet();
		ShardedJedis shardedJedis = com.rh.cloudcampus.edu.util.redis.RedisPool.getJedisShardInfo();
		try {
			final Collection<Jedis> allShards = shardedJedis.getAllShards();
			for (Jedis jedis : allShards) {
				try {
					final Set<String> keys = jedis.keys(pattern);
					stringSet.addAll(keys);
				}catch (Exception e) {
					e.printStackTrace(); 
				} finally {
					jedis.close();
		        }		
			}
		}catch (Exception e) {
            e.printStackTrace(); 
        } finally {
        	shardedJedis.close();
        }
		return stringSet;
	}

/**
 *	-----------------hash--------------------
 */
	/**
	 * hash表设值
	 * 描述：将哈希表key中的域field的值设为value。如果key不存在，一个新的哈希表被创建并进行HSET操作。如果域field已经存在于哈希表中，旧值将被覆盖。
	 * 参数：key field value
	 * 返回值：如果field是哈希表中的一个新建域，并且值设置成功，返回1。如果哈希表中域field已经存在且旧值已被新值覆盖，返回0。
	 */
	public static String hset(String key, String field, Object value) {
        key=SNXD+key;
		String result ="";
		ShardedJedis shardedJedis = com.rh.cloudcampus.edu.util.redis.RedisPool.getJedisShardInfo();
        try{
        	result =shardedJedis.hset(key, field, json.toJSONString(value) ).toString();
        }catch (Exception e) {
            e.printStackTrace(); 
        } finally {
        	shardedJedis.close();
        }
        return result;
    }
	/**
     * hash表取值
     * 返回哈希表 key 中给定域 field 的值。
     */
    public static Object hget(String key,String field) {
        key=SNXD+key;
    	ShardedJedis shardedJedis = com.rh.cloudcampus.edu.util.redis.RedisPool.getJedisShardInfo();
    	String value ="";
    	try{
    		Map<String, String> map = new HashMap<>();
	        map = shardedJedis.hgetAll(key);
	        value =map.get(field);
    	}catch (Exception e) {
            e.printStackTrace(); 
        } finally {
        	shardedJedis.close();
        }
    	return JSON.parseObject(value,Object.class);
    }
    
    /**
     * hash表 获取整个hash表
     * 返回哈希表 key 中给定域 field 的值。
     */
    public static Map<String, String> hgetAll(String key) {
        key=SNXD+key;
    	ShardedJedis shardedJedis = com.rh.cloudcampus.edu.util.redis.RedisPool.getJedisShardInfo();
    	Map<String, String> map = new HashMap<>();
    	try{
	        map = shardedJedis.hgetAll(key);
    	}catch (Exception e) {
            e.printStackTrace(); 
        } finally {
        	shardedJedis.close();
        }
    	return map;
    }
    
    /**
     * hash表 删除指定域
     */
    public static String hdel(String key,String field) {
        key=SNXD+key;
    	ShardedJedis shardedJedis = com.rh.cloudcampus.edu.util.redis.RedisPool.getJedisShardInfo();
    	String result="";
        try{
        	result = shardedJedis.hdel(key,field).toString();
    	}catch (Exception e) {
            e.printStackTrace(); 
        } finally {
        	shardedJedis.close();
        }
        return result;
    }
    /**
     * main方法
     */
	public static void mainL(String[] args) {
		
//		System.out.println(getKey("zj"));
//		System.out.println(setKeyBySecond("zj","zj",1000));
//		System.out.println(setKeyBySecond("zj","j1",100));
		//selectDb();
//		long begin= WSPDate.getCurrentTimestemp();
//		for(int i=0;i<10000;i++){
//			System.out.println(getKey("A_DEVICE_COMMON"));
//			
//		}
//		System.out.println(WSPDate.getCurrentTimestemp()-begin);
/**
 * -------------hash相关开始
 */
//		PageData pd =new PageData();
//		pd.put("id","222222222");
//		String s = hset("A_HASH_USER","403861432",pd);
//		System.out.println(s);
		
//		System.out.println(hget("A_HASH_USER","403861432"));
		
//		HashMap<String, String> map = (HashMap<String, String>)hgetAll("A_HASH_USER");
//		for(String key :map.keySet()){
//			System.out.println(map.get(key));
//		}
//		System.out.println(hdel("A_HASH_USER","403861432"));
/**
 * ---------------hash相关结束
 */

		
//		long a = new Date().getTime();
//		System.out.println(a);
//		Set<String> stringSet = getPatternKeys("JWT_USER_*");
//		for(String s:stringSet){
//			System.out.println(s);
//			System.out.println(getKey(s));
//		}
//		System.out.println(stringSet.size());
//		long b = new Date().getTime();
//		System.out.println(b);
//		System.out.println(b-a);
//		
//		
	
		
//		RedisUtil.lpush("test".getBytes(),"1".getBytes());
//		RedisUtil.lpush("test".getBytes(),"2".getBytes());
//		
//		for(int i=0;i<5;i++){
//			System.out.println(new String(RedisUtil.rpop("test".getBytes())));
//		}
		// System.out.println(new Date().getTime());
//		setKey("zj", "zhangj", 100000);
//		System.out.println(new Date().getTime());
//		System.out.println(getKey("005779EB-4AC9-48A8-B9BF-68CEA571E2C0"));
//		System.out.println(new Date().getTime());
		// setKey("rex","zhangj",1);
//		System.out.println(new Date().getTime());
//		System.out.println(getKey("005779EB-4AC9-48A8-B9BF-68CEA571E2C0"));
//		System.out.println(new Date().getTime());
		
//		System.out.println(remove("zj"));
		
		
		
	}

	
	/**
     * 测试代码
     * 选择数据库
     */
    public static void selectDb(){
    	ShardedJedis shardedJedis = RedisPool.getJedisShardInfo();
    	Collection<Jedis> collection=shardedJedis.getAllShards();
    	Iterator<Jedis> jedis = collection.iterator();
    	while(jedis.hasNext()){
    	jedis.next().select(2);
    	}
    }

//
//	public static void main(String[] args) {
//		RedisUtil sc=new RedisUtil();
//		sc.setKey("123123",123123123,1);
//	}
}
