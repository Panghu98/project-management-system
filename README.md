# 数据库设计
![数据库设计图](https://github.com/Panghu98/project-management-system/blob/master/p1.png)

# 功能说明
## 登录
- 二次MD5加密办证登录安全
## 注册
- 超级管理员进行手动注册
- 超级管理员通过导入Excel进行批量用户注册

## 项目导入
- 通过Excel导出,将生成的List先写入Redis,并将缓存的状态置为已刷新.再将List放入消息队列中,进行异步入库操作.

