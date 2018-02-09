var dataSingleDice;
var dataSumDice;
var chartSingleDice;
var chartSumDice;
var mqttClient;


var optionsSingleDice = {
    width: 400, height: 150,
    min: 1, max: 6,
    majorTicks: [1, 2, 3, 4, 5, 6],
    minorTicks: 0
};

var optionsSumDice = {
    width: 400, height: 150,
    min: 2, max: 12,
    majorTicks: [2, 4, 6, 8, 10, 12],
    minorTicks: 2
};

var optionsDiceDistribution = {
    width: 400, height: 200,
    title: 'Sum Distribution',
    curveType: 'function',
    legend: {position: 'bottom'},
    vAxis: {
        format: 'percent',
        minValue: 0,
        maxValue: 0.25
    }
};

var distributionData = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];

mqttClient = new Paho.MQTT.Client("localhost", 9002, "DiceSubscriber");

// set callback handlers
mqttClient.onConnectionLost = onConnectionLost;
mqttClient.onMessageArrived = onMessageArrived;


function googleLoadCallback() {
    // initialize data for drawing charts
    dataSingleDice = google.visualization.arrayToDataTable([
        ['Label', 'Value'],
        ['Die 1', 3],
        ['Die 2', 3]
    ]);

    dataSumDice = google.visualization.arrayToDataTable([
        ['Label', 'Value'],
        ['Sum', 7],
        ['Average', 7]
    ]);

    dataDiceDistribution = google.visualization.arrayToDataTable([
        ['Sum', 'Occurrences'],
        ['2', 0],
        ['3', 0],
        ['4', 0],
        ['5', 0],
        ['6', 0],
        ['7', 0],
        ['8', 0],
        ['9', 0],
        ['10', 0],
        ['11', 0],
        ['12', 0]
    ]);

    // build initial charts
    chartSingleDice = new google.visualization.Gauge(document.getElementById('singleDiceChart'));
    chartSumDice = new google.visualization.Gauge(document.getElementById('sumDiceChart'));
    chartDiceDistribution = new google.visualization.LineChart(document.getElementById('diceDistributionChart'));

    // connect the client after charts have been initialized
    mqttClient.connect({onSuccess: onConnect});
}

// update data and redraw charts according to received throws
function updateCharts(die1, die2) {
    distributionData[die1 + die2 - 2]++;

    var totalThrows = 0;
    var totalEyes = 0;
    for (var i = 2; i <= 12; i++) {
        totalThrows += distributionData[i - 2];
        totalEyes += distributionData[i - 2] * i;
    }

    dataSingleDice.setValue(0, 1, die1);
    dataSingleDice.setValue(1, 1, die2);

    dataSumDice.setValue(0, 1, die1 + die2);
    dataSumDice.setValue(1, 1, 1.0 * totalEyes / totalThrows);

    for (var i = 2; i <= 12; i++) {
        dataDiceDistribution.setValue(i - 2, 1, 1.0 * distributionData[i - 2] / totalThrows);
    }

    chartSingleDice.draw(dataSingleDice, optionsSingleDice);
    chartSumDice.draw(dataSumDice, optionsSumDice);
    chartDiceDistribution.draw(dataDiceDistribution, optionsDiceDistribution);
}

// MQTT PART

// called when the client connects
function onConnect() {
    // Once a connection has been made, make a subscription and send a message.
    console.log("onConnect");
    mqttClient.subscribe("dice");
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
    updateCharts(incomingMessage.Die1, incomingMessage.Die2);
}

// END MQTT PART

// Load the Visualization API and the corechart+gauge packages.
google.charts.load('current', {'packages': ['corechart', 'gauge']});

// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(googleLoadCallback);