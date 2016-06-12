package com.autosite.session.devicesynch;

public class SynchTrackingUtil {

    public static boolean isCreateAction(String scope){
        if ( scope == null) return false;
        return scope.endsWith("-create");
    }

    public static boolean isUpdateAction(String scope){
        if ( scope == null) return false;
        return scope.endsWith("-update");
    }
 
    public static boolean isDeleteAction(String scope){
        if ( scope == null) return false;
        return scope.endsWith("-delete");
    }
}
