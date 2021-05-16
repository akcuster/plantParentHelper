
<%@include file="taglib.jsp"%>
<html>
<c:import url="head.jsp"/>

  <body>
  <h3>${user.userName}</h3>
  <h3>${user.firstName}</h3>
  <h3>${user.lastName}</h3>
  <h3>${user.age}</h3>

  <c:forEach var="plant" items="${usersPlants}">
    ${plant.plantName}<br>
  </c:forEach>






  <a href="${pageContext.request.contextPath}/add-plant.jsp">Add Plants</a>
  <a href="${pageContext.request.contextPath}/log-out">Log Out</a>
  </body>
</html>
