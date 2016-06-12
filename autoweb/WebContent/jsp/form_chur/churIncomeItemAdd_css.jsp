<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ChurIncomeItem _ChurIncomeItemDefault = new ChurIncomeItem();// ChurIncomeItemDS.getInstance().getDeafult();
    
    String _category_idValue= (reqParams.get("categoryId")==null?WebUtil.display(_ChurIncomeItemDefault.getCategoryId()):WebUtil.display((String)reqParams.get("categoryId")));
    String _income_itemValue= (reqParams.get("incomeItem")==null?WebUtil.display(_ChurIncomeItemDefault.getIncomeItem()):WebUtil.display((String)reqParams.get("incomeItem")));
    String _displayValue= (reqParams.get("display")==null?WebUtil.display(_ChurIncomeItemDefault.getDisplay()):WebUtil.display((String)reqParams.get("display")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_item_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="churIncomeItemForm_topArea" class="formTopArea"></div>
<div id="churIncomeItemForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="churIncomeItemForm" method="POST" action="/churIncomeItemAction.html" >




	<div id="churIncomeItemForm_categoryId_field">
    <div id="churIncomeItemForm_categoryId_label" class="formLabel" >Category Id </div>
    <div id="churIncomeItemForm_categoryId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="categoryId" value="<%=WebUtil.display(_category_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeItemForm_incomeItem_field">
    <div id="churIncomeItemForm_incomeItem_label" class="formLabel" >Income Item </div>
    <div id="churIncomeItemForm_incomeItem_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="incomeItem" value="<%=WebUtil.display(_income_itemValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeItemForm_display_field">
    <div id="churIncomeItemForm_display_label" class="formLabel" >Display </div>
    <div id="churIncomeItemForm_display_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="display" value="<%=WebUtil.display(_displayValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="churIncomeItemForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churIncomeItemForm.submit();">Submit</a>
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
<div id="churIncomeItemForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ChurIncomeItemDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurIncomeItem _ChurIncomeItem = (ChurIncomeItem) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ChurIncomeItem.getId() %> </td>

    <td> <%= WebUtil.display(_ChurIncomeItem.getCategoryId()) %></td>
    <td> <%= WebUtil.display(_ChurIncomeItem.getIncomeItem()) %></td>
    <td> <%= WebUtil.display(_ChurIncomeItem.getDisplay()) %></td>


<td>
<form name="churIncomeItemForm<%=_ChurIncomeItem.getId()%>2" method="get" action="/v_chur_income_item_edit2.html" >
    <a href="javascript:document.churIncomeItemForm<%=_ChurIncomeItem.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ChurIncomeItem.getId() %>">
</form>

</td>
<td>
<a href="/churIncomeItemAction.html?del=true&id=<%=_ChurIncomeItem.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>