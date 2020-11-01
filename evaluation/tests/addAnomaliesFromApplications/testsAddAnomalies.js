const http = require('http');
console.log("Performance of storing critical information with single and different clients");

var num_app = 1; //change

let start_times = [num_app];
let start_times_bc = [num_app];
let end_times_bc = [num_app];
let end_times = [num_app];

let privateKeys = ["",""];

//Applications requesting temperature value from the same client
/*
for (let i = 0; i < num_app; i++) {
  start_times[i] = new Date().getTime()+i;
  end_times[i] = 0;
  getValue(i,'clientEndpoint1')
}
*/

//Applications requesting temperature value from different clients
/*
for (let i = 0; i < num_app; i++) {
  start_times[i] = new Date().getTime()+i;
  end_times[i] = 0;
  var numClient = i+1;
  getValue(i,'clientEndpoint'+numClient);
}
*/

function getValue(app,endpoint) {
    http.get('http://localhost:8081/api/server/test/'+endpoint, (resp) => {
    let data = '';

    // A chunk of data has been recieved.
    resp.on('data', (chunk) => {
        data += chunk;
    });

    // The whole response has been received. Print out the result.
    resp.on('end', () => {
        console.log(JSON.parse(data).value);
        valueTemp = JSON.parse(data).value;
        addAnomaly(app, endpoint, valueTemp);
    });

  }).on("error", (err) => {
    console.log("Error: " + err.message);
  });
}

function addAnomaly(app, endpoint, temperature) {
  const anomaly = JSON.stringify({
    timestamp: new Date().getTime(),
    endpoint: endpoint,
    emergencyLevel: analyzeTemperature(temperature),
    temperature: temperature,
    privateKey: privateKeys[app]
  })
  
  var content_length = anomaly.length;

  const options1 = {
    hostname: 'localhost',
    port: 8080,
    path: '/api/anomalies/test/add',
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Content-Length': content_length
    }
  }
  
  start_times_bc[app] = new Date().getTime();
  const req = http.request(options1, res => {
    console.log(`statusCode: ${res.statusCode}`)
  
    res.on('data', d => {
      process.stdout.write(d);
      end_times_bc[app] = new Date().getTime();
      end_times[app] = new Date().getTime();
      getTimes()
    })
  })
  
  req.on('error', error => {
    console.error(error)
  })
  
  req.write(anomaly)
  req.end()
}

function getTimes(){
  let conti = true;
  var i=0;

  let total_time_average = 0;
  let total_time_bc_average = 0;
  let time_without_bc_average = 0;

  while(conti==true && i<num_app){
    if(end_times[i]==0){
      conti=false;
    }
    i++;
  }
  
  if(conti==true){
    console.log("");
    let total_time_sum = 0;
    let total_time_bc_sum = 0;
    let time_without_bc_sum = 0;
    for (let i = 0; i < num_app; i++) {
      let total_time = end_times[i] - start_times[i];
      let total_time_bc = end_times_bc[i] - start_times_bc[i];
      let time_without_bc = total_time - total_time_bc;
      console.log("Total time: "+ total_time + " ms - Total time bc: "+ total_time_bc +" ms - Time without bc: "+ time_without_bc + " ms"); 

      total_time_sum += total_time;
      total_time_bc_sum += total_time_bc;
      time_without_bc_sum += time_without_bc;
    }
    total_time_average = total_time_sum/num_app;
    total_time_bc_average = total_time_bc_sum/num_app;
    time_without_bc_average = time_without_bc_sum/num_app;
    console.log("Total time average: "+ total_time_average + " ms - Total time bc average: "+ total_time_bc_average +" ms - Time without bc average: "+ time_without_bc_average + " ms"); 
  }
}

function analyzeTemperature(temperature) {
  let level = 0;
  switch (true) {
    case temperature > 50:
        level = 3;
        break;
    case temperature > 40:
        level = 2;
        break;
    case temperature > 30:
        level = 1;
        break;
    default:
        level = 0;
  }
  return level;
}
