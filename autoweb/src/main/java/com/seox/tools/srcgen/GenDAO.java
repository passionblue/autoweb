/*
 * Created on Nov 18, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.tools.srcgen;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;


class GenDAO
{
    public GenDAO()
    {
        try
        {
            /*
             * setup
             */

            Velocity.init("velocity.properties");
            
            /*
             *  Make a context object and populate with the data.  This 
             *  is where the Velocity engine gets the data to resolve the
             *  references (ex. $list) in the template
             */

            VelocityContext context = new VelocityContext();
            context.put("packageName", "com");
            context.put("className", "User");
            
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
                template = Velocity.getTemplate("DAO.vm");
            }
            catch( ResourceNotFoundException rnfe )
            {
                System.out.println("Example : error : cannot find template ");
            }
            catch( ParseErrorException pee )
            {
                System.out.println("Example : Syntax error in template :" + pee );
            }

            /*
             *  Now have the template engine process your template using the
             *  data placed into the context.  Think of it as a  'merge' 
             *  of the template and the data to produce the output stream.
             */

            BufferedWriter writer =  new BufferedWriter(
                new OutputStreamWriter(System.out));

            if ( template != null)
                template.merge(context, writer);

            /*
             *  flush and cleanup
             */

            writer.flush();
            writer.close();
        }
        catch( Exception e )
        {
            System.out.println(e);
        }
    }

    public ArrayList getNames()
    {
        ArrayList list = new ArrayList();

        list.add("ArrayList element 1");
        list.add("ArrayList element 2");
        list.add("ArrayList element 3");
        list.add("ArrayList element 4");

        return list;
    }

    public static void main(String[] args)
    {
        GenDAO t = new GenDAO();
    }
}
