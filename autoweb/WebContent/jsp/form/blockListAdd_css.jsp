<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    BlockList _BlockListDefault = new BlockList();// BlockListDS.getInstance().getDeafult();
    
    String _ip_dataValue= (reqParams.get("ipData")==null?WebUtil.display(_BlockListDefault.getIpData()):WebUtil.display((String)reqParams.get("ipData")));
    String _range_checkValue= (reqParams.get("rangeCheck")==null?WebUtil.display(_BlockListDefault.getRangeCheck()):WebUtil.display((String)reqParams.get("rangeCheck")));
    String _reason_codeValue= (reqParams.get("reasonCode")==null?WebUtil.display(_BlockListDefault.getReasonCode()):WebUtil.display((String)reqParams.get("reasonCode")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_BlockListDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "block_list_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="blockListForm_topArea" class="formTopArea"></div>
<div id="blockListForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="blockListForm" method="POST" action="/blockListAction.html" >




	<div id="blockListForm_ipData_field">
    <div id="blockListForm_ipData_label" class="formLabel" >Ip Data </div>
    <div id="blockListForm_ipData_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="ipData" value="<%=WebUtil.display(_ip_dataValue)%>"/>
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





        <div id="blockListForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.blockListForm.submit();">Submit</a>
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
<div id="blockListForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = BlockListDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlockList _BlockList = (BlockList) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlockList.getId() %> </td>

    <td> <%= WebUtil.display(_BlockList.getIpData()) %></td>
    <td> <%= WebUtil.display(_BlockList.getRangeCheck()) %></td>
    <td> <%= WebUtil.display(_BlockList.getReasonCode()) %></td>
    <td> <%= WebUtil.display(_BlockList.getTimeCreated()) %></td>


<td>
<form name="blockListForm<%=_BlockList.getId()%>2" method="get" action="/v_block_list_edit2.html" >
    <a href="javascript:document.blockListForm<%=_BlockList.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlockList.getId() %>">
</form>

</td>
<td>
<a href="/blockListAction.html?del=true&id=<%=_BlockList.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>