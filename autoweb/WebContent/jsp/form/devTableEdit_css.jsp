<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
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

    DevTable _DevTable = DevTableDS.getInstance().getById(id);

    if ( _DevTable == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "dev_table_home";

    String _dev_note_idValue=  WebUtil.display(_DevTable.getDevNoteId());
    String _titleValue=  WebUtil.display(_DevTable.getTitle());
    String _subjectValue=  WebUtil.display(_DevTable.getSubject());
    String _dataValue=  WebUtil.display(_DevTable.getData());
    String _typeValue=  WebUtil.display(_DevTable.getType());
    String _disableValue=  WebUtil.display(_DevTable.getDisable());
    String _radio_valueValue=  WebUtil.display(_DevTable.getRadioValue());
    String _time_createdValue=  WebUtil.display(_DevTable.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_DevTable.getTimeUpdated());
%> 

<br>
<div id="devTableForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="devTableFormEdit" method="POST" action="/devTableAction.html" >




	<div id="devTableForm_devNoteId_field">
    <div id="devTableForm_devNoteId_label" class="formLabel" >Dev Note Id </div>
    <div id="devTableForm_devNoteId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="devNoteId" value="<%=WebUtil.display(_dev_note_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="devTableForm_title_field">
    <div id="devTableForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="devTableForm_title_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="devTableForm_subject_field">
    <div id="devTableForm_subject_label" class="formLabel" >Subject </div>
    <div id="devTableForm_subject_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="subject" value="<%=WebUtil.display(_subjectValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="devTableForm_data_field">
    <div id="devTableForm_data_label" class="formLabel" >Data </div>
    <div id="devTableForm_data_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="data" COLS="50" ROWS="8" ><%=WebUtil.display(_dataValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>

	<div id="devTableForm_type_field">
    <div id="devTableForm_type_label" class="formLabel" >Type </div>
    <div id="devTableForm_type_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="type">
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



	<div id="devTableForm_disable_field">
    <div id="devTableForm_disable_label" class="formLabel" >Disable </div>
    <div id="devTableForm_disable_dropdown" class="formFieldDropDown" >       
        <select name="disable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div id="devTableForm_radioValue_field">
    <div id="devTableForm_radioValue_label" class="formLabel" >Radio Value </div>
    <div id="devTableForm_radioValue_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="radioValue" value="<%=WebUtil.display(_radio_valueValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





        <div id="devTableFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.devTableFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_DevTable.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
