package com.autosite.ec;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.autosite.AutositeGlobals;
import com.autosite.db.AutositeUser;
import com.autosite.db.EmailSubs;
import com.autosite.ds.ContentDS;
import com.autosite.session.AutositeSessionContext;

public class EcCartManager {

    private static Logger m_logger = Logger.getLogger(ContentDS.class);
    private static EcCartManager m_instance = new EcCartManager();;
    public static boolean m_debug = AutositeGlobals.m_debug;

    protected Map m_mapByUser;
    protected Map m_mapBySerial;
    protected Map m_mapByRPCId; // Random PC ID

    public static synchronized EcCartManager getInstance() {
        return m_instance;
    }

    public EcCart getCartByUser(AutositeUser user) {

        if (user == null)
            return null;

        Long keySiteId = new Long(user.getSiteId());
        Long keyUserId = new Long(user.getId());

        if (m_mapByUser.containsKey(keySiteId)) {

            Map mapSiteId = (Map) m_mapByUser.get(keySiteId);

            if (mapSiteId.containsKey(keyUserId)) {
                return (EcCart) mapSiteId.get(keyUserId);
            }
            else {
                return null;
            }

        }
        else {
            return null;
        }
    }

    public EcCart getByCartSerial(String serial){
        if (serial == null) return null;
        return (EcCart) m_mapBySerial.get(serial);
    }
    
    public EcCart getOrCreateByRPCId(long siteId, String rpcId) {

        Long keySiteId = new Long(siteId);

        if (m_mapByRPCId.containsKey(keySiteId)) {
            Map mapSiteId = (Map) m_mapByRPCId.get(keySiteId);

            if (mapSiteId.containsKey(rpcId)) {
                return (EcCart) mapSiteId.get(rpcId);
            }
            else {
                EcCart newCart = new EcCart();
                newCart.setRPCI(rpcId);
                mapSiteId.put(rpcId, newCart);
                m_mapBySerial.put(newCart.getSerial(), newCart);
                m_logger.info("** New Cart created with " + rpcId + ",serial=" + newCart.getSerial());
                return newCart;
            }

        }
        else {
            Map mapSiteId = new ConcurrentHashMap();
            EcCart newCart = new EcCart();
            newCart.setRPCI(rpcId);
            mapSiteId.put(rpcId, newCart);
            m_mapByRPCId.put(keySiteId, mapSiteId);
            m_mapBySerial.put(newCart.getSerial(), newCart);
            m_logger.info("** New Cart created with " + rpcId + ",serial=" + newCart.getSerial());
            return newCart;
        }
    }

    public boolean removeCart(long siteId, String rpcId) {

        if (rpcId == null) {
            m_logger.info("*WARNING* attempted to remove without rpcId");
            return false;
        }
        Long keySiteId = new Long(siteId);

        if (m_mapByRPCId.containsKey(keySiteId)) {
            Map mapSiteId = (Map) m_mapByRPCId.get(keySiteId);
            
            EcCart removed = (EcCart) mapSiteId.remove(rpcId.trim());
            if (removed != null){
                if (removed!= null) m_logger.info("Cart removed. " + removed);
                m_mapBySerial.remove(removed.getSerial());
                
                Long keyUserId = new Long(removed.getUserId());
                
                if ( m_mapByUser.containsKey(keySiteId)){
                    mapSiteId = (Map) m_mapByUser.get(keySiteId);
                    EcCart cart = (EcCart) mapSiteId.remove(keyUserId);
                    if (cart!= null) m_logger.info("Cart user association removed. " + cart);
                }

                return true;
            } else {
                
            }
        }
        
        return false;
    }

    public void makeMapByUser(AutositeUser user, EcCart cart) {
        if (user == null)
            return;
        Long keySiteId = new Long(user.getSiteId());
        Long keyUserId = new Long(user.getId());

        if (m_mapByUser.containsKey(keySiteId)) {
            Map mapSiteId = (Map) m_mapByUser.get(keySiteId);
            if (!mapSiteId.containsKey(keyUserId)) {
                mapSiteId.put(keyUserId, cart);
                cart.setUserId(user.getId());
                m_logger.info("** Cart [" + cart.getRPCI() + " associated to " + user.getId() + "/" + user.getUsername());
            }
        }
        else {

            Map mapSiteId = new ConcurrentHashMap();
            mapSiteId.put(user.getId(), cart);
            cart.setUserId(user.getId());
            m_mapByUser.put(keySiteId, mapSiteId);
            m_logger.info("** Cart [" + cart.getRPCI() + " associated to " + user.getId() + "/" + user.getUsername());
        }
    }

