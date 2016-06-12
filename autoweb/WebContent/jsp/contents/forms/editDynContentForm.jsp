<%@page language="java" import="com.jtrend.session.SessionContext,com.jtrend.util.*,com.autosite.session.*,com.autosite.ds.*,com.autosite.db.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<%

	AutositeSessionContext sessionCtx = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	SessionWrapper wrap = sessionCtx.getSessionWrapper();
	String pageName = (String) session.getAttribute("k_page_name");
	String pageId = (String) session.getAttribute("k_current_dyn_page");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    
   	long id  = WebParamUtil.getLongValue(request.getParameter("id"));
   	long pid  = WebParamUtil.getLongValue(request.getParameter("pid"));

	String cancelPage = (String) reqParams.get("cancelPage");
	
	
	Content content = ContentDS.getInstance().getById(id);	
	
	Page dynPage = PageDS.getInstance().getById(pid);
    List cats = ContentCategoryDS.getInstance().getByPageId(pid);
    
	JspUtil.getLogger().info("Page ID=" + pid);    
    
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
	oFCKeditor.ReplaceTextarea('mainContent');
} 
</script>
<%} %>

		<html:form action="/addDynContent">
			Column Num:
			<select name="columnNum">
				<option value="1" <%=HtmlUtil.getOptionSelect("1", content.getColumnNum())%>>1</option>
				<option value="2" <%=HtmlUtil.getOptionSelect("2", content.getColumnNum())%>>2</option>
				<option value="3" <%=HtmlUtil.getOptionSelect("3", content.getColumnNum())%>>3</option>
				<option value="4" <%=HtmlUtil.getOptionSelect("4", content.getColumnNum())%>>4</option>
				<option value="5" <%=HtmlUtil.getOptionSelect("5", content.getColumnNum())%>>5</option>
			</select>  
			&nbsp; Category
			<select name="categoryId" >
				<option value="" >-- Please Select --</option>
<%
	for(Iterator iter = cats.iterator(); iter.hasNext();){
		ContentCategory contCat = (ContentCategory) iter.next();
%>			
				<option value="<%=contCat.getId()%>"  <%=HtmlUtil.getOptionSelect(content.getCategoryId(), contCat.getId())%>><%= contCat.getCategory() %></option>
<%} %>
			</select> <br> 
			
		    Subject <br>
			<input type="text" size="95" name="title" value="<%=content.getContentSubject()%>"><br/>
			Abstract <br/>
			<TEXTAREA NAME="abstract" ROWS="5" style="width: 100%"></TEXTAREA><br>
			Content <br>
			<TEXTAREA id="editor1" NAME="content" COLS="70" ROWS="20" style="width: 100%; height: 200px"><%=content.getContent()%></TEXTAREA><br>

<% if ((content.getContentType() == 0 || content.getContentType() == 1) && WebUtil.isTrue(content.getInHtml())){ %>			



			<script type="text/javascript">
				//<![CDATA[
					// This call can be placed at any point after the
					// <textarea>, or inside a <head><script> in a
					// window.onload event handler.

					// Replace the <textarea id="editor"> with an CKEditor
					// instance, using default configurations.
					CKEDITOR.replace( 'editor1' );
				//]]>
			</script>
			
			Source Name <br>
			<input type="text" size="50" name="sourceName" value="<%=WebUtil.display(content.getSourceName())%>"><br>
		    Source Url <br>
			<input type="text" size="95" name="sourceUrl" value="<%=WebUtil.display(content.getSourceUrl())%>"><br>
<%} %>			
			Image Url <br>
			<input type="text" size="50" name="imageUrl"  value="<%=WebUtil.display(content.getImageUrl())%>">
			Image Height 
			<input type="text" size="5" name="imageHeight"  value="<%=WebUtil.display(content.getImageHeight())%>">
			Image Width 
			<input type="text" size="5" name="imageWidth"  value="<%=WebUtil.display(content.getImageWidth())%>"> <br>

			<select name="contentType">
				<option value="" >--Choose content type--</option>
				<option value="0" <%=HtmlUtil.getOptionSelect(0, content.getContentType())%>>Default</option>
				<option value="1" <%=HtmlUtil.getOptionSelect(1, content.getContentType())%>>ImageLeft</option>
				<option value="2" <%=HtmlUtil.getOptionSelect(2, content.getContentType())%>>Product(NOT READY)</option>
				<option value="3" <%=HtmlUtil.getOptionSelect(3, content.getContentType())%>>Ads</option>
				<option value="4" <%=HtmlUtil.getOptionSelect(4, content.getContentType())%>>Link</option>
				<option value="5" <%=HtmlUtil.getOptionSelect(5, content.getContentType())%>>Image Link </option>
				<option value="6" <%=HtmlUtil.getOptionSelect(6, content.getContentType())%>>Image Only</option>
			</select> <br> 
			
			<INPUT TYPE=HIDDEN NAME="panelId" value="<%=content.getPanelId()%>">
			<INPUT TYPE=HIDDEN NAME="cid" value="<%=id%>">
			<INPUT TYPE=HIDDEN NAME="edit" value="true">
			<b><a href="javascript:document.addDynContentForm.submit();">Submit</a> </b>|
<%	if (cancelPage != null) {%>					
			<b><a href="/<%=cancelPage %>.html">Cancel</a> </b>
<% } else {%>					
			<b><a href="/t_dyn_content_single.html?cid=<%=id %>&pid=<%=content.getPanelId() %>.html">Cancel</a> </b>
<% } %>
		</html:form>

