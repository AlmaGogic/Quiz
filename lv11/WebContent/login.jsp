<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
      <title>Quiz Login</title>
	  <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.brown-deep_orange.min.css">
      <script src="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.min.js"></script>
      <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>

    <form action="login" method="post">  
    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
               <input class="mdl-textfield__input" type="text" id="username" name="username">
               <label class="mdl-textfield__label" for="username">Name: </label>
    </div>
    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
               <input class="mdl-textfield__input" type="password" id="password" name="password">
               <label class="mdl-textfield__label" for="password">Password: </label>
    </div> 
      
    <input type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" value = "login">
     
    </form>  
</body>
</html>