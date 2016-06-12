package com.autosite.struts.action.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.autosite.db.AutositeDataObject;
import com.autosite.db.AutositeRemoteDevice;
import com.autosite.db.AutositeSynchLedger;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.Site;
import com.autosite.ds.AutositeRemoteDeviceDS;
import com.autosite.ds.AutositeSynchLedgerDS;
import com.autosite.session.AutositeSessionContext;
import com.autosite.session.devicesynch.AutositeLedgerSynchTrackingManager;
import com.autosite.session.devicesynch.DeviceSynchTracker;
import com.autosite.session.devicesynch.SynchTrackingEntry;
import com.jtrend.util.TimeNow;

public class AutositeSynchAction extends AutositeAccessAction {

    private static Logger m_logger = LoggerFactory.getLogger(AutositeSynchAction.class);

    public AutositeSynchAction() {
        super();
    }
    
    // This gets called when a new data comes from remote device. SynchLedger will be recorded. 
    // A synch ledger has two purposes. 
    // (1) keeps the record that the source device already has the copy so dont need to synch in the future. 
    // (2) Other devices should download the new record so that remain in synch with data residing in the server side. that way, all remote devices would 
    //     have the same data. 
    
    //registerSynchLedgerForRemoteEvents
    @Deprecated
    protected AutositeSynchLedger processSynchRequest(HttpServletRequest request, BaseAutositeDataObject autositeObject) {
        
        if ( autositeObject == null) {
            return null;
        }
        
        HttpSession session = request.getSession();
        AutositeSessionContext sessionContext = (AutositeSessionContext)getSessionContext(session);
        Site site = findSessionBoundSite(request);

        AutositeSynchLedger synchLedger = new AutositeSynchLedger();
        AutositeRemoteDevice device = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(), sessionContext.getSourceDeviceId());
        // What if there is no device?
        
        if (hasRequestValue(request, "_synchCmd", "remote-create")){
            
            synchLedger.setSiteId(site.getId());
            synchLedger.setDeviceId(device==null?0:device.getId()); 
            synchLedger.setScope(request.getParameter("_synchScope"));
            synchLedger.setRemoteToken(request.getParameter("_synchRemoteToken"));
            
            synchLedger.setObjectId(autositeObject.getId());
            synchLedger.setTimeCreated(new TimeNow());
            synchLedger.setTarget(autositeObject.getClass().getSimpleName());
            synchLedger.setSynchId("RC"+System.currentTimeMillis());
            AutositeSynchLedgerDS.getInstance().put(synchLedger);
            
            m_logger.info("Synch processed " + synchLedger.getScope() + "/" + synchLedger.getSynchId() + "/"+ synchLedger.getRemoteToken());
            
        } else if (hasRequestValue(request, "_synchCmd", "remote-update")){

            synchLedger.setSiteId(site.getId());
            synchLedger.setDeviceId(device==null?0:device.getId()); 
            synchLedger.setScope(request.getParameter("_synchScope"));
            synchLedger.setRemoteToken(request.getParameter("_synchRemoteToken"));
            
            synchLedger.setObjectId(autositeObject.getId());
            synchLedger.setTimeCreated(new TimeNow());
            synchLedger.setTarget(autositeObject.getClass().getSimpleName());
            synchLedger.setSynchId("RU"+System.currentTimeMillis());
            AutositeSynchLedgerDS.getInstance().put(synchLedger);

        } else if (hasRequestValue(request, "_synchCmd", "remote-delete")){

            synchLedger.setSiteId(site.getId());
            synchLedger.setDeviceId(device==null?0:device.getId()); 
            synchLedger.setScope(request.getParameter("_synchScope"));
            synchLedger.setRemoteToken(request.getParameter("_synchRemoteToken"));
            
            synchLedger.setObjectId(autositeObject.getId());
            synchLedger.setTimeCreated(new TimeNow());
            synchLedger.setTarget(autositeObject.getClass().getSimpleName());
            synchLedger.setSynchId("RD"+System.currentTimeMillis());
            AutositeSynchLedgerDS.getInstance().put(synchLedger);
            
        } else if (hasRequestValue(request, "_synchCmd", "confirm-server-create")){
            
            synchLedger.setSynchId("SC"+System.currentTimeMillis());
        } else if (hasRequestValue(request, "_synchCmd", "confirm-server-update")){
            
            synchLedger.setSynchId("SU"+System.currentTimeMillis());
        }
        
