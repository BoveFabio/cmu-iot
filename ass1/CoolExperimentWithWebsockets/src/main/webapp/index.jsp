<%@ page import="java.util.*" %>
<%@ page import="fbove.websocketExample.*" %>
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <script type="text/javascript" language="javascript" src="websocket.js"></script>
        <script type="text/javascript" language="javascript" src="cart.js"></script>
    </head>
    <body>
        <div style="float: left; width: 500px">
            <h2>Catalog</h2>
            <table border="1">
                <thead><th>Name</th><th>Description</th><th>Price</th><th></th></thead>
                <tbody>
                    <%-- ! loop through all items in the catalog and display them with details --%>
                    <%
                        for (Item item : Catalog.getAllItems()) {
                    %>
                    <tr><td><%= item.getName()%></td><td><%= item.getDescription()%></td><td><%= item.getFormattedPrice()%></td><td><button onclick="addToCart('<%= item.getCode()%>')">Add to Cart</button></td><td><button onclick="removeFromCart('<%= item.getCode()%>')">Remove from Cart</button></td></tr>
                    <% }%>
                </tbody>
            </table>
            <div style="position: absolute; top: 0px; right: 0px; width: 250px">
                <h2>Cart Contents</h2>
                <ul id="contents">
                </ul>
                Total cost: <span id="total">$0.00</span>
            </div>
        </div>
        <!-- only intermediary solution -->
        <!-- By calling the removeFromCart function, we make a dummy interaction with the server, resulting in an answer with the unchanged current cart. -->
        <script>
            if(websocket.readyState == websocket.OPEN){
                console.log("Already open!");
                removeFromCart(null)
            } else{
                console.log("Still connecting, registering callback");
                websocket.onopen = function() {
                    console.log("Now we're open.")
                    removeFromCart(null);
                }
            }
        </script>
    </body>
</html>
