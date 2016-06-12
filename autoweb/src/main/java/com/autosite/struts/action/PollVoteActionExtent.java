package com.autosite.struts.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.Poll;
import com.autosite.db.PollAnswer;
import com.autosite.db.PollVote;
import com.autosite.ds.PollAnswerDS;
import com.autosite.ds.PollDS;
import com.autosite.ds.PollVoteDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.autosite.util.CookieUtil;
import com.autosite.util.PollUtil;
import com.autosite.util.poll.PollResult;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;

public class PollVoteActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollVoteAction#xtent.beforeAdd");		
        PollVote _PollVote = (PollVote)baseDbOject;

        Poll poll = PollDS.getInstance().getById(_PollVote.getPollId());
        if (poll == null) throw new ActionExtentException("Internal Error Occurred.", "poll_display_default");

        if (PollUtil.pollClosed(poll)){
            throw new ActionExtentException("This poll was closed." , "poll_display_default");
        }
        
        List answers = PollAnswerDS.getInstance().getByPollId(_PollVote.getPollId());
        
        
        // -1 is reserved for the own answer.
        if (_PollVote.getAnswer() <-1 || _PollVote.getAnswer() > answers.size() ){
            throw new ActionExtentException("Invalid answer." , "poll_display_default");
        }
        
        prepareVote(request, _PollVote);
        if ( WebUtil.isTrue(poll.getAllowMultiple())){

            List votes = PollVoteDS.getInstance().getByPollIdDupCheckKey(_PollVote.getPollId(), _PollVote.getDupCheckKey());
            int numVoted = 0;
            for (Iterator iterator = votes.iterator(); iterator.hasNext();) {
                PollVote vote = (PollVote) iterator.next();
                
                if (vote.getAnswer() == 0 ) numVoted++;
            }
            
            m_logger.debug("Checking max votes. votes so far=" + numVoted + " max vote=" + poll.getMaxRepeatVote());
            if ( numVoted > poll.getMaxRepeatVote() ){
                m_logger.info("Has been voted " +numVoted + " times. " +  _PollVote.getDupCheckKey() + " max=" + poll.getMaxRepeatVote());
                throw new ActionExtentException("Voted more than allowed", "poll_display_default");
            }
            
            List list = PollAnswerDS.getInstance().getByPollId(_PollVote.getPollId());
            
            PollResult result = PollResult.getPollResult(_PollVote.getPollId());

            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                PollAnswer ans = (PollAnswer) iterator.next();
                
                String answerKey = "answer_" + ans.getAnswerNum();
                if ( WebUtil.isNotNull(request.getParameter(answerKey))){

                    PollVote vote = PollVoteDS.copy(_PollVote);
                    vote.setSiteId(_PollVote.getSiteId());
                    vote.setAnswer(ans.getAnswerNum());
                
                    prepareVote(request, vote);
                    
                    PollVoteDS.getInstance().put(vote);
                    result.addVote(vote);
                }
            }
            
        } else {
        
            prepareVote(request, _PollVote);
            
            List list = PollVoteDS.getInstance().getByPollIdDupCheckKey(_PollVote.getPollId(), _PollVote.getDupCheckKey());
            m_logger.debug("Checking max votes. votes so far=" + list.size() + " max vote=" + poll.getMaxRepeatVote());
            if ( list.size() > poll.getMaxRepeatVote() ){
                m_logger.info("Has been voted " +list.size() + " times. " +  _PollVote.getDupCheckKey() + " max=" + poll.getMaxRepeatVote());
                throw new ActionExtentException("Voted more than allowed", "poll_display_default");
            }
            
            if ( _PollVote.getAnswer() != -1){
                _PollVote.setOwnAnswer(null);
            } else {
                if ( WebUtil.isNull(_PollVote.getOwnAnswer())){
                    throw new ActionExtentException("Own answer text was not entered", "poll_display_default");
                }
                
                List answersList = PollAnswerDS.getInstance().getByPollId(_PollVote.getPollId());
                PollAnswer ownAns = null;
                for (Iterator iterator = answersList.iterator(); iterator.hasNext();) {
                    PollAnswer pa = (PollAnswer) iterator.next();
                    if (WebUtil.isTrue(pa.getOwnAnswer())){
                        ownAns = pa;
                    }
                }
                
                if (ownAns == null) {
                    m_logger.error("Couldn't find own answer object. even the vote is sent for it");
                } else {
                    _PollVote.setAnswer(ownAns.getAnswerNum());
                }
                
            }
        }
    }

    private void prepareVote(HttpServletRequest request,PollVote _PollVote){
        _PollVote.setIpAddress(request.getRemoteAddr());
        _PollVote.setPcid(CookieUtil.getRpcId(request));
        _PollVote.setDupCheckKey(_PollVote.getIpAddress());
        
        // Check the dup votes
        m_logger.debug("IP/PcIDSET to Vote=" + PollVoteDS.objectToString(_PollVote));
    }
    
    
    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#PollVoteAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        PollVote _PollVote = (PollVote)baseDbOject;
        
        if (_PollVote.getAnswer() >0){        
            PollResult result = PollResult.getPollResult(_PollVote.getPollId());
            result.addVote(_PollVote);
        } 
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollVoteAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        PollVote _PollVote = (PollVote)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollVoteAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        PollVote _PollVote = (PollVote)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollVoteAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        PollVote _PollVote = (PollVote)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollVoteAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        PollVote _PollVote = (PollVote)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Map ret = new HashMap();
        String ajaxOutType = request.getParameter("ajaxOut");
        
        if (ajaxOutType == null) return null;
        
        if (ajaxOutType.equals("getResultJson")){
            
            long pollId = WebParamUtil.getLongValue(request.getParameter("pollId"));
            Poll poll = PollDS.getInstance().getById(pollId);
            
            if ( poll != null) {
                
                PollResult pollResult = PollResult.getPollResult(poll.getId());
                List results = pollResult.getResultsByAnswerNum(); 

                JSONObject top = new JSONObject();
                JSONArray array = new JSONArray();
                
                top.put("pollId", poll.getId());
                top.put("totalVotes", pollResult.getNumVotes());
                
                for(Iterator iter = results.iterator(); iter.hasNext();){
                    PollResult.ResultEntry entry = (PollResult.ResultEntry) iter.next();    
                    JSONObject answerResult = new JSONObject();
                    answerResult.put("idx", entry.getAnswerObj().getAnswerNum());
                    answerResult.put("text", entry.getAnswerObj().getText());
                    answerResult.put("count", entry.getCount());
                    array.put(answerResult);
                }
                
                top.put("answers", array);
                
                
                m_logger.debug(top.toString());
                ret.put("__value", top.toString());
                return ret;
            }
        } else if (ajaxOutType.equals("getResultHtml")){
            
            long pollId = WebParamUtil.getLongValue(request.getParameter("pollId"));
            Poll poll = PollDS.getInstance().getById(pollId);
            
            if ( poll != null) {
                
                PollResult pollResult = PollResult.getPollResult(poll.getId());
                List results = pollResult.getResultsByCounts(); 

                StringBuffer buf = new StringBuffer();
                buf.append("<div class=\"pollResult\" ><div class=\"resultNumVotes\">").append( pollResult.getNumVotes()).append("</div>");
                for(Iterator iter = results.iterator(); iter.hasNext();){
                    PollResult.ResultEntry entry = (PollResult.ResultEntry) iter.next();    
                    buf.append("<div class=\"resultAnswerLine\"> ");
                    buf.append("<div class=\"resultAnswerTitle\"> ").append( entry.getAnswerObj().getText()).append("</div>");
                    buf.append("<div class=\"resultAnswerVotes\"> ").append( entry.getCount()).append("</div>");
                    buf.append("</div> ");
                }
                
                buf.append("</div>");
                m_logger.debug(buf.toString());
                ret.put("__value", buf.toString());
                return ret;
            } else if (ajaxOutType.equals("getResult")){
                
                
            }
            
        } else if (ajaxOutType.equals("getResultHtml2")){
            // htmls in this response does not depend on external styles defs. Used for snippets to other sites, which does not import poll styles.  
            
            long pollId = WebParamUtil.getLongValue(request.getParameter("pollId"));
            Poll poll = PollDS.getInstance().getById(pollId);
            
            if ( poll != null) {
                
                PollResult pollResult = PollResult.getPollResult(poll.getId());
                List results = pollResult.getResultsByCounts(); 
                
                String headerStyle = "font: normal normal bold 11px verdana; padding: 5px 0px 5px 0px;"; 
                String answerStyle = "font: normal normal normal 10px verdana; padding: 5px 0px 5px 0px;"; 

                String tableStyle           ="border: 5px solid #F5A236; background-color: #CF2B19; border-collapse: collapse; font: normal normal normal 12px verdana";
                String titleTdStyle         ="border-bottom: 5px solid #F5A236; font: normal normal bold 12px verdana;color: #F5A236; padding : 5 5 5 5; ";
                String tdStyle              ="border: 1px solid #F5A236; font: normal normal normal 12px verdana;;color: #F5A236; padding : 5 0 5 0; ";
                String voteAnchorStyle      = "font: normal normal bold 12px verdana; padding: 5 5 5 5; text-decoration: none; margin: 0 0 5 0; color: #CF2B19; ";
                String resultAnchorStyle    = "font: normal normal bold 12px verdana; padding: 5 5 5 5; text-decoration: none; margin: 0 0 5 0; color: #CF2B19; ";
                
                
                StringBuffer buf = new StringBuffer();
                buf.append("<div style=\"").append(headerStyle).append("\">").append( poll.getQuestion()).append("</div>");
                
                for(Iterator iter = results.iterator(); iter.hasNext();){
                    PollResult.ResultEntry entry = (PollResult.ResultEntry) iter.next();    
                    buf.append("<div style=\"padding:5px 0px 0px 10px;\"> ");
                    buf.append("<div style=\"font: normal normal normal 10px verdana\"> ").append( entry.getAnswerObj().getText()).append("</div>");
                    buf.append("<div style=\"font: normal normal bold 10px verdana; padding:5px 0px 0px 10px;\"> ").append( entry.getCount()).append("</div>");
                    buf.append("</div> ");
                }
                buf.append("<div style=\"font: normal normal normal 10px verdana;color: gray;\">").append("Total Votes:").append( pollResult.getNumVotes()).append("</div>");
                m_logger.debug(buf.toString());
                ret.put("__value", buf.toString());
                return ret;
            } else if (ajaxOutType.equals("getResult")){
                
                
            }
            
        } else if (ajaxOutType.equals("getResultHtml3")){
            // htmls in this response does not depend on external styles defs. Used for snippets to other sites, which does not import poll styles.  
            
            long pollId = WebParamUtil.getLongValue(request.getParameter("pollId"));
            Poll poll = PollDS.getInstance().getById(pollId);
            boolean forScriptSource = WebParamUtil.getBooleanValue(request.getParameter("scriptSource"));
            
            
            if ( poll != null) {
                
//                PollResult pollResult = PollResult.getPollResult(poll.getId());
//                List results = pollResult.getResultsByCounts(); 
                
                String headerStyle = "font: normal normal bold 11px verdana; padding: 5px 0px 5px 0px;"; 
                String answerStyle = "font: normal normal normal 10px verdana; padding: 5px 0px 5px 0px;"; 

                String tableStyle           ="border: 5px solid #F5A236; background-color: #CF2B19; border-collapse: collapse; font: normal normal normal 12px verdana";
                String titleTdStyle         ="border-bottom: 5px solid #F5A236; font: normal normal bold 12px verdana;color: #F5A236; padding : 5 5 5 5; ";
                String tdStyle              ="border: 1px solid #F5A236; font: normal normal normal 12px verdana;;color: #F5A236; padding : 5 0 5 0; ";
                String voteAnchorStyle      = "font: normal normal bold 12px verdana; padding: 5 5 5 5; text-decoration: none; margin: 0 0 5 0; color: #CF2B19; ";
                String resultAnchorStyle    = "font: normal normal bold 12px verdana; padding: 5 5 5 5; text-decoration: none; margin: 0 0 5 0; color: #CF2B19; ";
                
                StringBuffer buf = new StringBuffer();
                String hostName = request.getHeader("host");

                if (forScriptSource) {
                    buf.append("var resultDisplayObj"+poll.getId()+"=document.getElementById('resultDisplayPoll"+poll.getId()+"');");
                    buf.append("resultDisplayObj"+poll.getId()+".innerHTML = '");
                }

/*                
                buf.append("<div style=\"background-color:white; padding: 0 0 10px 0;\">");
                buf.append("<table width=\"100%\"  style=\""+PollUtil.tableStyle+"\">");
                buf.append("<tr><td style=\""+PollUtil.titleTdStyle+"\">");
                buf.append(poll.getQuestion());
                buf.append("</td></tr>");
                
                for(Iterator iter = results.iterator(); iter.hasNext();){
                    PollResult.ResultEntry entry = (PollResult.ResultEntry) iter.next();    

                    buf.append("<tr><td style=\""+tdStyle+"\">");
                    buf.append("<div style=\"font: normal normal normal 10px verdana\"> ").append( entry.getAnswerObj().getText()).append("</div>");
                    buf.append("<div style=\"font: normal normal bold 10px verdana; padding:5px 0px 0px 10px;\"> ").append( entry.getCount()).append("</div>");
                    buf.append("</td></tr>");
                }

                String hostName = request.getHeader("host");
                
                buf.append("</table>");
                buf.append("<div style=\"font: normal normal normal 10px verdana;color: gray;\">").append("Total Votes:").append( pollResult.getNumVotes()).append("</div>");
                buf.append("<a style=\""+ PollUtil.linkStyle +"\" href=\"http://"+hostName+"/t_poll_result_single.html?prv_serial="+poll.getSerial()+"\" >Comments</a>");
                buf.append("<a style=\""+ PollUtil.linkStyle +"\" href=\"http://"+hostName+"\" >Poll By Zapoll.com</a>");
                buf.append("</div>"); 
*/                
                String constructResults = PollUtil.constructPollWidgetHtml(poll, hostName, true);
                buf.append(constructResults);
                m_logger.debug("CONSTRUCTED=" + constructResults);

                if (forScriptSource)
                    buf.append("';");

                
                m_logger.debug(buf.toString());
                ret.put("__value", buf.toString());
                return ret;
            } else if (ajaxOutType.equals("getResult")){
                
                
            }
            
        } else if (ajaxOutType.equals("getResultKVP")){
            
            long pollId = WebParamUtil.getLongValue(request.getParameter("pollId"));
            Poll poll = PollDS.getInstance().getById(pollId);
            
            if ( poll != null) {
                
                PollResult pollResult = PollResult.getPollResult(poll.getId());
                List results = pollResult.getResultsByCounts(); 

                StringBuffer buf = new StringBuffer();
                buf.append("pollId=").append(poll.getId()).append("|");
                buf.append("numVotes=").append(pollResult.getNumVotes()).append("|");
                for(Iterator iter = results.iterator(); iter.hasNext();){
                    PollResult.ResultEntry entry = (PollResult.ResultEntry) iter.next();    
                    buf.append(entry.getAnswerObj().getText()).append("=").append(entry.getCount()).append("|");
                }
                
                m_logger.debug(buf.toString());
                ret.put("__value", buf.toString());
                return ret;
            }
        }
        
        return new HashMap() ;
    }

    public String getJsonPair(String key, int val, boolean bracket){
        return getJsonPair(key, ""+val, bracket);
    }
    public String getJsonPair(String key, String val, boolean bracket){

        String ret = "\"" + key +"\":\""+ val + "\"";
        
        if (bracket)
            return "{" + ret + "}";
        return ret;
    }
    
    
	private PollVoteDS m_ds = PollVoteDS.getInstance();
    private static Logger m_logger = Logger.getLogger( PollVoteActionExtent.class);
    
}
