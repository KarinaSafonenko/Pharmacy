<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
Добро пожаловать, ${sessionScope.name} ${sessionScope.surname} [${sessionScope.role}]!
<a href="/jsp/register.jsp">Главная</a>
</body>
</html>
