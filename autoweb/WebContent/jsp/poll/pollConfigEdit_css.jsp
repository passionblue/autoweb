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

    PollConfig _PollConfig = PollConfigDS.getInstance().getById(id);

    if ( _PollConfig == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "poll_config_home";

    String _poll_idValue=  WebUtil.display(_PollConfig.getPollId());
    String _image_thumb_heightValue=  WebUtil.display(_PollConfig.getImageThumbHeight());
    String _image_thumb_widthValue=  WebUtil.display(_PollConfig.getImageThumbWidth());
    String _image_align_verticalValue=  WebUtil.display(_PollConfig.getImageAlignVertical());
    String _backgroundValue=  WebUtil.display(_PollConfig.getBackground());
    String _time_createdValue=  WebUtil.display(_PollConfig.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_PollConfig.getTimeUpdated());
%> 

<br>
<div id="pollConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pollConfigFormEdit" method="POST" action="/pollConfigAction.html" >


    <INPUT TYPE="HIDDEN" NAME="pollId" value="<%=WebUtil.display(_poll_idValue)%>" />




	<div id="pollConfigForm_imageThumbHeight_field">
    <div id="pollConfigForm_imageThumbHeight_label" class="formLabel" >Image Thumb Height </div>
    <div id="pollConfigForm_imageThumbHeight_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="imageThumbHeight" value="<%=WebUtil.display(_image_thumb_heightValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollConfigForm_imageThumbWidth_field">
    <div id="pollConfigForm_imageThumbWidth_label" class="formLabel" >Image Thumb Width </div>
    <div id="pollConfigForm_imageThumbWidth_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="imageThumbWidth" value="<%=WebUtil.display(_image_thumb_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="pollConfigForm_imageAlignVertical_field">
    <div id="pollConfigForm_imageAlignVertical_label" class="formLabel" >Image Align Vertical </div>
    <div id="pollConfigForm_imageAlignVertical_dropdown" class="formFieldDropDown" >       
        <select name="imageAlignVertical">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _image_align_verticalValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _image_align_verticalValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="pollConfigForm_background_field">
    <div id="pollConfigForm_background_label" class="formLabel" >Background </div>
    <div id="pollConfigForm_background_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="background" value="<%=WebUtil.display(_backgroundValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





        <div id="pollConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pollConfigFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollConfig.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
