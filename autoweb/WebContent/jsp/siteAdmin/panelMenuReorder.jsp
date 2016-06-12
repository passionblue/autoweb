<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%

  	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	
	Panel panel = PanelDS.getInstance().getById(WebParamUtil.getLongValue(request.getParameter("panelId"))); 

	if ( panel == null) {
		JspUtil.getLogger().error("** Panel not found in session **");
		return;
	}

	
	
%>

<div id="panelMenuReorderPanel" style="padding:100px;">
<%
	List menuItems = PanelMenuOrderUtil.resetOrderedMenuList(panel);
	int idx = 1;
	for(Iterator iter = menuItems.iterator();iter.hasNext();) {

    	MenuItem mi = (MenuItem) iter.next();

%>
	<div style="border: 1px solid gray;height: 50px;margin-bottom: 20px;">
	<div style="float:left;border: 1px solid gray;display: table; margin: 5px; padding: 5px;">
		<a id="panelMenuOrderUp" href="#" rel="<%=idx %>"> <img src="/images/dev/PNG/Blue/24/up_01.png"/></a><br/> 
	</div>
	<div style="float:left;border: 1px solid gray;display: table; margin: 5px; padding: 5px;">
		 <a id="panelMenuOrderDown" href="#" rel="<%=idx %>"> <img src="/images/dev/PNG/Blue/24/down.png"/></a>
	</div>
	<div id="panelMenuOrderItem<%=idx %>" style="float:left;font:normal normal bold 20px Verdana; padding: 10px;" ><%= mi.getTitle() %> <%=mi.getTargetType() %>
		<div id="menuIdHold" style="display:none;"> <%= mi.getId() %></div> 
		<div id="menuOrderHold" style="display:none;"> <%= mi.getId()+"x" %></div> 
	</div>		
	</div><div class="clear"></div>

<%
		idx++;
	}
%>
<a class="generic" href="#" onclick="javascript:savePanelMenuOrder(<%=menuItems.size() %>,'<%=panel.getId()%>');return false;" > Save</a>
</div>
<div id="paneMenuReorderChanged" style="display:none" >false</div>
<div >
</div>	



