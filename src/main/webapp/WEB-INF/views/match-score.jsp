<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Match Score</title>
</head>
<body>
<h1>Tennis Match: ${player1Name} vs ${player2Name}</h1>

<table border="1">
    <tr>
        <th>Player</th>
        <th>Sets</th>
        <th>Games</th>
        <th>Points</th>
    </tr>
    <tr>
        <td>${player1Name}</td>
        <td>${player1Sets}</td>
        <td>${player1Games}</td>
        <td>${player1Points}</td>
    </tr>
    <tr>
        <td>${player2Name}</td>
        <td>${player2Sets}</td>
        <td>${player2Games}</td>
        <td>${player2Points}</td>
    </tr>
</table>

<% if (request.getAttribute("final") == null) { %>
<form method="post" action="${pageContext.request.contextPath}/match-score?uuid=${uuid}">
    <button type="submit" name="winner" value="player1">Point to ${player1Name}</button>
    <button type="submit" name="winner" value="player2">Point to ${player2Name}</button>
</form>
<% } else { %>
<h2>Match finished. Winner: ${winnerName}</h2>
<a href="${pageContext.request.contextPath}/new-match">New Match</a>
<% } %>

<br>
<a href="${pageContext.request.contextPath}/">Home</a>
</body>
</html>