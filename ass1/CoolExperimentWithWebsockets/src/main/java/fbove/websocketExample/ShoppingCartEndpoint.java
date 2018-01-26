/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fbove.websocketExample;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author nb
 */
@ServerEndpoint(value = "/shoppingCartEndpoint",
        //encoders = {CartEncoder.class},
        decoders = {CartInteractionInputDecoder.class})
public class ShoppingCartEndpoint {

    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    private static Cart cart = new Cart();

    @OnOpen
    public void onOpen(Session peer) {
        peers.add(peer);
    }

    @OnClose
    public void onClose(Session peer) {
        peers.remove(peer);
    }

    @OnMessage
    public void handleIncomingAction(CartInteractionInput cartInteractionInput, Session session) throws IOException, EncodeException {

        System.out.println(cartInteractionInput);

        // Get required parameter values from interactionInput
        String action = cartInteractionInput.action;
        String itemCode = cartInteractionInput.itemCode;

        /*
        Check whether action and item are present
        If one is not provided or if the action does not equal "add" nor "remove", nothing happens (i.e., no error)
         */
        if ((action != null) && (itemCode != null)) {
            if ("add".equals(action)) {
                cart.addItem(itemCode);

            } else if ("remove".equals(action)) {
                cart.decreaseItemQuantity(itemCode);

            }
        }

        for (Session peer : peers) {
            peer.getBasicRemote().sendObject(cart);
        }
    }
}
