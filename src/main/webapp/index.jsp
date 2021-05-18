<%@include file="taglib.jsp"%>
<!DOCTYPE html>
<html>
<c:set var="title" value="Plant Collector - Home" scope="session" />
  <c:import url="head.jsp" />
  <body>
  <c:import url="navigation.jsp" />
  <div class="container">
    <main class="d-flex row">
      <div class="col-md row">
        <div class="col-md-8">
          <img src="images/plantPet.jpg" class="img-fluid my-4 mx-auto shadow-lg" alt="A hand holding a small cactus in a clear pot against a yellow background">
        </div>
        <div class="col-md">

        </div>
      </div>
      <div class="col-md">
        <div class="card mt-5 shadow-lg">
          <h2 class="card-header text-dark text-center">Welcome To Plant Collector</h2>
          <div class="card-body">
            <p class="card-text pl-3 fs-3">
              <span class="fw-bold" id="firstLetter">S</span>ee your collection all in one place, get a customized care schedule, earn points for nurturing your chlorophyll loving family, and compete against your frienemies to see who has the lushest indoor tropical paradise!
            </p>
          </div>
        </div>
      </div>
    </main>
  <c:import url="footer.jsp" />
  </div>
  </body>
</html>
