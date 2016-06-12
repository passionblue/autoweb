package com.autosite.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.seox.util.PropertiesLoader;

public class UploadResourceManager {

    private static Logger m_logger = LoggerFactory.getLogger(UploadResourceManager.class);
    
    public static final int RESOURCE_TYPE_DEFAULT   = 0;
    public static final int RESOURCE_TYPE_IMAGE     = 1;
    public static final int RESOURCE_TYPE_TEXT      = 2;
    public static final int RESOURCE_TYPE_SCRIPTS   = 3; // For site changes
    
    private static UploadResourceManager _instance = new UploadResourceManager();

    public static UploadResourceManager Instance() {
        return _instance;
    }
    
    protected String m_rootDir = "c:\\ApacheTomcat\\apache-tomcat-5.5.23\\webapps\\autosite";
    protected String m_webResourceRoot = "upload";
    protected boolean m_deletePhysicalResource = true;
    
    private UploadResourceManager(){
        

        System.out.println(System.getenv("OS"));
        
        String osSuffix = "lunux";
        
        if (System.getenv("OS") != null && 
            ( System.getenv("OS").indexOf("Windows") >= 0 || System.getenv("OS").indexOf("WINDOWS") >= 0 || System.getenv("OS").indexOf("windows") >= 0)) {
            osSuffix = "windows";
        }
        
        m_rootDir = PropertiesLoader.getInstance().getProperty("app.application.root." + osSuffix);
        m_webResourceRoot = PropertiesLoader.getInstance().getProperty("app.resource.root");
        
        m_logger.info("UploadResourceManager root " + m_rootDir + "/<site>/" + m_webResourceRoot);
        
    }
    
    public String getWebUrl(long siteId, int resourceType ){
        String retDir =  siteId + "/" + getResourceTypeString(resourceType);
        makeSureDirectory(m_rootDir + "/" + m_webResourceRoot + "/" +retDir);
        
        return retDir;
    }

    public File[] getResourceNames(long siteId, int resourceType){
        String retDir =  m_rootDir + "/" + siteId + "/" + m_webResourceRoot + "/"+ getResourceTypeString(resourceType);
        File file = new File(retDir);
        if (!file.exists()){
            return new File[0];
        }
        
        return file.listFiles();
    }
    
    public String getRootDir(){
        return m_rootDir;
    }
    
    public String getWebRoot() {
        return m_webResourceRoot;
    }
    
    public String getResourceTypeString(int type){
        switch(type){
        case 1: return "image";
        case 2: return "text";
        default : return "other";
        }
    }
    
    public int getFileType(String fileName){
        if (fileName == null) return -1;
        
        int pos = fileName.lastIndexOf(".");
        if (pos == -1) return 0;
        
        String ext = fileName.substring(pos+1);
        return getFileTypeByExtention(ext);
    }
    
    public int getFileTypeByExtention(String extension){
        if (extension == null) return 0;
        if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpeg"))
            return 1;
        
        return 0;
    }

    public void makeSureDirectory(String path){
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
            m_logger.info("Directory created = " + path);
        }
    }
    
    
    public void writeToFile(byte[] data, String filename) throws Exception {
        
        BufferedOutputStream bufferedOutput = null;
        
        try {
            
            //Construct the BufferedOutputStream object
            bufferedOutput = new BufferedOutputStream(new FileOutputStream(filename));
            
            //Start writing to the output stream
            bufferedOutput.write(data);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage(), ex);
        } finally {
            //Close the BufferedOutputStream
            try {
                if (bufferedOutput != null) {
                    bufferedOutput.flush();
                    bufferedOutput.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void deleteFile(String url) throws Exception{
        if (! m_deletePhysicalResource ) return;
        String physicalFile = m_rootDir + "/" + m_webResourceRoot + "/" + url;
        File file = new File(physicalFile);
        m_logger.info("Deleting file =" + physicalFile);
        file.delete();
    }
    
    
    
    public static void main(String[] args) {
        UploadResourceManager.Instance();
    }
    
}