    public EcCartManager() {
        m_mapByUser = new ConcurrentHashMap();
        m_mapByRPCId = new ConcurrentHashMap();
        m_mapBySerial = new ConcurrentHashMap();
    }

    public EmailSubs getBySiteIdEmail(long SiteId, String Email) {

        Long keySiteId = new Long(SiteId);
        if (m_mapByUser.containsKey(keySiteId)) {

            Map mapSiteId = (Map) m_mapByUser.get(keySiteId);

            String keyEmail = Email;

            if (mapSiteId.containsKey(keyEmail)) {
                return (EmailSubs) mapSiteId.get(keyEmail);
            }
            else {
                return null;
            }

        }
        else {
            return null;
        }
    }

    private void updateSiteIdEmailOneMap(Object obj, boolean del) {
        EmailSubs o = (EmailSubs) obj;

        String keyEmail = o.getEmail();

        if (del) {
            // delete from SiteIdEmailToOneMap
            Map mapSiteId = (Map) m_mapByUser.get(new Long(o.getSiteId()));
            if (mapSiteId != null) {
                Map mapEmail = (Map) mapSiteId.get(keyEmail);
                if (mapSiteId.containsKey(keyEmail)) {
                    mapSiteId.remove(keyEmail);
                }
            }
            m_logger.debug("removed from m_SiteIdEmailToOneMap" + o.getId() + " from " + o.getSiteId() + " # " + o.getEmail());
        }
        else {

            // add to SiteIdEmailToOneMap
            Map mapSiteId = (Map) m_mapByUser.get(new Long(o.getSiteId()));
            if (mapSiteId == null) {
                mapSiteId = new TreeMap();
                m_mapByUser.put(new Long(o.getSiteId()), mapSiteId);
                m_logger.debug("created new   mapSiteId for " + o.getSiteId());
            }

            EmailSubs replaced = (EmailSubs) mapSiteId.put(keyEmail, o);
            if (replaced != null) {
                m_logger.debug("**WARNING**: existing object was replaced in one-one map. id=" + replaced.getId());
            }

            m_logger.debug("Panel added to SiteIdEmailToOneMap " + o.getId() + " to " + o.getSiteId());
        }

    }

    public static EcCart getCartMakeSure(AutositeSessionContext ctx, String rpcId, long siteId){
        AutositeUser user = ctx.getUserObject();
        
        EcCartManager ecCartManager = EcCartManager.getInstance();
        EcCart cart = ecCartManager.getCartByUser(user);

        if (cart == null) {
            cart = ecCartManager.getOrCreateByRPCId(siteId, rpcId);
            long existingUser = cart.getUserId();
            if (user!= null && (user.getId() != existingUser)){
                m_logger.info("Cart is associated with " + existingUser + " will be re-created");
                ecCartManager.removeCart(siteId, rpcId);
                cart = ecCartManager.getOrCreateByRPCId(siteId, rpcId);
            }
            ecCartManager.makeMapByUser(user, cart);
            m_logger.debug("*Cart not found with user so get by RPCI=" +rpcId); 
        } else {

            long existingUser = cart.getUserId();
            if (user!= null && (user.getId() != existingUser)){
                m_logger.info("Cart is associated with " + existingUser + " will be re-created");
                ecCartManager.removeCart(siteId, rpcId);
                cart = ecCartManager.getOrCreateByRPCId(siteId, rpcId);
                ecCartManager.makeMapByUser(user, cart);
            }
            
            m_logger.debug("*Cart found with user " + cart);
        }
        
        return cart;
    }

    public static void main(String[] args) {

        AutositeUser user = new AutositeUser();
        user.setSiteId(1);
        user.setId(0);

        AutositeSessionContext c = new AutositeSessionContext("");
        // c.setUserObject(user);

        System.out.println("-------------------------------------------------------------");
        EcCart cart = EcCartManager.getCartMakeSure(c, "XX", 1);
        System.out.println(cart);

        c.setUserObject(user);

        System.out.println("-------------------------------------------------------------");
        cart = EcCartManager.getCartMakeSure(c, "XX", 1);
        System.out.println(cart);
        
        user.setId(1);
        c.setUserObject(user);

        System.out.println("-------------------------------------------------------------");
        cart = EcCartManager.getCartMakeSure(c, "XX", 1);
        System.out.println(cart);
        

        user.setId(0);
        c.setUserObject(user);
        System.out.println("-------------------------------------------------------------");
        cart = EcCartManager.getCartMakeSure(c, "XX", 1);
        System.out.println(cart);

        System.out.println("-------------------------------------------------------------");
        cart = EcCartManager.getCartMakeSure(c, "XX", 1);
        System.out.println(cart);
        
        
    }
}
