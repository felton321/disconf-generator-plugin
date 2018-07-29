/**
 * Created on 2016年6月15日
 * Author feit
 */
package com.github.felton.disconf.generator.plugin.utils;

/**
 * @author feit
 * 按照一定的规则生成源码名称
 */
public class CodeNamer {

	/**
	 * 获取class名称
	 * @param configName
	 * @return
	 */
	public static String getClsNameFromConfigName(String configName)
	{
		String className = configName.substring(0, configName.indexOf('.'));
		return getTidyNameFromStr(className);
	}
	
	/**
	 * 获取变量名称
	 * @param itemName
	 * @return
	 */
	public static String getFieldNameFromItemName(String itemName)
	{
		String tempName = itemName.replace('.', '-');
		String fieldName = getTidyNameFromStr(tempName);
		return fieldName.replace(fieldName.charAt(0), (char)(fieldName.charAt(0) + 32));
	}
	
	/**
	 * 获取变量的set方法名称
	 * @param itemName
	 * @return
	 */
	public static String getSetterNameFromItemName(String itemName)
	{
		String tempName = itemName.replace('.', '-');
		return "set" + getTidyNameFromStr(tempName);
	}
	
	/**
	 * 获取变量的get方法名称
	 * @param itemName
	 * @return
	 */
	public static String getGetterNameFromItemName(String itemName, boolean isBool)
	{
		String tempName = itemName.replace('.', '-');
		String suffixName = getTidyNameFromStr(tempName);
		if(isBool)
			return "is" + suffixName;
		else
			return "get" + suffixName;
	}
	
	private static String getTidyNameFromStr(String src)
	{
		char[] array = src.toLowerCase().toCharArray();
		boolean match = true;
		for(int i = 0; i < array.length; i ++)
		{
			if(array[i] == '_' || array[i] == '-')
			{
				match = true;
				array[i] = '&';
			}else if(match){
				array[i] -= 32;
				match = false;
			}
		}
		return String.valueOf(array).replaceAll("&", "");
	}
}
