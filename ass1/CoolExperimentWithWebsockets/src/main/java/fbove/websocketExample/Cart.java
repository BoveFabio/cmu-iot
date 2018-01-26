package fbove.websocketExample;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * Models a shopping cart
 */
public class Cart {
    /**
     * Stores the contents of the shopping cart.
     */
    private HashMap<Item, Integer> contents;

    /**
     * Creates a new Cart instance
     */
    public Cart() {
        contents = new HashMap<Item, Integer>();
    }

    public HashMap<Item, Integer> getContents() {
        return contents;
    }

    /**
     * Adds a named item to the cart
     *
     * @param itemCode The code of the item to add to the cart
     */
    public void addItem(String itemCode) {

        if (Catalog.containsItem(itemCode)) {
            Item item = Catalog.getItem(itemCode);

            int newQuantity = 1;
            if (contents.containsKey(item)) {
                Integer currentQuantity = contents.get(item);
                newQuantity += currentQuantity.intValue();
            }
            
            contents.put(item, new Integer(newQuantity));
        }
    }

    /**
     * Removes the named item from the cart completely, i.e. sets quantity to zero
     *
     * @param itemCode code of item to remove
     */
    public void removeItems(String itemCode) {
        contents.remove(Catalog.getItem(itemCode));
    }

    /**
     * Decreases item quantity by one.
     *
     * @param itemCode code of the item to remove
     */
    public void decreaseItemQuantity(String itemCode) {
        Item item = Catalog.getItem(itemCode);
        if (contents.containsKey(item)) {
            if (contents.get(item) <= 1) {
                // Remove item completely when quantity reaches 0
                contents.remove(item);
            } else {
                contents.put(item, contents.get(item) - 1);
            }
        }
    }

    public JsonObject toJSON() {        
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder ob = factory.createObjectBuilder();
        JsonArrayBuilder ab = factory.createArrayBuilder();
        
        for(Item item : contents.keySet()){
            JsonObjectBuilder itemObjectBuilder = factory.createObjectBuilder();
            itemObjectBuilder.add("code", item.getCode());
            itemObjectBuilder.add("name", item.getName());
            itemObjectBuilder.add("quantity", contents.get(item));
            ab.add(itemObjectBuilder);
        }
        
        ob.add("generated", System.currentTimeMillis()).add("total", getCartTotal()).add("contents", ab);
        return ob.build();
    }


    private String getCartTotal() {
        int total = 0;

        for (Item item : contents.keySet()) {
            total += (item.getPrice() * contents.get(item));
        }

        return "$" + new BigDecimal(total).movePointLeft(2);
    }
    
    @Override
    public String toString() {
        return toJSON().toString();
    }
}