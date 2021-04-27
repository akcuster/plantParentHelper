<%@include file="taglib.jsp"%>
<%@include file="head.jsp"%>

<html>
<body>
    <h2>Search Results</h2>
    <table>
        <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">User Name</th>
                <th scope="col">First Name</th>
                <th scope="col">Last Name</th>
                <th scope="col">Age</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.userName}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
