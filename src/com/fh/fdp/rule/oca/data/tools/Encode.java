package com.fh.fdp.rule.oca.data.tools;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.crypto.digest.MD5;

public class Encode {

	private Logger logger = null;

	private Cipher c = null;

	public Encode(String key, Logger log) {
		logger = log;
		key = MD5.create().digestHex16(key);
		SecretKeySpec sks = new SecretKeySpec(key.getBytes(), "AES");
		try {
			c = Cipher.getInstance("AES/ECB/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, sks);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public String encode(String res) {
		try {
			return Base64Encoder.encode(c.doFinal(res.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}
}
