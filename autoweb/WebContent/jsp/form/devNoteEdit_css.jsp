<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
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

    DevNote _DevNote = DevNoteDS.getInstance().getById(id);

    if ( _DevNote == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "dev_note_home";

    String _note_typeValue=  WebUtil.display(_DevNote.getNoteType());
    String _completedValue=  WebUtil.display(_DevNote.getCompleted());
    String _categoryValue=  WebUtil.display(_DevNote.getCategory());
    String _subjectValue=  WebUtil.display(_DevNote.getSubject());
    String _noteValue=  WebUtil.display(_DevNote.getNote());
    String _tagsValue=  WebUtil.display(_DevNote.getTags());
    String _time_createdValue=  WebUtil.display(_DevNote.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_DevNote.getTimeUpdated());
%> 

<br>
<div id="devNoteForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="devNoteFormEdit" method="POST" action="/devNoteAction.html" >




	<div id="devNoteForm_noteType_field">
    <div id="devNoteForm_noteType_label" class="formLabel" >Note Type </div>
    <div id="devNoteForm_noteType_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="noteType" value="<%=WebUtil.display(_note_typeValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="devNoteForm_completed_field">
    <div id="devNoteForm_completed_label" class="formLabel" >Completed </div>
    <div id="devNoteForm_completed_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="completed" value="<%=WebUtil.display(_completedValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="devNoteForm_category_field">
    <div id="devNoteForm_category_label" class="formLabel" >Category </div>
    <div id="devNoteForm_category_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="category" value="<%=WebUtil.display(_categoryValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="devNoteForm_subject_field">
    <div id="devNoteForm_subject_label" class="formLabel" >Subject </div>
    <div id="devNoteForm_subject_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="subject" value="<%=WebUtil.display(_subjectValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="devNoteForm_note_field">
    <div id="devNoteForm_note_label" class="formLabel" >Note </div>
    <div id="devNoteForm_note_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="note" COLS="50" ROWS="8" ><%=WebUtil.display(_noteValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>



	<div id="devNoteForm_tags_field">
    <div id="devNoteForm_tags_label" class="formLabel" >Tags </div>
    <div id="devNoteForm_tags_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="tags" value="<%=WebUtil.display(_tagsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





        <div id="devNoteFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.devNoteFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_DevNote.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
