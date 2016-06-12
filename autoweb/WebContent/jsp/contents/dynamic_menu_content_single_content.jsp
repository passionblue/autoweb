<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.session.*,com.jtrend.session.*,com.autosite.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>
<%
	AutositeSessionContext loginContext = (AutositeSessionContext) session.getAttribute("k_session_context");

	PageDS pageDS = PageDS.getInstance();
	
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	String pageName = (String) session.getAttribute("k_page_name");
	String categoryName = (String) session.getAttribute("k_page_category");
	
	Page dynPage = pageDS.getBySiteIdPageName(site.getId(), pageName);
	
	List allContents = ContentDS.getInstance().getByPageId(dynPage.getId());
	
	String pageDetail = "Page Details: pageName=" + 	dynPage.getPageName() + 
	                    ",site=" + site.getSiteUrl() +  	
	                    ",siteId=" + site.getId() +  	
	                    ",PageId=" + dynPage.getId(); 

	SessionWrapper wrap = SessionWrapper.wrapIt(request, site.getId());

	PageView pageView = wrap.getViewPage(); //(PageView) session.getAttribute("k_view_pageview");

	                    
	// Confiture site config	                    
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
	
	if (siteConfig ==null) {	
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	} 

	
	List contents = ContentDS.getInstance().getByPageId(dynPage.getId());

	Content content = null;
	if (contents != null && contents.size() > 0 )
		content = (Content) contents.get(0);


	String cancelPage = null; 
	
	if ( wrap.isDynPage2() ) 
		cancelPage = "m_" + pageName;
	else 
		cancelPage = "v_" + pageView.getAlias();		
		
%>	                    

<% if (loginContext!= null && loginContext.isSiteAdminLogin()){ %>
<% if (content == null) { %>
	<a href="/v_add_dyn_content.html?pid=<%=dynPage.getId() %>&prv_cancelPage=<%=cancelPage %>" > <b>Add Content To This Page</b></a>&nbsp;|&nbsp;
		
<% 	return;
   } else { %>
	<a href="/v_edit_dyn_content_form.html?id=<%=content.getId() %>&pid=<%=dynPage.getId()%>&prv_cancelPage=<%=cancelPage %>" > <b>Edit This Content</b></a>&nbsp;|&nbsp;
	<a href="/deleteDynContent.html?id=<%=content.getId() %>&pid=<%=dynPage.getId()%>" > <b>Delete This Content</b></a>
<% } %>
<% } %>

<% 	if ( content != null) { %>
<div class="cntSingleFrame">
<div class="cntSingleSubject">
<%= WebUtil.display(content.getContentSubject()) %>
</div>

<div class="cntSingleData">
<%= content.getContent() %>
</div>
</div>
<% 	} else { %>
&nbsp;
<% 	} %>


<%
	if (content == null || !loginContext.isSiteAdmin()) return;

	boolean noWyswyg = content.getInHtml() == 0? true: false;
	

%>
<br/><br/>
<div id="addContentFormFrame"> 
	<html:form action="/addDynContent">
		    Subject <br>
			<input type="text" size="95" name="title" style="width: 100%"><br/>

			Appending Content <br/>
			<TEXTAREA id="editor1" NAME="content" ROWS="20" style="width: 100%"></TEXTAREA><br>

<% if (!noWyswyg){ %>			
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
<%} %>			

			<INPUT TYPE="HIDDEN" NAME="cid" value="<%=content.getId()%>">
			<INPUT TYPE="HIDDEN" NAME="pid" value="<%=content.getPageId()%>">
			<INPUT TYPE="HIDDEN" NAME="append" value="true">
			
			<b><a href="javascript:document.addDynContentForm.submit();">Append</a> </b>
			
</html:form>
</div>


