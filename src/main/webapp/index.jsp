<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tennis Scoreboard – Home</title>
</head>
<body>

<h1>Tennis Scoreboard</h1>
<p>
    A web application for tracking tennis match scores.<br>
    Supports best-of-3 sets, tie-breaks, and standard tennis scoring rules.
</p>

<p>
    <a href="${pageContext.request.contextPath}/new-match">Start New Match</a> |
    <a href="${pageContext.request.contextPath}/matches">View Completed Matches</a>
</p>

<h2>Project Overview</h2>
<p>This application was built as a learning project to practice:</p>
<ul>
    <li>Backend: Java Servlets, JSP, Hibernate ORM</li>
    <li>Database: PostgreSQL (via JDBC)</li>
    <li>Frontend: HTML, STL</li>
    <li>Build Tool: Maven</li>
    <li>Testing: JUnit 5</li>
    <li>Deployment: Apache Tomcat</li>
</ul>

<h2>Available Endpoints</h2>
<table border="1">
    <tr>
        <th>URL</th>
        <th>Method</th>
        <th>Description</th>
    </tr>
    <tr>
        <td><code>/new-match</code></td>
        <td>GET / POST</td>
        <td>Create a new tennis match</td>
    </tr>
    <tr>
        <td><code>/match-score?uuid=...</code></td>
        <td>GET / POST</td>
        <td>Live score tracking for an ongoing match</td>
    </tr>
    <tr>
        <td><code>/matches</code></td>
        <td>GET</td>
        <td>List completed matches with pagination and search</td>
    </tr>
</table>

<h2>License</h2>
<p>
    This project is open-source under the <strong>MIT License</strong>
    (or <strong>GNU General Public License v3.0</strong> – see repository).
</p>

<h2>Contacts</h2>
<p>
    Author: <strong>Amanbek Aziz uulu</strong><br>
    GitHub: <a href="https://github.com/AmanbekAzizUulu/simple-tennis-scoreboard">Source Code</a><br>
    Email: <a href="mailto:aibekdandaevv@gmail.com">aibekdandaevv@gmail.com</a>
</p>

<hr>
<p>&copy; 2026 Tennis Scoreboard</p>

</body>
</html>