<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SectionDataHolder _SectionDefault = new SectionDataHolder();// SectionDS.getInstance().getDeafult();
    
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_SectionDefault.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _main_page_idValue= (reqParams.get("mainPageId")==null?WebUtil.display(_SectionDefault.getMainPageId()):WebUtil.display((String)reqParams.get("mainPageId")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SectionDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "section_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="sectionForm_topArea" class="formTopArea"></div>
<div id="sectionForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="sectionForm" method="POST" action="/sectionAction.html" >




	<div id="sectionForm_title_field">
    <div id="sectionForm_title_label" class="formLabel" >Title </div>
    <div id="sectionForm_title_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="sectionForm_mainPageId_field">
    <div id="sectionForm_mainPageId_label" class="formLabel" >Main Page Id </div>
    <div id="sectionForm_mainPageId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="mainPageId" value="<%=WebUtil.display(_main_page_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>





        <div id="sectionForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.sectionForm.submit();">Submit</a>
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
<div id="sectionForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = SectionDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        SectionDataHolder _Section = (SectionDataHolder) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _Section.getId() %> </td>

    <td> <%= WebUtil.display(_Section.getTitle()) %></td>
    <td> <%= WebUtil.display(_Section.getMainPageId()) %></td>
    <td> <%= WebUtil.display(_Section.getTimeCreated()) %></td>


<td>
<form name="sectionForm<%=_Section.getId()%>2" method="get" action="/v_section_edit2.html" >
    <a href="javascript:document.sectionForm<%=_Section.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _Section.getId() %>">
</form>

</td>
<td>
<a href="/sectionAction.html?del=true&id=<%=_Section.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>