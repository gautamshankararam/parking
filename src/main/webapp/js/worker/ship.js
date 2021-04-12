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
                        
                     </div>  
                <div class="container">
                    <p>Capacity&nbsp; : ${user.capacity}</p>
                    <p>Parked&nbsp;&nbsp;&nbsp;&nbsp; : ${user.parkedShips.length}</p>
                    <p>Available : ${user.capacity - user.parkedShips.length}</p>
                    <p>Basic Pay : ${user.basicCharge} Rupees</p>
                    <p>Duration&nbsp; : ${user.basicDuration} Hours </p>
                    <p>Extra Pay : ${user.extraCharge} Rupees</p>    
                </div><div id="component">`;
                if(user.parkedShips.length<user.capacity){
	               html+=`<a href="addShip.html?id=${user.id}&dock=${user.name}" class="button" style="width:100%">Check In</a>`;
                }else{
	               html+=`<a href="javascript:void(0)" class="button" style="width:100%">Check In</a>`;
                }
                 
             html += `</div>
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





function showSpinner() {
  const spinner = document.getElementById("spinner");
  spinner.className = "show";
}

function hideSpinner() {
   const spinner = document.getElementById("spinner");
   spinner.className = spinner.className.replace("show", "");
}



function generateRevenue(amt,duration){
	
	var revenue = new Object();
	revenue.amount = amt;
	revenue.duration=duration;
	fetch(`http://localhost:8080/parking/api/revenue/2`,{method : 'PUT', headers: {'Content-Type': 'application/json'},body: JSON.stringify(revenue)});
		
}
