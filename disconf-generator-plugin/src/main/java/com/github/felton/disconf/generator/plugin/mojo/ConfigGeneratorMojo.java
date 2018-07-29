/**
 * Created on 2016年5月3日
 * Author felton
 */
package com.github.felton.disconf.generator.plugin.mojo;

import java.io.File;
import java.util.Set;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.github.felton.disconf.generator.plugin.generator.ConfigBeanGenerator;
import com.github.felton.disconf.generator.plugin.generator.PropertyBeanGenerator;
import com.google.common.collect.Sets;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import com.baidu.disconf.client.config.ConfigMgr;
import com.baidu.disconf.client.config.DisClientConfig;

@Mojo( name = "generate", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class ConfigGeneratorMojo extends AbstractMojo
{
 
    //disconf.properties地址
    @Parameter(defaultValue = "${basedir}", required=true)
    private String disconfPropertyPath;
    
    @Parameter(defaultValue = "${project.build.sourceDirectory}")
    private String srcRootPath;

    @Parameter
	private String targetPackage;

    @Parameter
	private String autoProperties;

    private Set<String> autoPropertyList;

	protected static final String filename = "disconf.properties";

    public void execute() throws MojoExecutionException
    {
       String dsconfPropertyFile = disconfPropertyPath + "\\" + filename;
       getLog().info("TargetPackage: " + targetPackage);
       getLog().info("SrcRootPath: " + srcRootPath);
       getLog().info("AutoProperties: " + autoProperties);
       getLog().info("DisconfPropertyFile is: " + dsconfPropertyFile);
       try {
       	   autoPropertyList = parseAutoProperties(autoProperties);
    	   ConfigMgr.init();
    	   ConfigBeanGenerator generator = new PropertyBeanGenerator(this, DisClientConfig.getInstance());
    	   generator.setSrcRootPath(srcRootPath);
    	   generator.setTargetFilePath(getTargetPath(DisClientConfig.getInstance().userDefineDownloadDir));
    	   //首先下载或直接使用本地
    	   generator.downloadOrNot();
    	   //然后生成
    	   generator.generate();
	   } catch (Exception e) {
			throw new MojoExecutionException("Generate Config bean error: ", e);
	   }
    }
    
    private String getTargetPath(String src)
    {
    	String dir = src;
    	dir = dir.replace("./", "");
    	if(dir.startsWith(File.separator))
    		dir = dir.substring(1, dir.length());
    	if(!dir.endsWith(File.separator))
    	{
    		dir += File.separator;
    	}
    	return disconfPropertyPath + File.separator + dir;
    }

    private Set<String> parseAutoProperties(String autoProperties){
    	String[] props = autoProperties.split(",");
    	return Sets.newHashSet(props);
	}

	public String getDisconfPropertyPath() {
		return disconfPropertyPath;
	}

	public void setDisconfPropertyPath(String disconfPropertyPath) {
		this.disconfPropertyPath = disconfPropertyPath;
	}

	public String getSrcRootPath() {
		return srcRootPath;
	}

	public void setSrcRootPath(String srcRootPath) {
		this.srcRootPath = srcRootPath;
	}

	public String getTargetPackage() {
		return targetPackage;
	}

	public String getAutoProperties() {
		return autoProperties;
	}

	public Set<String> getAutoPropertyList() {
		return autoPropertyList;
	}
}
