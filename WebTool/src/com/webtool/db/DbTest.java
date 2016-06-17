package com.webtool.db;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DbTest {

    private static Logger m_logger = LoggerFactory.getLogger(DbTest.class);

    //Default Constructor
    public DbTest() {
    }
    
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config-hsql.xml");
        
        //fixMsgPersistService was not defined in the config file but the class has Component annontation
        FixMsgPersistService fixMsgPersistService = (FixMsgPersistService) context.getBean("fixMsgPersistService"); 
        
        FIXMsgEntry entry = new FIXMsgEntry();
        
        entry.setFix("XX");
        entry.setTime(System.currentTimeMillis());
        entry.setType("S");
        
        fixMsgPersistService.getDbOutDao().insert(entry);
        
        List<FIXMsgEntry> all = fixMsgPersistService.getDbOutDao().selectAll();
        
        for (FIXMsgEntry fixMsgEntry : all) {
            m_logger.debug(fixMsgEntry.toString());
        }
        
    }

}
