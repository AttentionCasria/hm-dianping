# 🚀 HMDP（黑马点评）后端项目

> 基于 **Spring Boot + MyBatis Plus + Redis + MySQL** 构建的店铺点评与本地生活服务系统后端，实现了商铺管理、缓存优化、地理位置查询等核心功能。

---

## ✨ 项目简介

HMDP（黑马点评）是一套面向本地生活服务场景的后端系统，提供商铺查询、店铺管理、分类检索、附近商铺查询等功能。

项目采用 **Spring Boot** 作为核心开发框架，结合 **MyBatis Plus** 完成数据持久化，并使用 **Redis** 实现缓存优化，为高并发场景提供良好的性能支持。

> 本仓库主要展示后端核心业务逻辑及 RESTful API 的实现。

---

## 🏗️ 技术架构

| 技术               | 说明              |
| ---------------- | --------------- |
| ☕ Java 8+        | 开发语言            |
| 🌱 Spring Boot   | Web 开发框架        |
| 🗄️ MyBatis Plus | ORM 持久层框架       |
| 🐬 MySQL         | 数据存储            |
| ⚡ Redis          | 缓存、地理位置查询       |
| 🧰 Maven         | 项目构建工具          |
| 📦 Hutool        | Java 工具类库       |
| ✨ Lombok         | 简化 Java Bean 编写 |

---

## 🌟 核心功能

### 🏪 店铺管理

* ✅ 根据 ID 查询店铺详情
* ✅ 新增店铺信息
* ✅ 修改店铺信息

---

### 🔍 商铺搜索

支持按照店铺名称进行模糊搜索，并支持分页查询。

例如：

```
星巴克
火锅
奶茶
```

---

### 📂 店铺分类

根据店铺分类分页查询商铺，例如：

* 美食
* 酒店
* 娱乐
* 健身
* 景点

---

### 📍 附近商铺（Geo 查询）

支持传入经纬度坐标：

* Longitude（x）
* Latitude（y）

实现按照距离排序查询附近商铺，为 Redis GEO 查询提供接口支持。

---

## 📑 RESTful API

### 查询店铺

```http
GET /shop/{id}
```

| 参数 | 类型   | 描述   |
| -- | ---- | ---- |
| id | Long | 店铺ID |

---

### 新增店铺

```http
POST /shop
```

Body：

```json
{
  "name":"海底捞",
  "typeId":1
}
```

---

### 更新店铺

```http
PUT /shop
```

Body：

```json
{
  "id":1,
  "name":"新的店铺名称"
}
```

---

### 根据分类分页查询

```http
GET /shop/of/type
```

| 参数      | 必填 | 说明   |
| ------- | -- | ---- |
| typeId  | ✔  | 店铺类型 |
| current | ✔  | 页码   |
| x       | ✘  | 经度   |
| y       | ✘  | 纬度   |

---

### 根据名称查询

```http
GET /shop/of/name
```

| 参数      | 默认值 | 说明      |
| ------- | --- | ------- |
| name    | -   | 店铺名称关键字 |
| current | 1   | 当前页     |

---

## 📁 项目结构

```text
src
└── main
    └── java
        └── com.hmdp
            ├── config          # 配置类
            ├── controller      # 控制器
            ├── dto             # 数据传输对象
            ├── entity          # 实体类
            ├── mapper          # Mapper 接口
            ├── service         # 业务层
            ├── utils           # 工具类
            └── HmdpApplication.java
```

---

## ⚙️ 快速开始

### 1️⃣ 克隆项目

```bash
git clone https://github.com/DarksideCasria/hmdp.git
cd hmdp
```

---

### 2️⃣ 创建数据库

创建数据库：

```sql
CREATE DATABASE hmdp DEFAULT CHARSET utf8mb4;
```

然后导入项目提供的 SQL 文件。

---

### 3️⃣ 修改配置

修改：

```text
src/main/resources/application.yml
```

配置：

* MySQL
* Redis
* 端口（可选）

例如：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hmdp
    username: root
    password: xxxx

  redis:
    host: localhost
    port: 6379
```

---

### 4️⃣ 启动项目

安装依赖：

```bash
mvn clean install
```

运行：

```bash
mvn spring-boot:run
```

或者直接运行：

```
HmdpApplication.java
```

---

## 📌 请求示例

### 查询店铺

```http
GET http://localhost:8080/shop/1
```

---

### 名称搜索

```http
GET http://localhost:8080/shop/of/name?name=星巴克&current=1
```

---

### 分类查询

```http
GET http://localhost:8080/shop/of/type?typeId=2&current=1
```

---

## 🚀 后续规划

* [ ] Redis 缓存穿透
* [ ] Redis 缓存击穿
* [ ] Redis 缓存雪崩解决方案
* [ ] Redis GEO 附近商铺
* [ ] 点赞功能
* [ ] Feed 流
* [ ] 秒杀系统
* [ ] 分布式锁
* [ ] Redisson
* [ ] Lua 脚本
* [ ] Stream 消息队列
* [ ] 用户签到
* [ ] UV 统计

---

## 🤝 贡献

欢迎提交 **Issue** 或 **Pull Request** 来完善本项目。

如果这个项目对你有所帮助，欢迎点一个 ⭐ Star！

---

## 📄 License

本项目基于 **MIT License** 开源。
