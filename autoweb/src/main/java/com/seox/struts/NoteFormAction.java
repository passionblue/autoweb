/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.seox.struts;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jtrend.struts.core.LoginRequiredJtrendAction;
import com.seox.db.Category;
import com.seox.db.Note;
import com.seox.struts.form.NoteForm;
import com.seox.work.NoteDS;
import com.seox.work.UserBO;
import com.seox.work.UserBOPool;

/**
 * MyEclipse Struts Creation date: 11-27-2006
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/note_form_submit" name="noteform"
 *                input="/jsp/model/note.jsp" scope="request" validate="true"
 * @struts.action-forward name="success" path="/jsp/layout/layout.jsp"
 *                        contextRelative="true"
 */
public class NoteFormAction extends LoginRequiredJtrendAction {
    /*
     * Generated Methods
     */

    /**
     * Method execute
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        NoteForm noteform = (NoteForm) form;// TODO Auto-generated method stub
        HttpSession session = request.getSession();

        String cmd = noteform.getCmd();

        if (cmd == null) {
            session.setAttribute("k_top_text", "Internal error occurred. Please try again");
        }

        
        NoteDS noteDs = NoteDS.getInstance();
        UserBO userBO = getUserBO(session);
        List list = noteDs.getNotesByUser(new Long(userBO.getUserObj().getUserId()));

        if (cmd.equalsIgnoreCase("add")) {
            if (isMissing(noteform.getNote())) {
                session.setAttribute("k_top_text", "Note string was not entered.");
            }
            else {
                Note note = new Note();
                note.setNoteStr(noteform.getNote().trim());
                note.setNoteEnteredT(new Date());

                if (noteDs.newNote(userBO.getUserObj(), note)) {
                    session.setAttribute("k_top_text", "Note was successfully added");
                }
                else {
                    session.setAttribute("k_top_text", "Internal error occurred. Note was not entered. Please try again");
                }
            }
        }
        else if (cmd.equalsIgnoreCase("del")) {
            if (isMissing(noteform.getNote())) {
                session.setAttribute("k_top_text", "Internal error occurred. Please try again");
            }
            else {
                long id = Long.parseLong(noteform.getNote());
                Note note = noteDs.getNote(new Long(id));
                if (!noteDs.deleteNote(userBO.getUserObj(), note)) {
                    session.setAttribute("k_top_text", "Failed to delete Note. Please try again");
                }
            }
        }

        List notes = noteDs.getNotesByUser(new Long(userBO.getUserObj().getUserId()));

        noteform.setNote(null);
        setSession(session, "k_notes", notes);
        setPage(session, "note");
        return mapping.findForward("success");
    }
    private static Logger m_logger = Logger.getLogger(LoginFormAction.class);

}
