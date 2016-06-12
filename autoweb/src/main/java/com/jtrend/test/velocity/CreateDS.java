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

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
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

public class CreateDS
{
    PropertiesLoader m_props;
    public CreateDS(DSProps dsProps, String appRoot)
    {
        
        String templateFile = dsProps.templateFile;
        String outFile = appRoot + "out/" + dsProps.outFile;
        
        try
        {
            Velocity.init("velocity/velocity.properties");

            VelocityContext context = new VelocityContext();

            context.put("ds_package", dsProps.ds_package);
            context.put("ds_obj", dsProps.ds_object);
            context.put("ds_class", dsProps.ds_class);
            //context.put("fk_name", "SiteId");
            context.put("list", getList(dsProps.listKeys));
            context.put("listOneToOneStringKey", getList(dsProps.listOneToOneStringKey));
            context.put("listStringKey", getList(dsProps.listStringKeys));

            Template template =  null;

            try
            {
                template = Velocity.getTemplate(templateFile);
            }
            catch( ResourceNotFoundException rnfe )
            {
                System.out.println("Example : error : cannot find template " + templateFile );
            }
            catch( ParseErrorException pee )
            {
                System.out.println("Example : Syntax error in template " + templateFile + ":" + pee );
            }

            /*
             *  Now have the template engine process your template using the
             *  data placed into the context.  Think of it as a  'merge'
             *  of the template and the data to produce the output stream.
             */

            BufferedWriter writer = writer = new BufferedWriter(
                new OutputStreamWriter(System.out));

            Writer w = FileUtil.getFileWriter(outFile);
            
            if ( template != null)
                template.merge(context, w);

            /*
             *  flush and cleanup
             */

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
        
        
        DSProps dsProps = new DSProps();
        dsProps.ds_package = "com.autosite.ds"; 
        dsProps.ds_class = "VelocityMainDS"; 
        dsProps.ds_object = "VelocityMain"; 
        dsProps.listKeys = "SiteId,PageId"; 
        dsProps.listStringKeys = "Data"; 
        dsProps.listOneToOneStringKey = ""; 
              
        dsProps.templateFile="velocity/templates/DS.vm";
        dsProps.outFile="velocity/out/Example.out";
        
        CreateDS t = new CreateDS(dsProps, "");
    }
}


class DSProps {
    String templateFile;
    String outFile;
    String ds_package;
    String ds_object;
    String ds_class;
    String listKeys;
    String listStringKeys;
    String listOneToOneStringKey;
    
}
