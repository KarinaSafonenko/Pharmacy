<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html><head><title>Login</title></head>
<body>
<form name="registration" method="POST" action="/ControllerServlet">
    <input type="hidden" name="command" value="registration" />
    Фамилия:<br/>
    <input type="text" name="surname" value=""/>
    <br/>
    ${wrongSurnameMessage}
    <br>Имя:<br/>
    <input type="text" name="name" value=""/>
    <br/>
    ${wrongNameMessage}
    <br>Отчество:<br/>
    <input type="text" name="patronymic" value=""/>
    <br/>
    ${wrongPatronymicMessage}
    <br>Пол:<br/>
    <select name="sex">
        <option value="male">мужской</option>
        <option value="female">женский</option>
    </select>
    <br>Адрес электронной почты:<br/>
    <input type="text" name="mail"/>
    <br/>
    ${wrongEmailMessage}
    <br>Логин:<br/>
    <input type="text" name="login"/>
    <br/>
    ${wrongLoginMessage}
    <br/>Пароль:<br/>
    <input type="password" name="password"/>
    <br/>
    ${wrongPasswordMessage}
    <br/>
    <input type="submit" value="Зарегистрироваться"/>
</form>
</body>
</html>