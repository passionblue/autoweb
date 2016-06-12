package com.autosite.tool;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class TestHttpClientUrl {
    
    
    public static void main(String[] args) throws Exception {
    
        
        
        String url = "http://example.com";
        String charset = "UTF-8";
        String param1 = "value1";
        String param2 = "value2";
        // ...
        String query = String.format("param1=%s&param2=%s", 
             URLEncoder.encode(param1, charset), 
             URLEncoder.encode(param2, charset));
        
        
        URLConnection connection = new URL(url + "?" + query).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = connection.getInputStream();
    }
}
