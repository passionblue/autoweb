package com.webtool.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FixMsgPersistService {

    private FixMsgDao fixMsgDao;

    public FixMsgDao getDbOutDao() {
        return fixMsgDao;
    }

    @Autowired
    public void setFixMsgDao(FixMsgDao personDao) {
        this.fixMsgDao = personDao;
    }

    
    
    
}
