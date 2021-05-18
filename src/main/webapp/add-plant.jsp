<%@include file="taglib.jsp"%>
<!DOCTYPE html>
<html>
<c:set var="title" value="Plant Collector - Profile" scope="session" />
<c:import url="head.jsp" />
<body>
<c:import url="navigation.jsp" />
<div class="container">
    <main class="row d-flex mt-5">
        <div class="row">
            <h3 class="mb-3">${outputMessage}</h3>
            <form action="add-plant" class="row gy-2 gx-3 align-items-center">
                <div class="form-group col-auto">
                    <label class="visually-hidden" for="plantName">Plant Name</label>
                    <input type="text" class="form-control" name="plantName" id="plantName" placeholder="Plant Name">
                </div>
                <div class="form-group col-auto">
                    <label class="visually-hidden" for="dateAdopted">Plant's Adoption Date</label>
                    <input type="date" class="form-control" name="dateAdopted" id="dateAdopted" placeholder="Plant's Adoption Date">
                </div>
                <div class="col-auto">
                    <button type="submit" name="submit" value="addPlant" class="btn btn-success">Add Plant</button>
                </div>
            </form>
        </div>
        <div class="row">
            <div class="col-med-2">

            </div>
            <div class="col-med-8">
                <c:if test="${plants != null}" >
                    <table class="table table-secondary table-striped my-2 shadow-lg">
                        <thead>
                        <tr><th scope="col">Plant Name</th><th scope="col"></th></tr>
                        </thead>
                        <tbody>
                        <c:forEach var="plant" items="${plants}">
                            <tr><td>${plant.plantName}</td><td><a href="${pageContext.request.contextPath}/add-plant?plantid=${plant.id}">Select Plant</a></td></tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>

            <div class="col-med-2">

            </div>
        </div>
    </main>
    <c:import url="footer.jsp" />
</div>
</body>
</html>
