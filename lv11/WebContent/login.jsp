<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="UTF-8">
  <title>Quiz Login</title>
    <link rel="stylesheet" type="text/css" href="./loginStyle.css">
  

</head>

<body>
  <div class="section"></div>
  <main>
    <center>
      <img class="responsive-img" style="width: 250px;" src="https://img.evbuc.com/https%3A%2F%2Fcdn.evbuc.com%2Fimages%2F31843262%2F200686349170%2F1%2Foriginal.jpg?h=230&w=460&rect=9%2C0%2C2886%2C1443&s=5b8030079050ab0bb9083d0e6c41f0a6" />
      <div class="section"></div>

      <h5 class="indigo-text">Please, login into your account</h5>
      <div class="section"></div>

      <div class="container">
        <div class="z-depth-1 grey lighten-4 row" style="display: inline-block; padding: 32px 48px 0px 48px; border: 1px solid #EEE;">

          <form class="col s12" method="post">
            <div class='row'>
              <div class='col s12'>
              </div>
            </div>

            <div class='row'>
              <div class='input-field col s12'>
               
               <label  for="username">Username: </label>
   				 <input class="validate" type="text" id="username" name="username">
              </div>
            </div>

            <div class='row'>
              <div class='input-field col s12'>
                <label for='password'>Enter your password:</label>
       			<input class='validate' type='password' name='password' id='password' />
              </div>
            </div>

            <br />
            <center>
              <div class='row'>
                <button type='submit' name='btn_login' class='col s12 btn btn-large waves-effect indigo'>Login</button>
              </div>
            </center>
          </form>
        </div>
      </div>
      <div class="link">
      <a href="register">Create account</a>
      </div>
    </center>

  </main>

  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>
</body>

</html>
 