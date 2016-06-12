<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    Linkto _LinktoDefault = new Linkto();// LinktoDS.getInstance().getDeafult();
    
    String _link_keyValue= (reqParams.get("linkKey")==null?WebUtil.display(_LinktoDefault.getLinkKey()):WebUtil.display((String)reqParams.get("linkKey")));
    String _link_targetValue= (reqParams.get("linkTarget")==null?WebUtil.display(_LinktoDefault.getLinkTarget()):WebUtil.display((String)reqParams.get("linkTarget")));
    String _disableValue= (reqParams.get("disable")==null?WebUtil.display(_LinktoDefault.getDisable()):WebUtil.display((String)reqParams.get("disable")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_LinktoDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="linktoForm_topArea" class="formTopArea"></div>
<div id="linktoForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="linktoForm" method="get" action="/linktoAction.html" >




	<div id="linktoForm_linkKey_field">
    <div id="linktoForm_linkKey_label" class="formRequiredLabel" >Link Key* </div>
    <div id="linktoForm_linkKey_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="linkKey" value="<%=WebUtil.display(_link_keyValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="linktoForm_linkTarget_field">
    <div id="linktoForm_linkTarget_label" class="formRequiredLabel" >Link Target* </div>
    <div id="linktoForm_linkTarget_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="30" name="linkTarget" value="<%=WebUtil.display(_link_targetValue)%>"/> 
    </div>      
	</div><div class="clear"></div>



	<div id="linktoForm_disable_field">
    <div id="linktoForm_disable_label" class="formLabel" >Disable </div>
    <div id="linktoForm_disable_dropdown" class="formFieldDropDown" >       
        <select name="disable">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disableValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disableValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>






        <div id="linktoForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.linktoForm.submit();">Submit</a>
        </div>      

        <div id="linktoForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.linktoForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="linktoForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = LinktoDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        Linkto _Linkto = (Linkto) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _Linkto.getId() %> </td>

    <td> <%= WebUtil.display(_Linkto.getLinkKey()) %></td>
    <td> <%= WebUtil.display(_Linkto.getLinkTarget()) %></td>
    <td> <%= WebUtil.display(_Linkto.getDisable()) %></td>
    <td> <%= WebUtil.display(_Linkto.getTimeCreated()) %></td>


<td>
<form name="linktoForm<%=_Linkto.getId()%>" method="get" action="/v_linkto_edit.html" >
    <a href="javascript:document.linktoForm<%=_Linkto.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _Linkto.getId() %>">
</form>
<form name="linktoForm<%=_Linkto.getId()%>2" method="get" action="/v_linkto_edit2.html" >
    <a href="javascript:document.linktoForm<%=_Linkto.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _Linkto.getId() %>">
</form>

</td>
<td>
<a href="/linktoAction.html?del=true&id=<%=_Linkto.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>