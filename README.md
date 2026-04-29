# HMDP (黑马点评) 后端工程

这是一个基于 Java Spring Boot 开发的店铺点评与资讯系统后端项目。本项目主要用于管理店铺信息、提供商铺查询（包括基于地理位置的查询）等功能。

## 🛠 技术栈

本项目使用以下核心技术和框架：

- **开发语言**: Java
- **构建工具**: Maven
- **核心框架**: Spring Boot
- **ORM 框架**: MyBatis Plus
- **数据库**: MySQL
- **工具库**: Hutool (用于字符串处理等)
- **其他**: Lombok (推测), Redis (推测，常见于此类项目缓存场景)

## 📂 功能模块

目前主要展示店铺管理模块 (`ShopController`) 的相关功能：

*   **店铺查询**: 支持根据 ID 查询店铺详情。
*   **店铺搜索**: 支持根据店铺名称关键字进行模糊搜索（分页）。
*   **分类查询**: 支持根据店铺类型查询列表。
*   **地理位置**: 在查询店铺类型时，支持传入经纬度坐标 (`x`, `y`)，为基于地理位置的排序（附近商铺）预留了接口。
*   **店铺管理**: 提供新增店铺和更新店铺信息的接口。

## 🔌 API 接口文档

以下是店铺模块 (`src/main/java/com/hmdp/controller/ShopController.java`) 对外提供的 RESTful 接口：

| HTTP 方法 | 接口路径 | 描述 | 参数说明 |
| :--- | :--- | :--- | :--- |
| **GET** | `/shop/{id}` | 根据 ID 查询商铺信息 | `id`: 商铺 ID |
| **POST** | `/shop` | 新增商铺信息 | Body: `Shop` JSON 对象 |
| **PUT** | `/shop` | 更新商铺信息 | Body: `Shop` JSON 对象 |
| **GET** | `/shop/of/type` | 根据类型分页查询商铺 | `typeId`: 类型ID, `current`: 页码, `x`: 经度(可选), `y`: 纬度(可选) |
| **GET** | `/shop/of/name` | 根据名称关键字查询商铺 | `name`: 关键字(可选), `current`: 页码(默认1) |

## 🚀 快速开始

### 前置要求

*   JDK 1.8+
*   Maven 3.x
*   MySQL 5.7+
*   IDE (IntelliJ IDEA 推荐)

### 安装步骤

1.  **克隆项目**
    ```bash
    git clone https://github.com/DarksideCasria/hmdp.git
```

2.  **导入数据库**
    *   请确保本地 MySQL 服务已启动。
    *   创建数据库 `hmdp` 并导入项目提供的 SQL 脚本（如果存在）。

3.  **配置数据库连接**
    *   修改 `src/main/resources/application.yml` (或 `.properties`) 文件，更新数据库 URL、用户名和密码。

4.  **编译与运行**
    *   在项目根目录下运行 Maven 命令下载依赖：
        ```bash
        mvn clean install
        ```
    *   运行 Spring Boot 启动类。

### 示例请求

**查询 ID 为 1 的店铺:**
```http
GET http://localhost:8080/shop/1
```

**根据名称搜索店铺:**
```http
GET http://localhost:8080/shop/of/name?name=星巴克&current=1
```

## 📝 目录结构说明

```
src/main/java/com/hmdp
├── config       // 配置类
├── controller   // 控制器层 (如 ShopController)
├── dto          // 数据传输对象 (如 Result)
├── entity       // 实体类 (如 Shop)
├── mapper       // MyBatis Mapper 接口
├── service      // 业务逻辑接口 (如 IShopService)
├── utils        // 工具类 (如 SystemConstants)
└── HmdpApplication.java // 启动类
```

## 🤝 贡献

欢迎提交 Issue 或 Pull Request 来改进本项目。

## 📄 许可证

[MIT License](LICENSE)
