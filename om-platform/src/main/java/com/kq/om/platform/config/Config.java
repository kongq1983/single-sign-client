package com.kq.om.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author kq
 * @date 2021-05-08 11:03
 * @since 2020-0630
 */
@Configuration
public class Config {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
