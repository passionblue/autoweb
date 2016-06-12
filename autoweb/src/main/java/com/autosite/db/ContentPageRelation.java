package com.autosite.db;

import java.sql.Timestamp;

/**
 * ContentPageRelation entity. @author MyEclipse Persistence Tools
 */

public class ContentPageRelation extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long contentId;
    private long pageId;
    private Timestamp timeCreated;

    // Constructors

    /** default constructor */
    public ContentPageRelation() {
    }

    /** full constructor */
    public ContentPageRelation(long contentId, long pageId, Timestamp timeCreated) {
        this.contentId = contentId;
        this.pageId = pageId;
        this.timeCreated = timeCreated;
    }

    // Property accessors

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContentId() {
        return this.contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public long getPageId() {
        return this.pageId;
    }

    public void setPageId(long pageId) {
        this.pageId = pageId;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    @Override
    public long getSiteId() {
        // TODO Auto-generated method stub
        return 0;
    }

}
