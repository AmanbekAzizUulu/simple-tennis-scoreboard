<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Completed Matches</title>
</head>
<body>
<h1>Completed Matches</h1>

<form action="${pageContext.request.contextPath}/matches" method="get">
    <label>Filter by player name:
        <input type="text" name="filter_by_player_name" value="${filterName}">
    </label>
    <button type="submit">Search</button>
</form>

<c:if test="${not empty matches}">
    <table border="1">
        <tr>
            <th>Player 1</th>
            <th>Player 2</th>
            <th>Winner</th>
            <th>Date</th>
        </tr>
        <c:forEach items="${matches}" var="match">
            <tr>
                <td>${match.player1.name}</td>
                <td>${match.player2.name}</td>
                <td>${match.winner.name}</td>
                <td>${match.playedAt}</td>
            </tr>
        </c:forEach>
    </table>

    <c:if test="${totalPages > 1}">
        <div>
            <c:url var="prevUrl" value="/matches">
                <c:param name="page" value="${currentPage - 1}"/>
                <c:if test="${not empty filterName}">
                    <c:param name="filter_by_player_name" value="${filterName}"/>
                </c:if>
            </c:url>
            <c:if test="${currentPage > 1}">
                <a href="${prevUrl}">&laquo; Previous</a>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="i">
                <c:url var="pageUrl" value="/matches">
                    <c:param name="page" value="${i}"/>
                    <c:if test="${not empty filterName}">
                        <c:param name="filter_by_player_name" value="${filterName}"/>
                    </c:if>
                </c:url>
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <strong>${i}</strong>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageUrl}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:url var="nextUrl" value="/matches">
                <c:param name="page" value="${currentPage + 1}"/>
                <c:if test="${not empty filterName}">
                    <c:param name="filter_by_player_name" value="${filterName}"/>
                </c:if>
            </c:url>
            <c:if test="${currentPage < totalPages}">
                <a href="${nextUrl}">Next &raquo;</a>
            </c:if>
        </div>
    </c:if>
</c:if>

<c:if test="${empty matches}">
    <p>No matches found.</p>
</c:if>

<br>
<a href="${pageContext.request.contextPath}/">Home</a>
</body>
</html>