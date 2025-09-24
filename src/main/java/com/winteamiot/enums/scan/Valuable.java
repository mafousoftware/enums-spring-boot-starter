package com.winteamiot.enums.scan;


/**
 * 枚举实现该接口以提供统一的编码和值获取方法
 */
public interface Valuable {
    /**
     * 获取枚举的编码，可以是任意类型（Integer、String 等）
     * @return 枚举编码
     */
    Object getCode();
    
    /**
     * 获取枚举的值/描述
     * @return 枚举值
     */
    Object getValue();
}