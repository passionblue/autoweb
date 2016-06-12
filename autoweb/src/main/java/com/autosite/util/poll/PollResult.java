package com.autosite.util.poll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.autosite.db.Poll;
import com.autosite.db.PollAnswer;
import com.autosite.db.PollVote;
import com.autosite.ds.PollAnswerDS;
import com.autosite.ds.PollDS;
import com.autosite.ds.PollVoteDS;

public class PollResult {

    PollDS pollDS;
    PollAnswerDS ansDS;
    PollVoteDS voteDS;
    
    Map m_resultsMap = new ConcurrentHashMap();
    int m_numVotes = 0;
    long m_pollId = 0;

    
    private static Logger m_logger = Logger.getLogger(PollResult.class);

    private static Map m_resultsPool = new ConcurrentHashMap();
    
    public static synchronized PollResult getPollResult(long pollId){
        
        Long key = new Long(pollId);
        if ( !m_resultsPool.containsKey(key)){
            m_logger.info("Poll Result is being created for " + pollId);
            PollResult newResult = new PollResult(pollId);
            m_resultsPool.put( key, newResult);
            return newResult;
        } else {
            return (PollResult) m_resultsPool.get(key);
        }
    }
    
    // Called by site initator so that loads all poll results during the init time. 
    public static synchronized void loadAll() throws Exception {
        
        List allPolls = PollDS.getInstance().getAll();
        for (Iterator iterator = allPolls.iterator(); iterator.hasNext();) {
            Poll poll = (Poll) iterator.next();
            getPollResult(poll.getId());
        }
    }    
    
    protected PollResult( long pollId){
        pollDS = PollDS.getInstance();
        ansDS = PollAnswerDS.getInstance();
        voteDS = PollVoteDS.getInstance();
        m_pollId = pollId;
        
        try {
            init();
        }
        catch (Exception e) {
            m_logger.debug("Failed while init pollresult for " + pollId);
            m_logger.error(e.getMessage(),e);
        }
    }

    protected void init() throws Exception {
        List answers = ansDS.getByPollId(m_pollId);
        for (Iterator iterator = answers.iterator(); iterator.hasNext();) {
            PollAnswer ans = (PollAnswer) iterator.next();
            
            m_resultsMap.put(new Long(ans.getAnswerNum()), new ResultEntry(ans));
            
        }
        m_numVotes = 0;
        List votes = voteDS.getByPollId(m_pollId);
        for (Iterator iterator = votes.iterator(); iterator.hasNext();) {
            PollVote vote = (PollVote) iterator.next();
            addVote(vote);
        }
    }
    
    public void addVote(PollVote vote){
        if (vote.getAnswer() ==0) return;
        
        ResultEntry resultEntry = (ResultEntry) m_resultsMap.get(new Long(vote.getAnswer()));
        if (resultEntry != null) {
            resultEntry.increaseCount();
            m_numVotes++;
        }
        
//        m_logger.debug("Vote added for " + vote.getPollId() + " answer=" + vote.getAnswer());
    }
    
    public int getNumVotes(){
        return m_numVotes;
    }
    
    public int getNumAnswers(){
        return m_resultsMap.size();
    }
    public int getCount(int answerNum){
        ResultEntry resultEntry = (ResultEntry) m_resultsMap.get(new Long(answerNum));
        if (resultEntry != null)
            return resultEntry.getCount();
        return 0;
    }

    public String getText(int answerNum){
        ResultEntry resultEntry = (ResultEntry) m_resultsMap.get(new Long(answerNum));
        if (resultEntry != null)
            return resultEntry.getAnswerObj().getText();
        return null;
    }
    
    public List getResultsBy(){
        List list = new ArrayList(m_resultsMap.values());
        Collections.sort(list, new Comparator() {
            public int compare( Object o1, Object o2){
                ResultEntry r1 = (ResultEntry) o1;
                ResultEntry r2 = (ResultEntry) o2;
                
                if ( r1.getAnswerObj().getAnswerNum() > r2.getAnswerObj().getAnswerNum())
                    return 1;
                else if ( r1.getAnswerObj().getAnswerNum() == r2.getAnswerObj().getAnswerNum())
                    return 0;
                else 
                    return -1;
            }
        });
        
        return list;
    }
    
    public List getResultsByCounts(){
        List list = new ArrayList(m_resultsMap.values());
        Collections.sort(list, new Comparator() {
            public int compare( Object o1, Object o2){
                ResultEntry r1 = (ResultEntry) o1;
                ResultEntry r2 = (ResultEntry) o2;
                
                if ( r1.getCount()> r2.getCount())
                    return 1;
                else if ( r1.getCount() == r2.getCount())
                    return 0;
                else 
                    return -1;
            }
        });
        
        return list;
    }

    public List getResultsByAnswerNum() {
        
        List list = new ArrayList(m_resultsMap.values());
        Collections.sort(list, new Comparator() {
            public int compare( Object o1, Object o2){
                ResultEntry r1 = (ResultEntry) o1;
                ResultEntry r2 = (ResultEntry) o2;
                
                if ( r1.getAnswerObj().getAnswerNum()> r2.getAnswerObj().getAnswerNum())
                    return 1;
                else if ( r1.getAnswerObj().getAnswerNum() ==  r2.getAnswerObj().getAnswerNum())
                    return 0;
                else 
                    return -1;
            }
        });
        
        return list;
    }    
    
    public static class ResultEntry{
        PollAnswer m_answer;
        int m_count;
        ResultEntry(PollAnswer ans){
            m_count=0;
            m_answer = ans;
        }
        
        synchronized void increaseCount(){
            m_count++;
        }
        
        public  int getCount() {
            return m_count;
        }
        public PollAnswer getAnswerObj(){
            return m_answer;
        }
    }
    
    public static void main(String[] args) {

        PollResult res = new PollResult(26);
        
        System.out.println(res.getNumAnswers());
        
        System.out.println(res.getCount(5));
        System.out.println(res.getText(5));
        System.out.println(res.getCount(1));
        
        
        
    }
}
