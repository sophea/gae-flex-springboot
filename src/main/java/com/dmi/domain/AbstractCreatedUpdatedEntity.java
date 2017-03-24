package com.dmi.domain;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author os
 */
public class AbstractCreatedUpdatedEntity implements Serializable {
    
    /** Column name for field updatedBy is "updatedBy" */
    public static final  String COLUMN_NAME_UPDATEDBY = "updatedBy";
    /** Column name for field updatedDate is "updatedDate" */
    public static final  String COLUMN_NAME_UPDATEDDATE = "updatedDate";
    
    /** Column name for field createdBy is "createdBy" */
    public static final  String COLUMN_NAME_CREATEDBY = "createdBy";
    /** Column name for field createdDate is "createdDate" */
    public static final  String COLUMN_NAME_CREATEDDATE = "createdDate";
    
    private String createdBy;
    
    private Date createdDate;
    
    private String updatedBy;
    
    private Date updatedDate;
    
    
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    @Override
    public String toString() {
        return String.format("%s{createdBy:%s, createdDate:%s, updatedDate:%s, %s}",
                getClass().getSimpleName(), createdBy, createdDate, updatedDate, subString());
    }
    
    public String subString() {
        return "";
    }
}
