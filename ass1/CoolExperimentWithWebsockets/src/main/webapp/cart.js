// Timestamp of cart that page was last updated with
var lastCartUpdate = 0;

/*
 * Adds the specified item to the shopping cart
 * itemCode - product code of the item to add
 */
function addToCart(itemCode) {
    var cartInteraction = {"action":"add", "itemCode":itemCode};
    sendText(JSON.stringify(cartInteraction));
}

/*
 * Removes the specified item from the shopping cart
 * itemCode - product code of the item to add
 */
function removeFromCart(itemCode){
    var cartInteraction = {"action":"remove", "itemCode":itemCode};
    sendText(JSON.stringify(cartInteraction));
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
            var item = items[I];

            var listItem = document.createElement("li");
            listItem.appendChild(document.createTextNode(item.name + " x " + item.quantity));
            contents.appendChild(listItem);
        }

    }

    document.getElementById("total").innerHTML = cart.total;
}
