package com.autosite.tools.image;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

public class ThumbCreator {

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    public static final String IMAGE_JPEG = "jpeg";
    public static final String IMAGE_JPG = "jpg";
    public static final String IMAGE_PNG = "png";
    public static final String IMAGE_GIF = "gif";

    int m_direction = HORIZONTAL;
    int m_width = 200;
    int m_height = 200;
    int m_scaleOption = Image.SCALE_SMOOTH;
    String m_file;
    String m_thumbFile;
    String m_type;

    public ThumbCreator(String fileName) throws Exception {
        this(fileName, HORIZONTAL, 200, Image.SCALE_SMOOTH, null);
    }    

    public ThumbCreator(String fileName, String outDirectory) throws Exception {
        this(fileName, HORIZONTAL, 200, Image.SCALE_SMOOTH, outDirectory);
    }    
    
    public ThumbCreator(String fileName, int direction, int size, int scaleOption, String outDirectory) throws Exception {

        if (m_direction == HORIZONTAL) {
            setOptions(fileName, size, -1, scaleOption, outDirectory);
        }
        else { 
            setOptions(fileName, -1, size, scaleOption, outDirectory);
        }
        
    }
    
    public ThumbCreator(String fileName, int direction, int width, int height, int scaleOption, String outDirectory) throws Exception{
        setOptions(fileName, width, height, scaleOption, outDirectory);
    }

    private void setOptions(String fileName, int width, int height, int scaleOption, String outDirectory) throws Exception{
        m_width = width;
        m_height = height;
        m_scaleOption = scaleOption;
        m_file = fileName;
        
        int pos = m_file.lastIndexOf(".");
        
        if (pos == -1) throw new Exception("Invalid file name");
        
        m_type = m_file.substring(pos+1);
        
        
        File file = new File(m_file);
        if (!file.exists()) throw new Exception("File not exists. name=" + m_file);
        
        String thumbDir = file.getParent();
        String thumbFile = file.getName();
        

        if (outDirectory != null) {
            File dir = new File(outDirectory);
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }
        }
        
        if ( outDirectory == null)
            m_thumbFile = thumbDir + File.separator + "thumb-"+thumbFile;
        else 
            m_thumbFile = outDirectory + File.separator + "thumb-"+thumbFile;
        m_logger.debug("Outfile= " + m_thumbFile);
        
    }
    
    public void create(){
        
        long start = System.currentTimeMillis();
        ImageIcon image = new ImageIcon(m_file);

        m_logger.debug("Image loaded (" + m_file + ", width=" + image.getIconWidth() + ",height=" + image.getIconHeight());

        ImageIcon thumb = new ImageIcon(image.getImage().getScaledInstance(m_width, m_height, m_scaleOption));
        
        BufferedImage bi = new BufferedImage(thumb.getIconWidth(), thumb.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.drawImage(thumb.getImage(), 0, 0, null);
        try {
            ImageIO.write(bi, m_type, new File(m_thumbFile));
        }
        catch (IOException ioe) {
            System.out.println("Error occured saving thumbnail");
        }
        
        long end = System.currentTimeMillis();
        
        image.getImage().flush();
        thumb.getImage().flush();
        
        m_logger.debug(end-start);
    }
    
    public static void main(String[] args) throws Exception {
        new ThumbCreator("c:\\work_temp\\A.jpg", "c:\\work_temp\\out").create();
        new ThumbCreator("c:\\work_temp\\A.jpg", "c:\\work_temp\\out").create();
    }
    
    private static Logger m_logger = Logger.getLogger(ThumbCreator.class);
    
}
