package com.autosite.struts.action;

public class ActionExtentException extends Exception{
    
    protected String m_forwardPage;
    protected Throwable m_cause;
    protected String m_errorCode;
    
    public ActionExtentException(String msg){
        super(msg);
    }
    
    public ActionExtentException(String msg, String page){
        super(msg);
        m_forwardPage = page;
    }
    public ActionExtentException(String msg, Throwable cause){
        super(msg, cause);
        m_cause = cause;
    }
    public ActionExtentException(String msg, Throwable cause, String page){
        super(msg,cause);
        m_cause = cause;
        m_forwardPage = page;
    }
    
    public void setForwardPage(String forwardPage) {
        m_forwardPage = forwardPage;
    }
    public String getForwardPage(){
        return m_forwardPage;
    }

    public Throwable getCause() {
        return m_cause;
    }

    public void setCause(Throwable cause) {
        m_cause = cause;
    }

    public String getErrorCode() {
        return m_errorCode;
    }

    public void setErrorCode(String errorCode) {
        m_errorCode = errorCode;
    }

    
    
    
}
