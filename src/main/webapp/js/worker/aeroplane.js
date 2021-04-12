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
                        
                     </div>  
                <div class="container">
                    <p>Capacity&nbsp; : ${user.capacity}</p>
                    <p>Parked&nbsp;&nbsp;&nbsp;&nbsp; : ${user.parkedAeroplanes.length}</p>
                    <p>Available : ${user.capacity - user.parkedAeroplanes.length}</p>
                    <p>Basic Pay : ${user.basicCharge} Rupees</p>
                    <p>Duration&nbsp; : ${user.basicDuration} Hours </p>
                    <p>Extra Pay : ${user.extraCharge} Rupees</p>    
                </div><div id="component">`;
                if(user.parkedAeroplanes.length<user.capacity){
	               html+=`<a href="addAeroplane.html?id=${user.id}&apron=${user.name}" class="button" style="width:100%">Check In</a>`;
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
	fetch(`http://localhost:8080/parking/api/revenue/3`,{method : 'PUT', headers: {'Content-Type': 'application/json'},body: JSON.stringify(revenue)});
		
}