<%@ page import="java.util.Collection" %>
<%@ page import="quizClasses.Quiz" %>

<%@ page import="quizClasses.Answer" %>
<%@ page import="quizClasses.Question" %>
<%@ page import="quizClasses.Result" %>

<html>
<head>
      <title>Quiz User Home</title>
      <meta charset="utf-8">
      
	  <meta name="viewport" content="width=device-width, initial-scale=1">
      <script src="https://storage.googleapis.com/code.getmdl.io/1.0.6/material.min.js"></script>
      <link rel="stylesheet" type="text/css" href="../userHomeStyle.css">
      <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
      <script src="../userHomeJS.js"></script>
</head>

<body>



<div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
  <header class="mdl-layout__header">
    <div class="mdl-layout__header-row">
      <span class="mdl-layout-title">Welcome <%= session.getAttribute("username") %></span>
      <div class="mdl-layout-spacer"></div>
      
            
            <!-- start search form -->
            <div class="search">
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable">
                <label class="mdl-button mdl-js-button mdl-button--icon" id="searchClick" for="search-expandable">
                    <i class="material-icons">search</i>
                </label>
                <form action="" method="GET">
                <div class="mdl-textfield__expandable-holder">
                    <input class="mdl-textfield__input" type="text" name="search"  placeholder="Search for a quiz: " id="search-expandable" />
                    <label class="mdl-textfield__label" for="search-expandable">Search text</label>
                </div>
                </form>
            </div>
            </div>
            <!-- end search form -->
            
    </div>
  </header>
         <main>
         <div id="clear">
  <div class="mdl-grid">
  
 <% 
 String task=(String)request.getAttribute("task");
 String currentQuiz=(String)request.getAttribute("quiz");
 if(currentQuiz!=null){

	 %>
	 
	 
<div class="container">
    <div class="well" id="quiz">
      <h1><%=currentQuiz %> Trivia</h1>
      
    <div class="wrapper"><div class="cor"><div class="text">Correcty answered on:</div><div class="correct"id="correct">0</div> <div class=text>questions</div></div>
    <div class="pts"><div class="text">Total points:</div> <div id="points">0</div></div></div>
      
      <div id="parent">
        <%
        Collection<Question> unanswered=(Collection<Question>)request.getAttribute("unanswered");
        int i=0;
        for(Question qst : unanswered){
        	
        	%><div class="questions" id="<%=qst.getQuestionText() %>">
        	<%= qst.getQuestionText()%>  
	        
	      <% 
	      Collection<Answer>answers=qst.getAnswers();%>
	      <div class="answers">
	      <%
	      for(Answer a : answers){
	    	  
	    	  %><div class="answer" id='<%=a.getAnswer()+i %>' onclick="correct('<%=request.getAttribute("quiz") %>','<%=qst.getQuestionText() %>','<%=a.getAnswer()+i %>','<%=a.getCorrectStatus() %>','<%=qst.getQuestionPoints() %>')">
	    	  <%i++;%><%=a.getAnswer() %>
	    </div><%
	      }
	      %></div></div><%
        }
        %>

   </div>
   </div>
    </div>








	 
	 <%
 }
 if(task!=null){
	if(task.equals("search")){
 		Collection<Quiz> quizzes= (Collection<Quiz>)request.getAttribute("quizzes");
 		Collection<Result> results= (Collection<Result>)request.getAttribute("results");
 		
 		int i=0;
 		boolean show=true;
 		String name="";
     	for(Quiz quiz: quizzes){
     		for(Result r :results){
     			if(r.getQuiz().getQuizName().equals(quiz.getQuizName())){
     				show=false;
     				name=r.getQuiz().getQuizName();
     				break;
     			}
     		}
     		
 		%>
    	<div class="mdl-cell mdl-cell--4-col" onclick="loadQuiz('<%=quiz.getQuizName()%>')">
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
            
            <%
            
            Collection<Result> listresults= (Collection<Result>)request.getAttribute("results");
 		
     	for(Result result: listresults){
 		%>
 		            <tr>
			<td><%= result.getFirstName() %></td>
            <td><%= result.getLastName() %></td>
            <td><%= result.getQuiz().getQuizName() %></td>
            <td><%= result.getTotalPoints() %></td>
                  </tr>      
      
 		<%
     	}%>
            	
                

            
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
