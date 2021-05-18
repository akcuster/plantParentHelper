<%@include file="taglib.jsp"%>
<!DOCTYPE html>
<html>
<c:set var="title" value="Plant Collector - Profile" scope="session" />
  <c:import url="head.jsp" />
  <body>
    <c:import url="navigation.jsp" />
    <div class="container">
      <main class="row d-flex mt-5">
        <div class="col-md px-5">
          <h3>Welcome ${user.userName}!</h3>
          <table class="table table-secondary table-striped my-2 shadow-lg">
            <thead>
              <tr>
                <th scope="col">User Stats</th>
                <th scope="col"></th>
              </tr>
            </thead>
            <tbody>
              <tr><th>Username: </th><td>${user.userName}</td></tr>
              <tr><th>First Name: </th><td>${user.firstName}</td></tr>
              <tr><th>Last Name: </th><td>${user.lastName}</td></tr>
              <tr><th>Age: </th><td>${user.age}</td></tr>
            </tbody>
          </table>
        </div>
        <div class="col-md px-5">
          <h3>${outputMessage}</h3>

          <c:if test="${usersPlants != null}" >
            <table class="table table-success table-striped my-2 shadow-lg">
              <thead>
                <tr><th scope="col">Plant Name</th><th scope="col">Remove Plant</th></tr>
              </thead>
              <tbody>
              <c:forEach var="plants" items="${usersPlants}">
                <tr><td><a href="${pageContext.request.contextPath}/plant-profile?userPlantId=${plants.key}">${plants.value} </a></td><td><a href="${pageContext.request.contextPath}/remove-plant?userPlantId=${plants.key}">Remove</a></td></tr>
              </c:forEach>
              </tbody>
            </table>
          </c:if>
        </div>
      </main>
      <c:import url="footer.jsp" />
    </div>
  </body>
</html>

