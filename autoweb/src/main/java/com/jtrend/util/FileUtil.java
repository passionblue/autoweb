package com.jtrend.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.seox.util.PropertiesLoader;

public class FileUtil {

    public static BufferedReader getBufferedReader(String filename) {

        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            return in;
        }
        catch (FileNotFoundException e) {
            //m_logger.error("************ File not found from the path. will search class path. Try agan with classpath " + filename);
        }

        InputStream ins = PropertiesLoader.class.getResourceAsStream(filename);
        if (ins == null)
            ins = PropertiesLoader.class.getResourceAsStream("/" + filename);

        if (ins == null) {
            m_logger.error("##### Could not load the file. file=" + filename, new Exception("##### Could not load the file. file=" + filename));
            return null;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        m_logger.info("##### File successfully Opened up [" + filename + "]");
        return in;
    }

    public static Writer getFileWriter(String filename) {

        try {
            BufferedWriter in = new BufferedWriter(new FileWriter(filename));
            return in;
        }
        catch (Exception e) {
            m_logger.error(e);
            return null;
        }
    }

    public static List getFileLines(String filename) {

        BufferedReader in = getBufferedReader(filename);

        if (in == null)
            return new ArrayList();

        List ret = new ArrayList();

        while (true) {
            String line = null;
            try {
                line = in.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            if (line == null)
                break;

            ret.add(line);
        }

        return ret;
    }

    public static boolean saveToFile(String name, String content) {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(name, true)));
            writer.write(content);
            writer.flush();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String loadFileToString(String filename) throws Exception {

        BufferedReader in = getBufferedReader(filename);
        StringBuffer buffer = new StringBuffer();

        int curLen = 0;
        while (true) {
            String line = in.readLine();
            if (line == null)
                break;
            
            buffer.append(line);
        }

        in.close();
        
        return buffer.toString();
    }

    public static String loadCodesToString(String filename) throws Exception {

        BufferedReader in = getBufferedReader(filename);
        StringBuffer buffer = new StringBuffer();

        int curLen = 0;
        while (true) {
            String line = in.readLine();
            
            if (line == null)
                break;

            if (line.trim().startsWith("//")) continue;
            
            buffer.append(line).append("\n");
        }

        in.close();
        
        return buffer.toString();
    }
    
    
    public static void main(String[] args) throws Exception {
        String str = loadCodesToString("./inline_script.txt");
        str = str.replaceAll("\n", "");
        str = str.replaceAll("function", "function ");
        m_logger.debug(str);
    }
    
    private static Logger m_logger = Logger.getLogger(FileUtil.class);
}
