<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    DevNote _DevNote = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_DevNote = DevNoteDS.getInstance().getById(id);
		if ( _DevNote == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _DevNote = new DevNote();// DevNoteDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "dev_note_home";

    String _note_typeValue= (reqParams.get("noteType")==null?WebUtil.display(_DevNote.getNoteType()):WebUtil.display((String)reqParams.get("noteType")));
    String _completedValue= (reqParams.get("completed")==null?WebUtil.display(_DevNote.getCompleted()):WebUtil.display((String)reqParams.get("completed")));
    String _categoryValue= (reqParams.get("category")==null?WebUtil.display(_DevNote.getCategory()):WebUtil.display((String)reqParams.get("category")));
    String _subjectValue= (reqParams.get("subject")==null?WebUtil.display(_DevNote.getSubject()):WebUtil.display((String)reqParams.get("subject")));
    String _noteValue= (reqParams.get("note")==null?WebUtil.display(_DevNote.getNote()):WebUtil.display((String)reqParams.get("note")));
    String _tagsValue= (reqParams.get("tags")==null?WebUtil.display(_DevNote.getTags()):WebUtil.display((String)reqParams.get("tags")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_DevNote.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_DevNote.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="devNoteForm" class="formFrame">
<div id="devNoteFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="devNoteForm_Form" method="POST" action="/devNoteAction.html" id="devNoteForm_Form">






	<div id="devNoteForm_noteType_field" class="formFieldFrame">
    <div id="devNoteForm_noteType_label" class="formLabel" >Note Type </div>
    <div id="devNoteForm_noteType_text" class="formFieldText" >       
        <input id="noteType" class="field" type="text" size="70" name="noteType" value="<%=WebUtil.display(_note_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="devNoteForm_completed_field" class="formFieldFrame">
    <div id="devNoteForm_completed_label" class="formLabel" >Completed </div>
    <div id="devNoteForm_completed_text" class="formFieldText" >       
        <input id="completed" class="field" type="text" size="70" name="completed" value="<%=WebUtil.display(_completedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="devNoteForm_category_field" class="formFieldFrame">
    <div id="devNoteForm_category_label" class="formLabel" >Category </div>
    <div id="devNoteForm_category_text" class="formFieldText" >       
        <input id="category" class="field" type="text" size="70" name="category" value="<%=WebUtil.display(_categoryValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="devNoteForm_subject_field" class="formFieldFrame">
    <div id="devNoteForm_subject_label" class="formLabel" >Subject </div>
    <div id="devNoteForm_subject_text" class="formFieldText" >       
        <input id="subject" class="field" type="text" size="70" name="subject" value="<%=WebUtil.display(_subjectValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="devNoteForm_note_field" class="formFieldFrame">
    <div id="devNoteForm_note_label" class="formLabel" >Note </div>
    <div id="devNoteForm_note_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="note" class="field" NAME="note" COLS="50" ROWS="8" ><%=WebUtil.display(_noteValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>





	<div id="devNoteForm_tags_field" class="formFieldFrame">
    <div id="devNoteForm_tags_label" class="formLabel" >Tags </div>
    <div id="devNoteForm_tags_text" class="formFieldText" >       
        <input id="tags" class="field" type="text" size="70" name="tags" value="<%=WebUtil.display(_tagsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







<div class="submitFrame">

    <div id="devNoteForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.devNoteForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="devNoteForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="devNoteForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="devNoteForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

    <div id="devNoteForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_DevNote.getId()%>">

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


<%
	List list_DevNote = new ArrayList();
	DevNoteDS ds_DevNote = DevNoteDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_DevNote = ds_DevNote.getAll();
	else		
    	list_DevNote = ds_DevNote.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_DevNote, numDisplayInPage, listPage);

	list_DevNote = PagingUtil.getPagedList(pagingInfo, list_DevNote);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>
<!-- =================== END PAGING =================== -->

 
<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
//	if (showListAllByAdmin) {
	if (true) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>

    <td class="columnTitle">  Note Type </td> 
    <td class="columnTitle">  Completed </td> 
    <td class="columnTitle">  Category </td> 
    <td class="columnTitle">  Subject </td> 
    <td class="columnTitle">  Note </td> 
    <td class="columnTitle">  Tags </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_DevNote.iterator();iter.hasNext();) {
        DevNote o_DevNote = (DevNote) iter.next();
%>

<TR id="tableRow<%= o_DevNote.getId()%>">
    <td> <%= o_DevNote.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_DevNote.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_DevNote.getNoteType()  %> </td>
	<td> <%= o_DevNote.getCompleted()  %> </td>
	<td> <%= o_DevNote.getCategory()  %> </td>
	<td> <%= o_DevNote.getSubject()  %> </td>
	<td> <%= o_DevNote.getNote()  %> </td>
	<td> <%= o_DevNote.getTags()  %> </td>
	<td> <%= o_DevNote.getTimeCreated()  %> </td>
	<td> <%= o_DevNote.getTimeUpdated()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_dev_note_form('<%=o_DevNote.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/devNoteAction.html?del=true&id=<%=o_DevNote.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_DevNote.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_dev_note_form('<%=o_DevNote.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
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
	function open_edit_dev_note_form(target){
		location.href='/v_dev_note_form.html?cmd=edit&prv_returnPage=<%=PageViewUtil.getCurrentViewAlias(request)%>&id=' + target;
	}
	function delete_dev_note_form(target){
		javascript:sendFormAjaxSimple('/devNoteAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,target);
	}

</script>


<hr>

<TABLE class="mytable1">

<TR  >
    <td class="columnTitle"> ID </td>
<%	
	boolean  showListAllByAdmin = true;
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Note Type </td> 
    <td class="columnTitle">  Completed </td> 
    <td class="columnTitle">  Category </td> 
    <td class="columnTitle">  Subject </td> 
    <td class="columnTitle">  Note </td> 
    <td class="columnTitle">  Tags </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Time Updated </td> 
	<td class="columnTitle"> DEL </td>
	<td class="columnTitle"> EDIT </td>
</TR>

<%
   	List list = DevNoteDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        DevNote _oDevNote = (DevNote) iter.next();
%>

<TR id="tableRow<%= _oDevNote.getId()%>" >
    <td> <%= _oDevNote.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oDevNote.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= _oDevNote.getNoteType()  %> </td>
	<td> <%= _oDevNote.getCompleted()  %> </td>
	<td> <%= _oDevNote.getCategory()  %> </td>
	<td> <%= _oDevNote.getSubject()  %> </td>
	<td> <%= _oDevNote.getNote()  %> </td>
	<td> <%= _oDevNote.getTags()  %> </td>
	<td> <%= _oDevNote.getTimeCreated()  %> </td>
	<td> <%= _oDevNote.getTimeUpdated()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/devNoteAction.html?del=true&id=<%=_oDevNote.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=_oDevNote.getId()%> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
	<td>
    <a href="javascript:;" title="Edit" class="deleteLink" onclick="edit_dev_note_form('<%=_oDevNote.getId()%>');">Edit</a>
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
	function edit_dev_note_form(target){
		location.href='/v_dev_note_form.html?cmd=edit&prv_returnPage=dev_note_home&id=' + target;
	}
</script>
