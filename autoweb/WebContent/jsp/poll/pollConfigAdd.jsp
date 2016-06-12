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

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="pollConfigForm" method="post" action="/pollConfigAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Poll Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="pollId" value="<%=WebUtil.display(_poll_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Image Thumb Height</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="imageThumbHeight" value="<%=WebUtil.display(_image_thumb_heightValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Image Thumb Width</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="imageThumbWidth" value="<%=WebUtil.display(_image_thumb_widthValue)%>"/></TD>
	    </TR>
	                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Image Align Vertical</b> &nbsp;</TD>
        <TD>&nbsp;<select name="imageAlignVertical">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _image_align_verticalValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _image_align_verticalValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Background</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="background" value="<%=WebUtil.display(_backgroundValue)%>"/></TD>
	    </TR>
	            	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.pollConfigForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


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
<form name="pollConfigForm<%=_PollConfig.getId()%>" method="post" action="/v_poll_config_edit.html" >
    <a href="javascript:document.pollConfigForm<%=_PollConfig.getId()%>.submit();">Edit</a>           
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