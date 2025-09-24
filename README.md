# 枚举字典 Spring Boot Starter

一个轻量级的 Spring Boot Starter，用于自动扫描和管理枚举字典，提供统一的枚举字典 API 接口。

## 功能特性

-  **自动扫描**：自动扫描指定包路径下的枚举类
-  **多包支持**：支持扫描多个包路径
-  **注解驱动**：基于 `@EnableDictionary` 和 `@EnumDictionary` 注解
-  **REST API**：提供统一的枚举字典查询接口
-  **高性能**：内置缓存机制，避免重复扫描
-  **灵活配置**：支持注解参数和配置文件两种配置方式

## 环境要求

- Java 21+
- Spring Boot 3.2.4+
- Maven 3.6+

## 快速开始

### 1. 添加依赖

```xml
<dependency>
    <groupId>com.winteam.iot</groupId>
    <artifactId>enums-springboot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### 2. 启用字典功能

在启动类上添加 `@EnableDictionary` 注解：

```java
@SpringBootApplication
@EnableDictionary
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 3. 创建枚举字典

在需要扫描的包路径下创建枚举类，并添加 `@EnumDictionary` 注解：

```java
package com.example.enums;

import com.winteamiot.enums.annotation.EnumDictionary;
import com.winteamiot.enums.scan.Valuable;

@EnumDictionary("用户状态")
public enum UserStatus implements Valuable {
    ACTIVE(1, "激活"),
    INACTIVE(0, "未激活"),
    LOCKED(-1, "锁定");

    private final int code;
    private final String description;

    UserStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public Object getCode() {
        return code;
    }

    @Override
    public Object getValue() {
        return description;
    }
}
```

### 4. 访问 API

启动应用后，可以通过以下接口访问枚举字典：

```bash
# 获取所有枚举类型名称
GET /v1/enums/names

# 获取指定枚举的字典项
GET /v1/enums/UserStatus
```

## 配置说明

### 注解配置

#### @EnableDictionary

用于启用枚举字典功能，支持以下参数：

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `enabled` | `boolean` | `true` | 是否启用字典功能 |
| `basePackages` | `String[]` | `{}` | 要扫描的包路径，如果不指定则扫描当前类所在的包 |

#### @EnumDictionary

用于标记枚举类，支持以下参数：

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `value` | `String` | `""` | 枚举类型的中文描述 |

### 使用示例

#### 基本使用（扫描当前包）

```java
@SpringBootApplication
@EnableDictionary
public class Application {
    // 会扫描 com.example.Application 所在的包
}
```

#### 指定单个包路径

```java
@SpringBootApplication
@EnableDictionary(basePackages = "com.example.enums")
public class Application {
    // 会扫描 com.example.enums 包
}
```

#### 指定多个包路径

```java
@SpringBootApplication
@EnableDictionary(basePackages = {
    "com.example.enums",
    "com.example.common.dict",
    "com.example.business.enums"
})
public class Application {
    // 会扫描所有指定的包路径
}
```

#### 禁用字典功能

```java
@SpringBootApplication
@EnableDictionary(enabled = false)
public class Application {
    // 禁用字典功能
}
```

## API 接口

### 获取所有枚举类型名称

```http
GET /v1/enums/names
```

**响应示例：**
```json
{
  "code": 0,
  "message": "Success",
  "types": [
    {
      "name": "UserStatus",
      "description": "用户状态"
    },
    {
      "name": "OrderStatus",
      "description": "订单状态"
    }
  ]
}
```

### 获取指定枚举的字典项

```http
GET /v1/enums/{enumName}
```

**参数：**
- `enumName`: 枚举类名（如：UserStatus）

**响应示例：**
```json
{
  "code": 0,
  "message": "Success",
  "items": [
    {
      "name": "ACTIVE",
      "code": 1,
      "value": "激活"
    },
    {
      "name": "INACTIVE",
      "code": 0,
      "value": "未激活"
    },
    {
      "name": "LOCKED",
      "code": -1,
      "value": "锁定"
    }
  ]
}
```

**字符型 Code 响应示例（如 OrderStatus）：**
```json
{
  "code": 0,
  "message": "Success",
  "items": [
    {
      "name": "PENDING",
      "code": "P",
      "value": "待处理"
    },
    {
      "name": "PROCESSING",
      "code": "PR",
      "value": "处理中"
    },
    {
      "name": "COMPLETED",
      "code": "C",
      "value": "已完成"
    },
    {
      "name": "CANCELLED",
      "code": "CA",
      "value": "已取消"
    }
  ]
}
```

## 高级用法

### 自定义枚举值

如果枚举需要自定义值，可以实现 `Valuable` 接口：

```java
@EnumDictionary("订单状态")
public enum OrderStatus implements Valuable {
    PENDING("P", "待处理"),
    PROCESSING("PR", "处理中"),
    COMPLETED("C", "已完成"),
    CANCELLED("CA", "已取消");

    private final String code;
    private final String description;

    OrderStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public Object getCode() {
        return code; // 返回自定义的字符串 code 值
    }

    @Override
    public Object getValue() {
        return description; // 返回描述信息
    }
}
```

### 配置文件配置

除了使用注解，还可以通过配置文件进行配置：

```yaml
spring:
  dictionary:
    enabled: true
    basePackages:
      - com.example.enums
      - com.example.common.dict
```

## 架构说明

### 核心组件

- **DictionaryImportSelector**: 处理 `@EnableDictionary` 注解，决定是否启用字典功能
- **EnumsScanner**: 负责扫描枚举类并构建字典缓存
- **EnumsController**: 提供枚举字典的 REST API 接口
- **DictionaryProperties**: 字典配置属性类

### 扫描流程

1. `@EnableDictionary` 注解触发 `DictionaryImportSelector`
2. 根据注解参数确定要扫描的包路径
3. `EnumsStarterAutoConfiguration` 调用 `EnumsScanner.scan()` 进行扫描
4. `EnumsScanner` 扫描指定包下的枚举类，构建缓存
5. `EnumsController` 提供 API 接口访问缓存数据

## 注意事项

1. **包路径优先级**：
   - 注解中的 `basePackages` > 配置文件中的 `basePackages` > 默认包路径

2. **性能优化**：
   - 内置包路径缓存，避免重复扫描
   - 枚举数据缓存，提高 API 响应速度

3. **枚举要求**：
   - 枚举类必须添加 `@EnumDictionary` 注解
   - 如需自定义值，实现 `Valuable` 接口

## 版本历史

### v0.0.1-SNAPSHOT
- 初始版本
- 支持基本的枚举字典扫描和 API 接口
- 支持多包路径扫描
- 内置缓存机制

## 许可证

本项目采用 MIT 许可证，详情请参见 [LICENSE](LICENSE) 文件。

## 贡献

欢迎提交 Issue 和 Pull Request 来改进这个项目。

## 联系方式

- 作者：chenxiaoni
- 邮箱：chenxiaoni@apisets.com
