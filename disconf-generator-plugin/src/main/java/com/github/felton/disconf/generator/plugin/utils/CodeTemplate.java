/**
 * Created on 2016年6月14日
 * Author feit
 */
package com.github.felton.disconf.generator.plugin.utils;

import java.util.Map;
import javax.lang.model.element.Modifier;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

/**
 * @author feit
 *
 */
public class CodeTemplate {

	public static MethodSpec setter(String itemName, Class<?> paraType)
	{
		String methodName = CodeNamer.getSetterNameFromItemName(itemName);
		String fieldName = CodeNamer.getFieldNameFromItemName(itemName);
		ParameterSpec param = ParameterSpec.builder(paraType, fieldName).build();
		MethodSpec itemSetter = MethodSpec.methodBuilder(methodName)
				                          .addModifiers(Modifier.PUBLIC)
				                          .addParameter(param)
				                          .addStatement("this.$N = $N", fieldName, fieldName)
				                          .build();
		return itemSetter;
	}
	
	public static MethodSpec getter(AnnotationSpec an, String itemName, Class<?> returnType)
	{
		String methodName = CodeNamer.getGetterNameFromItemName(itemName, returnType == Boolean.class);
		String fieldName = CodeNamer.getFieldNameFromItemName(itemName);
		MethodSpec itemGetter = MethodSpec.methodBuilder(methodName)
				                          .addAnnotation(an)
				                          .addModifiers(Modifier.PUBLIC)
				                          .addStatement("return $N", fieldName)
				                          .returns(returnType)
				                          .build();
		return itemGetter;
	}
	
	public static FieldSpec field(String itemName, Class<?> fieldType)
	{
		String fieldName = CodeNamer.getFieldNameFromItemName(itemName);
		FieldSpec itemField = FieldSpec.builder(fieldType, fieldName)
				                       .addModifiers(Modifier.PRIVATE)
				                       .build();
		return itemField;
	}
	
	public static AnnotationSpec fileAnnotation(String configName)
	{
		AnnotationSpec an = AnnotationSpec.builder(DisconfFile.class)
				                          .addMember("filename", "$S", configName)
				                          .build();
		return an;
	}
	
	public static AnnotationSpec itemAnnotation(String itemName)
	{
		String fieldName = CodeNamer.getFieldNameFromItemName(itemName);
		AnnotationSpec an = AnnotationSpec.builder(DisconfFileItem.class)
				                          .addMember("name", "$S", itemName)
				                          .addMember("associateField", "$S", fieldName)
				                          .build();
		return an;
	}
	
	public static TypeSpec clazz(AnnotationSpec an, String clsName, Map<Class<?>, Object> members)
	{
		TypeSpec cls = TypeSpec.classBuilder(clsName)
				               .addModifiers(Modifier.PUBLIC)
				               .addAnnotation(an)
				               .addFields((Iterable<FieldSpec>) members.get(FieldSpec.class))
				               .addMethods((Iterable<MethodSpec>) members.get(MethodSpec.class))
				               .build();
		return cls;			
	}
}
