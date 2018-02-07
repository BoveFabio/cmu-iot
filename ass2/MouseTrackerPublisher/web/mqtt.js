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
    message = new Paho.MQTT.Message("Hello");
    message.destinationName = "MouseTracker";
    mqttClient.send(message);
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
}

// whenever this function is executed, the event that is passed by the canvas is checked for its clientX and clientY members
// which represent where in the canvas the mouse currently is. Out of that, a string is built to display and written inside the "demo" paragraph.
function myFunction(e) {
    var x = e.clientX;
    var y = e.clientY;
    var coor = "Coordinates: (" + x + "," + y + ")";
    document.getElementById("demo").innerHTML = coor;
    
    // When mouse moves inside the canvas, we publish a simple JSON message that contains the member "action" to indicate that the mouse moved inside the canvas along with its current x and y coordinates.
    message = new Paho.MQTT.Message(JSON.stringify({"x" : x, "y": y, "action": "movedInCanvas"}));
    message.destinationName = "MouseTracker";
    mqttClient.send(message);
}

// When this function is called, the x and y coordinates shown to the user are "erased", i.e., not displayed anymore because the mouse is outside the canvas
function clearCoor() {
    document.getElementById("demo").innerHTML = "";

    // When mouse leaves the canvas, we publish a simple JSON message that only contains the member "action" to indicate that the mouse left the canvas.
    message = new Paho.MQTT.Message(JSON.stringify({"action": "leftCanvas"}));
    message.destinationName = "MouseTracker";
    mqttClient.send(message);
}