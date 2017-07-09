<%@ page import="java.util.Collection" %>
<%@ page import="quizClasses.Quiz" %>
<%@ page import="quizClasses.Result" %>

<html>
<head>
      <title>Quiz User Home</title>
      <meta charset="utf-8">
	  <meta name="viewport" content="width=device-width, initial-scale=1">
      <script src="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.min.js"></script>
      <link rel="stylesheet" type="text/css" href="../userHomeStyle.css">
      <script src="../userHomeJS.js"></script>
</head>

<body>



<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
  <header class="mdl-layout__header">
    <div class="mdl-layout__header-row">
      <span class="mdl-layout-title">Welcome <%= session.getAttribute("username") %></span>
      <div class="mdl-layout-spacer"></div>
      
            
            <!-- start search form -->
            
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
                <label class="mdl-button mdl-js-button mdl-button--icon" id="searchClick" for="search-expandable">
                    <i class="material-icons">search</i>
                </label>
                <form action="" method="GET">
                <div class="mdl-textfield__expandable-holder">
                    <input class="mdl-textfield__input" type="text" name="search" id="search-expandable" />
                    <label class="mdl-textfield__label" for="search-expandable">Search text</label>
                </div>
                </form>
            </div>
            <!-- end search form -->
            
    </div>
  </header>
         <main>
         <div id="clear">
  <div class="mdl-grid">
  
 <% 
 String task=(String)request.getAttribute("task");
 if(task!=null){
	if(task.equals("search")){
 		Collection<Quiz> quizzes= (Collection<Quiz>)request.getAttribute("quizzes");
 		int i=0;
     	for(Quiz quiz: quizzes){
 		%>
    	<div class="mdl-cell mdl-cell--4-col">
      	<%=quiz.getQuizName() %>
      	</div>
      
 		<%
     	}
 	}
	else if(task.equals("profile")){
		%>
		<div>
		<style scoped>
        @import url("https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css");
    </style>
<div class="profile">
    	<div class="row">
    	    <div class="container">
        <div class="z-depth-1 grey lighten-4 row" style="display: inline-block; padding: 32px 48px 0px 48px; border: 1px solid #EEE;">
        	    <div class="form-wrap">
                <h4>Change your data</h4>
                    <form action="" class="col s12" method="post">
                        <div class="form-group">
                            <label for="firstname" class="sr-only">First name</label>
                            <input type="text" name="firstname" id="firstname" class="form-control" placeholder="First Name: ">
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="sr-only">Last name</label>
                            <input type="text" name="lastname" id="lastname" class="form-control" placeholder="Last Name: ">
                        </div>
                        <div class="form-group">
                            <label for="email" class="sr-only">Email</label>
                            <input type="email" name="email" id="email" class="form-control" placeholder="somebody@example.com">
                        </div>
                        <div class="form-group">
                            <label for="username" class="sr-only">Username</label>
                            <input type="text" name="username" id="username" class="form-control" placeholder="Username: ">
                        </div>
                        <div class="form-group">
                            <label for="key" class="sr-only">Password</label>
                            <input type="password" name="password" id="password" class="form-control" placeholder="Password: ">
                        </div>
                        <div class='row'>
                			<button type='submit' name='btn_login' class='col s12 btn btn-large waves-effect btn-success'>SAVE</button>
              			</div>
                    </form>
                    
        	    
        	    </div>
    		</div> <!-- /.col-xs-12 -->
    	</div> <!-- /.row -->
    </div> <!-- /.container -->
</div>
</div>
		
	<% }
	else{
		if(!request.getAttribute("results").equals("none")){
		Collection<Result> results= (Collection<Result>)request.getAttribute("results");
 		
		%>
		<div class="table">
		<style scoped>
		@import url(../scopedTableStyle.css);

    </style>
		
		
		<table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
        <thead>
            <tr>
                <th>First name</th>
                <th>Last name</th>
                <th>Quiz name</th>
                <th>Total points</th>
            </tr>
        </thead>
        <tbody>
            <tr>
            <%
            
            Collection<Result> listresults= (Collection<Result>)request.getAttribute("results");
 		int i=0;
     	for(Result result: listresults){
 		%>
			<td><%= result.getFirstName() %></td>
            <td><%= result.getLastName() %></td>
            <td><%= result.getQuiz().getQuizName() %></td>
            <td><%= result.getTotalPoints() %></td>
            
      
 		<%
     	}%>
            	
                
            </tr>
            
        </tbody>
    </table>
    </div>
		
		<%} 
	}
 }
 %>
 </div>
    </div>
    
</main>
    <div class="mdl-layout__drawer">
    <span class="mdl-layout-title">Menu</span>
    <nav class="mdl-navigation">
      <a class="mdl-navigation__link" onclick="window.history.replaceState(null, null, window.location.pathname);" href="">Home</a>
      <a class="mdl-navigation__link" onClick="resetDiv();" href="?t=profile">My Profile</a>
      <a class="mdl-navigation__link" onClick="resetDiv();" href="?t=results">My Results</a>
      <a class="mdl-navigation__link" href="?logout=true">Logout</a>
    </nav>
  </div>
  
  </div>
 
          
</body>
</html>