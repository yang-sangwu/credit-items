package com.credit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config.jwt")
@Data
public class JwtProperties {
    /**
     *密钥
     */
    public  String secret;
    /**
     * 过期时间
     */
    public int expire;
    /**
     * 名称
     */
    public String header;

}
