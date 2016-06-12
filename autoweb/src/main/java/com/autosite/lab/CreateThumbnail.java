package com.autosite.lab;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class CreateThumbnail {

    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;

    public static final String IMAGE_JPEG = "jpeg";
    public static final String IMAGE_JPG = "jpg";
    public static final String IMAGE_PNG = "png";
    public static final String IMAGE_GIF = "gif";

    private ImageIcon image;
    private ImageIcon thumb;

    public CreateThumbnail(Image image) {
        this.image = new ImageIcon(image);
    }

    public CreateThumbnail(String fileName) {
        image = new ImageIcon(fileName);
    }

    public Image getThumbnail(int size, int dir) {
        
        System.out.println("Original image width=" + image.getIconWidth());
        System.out.println("Original image height=" + image.getIconHeight());
        
        if (dir == HORIZONTAL) {
            thumb = new ImageIcon(image.getImage().getScaledInstance(size, -1, Image.SCALE_SMOOTH));
        }
        else {
            thumb = new ImageIcon(image.getImage().getScaledInstance(-1, size, Image.SCALE_SMOOTH));
        }
        return thumb.getImage();
    }

    public Image getThumbnail(int size, int dir, int scale) {
        if (dir == HORIZONTAL) {
            thumb = new ImageIcon(image.getImage().getScaledInstance(size, -1, scale));
        }
        else {
            thumb = new ImageIcon(image.getImage().getScaledInstance(-1, size, scale));
        }
        return thumb.getImage();
    }

    public void drawMarkString(String string, Graphics g){
        
        Color orgColor = g.getColor();
        
        System.out.println(Color.ORANGE.getBlue());
        System.out.println(Color.ORANGE.getRGB());
        System.out.println(Color.ORANGE.getRed());
        System.out.println(Color.ORANGE.getGreen());
        g.setColor(new Color(255,255,255,50)); //0 trans  255 no trans
        Font font = new Font("verdana", Font.PLAIN, 25);
        g.setFont(font);
        
//        g.set(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));  
        
        g.drawString(string, 30, 30);
        
        g.setColor(orgColor);
        
    }
    
    public void saveThumbnail(File file, String imageType) {
        if (thumb != null) {
            BufferedImage bi = new BufferedImage(thumb.getIconWidth(), thumb.getIconHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.getGraphics();
            g.drawImage(thumb.getImage(), 0, 0, null);
            drawMarkString("This is Test",g); 
            try {
                ImageIO.write(bi, imageType, file);
            }
            catch (IOException ioe) {
                System.out.println("Error occured saving thumbnail");
            }
        }
        else {
            System.out.println("Thumbnail has not yet been created");
        }
    }

    public static void main(String[] args) {
        CreateThumbnail ct = new CreateThumbnail("c:\\work_temp\\A.jpg");
        ct.getThumbnail(100, CreateThumbnail.HORIZONTAL);
        ct.saveThumbnail(new File("c:\\work_temp\\gar.jpg"), CreateThumbnail.IMAGE_JPG);

        long start = System.currentTimeMillis();
        CreateThumbnail ct1 = new CreateThumbnail("c:\\work_temp\\A.jpg");
        ct1.getThumbnail(100, CreateThumbnail.HORIZONTAL);
        ct1.saveThumbnail(new File("c:\\work_temp\\A-T1.jpg"), CreateThumbnail.IMAGE_JPG);
        long end = System.currentTimeMillis();
        System.out.println(end-start);

        long start2 = System.currentTimeMillis();
        CreateThumbnail ct12 = new CreateThumbnail("c:\\work_temp\\A.jpg");
        ct12.getThumbnail(300, CreateThumbnail.HORIZONTAL);
        ct12.saveThumbnail(new File("c:\\work_temp\\A-T2.jpg"), CreateThumbnail.IMAGE_JPG);
        long end2 = System.currentTimeMillis();
        System.out.println(end2-start2);
    
    }

}
