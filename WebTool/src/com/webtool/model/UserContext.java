package com.webtool.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class UserContext {

    private User user;
    private long createdTime;
    private long logonTime;
    
    public UserContext() {
        System.out.println("############### user created ###########");
        
    }
    
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public long getCreatedTime() {
        return createdTime;
    }
    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }
    public long getLogonTime() {
        return logonTime;
    }
    public void setLogonTime(long logonTime) {
        this.logonTime = logonTime;
    }
    
    
    
    
}
