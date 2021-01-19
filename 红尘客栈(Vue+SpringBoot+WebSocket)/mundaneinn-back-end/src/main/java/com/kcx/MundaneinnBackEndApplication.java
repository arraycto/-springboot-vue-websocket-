package com.kcx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
@EnableTransactionManagement //开启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />，但其实默认已经开启了
public class MundaneinnBackEndApplication {
    //注入Websocket启动,加上这个启动类，测试类就无法运行了，需要先注释
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    public static void main(String[] args) {
        SpringApplication.run(MundaneinnBackEndApplication.class, args);
    }

}
