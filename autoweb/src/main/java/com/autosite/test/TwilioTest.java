package com.autosite.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.CallFactory;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Call;
import com.twilio.sdk.resource.instance.Sms;

// Using the code downloaded from twilio
// This api for www.twilio.com

// https://github.com/twilio/twilio-java
// Sample copied from http://www.twilio.com/docs/howto/sms-notifications-and-alerts


public class TwilioTest {
    
    private static Logger m_logger = LoggerFactory.getLogger(TwilioTest.class);
    
    /* Twilio REST API version */
    public static final String ACCOUNTSID = "AC71bac1b371cde951047ff5c0fc081df6";
    public static final String AUTHTOKEN = "bfff6ca873e4f799dd97081034dd3425";
    public static final String SendPhone = "347-694-4178";
    
    public static final String TESTACCOUNTSID = "ACc88634c6b41a29d97ab859228b8aa902";
    public static final String TESTAUTHTOKEN = "c15500f2598567e853e3525746b362d0";

    public static void sendSMSText(String phoneNumber, String text, boolean useTestAccount){
        
        List list = new ArrayList();
        list.add(phoneNumber);
        
        sendSMSText(list, text, useTestAccount);
    }    
    public static void sendSMSText(List phoneNumbers, String text, boolean useTestAccount){

        TwilioRestClient client = null;
        if (useTestAccount) 
            client = new TwilioRestClient(TESTACCOUNTSID, TESTAUTHTOKEN);
        else
            client = new TwilioRestClient(ACCOUNTSID, AUTHTOKEN);
        
        // Get the account and call factory class
        Account acct = client.getAccount();
        SmsFactory smsFactory = acct.getSmsFactory();

        // build map of server admins
        Map<String, String> admins = new HashMap<String, String>();
        
        // Iterate over all our server admins
        for (Iterator iterator = phoneNumbers.iterator(); iterator.hasNext();) {
            String  toNumber = (String) iterator.next();
            
            // build map of post parameters

            Map<String, String> params = new HashMap<String, String>();
            params.put("From", SendPhone);
            params.put("To", toNumber);
            params.put("Body", text);
            
            try {
                // send an sms a call
                // ( This makes a POST request to the SMS/Messages resource)
                Sms sms = smsFactory.create(params);
                m_logger.info("Success in sending SMS: " + sms.getSid() + " " + SendPhone + "->" + toNumber + " Message: " + text + " Length: " + text.length());
            }
            catch (TwilioRestException e) {
                m_logger.error(e.getMessage(), e);
            }
        }
    }
    
    public static void sendCall(String phoneNumber, String text){
        
        List list = new ArrayList();
        list.add(phoneNumber);
        
        sendCall(list, text);
    }    
    

    public static void sendCall(List phoneNumbers, String text){

        TwilioRestClient client = new TwilioRestClient(ACCOUNTSID, AUTHTOKEN);
//        TwilioRestClient client = new TwilioRestClient(TESTACCOUNTSID, TESTAUTHTOKEN);
        // Get the account and call factory class
        Account acct = client.getAccount();
        CallFactory smsFactory = acct.getCallFactory();
        // build map of server admins
        Map<String, String> admins = new HashMap<String, String>();
        
        // Iterate over all our server admins

        for (Iterator iterator = phoneNumbers.iterator(); iterator.hasNext();) {
            String  toNumber = (String) iterator.next();
            
            // build map of post parameters

            Map<String, String> params = new HashMap<String, String>();
            params.put("From", SendPhone);
            params.put("To", toNumber);
            //params.put("Body", "Bad news " + admins.get(toNumber) + ", the server is down and it needs your help");
            params.put("Url", "http://demo.twilio.com/docs/voice.xml");
//            params.put("Body", text);
            
            try {
                // send an sms a call
                // ( This makes a POST request to the SMS/Messages resource)
                Call sms = smsFactory.create(params);
                System.out.println("Success sending SMS: " + sms.getSid());
            }
            catch (TwilioRestException e) {
                
                System.out.println(e.getErrorCode());
                
                e.printStackTrace();
                
            }
        }
    }
        
    public static void main(String args[]) {
        sendSMSText("17187814061", "test", false);
//        sendCall("17187814061", "test");
    }
    public static void main2(String args[]) {
        /* Instantiate a new Twilio Rest Client */
        
        
        TwilioRestClient client = new TwilioRestClient(ACCOUNTSID, AUTHTOKEN);
        // Get the account and call factory class
        Account acct = client.getAccount();
        SmsFactory smsFactory = acct.getSmsFactory();
        // build map of server admins
        Map<String, String> admins = new HashMap<String, String>();
         
        admins.put("7187814061", "Johnny");
        
        String fromNumber = "347-694-4178";
        
        // Iterate over all our server admins
        
        for (String toNumber : admins.keySet()) {
            // build map of post parameters

            Map<String, String> params = new HashMap<String, String>();
            params.put("From", fromNumber);
            params.put("To", toNumber);
            params.put("Body", "Bad news " + admins.get(toNumber) + ", the server is down and it needs your help");
            try {
                // send an sms a call
                // ( This makes a POST request to the SMS/Messages resource)
                Sms sms = smsFactory.create(params);
                System.out.println("Success sending SMS: " + sms.getSid());
            }
            catch (TwilioRestException e) {
                e.printStackTrace();
            }
        }
    }
}
