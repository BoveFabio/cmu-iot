var photons = new Map();
// Create a client instance
mqttClient = new Paho.MQTT.Client("localhost", 9002, "mouseTrackerSubscriberFB");

// set callback handlers
mqttClient.onConnectionLost = onConnectionLost;
mqttClient.onMessageArrived = onMessageArrived;

// connect the client
mqttClient.connect({onSuccess: onConnect});


// called when the client connects
function onConnect() {
    // Once a connection has been made, make a subscription and send a message.
    console.log("onConnect");
    mqttClient.subscribe("student/id");
}

// called when the client loses its connection
function onConnectionLost(responseObject) {
    if (responseObject.errorCode !== 0) {
        console.log("onConnectionLost:" + responseObject.errorMessage);
    }
}

// called when a message arrives
function onMessageArrived(message) {
    console.log("onMessageArrived:" + message.payloadString);
    var messageObject = JSON.parse(message.payloadString);

    // only do soemthing if we have not seen the name of the photon before
    // if two photon register with the same (owner's) name, the second one will be ignored.
    if (!photons.has(messageObject.name)) {
        console.log("event has name: " + messageObject.name);
        photons.set(messageObject.name, messageObject.URL);
        updateTable();
    }
}

// creates the table to be displayed with all registered photons
function updateTable() {
    console.log("Updating table");
    var tableDiv = document.getElementById("photonTable");
    
    var divInnerHtml = "<table border=\"1\"><thead><td>Name</td><td>URL</td></thead><tbody>";
    
    // include all currently registered photons
    for ([name, url] of photons.entries()) {
        divInnerHtml += "<tr><td>" + name + "</td><td>" + url + "</td></tr>";
    }
    
    divInnerHtml += "</tbody></table>"
    
    tableDiv.innerHTML = divInnerHtml;
}