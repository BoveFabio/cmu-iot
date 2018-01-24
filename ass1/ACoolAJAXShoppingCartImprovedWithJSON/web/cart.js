// Timestamp of cart that page was last updated with
var lastCartUpdate = 0;

/*
 * Adds the specified item to the shopping cart, via Ajax call
 * itemCode - product code of the item to add
 */
function addToCart(itemCode) {

    var req = newXMLHttpRequest();
    
    // updateCart is executed when response for request arrives
    req.onreadystatechange = getReadyStateHandler(req, updateCart);

    req.open("POST", "cart.do", true);
    req.setRequestHeader("Content-Type", "application/json");
    var interactionObject = {};
    interactionObject.action = "add";
    interactionObject.itemCode = itemCode;
    req.send(JSON.stringify(interactionObject));
}

function removeFromCart(itemCode){
    var req = newXMLHttpRequest();
    
    req.onreadystatechange = getReadyStateHandler(req, updateCart);
    
    req.open("POST", "cart.do", true);
    req.setRequestHeader("Content-Type", "application/json");
    var interactionObject = {};
    interactionObject.action = "remove";
    interactionObject.itemCode = itemCode;
    req.send(JSON.stringify(interactionObject));
}

// TODO maybe add extra function for just initializing cart when loading page?


/*
 * Update shopping-cart area of page to reflect contents of cart
 * described in XML document.
 */
function updateCart(cartJsonString) {
    console.log("updateCart: " + cartJsonString);
    cart = JSON.parse(cartJsonString);
    if (cart.generated > lastCartUpdate) {
        lastCartUpdate = cart.generated;
        // get current cart and "reset" it
        var contents = document.getElementById("contents");
        contents.innerHTML = "";

        // fill the reset cart with data that from XML
        var items = cart.contents;
        for (var I = 0; I < items.length; I++) {

            // leverages knowledge about specific cart XML structure
            var item = items[I];

            var listItem = document.createElement("li");
            listItem.appendChild(document.createTextNode(item.name + " x " + item.quantity));
            contents.appendChild(listItem);
        }

    }

    document.getElementById("total").innerHTML = cart.total;
}
