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

    TestWrapDataHolder _TestWrap = TestWrapDS.getInstance().getById(id);

    if ( _TestWrap == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "test_wrap_home";

    String _dataValue=  WebUtil.display(_TestWrap.getData());
    String _time_createdValue=  WebUtil.display(_TestWrap.getTimeCreated());
    String _wrap_dataValue=  WebUtil.display(_TestWrap.getWrapData());
%> 

<br>
<div id="testWrapForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="testWrapFormEdit" method="POST" action="/testWrapAction.html" >




	<div id="testWrapForm_data_field">
    <div id="testWrapForm_data_label" class="formLabel" >Data </div>
    <div id="testWrapForm_data_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>





	<div id="testWrapForm_wrapData_field">
    <div id="testWrapForm_wrapData_label" class="formLabel" >Wrap Data </div>
    <div id="testWrapForm_wrapData_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="wrapData" value="<%=WebUtil.display(_wrap_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="testWrapFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.testWrapFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_TestWrap.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
