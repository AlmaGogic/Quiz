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

.mdl-layout {
  color: white;
  font-size: 16px;
}

.cell {
    background: #00296b;
    border-radius: 25px;
    padding-left: 15px;
    width: 600px;   
}

.mdl-textfield {
	 background: #4cc47c;
	 border-radius: 25px;
}

a:hover {
  font-size: 17px;
  font-weight: bold;
}

.deleteItem{
	float: right;
	width: 10%;
	color: gray; 
	font-weight: bold;
	cursor: pointer;
}

.deleteItem:hover{
    color: red;
}

</style>



<html>
<head>
	  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Users edit</title>
	  <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.brown-deep_orange.min.css">
      <script src="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.min.js"></script>
      <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>

	<div class="demo-layout-transparent mdl-layout mdl-js-layout">
	<header class="mdl-layout__header mdl-layout__header--transparent">
    <div class="mdl-layout__header-row">
      <!-- Title -->
      <span class="mdl-layout-title">Editing quiz</span>
      <!-- Add spacer, to align navigation to the right -->
      <div class="mdl-layout-spacer"></div>
      <!-- Navigation -->
      <nav class="mdl-navigation">
        <a class="mdl-navigation__link" href="home">Quiz list</a>
        <a class="mdl-navigation__link" href="/RWAProjekat-Quiz/login">Log out</a>
      </nav>
    </div>
  </header>

	<main class="mdl-layout__content">
   			<div class="page-content" id="container" style="padding-left:100px;">Welcome, here you can edit Users!
   			<div id="users" class="us"></div>
   			
   			<form action="users" method="post">  
		    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
		               <input class="mdl-textfield__input" type="text" id="username" name="username">
		               <label class="mdl-textfield__label" for="username">Username: </label>
		    </div>
		    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
		               <input class="mdl-textfield__input" type="text" id="firstName" name="firstName">
		               <label class="mdl-textfield__label" for="firstName">First Name: </label>
		    </div> 
		    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
		               <input class="mdl-textfield__input" type="text" id="lastName" name="lastName">
		               <label class="mdl-textfield__label" for="lastName">Last Name: </label>
		    </div> 
		    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
		               <input class="mdl-textfield__input" type="text" id="email" name="email">
		               <label class="mdl-textfield__label" for="email">E-mail: </label>
		    </div> 
		    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
		               <input class="mdl-textfield__input" type="text" id="pass" name="pass">
		               <label class="mdl-textfield__label" for="pass">Password: </label>
		    </div> 
		    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
		               <input class="mdl-textfield__input" type="text" id="role" name="role">
		               <label class="mdl-textfield__label" for="role">Role: </label>
		    </div> 
		      
		    <input type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" value = "Create user">
		     
		    </form>  
   			
   			</div>
   	</main>

   </div>
   
   <script type="text/javascript">
	    console.log("korisnik");
		document.getElementById("users").innerHTML = "";
		var p = <%=request.getAttribute("users")%>;
		
		for (var key in p) {
			if (p.hasOwnProperty(key)) {
				
				var sdiv = document.createElement("div");
				sdiv.setAttribute("id", key);
				sdiv.setAttribute("class", "cell");
				
				sdiv.textContent = "  " + key;
				
				var del = document.createElement("div");
				del.setAttribute("class", "deleteItem");
				del.setAttribute("onclick", "removeUser('" + key + "')");
				del.textContent = "X";
				
				sdiv.appendChild(del);
				document.getElementById("users").appendChild(sdiv);
				
				var param = p[key];
				for(var i = 0; i<param.length; i++){
					var par = document.createElement("div");
					
					par.setAttribute("class", "mdl-cell");
					par.setAttribute("id", param[i]);
					par.textContent = param[i];
					
					document.getElementById(key).appendChild(par);
				}

	      }
         }
		
		function removeUser(u){	
			document.getElementById(u).remove();
			var sendReq = new XMLHttpRequest();
		    sendReq.open("POST", "http://localhost:8080/RWAProjekat-Quiz/admin/users?req=removeUser&user=" + u);
		    sendReq.send();
			
		}
		
   </script>
   
</body>
</html>