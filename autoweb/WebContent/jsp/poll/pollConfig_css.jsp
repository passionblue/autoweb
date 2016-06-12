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
    PollConfig _PollConfig = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_PollConfig = PollConfigDS.getInstance().getById(id);
		if ( _PollConfig == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _PollConfig = new PollConfig();// PollConfigDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "poll_config_home";

    String _poll_idValue= (reqParams.get("pollId")==null?WebUtil.display(_PollConfig.getPollId()):WebUtil.display((String)reqParams.get("pollId")));
    String _image_thumb_heightValue= (reqParams.get("imageThumbHeight")==null?WebUtil.display(_PollConfig.getImageThumbHeight()):WebUtil.display((String)reqParams.get("imageThumbHeight")));
    String _image_thumb_widthValue= (reqParams.get("imageThumbWidth")==null?WebUtil.display(_PollConfig.getImageThumbWidth()):WebUtil.display((String)reqParams.get("imageThumbWidth")));
    String _image_align_verticalValue= (reqParams.get("imageAlignVertical")==null?WebUtil.display(_PollConfig.getImageAlignVertical()):WebUtil.display((String)reqParams.get("imageAlignVertical")));
    String _backgroundValue= (reqParams.get("background")==null?WebUtil.display(_PollConfig.getBackground()):WebUtil.display((String)reqParams.get("background")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PollConfig.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_PollConfig.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="pollConfigForm" class="formFrame${classSuffix}">
<div id="pollConfigFormInnerFrame" class="formInnerFrame${classSuffix}">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="pollConfigForm_Form" method="POST" action="/pollConfigAction.html" id="pollConfigForm_Form">



    <INPUT TYPE="HIDDEN" NAME="pollId" value="<%=WebUtil.display(_poll_idValue)%>" />





	<div id="pollConfigForm_imageThumbHeight_field" class="formFieldFrame${classSuffix}">
    <div id="pollConfigForm_imageThumbHeight_label" class="formLabel${classSuffix}" >Image Thumb Height </div>
    <div id="pollConfigForm_imageThumbHeight_text" class="formFieldText${classSuffix}" >       
        <input id="imageThumbHeight" class="field" type="text" size="70" name="imageThumbHeight" value="<%=WebUtil.display(_image_thumb_heightValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="pollConfigForm_imageThumbWidth_field" class="formFieldFrame${classSuffix}">
    <div id="pollConfigForm_imageThumbWidth_label" class="formLabel${classSuffix}" >Image Thumb Width </div>
    <div id="pollConfigForm_imageThumbWidth_text" class="formFieldText${classSuffix}" >       
        <input id="imageThumbWidth" class="field" type="text" size="70" name="imageThumbWidth" value="<%=WebUtil.display(_image_thumb_widthValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="pollConfigForm_imageAlignVertical_field" class="formFieldFrame${classSuffix}">
    <div id="pollConfigForm_imageAlignVertical_label" class="formLabel${classSuffix}" >Image Align Vertical </div>
    <div id="pollConfigForm_imageAlignVertical_dropdown" class="formFieldDropDown${classSuffix}" >       
        <select name="imageAlignVertical">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _image_align_verticalValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _image_align_verticalValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="pollConfigForm_background_field" class="formFieldFrame${classSuffix}">
    <div id="pollConfigForm_background_label" class="formLabel${classSuffix}" >Background </div>
    <div id="pollConfigForm_background_text" class="formFieldText${classSuffix}" >       
        <input id="background" class="field" type="text" size="70" name="background" value="<%=WebUtil.display(_backgroundValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div class="submitFrame">

        <div id="pollConfigForm_submit" class="formButton${classSuffix}" >       
            <a id="formSubmit2" href="javascript:document.pollConfigForm_Form.submit();">Submit</a>
        </div>      
<!--
        <div id="pollConfigForm_submit_cancel" class="formButton${classSuffix}" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="pollConfigForm_submit_ext" class="formButton${classSuffix}" >       
            <a href="#">Ext</a>
        </div>      
-->
        <div id="pollConfigForm_submit_cancel" class="formButton${classSuffix}" >       
            <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
        </div>      

        <div id="pollConfigForm_submit_ext" class="formButton${classSuffix}" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollConfig.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>

<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">

<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> 
</div> <!-- form -->


<TABLE class="mytable1">

<TR  >
    <td class="columnTitle"> ID </td>
<%	
	boolean  showListAllByAdmin = true;
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Poll Id </td> 
    <td class="columnTitle">  Image Thumb Height </td> 
    <td class="columnTitle">  Image Thumb Width </td> 
    <td class="columnTitle">  Image Align Vertical </td> 
    <td class="columnTitle">  Background </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> DEL </td>
	<td class="columnTitle"> EDIT </td>
</TR>

<%
   	List list = PollConfigDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollConfig _oPollConfig = (PollConfig) iter.next();
%>

<TR id="tableRow<%= _oPollConfig.getId()%>" >
    <td> <%= _oPollConfig.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oPollConfig.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oPollConfig.getPollId()  %> </td>
	<td> <%= _oPollConfig.getImageThumbHeight()  %> </td>
	<td> <%= _oPollConfig.getImageThumbWidth()  %> </td>
	<td> <%= _oPollConfig.getImageAlignVertical()  %> </td>
	<td> <%= _oPollConfig.getBackground()  %> </td>
	<td> <%= _oPollConfig.getTimeCreated()  %> </td>
	<td> <%= _oPollConfig.getTimeUpdated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/pollConfigAction.html?del=true&id=<%=_oPollConfig.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_oPollConfig.getId()%> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
	<td>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_poll_config_form('<%=_oPollConfig.getId()%>');">Edit</a>
	</td>

</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
		if ($("#tableRow" + msg)) {
			$("#tableRow" + msg).fadeOut(1000);
		}
	}
	function submit_cancel_<%=pagestamp%>(){
		location.href='/moveTo.html?dest=<%=cancelPage%>';
	}	
	function submit_extent_<%=pagestamp%>(){
	}
	function edit_poll_config_form(target){
		location.href='/v_poll_config_form.html?cmd=edit&prv_returnPage=poll_config_home&id=' + target;
	}
</script>