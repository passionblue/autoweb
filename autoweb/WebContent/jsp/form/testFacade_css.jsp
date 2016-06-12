<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    TestFacadeDataHolder _TestFacade = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_TestFacade = TestFacadeDS.getInstance().getById(id);
		if ( _TestFacade == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _TestFacade = new TestFacadeDataHolder();// TestFacadeDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "test_facade_home";

    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_TestFacade.getData()):WebUtil.display((String)reqParams.get("data")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_TestFacade.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _facade_dataValue= (reqParams.get("facadeData")==null?WebUtil.display(_TestFacade.getFacadeData()):WebUtil.display((String)reqParams.get("facadeData")));

    long pagestamp = System.nanoTime();
%> 

<br>
<div id="testFacadeForm" class="formFrame">
<div id="testFacadeFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="testFacadeForm_Form" method="POST" action="/testFacadeAction.html" id="testFacadeForm_Form">






	<div id="testFacadeForm_data_field" class="formFieldFrame">
    <div id="testFacadeForm_data_label" class="formLabel" >Data </div>
    <div id="testFacadeForm_data_text" class="formFieldText" >       
        <input id="data" class="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>







	<div id="testFacadeForm_facadeData_field" class="formFieldFrame">
    <div id="testFacadeForm_facadeData_label" class="formLabel" >Facade Data </div>
    <div id="testFacadeForm_facadeData_text" class="formFieldText" >       
        <input id="facadeData" class="field" type="text" size="70" name="facadeData" value="<%=WebUtil.display(_facade_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

<div class="submitFrame">

    <div id="testFacadeForm_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.testFacadeForm_Form.submit();">Submit</a>
    </div>      
<!--
    <div id="testFacadeForm_submit_cancel" class="formButton" >       
        <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
    </div>      

    <div id="testFacadeForm_submit_ext" class="formButton" >       
        <a href="#">Ext</a>
    </div>      
-->
    <div id="testFacadeForm_submit_cancel" class="formButton" >       
        <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
    </div>      

    <div id="testFacadeForm_submit_ext" class="formButton" >       
        <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
    </div>      

</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_TestFacade.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>

<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">

<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> 
</div> <!-- form -->


<%
	List list_TestFacade = new ArrayList();
	TestFacadeDS ds_TestFacade = TestFacadeDS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_TestFacade = ds_TestFacade.getAll();
	else		
    	list_TestFacade = ds_TestFacade.getBySiteId(site.getId());

%> 



<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin2) optionQueryStr += "&listAllByAdmin=true";


	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list_TestFacade, numDisplayInPage, listPage);

	list_TestFacade = PagingUtil.getPagedList(pagingInfo, list_TestFacade);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>
<!-- =================== END PAGING =================== -->

 
<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
//	if (showListAllByAdmin) {
	if (true) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>

    <td class="columnTitle">  Data </td> 
    <td class="columnTitle">  Time Created </td> 
    <td class="columnTitle">  Facade Data </td> 
	<td class="columnTitle"> EDIT </td>
	<td class="columnTitle"> DEL </td>
</TR>

<%

    for(Iterator iter = list_TestFacade.iterator();iter.hasNext();) {
        TestFacadeDataHolder o_TestFacade = (TestFacadeDataHolder) iter.next();
%>

<TR id="tableRow<%= o_TestFacade.getId()%>">
    <td> <%= o_TestFacade.getId() %> </td>

<%	
//	if (showListAllByAdmin) {
	if (true) {
	long siteId = o_TestFacade.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= o_TestFacade.getData()  %> </td>
	<td> <%= o_TestFacade.getTimeCreated()  %> </td>
	<td> <%= o_TestFacade.getFacadeData()  %> </td>
	<td>
	    <a href="javascript:;" title="Edit" class="deleteLink" onclick="open_edit_test_facade_form('<%=o_TestFacade.getId()%>');">Edit</a>
	</td>
	<td> 
		<a href="javascript:sendFormAjaxSimple('/testFacadeAction.html?del=true&id=<%=o_TestFacade.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,<%=o_TestFacade.getId() %> );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
		<a href="javascript:;" onclick="delete_test_facade_form('<%=o_TestFacade.getId()%>');"><img id="deleteRow" src="/images/icons/led/cross.png"/></a>
	</td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
		if ($("#tableRow" + msg)) {
			$("#tableRow" + msg).fadeOut(1000);
		}
	}
	function open_edit_test_facade_form(target){
		location.href='/v_test_facade_form.html?cmd=edit&prv_returnPage=<%=PageViewUtil.getCurrentViewAlias(request)%>&id=' + target;
	}
	function delete_test_facade_form(target){
		javascript:sendFormAjaxSimple('/testFacadeAction.html?del=true&id='+target+'&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal,target);
	}

</script>


