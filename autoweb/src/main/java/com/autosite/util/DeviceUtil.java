package com.autosite.util;

import org.apache.commons.lang3.StringUtils;

public class DeviceUtil {

    public static String formatIphoneDeviceIdForNotification(String deviceId){
        
        if (deviceId == null) return null;
        
        String retStr = deviceId.trim().replaceAll(" ", "");
        
        retStr = StringUtils.removeEnd(retStr, ">");
        retStr = StringUtils.removeStart(retStr, "<");
        
        return retStr;
        
    }
}
