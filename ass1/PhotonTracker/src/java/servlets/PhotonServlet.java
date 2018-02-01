package servlets;

import java.io.StringReader;
import java.util.stream.Collectors;
import javax.servlet.http.*;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

public class PhotonServlet extends HttpServlet {

    private static long lastHeartbeat = -1;
    private static String photonId = "No Photon heartbeats received yet.";

    /**
     * Updates Photon ID and heartbeat time if request contains a Photon ID.
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws java.io.IOException {
        String requestBody = req.getReader().lines().collect(Collectors.joining());
        System.out.println("Incoming POST request: " + requestBody);

        if (!requestBody.startsWith("Photon_ID")) {
            System.out.println("No Photon ID present.");
        } else {
            photonId = requestBody.substring(10);
            lastHeartbeat = System.currentTimeMillis();
        }
    }

    @Override
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
