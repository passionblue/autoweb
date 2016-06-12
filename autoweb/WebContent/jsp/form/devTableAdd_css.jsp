<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    DevTable _DevTableDefault = new DevTable();// DevTableDS.getInstance().getDeafult();
    
    String _dev_note_idValue= (reqParams.get("devNoteId")==null?WebUtil.display(_DevTableDefault.getDevNoteId()):WebUtil.display((String)reqParams.get("devNoteId")));
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_DevTableDefault.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _subjectValue= (reqParams.get("subject")==null?WebUtil.display(_DevTableDefault.getSubject()):WebUtil.display((String)reqParams.get("subject")));
    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_DevTableDefault.getData()):WebUtil.display((String)reqParams.get("data")));
    String _typeValue= (reqParams.get("type")==null?WebUtil.display(_DevTableDefault.getType()):WebUtil.display((String)reqParams.get("type")));
    String _disableValue= (reqParams.get("disable")==null?WebUtil.display(_DevTableDefault.getDisable()):WebUtil.display((String)reqParams.get("disable")));
    String _radio_valueValue= (reqParams.get("radioValue")==null?WebUtil.display(_DevTableDefault.getRadioValue()):WebUtil.display((String)reqParams.get("radioValue")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_DevTableDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_DevTableDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "dev_table_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="devTableForm_topArea" class="formTopArea"></div>
<div id="devTableForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="devTableForm" method="POST" action="/devTableAction.html" >




	<div id="devTableForm_devNoteId_field">
    <div id="devTableForm_devNoteId_label" class="formLabel" >Dev Note Id </div>
    <div id="devTableForm_devNoteId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="devNoteId" value="<%=WebUtil.display(_dev_note_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="devTableForm_title_field">
    <div id="devTableForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="devTableForm_title_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="devTableForm_subject_field">
    <div id="devTableForm_subject_label" class="formLabel" >Subject </div>
    <div id="devTableForm_subject_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="subject" value="<%=WebUtil.display(_subjectValue)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="devTableForm_data_field">
    <div id="devTableForm_data_label" class="formLabel" >Data </div>
    <div id="devTableForm_data_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="data" COLS="50" ROWS="8" ><%=WebUtil.display(_dataValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>




	<div id="devTableForm_type_field">
    <div id="devTableForm_type_label" class="formRequiredLabel" >Type* </div>
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
    <div id="devTableForm_radioValue_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="radioValue" value="<%=WebUtil.display(_radio_valueValue)%>"/>
    </div>      
	</div><div class="clear"></div>









        <div id="devTableForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.devTableForm.submit();">Submit</a>
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
<div id="devTableForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = DevTableDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        DevTable _DevTable = (DevTable) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _DevTable.getId() %> </td>

    <td> <%= WebUtil.display(_DevTable.getDevNoteId()) %></td>
    <td> <%= WebUtil.display(_DevTable.getTitle()) %></td>
    <td> <%= WebUtil.display(_DevTable.getSubject()) %></td>
    <td> <%= WebUtil.display(_DevTable.getData()) %></td>
    <td> <%= WebUtil.display(_DevTable.getType()) %></td>
    <td> <%= WebUtil.display(_DevTable.getDisable()) %></td>
    <td> <%= WebUtil.display(_DevTable.getRadioValue()) %></td>
    <td> <%= WebUtil.display(_DevTable.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_DevTable.getTimeUpdated()) %></td>


<td>
<form name="devTableForm<%=_DevTable.getId()%>2" method="get" action="/v_dev_table_edit2.html" >
    <a href="javascript:document.devTableForm<%=_DevTable.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _DevTable.getId() %>">
</form>

</td>
<td>
<a href="/devTableAction.html?del=true&id=<%=_DevTable.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>