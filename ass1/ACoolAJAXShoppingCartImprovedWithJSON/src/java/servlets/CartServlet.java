package servlets;

import java.util.Collections;
import store.Cart;
import javax.servlet.http.*;

import java.util.Enumeration;
import java.util.List;

public class CartServlet extends HttpServlet {

    /**
     * Updates Cart, and outputs XML representation of contents
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {

        Enumeration<String> headers = req.getHeaderNames();
        List<String> headerList = Collections.list(headers);
        headerList.forEach(header -> System.out.println(header + ": " + req.getHeader(header)));

        // Retrieve cart from session or create new one
        Cart cart = getCartFromSession(req);

        // Get reqired parameter values
        String action = req.getParameter("action");
        String item = req.getParameter("item");
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

        // encode cart as XML, tell the response that it contains XML, and write the cart to it (as payload)
        String cartXml = cart.toXml();
        String cartJson = cart.toJSON();
        System.out.println("Json to be sent back: " + cartJson);
        //res.setContentType("text/xml");
        res.setContentType("application/json");
        res.getWriter().write(cartJson);
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
        // Bounce to post, for debugging use
        // Hit this servlet directly from the browser to see XML
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
}
