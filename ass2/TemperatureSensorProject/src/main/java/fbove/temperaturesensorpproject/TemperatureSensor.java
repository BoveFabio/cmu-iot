/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fbove.temperaturesensorpproject;

import java.util.concurrent.ThreadLocalRandom;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author fabio
 */
public class TemperatureSensor {

    public static void main(String[] args) throws InterruptedException {

        String topic = "";
        // We don't mind the potential performance impact
        int qos = 2;
        String broker = "tcp://localhost:1883";
        String clientId = "TemperatureSensor";

        MqttClient sampleClient;

        try {
            // connect to MQTT broker
            sampleClient = new MqttClient(broker, clientId);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts);

            // every 5 seconds, generate random temperature and publish it
            while (true) {
                int randomTemperature = ThreadLocalRandom.current().nextInt(0, 101);

                // make sure temperature is published to the right topic
                if (randomTemperature <= 45) {
                    topic = "pittsburgh/temperature/coldTemps";
                } else if (randomTemperature <= 80) {
                    topic = "pittsburgh/temperature/niceTemps";
                } else {
                    topic = "pittsburgh/temperature/hotTemps";
                }

                // Send temperature and time as JSON
                String content = String.format("{\"temperature\": %d, \"timestamp\": %d }", randomTemperature, System.currentTimeMillis());

                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(qos);
                sampleClient.publish(topic, message);

                Thread.sleep(5000);
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
