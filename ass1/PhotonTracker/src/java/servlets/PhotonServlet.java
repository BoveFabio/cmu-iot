package servlets;

import java.math.BigDecimal;
import java.util.Collections;
import javax.servlet.http.*;

import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObjectBuilder;

public class PhotonServlet extends HttpServlet {

    private static long lastHeartbeat = -1;
    private static String photonId = "UNINITIALIZED";

    /**
     * Updates Cart, and outputs XML representation of contents
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {

        Enumeration<String> headers = req.getHeaderNames();
        List<String> headerList = Collections.list(headers);
        headerList.forEach(header -> System.out.println(header + ": " + req.getHeader(header)));

        String requestBody = req.getReader().lines().collect(Collectors.joining());
        System.out.println("Incoming POST request: " + req.toString());
        System.out.println("===> Body " + requestBody);
        lastHeartbeat = System.currentTimeMillis();
        
        if(requestBody.startsWith("PHOTON_ID")){
            photonId = requestBody.substring(10);
        }else{
            System.out.println("Invalid Format");
        }

    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
        res.setContentType("application/json");
        
        JsonBuilderFactory factory = Json.createBuilderFactory(null);
        JsonObjectBuilder ob = factory.createObjectBuilder();
        ob.add("lastHeartbeat", lastHeartbeat);
        ob.add("photonId", photonId);
        ob.add("generated", System.currentTimeMillis());
        
        String answer = ob.build().toString();
        System.out.println("Answer: " + answer);
        res.getWriter().write(answer);
    }

}
