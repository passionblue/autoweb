<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    TestFacadeDataHolder _TestFacadeDefault = new TestFacadeDataHolder();// TestFacadeDS.getInstance().getDeafult();
    
    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_TestFacadeDefault.getData()):WebUtil.display((String)reqParams.get("data")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_TestFacadeDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _facade_dataValue= (reqParams.get("facadeData")==null?WebUtil.display(_TestFacadeDefault.getFacadeData()):WebUtil.display((String)reqParams.get("facadeData")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "test_facade_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="testFacadeForm_topArea" class="formTopArea"></div>
<div id="testFacadeForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="testFacadeForm" method="POST" action="/testFacadeAction.html" >




	<div id="testFacadeForm_data_field">
    <div id="testFacadeForm_data_label" class="formLabel" >Data </div>
    <div id="testFacadeForm_data_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/>
    </div>      
	</div><div class="clear"></div>








	<div id="testFacadeForm_facadeData_field">
    <div id="testFacadeForm_facadeData_label" class="formLabel" >Facade Data </div>
    <div id="testFacadeForm_facadeData_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="facadeData" value="<%=WebUtil.display(_facade_dataValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="testFacadeForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.testFacadeForm.submit();">Submit</a>
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
<div id="testFacadeForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = TestFacadeDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        TestFacadeDataHolder _TestFacade = (TestFacadeDataHolder) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _TestFacade.getId() %> </td>

    <td> <%= WebUtil.display(_TestFacade.getData()) %></td>
    <td> <%= WebUtil.display(_TestFacade.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_TestFacade.getFacadeData()) %></td>


<td>
<form name="testFacadeForm<%=_TestFacade.getId()%>2" method="get" action="/v_test_core_edit2.html" >
    <a href="javascript:document.testFacadeForm<%=_TestFacade.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _TestFacade.getId() %>">
</form>

</td>
<td>
<a href="/testFacadeAction.html?del=true&id=<%=_TestFacade.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>