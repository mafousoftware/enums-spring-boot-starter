package com.github.enums.controller;

import com.github.enums.scan.EnumsScanner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 枚举接口
 *
 * @author chenxiaoni 2025/9/18 19:20
 * @version 1.0
 */
@RestController
public class EnumsController {
    /**
     * 获取所有枚举字典
     *
     * @return 所有枚举字典列表
     */
    @GetMapping("/v1/enums/names")
    public ResponseEntity<?> names() {
        return ResponseEntity.ok(new TypeNameResponse(EnumsScanner.getTypeNames()));
    }

    /**
     * 获取指定枚举字典
     *
     * @param name 枚举名称
     * @return 枚举字典列表
     */
    @GetMapping("/v1/enums/{name}")
    public ResponseEntity<ItemResponse> list(@PathVariable String name) {
        return ResponseEntity.ok(new ItemResponse(EnumsScanner.getDictionary(name)));
    }
}
