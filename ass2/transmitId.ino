// This #include statement was automatically added by the Particle IDE.
#include <MQTT.h>


void callback(char* topic, byte* payload, unsigned int length);

// replace with your MQTT's server address (e.g. your local machine, hiveMQ, ...)
byte serverIP[] = { 128, 237, 192, 31 };
MQTT client(serverIP, 1883, callback);

// recieve message
void callback(char* topic, byte* payload, unsigned int length) {
}


void setup() {
    RGB.control(true);
    // connect to the server
    client.connect("PhotonIdPublisherFB");
}

void loop() {
    
    if (client.isConnected()){
        // signal ID transmission in green
        RGB.color(0, 255, 0);
        client.publish("student/id","{\"name\":\"Fabio Bove\",\"URL\":\"http://www.andrew.cmu.edu/user/fbove\"}");
        delay(1000);
        // turn light off after 1 sec
        RGB.color(0,0,0);
        delay(4000);
        client.loop();
    }else{
        // glow red on disconnect
        RGB.color(255, 0, 0);
    }
}