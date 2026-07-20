<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Match</title>
</head>
<body>
<h1>New Tennis Match</h1>

<% if (request.getAttribute("error") != null) { %>
<p><%= request.getAttribute("error") %></p>
<% } %>

<form method="post" action="${pageContext.request.contextPath}/new-match">
    <label>Player 1: <input type="text" name="player1" required></label><br>
    <label>Player 2: <input type="text" name="player2" required></label><br>
    <button type="submit">Start Match</button>
</form>
</body>
</html>