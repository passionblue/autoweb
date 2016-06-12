package com.autosite.struts.xform.impl;

import java.util.Map;

import com.autosite.struts.xform.AutositeXform;

public class DefaultErrorXform implements AutositeXform {

    public String transform(Map data) {
        // TODO Auto-generated method stub
        return null;
    }

    public String transform(Object data) {
        return "<div> Error </div>";
    }

    public void applyTemplate(String template) {
        // TODO Auto-generated method stub
        
    }

}
