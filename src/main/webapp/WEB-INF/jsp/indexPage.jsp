<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Insert title here</title>

</head>
<body>
<form action="/addUserPage" method="post">
    <input type="hidden" name="token" value="${token}"><span>姓名</span><input type="text" name="name"><br/>
    <span>年龄</span><input type="text" name="age"><br/>
    <span>性别</span><input type="text" name="sex"><br/>
    <input type="submit" name = "submit">
</form>
</body>
</html>