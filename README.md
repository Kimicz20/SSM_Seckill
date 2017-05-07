## Java高并发秒杀系统API

## 开发环境
IDEA+Maven+SSM框架。

### 开发工具
1. 准备工具
* Mysql
    1.  这里我们采用手写代码创建相关表，掌握这种能力对我们以后的项目二次上线会有很大的帮助
    2.  `SQL技巧` [SQL语句大全](http://www.runoob.com/sql/sql-tutorial.html)
    3.  事务和行级锁的理解和一些应用 
* maven介绍以及了解maven web项目的创建与配置:[Maven安装配置及创建你的第一个Maven项目](http://codingxiaxw.cn/2016/11/24/51-first-maven-project/)
* Logback 日志插件 [logback配置](https://logback.qos.ch/manual/jmxConfig.html)

2. 前端页面框架
* Bootstrap:[Bootstrap 教程](http://www.runoob.com/bootstrap/bootstrap-tutorial.html)
* JQuery/JS 技术 ajax请求以及JS的模块化编写

3. 后端框架
* SpringMVC 
    1. Restful接口设计和使用。Restful现在更多的被应用在一些互联网公司Web层接口的应用上。
    2. 框架运作流程。
    3. Spring Controller的使用技巧。  
* Spring 
    1. Spring IOC帮我们整合Service以及Service所有的依赖。
    2. 声明式事务。对Spring声明式事务做一些分析以及它的行为分析。  
* Mybatis [官网学习](http://www.mybatis.org/mybatis-3/zh/index.html)
    1. DAO层的设计与开发。
    2. MyBatis的合理使用，使用Mapper动态代理的方式进行数据库的访问。
    3. MyBatis和Spring框架的整合:如何高效的去整合MyBatis和Spring框架。  

## 系统介绍

系统主要是完成了用户抢购商品的处理环节，完成这个秒杀系统，需要完成四个模块的代码编写，分别是:
- 1.[Java高并发秒杀APi之业务分析与DAO层代码编写](http://codingxiaxw.cn/2016/11/27/53-maven-ssm-seckill-dao/)。
- 2.[Java高并发秒杀APi之Service层代码编写](http://codingxiaxw.cn/2016/11/28/54-seckill-service/)。
- 3.[Java高并发秒杀APi之Web层代码编写](http://codingxiaxw.cn/2016/11/28/55-seckill-web/)。
- 4.Java高并发秒杀APi之高并发优化。
其实完成前三个模块就可以完成我们的秒杀系统了，但对于我们的秒杀系统中一件秒杀商品，在秒杀的时候肯定会有成千上万的用户参与进来，通过上述三个模块完成的系统无法解决这么多用户的高并发操作，所以我们还需要第四个模块。
