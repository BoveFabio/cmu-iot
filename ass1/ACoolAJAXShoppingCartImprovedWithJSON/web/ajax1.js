/*
 * Returns an new XMLHttpRequest object, or false if the browser
 * doesn't support it
 */
function newXMLHttpRequest() {
    var xhr = false;

    // Create XMLHttpRequest object in non-Microsoft browsers
    if (window.XMLHttpRequest) {
        xhr = new XMLHttpRequest();

    } else if (window.ActiveXObject) {

        try {
            // Try to create XMLHttpRequest in later versions
            // of Internet Explorer

            xhr = new ActiveXObject("Msxml2.XMLHTTP");

        } catch (e1) {

            // Failed to create required ActiveXObject

            try {
                // Try version supported by older versions
                // of Internet Explorer

                xhr = new ActiveXObject("Microsoft.XMLHTTP");

            } catch (e2) {

                // Unable to create an XMLHttpRequest by any means
                xhr = false;
            }
        }
    }

    return xhr;
}

/*
 * Returns a function that waits for the specified XMLHttpRequest
 * to complete, then passes its JSON response to the given handler function.
 * req - The XMLHttpRequest whose state is changing
 * responseXmlHandler - Function to pass the XML response to
 */
function getReadyStateHandler(req, responseJsonHandler) {

    // Return an anonymous function that listens to the XMLHttpRequest instance
    return function () {

        // If the request's status is "complete"
        if (req.readyState == 4) {

            // Check that we received a successful response from the server
            if (req.status == 200) {

                // Pass the JSON payload of the response to the handler function.
                responseJsonHandler(req.responseText);

            } else {

                // An HTTP problem has occurred
                alert("HTTP error " + req.status + ": " + req.statusText);
            }
        }
    }
}
