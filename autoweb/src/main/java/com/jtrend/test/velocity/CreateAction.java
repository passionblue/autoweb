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


public class CreateAction
{
    PropertiesLoader m_props;
    public CreateAction(ActionProps props, String appRoot)
    {
        
        
        String templateFile = props.templateFile;
        String outFile = appRoot + "out/" + props.outFile;

        System.out.println("######################################################################");
        System.out.println("Template=" + templateFile);
        System.out.println("outFile=" + outFile);
        
        
        try
        {
            /*
             * setup
             */

            Velocity.init("velocity/velocity.properties");

            /*
             *  Make a context object and populate with the data.  This
             *  is where the Velocity engine gets the data to resolve the
             *  references (ex. $list) in the template
             */

            VelocityContext context = new VelocityContext();

            context.put("commonPackage", props.commonPackage);
            context.put("objClassName", props.objClassName);
            context.put("coreActionName", props.coreActionName);
            context.put("actionClassName", props.actionClassName);
            context.put("actionFormClassName", props.actionFormClassName);

            context.put("inputFields", props.inputFields);            

            /*
             *  get the Template object.  This is the parsed version of your
             *  template input file.  Note that getTemplate() can throw
             *   ResourceNotFoundException : if it doesn't find the template
             *   ParseErrorException : if there is something wrong with the VTL
             *   Exception : if something else goes wrong (this is generally
             *        indicative of as serious problem...)
             */

            Template template =  null;

            try
            {
                template = Velocity.getTemplate(templateFile);
            }
            catch( Exception rnfe )
            {
                System.out.println("Example : error : cannot find template " + templateFile );
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
            System.out.println(e);
        }
        
        
        templateFile = templateFile.replace(".vm", "form.vm");
        outFile = outFile.replace("Action.java", "Form.java");

        System.out.println("######################################################################");
        System.out.println("Template=" + templateFile);
        System.out.println("outFile=" + outFile);
        
        
        try
        {
            /*
             * setup
             */

            Velocity.init("velocity/velocity.properties");

            /*
             *  Make a context object and populate with the data.  This
             *  is where the Velocity engine gets the data to resolve the
             *  references (ex. $list) in the template
             */

            VelocityContext context = new VelocityContext();

            context.put("commonPackage", props.commonPackage);
            context.put("objClassName", props.objClassName);
            context.put("coreActionName", props.coreActionName);
            context.put("actionClassName", props.actionClassName);
            context.put("actionFormClassName", props.actionFormClassName);

            context.put("inputFields", props.inputFields);            

            /*
             *  get the Template object.  This is the parsed version of your
             *  template input file.  Note that getTemplate() can throw
             *   ResourceNotFoundException : if it doesn't find the template
             *   ParseErrorException : if there is something wrong with the VTL
             *   Exception : if something else goes wrong (this is generally
             *        indicative of as serious problem...)
             */

            Template template =  null;

            try
            {
                template = Velocity.getTemplate(templateFile);
            }
            catch( Exception rnfe )
            {
                System.out.println("Example : error : cannot find template " + templateFile );
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
            System.out.println(e);
        }
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

class ActionProps {
    String templateFile;
    String outFile;
    
    String commonPackage; //com.autosite
    String objClassName;   //Content
    String coreActionName; //
    String actionClassName; //
    String actionFormClassName;
    
    List   inputFields;
    
}



