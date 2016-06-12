package com.autosite.db;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public interface AutositeDataObject {
    public long getId();
    public String getKey();
    public long getSiteId();
    public void setSiteId(long id);
}
