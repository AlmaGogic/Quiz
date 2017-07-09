
<style>
.demo-layout-transparent {
  background: url('http://www.twitrcovers.com/wp-content/uploads/2013/02/Programming-Code-l.jpg') center / cover;
}
.demo-layout-transparent .mdl-layout__header,
.demo-layout-transparent .mdl-layout__drawer-button {
  /* This background is dark, so we set text to white. Use 87% black instead if
     your background is light. */
  color: white;
}

.mdl-grid{
   color: red;
   padding-left: 100px;
   height: 200px;
   font-family: "Serif";
}

.mdl-cell{
    background-size: 100% 100%;
}

span:hover {
  font-weight: bold;
}

a:hover {
  font-size: 17px;
  font-weight: bold;
}

main {
	color: white;
}
</style>

<html>
<head>
      <title>Quiz Admin</title>
      <meta charset="utf-8">
	  <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.brown-deep_orange.min.css">
      <script src="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.min.js"></script>
      <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>

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
        <a class="mdl-navigation__link" href="/RWAProjekat-Quiz/login">Log out</a>
      </nav>
    </div>
  </header>
  <div class="mdl-layout__drawer">
    <span class="mdl-layout-title">Editing</span>
    <nav class="mdl-navigation">
      <a class="mdl-navigation__link" href="home">Quiz list</a>
      <a class="mdl-navigation__link" href="">Users</a>
      <a class="mdl-navigation__link" href="">Inbox</a>
      <a class="mdl-navigation__link" href="/RWAProjekat-Quiz/login">Log out</a>
    </nav>
  </div>
  <main class="mdl-layout__content">
   <div class="page-content" id="container" style="padding-left:100px;">Welcome, here you can edit Quizzes!
   		<div class="mdl-grid" id="quiz"></div>
   		</div>
  </main>
</div>
     
<script type="text/javascript">
	function editQuiz(qName){
		 document.location.href = "quiz?name=" + qName;	
	}
	
		document.getElementById("quiz").innerHTML = "";
		var p = <%=request.getAttribute("q")%>;
	
		for (var key in p) {
			if (p.hasOwnProperty(key)) {
				var sdiv = document.createElement("div");
				sdiv.setAttribute("class", "mdl-cell mdl-cell--4-col");
				sdiv.setAttribute("onclick", "editQuiz('" + key + "')");
				var url = p[key];
				sdiv.style.backgroundImage = "url(" + url + ")";
				sdiv.setAttribute("id", key);
				document.getElementById("quiz").appendChild(sdiv);
				
	      }
         }
	
</script> 
          
</body>
</html>