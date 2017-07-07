<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>User registration</title>
	  <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="material.min.css">
      <script src="material.min.js"></script>
      <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>

<form action="register" method="post">  
    
    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
               <input class="mdl-textfield__input" type="text" id="firstname" name="firstname">
               <label class="mdl-textfield__label" for="firstname">First Name: </label>
    </div>
    
    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
               <input class="mdl-textfield__input" type="text" id="lastname" name="lastname">
               <label class="mdl-textfield__label" for="lastname">Last Name: </label>
    </div>
    
    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
               <input class="mdl-textfield__input" type="text" id="email" name="email">
               <label class="mdl-textfield__label" for="email">Email: </label>
    </div>
    
    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
               <input class="mdl-textfield__input" type="text" id="username" name="username">
               <label class="mdl-textfield__label" for="username">Username: </label>
    </div>
    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
               <input class="mdl-textfield__input" type="password" id="password" name="password">
               <label class="mdl-textfield__label" for="password">Password: </label>
    </div> 
      
    <input type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" value = "Register">
     
    </form>

</body>
</html>