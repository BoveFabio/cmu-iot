/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fbove.websocketExample;

import javax.json.JsonObject;

/**
 *
 * @author fabio
 */
public class CartInteractionInput {

    public String action;
    public String itemCode;
    
    CartInteractionInput(String action, String itemCode){
        this.action = action;
        this.itemCode = itemCode;
        System.out.println("Finished decoding: " + this);
    }
    
    @Override
    public String toString(){
        return String.format("{\"action\":\"%s\", \"itemCode\":\"%s\"}", action, itemCode);
    }
    
}
