<%@page language="java" import="com.jtrend.util.*,java.util.*,com.jtrend.util.*,com.autosite.db.*,com.autosite.ds.*,com.autosite.util.*,com.autosite.util.web.*"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
	List panels = PanelDS.getInstance().getBySiteId(site.getId());

	List menuPanels = new ArrayList();
	for(Iterator iter = panels.iterator();iter.hasNext();) {
		Panel panel = (Panel)iter.next();
		
		if (panel.getPanelType() == PanelUtil.PANEL_MENU ||
		    panel.getPanelType() == PanelUtil.PANEL_HEADER_MENU ||
		    panel.getPanelType() == PanelUtil.PANEL_FOOTER_MENU) {
			menuPanels.add(panel);
		}
	}
%>


	<html:form action="/addPage">
		<table>
		<tr>
			<td> Page Name: </td> 
			<td><html:text property="page"/><html:errors property="page"/> <font size="1" color="red"> (*Can't include space in the middle)</font> </td>
		</tr>
		<tr>
			<td> Page Display: </td> 
			<td><html:text property="pageDisplay"/><html:errors property="pageDisplay"/> <font size="1" > (Optional)</font> </td>
		</tr>
		
        <tr>
            <td> Menu On Header: </td> 
            <td>
	            <select name="headerPage">
		            <option value="0" selected > No</option>
		            <option value="1" >Yes</option>
				</select>
			</td>
		</tr>			

        <tr>
            <td> Menu Place Panel: </td> 
            <td>
	            <select name="menuPanelId">
	            <option value="0" selected >No Show On Menu</option>

<%
	// Creating dropdown menu with menu panels
	for(Iterator iter = menuPanels.iterator();iter.hasNext();) {
		Panel panel = (Panel)iter.next();
		
%>
	            <option value="<%=panel.getId()%>" ><%=panel.getId() + "("+ (panel.getHide()==1?"Hidden":"Shown") + ")"%></option>
	
<%
	}
%>
	            </select>
			</td>
		</tr>			
	            
        <tr>
            <td> parentPage </td> 
            <td>
	            <select name="parentPageId">
	            <option value="0" selected >No Parent</option>
<%
	// Creating dropdown menu for parent page
	
	List allPages = PageDS.getInstance().getBySiteId(site.getId());
	
	for(Iterator iter = allPages.iterator();iter.hasNext();) {
		    Page p = (Page) iter.next();
			if (p.getMenuPanelId() == 0 ) continue;
			//if (p.getParentId() > 0) continue;
%>
	            <option value="<%=p.getId()%>" ><%= p.getPageMenuTitle()%></option>
<%
	}
%>
	            </select>
			</td>
		</tr>			

        <tr>
            <td> Page Type: </td> 
            <td>
		 		<select name="underlyingPage">
<%
	List dropMenus = DropMenuUtil.getDropMenus("DynamicPages");
	for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
					<option value="<%=it.getValue() %>" ><%=it.getDisplay() %></option>
<%} %>
				</select>
<!-- 		 		
					<option value="dynamic_menu_content" >Default</option>
					<option value="dynamic_menu_content_list_all" >List All</option>
					<option value="dynamic_menu_content_two" >Dynamic Two</option>
					<option value="dynamic_menu_content_three" >Dynamic Tree</option>
					<option value="dynamic_menu_content_single_content" >Single Content page</option>
					<option value="ec_product_display" >EC Product Display</option>
					<option value="dynamic_menu_content_blog" >Blogging</option>
 -->					
			</td>
		</tr>			

	            
		</table> 
		<b><a href="javascript:document.addPageForm.submit();">[Create Page]</a> </b>
	</html:form>

<%

	List pages = PageDS.getInstance().getBySiteId(site.getId());
	
	if (pages == null) {
		return;
	}

