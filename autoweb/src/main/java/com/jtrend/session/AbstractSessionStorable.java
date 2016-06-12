/*
 * Created on Sep 10, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.jtrend.session;

import java.util.Date;

public abstract class AbstractSessionStorable implements SessionStorable {

    protected long m_createdTime;
    protected long m_expireTime;
    protected int  m_scope;
    
    protected AbstractSessionStorable() {
        m_createdTime = System.currentTimeMillis();
        m_expireTime = 0;
    }
    
    public Date getCreatedTime()
    {
        return new Date(m_createdTime); 
    }
    
    public long getExpireTime() {
        return m_expireTime;
    }

    public void setExpireTimeWithOffsetToCreation(long offsetFromCreationTime) {
        m_expireTime = m_createdTime + offsetFromCreationTime;
    }

    public void setExpireTimeWithOffsetToNow(long offsetFromCreationTime) {
        m_expireTime = System.currentTimeMillis() + offsetFromCreationTime;
    }
    
    public int getScope() {
        return m_scope;
    }
}
