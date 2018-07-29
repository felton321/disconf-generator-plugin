/**
 * Created on 2016年5月3日
 * Author felton
 */
package com.github.felton.disconf.generator.plugin.generator;

import java.util.Map;

import com.baidu.disconf.client.config.DisClientConfig;
import com.github.felton.disconf.generator.plugin.fetch.ConfigFileFetcher;
import com.github.felton.disconf.generator.plugin.mojo.ConfigGeneratorMojo;

/**
 * @author felton
 *
 */
public abstract class ConfigBeanGenerator {

	DisClientConfig config;
	ConfigGeneratorMojo mojo;
	String srcRootPath;
    String targetFilePath;
    
	public ConfigBeanGenerator(ConfigGeneratorMojo mojo, DisClientConfig config)
	{
		this.mojo = mojo;
		this.config = config;
	}
	
	public void setSrcRootPath(String rootPath)
	{
		this.srcRootPath = rootPath; 
	}
	
	public void setTargetFilePath(String targetFilePath) {
		this.targetFilePath = targetFilePath;
	}
	
	
	public void downloadOrNot()
	{
		//如果没有配置remote，则不下载
		if(!config.ENABLE_DISCONF)
			return;
		for(String configFile : mojo.getAutoPropertyList())
		{
			ConfigFileFetcher fetcher = new ConfigFileFetcher(config, configFile);
			try {
				fetcher.downloadConfig(targetFilePath);
			} catch (Exception e) {
				throw new RuntimeException("Can not download config file: " + configFile, e);
			}
		}
	}
	public abstract void generate() throws Exception;

	protected void decryptConfigOrNot(Map<String, Object> configValues, boolean needDecrypt)
	{
		if(!needDecrypt) return;
		Object value = null;
		for(String key : configValues.keySet())
		{
			value = configValues.get(key);
			if(value != null)
			{
				configValues.put(key, value.toString());
			}
		}
	}
}
