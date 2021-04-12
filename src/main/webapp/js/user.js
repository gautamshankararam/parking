function render(){
	fetch("http://localhost:8080/parking/api/users/")
.then(response => response.json())
.then(data =>{
	
	let html=`<tr>
            <th>Mail</th>
            <th>Password</th>
            <th>Role</th>
            <th>Status</th>
            <th>Change password</th>
          </tr>`;
     data.forEach(user =>{
            if(user.role != "admin" && user.status =="active"){
	           
              html+=`<tr>
                       <td>${user.email}</td>
                       <td>${user.password}</td>
                       <td>${user.role}</td>
                       <td>${user.status}</td>
                       <td><button class="button" onclick="changePassword(${user.id})">Change</button></td>
                      
               </tr>`;
    
            }	     
	
     });
     
     document.getElementById("user").innerHTML = html;
	
});
	
}

function changePassword(id){
	var user = new Object();
	user.id=id;
	user.password=Math.floor(1000 + Math.random() * 9000);;
	fetch(`http://localhost:8080/parking/api/users/${id}`,{method : 'PUT', headers: {'Content-Type': 'application/json'},body: JSON.stringify(user)})
	.then( () => {
		render();
	});
		
    

}