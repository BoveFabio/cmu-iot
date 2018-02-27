// @author Fabio Bove
void setup() {
    // enable color control of on board LED
    RGB.control(true);
    // necessary to read value from pin
    pinMode(D0,INPUT_PULLDOWN);
}

void loop() {
    
    if(digitalRead(D0) == HIGH){
        // show green light if value is HIGH, i.e., the wire is connected
        RGB.color(0, 255, 0);
        // and publish a 1 repesenting HIGH
        Particle.publish("OnOrOffValue", "1", PRIVATE);
    }else{
        // show purple light if value is LOW, i.e., the wire is not connected
        RGB.color(255, 0, 255);
        // and publish a 0 repesenting LOW
        Particle.publish("OnOrOffValue", "0", PRIVATE);
    }
    
    // wait a second before turning the light off again
    delay(1000);
    RGB.color(0, 0, 0);
    // wait 29 more seconds so a loop takes roughly 30 seconds
    delay(29000);
}