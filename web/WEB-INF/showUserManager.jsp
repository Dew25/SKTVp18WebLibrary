<%-- 
    Document   : showUserManager
    Created on : Jun 3, 2020, 7:11:54 PM
    Author     : user
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Уравление пользователями</h1>
        <form action="changeRole" method="POST">
            <table>
                <tr>
                    <th>Список пользователей</th>
                    <th>Список ролей</th>
                </tr>
                <tr>
                    <td>
                        <select name="userId">
                            <c:forEach var="entry" items="${mapUsers}">
                                <option value="${entry.key.id}">${entry.key.login} Role: ${entry.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>
                        <select name="roleId">
                            <c:forEach var="role" items="${listRoles}">
                                <option value="${role.id}">${role.roleName}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Изменить">
                    </td>
                </tr>
            </table>
            
        </form>    
    </body>
</html>
