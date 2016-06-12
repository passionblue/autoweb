<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    ContentAd _ContentAd = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ContentAd = ContentAdDS.getInstance().getById(id);
		if ( _ContentAd == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ContentAd = new ContentAd();// ContentAdDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    

    String _content_idValue= (reqParams.get("contentId")==null?WebUtil.display(_ContentAd.getContentId()):WebUtil.display((String)reqParams.get("contentId")));
    String _position_codeValue= (reqParams.get("positionCode")==null?WebUtil.display(_ContentAd.getPositionCode()):WebUtil.display((String)reqParams.get("positionCode")));
    String _ad_contentValue= (reqParams.get("adContent")==null?WebUtil.display(_ContentAd.getAdContent()):WebUtil.display((String)reqParams.get("adContent")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_ContentAd.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ContentAd.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_ContentAd.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
%> 

<br>
<div id="contentAdForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="contentAdFormEdit" method="POST" action="/contentAdAction.html" >


    <INPUT TYPE="HIDDEN" NAME="contentId" value="<%=WebUtil.display(_content_idValue)%>" />



	<div id="contentAdForm_positionCode_field">
    <div id="contentAdForm_positionCode_label" class="formLabel" >Position Code </div>
    <div id="contentAdForm_positionCode_text" class="formFieldText" >       
        <input id="field" type="text" size="30" name="positionCode" value="<%=WebUtil.display(_position_codeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="contentAdForm_adContent_field">
    <div id="contentAdForm_adContent_label" class="formLabel" >Ad Content </div>
    <div id="contentAdForm_adContent_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="adContent" COLS="50" ROWS="8" ><%=WebUtil.display(_ad_contentValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>



	<div id="contentAdForm_hide_field">
    <div id="contentAdForm_hide_label" class="formLabel" >Hide </div>
    <div id="contentAdForm_hide_dropdown" class="formFieldDropDown" >       
        <select name="hide">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hideValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hideValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>







        <div id="contentAdFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.contentAdFormEdit.submit();">Submit</a>
        </div>      

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentAd.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>


<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
