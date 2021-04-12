function render(){
	fetch("http://localhost:8080/parking/api/users/")
.then(response => response.json())
.then(data =>{
	
	let html=`<tr>
            <th>Mail</th>
            <th>Role</th>
            <th>Status</th>
            <th>Allow Access</th>
          </tr>`;
     data.forEach(user =>{
            if(user.role == "worker" && user.status =="inactive"){
	           
              html+=`<tr>
                       <td>${user.email}</td>
                       <td>${user.role}</td>
                       <td>${user.status}</td>
                       <td><button class="button" onclick="changeStatus(${user.id},${user.password})">Allow</button></td>
                      
               </tr>`;
    
            }	     
	
     });
     
     document.getElementById("worker").innerHTML = html;
	
});
	
}


function changeStatus(id,pass){
	var user = new Object();
	user.id=id;
	user.password=pass;
	fetch(`http://localhost:8080/parking/api/users/${id}`,{method : 'PUT', headers: {'Content-Type': 'application/json'},body: JSON.stringify(user)})
	.then( () => {
		render();
	});
		
    

}