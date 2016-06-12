package com.autosite.lab.mongo;

import java.nio.file.AccessMode;

import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;


@AccessType(AccessType.Type.PROPERTY)
abstract public class BaseData {
    

    @Id
    protected String id;

    abstract public String getLastName();
    abstract public String getFirstName();
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    
    
}
