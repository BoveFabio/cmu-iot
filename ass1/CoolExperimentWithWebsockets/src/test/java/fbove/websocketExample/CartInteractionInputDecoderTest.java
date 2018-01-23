/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fbove.websocketExample;

import javax.websocket.EndpointConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

/**
 *
 * @author fabio
 */
public class CartInteractionInputDecoderTest {
    
    public CartInteractionInputDecoderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of decode method, of class CartInteractionInputDecoder.
     */
    @org.junit.Test
    public void testDecode() throws Exception {
        System.out.println("decode");
        String input = "{\"action\":\"remove\", \"itemCode\":null}";
        CartInteractionInputDecoder instance = new CartInteractionInputDecoder();
        CartInteractionInput result = instance.decode(input);
        assertThat(result.action).isEqualTo("remove");
        assertThat(result.itemCode).isNull();
    }

}
