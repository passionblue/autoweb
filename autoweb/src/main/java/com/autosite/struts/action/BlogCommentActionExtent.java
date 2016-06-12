package com.autosite.struts.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.autosite.db.BaseAutositeDataObject;
import com.autosite.db.BlogComment;
import com.autosite.db.BlogPost;
import com.autosite.ds.BlogCommentDS;
import com.autosite.ds.BlogPostDS;
import com.autosite.struts.action.core.AutositeActionExtent;
import com.jtrend.util.WebParamUtil;
import com.jtrend.util.WebUtil;

public class BlogCommentActionExtent extends AutositeActionExtent {

    public void beforeAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogCommentAction#xtent.beforeAdd");		
        BlogComment _BlogComment = (BlogComment)baseDbOject;

        if ( WebUtil.isNull(_BlogComment.getComment())){
            m_logger.debug("comment is missing in this requres " + BlogCommentDS.objectToString(_BlogComment));
            throw new Exception("Comment is missing.");
        }
        
    }

    public void afterAdd(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject)  throws Exception {
		m_logger.debug("#BlogCommentAction#xtent.afterAdd. id=" + baseDbOject.getId());		
        BlogComment _BlogComment = (BlogComment)baseDbOject;
    }

    public void beforeUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogCommentAction#xtent.beforeUpdate. id=" + baseDbOject.getId());		
        BlogComment _BlogComment = (BlogComment)baseDbOject;
    }

    public void afterUpdate(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogCommentAction#xtent.afterUpdate. id=" + baseDbOject.getId());		
        BlogComment _BlogComment = (BlogComment)baseDbOject;
    }

    public void beforeDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogCommentAction#xtent.beforeDelete. id=" + baseDbOject.getId());		
        BlogComment _BlogComment = (BlogComment)baseDbOject;
    }

    public void afterDelete(HttpServletRequest request, HttpServletResponse response, BaseAutositeDataObject baseDbOject) throws Exception{
		m_logger.debug("#BlogCommentAction#xtent.afterDelete. id=" + baseDbOject.getId());		
        BlogComment _BlogComment = (BlogComment)baseDbOject;
    }

    public Map processAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map ret = new HashMap();
        
        String propValue = request.getParameter("ajaxOut");

        if (WebUtil.checkValue(propValue, "getListHtml")){
            m_logger.debug("processing Ajax request to get List in html");
            
            long postId = WebParamUtil.getLongValue(request.getParameter("blogPostId"));
            
            BlogPost post = BlogPostDS.getInstance().getById(postId);
            
            List list = BlogCommentDS.getInstance().getByBlogPostId(postId);
            StringBuffer buf = new StringBuffer();
            
            buf.append("<div id='blogCommentList'>");
            for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                BlogComment blogComm = (BlogComment) iterator.next();
                
                buf.append("<div id='blogCommentFrame"+blogComm.getId()+"'>");
                buf.append("<div id='blogCommentBy'>"+blogComm.getName()+"</div>");
                buf.append("<div id='blogCommentTime'>"+blogComm.getTimeCreated()+"</div>");
                buf.append("<div id='blogComment'><p>"+blogComm.getComment() +"</p></div>");
                buf.append("<a id =\"blogCommentDelete\" rel=\""+blogComm.getId()+"\" href=\"#\"  > <img src='/images/icons/led/cancel.png' style='float:right'/> </a>");
                buf.append("</div>");
                
            }
            buf.append("</div><br/>");
            
            if ( WebUtil.isTrue(request.getParameter("getForm"))){
                
                buf.append("<div id='blogCommentForm'>");
                buf.append("<form name=\"blogCommentForm\" method=\"POST\" action=\"/blogCommentAction.html\" >");
                buf.append("Name (Required)<br/>");
                buf.append("<input id=\"field\" type=\"text\" size=\"40\" name=\"name\" /><input id=\"field\" type=\"password\" size=\"10\" name=\"password\" /><br/>");
                buf.append("Email (Optional)<br/>");
                buf.append("<input id=\"field\" type=\"text\" size=\"40\" name=\"email\" /><br/>");
                buf.append("Comment<br/>");
                buf.append("<TEXTAREA id=\"field\" NAME=\"comment\" COLS=\"70\" ROWS=\"8\" ></TEXTAREA><br/>");

                buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"add\" value=\"true\">");
                buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"blogMainId\" value=\""+postId +"\" >");
                buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"blogPostId\" value=\""+post.getBlogMainId() +"\" >");
                buf.append("<INPUT TYPE=\"HIDDEN\" NAME=\"fwdTo\" value=\"/t_blog_single_post.html?id="+postId+"\" >");

                buf.append("</form>");

                buf.append("<a id=\"formSubmit\" href=\"javascript:document.blogCommentForm.submit();\">Submit</a>");
                buf.append("</div>");                
                
                buf.append("");
                buf.append("");
                buf.append("");
            }
            
            
            ret.put("__value", buf.toString());
        }
        
        return ret;
    }
    
    
	private BlogCommentDS m_ds = BlogCommentDS.getInstance();
    private static Logger m_logger = Logger.getLogger( BlogCommentActionExtent.class);
    
}
