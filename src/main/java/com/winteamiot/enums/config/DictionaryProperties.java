package com.winteamiot.enums.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 字典配置属性
 *
 * @author chenxiaoni 2025/1/27
 * @version 1.0
 */
@ConfigurationProperties(prefix = "spring.dictionary")
public class DictionaryProperties {
    
    /**
     * 静态存储从注解中获取的包路径
     */
    private static final ConcurrentHashMap<String, String[]> ANNOTATION_PACKAGES = new ConcurrentHashMap<>();
    
    /**
     * 静态存储从注解中获取的enabled状态
     */
    private static Boolean ANNOTATION_ENABLED = null;
    
    /**
     * 是否启用字典功能
     */
    private boolean enabled = true;
    
    /**
     * 扫描包路径列表（支持多个包路径）
     */
    private String[] basePackages;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }
    
    /**
     * 静态方法：存储从注解中获取的包路径
     */
    public static void setAnnotationBasePackages(String[] basePackages) {
        ANNOTATION_PACKAGES.put("default", basePackages);
    }
    
    /**
     * 静态方法：获取从注解中设置的包路径
     */
    public static String[] getAnnotationBasePackages() {
        return ANNOTATION_PACKAGES.get("default");
    }
    
    /**
     * 静态方法：存储从注解中获取的enabled状态
     */
    public static void setAnnotationEnabled(Boolean enabled) {
        ANNOTATION_ENABLED = enabled;
    }
    
    /**
     * 静态方法：获取从注解中设置的enabled状态
     */
    public static Boolean getAnnotationEnabled() {
        return ANNOTATION_ENABLED;
    }
}