        return synchLedger;
    }
    
    
    //TODO
    protected List processSynchRequestBulk(HttpServletRequest request, List autositeObjects) {
        
        if ( hasRequestValue(request, "_synchCmd", "remote-create") ||
                hasRequestValue(request, "_synchCmd", "remote-update") ||
                hasRequestValue(request, "_synchCmd", "remote-delete")  ) {
            
            
            
            
        } else {
            return new ArrayList();
        }
    
        
        
        return null;
    }    
    
    // This is called by web based request to register synchLedger.
    // For example, if the object gets created from web interface. It should register synchLedger for remote devices.
    // This ledger will be used as a base copy when remote devices checks whether there are any changes on the servers side. 
    
    protected AutositeSynchLedger registerSynchLedgerForServerEvents(HttpServletRequest request, AutositeDataObject autositeObject, String scopePrefix) {
        
        HttpSession             session = request.getSession();
        AutositeSessionContext  sessionContext = (AutositeSessionContext)getSessionContext(session);
        Site                    site = findSessionBoundSite(request);

        AutositeSynchLedger synchLedger = new AutositeSynchLedger();
        
        // This method should run only for web-bound changes (e.g using browser)
        boolean isDeviceRequest = false;
        if ( isThere(request, "_synchCmd") ){
            isDeviceRequest = true;
//            return null;
        }
        AutositeRemoteDevice    device          = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(site.getId(), sessionContext.getSourceDeviceId());

        if (isActionCmdAdd(request)){

            if ( device != null) {
                synchLedger.setDeviceId(device.getId());
                synchLedger.setRemoteToken(request.getParameter("_synchRemoteToken"));
                synchLedger.setSynchId("RC"+System.currentTimeMillis());
            }


            synchLedger.setSiteId(site.getId());
            synchLedger.setScope(scopePrefix + "-create");
            synchLedger.setObjectId(autositeObject.getId());
            synchLedger.setTarget(autositeObject.getClass().getSimpleName());
            synchLedger.setTimeCreated(new TimeNow());
            //synchLedger.setSynchId("SC"+System.currentTimeMillis());  this will be set when remote device get this
            AutositeSynchLedgerDS.getInstance().put(synchLedger);
            
            //The reason to save synchLedger into a request is when the server returns, it needs synchId. 
            // But the search logic in findOrRegisterSynchLedger() is to return a synchledger without synchId. It makes sense because there is synchId, it means it has been synched
            // so it doesnt have to re-synch. 
            // how ever remote device needs to get synchId for a newly created object. so this synchLedger will be saved here
            // and returned by findOrRegisterSynchLedger() method to prepare replies from the server.
            //@see findOrRegisterSynchLedger() method in this file.
            request.setAttribute("_synchledger.create", synchLedger);
        
        } else if (isActionCmdEdit(request)|| isActionCmdEditfield(request)){

            DeviceSynchTracker      tracker         = AutositeLedgerSynchTrackingManager.getInstance().getDeviceTracker(device);

            AutositeSynchLedger existingUpdateSynchLedger  = tracker.findSynchLedger(scopePrefix + "-update", autositeObject.getId());

            if ( existingUpdateSynchLedger != null)  {
                existingUpdateSynchLedger.setTarget(autositeObject.getClass().getSimpleName());
                existingUpdateSynchLedger.setRemoteToken(null);
                existingUpdateSynchLedger.setSynchId(null);
                AutositeSynchLedgerDS.getInstance().update(existingUpdateSynchLedger);
            } else {
                
                synchLedger.setSiteId(site.getId());
                synchLedger.setScope(scopePrefix + "-update");
                
                synchLedger.setObjectId(autositeObject.getId());
                synchLedger.setTarget(autositeObject.getClass().getSimpleName());
                synchLedger.setTimeCreated(new TimeNow());
                AutositeSynchLedgerDS.getInstance().put(synchLedger);
            }
            request.setAttribute("_synchledger.update", synchLedger);
            
        } else if (isActionCmdDelete(request)){
            
            if ( device != null) {
                synchLedger.setDeviceId(device.getId());
                synchLedger.setRemoteToken(request.getParameter("_synchRemoteToken"));
                synchLedger.setSynchId("RD"+System.currentTimeMillis());
            }
            
            synchLedger.setSiteId(site.getId());
            synchLedger.setScope(scopePrefix + "-delete");
            
            synchLedger.setObjectId(autositeObject.getId());
            synchLedger.setTarget(autositeObject.getClass().getSimpleName());
            synchLedger.setTimeCreated(new TimeNow());
            //synchLedger.setSynchId("SD"+System.currentTimeMillis());
            AutositeSynchLedgerDS.getInstance().put(synchLedger);
            request.setAttribute("_synchledger.delete", synchLedger);
            
        } else {
            //TODO
        }
        
        
        
        return synchLedger;
    }
    
    // called when the request comes from a remote device to synch with server-bound changes or changes from another device 
    // When the request arrives, AutositeLedgerSynchTrackingManager.java returns all LedgerEntry (that contains original ledger) for the device
    // the SynchLedger ids would be passed to copy originalSynchLedger to new one to cover for the device.
    // @see XXXXX AjaxAction  the part else if (hasRequestValue(request, "ajxr", "getjsonsynch")) 
    protected AutositeSynchLedger updateSynchLedgerToConfirmDeviceSynch(HttpServletRequest request, long targetSynchLedgerId) {
        
        AutositeSynchLedger originalSynchLedgerForSourceEvent = AutositeSynchLedgerDS.getInstance().getById(targetSynchLedgerId);
        
        if (originalSynchLedgerForSourceEvent == null) return null;

        HttpSession             session         = request.getSession();
        AutositeSessionContext  sessionContext  = (AutositeSessionContext)getSessionContext(session);
        AutositeRemoteDevice    device          = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(originalSynchLedgerForSourceEvent.getSiteId(), sessionContext.getSourceDeviceId());
        
        AutositeSynchLedger     synchLedger     = new AutositeSynchLedger();
        if ( (originalSynchLedgerForSourceEvent.getDeviceId() == device.getId())) {
            
            if ( originalSynchLedgerForSourceEvent.getSynchId() != null && originalSynchLedgerForSourceEvent.getRemoteToken() != null) 
                return null;
            
            // This seems re-synching.....so returns as is
            m_logger.debug("This ledger " + targetSynchLedgerId + " seems being re-synched ");
            //return originalSynchLedgerForSourceEvent;
            
            // this rarely happens when some action from remote device ends up updating something but status not in remote device itself.
            // likely it is update.
            synchLedger = originalSynchLedgerForSourceEvent;
            
            if ( synchLedger.getScope().endsWith("-update")) {
                
                synchLedger.setSynchId("SU"+System.currentTimeMillis());
                AutositeSynchLedgerDS.getInstance().update(synchLedger);

            } else if ( synchLedger.getScope().endsWith("-delete")) {
                
                synchLedger.setSynchId("SD"+System.currentTimeMillis());
                AutositeSynchLedgerDS.getInstance().update(synchLedger);
            }
            
            return synchLedger;
        }  

        if ( originalSynchLedgerForSourceEvent.getScope().endsWith("-create")){
            
            synchLedger.setDeviceId(device==null?0:device.getId()); 
            synchLedger.setOriginalLedgerId(originalSynchLedgerForSourceEvent.getId());
            
            synchLedger.setSiteId(originalSynchLedgerForSourceEvent.getSiteId());
            synchLedger.setScope(originalSynchLedgerForSourceEvent.getScope());
            
            synchLedger.setObjectId(originalSynchLedgerForSourceEvent.getObjectId());
            synchLedger.setTimeCreated(new TimeNow());
            synchLedger.setTarget(originalSynchLedgerForSourceEvent.getTarget());
            synchLedger.setSynchId("SC"+System.currentTimeMillis());
            AutositeSynchLedgerDS.getInstance().put(synchLedger);
            
            m_logger.info("Synch processed " + synchLedger.getScope() + "/" + synchLedger.getSynchId() + "/"+ synchLedger.getRemoteToken());
        } else if ( originalSynchLedgerForSourceEvent.getScope().endsWith("-update")){

            synchLedger.setDeviceId(device==null?0:device.getId()); 
            synchLedger.setOriginalLedgerId(originalSynchLedgerForSourceEvent.getId());
            
            synchLedger.setSiteId(originalSynchLedgerForSourceEvent.getSiteId());
            synchLedger.setScope(originalSynchLedgerForSourceEvent.getScope());
            
            synchLedger.setObjectId(originalSynchLedgerForSourceEvent.getObjectId());
            synchLedger.setTimeCreated(new TimeNow());
            synchLedger.setTarget(originalSynchLedgerForSourceEvent.getTarget());

            synchLedger.setSynchId("SU"+System.currentTimeMillis());
            AutositeSynchLedgerDS.getInstance().update(synchLedger);
        
        } else if ( originalSynchLedgerForSourceEvent.getScope().endsWith("-delete")){

            
            synchLedger.setOriginalLedgerId(originalSynchLedgerForSourceEvent.getId());
            synchLedger.setDeviceId(device==null?0:device.getId()); 
            
            synchLedger.setSiteId(originalSynchLedgerForSourceEvent.getSiteId());
            synchLedger.setScope(originalSynchLedgerForSourceEvent.getScope());
            
            synchLedger.setObjectId(originalSynchLedgerForSourceEvent.getObjectId());
            synchLedger.setTimeCreated(new TimeNow());
            synchLedger.setTarget(originalSynchLedgerForSourceEvent.getTarget());

            synchLedger.setSynchId("SD"+System.currentTimeMillis());
            AutositeSynchLedgerDS.getInstance().put(synchLedger);

        
        } else {
            synchLedger.setSynchId("SX"+System.currentTimeMillis());
        }
        
        return synchLedger;
    }
    
    // This method gets called to see if the object ( ref by objectId) requires synch up by checking whether there is a complete synchLedger object. 
    //
    // This will return a newly created or incomplete synch ledger, which means the referenced object was not synched-up properly as of the time of calling.
    //
    // 1. if found synched-up and returnOnlyNewSynchLedger is set, this will not return the ledger. Another words, it will return only new ledger.
    // @see XXXXX AjaxAction  the part else if (hasRequestValue(request, "ajxr", "getjson")) // loadAll command from device 

    public AutositeSynchLedger findOrRegisterSynchLedger(HttpServletRequest request, long objectId, String scopePrefix, boolean returnOnlyNewSynchLedger ) {
        HttpSession             session                 = request.getSession();
        Site                    existingSiteInSession   = (Site) session.getAttribute("k_site");

        AutositeSessionContext  sessionContext          = (AutositeSessionContext)getSessionContext(session);
        AutositeRemoteDevice    device                  = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(existingSiteInSession.getId(), sessionContext.getSourceDeviceId());
        DeviceSynchTracker      tracker                 = AutositeLedgerSynchTrackingManager.getInstance().getDeviceTracker(device);
        
        AutositeSynchLedger ledger                      = tracker.findSynchLedgerForTheRecord(scopePrefix, objectId);
        AutositeSynchLedger ledgerCreatedInThisRequest  = (AutositeSynchLedger)request.getAttribute("_synchledger.create");
        AutositeSynchLedger ledgerDeletedInThisRequest  = (AutositeSynchLedger)request.getAttribute("_synchledger.delete");

        // Checking whether there is ledger created in this session. If the ledger is created in this session, it has 
        // synchId and remoteToken. so might be assumed there is nothing to synch. But remote device hasn't had a chance to get synchId because it was created 
        // in this session. So in this case, have to return ledger so that server returns synch Id
        if ( isCreateSynchScope(request) && ledgerCreatedInThisRequest != null) {
            return ledgerCreatedInThisRequest;
        }
        
        if ( isDeleteSynchScope(request) && ledgerDeletedInThisRequest != null) {
            return ledgerDeletedInThisRequest;
        }
        
        
        //SynchLedger found for the record (objectId). If returnOnlyNewSynchLedger set, will not return synchId. It must be bulkLoad call.
        if ( ledger != null) {
            if ( returnOnlyNewSynchLedger ) {
                // This means that the object has been synched and confirmed at this moment. 
                return null;
            }
            else 
                return ledger;
        }
        
        // SynchLedger not found for the record in concern, which means the session bound device has not synched the record or the changes. 
        SynchTrackingEntry synchTrackingEntryToAddress = null;
        
        synchTrackingEntryToAddress = tracker.findSynchEntryForTheObject(scopePrefix + "-create", objectId);    
        
        SynchTrackingEntry synchTrackingEntryForUpdate = tracker.findSynchEntryForTheObject(scopePrefix + "-update", objectId);        
        
        //Check if any un-synched update after the creation has been synched-up. 
        if ( synchTrackingEntryForUpdate != null && synchTrackingEntryForUpdate.getRemoteTok() == null ) {
            synchTrackingEntryToAddress = synchTrackingEntryForUpdate; 
        }
        
        SynchTrackingEntry synchTrackingEntryForDelete = tracker.findSynchEntryForTheObject(scopePrefix + "-delete", objectId);        
        if ( synchTrackingEntryForDelete != null && synchTrackingEntryForDelete.getRemoteTok() == null ) {
            synchTrackingEntryToAddress = synchTrackingEntryForDelete; 
        }
        
        if ( synchTrackingEntryToAddress != null) { 
            AutositeSynchLedger originalSynchLedger = AutositeSynchLedgerDS.getInstance().getById(synchTrackingEntryToAddress.getSynchObjectId());
            return createAndPersistLedgerForThisDeviceWith(device, originalSynchLedger);
        } else {
            return null;
        }
    }
