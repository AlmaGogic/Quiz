var selected=false;
var pts=0;
var crct=0;
function resetDiv(){

	window.history.replaceState(null, null, window.location.pathname);
	document.getElementById("clear").innerHTML = "";
	
}

function loadQuiz(quiz){
	location.href="?quiz="+quiz;

}


function correct(quiz,question,answer,correct, points){
	var answerDiv=document.getElementById(answer);
	var element = document.getElementById("parent");
	var numberOfChildren = element.children.length;
	console.log(numberOfChildren);
	var pointsDiv=document.getElementById("points");
	var correctDiv=document.getElementById("correct");
	if(selected==false){
		if(correct==="true"){
			selected=true;
			answerDiv.style.backgroundColor = "#00AA00";
			crct++;
			var p=parseInt(pts)+parseInt(points);
			pts=p;
			var http = new XMLHttpRequest();
			var url = "http://localhost:8080/RWAProjekat-Quiz/user/home";
			var params = "questionAjax="+question;
			http.open("POST", url, true);

			//Send the proper header information along with the request
			http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

			http.send(params);
			
		}
		else{
			answerDiv.style.backgroundColor = "#AA0000";
			selected=true;
		}
	
		var quest=document.getElementById(question);
		quest.classList.toggle("visibility");
		quest.classList.add("slct");
	
		setTimeout(function(){quest.style.display="none";selected=false; element.removeChild(quest)}, 3000);
	}
	if(numberOfChildren==1){
		var http = new XMLHttpRequest();
		var url = "http://localhost:8080/RWAProjekat-Quiz/user/home";
		var params = "points="+pts+"&quiz="+quiz+"&questionAjax="+question;
		http.open("POST", url, true);

		//Send the proper header information along with the request
		http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");

		http.onreadystatechange = function() {//Call a function when the state changes.
		    if(http.readyState == 4 && http.status == 200) {
		        //alert("Score saved!");
		    }
		}
		http.send(params);
		
	}
	correctDiv.innerHTML="";
	correctDiv.innerHTML=crct;
	pointsDiv.innerHTML="";
	pointsDiv.innerHTML=pts;
}

window.onload = function() {
	}	


