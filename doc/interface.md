## User
### url
>/api/user

>#### method GET

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
