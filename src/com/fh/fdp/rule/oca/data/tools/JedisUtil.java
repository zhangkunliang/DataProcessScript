package com.fh.fdp.rule.oca.data.tools;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {

	private Logger logger;
	private static final int TIMEOUT = 5000;

	private JedisPool pool = null;

	public JedisUtil(String ip, int port, String passwd, Logger log) {
		logger = log;
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(10);
		config.setMaxTotal(25);
		if (StringUtils.isNotBlank(passwd)) {
			pool = new JedisPool(config, ip, port, TIMEOUT, passwd);
		} else {
			pool = new JedisPool(config, ip, port, TIMEOUT);
		}
	}

	public void close() {
		if (pool != null && !pool.isClosed()) {
			pool.close();
		}
	}

	public String getRandom(int dbNum) {
		try (Jedis client = pool.getResource()) {
			client.select(dbNum);
			return client.randomKey();
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public String add(String key, String val, long expire, int dbNum) {
		try (Jedis client = pool.getResource()) {
			client.select(dbNum);
			return client.setex(key, expire, val);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public String add(String key, String val, int dbNum) {
		try (Jedis client = pool.getResource()) {
			client.select(dbNum);
			return client.set(key, val);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public String get(String key, int dbNum) {
		try (Jedis client = pool.getResource()) {
			client.select(dbNum);
			return client.get(key);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public void del(String key, int dbNum) {
		try (Jedis client = pool.getResource()) {
			client.select(dbNum);
			client.del(key);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public boolean exist(String key, int dbNum) {
		try (Jedis client = pool.getResource()) {
			client.select(dbNum);
			return client.exists(key);
		} catch (Exception e) {
			logger.error("", e);
		}
		return false;
	}

	public void flashDb(int dbNum) {
		try (Jedis client = pool.getResource()) {
			client.select(dbNum);
			client.flushDB();
		} catch (Exception e) {
			logger.error("", e);
		}
	}


}
