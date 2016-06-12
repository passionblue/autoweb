package com.jtrend.test.velocity;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.jtrend.util.FileUtil;
import com.seox.util.PropertiesLoader;

/**
 * This class is a simple demonstration of how the Velocity Template Engine
 * can be used in a standalone application.
 *
 * @author <a href="mailto:jvanzyl@apache.org">Jason van Zyl</a>
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: Example.java 463298 2006-10-12 16:10:32Z henning $
 */

public class CreateForm
{
    PropertiesLoader m_props;
    public CreateForm(FormProps formProps, String appRoot)
    {
        //m_props = new PropertiesLoader("velocity/template.properties");
        //System.out.println(m_props.getProperty("template.list"));
        
        String templateFile = formProps.templateFile;
        String outFile = appRoot + "out/" + formProps.outFile;
        
        try
        {
            Velocity.init("velocity/velocity.properties");
            VelocityContext context = new VelocityContext();

            context.put("importPackages", formProps.importPackages);
            context.put("headCodes", formProps.headCodes);
            context.put("formName", formProps.formName);
            context.put("actionName", formProps.actionName);
            context.put("border", formProps.border);
            context.put("width", formProps.width);
            context.put("bgcolor", formProps.bgcolor);
            context.put("inputFields", formProps.inputFields);            
            String objClassName = AutoGen.convertDbFieldToJava(formProps.dbTable);
            context.put("objClassName", objClassName);
            
            Template template =  null;

            try
            {
                template = Velocity.getTemplate(templateFile);
            }
            catch( Exception rnfe )
            {
                System.out.println("Example : error : cannot find template " + templateFile );
            }

            Writer w = FileUtil.getFileWriter(outFile);
            
            if ( template != null)
                template.merge(context, w);
            w.flush();
            w.close();
        }
        catch( Exception e )
        {
            System.out.println(e);
        }
        
        
        //##############################################################################
        // Create Edit JSP form
        //##############################################################################
        
        templateFile = templateFile.replace(".vm", "edit.vm");
        outFile = outFile.replace(".jsp", "Edit.jsp");
        
        try
        {
            Velocity.init("velocity/velocity.properties");
            VelocityContext context = new VelocityContext();

            context.put("importPackages", formProps.importPackages);
            context.put("headCodes", formProps.headCodes);
            context.put("formName", formProps.formName);
            context.put("actionName", formProps.actionName);
            context.put("border", formProps.border);
            context.put("width", formProps.width);
            context.put("bgcolor", formProps.bgcolor);
            context.put("inputFields", formProps.inputFields);       
            
            String objClassName = AutoGen.convertDbFieldToJava(formProps.dbTable);
            context.put("objClassName", objClassName);
            
            Template template =  null;

            try
            {
                template = Velocity.getTemplate(templateFile);
            }
            catch( Exception rnfe )
            {
                System.out.println("Example : error : cannot find template " + templateFile );
            }

            Writer w = FileUtil.getFileWriter(outFile);
            
            if ( template != null)
                template.merge(context, w);
            w.flush();
            w.close();
        }
        catch( Exception e )
        {
            System.out.println(e);
        }
        
        
        
        
        
        
        
        
        
        
        
    }

    public ArrayList getNames()
    {
        ArrayList list = new ArrayList();

        list.add("SiteId");
        list.add("PageId");

        return list;
    }
    
    public ArrayList getListOneToOneStringKey()
    {
        ArrayList list = new ArrayList();

        list.add("Data");

        return list;
    }
    
    
    public ArrayList getList(String list)
    {
        StringTokenizer tokenizer = new StringTokenizer(list, ", ");
        
        ArrayList ret = new ArrayList();
        
        while(tokenizer.hasMoreTokens()){
            ret.add(tokenizer.nextToken());
        }
        
        return ret;
    }

    public static void main(String[] args)
    {
        
//        context.put("ds_package", "com.autosite.ds");
//        context.put("ds_obj", "VelocityMain");
//        context.put("ds_class", "VelocityMainDS");
//        context.put("list", getNames());
//        context.put("listOneToOneStringKey", getListOneToOneStringKey());
//        context.put("listStringKey", getListOneToOneStringKey());
        
        
        FormProps dsProps = new FormProps();
        dsProps.importPackages = "com.jtrend.session.SessionContext,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"; 
        dsProps.headCodes = "   Site site = SiteDS.getInstance().registerSite(request.getServerName());\n     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());"; 
        dsProps.formName = "GenTestForm";
        dsProps.actionName = "action";
              
        dsProps.templateFile="velocity/templates/jspform.vm";
        dsProps.outFile="velocity/out/Form.jsp";
        
        ArrayList list = new ArrayList();
        Map map = new HashMap();

        map.put("display", "Key");
        map.put("prop", "key");
        map.put("value", "key");
        map.put("size", "100");
        list.add(map);

        map = new HashMap();
        map.put("display", "Width");
        map.put("prop", "width");
        map.put("value", "width");
        map.put("size", "80");
        list.add(map);

        map = new HashMap();
        map.put("display", "Height");
        map.put("prop", "height");
        map.put("value", "height");
        map.put("size", "100");
        list.add(map);
        
        dsProps.inputFields = list;
        
        
        CreateForm t = new CreateForm(dsProps, "");
    }
}

class FormProps {
    String templateFile;
    String outFile;
    
    String bgcolor = "#99dae8";
    String border = "0";
    String width ="100%";
    
    String importPackages;
    String headCodes;
    String formName;
    String actionName;
    String dbTable;
    List   inputFields;
    
}

class ConfigHolder {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}


