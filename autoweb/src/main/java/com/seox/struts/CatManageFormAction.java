/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.seox.struts;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jtrend.struts.core.JtrendAction;
import com.jtrend.struts.core.LoginRequiredJtrendAction;
import com.seox.db.Category;
import com.seox.struts.form.CatManageForm;
import com.seox.work.CategoryDS;
import com.seox.work.UserBO;

/** 
 * MyEclipse Struts
 * Creation date: 11-22-2006
 * 
 * XDoclet definition:
 * @struts.action path="/cat_manage_form_submit" name="catmanageform" input="/jsp/model/cat_manage_form.jsp" scope="request" validate="true"
 * @struts.action-forward name="success" path="/jsp/layout/layout.jsp" contextRelative="true"
 */
public class CatManageFormAction extends LoginRequiredJtrendAction {
    /*
     * Generated Methods
     */

    /** 
     * Method execute
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        
        CatManageForm catmanageform = (CatManageForm) form;// TODO Auto-generated method stub
        HttpSession session = request.getSession();

        String cmd = catmanageform.getSubcmd();
        
        if (cmd == null) {
            session.setAttribute("k_top_text", "Internal error occurred. Please try again");
        }
        
        if (cmd.equalsIgnoreCase("add")) {
            if ( isMissing(catmanageform.getCategory()) ) {
                session.setAttribute("k_top_text", "Category name was not entered");
            }
            else {
                Category cat = new Category();
                cat.setCategoryName(catmanageform.getCategory().trim());
                cat.setEnteredT(new Date());
                UserBO userBO = getUserBO(session);
                
                if ( userBO.addCategory(cat) ) {
                    session.setAttribute("k_top_text", "Category " + cat.getCategoryName() + " was successfully added");
                } else {
                    session.setAttribute("k_top_text", "Internal error occurred. Category " + cat.getCategoryName() + " was not added. Please try again");
                }

            }
        }else  if (cmd.equalsIgnoreCase("del")) {
            if ( isMissing(catmanageform.getCategory()) ) {
                session.setAttribute("k_top_text", "Internal error occurred. Please try again");
            }
            else {
                long id = Long.parseLong(catmanageform.getCategory());
                UserBO userBO = getUserBO(session);
                if (!userBO.deleteCategoryById(new Long(id)) ){
                    session.setAttribute("k_top_text", "Failed to delete category. Please try again");
                }
            }
        }
        
        catmanageform.setCategory(null); //TODO dont know why but had to clean the field
        setPage(session, "category");
        return mapping.findForward("success");   
    }
}