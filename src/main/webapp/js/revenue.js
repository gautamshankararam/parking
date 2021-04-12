fetch("http://localhost:8080/parking/api/revenue/")
.then(response => response.json())
.then(data =>{
	
	let total=0;
	let vehicles=0;
	let duration=0;
	let rent=0;
	let amount=0;
	let html=`<tr>
            <th>Slot Type</th>
            <th>Rent</th>
            <th>Amount Generated</th>
            <th>Vehicles Parked</th>
            <th>Total Duration</th>
            <th>Average Cost Per Vehicle</th>
            <th>Profit / Loss </th>
            <th>Profit / Loss Amount</th>
          </tr>`;
     data.forEach(revenue =>{
	     if(revenue.amount>revenue.rent){
	          rev="profit";
              
            }else{
	          rev="loss";
              
            }
            amount = revenue.amount - revenue.rent;
           html+=`<tr>
            <td>${revenue.slot}</td>
            <td>${revenue.rent}</td>
            <td>${revenue.amount}</td>
            <td>${revenue.vehicles}</td>
            <td>${revenue.duration}</td>
            <td>${Math.round(revenue.amount/revenue.vehicles)}</td>
            <td>${rev}</td>
            <td>${amount}</td>
          </tr>`;
          
          total+=revenue.amount; 
          vehicles+=revenue.vehicles;
          duration+=revenue.duration;
          rent+=revenue.rent;
	
     });
    if(total>rent){
	    rev="profit";
    }else{
        rev="loss";	
    }
    amount = total - rent;      
     html+=`<tr>
            <td class="center">Total</td>
            <td class="center">${rent}</td>
            <td class="center">${total}</td>
            <td class="center">${vehicles}</td>
            <td class="center">${duration}</td>
            <td class="center">${Math.round(total/vehicles)}</td>
            <td class="center">${rev}</td>
            <td class="center">${amount}</td>

          </tr>`;
     
     document.getElementById("revenue").innerHTML = html;
	
});




