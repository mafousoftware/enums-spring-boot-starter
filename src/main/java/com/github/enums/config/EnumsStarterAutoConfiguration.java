package com.github.enums.config;

import com.github.enums.controller.EnumsController;
import com.github.enums.scan.EnumsScanner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 枚举配置
 *
 * @author chenxiaoni 2025/9/18 19:08
 * @version 1.0
 */
@Configuration
@EnableConfigurationProperties(DictionaryProperties.class)
public class EnumsStarterAutoConfiguration {

    private final DictionaryProperties dictionaryProperties;

    public EnumsStarterAutoConfiguration(DictionaryProperties dictionaryProperties) {
        this.dictionaryProperties = dictionaryProperties;
    }

    @Bean
    @ConditionalOnExpression(
        "#{T(com.winteamiot.enums.config.DictionaryProperties).getAnnotationEnabled() == null ? " +
        "${spring.dictionary.enabled:true} : " +
        "T(com.winteamiot.enums.config.DictionaryProperties).getAnnotationEnabled() && ${spring.dictionary.enabled:true}}"
    )
    public EnumsController enumsController() {
        // 优先使用从注解中设置的包路径
        String[] packagesToScan = DictionaryProperties.getAnnotationBasePackages();

        // 如果注解中没有设置，则使用配置文件中的basePackages
        if (packagesToScan == null || packagesToScan.length == 0) {
            packagesToScan = dictionaryProperties.getBasePackages();
        }

        // 如果配置文件中也没有，则使用默认包
        if (packagesToScan != null) {
            // 扫描所有指定的包路径
            for (String packageName : packagesToScan) {
                EnumsScanner.scan(packageName);
            }
        }

        return new EnumsController();
    }
}
