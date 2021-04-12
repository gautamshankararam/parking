function render(){
	
	
    if ( window.history.replaceState ) {
        window.history.replaceState( null, null, window.location.href );
    }


    showSpinner()
	
	fetch("http://localhost:8080/parking/api/air/")     
    .then(response => response.json()) 
    .then(data => { 
        hideSpinner();
        // Create a variable to store HTML 
        let html = ``; 
        data.forEach(user => { 
	        
            html += `<div class="card">
                     <div class="head">
                        <h3 class="center">${user.name}</h3>
                        <i class="fa fa-edit" style="font-size:30px;color:darkblue;" onclick="window.location='editApron.html?id=${user.id}'"></i>
                     </div>  
                <div class="container">
                    <p>Capacity&nbsp; : ${user.capacity}</p>
                    <p>Parked&nbsp;&nbsp;&nbsp;&nbsp; : ${user.parkedAeroplanes.length}</p>
                    <p>Available : ${user.capacity - user.parkedAeroplanes.length}</p>
                    <p>Basic Pay : ${user.basicCharge} Rupees</p>
                    <p>Duration&nbsp; : ${user.basicDuration} Hours </p>
                    <p>Extra Pay : ${user.extraCharge} Rupees</p>
                    <p>Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : ${user.type} </p>
                    <p>Rent&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : ${user.rent} Rupees</p>    
                </div><div id="component">`;
                if(user.parkedAeroplanes.length<user.capacity){
	               html+=`<a href="addAeroplane.html?id=${user.id}&apron=${user.name}" class="button">Check In</a>`;
                }else{
	               html+=`<a href="javascript:void(0)" class="button">Check In</a>`;
                }
                 
             html += `<a href="viewAeroplane.html?id=${user.id}" class="button">Aeroplanes</a></div>
                <div>
                    <button onClick="deleteApron(${user.id})" class="delete">Delete</button>
                </div>
            </div>`;
        }); 
        document.getElementById("main").innerHTML = html; 
    });
	
}

function addApron(){
	var apron = new Object();
     
    apron.name = document.getElementById('apronname').value;
	apron.capacity = document.getElementById('aproncapacity').value;
	apron.basicCharge = document.getElementById('apronbasiccharge').value;
	apron.basicDuration = document.getElementById('apronbasicduration').value;
	apron.extraCharge = document.getElementById('apronextracharge').value;
	apron.type = document.getElementById('aprontype').value;
	apron.rent = document.getElementById('apronrent').value;
	
	if(apron.name.length>0 && apron.capacity>0 && apron.basicCharge>0 && apron.basicDuration>0 && apron.extraCharge>0 && apron.type.length>0 && apron.rent>=0){
    	fetch(`http://localhost:8080/parking/api/air/`,{method : 'POST', headers: {'Content-Type': 'application/json'},body: JSON.stringify(apron)});
	}
}

function showAeroplane(){
	showSpinner()
	const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    fetch(`http://localhost:8080/parking/api/air/${id}/aeroplanes`)     
    .then(response => response.json()) 
    .then(data => { 
	    hideSpinner();
        // Create a variable to store HTML 
        let html = ``; 
        data.forEach(aeroplane => { 
	        
            html += `<div class="card">
                
                <h3 class="center">${aeroplane.name}</h3>
                <div class="container">
                    <p>Area : ${aeroplane.apronName}</p>
                    <p>Time : ${aeroplane.parkingDuration} Hours</p>
                    <p>Cost : ${aeroplane.parkingCost} Rupees</p>    
                </div>
                <div>
                    <button onclick="deleteAeroplane(${aeroplane.id},${aeroplane.apronId})" class="delete">Delete</button>
                </div>
            </div>`;
        }); 
        document.getElementById("main").innerHTML = html; 
    });

}

function setDefault(){
	const urlParams = new URLSearchParams(window.location.search);
    const apron = urlParams.get('apron');
    document.getElementById('apronname').value=apron;
    document.getElementById('apronname').readOnly=true;
}

