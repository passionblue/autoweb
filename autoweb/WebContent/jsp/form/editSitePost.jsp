<%@page language="java" import="com.jtrend.session.SessionContext,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<% 
	SiteConfig siteConfig = (SiteConfig) session.getAttribute("k_site_config");
	
	String idStr  = request.getParameter("id");
	String sidStr = request.getParameter("stid");
	
	if (idStr == null || sidStr == null) return;
	
	long id = Long.parseLong(idStr);
	long stid = Long.parseLong(sidStr);
	
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	
	SitePost sitePost = SitePostDS.getInstance().getById(new Long(id));
	
	if (site == null || sitePost == null || site.getId() != sitePost.getSiteId()) {
		return;
	}
	
	
	
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
						<option value="0" <%=HtmlUtil.getOptionSelect("0", sitePost.getPostScope())%>>Post Scope</option>
						<option value="1" <%=HtmlUtil.getOptionSelect("1", sitePost.getPostScope())%>>All</option>
						<option value="2" <%=HtmlUtil.getOptionSelect("2", sitePost.getPostScope())%>>This site</option>
						<option value="3" <%=HtmlUtil.getOptionSelect("3", sitePost.getPostScope())%>>Page</option>
						<option value="4" <%=HtmlUtil.getOptionSelect("4", sitePost.getPostScope())%>>Content</option>
						</select> 
					</td>
				</tr >

				<tr bgcolor="#ffffff"> 
					<td align="right"> Position Code &nbsp;&nbsp;</td>
					<td>
						<select name="position_code">
						<option value="1" <%=HtmlUtil.getOptionSelect("1", sitePost.getPositionCode())%>>Header Right <%=sitePost.getPositionCode()%></option>
						<option value="2" <%=HtmlUtil.getOptionSelect("2", sitePost.getPositionCode())%>>Right Side</option>
						<option value="3" <%=HtmlUtil.getOptionSelect("3", sitePost.getPositionCode())%>>Left Side</option>
						<option value="4" <%=HtmlUtil.getOptionSelect("4", sitePost.getPositionCode())%>>Left Side Bottom</option>
						<option value="5" <%=HtmlUtil.getOptionSelect("5", sitePost.getPositionCode())%>>Content Top</option>
						<option value="6" <%=HtmlUtil.getOptionSelect("6", sitePost.getPositionCode())%>>Content Bottom</option>
						<option value="7" <%=HtmlUtil.getOptionSelect("7", sitePost.getPositionCode())%>>Right Side for all</option>
						<option value="8" <%=HtmlUtil.getOptionSelect("8", sitePost.getPositionCode())%>>Left Side for all</option>
						<option value="9" <%=HtmlUtil.getOptionSelect("8", sitePost.getPositionCode())%>>Mid Column </option>
						</select> 
					</td>
				</tr >
				<tr bgcolor="#ffffff"> 
					<td align="right"> Post type &nbsp;&nbsp;</td>
					<td>  
						<select name="post_type">
						<option value="1" <%=HtmlUtil.getOptionSelect("1", sitePost.getPostType())%>>Ads</option>
						<option value="2" <%=HtmlUtil.getOptionSelect("2", sitePost.getPostType())%>>Link</option>
						<option value="3" <%=HtmlUtil.getOptionSelect("3", sitePost.getPostType())%>>Image</option>
						<option value="4" <%=HtmlUtil.getOptionSelect("4", sitePost.getPostType())%>>Content</option>
						<option value="5" <%=HtmlUtil.getOptionSelect("5", sitePost.getPostType())%>>Padding</option>
						</select> 
					</td>
				</tr >
				<tr bgcolor="#ffffff"> 
					<td align="right"> Data &nbsp;&nbsp;</td>
					<td> <TEXTAREA type="text" COLS=50 ROWS=15 name="post_data"><%=sitePost.getPostData() %></TEXTAREA></td>
				</tr>
				
				<tr bgcolor="#ffffff"> 
					<td align="right"> Sub &nbsp;&nbsp;</td>
					<td> <input type="text" size="55" name="post_data_extra" value="<%= sitePost.getPostDataExtra()==null?"": sitePost.getPostDataExtra()%>" > </td>
				</tr>

				<tr bgcolor="#ffffff"> 
					<td align="right"> Size &nbsp;&nbsp;</td>
					<td> Height <input type="text" size="10" name="height" value="<%= WebUtil.display(sitePost.getHeight())%>"> 
					     Width  <input type="text" size="10" name="width" value="<%= WebUtil.display(sitePost.getWidth())%>">
					</td>
				</tr>

				<tr bgcolor="#ffffff"> 
					<td align="right"> Style String &nbsp;&nbsp;</td>
					<td> <input type="text" size="70" name="style_string" value="<%= WebUtil.display(sitePost.getStyleString())%>"> </td>
				</tr>

				<tr bgcolor="#ffffff"> 
					<td align="right"> Option 1 &nbsp;&nbsp;</td>
					<td> <input type="text" size="70" name="option1" value="<%= WebUtil.display(sitePost.getOption1())%>"> </td>
				</tr>

				<tr bgcolor="#ffffff"> 
					<td align="right"> Option 2 &nbsp;&nbsp;</td>
					<td> <input type="text" size="70" name="option2" value="<%= WebUtil.display(sitePost.getOption2())%>"> </td>
				</tr>

				<tr bgcolor="#ffffff"> 
					<td align="right"> Option 3 &nbsp;&nbsp;</td>
					<td> <input type="text" size="70" name="option3" value="<%= WebUtil.display(sitePost.getOption3())%>"> </td>
				</tr>

				<tr bgcolor="#ffffff"> 
					<td align="right"> Option 4 &nbsp;&nbsp;</td>
					<td> <input type="text" size="70" name="option4" value="<%= WebUtil.display(sitePost.getOption4())%>"> </td>
				</tr>

				<tr bgcolor="#ffffff"> 
					<td align="right"> Option 5 &nbsp;&nbsp;</td>
					<td> <input type="text" size="70" name="option5" value="<%= WebUtil.display(sitePost.getOption5())%>"> </td>
				</tr>


				<tr bgcolor="#ffffff"> 
					<td>  </td>
					<td> <b><a href="javascript:document.addSitePostForm.submit();">Submit</a> </b> </td>
				</tr>
			</table>
			<INPUT TYPE=HIDDEN NAME="edit" value="true">
			<INPUT TYPE=HIDDEN NAME="id" value="<%=id %>">
			<INPUT TYPE=HIDDEN NAME="stid" value="<%=stid %>">
			
		</form>
	</body>
</html>
