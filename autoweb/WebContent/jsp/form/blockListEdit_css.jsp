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

    BlockList _BlockList = BlockListDS.getInstance().getById(id);

    if ( _BlockList == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "block_list_home";

    String _ip_dataValue=  WebUtil.display(_BlockList.getIpData());
    String _range_checkValue=  WebUtil.display(_BlockList.getRangeCheck());
    String _reason_codeValue=  WebUtil.display(_BlockList.getReasonCode());
    String _time_createdValue=  WebUtil.display(_BlockList.getTimeCreated());
%> 

<br>
<div id="blockListForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="blockListFormEdit" method="POST" action="/blockListAction.html" >




	<div id="blockListForm_ipData_field">
    <div id="blockListForm_ipData_label" class="formLabel" >Ip Data </div>
    <div id="blockListForm_ipData_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="ipData" value="<%=WebUtil.display(_ip_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="blockListForm_rangeCheck_field">
    <div id="blockListForm_rangeCheck_label" class="formLabel" >Range Check </div>
    <div id="blockListForm_rangeCheck_dropdown" class="formFieldDropDown" >       
        <select name="rangeCheck">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _range_checkValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _range_checkValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>



	<div id="blockListForm_reasonCode_field">
    <div id="blockListForm_reasonCode_label" class="formLabel" >Reason Code </div>
    <div id="blockListForm_reasonCode_dropdown" class="formFieldDropDown" >       
        <select id="field" name="reasonCode">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _reason_codeValue)%>>XX</option-->
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _reason_codeValue)%>>SPAM-AutoBlock</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _reason_codeValue)%>>SPAM-ManualBlock</option>
        <option value="3" <%=HtmlUtil.getOptionSelect("3", _reason_codeValue)%>>Attack-AutoBlock</option>
        <option value="99" <%=HtmlUtil.getOptionSelect("99", _reason_codeValue)%>>Other</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




        <div id="blockListFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.blockListFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlockList.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
