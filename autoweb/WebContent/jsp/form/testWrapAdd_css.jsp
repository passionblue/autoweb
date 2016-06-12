<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    TestWrapDataHolder _TestWrapDefault = new TestWrapDataHolder();// TestWrapDS.getInstance().getDeafult();
    
    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_TestWrapDefault.getData()):WebUtil.display((String)reqParams.get("data")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_TestWrapDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _wrap_dataValue= (reqParams.get("wrapData")==null?WebUtil.display(_TestWrapDefault.getWrapData()):WebUtil.display((String)reqParams.get("wrapData")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "test_wrap_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="testWrapForm_topArea" class="formTopArea"></div>
<div id="testWrapForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="testWrapForm" method="POST" action="/testWrapAction.html" >




	<div id="testWrapForm_data_field">
    <div id="testWrapForm_data_label" class="formLabel" >Data </div>
    <div id="testWrapForm_data_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/>
    </div>      
	</div><div class="clear"></div>








	<div id="testWrapForm_wrapData_field">
    <div id="testWrapForm_wrapData_label" class="formLabel" >Wrap Data </div>
    <div id="testWrapForm_wrapData_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="wrapData" value="<%=WebUtil.display(_wrap_dataValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="testWrapForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.testWrapForm.submit();">Submit</a>
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
<div id="testWrapForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = TestWrapDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        TestWrapDataHolder _TestWrap = (TestWrapDataHolder) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _TestWrap.getId() %> </td>

    <td> <%= WebUtil.display(_TestWrap.getData()) %></td>
    <td> <%= WebUtil.display(_TestWrap.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_TestWrap.getWrapData()) %></td>


<td>
<form name="testWrapForm<%=_TestWrap.getId()%>2" method="get" action="/v_test_core_edit2.html" >
    <a href="javascript:document.testWrapForm<%=_TestWrap.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _TestWrap.getId() %>">
</form>

</td>
<td>
<a href="/testWrapAction.html?del=true&id=<%=_TestWrap.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>