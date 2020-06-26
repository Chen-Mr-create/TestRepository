<%--
  Created by IntelliJ IDEA.
  User: chen
  Date: 2020/6/2
  Time: 11:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>登录页面</title>
</head>
<body>

    <form action="${pageContext.request.contextPath}/doLogin" method="post">
        <p>
            用户名：<input type="text" name="username"/>
        </p>
        <p>
            密码：<input type="password" name="password"/>
        </p>
        <p>
            <input type="submit" value="登录"/>
        </p>
    </form>
</body>
</html>
