package com.autosite.struts.action;

public class ActionExtentDuplicateAttemptException extends Exception{
    
    protected String m_forwardPage;
    protected Throwable m_cause;
    
    public ActionExtentDuplicateAttemptException(String msg){
        super(msg);
    }
    
    public ActionExtentDuplicateAttemptException(String msg, String page){
        super(msg);
        m_forwardPage = page;
    }
    public ActionExtentDuplicateAttemptException(String msg, Throwable cause){
        super(msg, cause);
        m_cause = cause;
    }
    public ActionExtentDuplicateAttemptException(String msg, Throwable cause, String page){
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

}
