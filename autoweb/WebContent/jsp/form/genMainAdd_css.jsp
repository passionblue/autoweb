<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    GenMain _GenMainDefault = new GenMain();// GenMainDS.getInstance().getDeafult();
    
    String _activeValue= (reqParams.get("active")==null?WebUtil.display(_GenMainDefault.getActive()):WebUtil.display((String)reqParams.get("active")));
    String _valueValue= (reqParams.get("value")==null?WebUtil.display(_GenMainDefault.getValue()):WebUtil.display((String)reqParams.get("value")));
    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_GenMainDefault.getData()):WebUtil.display((String)reqParams.get("data")));
    String _requiredValue= (reqParams.get("required")==null?WebUtil.display(_GenMainDefault.getRequired()):WebUtil.display((String)reqParams.get("required")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_GenMainDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_GenMainDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "gen_main_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="genMainForm_topArea" class="formTopArea"></div>
<div id="genMainForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="genMainForm" method="POST" action="/genMainAction.html" >




	<div id="genMainForm_active_field">
    <div id="genMainForm_active_label" class="formLabel" >Active </div>
    <div id="genMainForm_active_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="active" value="<%=WebUtil.display(_activeValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="genMainForm_value_field">
    <div id="genMainForm_value_label" class="formLabel" >Value </div>
    <div id="genMainForm_value_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="value" value="<%=WebUtil.display(_valueValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="genMainForm_data_field">
    <div id="genMainForm_data_label" class="formLabel" >Data </div>
    <div id="genMainForm_data_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="genMainForm_required_field">
    <div id="genMainForm_required_label" class="formLabel" >Required </div>
    <div id="genMainForm_required_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="required" value="<%=WebUtil.display(_requiredValue)%>"/>
    </div>      
	</div><div class="clear"></div>








	<div id="genMainForm_timeUpdated_field">
    <div id="genMainForm_timeUpdated_label" class="formLabel" >Time Updated </div>
    <div id="genMainForm_timeUpdated_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="timeUpdated" value="<%=WebUtil.display(_time_updatedValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="genMainForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.genMainForm.submit();">Submit</a>
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
<div id="genMainForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = GenMainDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        GenMain _GenMain = (GenMain) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _GenMain.getId() %> </td>

    <td> <%= WebUtil.display(_GenMain.getActive()) %></td>
    <td> <%= WebUtil.display(_GenMain.getValue()) %></td>
    <td> <%= WebUtil.display(_GenMain.getData()) %></td>
    <td> <%= WebUtil.display(_GenMain.getRequired()) %></td>
    <td> <%= WebUtil.display(_GenMain.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_GenMain.getTimeUpdated()) %></td>


<td>
<form name="genMainForm<%=_GenMain.getId()%>2" method="get" action="/v_gen_main_edit2.html" >
    <a href="javascript:document.genMainForm<%=_GenMain.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _GenMain.getId() %>">
</form>

</td>
<td>
<a href="/genMainAction.html?del=true&id=<%=_GenMain.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>