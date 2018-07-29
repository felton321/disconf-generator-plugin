package com.github.felton.disconf.generator.example.config;

import com.baidu.disconf.client.common.annotations.DisconfFile;
import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import java.lang.Integer;
import java.lang.String;

@DisconfFile(
    filename = "myserver_slave.properties"
)
public class MyserverSlave {
  private String server;

  private Integer retry;

  public void setServer(String server) {
    this.server = server;
  }

  @DisconfFileItem(
      name = "server",
      associateField = "server"
  )
  public String getServer() {
    return server;
  }

  public void setRetry(Integer retry) {
    this.retry = retry;
  }

  @DisconfFileItem(
      name = "retry",
      associateField = "retry"
  )
  public Integer getRetry() {
    return retry;
  }
}
