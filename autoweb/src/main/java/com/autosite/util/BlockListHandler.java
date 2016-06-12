package com.autosite.util;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.autosite.db.BlockList;
import com.autosite.ds.BlockListDS;

public class BlockListHandler {
    
    public static int BLOCK_REASON_SPAM_AUTO = 1;
    public static int BLOCK_REASON_SPAM_MANUAL = 1;
    public static int BLOCK_REASON_ATTACK_AUTO = 1;
    public static int BLOCK_REASON_OTHER = 99;
    
    
    private ConcurrentHashMap m_blockIp = new  ConcurrentHashMap(10000);
    private ConcurrentHashMap m_blockSubnet = new  ConcurrentHashMap(1000);
    
    private BlockListDS m_ds;
    
    public static BlockListHandler getInstance() {
        return m_instance;
    }

    private BlockListHandler() {
        m_ds = BlockListDS.getInstance();
        
        List list = m_ds.getAll();
        
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            BlockList block = (BlockList) iterator.next();
            
            if (block.getRangeCheck() == 1){
                m_blockSubnet.put( block.getIpData(), block);
            } else {
                m_blockIp.put( block.getIpData(), block);
            }
        }
    }
    
    public boolean blocked(String addr){
        
        //
        if ( m_blockIp.containsKey(addr)){
            m_logger.debug("Blocked by ip blocking: " + addr);
            return true;
        }
        
        String subnet = getSubnet(addr);
        
        if ( m_blockSubnet.containsKey(subnet)){
            m_logger.debug("Blocked by subnet blocking: " + addr);
            return true;
        }
        
        
        
        return false;
        
    }
    
    
    public boolean addBlock(String addr, boolean range, int reasonCode){

        BlockList newBlock = new BlockList();
        
        newBlock.setRangeCheck(range?1:0);
        newBlock.setIpData(addr);
        newBlock.setReasonCode(reasonCode);
        newBlock.setTimeCreated(new Timestamp(System.currentTimeMillis()));
        
        if ( m_ds.put(newBlock)){
            if (range)
                m_blockSubnet.put(addr, newBlock);
            else 
                m_blockIp.put(addr, newBlock);
        } else {
            m_logger.error("ERROR: failed while saving blocklist " + m_ds.objectToString(newBlock) );
        }
        
        
        
        return true;
    }
    
    public static String getSubnet(String addr){

        int pos =  addr.lastIndexOf('.');
        
        if ( pos > 0 ) {
            
            return addr.substring(0, pos);
            
        }
        
        return null;
        
    }

    private static Logger m_logger = Logger.getLogger(BlockListHandler.class);
    private static BlockListHandler m_instance = new BlockListHandler();
    
}
