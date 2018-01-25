// Timestamp of cart that page was last updated with
var lastCartUpdate = 0;

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
 * described in JSON document.
 */
function updateCart(cartJsonString) {
    console.log("updateCart: " + cartJsonString);
    cart = JSON.parse(cartJsonString);
    if (cart.generated > lastCartUpdate) {
        lastCartUpdate = cart.generated;
        // get current cart and "reset" it
        var contents = document.getElementById("contents");
        contents.innerHTML = "";

        // fill the reset cart with data that from JSON
        var items = cart.contents;
        for (var I = 0; I < items.length; I++) {

            // leverages knowledge about specific cart JSON structure
            var item = items[I];

            var listItem = document.createElement("li");
            listItem.appendChild(document.createTextNode(item.name + " x " + item.quantity));
            contents.appendChild(listItem);
        }

    }

    document.getElementById("total").innerHTML = cart.total;
}
