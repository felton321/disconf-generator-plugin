/**
 * Created on 2016年5月3日
 * Author felton
 */
package com.github.felton.disconf.generator.plugin.generator;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.baidu.disconf.client.common.constants.SupportFileTypeEnum;
import com.baidu.disconf.client.config.DisClientConfig;
import com.baidu.disconf.client.core.filetype.FileTypeProcessorUtils;
import com.github.felton.disconf.generator.plugin.mojo.ConfigGeneratorMojo;
import com.github.felton.disconf.generator.plugin.utils.TypeSurmiser;

/**
 * @author felton
 *
 */
public class PropertyBeanGenerator extends ConfigBeanGenerator{

	public PropertyBeanGenerator(ConfigGeneratorMojo mojo, DisClientConfig config) {
		super(mojo, config);
	}

	@Override
	public void generate() throws Exception {
		Set<String> autoProps = mojo.getAutoPropertyList();
		boolean needDecrypt;
		for(String item : autoProps)
		{		
			generate(item, false);
		}
	}
	
	private  void generate(String configFile, boolean needDecrypt) throws Exception
	{		
		Map<String, Object> configValues = getConfigValues(configFile);
		decryptConfigOrNot(configValues, needDecrypt);
		ConfigClassContainer configCls = new ConfigClassContainer(this.srcRootPath, configFile);
		for(Entry<String, Object> item : configValues.entrySet())
		{
			Class<?> type = TypeSurmiser.instance().surmiseType(item.getValue());
			configCls.addField(item.getKey(), type);
			configCls.addSetter(item.getKey(), type);
			configCls.addGetter(item.getKey(), type);
		}
		configCls.generate(mojo.getTargetPackage());
	}
	
	private Map<String, Object> getConfigValues(String configFile) throws Exception
	{
		String filePath = this.targetFilePath + configFile;
	    return FileTypeProcessorUtils.getKvMap(SupportFileTypeEnum.PROPERTIES, filePath);
	}
	
	public static void main(String[] args)
	{

	}
}