%>
<br> <br> <br>
<h3> Pages of this site </h3>
<TABLE border="0" width="100%" cellpadding="0" cellspacing="0" >
<TR>
<TD width="10px"></TD>
<TD>
	<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">

		<TR> 
			<TD colspan="4" width="20" bgcolor="#ffffff" style="border-bottom : 1px #6DAEFA solid;"> Page Name </TD>
		</TR>

	<%
	
	for (int i = 0; i <2 ;i++) {
	
		for(Iterator iter = pages.iterator();iter.hasNext();) {
		    Page p = (Page) iter.next();
		    
		    //if (p.getPageName().equals("XHOME")) continue;
		    
			String link = "<a href=\"/m_" + p.getPageName() + ".html\" > " + p.getPageMenuTitle() + "/" + p.getPageName() + "/" + p.getId(); 
			
			if ( p.getHide() == 1) 
				link += " (Hidden)";
			else 
				link += " ";
				
			 link +=" </a>";		
			 
			 if ( p.getHeaderPage() != i) continue;
				  
	 %>
	
		<TR> 
			<TD height="20" bgcolor="#ffffff" style="border-bottom : 1px #e0e0e0 solid;"> 
			<%= link %> (<a href="/v_page_edit.html?id=<%=p.getId()%>&prv_returnPage=add_page">edit</a>)
			</TD>
			<TD height="20"  bgcolor="#ffffff" style="border-bottom : 1px #e0e0e0 solid;" align="left" valign="bottom">
				<form name="updatePageForm<%=p.getId() %>" method="get" action="/updatePage.html" >
			 		<input type="text" size="5" name="pageDisplay" >
			 		<a href="javascript:document.updatePageForm<%=p.getId() %>.submit();">Name</a>
					<INPUT TYPE="HIDDEN" NAME="act" value="rename">
					<INPUT TYPE="HIDDEN" NAME="pid" value="<%= p.getId() %>">
			 	</form>
			</TD>
			
			<TD height="20"  bgcolor="#ffffff" style="border-bottom : 1px #e0e0e0 solid;" align="left" valign="bottom">
				<form name="updateKeyworForm<%=p.getId() %>" method="get" action="/updatePage.html" >
			 		<input type="text" size="15" name="keywords" value="<%= p.getPageKeywords()==null?"":p.getPageKeywords() %>">
			 		<a href="javascript:document.updateKeyworForm<%=p.getId() %>.submit();">Keyword</a>
					<INPUT TYPE="HIDDEN" NAME="act" value="keywords">
					<INPUT TYPE="HIDDEN" NAME="pid" value="<%= p.getId() %>">
			 	</form>
			</TD>
<!--
			<TD height="20"  bgcolor="#ffffff" style="border-bottom : 1px #e0e0e0 solid;" align="left" valign="bottom">
				<form name="updateUnderlyingPageForm<%=p.getId() %>" method="get" action="/updatePage.html" >
			 		<input type="text" size="30" name="underlyingPage" value="<%= WebUtil.display(p.getUnderlyingPage()) %>">
			 		<a href="javascript:document.updateUnderlyingPageForm<%=p.getId() %>.submit();">Page</a>
					<INPUT TYPE=HIDDEN NAME="act" value="underlyingPage">
					<INPUT TYPE=HIDDEN NAME="pid" value="<%= p.getId() %>">
			 	</form>
			</TD>
-->
			<TD height="20"  bgcolor="#ffffff" style="border-bottom : 1px #e0e0e0 solid;" align="left" valign="bottom">
				<form name="updateUnderlyingPageForm<%=p.getId() %>" method="get" action="/updatePage.html" >
			 		<select name="underlyingPage">
<%
	List dropMenus = DropMenuUtil.getDropMenus("DynamicPages");
	for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
						<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect("" + it.getCompareValue(), p.getUnderlyingPage())%>><%=it.getDisplay() %></option>
<%} %>
<!-- 

						<option value="dynamic_menu_content" <%=HtmlUtil.getOptionSelect("", p.getUnderlyingPage())%>>Default</option>
						<option value="dynamic_menu_content_list_all" <%=HtmlUtil.getOptionSelect("dynamic_menu_content_list_all", p.getUnderlyingPage())%>>List All</option>
						<option value="dynamic_menu_content_two" <%=HtmlUtil.getOptionSelect("dynamic_menu_content_two", p.getUnderlyingPage())%>>Dynamic Two</option>
						<option value="dynamic_menu_content_three" <%=HtmlUtil.getOptionSelect("dynamic_menu_content_three", p.getUnderlyingPage())%>>Dynamic Tree</option>
						<option value="dynamic_menu_content_single_content" <%=HtmlUtil.getOptionSelect("dynamic_menu_content_single_content", p.getUnderlyingPage())%>>Single Content page</option>
						<option value="ec_product_display" <%=HtmlUtil.getOptionSelect("ec_product_display", p.getUnderlyingPage())%>>EC Product Display</option>
						<option value="dynamic_menu_content_blog" <%=HtmlUtil.getOptionSelect("dynamic_menu_content_blog", p.getUnderlyingPage())%>>Bloggin</option>
						<option value="select_team" <%=HtmlUtil.getOptionSelect("select_team", p.getUnderlyingPage())%>>Select Team</option>
-->
					</select>
			 		<a href="javascript:document.updateUnderlyingPageForm<%=p.getId() %>.submit();">Page</a>
					<INPUT TYPE="HIDDEN" NAME="act" value="underlyingPage">
					<INPUT TYPE="HIDDEN" NAME="pid" value="<%= p.getId() %>">
			 	</form>
			</TD>
			
			
            <TD height="20"  bgcolor="#ffffff" style="border-bottom : 1px #e0e0e0 solid;" align="left" valign="top">
            <form name="updateUnderlyingMenuIdForm<%=p.getId() %>" method="get" action="/updatePage.html" >
            <select name="panelId">
            <option value="0" >Not Defined</option>
            
<%
	// Creating dropdown menu with menu panels
	for(Iterator iter2 = menuPanels.iterator();iter2.hasNext();) {
		Panel panel = (Panel)iter2.next();
		
%>
            <option value="<%=panel.getId()%>" <%=HtmlUtil.getOptionSelect(panel.getId(), p.getMenuPanelId())%>><%=panel.getId()%></option>

<%}%>            
            
            </select>
			<INPUT TYPE="HIDDEN" NAME="act" value="menuPanel">
			<INPUT TYPE="HIDDEN" NAME="pid" value="<%= p.getId() %>">
	 		<a href="javascript:document.updateUnderlyingMenuIdForm<%=p.getId() %>.submit();">Set</a>
		 	</form>
            </TD>

			<TD height="20"  bgcolor="#ffffff" style="border-bottom : 1px #e0e0e0 solid;" align="right" valign="top">
			
				<a href="/updatePage.html?act=show&pid=<%= p.getId() %>" >On</a>|
				<a href="/updatePage.html?act=hide&pid=<%= p.getId() %>" >Off</a>|
				<a href="/updatePage.html?act=del&pid=<%= p.getId() %>" >Del</a> 
			</TD>
		</TR>
		
	<%
		}
	}
	%>		

	</TABLE>    
</TD>
<TD width="10px"></TD>
</TR>	
</TABLE>	
