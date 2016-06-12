package com.autosite.util;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.autosite.db.AutositeUser;
import com.autosite.db.Poll;
import com.autosite.db.PollAnswer;
import com.autosite.db.PollVote;
import com.autosite.ds.PollAnswerDS;
import com.autosite.ds.PollVoteDS;
import com.autosite.struts.action.ActionExtentException;
import com.autosite.util.poll.PollResult;
import com.jtrend.util.WebUtil;

public class PollUtil {

    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_IMAGE_ANSWERS = 2;
    public static final int TYPE_SCALE = 3;
    public static final int TYPE_YES_NO = 4;

    
    public static final String tableStyle           ="border: 5px solid #F5A236; background-color: #CF2B19; border-collapse: collapse; font: normal normal normal 12px verdana";
    public static final String titleTdStyle         ="border-bottom: 5px solid #F5A236; font: normal normal bold 12px verdana;color: #F5A236; padding : 5 5 5 5; ";
    public static final String tdStyle              ="border: 1px solid #F5A236; font: normal normal normal 12px verdana;;color: #F5A236; padding : 5 0 5 0; ";
    public static final String linkStyle            = "font: normal normal bold 12px verdana; padding: 5px 5px 5px 0px; text-decoration: none; margin: 0 0 5 0; color: #CF2B19; ";
    public static final String linkStyleMid         = "font: normal normal bold 12px verdana; padding: 5px 5px 5px 0px; text-decoration: none; margin: 0 0 5 0; color: #CF2B19; ";
    public static final String linkStyleSmallGray       = "font: normal normal bold 10px verdana; padding: 5px 5px 5px 0px; text-decoration: none; margin: 0 0 5 0; color: gray; ";

    public static final String hoverColor           = "blue";
    public static final String defaultColor         = "#CF2B19";

    //========================================================================================================================
    // Poll Status Checking Methods
    //========================================================================================================================

    public static boolean pollClosed(Poll poll) {
        
        if (poll.getTimeExpired() == null || poll.getTimeExpired().getTime() > System.currentTimeMillis()){
            return false;
        }
        
        return true;

    }    
    
    public static boolean votedAlready(Poll poll, AutositeUser user, String ip, String rpcId) {
        if ( poll == null ) return false;
        boolean voted = false;

// TODO Need to use user object later. 
//        if (user != null) {
//            if (PollVoteDS.getInstance().getByPollIdUserId(poll.getId(), user.getId()) != null)
//                voted = true;
//        }

        
        String key = ip;
        
        if ( WebUtil.isTrue(poll.getUseCookieForDup())){
            key = rpcId;
        }
        

        if ( poll.getAllowMultiple() == 1){
            List votes = PollVoteDS.getInstance().getByPollIdDupCheckKey(poll.getId(), key);
            int numVoted = 0;
            for (Iterator iterator = votes.iterator(); iterator.hasNext();) {
                PollVote vote = (PollVote) iterator.next();
                if (vote.getAnswer() == 0 ) numVoted++;
            }
            
            if ( numVoted > poll.getMaxRepeatVote() ){
                voted = true;
            }
            
        } else {
        
            List list = PollVoteDS.getInstance().getByPollIdDupCheckKey(poll.getId(), key);
            if (list.size() > poll.getMaxRepeatVote()) {
                voted = true;
            }
        }
        return voted;
    }

    
    
    //========================================================================================================================
    //
    //========================================================================================================================
    
    public static List sortResultsByCount(List list, boolean reverse){
        Collections.sort(list, new Comparator(){
            public int compare(Object o1, Object o2){
                
                PollResult.ResultEntry a1 = ( PollResult.ResultEntry) o1;
                PollResult.ResultEntry a2 = ( PollResult.ResultEntry) o2;
                
                if ( a1.getCount() >a2.getCount()) return 1;
                if ( a1.getCount() == a2.getCount()) return 0;
                return -1; 
            }
        });
        
        return list;
    }
    
    public static Timestamp getExpiringTime(Timestamp startTime, int numDays){
        if (numDays == 0 || startTime == null) return null;
        
        long time = startTime.getTime();
        time += numDays*86400000;
        
        return new Timestamp(time);
    }
    
    public static String getPollDisplayPartPage(int type){
        switch(type){
        case TYPE_IMAGE_ANSWERS: return "/jsp/poll/work/parts/partImagePollListDisplay.jsp";
        case TYPE_SCALE: return "/jsp/poll/work/parts/partScalePollDisplay.jsp";
        case TYPE_YES_NO: return "/jsp/poll/work/parts/partYesNoPollDisplay.jsp";
        case TYPE_DEFAULT: default: return "/jsp/poll/work/parts/partPollListDisplay.jsp";
        }
    }
    
    public static String constructPollWidgetHtml(Poll poll, String hostName, boolean resultsOnly ){

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

            
            if ( pollClosed(poll)){
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
