# spring_boot-jersey-jwt
## 运行
1. 使用mongo和mysql，建议使用docker安装，mongo中可以手动建立一个名为user的collection，其中有个具有用户名和密码的文档
```
{ 
    "_id" : NumberLong(20000), 
    "_class" : "com.example.entity.mongo.User", 
    "userName" : "binglin", 
    "password" : "123456", 
    "roles" : [
        "user"
    ], 
    "version" : NumberInt(1)
}
```
2. 直接运行main方法即可

## 测试，使用postman
1. 获取token

	post http://localhost:8080/authentication 
	body选择x-www-form-urlencoded
	username  binglin
	password 123456		
	返回
	```
	{
  "authToken": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJKZXJzZXktU2VjdXJpdHktQmFzaWMiLCJzdWIiOiJiaW5nbGluIiwiYXVkIjoidXNlciIsImV4cCI6MTQ3NTg5ODA0MiwiaWF0IjoxNDc1ODk3MTQ0LCJqdGkiOiIxIn0.sNeMZqAh6V3cXV9IYxIvydfoh0sTCAXsdCl1FcSBclA",
  "expires": "2016-10-08T03:40:42.863 UTC"
}
```
2. 使用token访问rest接口
GET http://localhost:8080/users
在Headers 中添加
`Authorization Bearer "${上一步获取的token}"
返回mongo中的所有user文档