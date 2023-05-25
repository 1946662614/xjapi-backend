package com.xj.xjapiclientsdk;

import com.xj.xjapiclientsdk.client.XjApiClient;
import com.xj.xjapiclientsdk.model.User;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName XjApiClientConfig
 * @Description TODO
 * @Author 嘻精
 * @Date 2023/5/20 16:23
 * @Version 1.0
 */
@Configuration
@ConfigurationProperties("xjapi.client")
@Data
@ComponentScan
public class XjApiClientConfig {
    private String accessKey;
    
    private String secretKey;

    @Bean
    public XjApiClient xjApiClient() {
        return new XjApiClient(accessKey, secretKey);
    }
}
