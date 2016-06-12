package com.autosite.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.autosite.db.Poll;
import com.autosite.db.PollAnswer;
import com.autosite.ds.PollAnswerDS;
import com.autosite.ds.PollDS;
import com.autosite.util.CookieUtil;
import com.autosite.util.PollUtil;
import com.autosite.util.poll.PollWidgetUtil;
import com.jtrend.util.FileUtil;
import com.jtrend.util.WebParamUtil;

public class PollScriptsServlet  extends AbstractAutositeServlet {
    
    protected boolean m_debug = AutositeGlobals.m_debug;
    protected String m_importedScripts = "";
    protected String m_importedPollScripts = "";
    public PollScriptsServlet() 
    {
        try {
            m_importedScripts = FileUtil.loadCodesToString("./inline_script.txt");
            m_importedPollScripts = FileUtil.loadCodesToString("./inline_poll_script.txt");
            m_logger.debug(m_importedScripts);
        }
        catch (Exception e) {
            m_logger.error(e.getMessage(),e);
            m_importedScripts = null;
        }
        
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        m_logger.debug("################################ START ##########################################################################");
        HttpSession session = request.getSession();

        printHeaders(request);
 
        String url = request.getRequestURI();
        m_logger.debug(url);

        String parts[] = url.split("/");

        m_logger.debug("num parts=" + parts.length);
        
        if ( parts.length < 1 ) {
            m_logger.info("length of parts is too short. returning as error");
        }

        if ( !parts[1].equals("pollscr") ){
        }

        int pos = parts[2].indexOf(".poll");
        
        if (pos < 0) return;
        
        String pollId = parts[2].substring(0, pos);
        
        m_logger.debug("PollId=" + pollId);
        
        Poll poll = PollDS.getInstance().getObjectBySerial(pollId);
        
        if ( poll == null) {
            
            long id = WebParamUtil.getLongValue(pollId);
            poll = PollDS.getInstance().getById(id);
        }
        
        PrintWriter out = response.getWriter();
        String output = "";

        if ( poll != null) {
            m_logger.info("Poll found=" + poll.getQuestion());
            
            if ( hasRequestValue(request, "dispType", "simple") || isMissing(request.getParameter("dispType"))){
                m_logger.debug("sending SIMPLE type of display");
                output = getSimple(poll, request);
            } else if ( hasRequestValue(request, "dispType", "scriptlet") ){

                m_logger.debug("sending SCRIPTLET type of display");
                if ( m_importedScripts == null) {
                    m_logger.debug("received SCRIPTLET request but previously failed to load scripts required for this request. So returning simple diplay");
                    output = getSimple(poll, request);
                } else {
                    output = getScriptlet(poll, request);
                }
            }
            
        } else {
            m_logger.info("Poll Not found=" + pollId);
            output = getError();
        }
        
        out.print(output);

        m_logger.debug(output);
        
        m_logger.debug("################################ END ##########################################################################");
    }
    
    private String getError(){
        StringBuffer buf = new StringBuffer();
        
        buf.append("document.write('");
        buf.append("<table>");
        buf.append("<tr><td>");
        buf.append("</td></tr>");
        buf.append("</table>");
        buf.append("');");
        
        return buf.toString();
        
    }
    
    

    // Working for simple version which would make a click to poll site, instead of self- serving. 
    private String getSimple(Poll poll, HttpServletRequest request) {
        StringBuffer buf = new StringBuffer();
        String hostName = request.getHeader("host");
        
        String backgroundStyle      =" background-color:black; padding: 10px 0 10px 0;";
        String tableStyle           =" border: 5px solid #F5A236; background-color: #CF2B19; border-collapse: collapse; font: normal normal normal 12px verdana";
        
        String titleTdStyle         =" border-bottom: 5px solid #F5A236; font: normal normal bold 12px verdana;color: #F5A236; padding : 5 5 5 5; ";
        String tdStyle              =" border: 1px solid #F5A236; font: normal normal normal 12px verdana;;color: #F5A236; padding : 5 0 5 0; ";
        
        String voteAnchorStyle      = "font: normal normal bold 12px verdana; padding: 5 5 5 5; text-decoration: none; margin: 0 0 5 0; color: #CF2B19; ";
        String resultAnchorStyle    = "font: normal normal bold 12px verdana; padding: 5 5 5 5; text-decoration: none; margin: 0 0 5 0; color: #CF2B19; ";
        
        buf.append("document.write('");
        buf.append("<div style=\""+backgroundStyle+"\">");
        buf.append("<form name=\"pollVoteForm"+poll.getId()+"\" method=\"post\" action=\"http://"+hostName+"/pollVoteAction.html\">");
        buf.append("<table width=\"100%\"  style=\""+tableStyle+"\">");
        buf.append("<tr><td style=\""+titleTdStyle+"\">");
        buf.append(poll.getQuestion());
        buf.append("</td></tr>");
        
        List answers = PollAnswerDS.getInstance().getByPollId(poll.getId());
        for (Iterator iterator = answers.iterator(); iterator.hasNext();) {
            PollAnswer ans = (PollAnswer) iterator.next();    
            
            buf.append("<tr><td style=\""+tdStyle+"\">");
            buf.append("<input type=\"radio\" name=\"answer\"  value=\""+ans.getAnswerNum()+"\"/>");
            buf.append(ans.getText());
            buf.append("</td></tr>");
        }        

        
        buf.append("</table>");
        buf.append("<INPUT TYPE=\"hidden\" NAME=\"add\" value=\"true\">");
        buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pollId\" value=\""+poll.getId()+"\">");
        buf.append("<input type=\"hidden\" name=\"fwdTo\" value=\"/t_poll_result_single.html?prv_serial="+poll.getSerial()+"\">");
        
