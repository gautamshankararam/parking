function render(){
	
	
    if ( window.history.replaceState ) {
        window.history.replaceState( null, null, window.location.href );
    }


    showSpinner()
	
	fetch("http://localhost:8080/parking/api/docks/")     
    .then(response => response.json()) 
    .then(data => { 
        hideSpinner();
        // Create a variable to store HTML 
        let html = ``; 
        data.forEach(user => { 
	        
            html += `<div class="card">
                     <div class="head">
                        <h3 class="center">${user.name}</h3>
                        <i class="fa fa-edit" style="font-size:30px;color:darkblue;" onclick="window.location='editDock.html?id=${user.id}'"></i>
                     </div>  
                <div class="container">
                    <p>Capacity&nbsp; : ${user.capacity}</p>
                    <p>Parked&nbsp;&nbsp;&nbsp;&nbsp; : ${user.parkedShips.length}</p>
                    <p>Available : ${user.capacity - user.parkedShips.length}</p>
                    <p>Basic Pay : ${user.basicCharge} Rupees</p>
                    <p>Duration&nbsp; : ${user.basicDuration} Hours </p>
                    <p>Extra Pay : ${user.extraCharge} Rupees</p>
                    <p>Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : ${user.type} </p>
                    <p>Rent&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : ${user.rent} Rupees</p>    
                </div><div id="component">`;
                if(user.parkedShips.length<user.capacity){
	               html+=`<a href="addShip.html?id=${user.id}&dock=${user.name}" class="button">Check In</a>`;
                }else{
	               html+=`<a href="javascript:void(0)" class="button">Check In</a>`;
                }
                 
             html += `<a href="viewShip.html?id=${user.id}" class="button">Ships</a></div>
                <div>
                    <button onClick="deleteDock(${user.id})" class="delete">Delete</button>
                </div>
            </div>`;
        }); 
        document.getElementById("main").innerHTML = html; 
    });
	
}

function addDock(){
	var dock = new Object();
     
    dock.name = document.getElementById('dockname').value;
	dock.capacity = document.getElementById('dockcapacity').value;
	dock.basicCharge = document.getElementById('dockbasiccharge').value;
	dock.basicDuration = document.getElementById('dockbasicduration').value;
	dock.extraCharge = document.getElementById('dockextracharge').value;
	dock.type = document.getElementById('docktype').value;
	dock.rent = document.getElementById('dockrent').value;
	
	if(dock.name.length>0 && dock.capacity>0 && dock.basicCharge>0 && dock.basicDuration>0 && dock.extraCharge>0 && dock.type.length>0 && dock.rent>=0 ){
    	fetch(`http://localhost:8080/parking/api/docks/`,{method : 'POST', headers: {'Content-Type': 'application/json'},body: JSON.stringify(dock)});
	}
}

function showShip(){
	showSpinner()
	const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    fetch(`http://localhost:8080/parking/api/docks/${id}/ships`)     
    .then(response => response.json()) 
    .then(data => { 
	    hideSpinner();
        // Create a variable to store HTML 
        let html = ``; 
        data.forEach(ship => { 
	        
            html += `<div class="card">
                
                <h3 class="center">${ship.name}</h3>
                <div class="container">
                    <p>Area : ${ship.dockName}</p>
                    <p>Time : ${ship.parkingDuration} Hours</p>
                    <p>Cost : ${ship.parkingCost} Rupees</p>    
                </div>
                <div>
                    <button onclick="deleteShip(${ship.id},${ship.dockId})" class="delete">Delete</button>
                </div>
            </div>`;
        }); 
        document.getElementById("main").innerHTML = html; 
    });

}

function setDefault(){
	const urlParams = new URLSearchParams(window.location.search);
    const dock = urlParams.get('dock');
    document.getElementById('dockname').value=dock;
    document.getElementById('dockname').readOnly=true;
}

