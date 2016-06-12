/*
 * Created on Jan 4, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.tools.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetTool {
    
    public static void main(String[] args) {
    
        String url = "http://www.google.com/search?q=new+york&hl=en&lr=&start=10";
        String result = retrieveData(url);

        System.out.println(result);
    }
    
    public static String retrieveData(String url)
    {
        
        URL server = null;
        try {
            server = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            
            connection = (HttpURLConnection) server.openConnection();
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "US-ASCII"));
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        }

        StringBuffer buffer = new StringBuffer();
        try {
            while (true) 
            {
                String line = reader.readLine();
                if (line == null) break;

                buffer.append(line + "\n");
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            return null;
        }
        finally
        {
            try {
                reader.close();
            } catch (IOException e3) {
            }
        }

        return buffer.toString();
    }
}
