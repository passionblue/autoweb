package com.autosite.util.poll;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.autosite.db.AutositeUser;
import com.autosite.db.Poll;
import com.autosite.ds.PollCommentDS;
import com.autosite.util.CookieUtil;
import com.autosite.util.PollUtil;

public class PollWrapper {

    private Poll m_poll;
    private boolean m_voted;

    public static PollWrapper getWrapper(Poll poll, HttpServletRequest request, AutositeUser user ){
        PollWrapper wrapper =  new PollWrapper(poll);
    
        boolean voted = PollUtil.votedAlready(poll , user, request.getRemoteAddr(), CookieUtil.getRpcId(request));
        wrapper.setVoted(voted);
        
        return wrapper;
    }

    public PollWrapper(Poll poll) {
        super();
        m_poll = poll;
    }

    public Poll getPoll() {
        return m_poll;
    }

    public void setPoll(Poll poll) {
        m_poll = poll;
    }

    public boolean isVoted() {
        return m_voted;
    }

    public void setVoted(boolean voted) {
        m_voted = voted;
    }

    public PollResult getResult() {
        if (m_poll == null) return null;
        return PollResult.getPollResult(m_poll.getId());
    }
    
    public List getComments() {
        if (m_poll == null) return new ArrayList();
        List comments = PollCommentDS.getInstance().getByPollId(m_poll.getId());
        return comments;
    }
    
}
