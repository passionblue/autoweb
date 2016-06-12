<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    Linkto _Linkto = LinktoDS.getInstance().getById(id);

    if ( _Linkto == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _link_keyValue=  WebUtil.display(_Linkto.getLinkKey());
    String _link_targetValue=  WebUtil.display(_Linkto.getLinkTarget());
    String _disableValue=  WebUtil.display(_Linkto.getDisable());
    String _time_createdValue=  WebUtil.display(_Linkto.getTimeCreated());
%> 

<br>
<div id="linktoForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="linktoFormEdit" method="get" action="/linktoAction.html" >




	<div id="linktoForm_linkKey_field">
    <div id="linktoForm_linkKey_label" class="formRequiredLabel" >Link Key* </div>
    <div id="linktoForm_linkKey_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="linkKey" value="<%=WebUtil.display(_link_keyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="linktoForm_linkTarget_field">
    <div id="linktoForm_linkTarget_label" class="formRequiredLabel" >Link Target* </div>
    <div id="linktoForm_linkTarget_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="linkTarget" value="<%=WebUtil.display(_link_targetValue)%>"/> <span></span>
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





        <div id="linktoFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.linktoFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Linkto.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
