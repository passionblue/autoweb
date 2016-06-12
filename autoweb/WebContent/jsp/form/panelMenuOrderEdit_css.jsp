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

    PanelMenuOrder _PanelMenuOrder = PanelMenuOrderDS.getInstance().getById(id);

    if ( _PanelMenuOrder == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "panel_menu_order_home";

    String _panel_idValue=  WebUtil.display(_PanelMenuOrder.getPanelId());
    String _ordered_idsValue=  WebUtil.display(_PanelMenuOrder.getOrderedIds());
    String _reverseValue=  WebUtil.display(_PanelMenuOrder.getReverse());
    String _time_createdValue=  WebUtil.display(_PanelMenuOrder.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_PanelMenuOrder.getTimeUpdated());
%> 

<br>
<div id="panelMenuOrderForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="panelMenuOrderFormEdit" method="POST" action="/panelMenuOrderAction.html" >




	<div id="panelMenuOrderForm_orderedIds_field">
    <div id="panelMenuOrderForm_orderedIds_label" class="formLabel" >Ordered Ids </div>
    <div id="panelMenuOrderForm_orderedIds_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="orderedIds" value="<%=WebUtil.display(_ordered_idsValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="panelMenuOrderForm_reverse_field">
    <div id="panelMenuOrderForm_reverse_label" class="formLabel" >Reverse </div>
    <div id="panelMenuOrderForm_reverse_dropdown" class="formFieldDropDown" >       
        <select name="reverse">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _reverseValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _reverseValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>







        <div id="panelMenuOrderFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.panelMenuOrderFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PanelMenuOrder.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
