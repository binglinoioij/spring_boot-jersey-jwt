# spring_boot-jersey-jwt
集成mongo，redis，spring boot，jersey，jwt，redisson，mybatis等app服务端一般用到的组件

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

3. 实现组合接口功能
如
```
curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -H "Postman-Token: 35d5e900-65b6-a62b-b5eb-c82e0a761915" -d '[  
    {  
        "url": "/users/bean",   
        "method": "GET"  
    },   
    {  
        "url": "/books",   
        "method": "GET",  
        "param" : {  
        	"name":""  
        }  
    }  
]' "http://localhost:8080/combine"  
```
接口会分别返回每个url对应的结果
```
[
  {
    "url": "/books",
    "method": "GET",
    "entity": {
      "content": [
        {
          "id": 2,
          "uuid": "11233",
          "name": "testName",
          "author": "binglin"
        },
        {
          "id": 20000,
          "uuid": "11233",
          "name": "testName",
          "author": "binglin"
        },
        {
          "id": 20001,
          "uuid": "11233",
          "name": "testName",
          "author": "binglin"
        },
        {
          "id": 20002,
          "uuid": "9937374",
          "name": "testName",
          "author": "binglin"
        },
        {
          "id": 20003,
          "uuid": "9937374",
          "name": "testName",
          "author": "binglin"
        },
        {
          "id": 20004,
          "uuid": "9937374",
          "name": "testName",
          "author": "binglin"
        },
        {
          "id": 20005,
          "uuid": "9937374",
          "name": "testName",
          "author": "binglin"
        },
        {
          "id": 20006,
          "uuid": "11233",
          "name": "testName",
          "author": "binglin"
        }
      ],
      "last": true,
      "totalElements": 8,
      "totalPages": 1,
      "number": 0,
      "size": 10,
      "sort": null,
      "first": true,
      "numberOfElements": 8
    },
    "httpStatus": 200
  },
  {
    "url": "/users/bean",
    "method": "GET",
    "entity": [
      {
        "id": 1,
        "name": "binglin",
        "password": "123256"
      },
      {
        "id": 2,
        "name": "tom",
        "password": "123456"
      }
    ],
    "httpStatus": 200
  }
]
```

## 
集成通用 Mapper 和 分页插件 Mybatis-PageHelper 集成
通用 Mapper https://github.com/abel533/Mapper)
Myabtis 分页插件 https://github.com/pagehelper/Mybatis-PageHelper