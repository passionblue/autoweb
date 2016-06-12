<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ContentData _ContentDataDefault = new ContentData();// ContentDataDS.getInstance().getDeafult();
    
    String _content_idValue= (reqParams.get("contentId")==null?WebUtil.display(_ContentDataDefault.getContentId()):WebUtil.display((String)reqParams.get("contentId")));
    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_ContentDataDefault.getData()):WebUtil.display((String)reqParams.get("data")));
    String _option1Value= (reqParams.get("option1")==null?WebUtil.display(_ContentDataDefault.getOption1()):WebUtil.display((String)reqParams.get("option1")));
    String _option2Value= (reqParams.get("option2")==null?WebUtil.display(_ContentDataDefault.getOption2()):WebUtil.display((String)reqParams.get("option2")));
    String _option3Value= (reqParams.get("option3")==null?WebUtil.display(_ContentDataDefault.getOption3()):WebUtil.display((String)reqParams.get("option3")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ContentDataDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_ContentDataDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "content_data_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="contentDataForm_topArea" class="formTopArea"></div>
<div id="contentDataForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="contentDataForm" method="POST" action="/contentDataAction.html" >




	<div id="contentDataForm_contentId_field">
    <div id="contentDataForm_contentId_label" class="formLabel" >Content Id </div>
    <div id="contentDataForm_contentId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="contentId" value="<%=WebUtil.display(_content_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="contentDataForm_data_field">
    <div id="contentDataForm_data_label" class="formLabel" >Data </div>
    <div id="contentDataForm_data_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="data" COLS="50" ROWS="8" ><%=WebUtil.display(_dataValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>





	<div id="contentDataForm_option1_field">
    <div id="contentDataForm_option1_label" class="formLabel" >Option1 </div>
    <div id="contentDataForm_option1_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="option1" value="<%=WebUtil.display(_option1Value)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="contentDataForm_option2_field">
    <div id="contentDataForm_option2_label" class="formLabel" >Option2 </div>
    <div id="contentDataForm_option2_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="option2" value="<%=WebUtil.display(_option2Value)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="contentDataForm_option3_field">
    <div id="contentDataForm_option3_label" class="formLabel" >Option3 </div>
    <div id="contentDataForm_option3_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="option3" value="<%=WebUtil.display(_option3Value)%>"/>
    </div>      
	</div><div class="clear"></div>









        <div id="contentDataForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.contentDataForm.submit();">Submit</a>
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
<div id="contentDataForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ContentDataDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentData _ContentData = (ContentData) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentData.getId() %> </td>

    <td> <%= WebUtil.display(_ContentData.getContentId()) %></td>
    <td> <%= WebUtil.display(_ContentData.getData()) %></td>
    <td> <%= WebUtil.display(_ContentData.getOption1()) %></td>
    <td> <%= WebUtil.display(_ContentData.getOption2()) %></td>
    <td> <%= WebUtil.display(_ContentData.getOption3()) %></td>
    <td> <%= WebUtil.display(_ContentData.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_ContentData.getTimeUpdated()) %></td>


<td>
<form name="contentDataForm<%=_ContentData.getId()%>2" method="get" action="/v_content_data_edit2.html" >
    <a href="javascript:document.contentDataForm<%=_ContentData.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentData.getId() %>">
</form>

</td>
<td>
<a href="/contentDataAction.html?del=true&id=<%=_ContentData.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>