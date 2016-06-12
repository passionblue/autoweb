<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    PollConfig _PollConfigDefault = new PollConfig();// PollConfigDS.getInstance().getDeafult();
    
    String _poll_idValue= (reqParams.get("pollId")==null?WebUtil.display(_PollConfigDefault.getPollId()):WebUtil.display((String)reqParams.get("pollId")));
    String _image_thumb_heightValue= (reqParams.get("imageThumbHeight")==null?WebUtil.display(_PollConfigDefault.getImageThumbHeight()):WebUtil.display((String)reqParams.get("imageThumbHeight")));
    String _image_thumb_widthValue= (reqParams.get("imageThumbWidth")==null?WebUtil.display(_PollConfigDefault.getImageThumbWidth()):WebUtil.display((String)reqParams.get("imageThumbWidth")));
    String _image_align_verticalValue= (reqParams.get("imageAlignVertical")==null?WebUtil.display(_PollConfigDefault.getImageAlignVertical()):WebUtil.display((String)reqParams.get("imageAlignVertical")));
    String _backgroundValue= (reqParams.get("background")==null?WebUtil.display(_PollConfigDefault.getBackground()):WebUtil.display((String)reqParams.get("background")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PollConfigDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_PollConfigDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "poll_config_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="pollConfigForm_topArea" class="formTopArea"></div>
<div id="pollConfigForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="pollConfigForm" method="POST" action="/pollConfigAction.html" >



    <INPUT TYPE="HIDDEN" NAME="pollId" value="<%=WebUtil.display(_poll_idValue)%>" />





	<div id="pollConfigForm_imageThumbHeight_field">
    <div id="pollConfigForm_imageThumbHeight_label" class="formLabel" >Image Thumb Height </div>
    <div id="pollConfigForm_imageThumbHeight_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="imageThumbHeight" value="<%=WebUtil.display(_image_thumb_heightValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="pollConfigForm_imageThumbWidth_field">
    <div id="pollConfigForm_imageThumbWidth_label" class="formLabel" >Image Thumb Width </div>
    <div id="pollConfigForm_imageThumbWidth_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="imageThumbWidth" value="<%=WebUtil.display(_image_thumb_widthValue)%>"/>
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
    <div id="pollConfigForm_background_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="background" value="<%=WebUtil.display(_backgroundValue)%>"/>
    </div>      
	</div><div class="clear"></div>









        <div id="pollConfigForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.pollConfigForm.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      
            

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="pollConfigForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = PollConfigDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollConfig _PollConfig = (PollConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PollConfig.getId() %> </td>

    <td> <%= WebUtil.display(_PollConfig.getPollId()) %></td>
    <td> <%= WebUtil.display(_PollConfig.getImageThumbHeight()) %></td>
    <td> <%= WebUtil.display(_PollConfig.getImageThumbWidth()) %></td>
    <td> <%= WebUtil.display(_PollConfig.getImageAlignVertical()) %></td>
    <td> <%= WebUtil.display(_PollConfig.getBackground()) %></td>
    <td> <%= WebUtil.display(_PollConfig.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_PollConfig.getTimeUpdated()) %></td>


<td>
<form name="pollConfigForm<%=_PollConfig.getId()%>2" method="get" action="/v_poll_config_edit2.html" >
    <a href="javascript:document.pollConfigForm<%=_PollConfig.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollConfig.getId() %>">
</form>

</td>
<td>
<a href="/pollConfigAction.html?del=true&id=<%=_PollConfig.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>