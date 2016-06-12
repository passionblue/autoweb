<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<%

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	if (siteConfig == null) {
		siteConfig = SiteConfigDS.getDefaultSiteConfig(); 
	}

    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String cancelPage = (String) reqParams.get("cancelPage");
	String retPage = (String) reqParams.get("returnPage");

	int columnNum  = WebParamUtil.getIntValue((String)reqParams.get("columnNum"));
	long panelId   = WebParamUtil.getLongValue((String)reqParams.get("panelId"));
	long pageId   = WebParamUtil.getLongValue((String)request.getParameter("pid"));

	List cats = ContentCategoryDS.getInstance().getBySiteIdToPageIdList(site.getId(), pageId);
	Page dynPage = PageDS.getInstance().getById(pageId);
	
	int contentType = WebParamUtil.getIntValue((String) reqParams.get("contentType"), 0);
	int contentPageType = WebParamUtil.getIntValue((String) reqParams.get("contentPageType"), 0);
	
	
	boolean disableContentType  = (contentType > 0);
	boolean noWyswyg = WebParamUtil.getBooleanValue((String) reqParams.get("noWyswyg"));
	
%> 

<% if (false && siteConfig.getUseWysiwygContent() == 1) { %>
<script type="text/javascript" src="/fckeditor/fckeditor.js"></script>
<script type="text/javascript">
window.onload = function()
{
	// Automatically calculates the editor base path based on the _samples directory.
	// This is usefull only for these samples. A real application should use something like this:
	// oFCKeditor.BasePath = '/fckeditor/' ;	// '/fckeditor/' is the default value.
	//var sBasePath = document.location.href.substring(0,document.location.href.lastIndexOf('_samples')) ;
	var sBasePath = '/fckeditor/' ;

	var oFCKeditor = new FCKeditor( 'content' ) ;
	oFCKeditor.BasePath	= sBasePath ;
	oFCKeditor.Height   = '700';
	oFCKeditor.ReplaceTextarea('mainContent') ;
}
</script>
<%} %>

<% 	if ( sessionContext.isSuperAdminLogin()) { %>
<table class="mytable1">
	<tr>
		<td>Page to </td><td> <%= dynPage==null?"":dynPage.getPageMenuTitle() %></td>	
	</tr>
	<tr>
		<td></td><td></td>	
	</tr>
</table>
<%	} %>

<div id="addContentFormFrame"> 
		<html:form action="/addDynContent">
			Column Num : 
			
			<select name="columnNum" >
				<option value="1" <%=HtmlUtil.getOptionSelect("1", columnNum)%>>1</option>
				<option value="2" <%=HtmlUtil.getOptionSelect("2", columnNum)%>>2</option>
				<option value="3" <%=HtmlUtil.getOptionSelect("3", columnNum)%>>3</option>
				<option value="4" <%=HtmlUtil.getOptionSelect("4", columnNum)%>>4</option>
				<option value="5" <%=HtmlUtil.getOptionSelect("5", columnNum)%>>5</option>
			</select> 
<% if (contentPageType == 0){ %>			
			Category :
			<select name="categoryId" >
				<option value="" >-- Please Select --</option>
<%
	for(Iterator iter = cats.iterator(); iter.hasNext();){
		ContentCategory contCat = (ContentCategory) iter.next();
%>			
				<option value="<%=contCat.getId() %>" ><%= contCat.getCategory() %></option>
<%} %>
			</select>  
<%} %>
			<br>
			
<% if (contentType == 0 || contentType == 1 || contentType == 4){ %>			
		    Subject <br>
			<input type="text" size="95" name="title" style="width: 100%"><br/>
<%} %>

<% if (contentType == 0 || contentType == 1 ){ %>			
			Abstract <br/>
			<TEXTAREA NAME="abstract" ROWS="3" style="width: 100%"></TEXTAREA><br>
<%} %>			
<% if (contentType == 0 || contentType == 1 || contentType == 3){ %>			
			Content <a id="convertTo" href="#"> click </a><br/>
			
			<TEXTAREA id="editor1" NAME="content" ROWS="20" style="width: 100%"></TEXTAREA><br>
<%} else if (contentType == 5){ %>
			URL <br/>
			<input type="text" size="80" NAME="content" ></input><br>
<%} %>
<% if ((contentType == 0 || contentType == 1) && !noWyswyg){ %>			
			<script type="text/javascript">
				//<![CDATA[
					CKEDITOR.replace( 'editor1' );
				//]]>

			</script>
		    Source Name <br>
			<input type="text" size="80" name="sourceName" style="width: 100%"><br>
		    Source Url <br>
			<input type="text" size="80" name="sourceUrl" style="width: 100%"><br>
<%} %>			

			<script type="text/javascript">
			$(document).ready(function(){
				$("#convertTo").click(function(e){
					
					$("#editor1").slideUp();
					e.preventDefault();												
				});
			});		
			</script>			

			Image Url <br>
			<input type="text" size="50" name="imageUrl">
			Image Height 
			<input type="text" size="5" name="imageHeight">
			Image Width 
			<input type="text" size="5" name="imageWidth"> <br>

<% if (siteConfig.getUseWysiwygContent() == 0) { %>
			<input type="checkbox" name="html"> Content in HTML <br><br>
<% } else { %>
<% if (noWyswyg) { %>
			<input type="checkbox" name="html"> Content in HTML <br><br>
<% } else { %>
			<input type="HIDDEN" name="html" value="on" >
<% } %>
<% } %>

			<select name="contentType" <%= (disableContentType? "disabled=\"disabled\"":"") %>>
				<option value="" >--Choose content type--</option>
				<option value="0" <%=HtmlUtil.getOptionSelect(0, contentType)%>>Default</option>
				<option value="1" <%=HtmlUtil.getOptionSelect(1, contentType)%>>ImageLeft </option>
				<option value="2" <%=HtmlUtil.getOptionSelect(2, contentType)%>>Product(NOT READY)</option>
				<option value="3" <%=HtmlUtil.getOptionSelect(3, contentType)%>>Ads </option>
				<option value="4" <%=HtmlUtil.getOptionSelect(4, contentType)%>>Link </option>
				<option value="5" <%=HtmlUtil.getOptionSelect(5, contentType)%>>Image Link </option>
				<option value="6" <%=HtmlUtil.getOptionSelect(6, contentType)%>>Image Only</option>
			</select> <br> 

			<INPUT TYPE="HIDDEN" NAME="contentType" value="<%=contentType%>">
			<INPUT TYPE="HIDDEN" NAME="pid" value="<%=pageId%>">
			<INPUT TYPE="HIDDEN" NAME="panelId" value="<%=panelId%>">

			
			<b><a href="javascript:document.addDynContentForm.submit();">Submit</a> </b>|
<%	if (cancelPage != null) {%>					
			<b><a href="/<%=cancelPage %>.html">Cancel</a> </b>
<% } else {%>					
			<b><a href="/home.html">Cancel</a> </b>
<% } %>

			
			</html:form>
</div>
