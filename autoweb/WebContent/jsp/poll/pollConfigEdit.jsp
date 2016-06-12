<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    PollConfig _PollConfig = PollConfigDS.getInstance().getById(id);

    if ( _PollConfig == null ) {
        return;
    }

    String _poll_idValue=  WebUtil.display(_PollConfig.getPollId());
    String _image_thumb_heightValue=  WebUtil.display(_PollConfig.getImageThumbHeight());
    String _image_thumb_widthValue=  WebUtil.display(_PollConfig.getImageThumbWidth());
    String _image_align_verticalValue=  WebUtil.display(_PollConfig.getImageAlignVertical());
    String _backgroundValue=  WebUtil.display(_PollConfig.getBackground());
    String _time_createdValue=  WebUtil.display(_PollConfig.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_PollConfig.getTimeUpdated());
%> 

<br>
<form name="pollConfigFormEdit" method="post" action="/pollConfigAction.html" >
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
            <b><a href="javascript:document.pollConfigFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollConfig.getId()%>">
</form>
