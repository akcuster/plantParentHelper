

<html>
<c:import url="head.jsp"/>

  <body>
  <h3>${user.userName}</h3>
  <h3>${user.firstName}</h3>
  <h3>${user.lastName}</h3>
  <h3>${user.age}</h3>

  <h3>${outputMessage}</h3>

  <c:forEach var="plants" items="${usersPlants}">
    <a href="${pageContext.request.contextPath}/plant-profile?userPlantId=${plants.key}">${plants.value}</a><a href="${pageContext.request.contextPath}/remove-plant?userPlantId=${plants.key}">Remove</a>
  </c:forEach>






  <a href="${pageContext.request.contextPath}/add-plant.jsp">Add Plants</a>
  <a href="${pageContext.request.contextPath}/log-out">Log Out</a>
  </body>
</html>
