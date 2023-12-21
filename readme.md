# 电脑商城

电脑商城是一个基于`Spring Boot`的商城系统，用于实现一些简单的商城功能，是一个初学`Spring Boot`的练手项目。

## 技术栈

- Spring Boot 3.0.3：提供了快速搭建项目的框架和开发工具
- Mybatis 3.0.0 ：持久层框架
- MySQL数据库：用于存储和管理数据的数据库
- 前端技术栈：bootstrap3 + AJAX + Jquery
- lombok : 简化JavaBean编写

## 环境要求

- Java 17+
- Maven 3+
- MySQL 8+

## 快速开始

1. 使用`git`等工具`clone`项目到本地
2. 修改`application.yml`配置文件(MySQL的`datasource`配置)
3. 运行提供的`sql`文件

## 代码结构说明

```
Copy Code├── src/main/java/com/example/demo/  # Java源代码目录
│   ├── config/                     # 配置类目录
│   ├── controller/                 # 控制器类目录
│   ├── service/                    # 服务类目录
│   ├── repository/                 # 数据库访问类目录
│   └── Application.java             # 项目启动类
├── src/main/resources/              # 资源文件目录
│   ├── static/                      # 静态资源目录
│   ├── templates/                   # 页面模板目录（可选）
│   ├── application.yml              # 项目配置文件
│   └── ...
├── src/test/java/                   # 测试代码目录
├── pom.xml                           # Maven依赖配置文件
└── README.md                         # 项目文档
```