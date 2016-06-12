package com.autosite.util.poll;

import java.util.Iterator;
import java.util.List;

import com.autosite.db.Poll;
import com.autosite.db.PollAnswer;
import com.autosite.db.PollConfig;
import com.autosite.ds.PollAnswerDS;
import com.autosite.ds.PollConfigDS;
import com.autosite.util.PollUtil;
import com.jtrend.util.WebUtil;

public class PollWidgetUtil {


    public static final String backgroundColor      = "#CF2B19";
    public static final String borderColor          = "#F5A236";
    public static final String textColor            = "#F5A236";
    public static final String linkTextColor        = "#CF2B19";

    public static final String tableStyle           = "border: 5px solid "+borderColor+"; background-color: #CF2B19; border-collapse: collapse; font: normal normal normal 12px verdana";
    public static final String titleTdStyle         = "border-bottom: 5px solid "+borderColor+"; font: normal normal bold 12px verdana; color: "+textColor+"; padding : 5 5 5 5; ";
    public static final String tdStyle              = "border: 1px solid "+borderColor+"; font: normal normal normal 12px verdana; color:  "+textColor+"; padding : 5 0 5 0; ";
    public static final String linkStyle            = "font: normal normal bold 12px verdana; padding: 5px 5px 5px 0px; text-decoration: none; margin: 0 0 5 0; color: "+linkTextColor+"; ";
    public static final String linkStyleMid         = "font: normal normal bold 12px verdana; padding: 5px 5px 5px 0px; text-decoration: none; margin: 0 0 5 0; color: "+linkTextColor+"; ";
    public static final String linkStyleSmallGray   = "font: normal normal bold 10px verdana; padding: 5px 5px 5px 0px; text-decoration: none; margin: 0 0 5 0; color: gray; ";

    public static final String hoverColor           = "black";
    public static final String defaultColor         = "#CF2B19";

