<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    DevNote _DevNoteDefault = new DevNote();// DevNoteDS.getInstance().getDeafult();
    
    String _note_typeValue= (reqParams.get("noteType")==null?WebUtil.display(_DevNoteDefault.getNoteType()):WebUtil.display((String)reqParams.get("noteType")));
    String _completedValue= (reqParams.get("completed")==null?WebUtil.display(_DevNoteDefault.getCompleted()):WebUtil.display((String)reqParams.get("completed")));
    String _categoryValue= (reqParams.get("category")==null?WebUtil.display(_DevNoteDefault.getCategory()):WebUtil.display((String)reqParams.get("category")));
    String _subjectValue= (reqParams.get("subject")==null?WebUtil.display(_DevNoteDefault.getSubject()):WebUtil.display((String)reqParams.get("subject")));
    String _noteValue= (reqParams.get("note")==null?WebUtil.display(_DevNoteDefault.getNote()):WebUtil.display((String)reqParams.get("note")));
    String _tagsValue= (reqParams.get("tags")==null?WebUtil.display(_DevNoteDefault.getTags()):WebUtil.display((String)reqParams.get("tags")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_DevNoteDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_DevNoteDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "dev_note_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="devNoteForm_topArea" class="formTopArea"></div>
<div id="devNoteForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="devNoteForm" method="POST" action="/devNoteAction.html" >




	<div id="devNoteForm_noteType_field">
    <div id="devNoteForm_noteType_label" class="formLabel" >Note Type </div>
    <div id="devNoteForm_noteType_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="noteType" value="<%=WebUtil.display(_note_typeValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="devNoteForm_completed_field">
    <div id="devNoteForm_completed_label" class="formLabel" >Completed </div>
    <div id="devNoteForm_completed_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="completed" value="<%=WebUtil.display(_completedValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="devNoteForm_category_field">
    <div id="devNoteForm_category_label" class="formLabel" >Category </div>
    <div id="devNoteForm_category_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="category" value="<%=WebUtil.display(_categoryValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="devNoteForm_subject_field">
    <div id="devNoteForm_subject_label" class="formLabel" >Subject </div>
    <div id="devNoteForm_subject_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="subject" value="<%=WebUtil.display(_subjectValue)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="devNoteForm_note_field">
    <div id="devNoteForm_note_label" class="formLabel" >Note </div>
    <div id="devNoteForm_note_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="note" COLS="50" ROWS="8" ><%=WebUtil.display(_noteValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>





	<div id="devNoteForm_tags_field">
    <div id="devNoteForm_tags_label" class="formLabel" >Tags </div>
    <div id="devNoteForm_tags_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="tags" value="<%=WebUtil.display(_tagsValue)%>"/>
    </div>      
	</div><div class="clear"></div>









        <div id="devNoteForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.devNoteForm.submit();">Submit</a>
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
<div id="devNoteForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = DevNoteDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        DevNote _DevNote = (DevNote) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _DevNote.getId() %> </td>

    <td> <%= WebUtil.display(_DevNote.getNoteType()) %></td>
    <td> <%= WebUtil.display(_DevNote.getCompleted()) %></td>
    <td> <%= WebUtil.display(_DevNote.getCategory()) %></td>
    <td> <%= WebUtil.display(_DevNote.getSubject()) %></td>
    <td> <%= WebUtil.display(_DevNote.getNote()) %></td>
    <td> <%= WebUtil.display(_DevNote.getTags()) %></td>
    <td> <%= WebUtil.display(_DevNote.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_DevNote.getTimeUpdated()) %></td>


<td>
<form name="devNoteForm<%=_DevNote.getId()%>2" method="get" action="/v_dev_note_edit2.html" >
    <a href="javascript:document.devNoteForm<%=_DevNote.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _DevNote.getId() %>">
</form>

</td>
<td>
<a href="/devNoteAction.html?del=true&id=<%=_DevNote.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>