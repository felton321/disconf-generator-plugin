/**
 * Created on 2016年6月15日
 * Author feit
 */
package com.github.felton.disconf.generator.plugin.utils;

/**
 * @author feit
 *
 */
public final class TypeSurmiser {

	private ConfigItemParser rootParser;
	private static TypeSurmiser Instance = new TypeSurmiser();
	private TypeSurmiser()
	{
		rootParser = new BooleanParser();
		rootParser.next(new IntParser())/*.next(new SwitchParser())*/.next(new StringParser());
	}
	public static TypeSurmiser instance()
	{
		return Instance;
	}
	public Class<?> surmiseType(Object configValue)
	{
		if(configValue == null)
			configValue = "";
		return surmiseType(rootParser, configValue.toString());
	}
	private Class<?> surmiseType(ConfigItemParser parser, String configValue)
	{
		try{
			parser.tryParser(configValue);
			return parser.getType();
		}catch(Exception e){
			if(parser.nextParser != null)
			{
				return surmiseType(parser.nextParser, configValue);
			}else
				return null;
		}	
	}
	
	public static void main(String[] args)
	{
		System.out.println(TypeSurmiser.instance().surmiseType("true"));
		System.out.println(TypeSurmiser.instance().surmiseType("FALSE"));
		System.out.println(TypeSurmiser.instance().surmiseType("123"));
		System.out.println(TypeSurmiser.instance().surmiseType("f1245fds"));
		System.out.println(TypeSurmiser.instance().surmiseType("ON"));
		System.out.println(TypeSurmiser.instance().surmiseType("off"));
		System.out.println(TypeSurmiser.instance().surmiseType("fdssafdsa"));
		System.out.println(TypeSurmiser.instance().surmiseType("FVD"));
	}
}
