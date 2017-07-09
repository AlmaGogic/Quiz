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

.mdl-cell {
    background: #00296b;
    border-radius: 25px;
    padding-left: 15px;
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

.ques{
    padding-bottom: 15px;
}

</style>


<html>
<head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Quiz edit</title>
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
   			<div class="page-content" id="container" style="padding-left:100px;">Welcome, here you can edit Quizzes!
   			<div id="questions" class="ques"></div>
   			
   			<form action="admin/quiz" method="post">  
		    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
		               <input class="mdl-textfield__input" type="text" id="qTxt" name="qTxt">
		               <label class="mdl-textfield__label" for="qTxt">Question: </label>
		    </div>
		    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label">
		               <input class="mdl-textfield__input" type="text" id="aTxt" name="aTxt">
		               <label class="mdl-textfield__label" for="password">Answer: </label>
		    </div> 
		      
		    <input type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--accent" value = "admin/quiz">
		     
		    </form>  
   			
   			</div>
  		</main>
	</div>

<script type="text/javascript">
	    console.log("kviz");
		document.getElementById("questions").innerHTML = "";
		var p = <%=request.getAttribute("questions")%>;
		
		for (var key in p) {
			if (p.hasOwnProperty(key)) {
				
				var sdiv = document.createElement("div");
				sdiv.setAttribute("id", key);
				sdiv.setAttribute("class", "mdl-cell");
				
				sdiv.textContent = "  " + key;
				
				var del = document.createElement("div");
				del.setAttribute("class", "deleteItem");
				del.setAttribute("onclick", "removeQuestion('" + key + "')");
				del.textContent = "X";
				
				sdiv.appendChild(del);
				document.getElementById("questions").appendChild(sdiv);
				
				var answerList = p[key];
				for(var i = 0; i<answerList.length; i++){
					var answer = document.createElement("div");
					var delAns = document.createElement("div");
					
					answer.setAttribute("class", "mdl-cell");
					answer.setAttribute("id", answerList[i]);
					answer.textContent = answerList[i];
					
					delAns.setAttribute("class", "deleteItem");
					delAns.setAttribute("onclick", "removeAnswer('" + key + "','" + answerList[i] + "')");
					delAns.textContent = "x";
					
					answer.appendChild(delAns);
					document.getElementById(key).appendChild(answer);
				}

	      }
         }
		
	function removeQuestion(k){	
		document.getElementById(k).remove();
		var sendReq = new XMLHttpRequest();
	    sendReq.open("POST", "http://localhost:8080/RWAProjekat-Quiz/admin/quiz?req=removeQuestion&question=" + k);
	    sendReq.send();
		
	}
	
	function removeAnswer(ques, ans){
		var children= document.getElementById(ques).childNodes;
		for(var i = 0; i<children.length; i++){
			if(children[i].id === ans){
				children[i].remove();
			}
		}
		var sendReq = new XMLHttpRequest();
		sendReq.open("POST", "http://localhost:8080/RWAProjekat-Quiz/admin/quiz?req=removeAnswer&question=" + ques + "&answer=" + ans);
	    sendReq.send();
	}
	
</script> 

</body>
</html>