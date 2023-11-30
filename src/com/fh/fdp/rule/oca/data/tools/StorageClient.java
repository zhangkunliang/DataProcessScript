package com.fh.fdp.rule.oca.data.tools;

import java.time.Instant;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import com.fhfs.bean.FhfsMainFile;
import com.fhfs.client.FhfsClient;
import com.fhfs.client.FhfsClientConf;
import com.fhfs.client.PutRet;
import com.fhfs.proto.FhfsProtocolMsg;

/**
 */
public class StorageClient {

	private Logger logger;

	/**
	 * 发送客户端
	 */
	private FhfsClient fhfsClient;

	public StorageClient(String uri, Logger log) {
		logger = log;
		/**
		 * 全文客户端初始化配置
		 */
		FhfsClientConf conf = new FhfsClientConf();
		conf.m_agentIp = uri;
		/**
		 * 全文客户端创建
		 */
		fhfsClient = new FhfsClient();
		int ret = fhfsClient.Init(conf);
		if (ret != FhfsProtocolMsg.FsfpStatus.FsfpSuccess.getNumber()) {
			logger.error("全文存储客户端初始化失败,代理IP:{} 错误码:{}", uri, ret);
		}
		logger.info("全文存储客户端初始化成功.");
	}

	/**
	 * 退出关闭客户端
	 */
	public void destroy() {
		if (fhfsClient != null) {
			try {
				fhfsClient.Close();
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	/**
	 * 发送全文数据
	 *
	 * @param
	 * @param
	 * @return
	 */
	public String sendFile(byte[] content, String fileName, String storeType) {
		FhfsMainFile file = new FhfsMainFile();
		// 设置捕获时间
		file.SetCaptime((int) Instant.now().getEpochSecond());
		// 设置文件的存储类型，存储类型决定了文件的存储周期(后台配置存储周期)
		file.SetStoreType(storeType);
		// 设置文件的后缀名称
		file.SetExtName(StringUtils.substringAfterLast(fileName, "."));
		// 设置文件名称
		file.SetFileName(fileName);
		// 设置文件的路径
		file.SetContent(content);
		PutRet putret = new PutRet();
		// 发送文件,设置压缩方法
		int retCode = fhfsClient.Put(file, FhfsProtocolMsg.FileCompressType.CompressNone, putret);
		if (retCode != FhfsProtocolMsg.FsfpStatus.FsfpSuccess.getNumber()) {
			logger.error("send storage error. file:{}, code:{}", fileName, retCode);
		}
		return putret.url;
	}

}
