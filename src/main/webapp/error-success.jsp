<%@include file="taglib.jsp"%>
<!DOCTYPE html>
<html>
<c:set var="title" value="Plant Collector - Home" scope="session" />
    <c:import url="head.jsp" />
    <body>
    <c:import url="navigation.jsp" />
    <div class="container">
        <main class="d-flex row">

            <h1 class="text-center mt-5">${outputMessage}</h1>
        </main>
    <c:import url="footer.jsp" />
    </div>
    </body>
</html>
