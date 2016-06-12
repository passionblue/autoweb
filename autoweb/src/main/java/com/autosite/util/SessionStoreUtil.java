package com.autosite.util;

import javax.servlet.http.HttpSession;

import com.autosite.session.SiteRegStore;
import com.autosite.session.storable.AutositeSessionObjectStore;

public class SessionStoreUtil {
    
    public static AutositeSessionObjectStore createAndSave(HttpSession session, String key) {
        return createAndSave(session, key, false);
    }
    
    public static AutositeSessionObjectStore createAndSave(HttpSession session, String key, boolean overwriteExisting) {

        AutositeSessionObjectStore ret = null;
        if (session.getAttribute(key) == null || overwriteExisting) {
            ret = newObject(key);
            session.removeAttribute(key);
            session.setAttribute(key, ret);
        } else {
            ret = (AutositeSessionObjectStore) session.getAttribute(key);
        }

        return ret;
    }

    public static AutositeSessionObjectStore getObjectStore(HttpSession session, String key){
        AutositeSessionObjectStore ret = null;
        ret = (AutositeSessionObjectStore) session.getAttribute(key);
        return ret;
    }
    
    private static AutositeSessionObjectStore newObject(String key) {

        if (key == null)
            return null;

        if (key.equals(SiteRegStore.getSessionKey())) {
            return new SiteRegStore();
        }

        return null;
    }
}
