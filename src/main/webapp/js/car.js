
function render(){
	
	
    if ( window.history.replaceState ) {
        window.history.replaceState( null, null, window.location.href );
    }


    showSpinner()
	
	fetch("http://localhost:8080/parking/api/areas/")     
    .then(response => response.json()) 
    .then(data => { 
        hideSpinner();
        // Create a variable to store HTML 
        let html = ``; 
        data.forEach(user => { 
	        
            html += `<div class="card">
                     <div class="head">
                        <h3 class="center">${user.name}</h3>
                        <i class="fa fa-edit" style="font-size:30px;color:darkblue;" onclick="window.location='editArea.html?id=${user.id}'"></i>
                     </div>  
                <div class="container">
                    <p>Capacity&nbsp; : ${user.capacity}</p>
                    <p>Parked&nbsp;&nbsp;&nbsp;&nbsp; : ${user.parkedCars.length}</p>
                    <p>Available : ${user.capacity - user.parkedCars.length}</p>
                    <p>Basic Pay : ${user.basicCharge} Rupees</p>
                    <p>Duration&nbsp; : ${user.basicDuration} Hours </p>
                    <p>Extra Pay : ${user.extraCharge} Rupees</p>
                    <p>Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : ${user.type} </p>
                    <p>Rent&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : ${user.rent} Rupees</p>    
                </div>
                <div id="component">`;
                if(user.parkedCars.length<user.capacity){
	               html+=`<a href="addCar.html?id=${user.id}&area=${user.name}" class="button">Check In</a>`;
                }else{
	               html+=`<a href="javascript:void(0)" class="button">Check In</a>`;
                }
                 
             html += `<a href="viewCar.html?id=${user.id}" class="button">Cars</a></div>
                <div>
                    <button onClick="deleteArea(${user.id})" class="delete">Delete</button>
                </div>
            </div>`;
        }); 
        document.getElementById("main").innerHTML = html; 
    });
	
}

function addArea(){
	var area = new Object();
     
    area.name = document.getElementById('areaname').value;
	area.capacity = document.getElementById('areacapacity').value;
	area.basicCharge = document.getElementById('areabasiccharge').value;
	area.basicDuration = document.getElementById('areabasicduration').value;
	area.extraCharge = document.getElementById('areaextracharge').value;
	area.type = document.getElementById('areatype').value;
	area.rent = document.getElementById('arearent').value;
	if(area.name.length>0 && area.capacity>0 && area.basicCharge>0 && area.basicDuration>0 && area.extraCharge>0 && area.type.length>0 && area.rent>=0){
    	fetch(`http://localhost:8080/parking/api/areas/`,{method : 'POST', headers: {'Content-Type': 'application/json'},body: JSON.stringify(area)});
	}
}

function showCar(){
	showSpinner()
	const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    fetch(`http://localhost:8080/parking/api/areas/${id}/cars`)     
    .then(response => response.json()) 
    .then(data => { 
	    hideSpinner();
        // Create a variable to store HTML 
        let html = ``; 
        data.forEach(car => { 
	        
            html += `<div class="card">
                
                <h3 class="center">${car.name}</h3>
                <div class="container">
                    <p>Area : ${car.areaName}</p>
                    <p>Time : ${car.parkingDuration} Hours</p>
                    <p>Cost : ${car.parkingCost} Rupees</p>    
                </div>
                <div>
                    <button onclick="deleteCar(${car.id},${car.areaId})" class="delete">Delete</button>
                </div>
            </div>`;
        }); 
        document.getElementById("main").innerHTML = html; 
    });

}

function setDefault(){
	const urlParams = new URLSearchParams(window.location.search);
    const area = urlParams.get('area');
    document.getElementById('areaname').value=area;
    document.getElementById('areaname').readOnly=true;
}

function calculateCost(){
	const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    fetch(`http://localhost:8080/parking/api/areas/${id}`)     
    .then(response => response.json())
    .then(data => {
	    let duration = document.getElementById('carparkingduration').value;
        let cost = data.basicCharge;
        if(duration>data.basicDuration){ 
	         cost+= ((duration-data.basicDuration)*data.extraCharge); 
        }
	    document.getElementById('carparkingcost').value = cost;
	
    }); 


}

function addCar(){
	const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
	
	var car = new Object();
   
	car.name = document.getElementById('carname').value;
    car.areaId = id;
    car.areaName = document.getElementById('areaname').value;
    car.parkingDuration = document.getElementById('carparkingduration').value;
    car.parkingCost = document.getElementById('carparkingcost').value;

	if(car.name.length>0 && car.areaId>0 && car.areaName.length>0 && car.parkingDuration>0 && car.parkingCost>0){   	
        fetch(`http://localhost:8080/parking/api/areas/${car.areaId}/cars`,{method : 'POST', headers: {'Content-Type': 'application/json'},body: JSON.stringify(car)}); 
	    generateRevenue(car.parkingCost,car.parkingDuration);  
        
   }
}

function deleteCar(carId,areaId){
	var result = confirm("Are you sure?");
    if (result) {
        fetch(`http://localhost:8080/parking/api/areas/${areaId}/cars/${carId}`,{method : 'DELETE', headers: {'Content-Type': 'application/json'},body: null})
     	.then(()=>{
	       showCar();
	    });
    }
}

function deleteArea(areaId){
	var result = confirm("Deleting the area will also remove the cars... are you sure?");
	if(result){
		fetch(`http://localhost:8080/parking/api/areas/${areaId}`,{method : 'DELETE', headers: {'Content-Type': 'application/json'},body: null})
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
    
    fetch(`http://localhost:8080/parking/api/areas/${id}`)     
    .then(response => response.json()) 
    .then(area => {
	    document.getElementById("areaname").value=area.name;
        document.getElementById("areacapacity").value=area.capacity;
	    document.getElementById("areabasiccharge").value=area.basicCharge;
        document.getElementById("areabasicduration").value=area.basicDuration;
        document.getElementById("areaextracharge").value=area.extraCharge;
        document.getElementById('areatype').value=area.type;
        document.getElementById("arearent").value=area.rent;
        if(area.parkedCars.length>0){
	       document.getElementById("areacapacity").min=area.parkedCars.length;
        }
        
        

    });


}

function updateArea(){
	const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    var area = new Object();
     
    area.name = document.getElementById('areaname').value;
	area.capacity = document.getElementById('areacapacity').value;
	area.basicCharge = document.getElementById('areabasiccharge').value;
	area.basicDuration = document.getElementById('areabasicduration').value;
	area.extraCharge = document.getElementById('areaextracharge').value;
    area.type = document.getElementById('areatype').value;
	area.rent = document.getElementById('arearent').value;
    if(area.name.length>0 && area.capacity>0 && area.basicCharge>0 && area.basicDuration>0 && area.extraCharge>0 && area.type.length>0 && area.rent>=0) {
	    fetch(`http://localhost:8080/parking/api/areas/${id}`,{method : 'PUT', headers: {'Content-Type': 'application/json'},body: JSON.stringify(area)});
	}
}

function generateRevenue(amt,duration){
	
	var revenue = new Object();
	revenue.amount = amt;
	revenue.duration=duration;
	fetch(`http://localhost:8080/parking/api/revenue/1`,{method : 'PUT', headers: {'Content-Type': 'application/json'},body: JSON.stringify(revenue)});
		
}

