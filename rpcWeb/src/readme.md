分布式缓存redis：

http://www.cnblogs.com/java-zhao/p/5166931.html
1、redisFactory:
	构建ShardJedisPool---------》生产ShardJedis连接实例
2、RedisBaseUtil：使用redisfactory。
	获取获取ShardJedis连接与归还ShardJedis连接
	
具体的操作类:
	针对redis的5中数据结构的操作
	
3.1：RedisStringUtil：	redis的第一种数据结构--字符串，操作类
3.2：RedisListUtil：		redis的第二种数据结构--list，操作类
3.3：RedisSetUtil：		redis的第三种数据结构--set，操作类
3.4：RedisSortedSetUtil：redis的第四种数据结构--sorted set，操作类
3.5：RedisHashUtil：		redis的第五种数据结构--hash，操作类



分析：
注意：
	String、hash具有按key查找value的功能
	list、set、sorted set没有按key查找的功能
适用场景：

	需要按key查找value的，用hash和String
	set类的两种典型应用场景：（归结为一句话：存储特殊的key，之后可能还需要根据这些key进行数据库的查询）
		》当有用户注册或者用户信息修改或用户被删除之后，我们将其ID放入缓存，之后可能会启动一个定时任务，定时扫描该set中是否有ID存在，如果有，
		说明有用户信息发生变化，然后再进行一些操作，操作之后将set清空。之后继续循环上述的方式
	            》存储一个网站的活跃的用户ID，之后我们可以确定set中存在的所有ID都是活跃用户，之后可以按照他们的ID进行数据库的查询；如果用MySQL去做这个事儿，可 能需 要扫描全表，然后再进行一些操作
	
	