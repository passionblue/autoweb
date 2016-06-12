package com.jtrend.session;


public class GenericStorable extends AbstractSessionStorable {

    private Object m_object;
    
    public GenericStorable(String val){
        super();
        m_object = val;
    }
    
    public Object getValue() {
        return m_object;
    }

    public void setValue(Object value) {
        m_object = value;
    }
    
    public String toString(){
        if ( m_object != null)
            return m_object.toString();
        return null;
    }
}
