<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    Linkto _Linkto = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_Linkto = LinktoDS.getInstance().getById(id);
		if ( _Linkto == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
		}
		isEdit = true;
		
	} else {

	    _Linkto = new Linkto();// LinktoDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    

    String _link_keyValue= (reqParams.get("linkKey")==null?WebUtil.display(_Linkto.getLinkKey()):WebUtil.display((String)reqParams.get("linkKey")));
    String _link_targetValue= (reqParams.get("linkTarget")==null?WebUtil.display(_Linkto.getLinkTarget()):WebUtil.display((String)reqParams.get("linkTarget")));
    String _disableValue= (reqParams.get("disable")==null?WebUtil.display(_Linkto.getDisable()):WebUtil.display((String)reqParams.get("disable")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_Linkto.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
%> 

<br>
<div id="linktoForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="linktoFormEdit" method="get" action="/linktoAction.html" >




	<div id="linktoForm_linkKey_field">
    <div id="linktoForm_linkKey_label" class="formRequiredLabel" >Link Key* </div>
    <div id="linktoForm_linkKey_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="linkKey" value="<%=WebUtil.display(_link_keyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linktoForm_linkTarget_field">
    <div id="linktoForm_linkTarget_label" class="formRequiredLabel" >Link Target* </div>
    <div id="linktoForm_linkTarget_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="linkTarget" value="<%=WebUtil.display(_link_targetValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="linktoForm_disable_field">
    <div id="linktoForm_disable_label" class="formLabel" >Disable </div>
    <div id="linktoForm_disable_dropdown" class="formFieldDropDown" >       
        <select name="disable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





        <div id="linktoFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.linktoFormEdit.submit();">Submit</a>
        </div>      

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Linkto.getId()%>">
<INPUT TYPE="HIDDEN" NAME="fwdTo" value="/v_linkto_home.html">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<INPUT TYPE="HIDDEN" NAME="fwdTo" value="/v_linkto_home.html">

<% } %>


<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
