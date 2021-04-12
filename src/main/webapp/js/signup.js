function validate(){
	
	var user = new Object()
    user.email = document.getElementById("email").value;
    user.role = "user";
    user.status = "active";
	if(matchPass()){
	    user.password=document.getElementById("password1").value;	
	}
	if(user.email.length >0 && user.password.length>0){
		fetch(`http://localhost:8080/parking/api/users/`,{method : 'POST', headers: {'Content-Type': 'application/json'},body: JSON.stringify(user)});
	}
	 
}

function matchPass(){
	pass1 = document.getElementById("password1").value;
	pass2 = document.getElementById("password2").value;
	
	if(pass1 === pass2){
		document.getElementById("verify").innerHTML = "Matching";
		document.getElementById("verify").style.color = "green";
		return true;
	}
	else{
		document.getElementById("verify").innerHTML = "Not Matching";
		document.getElementById("verify").style.color = "red";
		return false;
	}
	
	
}

if(sessionStorage.getItem('user') != null){
    window.location.href="user/home.html";
}
if(sessionStorage.getItem('worker') != null){
    window.location.href="worker/home.html";
}
if(sessionStorage.getItem('admin') != null){
    window.location.href="admin/home.html";
}