<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<% 
	SiteConfig siteConfig = (SiteConfig) session.getAttribute("k_site_config");
	Map reqParams = (Map) session.getAttribute("k_reserved_params");
	
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String pageId = (String) session.getAttribute("k_current_dyn_page");
//	String pageName = (String) session.getAttribute("k_page_name");
//	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
	Page dynPage = DynPageUtil.getCurrentPage(request, site.getId());
	
	String panelId= WebUtil.display((String)reqParams.get("panelId"));;
	String _wpId = WebProcManager.registerWebProcess();
	
%> 
 
<html> 
	<head>
		<title>JSP for SiteConfigForm form</title>
	</head>
	<body>
		<form name="addSitePostForm" method="post" action="/addSitePost.html" > 
			<table border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
				<tr bgcolor="#ffffff"> 
					<td align="right"> View Scope &nbsp;&nbsp;</td>
					<td>
						<select name="post_scope">
						<option value="0">Choose position for the scope</option>
						<option value="1">All</option>
						<option value="2" selected>This Site (<%= site.getSiteUrl()%>)</option>
						<option value="3">This Page (<%= dynPage == null? "INVALID" : dynPage.getPageMenuTitle()%>)</option>
						<option value="4">This Content</option>
						</select> 
						Site=<%= request.getServerName() %>
						Page=<%= dynPage == null? "INVALID" : dynPage.getPageMenuTitle()%>
					</td>
				</tr >
				<tr bgcolor="#ffffff"> 
					<td align="right"> Position Code &nbsp;&nbsp;</td>
					<td>
						<select name="position_code">
						<option value="0">Choose position for the post</option>
						<option value="1">Header Right</option>
						<option value="2">Right Side</option>
						<option value="3">Left Side</option>
						<option value="4">Left Side Bottom</option>
						<option value="5">Content Top</option>
						<option value="6">Content Bottom</option>
						<option value="7">Right Side for all</option>
						<option value="8">Left Side for all</option>
						<option value="9">Mid Column</option>
						
						</select> 
					</td>
				</tr >
				<tr bgcolor="#ffffff"> 
					<td align="right"> Post type &nbsp;&nbsp;</td>
					<td>  
						<select name="post_type">
						<option value="0">Choose type for the post</option>
						<option value="1">Ads</option>
						<option value="2">Link</option>
						<option value="3">Image</option>
						<option value="4">Content</option>
						<option value="5">Padding</option>
						</select> 
					</td>
				</tr >
				<tr bgcolor="#ffffff"> 
					<td align="right"> Panel Id &nbsp;&nbsp;</td>
					<td> <%= panelId %></td>
				</tr>
				<tr bgcolor="#ffffff"> 
					<td align="right"> Main Data &nbsp;&nbsp;</td>
					<td> <TEXTAREA type="text" COLS=50 ROWS=15 name="post_data"></TEXTAREA></td>
				</tr>
				<tr bgcolor="#ffffff"> 
					<td align="right"> Sub &nbsp;&nbsp;</td>
					<td> <input type="text" size="55" name="post_data_extra" > </td>
				</tr>
				<tr bgcolor="#ffffff"> 
					<td align="right"> Size &nbsp;&nbsp;</td>
					<td> Height <input type="text" size="10" name="height" > Width <input type="text" size="10" name="width" >
					<input type="checkbox" name="fit_border" checked /> Fit To Border</td>
				</tr>
				<tr bgcolor="#ffffff"> 
					<td align="right"> Style String &nbsp;&nbsp;</td>
					<td> <input type="text" size="70" name="style_string" > </td>
				</tr>

				<tr bgcolor="#ffffff"> 
					<td align="right"> Option 1 &nbsp;&nbsp;</td>
					<td> <input type="text" size="70" name="option1" > </td>
				</tr>

				<tr bgcolor="#ffffff"> 
					<td align="right"> Option 2 &nbsp;&nbsp;</td>
					<td> <input type="text" size="70" name="option2" > </td>
				</tr>

				<tr bgcolor="#ffffff"> 
					<td align="right"> Option 3 &nbsp;&nbsp;</td>
					<td> <input type="text" size="70" name="option3" > </td>
				</tr>

				<tr bgcolor="#ffffff"> 
					<td align="right"> Option 4 &nbsp;&nbsp;</td>
					<td> <input type="text" size="70" name="option4" > </td>
				</tr>

				<tr bgcolor="#ffffff"> 
					<td align="right"> Option 5 &nbsp;&nbsp;</td>
					<td> <input type="text" size="70" name="option5" > </td>
				</tr>

				<tr bgcolor="#ffffff"> 
					<td align="right">  &nbsp;&nbsp;</td>
					<td> 
						Link type: Data=link, Extra=Display <br/>
					 	Ads Type: Just plain<br/>
					 	Image: Data=Img Source, Extra= Anchor Ref, Option 1=Text Display
					</td>
				</tr>

				<tr bgcolor="#ffffff"> 
				</tr>


				<tr bgcolor="#ffffff"> 
					<td>t  </td>
					<td> <b><a href="javascript:document.addSitePostForm.submit();">Submit</a> </b> </td>
				</tr>
			</table>
			
			<INPUT TYPE=HIDDEN NAME="add" value="true">
			<INPUT TYPE=HIDDEN NAME="pid" value="<%=dynPage == null? "0" : dynPage.getId() %>">
			<INPUT TYPE=HIDDEN NAME="contentId" value="0">
			<INPUT TYPE=HIDDEN NAME="wpid" value="<%= _wpId %>">
			<INPUT TYPE=HIDDEN NAME="panelId" value="<%=panelId %>">
		</form>
	</body>
</html>
