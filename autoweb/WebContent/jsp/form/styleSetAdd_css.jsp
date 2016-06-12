<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    StyleSet _StyleSetDefault = new StyleSet();// StyleSetDS.getInstance().getDeafult();
    
    String _nameValue= (reqParams.get("name")==null?WebUtil.display(_StyleSetDefault.getName()):WebUtil.display((String)reqParams.get("name")));
    String _style_idValue= (reqParams.get("styleId")==null?WebUtil.display(_StyleSetDefault.getStyleId()):WebUtil.display((String)reqParams.get("styleId")));
    String _data_style_idValue= (reqParams.get("dataStyleId")==null?WebUtil.display(_StyleSetDefault.getDataStyleId()):WebUtil.display((String)reqParams.get("dataStyleId")));
    String _link_style_idValue= (reqParams.get("linkStyleId")==null?WebUtil.display(_StyleSetDefault.getLinkStyleId()):WebUtil.display((String)reqParams.get("linkStyleId")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_StyleSetDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_StyleSetDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="styleSetForm_topArea" class="formTopArea"></div>
<div id="styleSetForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="styleSetForm" method="POST" action="/styleSetAction.html" >




	<div id="styleSetForm_name_field">
    <div id="styleSetForm_name_label" class="formLabel" >Name </div>
    <div id="styleSetForm_name_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="name" value="<%=WebUtil.display(_nameValue)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="styleSetForm_styleId_field">
    <div id="styleSetForm_styleId_label" class="formLabel" >Style Id </div>
    <div id="styleSetForm_styleId_dropdown" class="formFieldDropDown" >       
        <select id="field" name="styleId">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _style_idValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleSetForm_dataStyleId_field">
    <div id="styleSetForm_dataStyleId_label" class="formLabel" >Data Style Id </div>
    <div id="styleSetForm_dataStyleId_dropdown" class="formFieldDropDown" >       
        <select id="field" name="dataStyleId">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _data_style_idValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="styleSetForm_linkStyleId_field">
    <div id="styleSetForm_linkStyleId_label" class="formLabel" >Link Style Id </div>
    <div id="styleSetForm_linkStyleId_dropdown" class="formFieldDropDown" >       
        <select id="field" name="linkStyleId">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _link_style_idValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>









        <div id="styleSetForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.styleSetForm.submit();">Submit</a>
        </div>      

        <div id="styleSetForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.styleSetForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="styleSetForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = StyleSetDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        StyleSet _StyleSet = (StyleSet) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _StyleSet.getId() %> </td>

    <td> <%= WebUtil.display(_StyleSet.getName()) %></td>
    <td> <%= WebUtil.display(_StyleSet.getStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleSet.getDataStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleSet.getLinkStyleId()) %></td>
    <td> <%= WebUtil.display(_StyleSet.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_StyleSet.getTimeUpdated()) %></td>


<td>
<form name="styleSetForm<%=_StyleSet.getId()%>2" method="get" action="/v_style_set_edit2.html" >
    <a href="javascript:document.styleSetForm<%=_StyleSet.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _StyleSet.getId() %>">
</form>

</td>
<td>
<a href="/styleSetAction.html?del=true&id=<%=_StyleSet.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>