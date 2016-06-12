<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    TestDataDepot _TestDataDepot = TestDataDepotDS.getInstance().getById(id);

    if ( _TestDataDepot == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "test_data_depot_home";

    String _titleValue=  WebUtil.display(_TestDataDepot.getTitle());
    String _dataValue=  WebUtil.display(_TestDataDepot.getData());
    String _typeValue=  WebUtil.display(_TestDataDepot.getType());
    String _time_createdValue=  WebUtil.display(_TestDataDepot.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_TestDataDepot.getTimeUpdated());
%> 

<br>
<div id="testDataDepotForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="testDataDepotFormEdit" method="POST" action="/testDataDepotAction.html" >




	<div id="testDataDepotForm_title_field">
    <div id="testDataDepotForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="testDataDepotForm_title_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="testDataDepotForm_data_field">
    <div id="testDataDepotForm_data_label" class="formRequiredLabel" >Data* </div>
    <div id="testDataDepotForm_data_text" class="formFieldText" >       
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






        <div id="testDataDepotFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.testDataDepotFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_TestDataDepot.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