        buf.append("</form>");
        
        buf.append("<a style=\""+ voteAnchorStyle +"\" href=\"javascript:document.pollVoteForm"+poll.getId()+".submit();\" >VOTE</a>");
        buf.append("<a style=\""+ resultAnchorStyle +"\" href=\"http://"+hostName+"/t_poll_result_single.html?prv_serial="+poll.getSerial()+"\" >View Result</a>");
        buf.append("</div>"); 
        buf.append("');");
         
        return buf.toString();
    }
    
    // Contents from this request would be embedded into javascript clause. The caller would look like 
    // <script type="text/javascript" src="/blockListAction.html?ajaxRequest=true&ajaxOut=getscriptform"></script>
    
    private String getScriptlet(Poll poll, HttpServletRequest request) {
        StringBuffer buf = new StringBuffer();
        String hostName = request.getHeader("host");
        
        boolean voted = PollUtil.votedAlready(poll , null, request.getRemoteAddr(), CookieUtil.getRpcId(request));
        boolean noScript = WebParamUtil.getBooleanValue(request.getParameter("noScript"));
        
        if (!noScript && !voted){
            buf.append("/*=== scripts contents from common scripts =============================================================== */\n");
            buf.append(m_importedScripts);
            buf.append("/*=== scripts contents from poll scripts================================================================== */\n");
            buf.append(m_importedPollScripts);
            buf.append("\n");
        }
        
        buf.append("document.write('");

        if (voted){
            m_logger.info("User has no more vote on " + poll.getId() + " ip " +request.getRemoteAddr() + " rpc=" + CookieUtil.getRpcId(request) );
        }
        
        String constructResults = PollWidgetUtil.constructPollWidgetHtml(poll, hostName, voted);
        buf.append(constructResults);
        buf.append("');");

        m_logger.debug("CONSTRUCTED=" + constructResults);

        return buf.toString();
    }    
    
    // Saved for backup before migrate to PollWidgetUtil
    private String getSimple_working(Poll poll, HttpServletRequest request) {
        StringBuffer buf = new StringBuffer();
        String hostName = request.getHeader("host");
        
        String backgroundStyle      =" background-color:black; padding: 10px 0 10px 0;";
        String tableStyle           =" border: 5px solid #F5A236; background-color: #CF2B19; border-collapse: collapse; font: normal normal normal 12px verdana";
        
        String titleTdStyle         =" border-bottom: 5px solid #F5A236; font: normal normal bold 12px verdana;color: #F5A236; padding : 5 5 5 5; ";
        String tdStyle              =" border: 1px solid #F5A236; font: normal normal normal 12px verdana;;color: #F5A236; padding : 5 0 5 0; ";
        
        String voteAnchorStyle      = "font: normal normal bold 12px verdana; padding: 5 5 5 5; text-decoration: none; margin: 0 0 5 0; color: #CF2B19; ";
        String resultAnchorStyle    = "font: normal normal bold 12px verdana; padding: 5 5 5 5; text-decoration: none; margin: 0 0 5 0; color: #CF2B19; ";
        
        buf.append("document.write('");
        buf.append("<div style=\""+backgroundStyle+"\">");
        buf.append("<form name=\"pollVoteForm"+poll.getId()+"\" method=\"post\" action=\"http://"+hostName+"/pollVoteAction.html\">");
        buf.append("<table width=\"100%\"  style=\""+tableStyle+"\">");
        buf.append("<tr><td style=\""+titleTdStyle+"\">");
        buf.append(poll.getQuestion());
        buf.append("</td></tr>");
        
        List answers = PollAnswerDS.getInstance().getByPollId(poll.getId());
        for (Iterator iterator = answers.iterator(); iterator.hasNext();) {
            PollAnswer ans = (PollAnswer) iterator.next();    
            
            buf.append("<tr><td style=\""+tdStyle+"\">");
            buf.append("<input type=\"radio\" name=\"answer\"  value=\""+ans.getAnswerNum()+"\"/>");
            buf.append(ans.getText());
            buf.append("</td></tr>");
        }        

        
        buf.append("</table>");
        buf.append("<INPUT TYPE=\"hidden\" NAME=\"add\" value=\"true\">");
        buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pollId\" value=\""+poll.getId()+"\">");
        buf.append("<input type=\"hidden\" name=\"fwdTo\" value=\"/t_poll_result_single.html?prv_serial="+poll.getSerial()+"\">");
        
        buf.append("</form>");
        
        buf.append("<a style=\""+ voteAnchorStyle +"\" href=\"javascript:document.pollVoteForm"+poll.getId()+".submit();\" >VOTE</a>");
        buf.append("<a style=\""+ resultAnchorStyle +"\" href=\"http://"+hostName+"/t_poll_result_single.html?prv_serial="+poll.getSerial()+"\" >View Result</a>");
        buf.append("</div>"); 
        buf.append("');");
         
        return buf.toString();
    }
    
    private static Logger m_logger = Logger.getLogger(PollScriptsServlet.class);

    
}
