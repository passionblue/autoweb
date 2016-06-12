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

import java.io.File;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
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

public class CreateDS2
{
    private static Logger m_logger = Logger.getLogger(CreateDS2.class);
    PropertiesLoader m_props;
    public CreateDS2(Map props, String appRoot, String appName, String type)
    {
        String templateFile = (String) props.get(type + ".templateFile");
        String outFileName = (String) props.get(type + ".outFile");
        String outFile = appRoot + "out/" + appName +"/" + outFileName;
        String outDir = appRoot + "out/" + appName;
        m_logger.debug("=================================================================================================");
        m_logger.debug(props);
        m_logger.debug("##= " + templateFile);
        m_logger.debug("##= " + outFile);
        m_logger.debug("##= " + outFileName);

        
        File dir = new File(outDir);
        dir.mkdir();
        
        try
        {
            Velocity.init("velocity/velocity.properties");

            VelocityContext context = new VelocityContext();

            for (Iterator iterator = props.keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                String newkey = key.replaceAll("\\.", "_");

                //m_logger.debug("Add :" + newkey + ">"+ props.get(key));
                context.put(newkey, props.get(key));
            }
            
            Template template =  null;

            try
            {
                template = Velocity.getTemplate(templateFile);
            }
            catch( ResourceNotFoundException rnfe )
            {
                m_logger.error("Example : error : cannot find template " + templateFile );
            }
            catch( ParseErrorException pee )
            {
                m_logger.error("Example : Syntax error in template " + templateFile + ":" + pee );
            }

            /*
             *  Now have the template engine process your template using the
             *  data placed into the context.  Think of it as a  'merge'
             *  of the template and the data to produce the output stream.
             */

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
            m_logger.debug(e);
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

    }
}


