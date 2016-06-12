package com.autosite.util.cleaner;

public class CleanerUtil {

    public static String generatePickupDeliveryTicketSerial(){
        return "PD"+System.currentTimeMillis();
    }

    public static String generateCheckInTicketSerial(){
        return "CH"+System.currentTimeMillis();
    }

}
