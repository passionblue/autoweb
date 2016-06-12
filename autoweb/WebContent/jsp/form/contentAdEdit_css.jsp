<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
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

    ContentAd _ContentAd = ContentAdDS.getInstance().getById(id);

    if ( _ContentAd == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    

    String _content_idValue=  WebUtil.display(_ContentAd.getContentId());
    String _position_codeValue=  WebUtil.display(_ContentAd.getPositionCode());
    String _ad_contentValue=  WebUtil.display(_ContentAd.getAdContent());
    String _hideValue=  WebUtil.display(_ContentAd.getHide());
    String _time_createdValue=  WebUtil.display(_ContentAd.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_ContentAd.getTimeUpdated());
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

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentAd.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
