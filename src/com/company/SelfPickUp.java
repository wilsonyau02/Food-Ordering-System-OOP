package com.company;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SelfPickUp extends Order {
    private LocalDateTime selfPickUpTime;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy 'at' hh:mm a");

    public LocalDateTime getSelfPickUpTime() {
        return selfPickUpTime;
    }


    public SelfPickUp() {}

    public SelfPickUp(LocalDateTime selfPickUpTime) {
        this.selfPickUpTime = selfPickUpTime;
    }

    public void setSelfPickUpTime(int afterNumDays, int hour, int minute) {
        this.selfPickUpTime = DeliveryOrder.getLocalDateTime(afterNumDays, hour, minute);
    }

    public void setSelfPickUpTime(int hour, int minute) {
        this.selfPickUpTime = DeliveryOrder.getLocalDateTime(hour, minute);
    }

    public void setSelfPickUpTime() {
        this.selfPickUpTime = LocalDateTime.now().plusMinutes(10);
    }



    public String printSelfPickupDetails() {
        return "\nPick Up Time: " + formatter.format(selfPickUpTime);
    }

    public String tracePickupOrder() {
        if (LocalDateTime.now().isBefore(this.selfPickUpTime.minusMinutes(8))) {return "Not yet preparing order";}
        else if (LocalDateTime.now().isAfter(this.selfPickUpTime.minusMinutes(8)) &&
                LocalDateTime.now().isBefore(this.selfPickUpTime)) {return "Preparing order";}
        else {return "Ready to be picked up";}
    }
}
