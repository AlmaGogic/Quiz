<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 
	  <meta name="viewport" content="width=device-width, initial-scale=1">
      
      <script src="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.min.js"></script>
      <link href="../material.css" rel="stylesheet" type="text/css">

<title>User home</title>
</head>
<body>

<!-- User toolbar -->
<div class="demo-layout-transparent mdl-layout mdl-js-layout">
  <header class="mdl-layout__header mdl-layout__header--transparent">
    <div class="mdl-layout__header-row">
      <!-- Title -->
      <span class="mdl-layout-title">Editing</span>
      <!-- Add spacer, to align navigation to the right -->
      <div class="mdl-layout-spacer"></div>
      <!-- Navigation -->
      <nav class="mdl-navigation">
        <span class="mdl-badge" data-badge="4">Inbox</span>
        <a class="mdl-navigation__link" href="home">Quiz list</a>
        <a class="mdl-navigation__link" href="">Users</a>
        <a class="mdl-navigation__link" href="">Log out</a>
      </nav>
    </div>
  </header>
  <div class="mdl-layout__drawer">
    <span class="mdl-layout-title">Editing</span>
    <nav class="mdl-navigation">
      <a class="mdl-navigation__link" href="home">Quiz list</a>
      <a class="mdl-navigation__link" href="">Users</a>
      <a class="mdl-navigation__link" href="">Inbox</a>
      <a class="mdl-navigation__link" href="">Log out</a>
    </nav>
  </div>
  <main class="mdl-layout__content">
   <div class="page-content" id="container" style="padding-left:100px;">Welcome, here you can edit Quizzes!
   		<div class="mdl-grid" id="quiz"></div>
   		</div>
  </main>
</div>


</body>
</html>