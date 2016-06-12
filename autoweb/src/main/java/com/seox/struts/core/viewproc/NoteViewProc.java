/*
 * Created on Nov 30, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.struts.core.viewproc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jtrend.session.PageView;
import com.jtrend.struts.core.viewproc.ViewProc;
import com.seox.work.NoteDS;
import com.seox.work.UserBO;

public class NoteViewProc implements ViewProc{
    
    private static Logger m_logger = Logger.getLogger(NoteViewProc.class);
    
    public String getName() {
        return "NoteViewProc";
    }
    
    public void process(HttpServletRequest request, HttpSession session, boolean nocache) throws Exception{

        UserBO userBO = (UserBO) session.getAttribute("k_userbo");
        
        List notes = NoteDS.getInstance().getNotesByUser(new Long(userBO.getUserObj().getUserId()));
        session.setAttribute("k_notes", notes);
    }
    public void process(HttpServletRequest request, HttpSession session, PageView viewPage, boolean nocache) throws Exception {
    }
}
