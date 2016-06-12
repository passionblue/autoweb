<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    DevTable _DevTable = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_DevTable = DevTableDS.getInstance().getById(id);
		if ( _DevTable == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _DevTable = new DevTable();// DevTableDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "dev_table_home";

    String _dev_note_idValue= (reqParams.get("devNoteId")==null?WebUtil.display(_DevTable.getDevNoteId()):WebUtil.display((String)reqParams.get("devNoteId")));
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_DevTable.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _subjectValue= (reqParams.get("subject")==null?WebUtil.display(_DevTable.getSubject()):WebUtil.display((String)reqParams.get("subject")));
    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_DevTable.getData()):WebUtil.display((String)reqParams.get("data")));
    String _typeValue= (reqParams.get("type")==null?WebUtil.display(_DevTable.getType()):WebUtil.display((String)reqParams.get("type")));
    String _disableValue= (reqParams.get("disable")==null?WebUtil.display(_DevTable.getDisable()):WebUtil.display((String)reqParams.get("disable")));
    String _radio_valueValue= (reqParams.get("radioValue")==null?WebUtil.display(_DevTable.getRadioValue()):WebUtil.display((String)reqParams.get("radioValue")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_DevTable.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_DevTable.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
%> 

<br>
<div id="devTableForm" class="formFrame">
<div id="devTableFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="devTableForm_Form" method="POST" action="/devTableAction.html" id="devTableForm_Form">



	<div id="devTableForm_devNoteId_field" class="formFieldFrame">
    <div id="devTableForm_devNoteId_label" class="formLabel" >Dev Note Id </div>
    <div id="devTableForm_devNoteId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="devNoteId" id="devNoteId">
        <option value="" >- Please Select -</option>
<%
	List _listDevNote_devNoteId = DevNoteDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listDevNote_devNoteId.iterator(); iter.hasNext();){
		DevNote _obj = (DevNote) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_dev_note_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getNote() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="devTableForm_title_field" class="formFieldFrame">
    <div id="devTableForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="devTableForm_title_text" class="formFieldText" >       
        <input id="title" class="requiredField" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="devTableForm_subject_field" class="formFieldFrame">
    <div id="devTableForm_subject_label" class="formLabel" >Subject </div>
    <div id="devTableForm_subject_text" class="formFieldText" >       
        <input id="subject" class="field" type="text" size="70" name="subject" value="<%=WebUtil.display(_subjectValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="devTableForm_data_field" class="formFieldFrame">
    <div id="devTableForm_data_label" class="formLabel" >Data </div>
    <div id="devTableForm_data_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="data" class="field" NAME="data" COLS="50" ROWS="8" ><%=WebUtil.display(_dataValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>



	<div id="devTableForm_type_field" class="formFieldFrame">
    <div id="devTableForm_type_label" class="formLabel" >Type </div>
    <div id="devTableForm_type_dropdown" class="formFieldDropDown" >       
        <select class="requiredField" name="type" id="type">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _typeValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _typeValue)%>>Newyork</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _typeValue)%>>Ca</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _typeValue)%>>seela</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _typeValue)%>>Ocean</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _typeValue)%>>5</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="devTableForm_disable_field" class="formFieldFrame">
    <div id="devTableForm_disable_label" class="formLabel" >Disable </div>
    <div id="devTableForm_disable_dropdown" class="formFieldDropDown" >       
        <select name="disable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="devTableForm_radioValue_field" class="formFieldFrame">
    <div id="devTableForm_radioValue_label" class="formLabel" >Radio Value </div>
    <div id="devTableForm_radioValue_text" class="formFieldText" >       
        <input id="radioValue" class="field" type="text" size="70" name="radioValue" value="<%=WebUtil.display(_radio_valueValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div class="submitFrame">

        <div id="devTableForm_submit" class="formSubmit" >       
            <a id="formSubmit2" href="javascript:document.devTableForm_Form.submit();">Submit</a>
        </div>      

        <div id="devTableForm_submit_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="devTableForm_submit_ext" class="formSubmitExt" >       
            <a href="#">Ext</a>
        </div>      
	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_DevTable.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>


<%	
	Map resTransMap = (Map) session.getAttribute("k_reserved_params");
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
