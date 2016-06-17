package com.webtool.solace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolaceController implements EntityMessageListener<String>{

    private static Logger m_logger = LoggerFactory.getLogger(SolaceController.class);

    //Default Constructor
    public SolaceController() {

    
    }

    
    public void startListen() {
        
    }
    
    public void stopListen() {
        
    }


    @Override
    public void onMessage(String entity) {
        
        
    }
}
