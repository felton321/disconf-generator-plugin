/**
 * Created on 2016年6月15日
 * Author feit
 */
package com.github.felton.disconf.generator.plugin.generator;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.felton.disconf.generator.plugin.utils.CodeNamer;
import com.github.felton.disconf.generator.plugin.utils.CodeTemplate;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

/**
 * @author feit
 *
 */
public class ConfigClassContainer {

	private String targetPath;
	private String configName;
	private Map<Class<?>, Object> members = new HashMap<>();
	
	public ConfigClassContainer(String targetPath, String configName)
	{
		this.targetPath = targetPath;
		this.configName = configName;
	}
	
	public void addField(FieldSpec field){
		addMember(FieldSpec.class, field);
	}
	
	public void addMethod(MethodSpec method){
		addMember(MethodSpec.class, method);
	}
	
	public void addField(String itemName, Class<?> fieldType)
	{
		FieldSpec spec = CodeTemplate.field(itemName, fieldType);
		addField(spec);
	}
	
	public void addSetter(String itemName, Class<?> paraType)
	{
		MethodSpec spec = CodeTemplate.setter(itemName, paraType);
		addMethod(spec);
	}
	public void addGetter(String itemName, Class<?> returnType)
	{
		AnnotationSpec an = CodeTemplate.itemAnnotation(itemName);
		MethodSpec spec = CodeTemplate.getter(an, itemName, returnType);
		addMethod(spec);
	}
	
	private void addMember(Class<?> cls, Object value)
	{
		if(!members.containsKey(cls))
		{
			members.put(cls, new ArrayList<>());
		}
		List<Object> temp = (List<Object>) members.get(cls);
		temp.add(value);
	}
	
	public void generate(String targetPkg) throws Exception
	{
		String clazzName = CodeNamer.getClsNameFromConfigName(configName);
		AnnotationSpec an = CodeTemplate.fileAnnotation(configName);
		TypeSpec clazz = CodeTemplate.clazz(an, clazzName, members);
		JavaFile java = JavaFile.builder(targetPkg, clazz).build();
		File root = new File(targetPath);
		java.writeTo(root);
	}
	
}
