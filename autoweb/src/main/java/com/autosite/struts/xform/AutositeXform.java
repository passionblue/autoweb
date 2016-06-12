package com.autosite.struts.xform;

import java.util.Map;

public interface AutositeXform {

    String transform(Map data);
    String transform(Object data);
    
    void applyTemplate(String template);
}
