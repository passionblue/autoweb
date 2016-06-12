package com.autosite.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.apache.log4j.Logger;

public class WhoisUtil {
    
    
    public static String getRawWhois(String target, String server, int port, long timeout) {
        return getRawWhois(new String[]{target}, server, port, timeout);
    }    
    
    public static String getRawWhois(String target[], String server, int port, long timeout) {
        // Build the whois query using command line arguments
        StringBuffer buff = new StringBuffer(target[0]);

        for (int i = 1; i < target.length; i++) {
            buff.append(" " + target[i]);
        }

        m_logger.info("Sending whois request to " + server + " ips=" + buff.toString());
        
        // Convert string buffer to string
        String query = buff.toString();

        // The whois server can be specified after "@"
        // e.g.: query@whois.networksolutions.com
        int at = query.lastIndexOf("@");
        int len = query.length();

        if ((at != -1)) {
            // Remove the @ if last character in query
            // e.g.: john@doe.com@
            if (at == (len - 1)) {
                query = query.substring(0, len - 1);
            }
            else {
                // The whois server is specified after "@"
                server = query.substring(at + 1, len);
                // The whois query is specified before "@"
                query = query.substring(0, at);
            }
        }

        long start = System.currentTimeMillis();
        try {
            // Establish connection to whois server & port
            Socket connection = new Socket(server, port);
            PrintStream out = new PrintStream(connection.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";

            // Send the whois query
            out.println(query);
            // Display the whois server's address & port
            System.out.println("[" + server + ":" + port + "]");

            // Read/display the query's result
            StringBuffer ret = new StringBuffer();
            while ((line = in.readLine()) != null) {
                //System.out.println(line);
                ret.append(line).append("\n");
            }
            long end = System.currentTimeMillis();
            System.out.println(end - start);
            return ret.toString();
        }
        catch (java.net.UnknownHostException e) {
            // Unknown whois server
            System.err.println("Whois: unknown host: " + server);

            return null;
        }
        catch (IOException e) {
            // Could not connect to whois server
            System.err.println("Whois: " + e);

            return null;
        }
    }
    
    
    public static void main(String[] args) {
        String data = WhoisUtil.getRawWhois("38.100.41.112", "whois.arin.net", 43, 0);
        System.out.println(data);
    }
    
    private static Logger m_logger = Logger.getLogger(WhoisUtil.class);
}
