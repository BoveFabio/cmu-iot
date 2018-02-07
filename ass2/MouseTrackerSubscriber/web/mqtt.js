// Create a client instance
mqttClient = new Paho.MQTT.Client("localhost", 9002, "mouseTrackerSubscriber");

// set callback handlers
mqttClient.onConnectionLost = onConnectionLost;
mqttClient.onMessageArrived = onMessageArrived;

// connect the client
mqttClient.connect({onSuccess: onConnect});


// called when the client connects
function onConnect() {
    // Once a connection has been made, make a subscription and send a message.
    console.log("onConnect");
    mqttClient.subscribe("MouseTracker");
    
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
    var event = JSON.parse(message.payloadString);
    var toBeDisplayed = "";
    if(event.action == "leftCanvas"){
        toBeDisplayed = "Mouse left the canvas.";
    } else if (event.action == "movedInCanvas"){
        toBeDisplayed = "Mouse is currently at (x,y) = (" + event.x + "," + event.y + ").";
    }
    
    document.getElementById("coordinates").innerHTML = toBeDisplayed;
}