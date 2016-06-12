package com.jtrend.session.webprocess;


public class SingleWebProcess extends AbstractWebProcess implements WebProcess {

    public SingleWebProcess(int totalStage) {
        super(totalStage, 1800000, null, false);
    }

    public SingleWebProcess() {
        super(1, 1800000, null, false);
    }

    public SingleWebProcess(String arg) {
        super(1, 1800000, arg, false);
    }

}

//    protected long m_timeCreated = System.currentTimeMillis();
//    protected long m_timeLastModified;
//    protected long m_timeCompleted;
//    protected long m_timeClosed;
//    protected long m_life = 1800000;
//    
//    protected boolean m_allowReprocess;
//    protected boolean m_closed;
//    protected boolean m_started;
//    protected boolean m_completed;
//    
//    protected int m_totalStage;
//    protected int m_currentStage;
//    
//    protected Map m_commonResource = new HashMap();
//    protected Map m_stageResource[];
//    
//    protected String m_arg;
//    protected String m_id;
//    
//    public SingleWebProcess(int totalStage, long life, String arg) {
//        m_totalStage = totalStage;
//        m_currentStage = 0;
//        
//        m_stageResource = new Map[totalStage+1];
//        
//        for (int i = 0; i<totalStage;i++){
//            m_stageResource[i] = new HashMap();
//        }
//        
//        m_timeLastModified = System.currentTimeMillis();
//        m_life = life;
//        m_arg = arg;
//        if ( m_arg != null && !m_arg.isEmpty()) 
//            m_id = m_arg+":" + getIndex() + ":" + System.nanoTime();
//        else 
//            m_id = "web_proc:" + getIndex() + ":" + System.nanoTime();
//    }
//
//    public SingleWebProcess(int totalStage) {
//        this(totalStage, 1800000, null);
//    }    
//    
//    public SingleWebProcess() {
//        this(1, 1800000, null);
//    }    
//
//    public SingleWebProcess(String arg) {
//        this(1, 1800000, arg);
//    }    
//    
//    public Map getCommonResource() {
//        return m_commonResource;
//    }
//
//    public long getCreatedTimestamp() {
//        return m_timeCreated;
//    }
//
//    public int getCurrentStage() {
//        return m_currentStage;
//    }
//
//    public Map getStageResource(int stage) {
//        
//        if ( stage <1 || stage > m_totalStage)
//            return null;
//        
//        return m_stageResource[stage];
//    }
//
//    public long getTimeToExpire() {
//        return m_timeLastModified + m_life;
//    }
//
//    public int getTotalStage() {
//        return m_totalStage;
//    }
//
//    public boolean isExpired() {
//        if ( System.currentTimeMillis() > m_timeLastModified + m_life) return true;
//        return false;
//    }
//
//    public boolean isStarted() {
//        return (m_currentStage > 0 );
//    }
//    
//    public void close() {
//        if (!m_closed) {
//            m_closed = true;
//            m_timeClosed = System.currentTimeMillis();
//        }
//    }
//
//    public boolean hasNextStage() {
//        if (m_totalStage > m_currentStage) return true;
//        return false;
//    }
//
//    public boolean isCompleted() {
//        return m_completed;
//    }
//
//    public void setCurrentStage(int stage) throws Exception {
//        if (m_closed || isCompleted()|| isExpired()) {
//            throw new Exception("This WebProcess has been closed or completed");
//        }
//        
//        if (stage < 1 || stage > m_totalStage) {
//            throw new Exception("Stage idx has exceeded");
//        }
//        
//        m_currentStage = stage;
//        setCurrent();
//    }
//
//    public void addCommonResource(String name, String value) {
//        m_commonResource.put(name, value);
//    }
//
//    public String getCommonResource(String name) {
//        return (String) m_commonResource.get(name);
//    }
//
//    private void setCurrent() {
//        m_timeLastModified = System.currentTimeMillis();
//    }
//    
//    
//    public void complete() {
//        if ( !m_completed && !m_closed && !isExpired()){
//            m_completed = true;
//            m_timeCompleted = System.currentTimeMillis();
//        }
//        m_logger.debug("*Complieting web process that was created " + new Date(m_timeCreated));
//    }
//
//    public boolean isClosed() {
//        return m_closed;
//    }
//
//    public String getId() {
//        return m_id;
//    }
//
//    public static int getIndex(){
//        return m_atomicInt.incrementAndGet();
//    }
//    
//    public String getArg() {
//        return m_arg;
//    }
//
//
//
//    private static Logger m_logger = Logger.getLogger(SingleWebProcess.class);
//
//    static AtomicInteger m_atomicInt = new AtomicInteger(0);
//}
