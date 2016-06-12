<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    TestDataDepot _TestDataDepotDefault = new TestDataDepot();// TestDataDepotDS.getInstance().getDeafult();
    
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_TestDataDepotDefault.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_TestDataDepotDefault.getData()):WebUtil.display((String)reqParams.get("data")));
    String _typeValue= (reqParams.get("type")==null?WebUtil.display(_TestDataDepotDefault.getType()):WebUtil.display((String)reqParams.get("type")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_TestDataDepotDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_TestDataDepotDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "test_data_depot_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="testDataDepotForm_topArea" class="formTopArea"></div>
<div id="testDataDepotForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="testDataDepotForm" method="POST" action="/testDataDepotAction.html" >




	<div id="testDataDepotForm_title_field">
    <div id="testDataDepotForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="testDataDepotForm_title_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> 
    </div>      
	</div><div class="clear"></div>



	<div id="testDataDepotForm_data_field">
    <div id="testDataDepotForm_data_label" class="formRequiredLabel" >Data* </div>
    <div id="testDataDepotForm_data_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="requiredField" NAME="data" COLS="50" ROWS="8" ><%=WebUtil.display(_dataValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>




	<div id="testDataDepotForm_type_field">
    <div id="testDataDepotForm_type_label" class="formLabel" >Type </div>
    <div id="testDataDepotForm_type_dropdown" class="formFieldDropDown" >       
        <select id="field" name="type">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _typeValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _typeValue)%>>0</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _typeValue)%>>1</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _typeValue)%>>2</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _typeValue)%>>3</option>
        <option value="4" <%=HtmlUtil.getOptionSelect("4", _typeValue)%>>4</option>
        <option value="5" <%=HtmlUtil.getOptionSelect("5", _typeValue)%>>5</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>









        <div id="testDataDepotForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.testDataDepotForm.submit();">Submit</a>
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
<div id="testDataDepotForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = TestDataDepotDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        TestDataDepot _TestDataDepot = (TestDataDepot) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _TestDataDepot.getId() %> </td>

    <td> <%= WebUtil.display(_TestDataDepot.getTitle()) %></td>
    <td> <%= WebUtil.display(_TestDataDepot.getData()) %></td>
    <td> <%= WebUtil.display(_TestDataDepot.getType()) %></td>
    <td> <%= WebUtil.display(_TestDataDepot.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_TestDataDepot.getTimeUpdated()) %></td>


<td>
<form name="testDataDepotForm<%=_TestDataDepot.getId()%>2" method="get" action="/v_test_data_depot_edit2.html" >
    <a href="javascript:document.testDataDepotForm<%=_TestDataDepot.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _TestDataDepot.getId() %>">
</form>

</td>
<td>
<a href="/testDataDepotAction.html?del=true&id=<%=_TestDataDepot.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>