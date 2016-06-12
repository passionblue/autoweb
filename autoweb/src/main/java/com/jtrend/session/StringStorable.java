package com.jtrend.session;


public class StringStorable extends AbstractSessionStorable {

    private String m_value;
    
    public StringStorable(String val){
        super();
        m_value = val;
    }
    
    public String getValue() {
        return m_value;
    }

    public void setValue(String value) {
        m_value = value;
    }
    
    public String toString(){
        return m_value;
    }
}
