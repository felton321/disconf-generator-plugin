package com.github.felton.disconf.generator.example.config;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import org.springframework.stereotype.Component;

import java.lang.Integer;
import java.lang.String;

@Component
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
