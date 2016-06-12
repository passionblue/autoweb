<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ChurPayee _ChurPayeeDefault = new ChurPayee();// ChurPayeeDS.getInstance().getDeafult();
    
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_ChurPayeeDefault.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _remarkValue= (reqParams.get("remark")==null?WebUtil.display(_ChurPayeeDefault.getRemark()):WebUtil.display((String)reqParams.get("remark")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_payee_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="churPayeeForm_topArea" class="formTopArea"></div>
<div id="churPayeeForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="churPayeeForm" method="POST" action="/churPayeeAction.html" >




	<div id="churPayeeForm_title_field">
    <div id="churPayeeForm_title_label" class="formLabel" >Title </div>
    <div id="churPayeeForm_title_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churPayeeForm_remark_field">
    <div id="churPayeeForm_remark_label" class="formLabel" >Remark </div>
    <div id="churPayeeForm_remark_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="remark" value="<%=WebUtil.display(_remarkValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="churPayeeForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churPayeeForm.submit();">Submit</a>
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
<div id="churPayeeForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ChurPayeeDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurPayee _ChurPayee = (ChurPayee) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ChurPayee.getId() %> </td>

    <td> <%= WebUtil.display(_ChurPayee.getTitle()) %></td>
    <td> <%= WebUtil.display(_ChurPayee.getRemark()) %></td>


<td>
<form name="churPayeeForm<%=_ChurPayee.getId()%>2" method="get" action="/v_chur_payee_edit2.html" >
    <a href="javascript:document.churPayeeForm<%=_ChurPayee.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ChurPayee.getId() %>">
</form>

</td>
<td>
<a href="/churPayeeAction.html?del=true&id=<%=_ChurPayee.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>