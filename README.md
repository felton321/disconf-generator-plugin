# disconf-generator-plugin

#### 简介
---
disconf是一款优秀的分布式配置管理平台，使用注解的方式使客户端bean与远程配置保持动态同步，但使用时除了上传配置文件，还需要手动编写客户端的配置bean，如果配置较多，会浪费诸多时间，本项目为自动生成客户端配置类的maven插件，只需添加pom plugin，一键生成全部配置类，解放双手，节约时间，内含example。

#### 版本
---
disconf: 2.6.36

#### 使用步骤：
---
1. 首先感谢knightliao的开源奉献。
2. 进入disconf的源项目：[https://github.com/knightliao/disconf](https://github.com/knightliao/disconf) ,
   根据官网的指示编译安装disconf项目。
3. 下载本项目，根目录下运行`mvn clean install`安装disconf-generator-plugin。注意根pom.xml中有disconf-client模块的依赖，请保证在第2步中已经安装了disconf-client模块。
4. 在需要使用disconf的项目pom中添加插件:
```
<plugin>
    <groupId>com.github.felton</groupId>
    <artifactId>disconf-generator-plugin</artifactId>
    <version>1.0.0</version>
    <configuration>
      <autoProperties>redis.properties,myserver_slave.properties</autoProperties>
      <targetPackage>com.github.felton.disconf.generator.example.config</targetPackage>
    </configuration>
</plugin>
```
   以假设项目A要生成redis.properties,myserver_slave.properties两个配置的类为例，解释如下：
- `<autoProperties>`： 需要生成客户端配置类的配置文件，如有多个，以','隔开，目前只支持properties文件, 必需配置。
- `<targetPackage>` ： 生成配置类的包名，如上示例则会在项目的`src/main/java/com/github/felton/disconf/generator/example/config`包中生成`Redis.java`,`MyserverSlave.java`两个配置类（下划线会自动转为驼峰命名，变量亦如此）， 必需配置。
- `<disconfPropertyPath>` ：`disconf.properties`的目录，默认为项目根目录，可选。
- `<srcRootPath>` ： 生成配置类的根目录，默认为`src/main/java`, 可选。
5. 确认项目A的`disconf.properties`已经按照官网的说明配置完成，`redis.properties`,`myserver_slave.properties`两个配置文件已经上传到disconf网站上。
- 如果`disconf.enable.remote.conf=true`，则运行本插件时会从远端下载配置然后反向生成类；
- 为`false`则可以将`redis.properties`,`myserver_slave.properties`放置在项目的classpath中使用本地反向生成方式。
6. 在项目A下运行
```mvn com.github.felton:disconf-generator-plugin:1.0.0:generate``` 即可生成配置类。
例如，如果redis.properties内容为：
```
redis.host=127.0.0.1
redis.port=6378
```
则自动生成的Redis.java内容为：
```
package com.github.felton.disconf.generator.example.config;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import java.lang.Integer;
import java.lang.String;

@DisconfFile(
    filename = "redis.properties"
)
public class Redis {
  private String redisHost;

  private Integer redisPort;

  public void setRedisHost(String redisHost) {
    this.redisHost = redisHost;
  }

  @DisconfFileItem(
      name = "redis.host",
      associateField = "redisHost"
  )
  public String getRedisHost() {
    return redisHost;
  }

  public void setRedisPort(Integer redisPort) {
    this.redisPort = redisPort;
  }

  @DisconfFileItem(
      name = "redis.port",
      associateField = "redisPort"
  )
  public Integer getRedisPort() {
    return redisPort;
  }
}
```
7. 可在`discon-generator-example`模块中运行示例，(友情提示: 生成完了不要忘了在类上加`@Component`哦~）
8. 配置类中Field的类型自动识别的顺序为:
```
Boolean -> Integer -> String
```
其他暂不支持。
9. 如有问题请联系: felton321@sina.com
