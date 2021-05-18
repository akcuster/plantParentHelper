<%@include file="taglib.jsp"%>
<nav class="navbar navbar-expand-lg navbar-light shadow-lg" style="background-color: yellowgreen">
    <div class="container-fluid p-2">
        <span class="navbar-brand fs-1" id="logo">Plant Collector</span>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav ms-auto">
                <c:if test="${user == null}" >
                    <a class="nav-link" href="${pageContext.request.contextPath}/log-in">Log In/Sign Up</a>
                </c:if>

                <c:if test="${user != null}" >
                    <a class="nav-link" href="${pageContext.request.contextPath}/user-profile">Profile</a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/add-plant">Add Plant</a>
                    <a class="nav-link" href="${pageContext.request.contextPath}/log-out">Log Out ${user.userName}</a>
                </c:if>
            </div>
        </div>
    </div>
</nav>

