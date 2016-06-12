package com.autosite.struts.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.Poll;
import com.autosite.db.PollAnswer;
import com.autosite.db.PollComment;
import com.autosite.db.PollStyle;
import com.autosite.db.PollVote;
import com.autosite.ds.PollAnswerDS;
import com.autosite.ds.PollCommentDS;
import com.autosite.ds.PollDS;
import com.autosite.ds.PollStyleDS;
import com.autosite.ds.PollVoteDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.util.WebUtil;

public class PollActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollAction#xtent.beforeAdd");		
        Poll _Poll = (Poll)baseDbOject;

        if ( WebUtil.isNull(_Poll.getQuestion())){
            m_logger.debug("question is missing in this requres " + PollDS.objectToString(_Poll));
            throw new Exception("Question is missing.");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#PollAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        Poll _Poll = (Poll)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        Poll _Poll = (Poll)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        Poll _Poll = (Poll)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        Poll _Poll = (Poll)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#PollAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        Poll _Poll = (Poll)baseDbOject;
        
        
        List answers = PollAnswerDS.getInstance().getByPollId(_Poll.getId());
        for (Iterator iterator = answers.iterator(); iterator.hasNext();) {
            PollAnswer pa = (PollAnswer) iterator.next();
            PollAnswerDS.getInstance().delete(pa);
        }

        List comments = PollCommentDS.getInstance().getByPollId(_Poll.getId());
        for (Iterator iterator = comments.iterator(); iterator.hasNext();) {
            PollComment pa = (PollComment) iterator.next();
            PollCommentDS.getInstance().delete(pa);
        }

        List votes = PollVoteDS.getInstance().getByPollId(_Poll.getId());
        for (Iterator iterator = votes.iterator(); iterator.hasNext();) {
            PollVote pa = (PollVote) iterator.next();
            PollVoteDS.getInstance().delete(pa);
        }
        
        
        PollStyle pollStyle = PollStyleDS.getInstance().getBySiteIdPollId(_Poll.getSiteId(), _Poll.getId());
        if (pollStyle != null)
            PollCommentDS.getInstance().delete(pollStyle);
        
    }

	private PollDS m_ds = PollDS.getInstance();
    private static Logger m_logger = Logger.getLogger( PollActionExtent.class);
    
}
