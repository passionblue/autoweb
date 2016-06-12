<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    WhoisData _WhoisDataDefault = new WhoisData();// WhoisDataDS.getInstance().getDeafult();
    
    String _ipValue= (reqParams.get("ip")==null?WebUtil.display(_WhoisDataDefault.getIp()):WebUtil.display((String)reqParams.get("ip")));
    String _whois_dataValue= (reqParams.get("whoisData")==null?WebUtil.display(_WhoisDataDefault.getWhoisData()):WebUtil.display((String)reqParams.get("whoisData")));
    String _serverValue= (reqParams.get("server")==null?WebUtil.display(_WhoisDataDefault.getServer()):WebUtil.display((String)reqParams.get("server")));
    String _force_requestValue= (reqParams.get("forceRequest")==null?WebUtil.display(_WhoisDataDefault.getForceRequest()):WebUtil.display((String)reqParams.get("forceRequest")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_WhoisDataDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_expiredValue= (reqParams.get("timeExpired")==null?WebUtil.display(_WhoisDataDefault.getTimeExpired()):WebUtil.display((String)reqParams.get("timeExpired")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "whois_data_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="whoisDataForm_topArea" class="formTopArea"></div>
<div id="whoisDataForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="whoisDataForm" method="POST" action="/whoisDataAction.html" >




	<div id="whoisDataForm_ip_field">
    <div id="whoisDataForm_ip_label" class="formRequiredLabel" >Ip* </div>
    <div id="whoisDataForm_ip_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="ip" value="<%=WebUtil.display(_ipValue)%>"/> 
    </div>      
	</div><div class="clear"></div>



	<div id="whoisDataForm_whoisData_field">
    <div id="whoisDataForm_whoisData_label" class="formLabel" >Whois Data </div>
    <div id="whoisDataForm_whoisData_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="whoisData" COLS="50" ROWS="8" ><%=WebUtil.display(_whois_dataValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>





	<div id="whoisDataForm_server_field">
    <div id="whoisDataForm_server_label" class="formRequiredLabel" >Server* </div>
    <div id="whoisDataForm_server_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="server" value="<%=WebUtil.display(_serverValue)%>"/> 
    </div>      
	</div><div class="clear"></div>



	<div id="whoisDataForm_forceRequest_field">
    <div id="whoisDataForm_forceRequest_label" class="formLabel" >Force Request </div>
    <div id="whoisDataForm_forceRequest_dropdown" class="formFieldDropDown" >       
        <select name="forceRequest">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _force_requestValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _force_requestValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>










        <div id="whoisDataForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.whoisDataForm.submit();">Submit</a>
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
<div id="whoisDataForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = WhoisDataDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        WhoisData _WhoisData = (WhoisData) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _WhoisData.getId() %> </td>

    <td> <%= WebUtil.display(_WhoisData.getIp()) %></td>
    <td> <%= WebUtil.display(_WhoisData.getWhoisData()) %></td>
    <td> <%= WebUtil.display(_WhoisData.getServer()) %></td>
    <td> <%= WebUtil.display(_WhoisData.getForceRequest()) %></td>
    <td> <%= WebUtil.display(_WhoisData.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_WhoisData.getTimeExpired()) %></td>


<td>
<form name="whoisDataForm<%=_WhoisData.getId()%>2" method="get" action="/v_whois_data_edit2.html" >
    <a href="javascript:document.whoisDataForm<%=_WhoisData.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _WhoisData.getId() %>">
</form>

</td>
<td>
<a href="/whoisDataAction.html?del=true&id=<%=_WhoisData.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>