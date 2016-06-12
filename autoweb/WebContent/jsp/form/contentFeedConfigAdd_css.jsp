<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ContentFeedConfig _ContentFeedConfigDefault = new ContentFeedConfig();// ContentFeedConfigDS.getInstance().getDeafult();
    
    String _feed_categoryValue= (reqParams.get("feedCategory")==null?WebUtil.display(_ContentFeedConfigDefault.getFeedCategory()):WebUtil.display((String)reqParams.get("feedCategory")));
    String _feed_typeValue= (reqParams.get("feedType")==null?WebUtil.display(_ContentFeedConfigDefault.getFeedType()):WebUtil.display((String)reqParams.get("feedType")));
    String _display_typeValue= (reqParams.get("displayType")==null?WebUtil.display(_ContentFeedConfigDefault.getDisplayType()):WebUtil.display((String)reqParams.get("displayType")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ContentFeedConfigDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="contentFeedConfigForm_topArea" class="formTopArea"></div>
<div id="contentFeedConfigForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="contentFeedConfigForm" method="get" action="/contentFeedConfigAction.html" >




	<div id="contentFeedConfigForm_feedCategory_field">
    <div id="contentFeedConfigForm_feedCategory_label" class="formRequiredLabel" >Feed Category* </div>
    <div id="contentFeedConfigForm_feedCategory_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="feedCategory" value="<%=WebUtil.display(_feed_categoryValue)%>"/> 
    </div>      
	</div><div class="clear"></div>



	<div id="contentFeedConfigForm_feedType_field">
    <div id="contentFeedConfigForm_feedType_label" class="formLabel" >Feed Type </div>
    <div id="contentFeedConfigForm_feedType_dropdown" class="formFieldDropDown" >       
        <select id="field" name="feedType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _feed_typeValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _feed_typeValue)%>>0</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _feed_typeValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _feed_typeValue)%>>2</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _feed_typeValue)%>>3</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _feed_typeValue)%>>4</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _feed_typeValue)%>>5</option>
        <option value="6" <%=HtmlUtil.getOptionSelect("6", _feed_typeValue)%>>6</option>
        <option value="7" <%=HtmlUtil.getOptionSelect("7", _feed_typeValue)%>>7</option>
        <option value="8" <%=HtmlUtil.getOptionSelect("8", _feed_typeValue)%>>8</option>
        <option value="9" <%=HtmlUtil.getOptionSelect("9", _feed_typeValue)%>>9</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="contentFeedConfigForm_displayType_field">
    <div id="contentFeedConfigForm_displayType_label" class="formLabel" >Display Type </div>
    <div id="contentFeedConfigForm_displayType_dropdown" class="formFieldDropDown" >       
        <select id="field" name="displayType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _display_typeValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>





        <div id="contentFeedConfigForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.contentFeedConfigForm.submit();">Submit</a>
        </div>      

        <div id="contentFeedConfigForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.contentFeedConfigForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="contentFeedConfigForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ContentFeedConfigDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentFeedConfig _ContentFeedConfig = (ContentFeedConfig) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentFeedConfig.getId() %> </td>

    <td> <%= WebUtil.display(_ContentFeedConfig.getFeedCategory()) %></td>
    <td> <%= WebUtil.display(_ContentFeedConfig.getFeedType()) %></td>
    <td> <%= WebUtil.display(_ContentFeedConfig.getDisplayType()) %></td>
    <td> <%= WebUtil.display(_ContentFeedConfig.getTimeCreated()) %></td>


<td>
<form name="contentFeedConfigForm<%=_ContentFeedConfig.getId()%>" method="get" action="/v_content_feed_config_edit.html" >
    <a href="javascript:document.contentFeedConfigForm<%=_ContentFeedConfig.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedConfig.getId() %>">
</form>
<form name="contentFeedConfigForm<%=_ContentFeedConfig.getId()%>2" method="get" action="/v_content_feed_config_edit2.html" >
    <a href="javascript:document.contentFeedConfigForm<%=_ContentFeedConfig.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentFeedConfig.getId() %>">
</form>

</td>
<td>
<a href="/contentFeedConfigAction.html?del=true&id=<%=_ContentFeedConfig.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>