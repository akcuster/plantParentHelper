<%@include file="taglib.jsp"%>
<html>
<c:import url="head.jsp"/>
<body>
    <form action="add-plant" class="form-inline">
        <div class="form-group">
            <label for="plantName">Plant Name</label>
            <input type="text" class="form-control" name="plantName" id="plantName">
        </div>
        <div class="form-group">
            <label for="dateAdopted">Plant's Adoption Date</label>
            <input type="date" class="form-control" name="dateAdopted" id="dateAdopted">
        </div>
        <button type="submit" name="submit" value="addPlant" class="btn btn-primary">Add Plant</button>
    </form>

    <h3>${outputMessage}</h3>

    <c:if test="${plants != null}" >
        <table>
            <tbody>
            <tr><th>Plant Name</th><th></th></tr>
            <c:forEach var="plant" items="${plants}">
                <tr><td>${plant.plantName}</td><td><a href="${pageContext.request.contextPath}/add-plant?plantid=${plant.id}">Select Plant</a></td></tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>



</body>
</html>
