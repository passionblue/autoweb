<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SiteHeader _SiteHeaderDefault = new SiteHeader();// SiteHeaderDS.getInstance().getDeafult();
    
    String _as_isValue= (reqParams.get("asIs")==null?WebUtil.display(_SiteHeaderDefault.getAsIs()):WebUtil.display((String)reqParams.get("asIs")));
    String _include_typeValue= (reqParams.get("includeType")==null?WebUtil.display(_SiteHeaderDefault.getIncludeType()):WebUtil.display((String)reqParams.get("includeType")));
    String _include_textValue= (reqParams.get("includeText")==null?WebUtil.display(_SiteHeaderDefault.getIncludeText()):WebUtil.display((String)reqParams.get("includeText")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SiteHeaderDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_SiteHeaderDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "site_header_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="siteHeaderForm_topArea" class="formTopArea"></div>
<div id="siteHeaderForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="siteHeaderForm" method="POST" action="/siteHeaderAction.html" >



	<div id="siteHeaderForm_asIs_field">
    <div id="siteHeaderForm_asIs_label" class="formLabel" >As Is </div>
    <div id="siteHeaderForm_asIs_dropdown" class="formFieldDropDown" >       
        <select name="asIs">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _as_isValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _as_isValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="siteHeaderForm_includeType_field">
    <div id="siteHeaderForm_includeType_label" class="formLabel" >Include Type </div>
    <div id="siteHeaderForm_includeType_dropdown" class="formFieldDropDown" >       
        <select id="field" name="includeType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _include_typeValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _include_typeValue)%>>Default</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _include_typeValue)%>>ScriptLink</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _include_typeValue)%>>ScriptText</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _include_typeValue)%>>StyleLink</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _include_typeValue)%>>StyleText</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="siteHeaderForm_includeText_field">
    <div id="siteHeaderForm_includeText_label" class="formLabel" >Include Text </div>
    <div id="siteHeaderForm_includeText_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="includeText" COLS="50" ROWS="8" ><%=WebUtil.display(_include_textValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>










        <div id="siteHeaderForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.siteHeaderForm.submit();">Submit</a>
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
<div id="siteHeaderForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = SiteHeaderDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        SiteHeader _SiteHeader = (SiteHeader) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SiteHeader.getId() %> </td>

    <td> <%= WebUtil.display(_SiteHeader.getAsIs()) %></td>
    <td> <%= WebUtil.display(_SiteHeader.getIncludeType()) %></td>
    <td> <%= WebUtil.display(_SiteHeader.getIncludeText()) %></td>
    <td> <%= WebUtil.display(_SiteHeader.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_SiteHeader.getTimeUpdated()) %></td>


<td>
<form name="siteHeaderForm<%=_SiteHeader.getId()%>2" method="get" action="/v_site_header_edit2.html" >
    <a href="javascript:document.siteHeaderForm<%=_SiteHeader.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SiteHeader.getId() %>">
</form>

</td>
<td>
<a href="/siteHeaderAction.html?del=true&id=<%=_SiteHeader.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>