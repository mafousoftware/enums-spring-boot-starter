package com.github.enums.scan;

import com.github.enums.annotation.EnumDictionary;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class EnumsScanner {

    private static final Map<String, List<Item>> CACHE = new ConcurrentHashMap<>();
    private static final List<Type> TYPE_NAME_CACHE = new LinkedList<>();
    private static final Set<String> SCANNED_PACKAGES = ConcurrentHashMap.newKeySet();

    /* 只读对外暴露 */
    public static List<Item> getDictionary(String enumType) {
        return CACHE.getOrDefault(enumType, List.of());
    }

    public static List<Type> getTypeNames() {
        return TYPE_NAME_CACHE;
    }

    /* 内部扫描逻辑 */
    @SuppressWarnings("unchecked")
    public static void scan(String basePackage) {
        // 避免重复扫描同一个包
        if (SCANNED_PACKAGES.contains(basePackage)) {
            return;
        }

        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(EnumDictionary.class));

        for (BeanDefinition bd : provider.findCandidateComponents(basePackage)) {
            try {
                Class<?> cls = Class.forName(bd.getBeanClassName());
                if (!cls.isEnum()) continue;
                String name = cls.getSimpleName();
                if (cls.isAnnotationPresent(EnumDictionary.class)) {
                    EnumDictionary dictionary = cls.getAnnotation(EnumDictionary.class);
                    TYPE_NAME_CACHE.add(new Type(name, dictionary.value()));
                }
                Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>) cls;
                Enum<?>[] constants = enumClass.getEnumConstants();
                List<Item> list = Arrays.stream(constants)
                        .map(e -> {
                                    if (e instanceof Valuable valuable) {
                                        Object code = valuable.getCode();
                                        Object value = valuable.getValue();
                                        return new Item(e.name(), code, value);
                                    } else {
                                        return new Item(e.name(), e.ordinal(), null);
                                    }
                                }
                        )
                        .collect(Collectors.toList());

                CACHE.put(name, list);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }

        // 标记该包已扫描
        SCANNED_PACKAGES.add(basePackage);
    }

}
