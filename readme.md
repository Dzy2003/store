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

## 部分功能实现

1. [初始环境搭建](doc/01.md)
2. [用户注册和登录](doc/02.md)
3. [用户资料修改](doc/03.md)
4. [用户上传头像](doc/04.md)
5. [用户收货管理](doc/05.md)



## 代码结构说明

```
E:.
├─main           
│  ├─java  #Java源代码目录
│  │  └─com
│  │      └─duan
│  │          ├─config #配置文件
│  │          ├─Controller # 控制器层
│  │          │  └─Exception #自定义异常
│  │          ├─entity #实体类
│  │          ├─interceptor #拦截器
│  │          ├─Mapper #与数据库交互
│  │          ├─Service #业务层
│  │          │  └─ServiceImpl #业务实现类
│  │          ├─util #工具类
│  │          └─vo 给前端显示的VO对象
│  └─resources 资源文件
│      ├─com 
│      │  └─duan
│      │      └─Mapper #mapper映射文件
│      ├─static #静态页面资源
```
