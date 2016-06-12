package com.autosite.struts.xform.impl;

import java.util.Map;

import com.autosite.struts.xform.AutositeXform;

public class DefaultBasicXform  implements AutositeXform {

    String m_template;
    
    public String transform(Map data) {
        return m_template;
    }

    public String transform(Object data) {
        return m_template;
    }

    public void applyTemplate(String template) {
        m_template = template;
    }
    
}
