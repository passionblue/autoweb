<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    TestCore _TestCoreDefault = new TestCore();// TestCoreDS.getInstance().getDeafult();
    
    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_TestCoreDefault.getData()):WebUtil.display((String)reqParams.get("data")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_TestCoreDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "test_core_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="testCoreForm_topArea" class="formTopArea"></div>
<div id="testCoreForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="testCoreForm" method="POST" action="/testCoreAction.html" >




	<div id="testCoreForm_data_field">
    <div id="testCoreForm_data_label" class="formLabel" >Data </div>
    <div id="testCoreForm_data_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/>
    </div>      
	</div><div class="clear"></div>





        <div id="testCoreForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.testCoreForm.submit();">Submit</a>
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
<div id="testCoreForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = TestCoreDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        TestCore _TestCore = (TestCore) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _TestCore.getId() %> </td>

    <td> <%= WebUtil.display(_TestCore.getData()) %></td>
    <td> <%= WebUtil.display(_TestCore.getTimeCreated()) %></td>


<td>
<form name="testCoreForm<%=_TestCore.getId()%>2" method="get" action="/v_test_core_edit2.html" >
    <a href="javascript:document.testCoreForm<%=_TestCore.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _TestCore.getId() %>">
</form>

</td>
<td>
<a href="/testCoreAction.html?del=true&id=<%=_TestCore.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>