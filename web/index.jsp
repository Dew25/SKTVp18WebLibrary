<%-- 
    Document   : index
    Created on : Nov 4, 2019, 6:03:24 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Библиотека группы SKTVp18</title>
    </head>
    <body>
        <H1>Добро пожаловать в SKTVp18WebLibrary!</H1>
        <p>${info}</p>
        <a href="showLogin">Вход</a><br>
        <a href="logout">Выход</a><br>
        <a href="newBook">Создать книгу</a><br>
        <a href="listBooks">Список книг</a><br>
        <a href="newReader">Создать читателя</a><br>
        <a href="listReaders">Список читателей</a><br>
        <a href="showTakeOnBook">Выдать книгу</a><br>
        <a href="showReturnBook">Вернуть книгу</a><br>
    </body>
</html>
