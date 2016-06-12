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
    PanelMenuOrder _PanelMenuOrder = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_PanelMenuOrder = PanelMenuOrderDS.getInstance().getById(id);
		if ( _PanelMenuOrder == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _PanelMenuOrder = new PanelMenuOrder();// PanelMenuOrderDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "panel_menu_order_home";

    String _panel_idValue= (reqParams.get("panelId")==null?WebUtil.display(_PanelMenuOrder.getPanelId()):WebUtil.display((String)reqParams.get("panelId")));
    String _ordered_idsValue= (reqParams.get("orderedIds")==null?WebUtil.display(_PanelMenuOrder.getOrderedIds()):WebUtil.display((String)reqParams.get("orderedIds")));
    String _reverseValue= (reqParams.get("reverse")==null?WebUtil.display(_PanelMenuOrder.getReverse()):WebUtil.display((String)reqParams.get("reverse")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PanelMenuOrder.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_PanelMenuOrder.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
%> 

<br>
<div id="panelMenuOrderForm" class="formFrame">
<div id="panelMenuOrderFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="panelMenuOrderForm_Form" method="POST" action="/panelMenuOrderAction.html" id="panelMenuOrderForm_Form">






	<div id="panelMenuOrderForm_orderedIds_field" class="formFieldFrame">
    <div id="panelMenuOrderForm_orderedIds_label" class="formLabel" >Ordered Ids </div>
    <div id="panelMenuOrderForm_orderedIds_text" class="formFieldText" >       
        <input id="orderedIds" class="field" type="text" size="70" name="orderedIds" value="<%=WebUtil.display(_ordered_idsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="panelMenuOrderForm_reverse_field" class="formFieldFrame">
    <div id="panelMenuOrderForm_reverse_label" class="formLabel" >Reverse </div>
    <div id="panelMenuOrderForm_reverse_dropdown" class="formFieldDropDown" >       
        <select name="reverse">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _reverseValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _reverseValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>








	<div class="submitFrame">

        <div id="panelMenuOrderForm_submit" class="formSubmit" >       
            <a id="formSubmit2" href="javascript:document.panelMenuOrderForm_Form.submit();">Submit</a>
        </div>      

        <div id="panelMenuOrderForm_submit_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="panelMenuOrderForm_submit_ext" class="formSubmitExt" >       
            <a href="#">Ext</a>
        </div>      
	<div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PanelMenuOrder.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>


<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> 
</div> <!-- form -->
