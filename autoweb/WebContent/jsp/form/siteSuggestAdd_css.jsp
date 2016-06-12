<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    SiteSuggest _SiteSuggestDefault = new SiteSuggest();// SiteSuggestDS.getInstance().getDeafult();
    
    String _categoryValue= (reqParams.get("category")==null?WebUtil.display(_SiteSuggestDefault.getCategory()):WebUtil.display((String)reqParams.get("category")));
    String _suggestValue= (reqParams.get("suggest")==null?WebUtil.display(_SiteSuggestDefault.getSuggest()):WebUtil.display((String)reqParams.get("suggest")));
    String _resolvedValue= (reqParams.get("resolved")==null?WebUtil.display(_SiteSuggestDefault.getResolved()):WebUtil.display((String)reqParams.get("resolved")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_SiteSuggestDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_SiteSuggestDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "site_suggest_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="siteSuggestForm_topArea" class="formTopArea"></div>
<div id="siteSuggestForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="siteSuggestForm" method="POST" action="/siteSuggestAction.html" >




	<div id="siteSuggestForm_category_field">
    <div id="siteSuggestForm_category_label" class="formRequiredLabel" >Category* </div>
    <div id="siteSuggestForm_category_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="category" value="<%=WebUtil.display(_categoryValue)%>"/> 
    </div>      
	</div><div class="clear"></div>



	<div id="siteSuggestForm_suggest_field">
    <div id="siteSuggestForm_suggest_label" class="formRequiredLabel" >Suggest* </div>
    <div id="siteSuggestForm_suggest_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="requiredField" NAME="suggest" COLS="50" ROWS="8" ><%=WebUtil.display(_suggestValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>




	<div id="siteSuggestForm_resolved_field">
    <div id="siteSuggestForm_resolved_label" class="formLabel" >Resolved </div>
    <div id="siteSuggestForm_resolved_dropdown" class="formFieldDropDown" >       
        <select name="resolved">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _resolvedValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _resolvedValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>










        <div id="siteSuggestForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.siteSuggestForm.submit();">Submit</a>
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
<div id="siteSuggestForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = SiteSuggestDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        SiteSuggest _SiteSuggest = (SiteSuggest) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SiteSuggest.getId() %> </td>

    <td> <%= WebUtil.display(_SiteSuggest.getCategory()) %></td>
    <td> <%= WebUtil.display(_SiteSuggest.getSuggest()) %></td>
    <td> <%= WebUtil.display(_SiteSuggest.getResolved()) %></td>
    <td> <%= WebUtil.display(_SiteSuggest.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_SiteSuggest.getTimeUpdated()) %></td>


<td>
<form name="siteSuggestForm<%=_SiteSuggest.getId()%>2" method="get" action="/v_site_suggest_edit2.html" >
    <a href="javascript:document.siteSuggestForm<%=_SiteSuggest.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SiteSuggest.getId() %>">
</form>

</td>
<td>
<a href="/siteSuggestAction.html?del=true&id=<%=_SiteSuggest.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>