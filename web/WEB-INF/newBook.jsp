<%-- 
    Document   : newBook
    Created on : Nov 18, 2019, 7:21:01 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Новая книга</title>
    </head>
    <body>
        <h1>Создать книгу</h1>
        <form action="addBook" method="POST">
            Название книги: <input type="text" name="title"><br>
            Автор книги: <input type="text" name="author"><br>
            Год издания книги: <input type="text" name="year"><br>
            Ссылка на обложку: <input type="text" name="cover"><br>
            Текст книги: <textarea name="textBookInString"></textarea><br>
            <input type="submit" value="Создать книгу"><br>
        </form>
    </body>
</html>
