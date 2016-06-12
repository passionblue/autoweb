package com.autosite.content;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class FileRepository {

    public static final String SEPARATOR  = "$$$=";
    
    List m_data = new ArrayList();
    String m_filename = null;
    Writer m_writer = null;
    public FileRepository(String filename) throws Exception{
        m_filename=filename;
        try {
            loadAll();
        }
        catch (Exception e) {
            m_logger.warn("Load has been failed from " + filename, e);
        }
        m_writer = getWriter(m_filename);
        m_logger.info("FileRepo initiated for " + filename);
    }

    private void loadAll() throws Exception {
        
        BufferedReader in = new BufferedReader(new FileReader(m_filename));
        m_data.clear();
        boolean beginData = true;
        StringBuffer buffer = new StringBuffer();
        
        while (true) {
            String line = in.readLine();
            if (line == null) break;
       
            if (line.startsWith(SEPARATOR)) {
                beginData = true;
                if (buffer.length() > 0 ) {
                    m_logger.debug("Loaded Data=" + buffer.toString());
                    m_data.add(buffer.toString());
                }
                buffer  = new StringBuffer();
                continue;
            } 
            
            buffer.append(line);
        }
        
        if (buffer.length() > 0 ) {
            m_logger.debug("Loaded Data=" + buffer.toString());
            m_data.add(buffer.toString());
        }

        in.close();
    }
    
    public void append(String data) {
        if ( m_writer == null) m_writer =  getWriter(m_filename);
        if (m_writer == null ) {
            m_logger.error("Writer could not created for " + m_filename);
        } else {
            try {
                m_writer.write(SEPARATOR + new Date()+ "\n");
                m_writer.write(data);
                m_writer.write("\n");
                m_writer.flush();
            }
            catch (IOException e) {
                m_logger.error("Failed to write to file" + data, e);
            }
        }
        m_data.add(data);
    }
    
    
    public Writer getWriter(String file)  {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
        }
        catch (Exception e) {
            m_logger.error(e);
        }
        return out;
    }
    
    public List getAllData() {
        return new ArrayList(m_data);
    }
    
    public static void main(String[] args) throws Exception{
    
        FileRepository fr = new FileRepository("test.dat");
        fr.append("asdfsdfasfsfsafd\nafdsafsdafadf");
        
        System.out.println(fr.getAllData());
    }
    
    private static Logger m_logger = Logger.getLogger(FileRepository.class);
}
