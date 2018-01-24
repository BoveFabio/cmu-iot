// Timestamp of cart that page was last updated with
var lastCartUpdate = 0;

/*
 * Adds the specified item to the shopping cart, via Ajax call
 * itemCode - product code of the item to add
 */
function addToCartXml(itemCode) {

    var req = newXMLHttpRequest();
    
    // updateCart is executed when response for request arrives
    req.onreadystatechange = getReadyStateHandler(req, updateCart);

    req.open("POST", "cart.do", true);
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    req.send("action=add&item=" + itemCode);
}

function addToCartJson(itemCode) {
var req = newXMLHttpRequest();
    
    req.onreadystatechange = getReadyStateHandler(req, updateCart);
    
    req.open("POST", "cart.do", true);
    req.setRequestHeader("Content-Type", "application/json");
    var interaction = {};
    interaction.action = "add";
    interaction.item = itemCode;
    
    req.send(JSON.stringify(interaction));
}

function removeFromCartXml(itemCode){
    var req = newXMLHttpRequest();
    
    req.onreadystatechange = getReadyStateHandler(req, updateCart);
    
    req.open("POST", "cart.do", true);
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    req.send("action=remove&item=" + itemCode);
}

function removeFromCartJson(itemCode){
    var req = newXMLHttpRequest();
    
    req.onreadystatechange = getReadyStateHandler(req, updateCart);
    
    req.open("POST", "cart.do", true);
    req.setRequestHeader("Content-Type", "application/json");
    var interaction = {};
    interaction.action = "remove";
    interaction.item = itemCode;
    
    req.send(JSON.stringify(interaction));
}


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
