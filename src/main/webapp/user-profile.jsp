
<%@include file="taglib.jsp"%>
<html>
<c:import url="head.jsp"/>

  <body>
  <h3>${user.userName}</h3>
  <h3>${user.firstName}</h3>
  <h3>${user.lastName}</h3>
  <h3>${user.age}</h3>
  <c:choose>
    <c:when test="user.plants != null">
      <c:forEach var="plant" items="${user.plants}">
        ${userPlant.plant.plantName}<br>
      </c:forEach>
    </c:when>

    <c:otherwise>
      <h4>You Don't Have Any Plants.</h4>
    </c:otherwise>
  </c:choose>
  </body>
</html>