function calculateCost(){
	const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    fetch(`http://localhost:8080/parking/api/air/${id}`)     
    .then(response => response.json())
    .then(data => {
	    let duration = document.getElementById('aeroplaneparkingduration').value;
        let cost = data.basicCharge;
        if(duration>data.basicDuration){ 
	         cost+= ((duration-data.basicDuration)*data.extraCharge); 
        }
	    document.getElementById('aeroplaneparkingcost').value = cost;
	
    }); 


}

function addAeroplane(){
	const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
	
	var aeroplane = new Object();
   
	aeroplane.name = document.getElementById('aeroplanename').value;
    aeroplane.apronId = id;
    aeroplane.apronName = document.getElementById('apronname').value;
    aeroplane.parkingDuration = document.getElementById('aeroplaneparkingduration').value;
    aeroplane.parkingCost = document.getElementById('aeroplaneparkingcost').value;

	if(aeroplane.name.length>0 && aeroplane.apronId>0 && aeroplane.apronName.length>0 && aeroplane.parkingDuration>0 && aeroplane.parkingCost>0){   	
        fetch(`http://localhost:8080/parking/api/air/${aeroplane.apronId}/aeroplanes`,{method : 'POST', headers: {'Content-Type': 'application/json'},body: JSON.stringify(aeroplane)});
        generateRevenue(aeroplane.parkingCost,aeroplane.parkingDuration);
   }
}

function deleteAeroplane(aeroplaneId,apronId){
	var result = confirm("Are you sure?");
    if (result) {
        fetch(`http://localhost:8080/parking/api/air/${apronId}/aeroplanes/${aeroplaneId}`,{method : 'DELETE', headers: {'Content-Type': 'application/json'},body: null})
     	.then(()=>{
	       showAeroplane();
	    });
    }
}

function deleteApron(apronId){
	var result = confirm("Deleting the apron will also remove the aeroplanes... are you sure?");
	if(result){
		fetch(`http://localhost:8080/parking/api/air/${apronId}`,{method : 'DELETE', headers: {'Content-Type': 'application/json'},body: null})
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
    
    fetch(`http://localhost:8080/parking/api/air/${id}`)     
    .then(response => response.json()) 
    .then(apron => {
	    document.getElementById("apronname").value=apron.name;
        document.getElementById("aproncapacity").value=apron.capacity;
	    document.getElementById("apronbasiccharge").value=apron.basicCharge;
        document.getElementById("apronbasicduration").value=apron.basicDuration;
        document.getElementById("apronextracharge").value=apron.extraCharge;
        document.getElementById('aprontype').value=apron.type;
        document.getElementById("apronrent").value=apron.rent;
   
        if(apron.parkedAeroplanes.length>0){
	       document.getElementById("aproncapacity").min=apron.parkedAeroplanes.length;
        }
        

    });


}

function updateApron(){
	const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');

    var apron = new Object();
     
    apron.name = document.getElementById('apronname').value;
	apron.capacity = document.getElementById('aproncapacity').value;
	apron.basicCharge = document.getElementById('apronbasiccharge').value;
	apron.basicDuration = document.getElementById('apronbasicduration').value;
	apron.extraCharge = document.getElementById('apronextracharge').value;
    apron.type = document.getElementById('aprontype').value;
	apron.rent = document.getElementById('apronrent').value;
    
    if(apron.name.length>0 && apron.capacity>0 && apron.basicCharge>0 && apron.basicDuration>0 && apron.extraCharge>0 && apron.type.length>0 && apron.rent >0){
	    fetch(`http://localhost:8080/parking/api/air/${id}`,{method : 'PUT', headers: {'Content-Type': 'application/json'},body: JSON.stringify(apron)});
	}
}

function generateRevenue(amt,duration){
	
	var revenue = new Object();
	revenue.amount = amt;
	revenue.duration=duration;
	fetch(`http://localhost:8080/parking/api/revenue/3`,{method : 'PUT', headers: {'Content-Type': 'application/json'},body: JSON.stringify(revenue)});
		
}