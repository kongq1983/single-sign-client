package com.kq.om.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * http://192.168.0.113/hzsun2020/%E7%BB%84%E7%BB%87%E7%BA%A7%E8%B5%84%E4%BA%A7%E5%BA%93/_wiki/wikis/%E7%BB%84%E7%BB%87%E7%BA%A7%E8%B5%84%E4%BA%A7%E5%BA%93.wiki/1243/%E5%8D%95%E7%82%B9%E7%99%BB%E5%BD%95%E5%AF%B9%E6%8E%A5%E6%96%B9%E6%A1%88
 * @author kq
 * @date 2021-05-08 8:23
 * @since 2020-0630
 */
@SpringBootApplication
public class OmPlatformApplication {

    protected static Logger logger = LoggerFactory.getLogger(OmPlatformApplication.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(OmPlatformApplication.class, args);

//        String[] beanNames = context.getBeanDefinitionNames();
//
//        for(String beanName : beanNames) {
//            logger.info("load beanName ={}",beanName);
//        }
//
//        logger.info("load beanNames size ={}",context.getBeanDefinitionCount());

    }

}
