<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    GenFormFlowDataHolder _GenFormFlowDefault = new GenFormFlowDataHolder();// GenFormFlowDS.getInstance().getDeafult();
    
    String _ext_stringValue= (reqParams.get("extString")==null?WebUtil.display(_GenFormFlowDefault.getExtString()):WebUtil.display((String)reqParams.get("extString")));
    String _ext_intValue= (reqParams.get("extInt")==null?WebUtil.display(_GenFormFlowDefault.getExtInt()):WebUtil.display((String)reqParams.get("extInt")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "gen_form_flow_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="genFormFlowForm_topArea" class="formTopArea"></div>
<div id="genFormFlowForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="genFormFlowForm" method="POST" action="/genFormFlowAction.html" >




	<div id="genFormFlowForm_extString_field">
    <div id="genFormFlowForm_extString_label" class="formLabel" >ExtraString </div>
    <div id="genFormFlowForm_extString_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="3" name="extString" value="<%=WebUtil.display(_ext_stringValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="genFormFlowForm_extInt_field">
    <div id="genFormFlowForm_extInt_label" class="formLabel" >Ext Int </div>
    <div id="genFormFlowForm_extInt_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="extInt" value="<%=WebUtil.display(_ext_intValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="genFormFlowForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.genFormFlowForm.submit();">Submit</a>
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
<div id="genFormFlowForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = GenFormFlowDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        GenFormFlowDataHolder _GenFormFlow = (GenFormFlowDataHolder) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _GenFormFlow.getId() %> </td>

    <td> <%= WebUtil.display(_GenFormFlow.getExtString()) %></td>
    <td> <%= WebUtil.display(_GenFormFlow.getExtInt()) %></td>


<td>
<form name="genFormFlowForm<%=_GenFormFlow.getId()%>2" method="get" action="/v_${useDbTable}_edit2.html" >
    <a href="javascript:document.genFormFlowForm<%=_GenFormFlow.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _GenFormFlow.getId() %>">
</form>

</td>
<td>
<a href="/genFormFlowAction.html?del=true&id=<%=_GenFormFlow.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>