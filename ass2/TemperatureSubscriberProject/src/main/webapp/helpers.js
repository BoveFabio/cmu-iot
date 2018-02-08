// Create a client instance
mqttClient = new Paho.MQTT.Client("localhost", 9002, "mouseTrackerPublisher");

// set callback handlers
mqttClient.onConnectionLost = onConnectionLost;
mqttClient.onMessageArrived = onMessageArrived;

// connect the client
mqttClient.connect({onSuccess: onConnect});


// called when the client connects
function onConnect() {
    // Once a connection has been made, make a subscription and send a message.
    console.log("onConnect");
}

// called when the client loses its connection
function onConnectionLost(responseObject) {
    if (responseObject.errorCode !== 0) {
        console.log("onConnectionLost:" + responseObject.errorMessage);
    }
}

// called when a message arrives
function onMessageArrived(message) {
    // handle message according to topic
    var incomingMessage = JSON.parse(message.payloadString);
    if(message.destinationName == "pittsburgh/temperature/coldTemps"){
        document.getElementById("coldTemp").innerHTML = incomingMessage.temperature;
        document.getElementById("coldTempTime").innerHTML = new Date(incomingMessage.timestamp);
    } else if(message.destinationName == "pittsburgh/temperature/niceTemps"){
        document.getElementById("niceTemp").innerHTML = incomingMessage.temperature;
        document.getElementById("niceTempTime").innerHTML = new Date(incomingMessage.timestamp);
    } else if(message.destinationName == "pittsburgh/temperature/hotTemps"){
        document.getElementById("hotTemp").innerHTML = incomingMessage.temperature;
        document.getElementById("hotTempTime").innerHTML = new Date(incomingMessage.timestamp);
    }
}

// subscribe / unsubscribe from topic when correspoding checkbox is checked / unchecked
function tempsBoxChanged(checked, temperature) {
    if (checked) {
        mqttClient.subscribe("pittsburgh/temperature/" + temperature + "Temps")
    } else{
        mqttClient.unsubscribe("pittsburgh/temperature/" + temperature + "Temps")
    }
}

// convenience method to subscribe to all temperatures
function allTempsButtonPressed() {
    document.getElementById("coldTempsInterest").checked = true;
    document.getElementById("niceTempsInterest").checked = true;
    document.getElementById("hotTempsInterest").checked = true;

    mqttClient.subscribe("pittsburgh/temperature/+")
}

// conc´venience method to unsubscribe from all topics
function noTempsButtonPressed() {
    document.getElementById("coldTempsInterest").checked = false;
    document.getElementById("niceTempsInterest").checked = false;
    document.getElementById("hotTempsInterest").checked = false;

    mqttClient.unsubscribe("pittsburgh/temperature/+")
}
