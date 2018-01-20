package store;

import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;

/**
 * A very simple shopping Cart
 */
public class Cart {

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
     * @param itemName The name of the item to add to the cart
     */
    public void addItem(String itemCode) {

        Catalog catalog = new Catalog();

        if (catalog.containsItem(itemCode)) {
            Item item = catalog.getItem(itemCode);

            int newQuantity = 1;
            if (contents.containsKey(item)) {
                Integer currentQuantity = contents.get(item);
                newQuantity += currentQuantity.intValue();
            }

            contents.put(item, new Integer(newQuantity));
        }
    }

    /**
     * Removes the named item from the cart
     *
     * @param itemName Name of item to remove
     */
    public void removeItems(String itemCode) {

        contents.remove(new Catalog().getItem(itemCode));
    }

    /**
     * Decreases item quantity by one.
     *
     * @param itemCode Name of the item to remove
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

    public String toJSON() {        
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

        return ob.build().toString();
    }

    /**
     * @return XML representation of cart contents
     */
    public String toXml() {
        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version=\"1.0\"?>\n");
        xml.append("<cart generated=\"" + System.currentTimeMillis() + "\" total=\"" + getCartTotal() + "\">\n");

        contents.keySet().forEach(item -> {
            int itemQuantity = contents.get(item).intValue();

            // Could we not put name and quantity ito item as we do woth the code?
            xml.append("<item code=\"" + item.getCode() + "\">\n");
            xml.append("<name>");
            xml.append(item.getName());
            xml.append("</name>\n");
            xml.append("<quantity>");
            xml.append(itemQuantity);
            xml.append("</quantity>\n");
            xml.append("</item>\n");
        });

        xml.append("</cart>\n");
        return xml.toString();
    }

    private String getCartTotal() {
        int total = 0;

        for (Iterator<Item> I = contents.keySet().iterator(); I.hasNext();) {
            Item item = I.next();
            int itemQuantity = contents.get(item).intValue();

            total += (item.getPrice() * itemQuantity);
        }

        return "$" + new BigDecimal(total).movePointLeft(2);
    }
}
