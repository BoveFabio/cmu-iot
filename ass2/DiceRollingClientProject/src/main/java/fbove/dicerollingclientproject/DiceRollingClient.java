/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fbove.dicerollingclientproject;

import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author fabio
 */
public class DiceRollingClient {

    public static void main(String[] args) throws InterruptedException {

        String topic = "dice";
        // We don't mind the potential performance impact
        int qos = 2;
        String broker = "tcp://localhost:1883";
        String clientId = "DiceRoller";

        MqttClient sampleClient;

        try {
            // connect to MQTT broker
            sampleClient = new MqttClient(broker, clientId);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts);

            // every second, generate two random integers between 1 and 6
            while (true) {
                // no need for cryptographic randomness
                int die1 = ThreadLocalRandom.current().nextInt(1, 7);
                int die2 = ThreadLocalRandom.current().nextInt(1, 7);

                // Send dice as JSON
                String content = String.format("{\"Die1\": %d, \"Die2\": %d }", die1, die2);

                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
                sampleClient.publish(topic, message);

                Thread.sleep(1000);
            }

        } catch (MqttException me) {
            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }

    }

}
