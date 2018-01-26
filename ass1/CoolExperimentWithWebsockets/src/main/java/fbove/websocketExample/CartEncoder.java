/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fbove.websocketExample;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author nb
 */
public class CartEncoder implements Encoder.Text<Cart> {

    @Override
    public String encode(Cart cart) throws EncodeException {
        return cart.toJSON().toString();
    }

    @Override
    public void init(EndpointConfig config) {
        System.out.println("init");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
    
}
