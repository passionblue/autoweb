package com.autosite.concur2;

import com.jtrend.concur2.DropInterface;
import com.jtrend.concur2.DropMessage;
import com.jtrend.concur2.DropProcessorFactory;
import com.jtrend.concur2.impl.DefaultDropMessageImpl;
import com.jtrend.concur2.test.Concur2Test;

public class AutositeEventHandlerManager {

    private static AutositeEventHandlerManager m_instance = new AutositeEventHandlerManager();

    private DropInterface m_dropInterface; 
    
    public static AutositeEventHandlerManager getInstance() {
        return m_instance;
    }

    private AutositeEventHandlerManager() {
        m_dropInterface = DropProcessorFactory.getInstance().createWith(new AutositeEventHandler());

    }
    
    public DropInterface getDropHandler(){
        return m_dropInterface;
    }
    
    
    public void dropEvent(String eventCategory, String eventName, String eventMessage) {
        
        DropMessage dropMessage  = new DefaultDropMessageImpl(eventName);
        
        dropMessage.addProperty("eventCategory", eventCategory);
        dropMessage.addProperty("eventName", eventName);
        dropMessage.addProperty("eventMessage", eventMessage);
    
        getDropHandler().dropAndGo(dropMessage);
    
    }
    
    
    public static void main(String[] args) {
        
        AutositeEventHandlerManager.getInstance().dropEvent("Notification", "Test", "Test event ocurred");
    }
}
