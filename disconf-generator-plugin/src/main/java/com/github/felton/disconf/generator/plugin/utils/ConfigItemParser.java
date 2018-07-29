/**
 * Created on 2016年6月15日
 * Author feit
 */
package com.github.felton.disconf.generator.plugin.utils;

/**
 * @author feit
 *
 */
public abstract class ConfigItemParser {

	protected ConfigItemParser nextParser;
	
	public ConfigItemParser next(ConfigItemParser parser)
	{
		this.nextParser = parser;
		return parser;
	}
	
	public abstract void tryParser(String configValue);
	public abstract Class<?> getType();
}

class BooleanParser extends ConfigItemParser
{
	@Override
	public void tryParser(String configValue) {
		if(!(configValue.equalsIgnoreCase("true") || configValue.equalsIgnoreCase("false")))
		{
			throw new RuntimeException("Can not parse to boolean: " + configValue);
		}
	}

	@Override
	public Class<?> getType() {
		return Boolean.class;
	}
}

class IntParser extends ConfigItemParser
{

	@Override
	public void tryParser(String configValue) {
		Integer.parseInt(configValue);
	}

	@Override
	public Class<?> getType() {
		return Integer.class;
	}
}

class SwitchParser extends ConfigItemParser
{

	@Override
	public void tryParser(String configValue) {
		if(!(configValue.equalsIgnoreCase("on") || configValue.equalsIgnoreCase("off")))
		{
			throw new RuntimeException("Can not parse to switch: " + configValue);
		}
	}

	@Override
	public Class<?> getType() {
		return Boolean.class;
	}
}

class StringParser extends ConfigItemParser
{

	@Override
	public void tryParser(String configValue) {
		
	}

	@Override
	public Class<?> getType() {
		return String.class;
	}
	
}
