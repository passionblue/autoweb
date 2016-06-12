<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    ContentData _ContentData = ContentDataDS.getInstance().getById(id);

    if ( _ContentData == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "content_data_home";

    String _content_idValue=  WebUtil.display(_ContentData.getContentId());
    String _dataValue=  WebUtil.display(_ContentData.getData());
    String _option1Value=  WebUtil.display(_ContentData.getOption1());
    String _option2Value=  WebUtil.display(_ContentData.getOption2());
    String _option3Value=  WebUtil.display(_ContentData.getOption3());
    String _time_createdValue=  WebUtil.display(_ContentData.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_ContentData.getTimeUpdated());
%> 

<br>
<div id="contentDataForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="contentDataFormEdit" method="POST" action="/contentDataAction.html" >




	<div id="contentDataForm_contentId_field">
    <div id="contentDataForm_contentId_label" class="formLabel" >Content Id </div>
    <div id="contentDataForm_contentId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="contentId" value="<%=WebUtil.display(_content_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="contentDataForm_data_field">
    <div id="contentDataForm_data_label" class="formLabel" >Data </div>
    <div id="contentDataForm_data_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="data" COLS="50" ROWS="8" ><%=WebUtil.display(_dataValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>



	<div id="contentDataForm_option1_field">
    <div id="contentDataForm_option1_label" class="formLabel" >Option1 </div>
    <div id="contentDataForm_option1_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="option1" value="<%=WebUtil.display(_option1Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="contentDataForm_option2_field">
    <div id="contentDataForm_option2_label" class="formLabel" >Option2 </div>
    <div id="contentDataForm_option2_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="option2" value="<%=WebUtil.display(_option2Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="contentDataForm_option3_field">
    <div id="contentDataForm_option3_label" class="formLabel" >Option3 </div>
    <div id="contentDataForm_option3_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="option3" value="<%=WebUtil.display(_option3Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





        <div id="contentDataFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.contentDataFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentData.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
