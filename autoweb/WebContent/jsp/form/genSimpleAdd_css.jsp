<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    GenSimpleDataHolder _GenSimpleDefault = new GenSimpleDataHolder();// GenSimpleDS.getInstance().getDeafult();
    
    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_GenSimpleDefault.getData()):WebUtil.display((String)reqParams.get("data")));
    String _activeValue= (reqParams.get("active")==null?WebUtil.display(_GenSimpleDefault.getActive()):WebUtil.display((String)reqParams.get("active")));
    String _ext_stringValue= (reqParams.get("extString")==null?WebUtil.display(_GenSimpleDefault.getExtString()):WebUtil.display((String)reqParams.get("extString")));
    String _ext_intValue= (reqParams.get("extInt")==null?WebUtil.display(_GenSimpleDefault.getExtInt()):WebUtil.display((String)reqParams.get("extInt")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "gen_simple_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="genSimpleForm_topArea" class="formTopArea"></div>
<div id="genSimpleForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="genSimpleForm" method="POST" action="/genSimpleAction.html" >




	<div id="genSimpleForm_data_field">
    <div id="genSimpleForm_data_label" class="formLabel" >Data </div>
    <div id="genSimpleForm_data_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="genSimpleForm_active_field">
    <div id="genSimpleForm_active_label" class="formLabel" >Active </div>
    <div id="genSimpleForm_active_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="active" value="<%=WebUtil.display(_activeValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="genSimpleForm_extString_field">
    <div id="genSimpleForm_extString_label" class="formLabel" >ExtraString </div>
    <div id="genSimpleForm_extString_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="3" name="extString" value="<%=WebUtil.display(_ext_stringValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="genSimpleForm_extInt_field">
    <div id="genSimpleForm_extInt_label" class="formLabel" >Ext Int </div>
    <div id="genSimpleForm_extInt_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="extInt" value="<%=WebUtil.display(_ext_intValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="genSimpleForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.genSimpleForm.submit();">Submit</a>
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
<div id="genSimpleForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = GenSimpleDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        GenSimpleDataHolder _GenSimple = (GenSimpleDataHolder) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _GenSimple.getId() %> </td>

    <td> <%= WebUtil.display(_GenSimple.getData()) %></td>
    <td> <%= WebUtil.display(_GenSimple.getActive()) %></td>
    <td> <%= WebUtil.display(_GenSimple.getExtString()) %></td>
    <td> <%= WebUtil.display(_GenSimple.getExtInt()) %></td>


<td>
<form name="genSimpleForm<%=_GenSimple.getId()%>2" method="get" action="/v_gen_simple_edit2.html" >
    <a href="javascript:document.genSimpleForm<%=_GenSimple.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _GenSimple.getId() %>">
</form>

</td>
<td>
<a href="/genSimpleAction.html?del=true&id=<%=_GenSimple.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>