package servlets;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import store.Cart;
import javax.servlet.http.*;

import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class CartServlet extends HttpServlet {

    /**
     * Updates Cart, and outputs JSON representation of contents
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {

        Enumeration<String> headers = req.getHeaderNames();
        List<String> headerList = Collections.list(headers);
        headerList.forEach(header -> System.out.println(header + ": " + req.getHeader(header)));

        // Retrieve cart from session or create new one
        Cart cart = getCartFromSession(req);

        // Get reqired parameter values
        String action = "";
        String item = "";
        if (false) {
            action = req.getParameter("action");
            item = req.getParameter("item");
        } else{
            String requestBody = req.getReader().lines().collect(Collectors.joining());
            System.out.println(requestBody);
            JsonReader reader = Json.createReader(new StringReader(requestBody));
            JsonObject interactionObject = reader.readObject();
            
            action = interactionObject.getString("action");
            item = interactionObject.getString("item");
        }
        System.out.println(action + " " + item);

        /*
        Check whether action and item are present
        If one is not provided or if the action does not equal "add" nor "remove", nothing happens (i.e., no error)
         */
        if ((action != null) && (item != null)) {
            System.out.println("Doing some action");
            if ("add".equals(action)) {
                cart.addItem(item);

            } else if ("remove".equals(action)) {
                cart.decreaseItemQuantity(item);

            }
        }

        // encode cart as JSON, tell the response that it contains JSON, and write the cart to it (as payload)
        sendJsonResponse(res, cart);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
        // Bounce to post, for debugging use
        // Hit this servlet directly from the browser to see JSON
        doPost(req, res);
    }

    // if session contains no Cart object, a new Cart is created and bound to the session
    // (i.e. first connection between client and server or user does not use cookies)
    private Cart getCartFromSession(HttpServletRequest req) {

        HttpSession session = req.getSession(true);
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            System.out.println("New Cart");
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        return cart;
    }

    private void sendJsonResponse(HttpServletResponse res, Cart cart) throws IOException {
        String cartJson = cart.toJSON();
        System.out.println("Json to be sent back: " + cartJson);
        res.setContentType("application/json");
        res.getWriter().write(cartJson);
    }
}
