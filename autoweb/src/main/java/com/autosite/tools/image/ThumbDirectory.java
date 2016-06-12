package com.autosite.tools.image;

import java.io.File;

public class ThumbDirectory {

    
    public ThumbDirectory(String directory, String outDirectory) throws Exception{
    
        File file = new File(directory);
    
        File files[] = file.listFiles();
        
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory()) continue;
            if (!isImageFile(f.getName())) continue;
            
            new ThumbCreator(f.getAbsolutePath(), outDirectory).create();
        }
    }
    
    private boolean isImageFile(String file){
        
        if (file.endsWith(".JPG")) return true;
        if (file.endsWith(".GIF")) return true;
        if (file.endsWith(".jpg")) return true;
        if (file.endsWith(".gif")) return true;
        
        return false;
        
    }
    
    public static void main(String[] args) throws Exception {
        new ThumbDirectory("c:\\myPictures\\014", "c:\\work_temp\\out2");
    }
    
}
