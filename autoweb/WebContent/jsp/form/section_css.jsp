<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    SectionDataHolder _Section = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_Section = SectionDS.getInstance().getById(id);
		if ( _Section == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _Section = new SectionDataHolder();// SectionDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "section_home";

    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_Section.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _main_page_idValue= (reqParams.get("mainPageId")==null?WebUtil.display(_Section.getMainPageId()):WebUtil.display((String)reqParams.get("mainPageId")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_Section.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="sectionForm" class="formFrame">
<div id="sectionFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="sectionForm_Form" method="POST" action="/sectionAction.html" id="sectionForm_Form">





	<div id="sectionForm_title_field" class="formFieldFrame">
    <div id="sectionForm_title_label" class="formLabel" >Title </div>
    <div id="sectionForm_title_text" class="formFieldText" >       
        <input id="title" class="field" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="sectionForm_mainPageId_field" class="formFieldFrame">
    <div id="sectionForm_mainPageId_label" class="formLabel" >Main Page Id </div>
    <div id="sectionForm_mainPageId_text" class="formFieldText" >       
        <input id="mainPageId" class="field" type="text" size="70" name="mainPageId" value="<%=WebUtil.display(_main_page_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div class="submitFrame">

        <div id="sectionForm_submit" class="formButton" >       
            <a id="formSubmit2" href="javascript:document.sectionForm_Form.submit();">Submit</a>
        </div>      
<!--
        <div id="sectionForm_submit_cancel" class="formButton" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="sectionForm_submit_ext" class="formButton" >       
            <a href="#">Ext</a>
        </div>      
-->
        <div id="sectionForm_submit_cancel" class="formButton" >       
            <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
        </div>      

        <div id="sectionForm_submit_ext" class="formButton" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Section.getId()%>">

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

<TR >
    <td class="columnTitle"> ID </td>
<%	
	boolean  showListAllByAdmin = true;
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Title </td> 
    <td class="columnTitle">  Main Page Id </td> 
    <td class="columnTitle">  Time Created </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = SectionDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SectionDataHolder _oSection = (SectionDataHolder) iter.next();
%>

<TR>
    <td> <%= _oSection.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oSection.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oSection.getTitle()  %> </td>
	<td> <%= _oSection.getMainPageId()  %> </td>
	<td> <%= _oSection.getTimeCreated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/sectionAction.html?del=true&id=<%=_oSection.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
	}
	function submit_cancel_<%=pagestamp%>(){
		location.href='/moveTo.html?dest=<%=cancelPage%>';
	}	
	function submit_extent_<%=pagestamp%>(){
	}
</script>