    public static String constructPollWidgetHtml(Poll poll, String hostName, boolean resultsOnly ){

        StringBuffer buf = new StringBuffer();
        
        PollConfig pollConfig = PollConfigDS.getInstance().getObjectByPollId(poll.getId());
        
        
        if (resultsOnly) {

            PollResult pollResult = PollResult.getPollResult(poll.getId());
            List results = pollResult.getResultsByCounts(); 
            
            buf.append("<div style=\"background-color:white; padding: 0 0 10px 0;\" id=\"pollVoteFormFrame"+poll.getId()+"\" >");
            buf.append("<table width=\"100%\"  style=\""+PollUtil.tableStyle+"\">");
            buf.append("<tr><td style=\""+PollUtil.titleTdStyle+"\">");
            buf.append(poll.getQuestion());
            buf.append("</td></tr>");

            PollResult.ResultEntry ownAnsEntry = null;

            for(Iterator iter = results.iterator(); iter.hasNext();){
                PollResult.ResultEntry entry = (PollResult.ResultEntry) iter.next();    
                
                if ( WebUtil.isTrue(entry.getAnswerObj().getOwnAnswer())){
                    ownAnsEntry = entry;
                    continue;
                }
                
                
                buf.append("<tr><td style=\""+tdStyle+"\">");
                buf.append("<div style=\"font: normal normal normal 10px verdana\"> ").append( entry.getAnswerObj().getText()).append("</div>");
                buf.append("<div style=\"font: normal normal bold 10px verdana; padding:5px 0px 0px 10px;\"> ").append( entry.getCount()).append("</div>");
                buf.append("</td></tr>");
            }

            
            if (WebUtil.isTrue(poll.getAllowOwnAnswer()) && ownAnsEntry!= null){
                buf.append("<tr><td style=\""+tdStyle+"\">");
                buf.append("<div style=\"font: normal normal normal 10px verdana\"> ").append( ownAnsEntry.getAnswerObj().getText()).append("</div>");
                buf.append("<div style=\"font: normal normal bold 10px verdana; padding:5px 0px 0px 10px;\"> ").append( ownAnsEntry.getCount()).append("</div>");
                buf.append("</td></tr>");
            }
            
            
            buf.append("</table>");
            buf.append("<div style=\"font: normal normal normal 10px verdana;color: gray;\">").append("Total Votes:").append( pollResult.getNumVotes()).append("</div>");
            buf.append("<a style=\""+ PollUtil.linkStyleMid +"\" href=\"http://"+hostName+"/t_poll_result_single.html?prv_serial="+poll.getSerial()+"\" onMouseOver=\"this.style.color=\\'"+hoverColor+"\\';\" onMouseOut=\"this.style.color=\\'"+linkTextColor+"\\';\">Comments</a>");
            buf.append("<a style=\""+ PollUtil.linkStyleMid +"\" href=\"javascript:backToPoll(").append(poll.getId()).append(");\" onMouseOver=\"this.style.color=\\'"+hoverColor+"\\';\" onMouseOut=\"this.style.color=\\'"+linkTextColor+"\\';\">back to Poll</a><br><br>");
            buf.append("<a style=\""+ PollUtil.linkStyleSmallGray +"\" href=\"http://"+hostName+"\" >More Polls at Zapoll.com</a>");
            buf.append("</div>"); 
            
        } else {
            
            buf.append("<div style=\"background-color:white; padding: 0 0 10px 0;\" id=\"pollVoteFormFrame"+poll.getId()+"\" >");
            buf.append("<form name=\"pollVoteForm"+poll.getId()+"\" method=\"post\" action=\"http://"+hostName+"/pollVoteAction.html\" id=\"pollVoteForm"+poll.getId()+"\" >");
            buf.append("<table width=\"100%\"  style=\""+PollUtil.tableStyle+"\">");
            buf.append("<tr><td style=\""+ PollUtil.titleTdStyle+"\">");
            buf.append(poll.getQuestion());
            buf.append("</td></tr>");
            
            List answers = PollAnswerDS.getInstance().getByPollId(poll.getId());
            PollAnswer ownAns = null;
            for (Iterator iterator = answers.iterator(); iterator.hasNext();) {
                PollAnswer ans = (PollAnswer) iterator.next();    

                if ( WebUtil.isTrue(ans.getOwnAnswer())){
                    ownAns = ans;
                    continue;
                }
                
                buf.append("<tr><td style=\""+PollUtil.tdStyle+"\">");
                buf.append("<input id=\"answer"+poll.getId()+"\" type=\"radio\" name=\"answer\"  value=\""+ans.getAnswerNum()+"\"/>");
                buf.append(ans.getText());
                buf.append("</td></tr>");
            }        

            if (WebUtil.isTrue(poll.getAllowOwnAnswer()) && ownAns != null){
                buf.append("<tr><td style=\""+PollUtil.tdStyle+"\">");
                buf.append("<input id=\"answer"+poll.getId()+"\" type=\"radio\" name=\"answer\"  value=\"-1\"/>");
                buf.append("-Enter Own Answer-<br/><br/>");
                buf.append("&nbsp;<input id=\"ownAnswer"+poll.getId()+"\" type=\"text\" name=\"ownAnswer\" value=\"\" />");
                buf.append("</td></tr>");
            }
            
            buf.append("</table>");
            buf.append("<INPUT TYPE=\"hidden\" NAME=\"add\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pollId\" value=\""+poll.getId()+"\">");
            buf.append("</form>");

            
            if ( PollUtil.pollClosed(poll)){
                buf.append("<div  style=\""+ PollUtil.linkStyleMid +"\" >Closed</div>");
            } else {
                buf.append("<a  style=\""+ PollUtil.linkStyleMid +"\"");
                buf.append(" href=\"javascript:sendVoteAndDisappear("+poll.getId()+", \\'"+hostName+"\\');\"  onMouseOver=\"this.style.color=\\'"+hoverColor+"\\';\" onMouseOut=\"this.style.color=\\'"+linkTextColor+"\\';\">Vote</a>");
            }
            
            buf.append("<a  style=\""+ PollUtil.linkStyleMid +"\"");
            buf.append(" href=\"javascript:sendViewAndDisappear("+poll.getId()+", \\'"+hostName+"\\');\"  onMouseOver=\"this.style.color=\\'"+hoverColor+"\\';\" onMouseOut=\"this.style.color=\\'"+linkTextColor+"\\';\">View Results</a><br><br>");
            
            buf.append("<a style=\""+ PollUtil.linkStyleSmallGray +"\" href=\"http://"+hostName+"\" >More Polls at Zapoll.com</a>");
            
            buf.append("</div>"); 
            buf.append("<span id=\"resultDisplayPoll"+poll.getId()+"\"></span>");

        }
        
        return buf.toString();
    }
    
