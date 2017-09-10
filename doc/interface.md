## User
### url
>/api/user

>#### method GET
>>无需权限
>>##### request 
>>> ?id=123 或 ?from_context=true 或 ?username=abc

>>##### response

>>>利用id/用户名:

>>>>```json
>>>>{
>>>>    "id":123,
>>>>    "username":"??",
>>>>    "registerTime":123,
>>>>    "role":"??"
>>>>}
>>>>```

>>>利用security context:

>>>>已登录:

>>>>```json
>>>>{
>>>>    "id":123,
>>>>    "username":"??",
>>>>    "registerTime":123,
>>>>    "role":"??"
>>>>}
>>>>```

>>>未登录:
>>>>code:400



>#### method POST PUT DELETE
>>不支持


## UserInfo
### url
>/api/user_info

> #### method GET
>>无需权限
>> ##### request
>>> ?id=123

>> ##### response


>>>>```json
>>>>{
>>>>    "userId":123,
>>>>    "gender":"??",
>>>>    "avatarPath":"??",
>>>>    "personalSignature":"??"
>>>>}
>>>>```


>#### method PUT
>>需登录
>>##### request

>>>```json
>>>{
>>>     "id":123,
>>>     "gender":"??",
>>>     "avatarPath":"??",
>>>     "personalSignature":"??"
>>> }
>>>```

>>##### response
>>>已登录且修改成功:
>>>>code:200

>>>未登录或修改失败:
>>>>code:400



>#### method POST DELETE
>>不支持


## UserSecurity
###url
> /api/user_security

> #### method GET
>>需登录
>> ##### request

>>>[none]

>> #### response
>>>已登录:
>>>```json
>>>{
>>>    "oldQuestion1":"??",
>>>    "oldQuestion2":"??",
>>>    "oldQuestion3":"??"
>>>}
>>>```

>>>未登录:
>>>> code: 400

 

> #### method PUT
>>需登录
>> ##### request

>>>"update"属性用于指定是否为修改用途，否则新密码、新密保问题无效
>>>```json
>>>{
>>>    "update":true,
>>>    "oldPassword":"??",
>>>    "oldQuestion1":"??",
>>>    "oldAnswer1":"??",
>>>    "oldQuestion2":"??",
>>>    "oldAnswer2":"??",
>>>    "oldQuestion3":"??",
>>>    "oldAnswer3":"??",
>>>    "newPassword":"??",
>>>    "newQuestion1":"??",
>>>    "newAnswer1":"??",
>>>    "newQuestion2":"??",
>>>    "newAnswer2":"??",
>>>    "newQuestion3":"??",
>>>    "newAnswer3":"??"
>>>}
>>>```

>> ##### response

>>> 已登录且旧密码与旧密保问题验证正确:

>>>>code:200

>>>未登录或验证不通过

>>>>code:400


## SignUp

###url
> /api/sign_up_info

>#### method POST

>>#####request

>>>
>>>```json
>>>{
>>>    "username":"??",
>>>    "gender":"??",
>>>    "password":"??",
>>>    "question1":"??",
>>>    "answer1":"??",
>>>    "question2":"??",
>>>    "answer2":"??",
>>>    "question3":"??",
>>>    "answer3":"??"
>>>}
>>>```

>>#####response

>>>成功:
>>>>code:200

>>>失败:
>>>>code:400



## Image

###url
> /image/avatar
>>头像上传与获取

>#### method POST
>>需登录
>>##### request

>>>key:pic

>>>value:[multipart]


>>##### response

>>>成功:

>>>>code:200

>>>>body:
>>>>```json
>>>>{"avatarName":"??"}
>>>>```
>>>失败:400

>#### method GET
>>无需权限
>>##### request

>>>/{avatarName}

>>##### response

>>>图片数据流

### url
> /image/article

> #### method GET
>>无需权限

>>##### request

>>>/{imageName}

>>##### response

>>>图片数据流


>#### method POST
>>admin权限

>>##### request

>>>key:pic

>>>value:[multipart]



>>##### response

>>>成功:

>>>>code:200

>>>>body:
>>>>```json
>>>>{"imageName":"??"}
>>>>```
>>>失败:400

## Article

### url
> /api/article

>#### method GET

>>获取单篇文章

>>无需权限

>>##### request

>>> /{id}

>>##### response

>>>```json
>>>{
>>>    "title":"???",
>>>    "thumbnailName":"???",
>>>    "content":"???",
>>>    "authorId":123,
>>>    "creationTime":123,
>>>    "readCount":123,
>>>    "likeCount":123
>>>}
>>>```

>#### method POST

>> 发表文章

>> 需admin权限

>>##### request

>>>```json
>>>{
>>>    "title":"???",
>>>    "content":"???",
>>>    "thumbnailName":"???",
>>>    "topic":"???"
>>>}
>>>```


### url
> /api/articles
>>获取多篇文章摘要

>#### method GET

>>##### request

>>> ?order=[latest/hottest]&topic=[sim/ream]&count=[数目]&page=[页序号]
>>> top

>>##### response

>>>```json
>>>[
>>>      {
>>>        "id":123,
>>>        "title":"???",
>>>        "thumbnailName":"???",
>>>        "creationTime":123,
>>>        "readCount":123,
>>>        "likeCount":123
>>>      },
>>>      ...
>>>]
>>>```

### url
> /api/articles/page_count

>#### method GET
>>获取总页数

>>##### request

>>> ?topic=[sim/real]&count_per_page=[每页数目]

>>##### response

>>> [页数]