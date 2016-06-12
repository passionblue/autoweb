package com.jtrend.concur.impl;

import com.jtrend.concur.WorkHandle;

public class SimpleWorkHandle implements WorkHandle {

    private String m_id;
    private int m_status;
    private int m_subStatus;
    private String m_message;
    
    public SimpleWorkHandle(String id) {
        m_id = id;
    }    
    
    public SimpleWorkHandle(String id, int status, int subStatus, String message) {
        m_id = id;
        m_status = status;
        m_subStatus = subStatus;
        m_message = message;
    }
    public SimpleWorkHandle(WorkHandle handle) {
        m_id = handle.getHandleId();
        m_status = handle.getStatus();
        m_subStatus = handle.getStatus();
        m_message = handle.getErrorMessage();
    }

    public String getHandleId() {
        return m_id;
    }

    public int getStatus() {
        // TODO Auto-generated method stub
        return m_status;
    }

    public String getErrorMessage() {
        // TODO Auto-generated method stub
        return m_message;
    }

    public String getId() {
        return m_id;
    }

    public void setId(String id) {
        m_id = id;
    }

    public int getSubStatus() {
        return m_subStatus;
    }

    public void setSubStatus(int subStatus) {
        m_subStatus = subStatus;
    }

    public String getMessage() {
        return m_message;
    }

    public void setMessage(String message) {
        m_message = message;
    }

    public void setStatus(int status) {
        m_status = status;
    }

    @Override
    public String toString() {
        return "ID=" + getHandleId() + "/status=" + getStatus();
    }
    
    
    
    
    
    

}
