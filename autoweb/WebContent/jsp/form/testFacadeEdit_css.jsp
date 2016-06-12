<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
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

    TestFacadeDataHolder _TestFacade = TestFacadeDS.getInstance().getById(id);

    if ( _TestFacade == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "test_facade_home";

    String _dataValue=  WebUtil.display(_TestFacade.getData());
    String _time_createdValue=  WebUtil.display(_TestFacade.getTimeCreated());
    String _facade_dataValue=  WebUtil.display(_TestFacade.getFacadeData());
%> 

<br>
<div id="testFacadeForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="testFacadeFormEdit" method="POST" action="/testFacadeAction.html" >




	<div id="testFacadeForm_data_field">
    <div id="testFacadeForm_data_label" class="formLabel" >Data </div>
    <div id="testFacadeForm_data_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





	<div id="testFacadeForm_facadeData_field">
    <div id="testFacadeForm_facadeData_label" class="formLabel" >Facade Data </div>
    <div id="testFacadeForm_facadeData_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="facadeData" value="<%=WebUtil.display(_facade_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="testFacadeFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.testFacadeFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_TestFacade.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
