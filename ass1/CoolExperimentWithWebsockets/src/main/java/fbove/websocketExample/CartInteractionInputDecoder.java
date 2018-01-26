/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fbove.websocketExample;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author nb
 */
public class CartInteractionInputDecoder implements Decoder.Text<CartInteractionInput> {

    @Override
    public CartInteractionInput decode(String string) throws DecodeException {
        System.out.println("decoding: " + string);
        JsonObject jsonObject = Json.createReader(new StringReader(string)).readObject();
        String action = jsonObject.getString("action", null);
        String itemCode = jsonObject.getString("itemCode", null);
        return new CartInteractionInput(action, itemCode);
    }

    @Override
    public boolean willDecode(String string) {
        try {
            JsonObject object = Json.createReader(new StringReader(string)).readObject();
            if(object.isNull("action")){
                return false;
            }
            return true;
        } catch (JsonException ex) {
            return false;
        }
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
