<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ChurIncomeCategory _ChurIncomeCategoryDefault = new ChurIncomeCategory();// ChurIncomeCategoryDS.getInstance().getDeafult();
    
    String _income_categoryValue= (reqParams.get("incomeCategory")==null?WebUtil.display(_ChurIncomeCategoryDefault.getIncomeCategory()):WebUtil.display((String)reqParams.get("incomeCategory")));
    String _displayValue= (reqParams.get("display")==null?WebUtil.display(_ChurIncomeCategoryDefault.getDisplay()):WebUtil.display((String)reqParams.get("display")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ChurIncomeCategoryDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_category_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="churIncomeCategoryForm_topArea" class="formTopArea"></div>
<div id="churIncomeCategoryForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="churIncomeCategoryForm" method="POST" action="/churIncomeCategoryAction.html" >




	<div id="churIncomeCategoryForm_incomeCategory_field">
    <div id="churIncomeCategoryForm_incomeCategory_label" class="formLabel" >Income Category </div>
    <div id="churIncomeCategoryForm_incomeCategory_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="incomeCategory" value="<%=WebUtil.display(_income_categoryValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeCategoryForm_display_field">
    <div id="churIncomeCategoryForm_display_label" class="formLabel" >Display </div>
    <div id="churIncomeCategoryForm_display_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="display" value="<%=WebUtil.display(_displayValue)%>"/>
    </div>      
	</div><div class="clear"></div>





        <div id="churIncomeCategoryForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churIncomeCategoryForm.submit();">Submit</a>
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
<div id="churIncomeCategoryForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ChurIncomeCategoryDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurIncomeCategory _ChurIncomeCategory = (ChurIncomeCategory) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ChurIncomeCategory.getId() %> </td>

    <td> <%= WebUtil.display(_ChurIncomeCategory.getIncomeCategory()) %></td>
    <td> <%= WebUtil.display(_ChurIncomeCategory.getDisplay()) %></td>
    <td> <%= WebUtil.display(_ChurIncomeCategory.getTimeCreated()) %></td>


<td>
<form name="churIncomeCategoryForm<%=_ChurIncomeCategory.getId()%>2" method="get" action="/v_chur_income_category_edit2.html" >
    <a href="javascript:document.churIncomeCategoryForm<%=_ChurIncomeCategory.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ChurIncomeCategory.getId() %>">
</form>

</td>
<td>
<a href="/churIncomeCategoryAction.html?del=true&id=<%=_ChurIncomeCategory.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>