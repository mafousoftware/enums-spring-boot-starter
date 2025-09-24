package com.github.enums.config;

import java.util.Map;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import com.github.enums.annotation.EnableDictionary;

/**
 * 字典功能导入选择器
 * 根据EnableDictionary注解的enabled属性决定是否导入配置类
 *
 * @author chenxiaoni 2025/1/27
 * @version 1.0
 */
public class DictionaryImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableDictionary.class.getName());

        if (attributes != null) {
            // 获取basePackages参数
            String[] basePackages = (String[]) attributes.get("basePackages");

            // 如果basePackages为空，使用被@EnableDictionary标记的类所在的包
            if (basePackages == null || basePackages.length == 0) {
                String className = importingClassMetadata.getClassName();
                String currentPackage = className.substring(0, className.lastIndexOf('.'));
                basePackages = new String[]{currentPackage};
            }

            // 将basePackages存储到静态存储中
            DictionaryProperties.setAnnotationBasePackages(basePackages);
            
            // 存储注解中的enabled状态，供后续使用
            Boolean annotationEnabled = (Boolean) attributes.get("enabled");
            DictionaryProperties.setAnnotationEnabled(annotationEnabled);

            // 始终导入配置类，让@ConditionalOnProperty来决定是否创建Bean
            return new String[]{EnumsStarterAutoConfiguration.class.getName()};
        }

        return new String[0];
    }
}
