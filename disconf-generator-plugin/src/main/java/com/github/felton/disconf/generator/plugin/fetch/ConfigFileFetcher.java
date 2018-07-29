/**
 * Created on 2016年6月15日
 * Author feit
 */
package com.github.felton.disconf.generator.plugin.fetch;

import com.baidu.disconf.client.config.DisClientConfig;
import com.baidu.disconf.client.config.DisClientSysConfig;
import com.baidu.disconf.client.fetcher.FetcherFactory;
import com.baidu.disconf.client.fetcher.FetcherMgr;
import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.baidu.disconf.core.common.path.DisconfWebPathMgr;

/**
 * @author feit
 *
 */
public class ConfigFileFetcher {

	private DisClientConfig config;
	private String configFile;
	private String url;
	
	public ConfigFileFetcher(DisClientConfig config, String configFile)
	{
		this.config = config;
		this.configFile = configFile;
		this.url = getUrl(config, configFile);
	}
	
	public String getUrl(DisClientConfig config, String configFile)
	{
		// Remote URL
        String url = DisconfWebPathMgr.getRemoteUrlParameter(DisClientSysConfig.getInstance().CONF_SERVER_STORE_ACTION,
                config.APP,
                config.VERSION,
                config.ENV,
                configFile,
                DisConfigTypeEnum.FILE);
        return url;
	}
	
	public void downloadConfig(String targetPath) throws Exception
	{
		FetcherMgr fetcher = FetcherFactory.getFetcherMgr();
		fetcher.downloadFileFromServer(url, configFile, targetPath);
	}
}
