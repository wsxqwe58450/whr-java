package com.at.wangshixiu.whr;

import com.at.wangshixiu.whr.manager.ApplicationInitialize;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@MapperScan({"com.at.wangshixiu.whr.mapper","com.at.wangshixiu.whr.mapper.related.mapper"})
@Slf4j
public class WhrJavaApplication implements ApplicationRunner {

    private final ApplicationInitialize applicationInitialize;

    public WhrJavaApplication(@Autowired ApplicationInitialize applicationInitialize) {
        this.applicationInitialize = applicationInitialize;
    }

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run = SpringApplication.run(WhrJavaApplication.class, args);
        Environment env = run.getEnvironment();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        if (null == path) {
            path = "";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application ptstd_sac_server is running! Access URLs:\n\t" +
                "swagger: \thttp://localhost:" + port + path + "/doc.html\n\t" +
                "druid: \t\thttp://localhost:" + port + path + "/druid/index.html\n\t" +
                "----------------------------------------------------------");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // ApplicationInitialize
        log.info("whr项目启动成功,开始缓存字典数据");
        //    TODO 异步缓存字典数据
        applicationInitialize.updateReadCount("whr");
    }
}
