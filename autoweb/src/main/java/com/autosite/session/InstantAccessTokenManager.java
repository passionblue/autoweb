package com.autosite.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.autosite.db.AutositeUser;

public class InstantAccessTokenManager {

    private static InstantAccessTokenManager m_instance = new InstantAccessTokenManager();

    public static InstantAccessTokenManager getInstance() {
        return m_instance;
    }

    private Map <String, InstantAccessToken> m_tokenPool = new ConcurrentHashMap<String, InstantAccessToken>();
    
    
    private InstantAccessTokenManager() {

    }
    
    
    
    
    public Map<String, InstantAccessToken> getTokenPool() {
        return m_tokenPool;
    }


    public InstantAccessToken findAndAcceptToken(String sessionId, String url, String  tokenId){
        if ( tokenId == null) return null;
        InstantAccessToken tok = m_tokenPool.get(tokenId);
        
        if ( tok != null) {
            if ( tok.getUseableSiteUrls().contains(url)){
                tok.getAcceptedSessionIds().add(sessionId);
                return tok;
            }
        }
        
        return null;
    }
    
    public static InstantAccessToken createLoginToken(String siteUrl, String sessionId, String targetSiteUrl, AutositeUser user, long life){
        InstantAccessToken ret = new InstantAccessToken(sessionId, targetSiteUrl, InstantAccessToken.ACCESS_TYPE_LOGIN  );
        
        ret.setLife(life);
        ret.setAccessUser(user);
        ret.getUseableSiteUrls().add(targetSiteUrl);
        
        getInstance().getTokenPool().put(ret.getTokenId(), ret);
        return ret;
    }
}
