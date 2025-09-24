package com.github.enums.annotation;

import com.github.enums.config.DictionaryImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DictionaryImportSelector.class)
public @interface EnableDictionary {
    boolean enabled() default true;
    
    /**
     * 指定要扫描的包路径，支持多个包路径
     * 如果不指定，则默认扫描被@EnableDictionary标记的类所在的包
     */
    String[] basePackages() default {};
}