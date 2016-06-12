<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    PanelMenuOrder _PanelMenuOrderDefault = new PanelMenuOrder();// PanelMenuOrderDS.getInstance().getDeafult();
    
    String _panel_idValue= (reqParams.get("panelId")==null?WebUtil.display(_PanelMenuOrderDefault.getPanelId()):WebUtil.display((String)reqParams.get("panelId")));
    String _ordered_idsValue= (reqParams.get("orderedIds")==null?WebUtil.display(_PanelMenuOrderDefault.getOrderedIds()):WebUtil.display((String)reqParams.get("orderedIds")));
    String _reverseValue= (reqParams.get("reverse")==null?WebUtil.display(_PanelMenuOrderDefault.getReverse()):WebUtil.display((String)reqParams.get("reverse")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PanelMenuOrderDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_PanelMenuOrderDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "panel_menu_order_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="panelMenuOrderForm_topArea" class="formTopArea"></div>
<div id="panelMenuOrderForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="panelMenuOrderForm" method="POST" action="/panelMenuOrderAction.html" >






	<div id="panelMenuOrderForm_orderedIds_field">
    <div id="panelMenuOrderForm_orderedIds_label" class="formLabel" >Ordered Ids </div>
    <div id="panelMenuOrderForm_orderedIds_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="orderedIds" value="<%=WebUtil.display(_ordered_idsValue)%>"/>
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










        <div id="panelMenuOrderForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.panelMenuOrderForm.submit();">Submit</a>
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
<div id="panelMenuOrderForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = PanelMenuOrderDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        PanelMenuOrder _PanelMenuOrder = (PanelMenuOrder) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PanelMenuOrder.getId() %> </td>

    <td> <%= WebUtil.display(_PanelMenuOrder.getPanelId()) %></td>
    <td> <%= WebUtil.display(_PanelMenuOrder.getOrderedIds()) %></td>
    <td> <%= WebUtil.display(_PanelMenuOrder.getReverse()) %></td>
    <td> <%= WebUtil.display(_PanelMenuOrder.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_PanelMenuOrder.getTimeUpdated()) %></td>


<td>
<form name="panelMenuOrderForm<%=_PanelMenuOrder.getId()%>2" method="get" action="/v_panel_menu_order_edit2.html" >
    <a href="javascript:document.panelMenuOrderForm<%=_PanelMenuOrder.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PanelMenuOrder.getId() %>">
</form>

</td>
<td>
<a href="/panelMenuOrderAction.html?del=true&id=<%=_PanelMenuOrder.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>