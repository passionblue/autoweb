/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.autosite.struts.action;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.json.JSONObject;

import com.autosite.db.ResourceList;
import com.autosite.db.Site;
import com.autosite.ds.ResourceListDS;
import com.autosite.ds.SiteDS;
import com.autosite.struts.action.core.AutositeCoreAction;
import com.autosite.struts.form.FileupForm;
import com.autosite.util.UploadResourceManager;
import com.jtrend.util.WebParamUtil;

/**
 * MyEclipse Struts Creation date: 07-21-2008
 * 
 * XDoclet definition:
 * 
 * @struts.action path="/fileup" name="fileupForm" input="/jsp/form/fileup.jsp"
 *                scope="request" validate="true"
 * @struts.action-forward name="default" path="/jsp/layout/layout.jsp"
 *                        contextRelative="true"
 */
public class FileupAction extends AutositeCoreAction {
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
    
    public FileupAction(){
        
    }
    public ActionForward ex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, boolean throwException)  throws Exception {
        FileupForm fileupForm = (FileupForm) form;// TODO Auto-generated
        HttpSession session = request.getSession();

        
        // Process the FormFile
        try {
            
            Site site = SiteDS.getInstance().registerSite(request.getServerName());
            m_logger.debug("Starting upload");
            long start = System.currentTimeMillis();
            
            FormFile myFile = fileupForm.getFormFile();
            String contentType = myFile.getContentType();
            String fileName = myFile.getFileName();
            int fileSize = myFile.getFileSize();
            byte[] fileData = myFile.getFileData();
            
            long end = System.currentTimeMillis();
            m_logger.debug("File load took " + (end - start));
            
            m_logger.debug("contentType: " + contentType);
            m_logger.debug("File Name: " + fileName);
            m_logger.debug("File Size: " + fileSize);
            
            int resourceType = WebParamUtil.getIntValue(fileupForm.getFileType());
            String convertedFileName = fileName.replace(" ", "_");
            
            //check image type and file extension matched
            
            int typeByExtension = m_resourceManager.getFileType(convertedFileName);
            
            if ( resourceType ==UploadResourceManager.RESOURCE_TYPE_IMAGE && typeByExtension != resourceType) {
                setPage(session, request.getServerName(), "fileup");
                sessionErrorText(session, "Upload failed. Image type selected. Only jpg/gif/png extension allowed");
                return mapping.findForward("default");
            }
            
            String rootDir = m_resourceManager.getRootDir();
            String webRoot = m_resourceManager.getWebRoot();
            String targetDir = m_resourceManager.getWebUrl(site.getId(), resourceType);
            String url = targetDir + "/" + convertedFileName;
  
            // Same name file exists. need to change the latter one. 
            if (m_resourceDS.getByUrl(url) != null){
                url = targetDir + "/" + System.currentTimeMillis() + "___" + convertedFileName;
            }

            String targetFile = rootDir + "/" + webRoot + "/" + url;
            m_resourceManager.writeToFile(fileData, targetFile);
            m_logger.debug("targetFile=" + targetFile);

            
            ResourceList resourceList = new ResourceList();
            resourceList.setResourceType(resourceType);
            resourceList.setSiteId(site.getId());
            resourceList.setSize(fileSize);
            resourceList.setOriginalName(fileName);
            resourceList.setUrl(url);
            resourceList.setTimeCreaeted(new Timestamp(System.currentTimeMillis()));
            resourceList.setTimeUpdated(new Timestamp(System.currentTimeMillis()));

            if ( resourceType ==UploadResourceManager.RESOURCE_TYPE_IMAGE) {
                ImageIcon image = new ImageIcon(targetFile);
                resourceList.setImageWidth(image.getIconWidth());
                resourceList.setImageHight(image.getIconHeight());
            }
            
            m_resourceDS.put(resourceList);

            sessionTopText(session, "File was uploaded successfully. File name=" + fileName);
            setPage(session, request.getServerName(), "fileup");
            return mapping.findForward("default");
        }
        catch (Exception e) {
            m_logger.error("",e);
            
            if ( throwException ) throw e;
            setPage(session, request.getServerName(), "fileup");
            sessionErrorText(session, "File was too big");
            return mapping.findForward("default");
        }
        
        
    }
    
    public Map exAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        Map ret = new HashMap();
        StringBuffer buf = new StringBuffer();

        if (hasRequestValue(request, "upload", "true")) {    
            
            try {
                ex(mapping, form, request, response, true);
            }
            catch (Exception e) {
                m_logger.error(e.getMessage(),e);
                ret.put("__error", "true");
                ret.put("__errorMsg", e.getMessage());
            }
        }
        
        if (hasRequestValue(request, "ajaxOut", "getmodalform")){

            if (isMissing(request.getParameter("formType")) || request.getParameter("formType").equalsIgnoreCase("default") ){
                buf.append("<FORM Name=\"fileupForm\" ACTION=\"/fileup.html\" ENCTYPE=\"multipart/form-data\" METHOD=POST>");
                buf.append("<input type=\"submit\" value=Submit>");
                buf.append("<INPUT TYPE=\"FILE\" NAME=\"formFile\"><br>");
                buf.append("<input type=\"radio\" name=\"fileType\" value=\"0\"> Other");
                buf.append("<input type=\"radio\" name=\"fileType\" value=\"1\"> Image");
                buf.append("<input type=\"radio\" name=\"fileType\" value=\"2\"> Text File");
                buf.append("</FORM>");
                ret.put("__value", buf.toString());
            } else if (request.getParameter("formType").equalsIgnoreCase("ajaximgupload")){
                buf.append("<FORM Name=\"fileupForm\" id=\"fileupForm\" ACTION=\"/fileup.html\" ENCTYPE=\"multipart/form-data\" METHOD=POST>");
                //buf.append("Name (Optional) <INPUT id="fileNameOption" TYPE=\"text\" NAME="fileName"><br>);
                buf.append("<INPUT id=\"formFile\" TYPE=\"FILE\" size=\"60\" NAME=\"formFile\"><br>");
                buf.append("<button class=\"button\" id=\"buttonUpload\" onclick=\"javascript:ajaxFileUpload('/fileup.html', 'formFile');\">Upload</button>");
                buf.append("<INPUT TYPE=\"hidden\" NAME=\"fileType\" value=\"1\">");
                buf.append("<INPUT TYPE=\"hidden\" NAME=\"upload\" value=\"true\">");
                buf.append("</FORM>");

            }
            
        } else if (hasRequestValue(request, "ajaxOut", "getjson")){
        } else if (hasRequestValue(request, "ajaxOut", "fileloadstop")) {
        
//            String s =  "<script language=\"javascript\" type=\"text/javascript\">window.top.window.stopUpload(1);</script>"; 
            String s =  "<script language=\"javascript\" type=\"text/javascript\">window.top.window.stopUpload(1);</script>"; 
            ret.put("__value", s);

        } else if (hasRequestValue(request, "ajaxOut", "getmodalstatus")){
            // Will handle submission from modal form. It will not display anything but just need to know the status. 
            // if everything was okay, return "0", if not error will be put into.
            
            JSONObject top = new JSONObject();
            top.put("msg", "Successfully received.");
            ret.put("__value", top.toString());
        }        
        return ret;
    }    
    
    protected boolean loginRequired() {
        return false;
    }
    
    private UploadResourceManager m_resourceManager = UploadResourceManager.Instance();
    private ResourceListDS m_resourceDS = ResourceListDS.getInstance();
    private static Logger m_logger = Logger.getLogger(FileupAction.class);
}
