# 项目部署流程：

1. 修改数据库的连接信息：application.properties文件
2. 在数据库中执行提供的sql脚本，脚本位置：`src/main/resource/sql`，其中init.sql是项目初始化的sql文件，data.sql是测试数据的sql文件，这个sql可以自由选择是否导入。
3. 项目的访问地址：localhost:1801/
4. 项目中用到的Excel模板位于`src/test/resource/用户信息.xlsx`,请使用该Excel模板测试Excel导入功能。
5. 超级管理员登录用户名：admin 密码：admin

