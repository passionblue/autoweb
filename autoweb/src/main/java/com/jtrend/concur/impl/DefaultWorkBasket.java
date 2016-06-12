package com.jtrend.concur.impl;

import com.jtrend.concur.WorkBasket;
import com.jtrend.concur.WorkHandle;

public class DefaultWorkBasket implements WorkBasket{

    private String m_result;
    private WorkHandle m_handle;
    
    
    public DefaultWorkBasket(String result, WorkHandle handle) {
        super();
        m_result = result;
        m_handle = handle;
    }

    public WorkHandle getHandle() {
        return m_handle;
    }

    public Object getResult() {
        return m_result;
    }
}
