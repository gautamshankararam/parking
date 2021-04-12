
const urlParams = new URLSearchParams(window.location.search);
const id = urlParams.get('email');
const pass=urlParams.get('password')

if(id && pass){
	fetch(`http://localhost:8080/parking/api/users/${id}`)     
    .then(response => response.json()) 
    .then(data => {
	
	   if(id == data.email && pass== data.password && data.role=="admin"){
		     sessionStorage.setItem('admin', JSON.stringify(data));
             window.location.href="admin/home.html";
      	}else if(id == data.email && pass== data.password && data.role=="user"){
	         sessionStorage.setItem('user', JSON.stringify(data));
             window.location.href="user/home.html";
        }else if(id == data.email && pass== data.password && data.role=="worker" && data.status=="active" ){
             sessionStorage.setItem('worker', JSON.stringify(data));
             window.location.href="worker/home.html";
        
        }else{
            window.location.href="index.html";
        }
        
	
	
});
	
}

if(sessionStorage.getItem('admin') != null){
    window.location.href="admin/home.html";
}

if(sessionStorage.getItem('user') != null){
    window.location.href="user/home.html";
}

if(sessionStorage.getItem('worker') != null){
    window.location.href="worker/home.html";
}