    public static String constructPollWidgetHtml_0414(Poll poll, String hostName, boolean resultsOnly ){

        StringBuffer buf = new StringBuffer();
        
        if (resultsOnly) {

            PollResult pollResult = PollResult.getPollResult(poll.getId());
            List results = pollResult.getResultsByCounts(); 
            
            buf.append("<div style=\"background-color:white; padding: 0 0 10px 0;\">");
            buf.append("<table width=\"100%\"  style=\""+PollUtil.tableStyle+"\">");
            buf.append("<tr><td style=\""+PollUtil.titleTdStyle+"\">");
            buf.append(poll.getQuestion());
            buf.append("</td></tr>");

            PollResult.ResultEntry ownAnsEntry = null;

            for(Iterator iter = results.iterator(); iter.hasNext();){
                PollResult.ResultEntry entry = (PollResult.ResultEntry) iter.next();    
                
                if ( WebUtil.isTrue(entry.getAnswerObj().getOwnAnswer())){
                    ownAnsEntry = entry;
                    continue;
                }
                
                
                buf.append("<tr><td style=\""+tdStyle+"\">");
                buf.append("<div style=\"font: normal normal normal 10px verdana\"> ").append( entry.getAnswerObj().getText()).append("</div>");
                buf.append("<div style=\"font: normal normal bold 10px verdana; padding:5px 0px 0px 10px;\"> ").append( entry.getCount()).append("</div>");
                buf.append("</td></tr>");
            }

            
            if (WebUtil.isTrue(poll.getAllowOwnAnswer()) && ownAnsEntry!= null){
                buf.append("<tr><td style=\""+tdStyle+"\">");
                buf.append("<div style=\"font: normal normal normal 10px verdana\"> ").append( ownAnsEntry.getAnswerObj().getText()).append("</div>");
                buf.append("<div style=\"font: normal normal bold 10px verdana; padding:5px 0px 0px 10px;\"> ").append( ownAnsEntry.getCount()).append("</div>");
                buf.append("</td></tr>");
            }
            
            
            buf.append("</table>");
            buf.append("<div style=\"font: normal normal normal 10px verdana;color: gray;\">").append("Total Votes:").append( pollResult.getNumVotes()).append("</div>");
            buf.append("<a style=\""+ PollUtil.linkStyleMid +"\" href=\"http://"+hostName+"/t_poll_result_single.html?prv_serial="+poll.getSerial()+"\" onMouseOver=\"this.style.color=\\'blue\\';\" onMouseOut=\"this.style.color=\\'#CF2B19\\';\">Comments</a>");
            buf.append("<a style=\""+ PollUtil.linkStyleMid +"\" href=\"javascript:backToPoll(").append(poll.getId()).append(");\" onMouseOver=\"this.style.color=\\'blue\\';\" onMouseOut=\"this.style.color=\\'#CF2B19\\';\">back to Poll</a><br><br>");
            buf.append("<a style=\""+ PollUtil.linkStyleSmallGray +"\" href=\"http://"+hostName+"\" >More Polls at Zapoll.com</a>");
            buf.append("</div>"); 
            
        } else {
            
            buf.append("<div style=\"background-color:white; padding: 0 0 10px 0;\" id=\"pollVoteFormFrame"+poll.getId()+"\" >");
            buf.append("<form name=\"pollVoteForm"+poll.getId()+"\" method=\"post\" action=\"http://"+hostName+"/pollVoteAction.html\" id=\"pollVoteForm"+poll.getId()+"\" >");
            buf.append("<table width=\"100%\"  style=\""+PollUtil.tableStyle+"\">");
            buf.append("<tr><td style=\""+ PollUtil.titleTdStyle+"\">");
            buf.append(poll.getQuestion());
            buf.append("</td></tr>");
            
            List answers = PollAnswerDS.getInstance().getByPollId(poll.getId());
            PollAnswer ownAns = null;
            for (Iterator iterator = answers.iterator(); iterator.hasNext();) {
                PollAnswer ans = (PollAnswer) iterator.next();    

                if ( WebUtil.isTrue(ans.getOwnAnswer())){
                    ownAns = ans;
                    continue;
                }
                
                buf.append("<tr><td style=\""+PollUtil.tdStyle+"\">");
                buf.append("<input id=\"answer"+poll.getId()+"\" type=\"radio\" name=\"answer\"  value=\""+ans.getAnswerNum()+"\"/>");
                buf.append(ans.getText());
                buf.append("</td></tr>");
            }        

            if (WebUtil.isTrue(poll.getAllowOwnAnswer()) && ownAns != null){
                buf.append("<tr><td style=\""+PollUtil.tdStyle+"\">");
                buf.append("<input id=\"answer"+poll.getId()+"\" type=\"radio\" name=\"answer\"  value=\"-1\"/>");
                buf.append("-Enter Own Answer-<br/><br/>");
                buf.append("&nbsp;<input id=\"ownAnswer"+poll.getId()+"\" type=\"text\" name=\"ownAnswer\" value=\"\" />");
                buf.append("</td></tr>");
            }
            
            buf.append("</table>");
            buf.append("<INPUT TYPE=\"hidden\" NAME=\"add\" value=\"true\">");
            buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"pollId\" value=\""+poll.getId()+"\">");
            buf.append("</form>");

            
            if ( PollUtil.pollClosed(poll)){
                buf.append("<div  style=\""+ PollUtil.linkStyleMid +"\" >Closed</div>");
            } else {
                buf.append("<a  style=\""+ PollUtil.linkStyleMid +"\"");
                buf.append(" href=\"javascript:sendVoteAndDisappear("+poll.getId()+", \\'"+hostName+"\\');\"  onMouseOver=\"this.style.color=\\'blue\\';\" onMouseOut=\"this.style.color=\\'#CF2B19\\';\">Vote</a>");
            }
            
            buf.append("<a  style=\""+ PollUtil.linkStyleMid +"\"");
            buf.append(" href=\"javascript:sendViewAndDisappear("+poll.getId()+", \\'"+hostName+"\\');\"  onMouseOver=\"this.style.color=\\'blue\\';\" onMouseOut=\"this.style.color=\\'#CF2B19\\';\">View Results</a><br><br>");
            
            buf.append("<a style=\""+ PollUtil.linkStyleSmallGray +"\" href=\"http://"+hostName+"\" >More Polls at Zapoll.com</a>");
            
            buf.append("</div>"); 
            buf.append("<span id=\"resultDisplayPoll"+poll.getId()+"\"></span>");

        }
        
        return buf.toString();
    }
    public static void main(String[] args) {
        
    }

}
