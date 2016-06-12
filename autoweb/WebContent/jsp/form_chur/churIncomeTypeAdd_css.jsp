<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ChurIncomeType _ChurIncomeTypeDefault = new ChurIncomeType();// ChurIncomeTypeDS.getInstance().getDeafult();
    
    String _income_typeValue= (reqParams.get("incomeType")==null?WebUtil.display(_ChurIncomeTypeDefault.getIncomeType()):WebUtil.display((String)reqParams.get("incomeType")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_type_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="churIncomeTypeForm_topArea" class="formTopArea"></div>
<div id="churIncomeTypeForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="churIncomeTypeForm" method="POST" action="/churIncomeTypeAction.html" >




	<div id="churIncomeTypeForm_incomeType_field">
    <div id="churIncomeTypeForm_incomeType_label" class="formLabel" >Income Type </div>
    <div id="churIncomeTypeForm_incomeType_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="incomeType" value="<%=WebUtil.display(_income_typeValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="churIncomeTypeForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churIncomeTypeForm.submit();">Submit</a>
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
<div id="churIncomeTypeForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ChurIncomeTypeDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurIncomeType _ChurIncomeType = (ChurIncomeType) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ChurIncomeType.getId() %> </td>

    <td> <%= WebUtil.display(_ChurIncomeType.getIncomeType()) %></td>


<td>
<form name="churIncomeTypeForm<%=_ChurIncomeType.getId()%>2" method="get" action="/v_chur_income_type_edit2.html" >
    <a href="javascript:document.churIncomeTypeForm<%=_ChurIncomeType.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ChurIncomeType.getId() %>">
</form>

</td>
<td>
<a href="/churIncomeTypeAction.html?del=true&id=<%=_ChurIncomeType.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>