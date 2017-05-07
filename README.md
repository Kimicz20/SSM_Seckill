## Java高并发秒杀系统API

## 开发环境
IDEA+Maven+SSM框架。

### 开发工具
1. 准备工具
- Mysql [SQL语句大全](http://www.runoob.com/sql/sql-tutorial.html)
- maven介绍以及了解maven web项目的创建与配置:[Maven安装配置及创建你的第一个Maven项目](http://codingxiaxw.cn/2016/11/24/51-first-maven-project/)
- Logback 日志插件 [logback配置](https://logback.qos.ch/manual/jmxConfig.html)
2. 前端页面框架
- Bootstrap:[Bootstrap 教程](http://www.runoob.com/bootstrap/bootstrap-tutorial.html)
- Restful 软件架构规范，主要是对业务逻辑在web层URL规范
- JQuery/JS 技术 ajax请求以及JS的模块化编写
3. 后端框架
- SpringMVC 
- Spring 
- Mybatis [官网学习](http://www.mybatis.org/mybatis-3/zh/index.html)

## 系统介绍

系统主要是完成了用户抢购商品的处理环节，完成这个秒杀系统，需要完成四个模块的代码编写，分别是:
- 1.[Java高并发秒杀APi之业务分析与DAO层代码编写](http://codingxiaxw.cn/2016/11/27/53-maven-ssm-seckill-dao/)。
- 2.[Java高并发秒杀APi之Service层代码编写](http://codingxiaxw.cn/2016/11/28/54-seckill-service/)。
- 3.[Java高并发秒杀APi之Web层代码编写](http://codingxiaxw.cn/2016/11/28/55-seckill-web/)。
- 4.Java高并发秒杀APi之高并发优化。
其实完成前三个模块就可以完成我们的秒杀系统了，但对于我们的秒杀系统中一件秒杀商品，在秒杀的时候肯定会有成千上万的用户参与进来，通过上述三个模块完成的系统无法解决这么多用户的高并发操作，所以我们还需要第四个模块。
