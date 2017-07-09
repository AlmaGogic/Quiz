function resetDiv(){

	window.history.replaceState(null, null, window.location.pathname);
	document.getElementById("clear").innerHTML = "";
	
}