function calculateCost(){
	const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    fetch(`http://localhost:8080/parking/api/docks/${id}`)     
    .then(response => response.json())
    .then(data => {
	    let duration = document.getElementById('shipparkingduration').value;
        let cost = data.basicCharge;
        if(duration>data.basicDuration){ 
	         cost+= ((duration-data.basicDuration)*data.extraCharge); 
        }
	    document.getElementById('shipparkingcost').value = cost;
	
    }); 


}

function addShip(){
	const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
	
	var ship = new Object();
   
	ship.name = document.getElementById('shipname').value;
    ship.dockId = id;
    ship.dockName = document.getElementById('dockname').value;
    ship.parkingDuration = document.getElementById('shipparkingduration').value;
    ship.parkingCost = document.getElementById('shipparkingcost').value;

	if(ship.name.length>0 && ship.dockId>0 && ship.dockName.length>0 && ship.parkingDuration>0 && ship.parkingCost>0){   	
        fetch(`http://localhost:8080/parking/api/docks/${ship.dockId}/ships`,{method : 'POST', headers: {'Content-Type': 'application/json'},body: JSON.stringify(ship)});
        generateRevenue(ship.parkingCost,ship.parkingDuration);
   }
}

function deleteShip(shipId,dockId){
	var result = confirm("Are you sure?");
    if (result) {
        fetch(`http://localhost:8080/parking/api/docks/${dockId}/ships/${shipId}`,{method : 'DELETE', headers: {'Content-Type': 'application/json'},body: null})
     	.then(()=>{
	       showShip();
	    });
    }
}

function deleteDock(dockId){
	var result = confirm("Deleting the dock will also remove the ships... are you sure?");
	if(result){
		fetch(`http://localhost:8080/parking/api/docks/${dockId}`,{method : 'DELETE', headers: {'Content-Type': 'application/json'},body: null})
	    .then(()=>{
	       render();  
	    });
		
	}
}


function showSpinner() {
  const spinner = document.getElementById("spinner");
  spinner.className = "show";
}

function hideSpinner() {
   const spinner = document.getElementById("spinner");
   spinner.className = spinner.className.replace("show", "");
}

function setEdit(){
	const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    
    fetch(`http://localhost:8080/parking/api/docks/${id}`)     
    .then(response => response.json()) 
    .then(dock => {
	    document.getElementById("dockname").value=dock.name;
        document.getElementById("dockcapacity").value=dock.capacity;
	    document.getElementById("dockbasiccharge").value=dock.basicCharge;
        document.getElementById("dockbasicduration").value=dock.basicDuration;
        document.getElementById("dockextracharge").value=dock.extraCharge;
        document.getElementById('docktype').value=dock.type;
        document.getElementById("dockrent").value=dock.rent;
        if(dock.parkedShips.length>0){
	       document.getElementById("dockcapacity").min=dock.parkedShips.length;
        }
        

    });


}

function updateDock(){
	const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    var dock = new Object();
     
    dock.name = document.getElementById('dockname').value;
	dock.capacity = document.getElementById('dockcapacity').value;
	dock.basicCharge = document.getElementById('dockbasiccharge').value;
	dock.basicDuration = document.getElementById('dockbasicduration').value;
	dock.extraCharge = document.getElementById('dockextracharge').value;
    dock.type = document.getElementById('docktype').value;
	dock.rent = document.getElementById('dockrent').value;
    if(dock.name.length>0 && dock.capacity>0 && dock.basicCharge>0 && dock.basicDuration>0 && dock.extraCharge>0 && dock.type.length>0 && dock.rent>=0){
	    fetch(`http://localhost:8080/parking/api/docks/${id}`,{method : 'PUT', headers: {'Content-Type': 'application/json'},body: JSON.stringify(dock)});
	}
}

function generateRevenue(amt,duration){
	
	var revenue = new Object();
	revenue.amount = amt;
	revenue.duration=duration;
	fetch(`http://localhost:8080/parking/api/revenue/2`,{method : 'PUT', headers: {'Content-Type': 'application/json'},body: JSON.stringify(revenue)});
		
}