/*
    public Map findOrRegisterSynchLedger(HttpServletRequest request, List list, String scopePrefix, boolean returnOnlyNewSynchLedger ) {
        
        HttpSession             session                 = request.getSession();
        Site                    existingSiteInSession   = (Site) session.getAttribute("k_site");

        AutositeSessionContext  sessionContext          = (AutositeSessionContext)getSessionContext(session);
        AutositeRemoteDevice    device                  = AutositeRemoteDeviceDS.getInstance().getBySiteIdToDeviceId(existingSiteInSession.getId(), sessionContext.getSourceDeviceId());
        DeviceSynchTracker      tracker                 = AutositeLedgerSynchTrackingManager.getInstance().getDeviceTracker(device);
        
        AutositeSynchLedger ledger                      = tracker.findSynchLedgerForTheRecord(scopePrefix, objectId);
        AutositeSynchLedger ledgerCreatedInThisRequest  = (AutositeSynchLedger)request.getAttribute("_synchledger.create");
        AutositeSynchLedger ledgerDeletedInThisRequest  = (AutositeSynchLedger)request.getAttribute("_synchledger.delete");

        // Checking whether there is ledger created in this session. If the ledger is created in this session, it has 
        // synchId and remoteToken. so might be assumed there is nothing to synch. But remote device hasn't had a chance to get synchId because it was created 
        // in this session. So in this case, have to return ledger so that server returns synch Id
        if ( isCreateSynchScope(request) && ledgerCreatedInThisRequest != null) {
            return ledgerCreatedInThisRequest;
        }
        
        if ( isDeleteSynchScope(request) && ledgerDeletedInThisRequest != null) {
            return ledgerDeletedInThisRequest;
        }
        
        
        //SynchLedger found for the record (objectId). If returnOnlyNewSynchLedger set, will not return synchId. It must be bulkLoad call.
        if ( ledger != null) {
            if ( returnOnlyNewSynchLedger ) {
                // This means that the object has been synched and confirmed at this moment. 
                return null;
            }
            else 
                return ledger;
        }
        
        // SynchLedger not found for the record in concern, which means the session bound device has not synched the record or the changes. 
        SynchTrackingEntry synchTrackingEntryToAddress = null;
        
        synchTrackingEntryToAddress = tracker.findSynchEntryForTheObject(scopePrefix + "-create", objectId);    
        
        SynchTrackingEntry synchTrackingEntryForUpdate = tracker.findSynchEntryForTheObject(scopePrefix + "-update", objectId);        
        
        //Check if any un-synched update after the creation has been synched-up. 
        if ( synchTrackingEntryForUpdate != null && synchTrackingEntryForUpdate.getRemoteTok() == null ) {
            synchTrackingEntryToAddress = synchTrackingEntryForUpdate; 
        }
        
        SynchTrackingEntry synchTrackingEntryForDelete = tracker.findSynchEntryForTheObject(scopePrefix + "-delete", objectId);        
        if ( synchTrackingEntryForDelete != null && synchTrackingEntryForDelete.getRemoteTok() == null ) {
            synchTrackingEntryToAddress = synchTrackingEntryForDelete; 
        }
        
        if ( synchTrackingEntryToAddress != null) { 
            AutositeSynchLedger originalSynchLedger = AutositeSynchLedgerDS.getInstance().getById(synchTrackingEntryToAddress.getSynchObjectId());
            return createAndPersistLedgerForThisDeviceWith(device, originalSynchLedger);
        } else {
            return null;
        }
        

    }
*/
    // Should return if there is synchNeeded up the receiving client. 
    private AutositeSynchLedger createAndPersistLedgerForThisDeviceWith(AutositeRemoteDevice device, AutositeSynchLedger originalSynchLedgerForSourceEvent) {
        
        AutositeSynchLedger     synchLedger    = null;
        
        if ( (originalSynchLedgerForSourceEvent.getDeviceId() == device.getId())) {
            
            if ( originalSynchLedgerForSourceEvent.getSynchId() != null && originalSynchLedgerForSourceEvent.getRemoteToken() != null) 
                return null;

            // this rarely happens when some action from remote device ends up updating something but status not in remote device itself.
            // likely it is update.
            synchLedger = originalSynchLedgerForSourceEvent;

            if ( synchLedger.getScope().endsWith("-update")) {
                synchLedger.setSynchId("SU"+System.currentTimeMillis());
                AutositeSynchLedgerDS.getInstance().update(synchLedger);

            } else if ( synchLedger.getScope().endsWith("-delete")) {
                
                synchLedger.setSynchId("SD"+System.currentTimeMillis());
                AutositeSynchLedgerDS.getInstance().update(synchLedger);
            }
            
            return synchLedger;
        }  

        synchLedger     = new AutositeSynchLedger();
        if ( originalSynchLedgerForSourceEvent.getScope().endsWith("-create")){
            
            synchLedger.setDeviceId(device==null?0:device.getId()); 
            
            synchLedger.setSiteId(originalSynchLedgerForSourceEvent.getSiteId());
            synchLedger.setScope(originalSynchLedgerForSourceEvent.getScope());
            
            synchLedger.setObjectId(originalSynchLedgerForSourceEvent.getObjectId());
            synchLedger.setTimeCreated(new TimeNow());
            synchLedger.setTarget(originalSynchLedgerForSourceEvent.getTarget());
            synchLedger.setSynchId("SC"+System.currentTimeMillis());
            AutositeSynchLedgerDS.getInstance().put(synchLedger);
            
            m_logger.info("Synch processed " + synchLedger.getScope() + "/" + synchLedger.getSynchId() + "/"+ synchLedger.getRemoteToken());
        } else if ( originalSynchLedgerForSourceEvent.getScope().endsWith("-update")){

            synchLedger.setDeviceId(device==null?0:device.getId()); 
            
            synchLedger.setSiteId(originalSynchLedgerForSourceEvent.getSiteId());
            synchLedger.setScope(originalSynchLedgerForSourceEvent.getScope());
            
            synchLedger.setObjectId(originalSynchLedgerForSourceEvent.getObjectId());
            synchLedger.setTimeCreated(new TimeNow());
            synchLedger.setTarget(originalSynchLedgerForSourceEvent.getTarget());

            synchLedger.setSynchId("SU"+System.currentTimeMillis());
            AutositeSynchLedgerDS.getInstance().update(synchLedger);
        
        } else if ( originalSynchLedgerForSourceEvent.getScope().endsWith("-delete")){
            
            
            
            
            synchLedger.setSynchId("SD"+System.currentTimeMillis());
        } else {
            synchLedger.setSynchId("SX"+System.currentTimeMillis());
        }
        
        return synchLedger;
    }
    
    
    protected boolean isCreateSynchScope(HttpServletRequest request) {
        if ( isThere(request, "_synchScope")) {
            return request.getParameter("_synchScope").endsWith("-create");
        }
        
        return false;
    }
    
    protected boolean isUpdateSynchScope(HttpServletRequest request) {
        
        if ( isThere(request, "_synchScope")) {
            return request.getParameter("_synchScope").endsWith("-update");
        }
        
        return false;
    }

    protected boolean isDeleteSynchScope(HttpServletRequest request) {
        if ( isThere(request, "_synchScope")) {
            return request.getParameter("_synchScope").endsWith("-delete");
        }
        
        return false;
        
    }

}
