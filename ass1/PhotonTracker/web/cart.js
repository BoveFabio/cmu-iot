// Timestamp of cart that page was last updated with
var lastCartUpdate = 0;

/*
 * Adds the specified item to the shopping cart, via Ajax call
 * itemCode - product code of the item to add
 */
function requestUpdate() {

    var req = newXMLHttpRequest();

    // updateCart is executed when response for request arrives
    req.onreadystatechange = getReadyStateHandler(req, updateStatus);

    req.open("GET", "tracker.do", true);
    req.setRequestHeader("Content-Type", "application/json");
    req.send();
}


/*
 * Receive Photon ID and date of latest heartbeat. Update the webpage to display it accordingly.
 */
function updateStatus(photonInfoJsonString) {
    var photonInfo = JSON.parse(photonInfoJsonString);
    var generated = photonInfo.generated;
    
    if (generated > lastCartUpdate) {
        lastCartUpdate = generated;
        document.getElementById("photonId").innerHTML = photonInfo.photonId;
        
        if (new Date() - photonInfo.lastHeartbeat > 20000) {
            if (photonInfo.lastHeartbeat <= 0) {
                document.getElementById("lastHeartbeat").innerHTML = "No Photon heartbeats received yet.";
            } else {
                document.getElementById("lastHeartbeat").innerHTML = new Date(photonInfo.lastHeartbeat) + "\nProbably disconnected";
            }
        } else {
            document.getElementById("lastHeartbeat").innerHTML = new Date(photonInfo.lastHeartbeat);
        }
    }
}
