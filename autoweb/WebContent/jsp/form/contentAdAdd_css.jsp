<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ContentAd _ContentAdDefault = new ContentAd();// ContentAdDS.getInstance().getDeafult();
    
    String _content_idValue= (reqParams.get("contentId")==null?WebUtil.display(_ContentAdDefault.getContentId()):WebUtil.display((String)reqParams.get("contentId")));
    String _position_codeValue= (reqParams.get("positionCode")==null?WebUtil.display(_ContentAdDefault.getPositionCode()):WebUtil.display((String)reqParams.get("positionCode")));
    String _ad_contentValue= (reqParams.get("adContent")==null?WebUtil.display(_ContentAdDefault.getAdContent()):WebUtil.display((String)reqParams.get("adContent")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_ContentAdDefault.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_ContentAdDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_ContentAdDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));

	String retPage = (String) reqParams.get("returnPage");    

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="contentAdForm_topArea" class="formTopArea"></div>
<div id="contentAdForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="contentAdForm" method="POST" action="/contentAdAction.html" >



    <INPUT TYPE="HIDDEN" NAME="contentId" value="<%=WebUtil.display(_content_idValue)%>" />





	<div id="contentAdForm_positionCode_field">
    <div id="contentAdForm_positionCode_label" class="formLabel" >Position Code </div>
    <div id="contentAdForm_positionCode_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="30" name="positionCode" value="<%=WebUtil.display(_position_codeValue)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="contentAdForm_adContent_field">
    <div id="contentAdForm_adContent_label" class="formLabel" >Ad Content </div>
    <div id="contentAdForm_adContent_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="adContent" COLS="50" ROWS="8" ><%=WebUtil.display(_ad_contentValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>




	<div id="contentAdForm_hide_field">
    <div id="contentAdForm_hide_label" class="formLabel" >Hide </div>
    <div id="contentAdForm_hide_dropdown" class="formFieldDropDown" >       
        <select name="hide">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hideValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hideValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>










        <div id="contentAdForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.contentAdForm.submit();">Submit</a>
        </div>      

        <div id="contentAdForm_cancel" class="formCancel" >       
            <a id="formSubmit" href="javascript:document.contentAdForm.submit();">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="contentAdForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ContentAdDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ContentAd _ContentAd = (ContentAd) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ContentAd.getId() %> </td>

    <td> <%= WebUtil.display(_ContentAd.getContentId()) %></td>
    <td> <%= WebUtil.display(_ContentAd.getPositionCode()) %></td>
    <td> <%= WebUtil.display(_ContentAd.getAdContent()) %></td>
    <td> <%= WebUtil.display(_ContentAd.getHide()) %></td>
    <td> <%= WebUtil.display(_ContentAd.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_ContentAd.getTimeUpdated()) %></td>


<td>
<form name="contentAdForm<%=_ContentAd.getId()%>" method="get" action="/v_content_ad_edit.html" >
    <a href="javascript:document.contentAdForm<%=_ContentAd.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentAd.getId() %>">
</form>
<form name="contentAdForm<%=_ContentAd.getId()%>2" method="get" action="/v_content_ad_edit2.html" >
    <a href="javascript:document.contentAdForm<%=_ContentAd.getId()%>2.submit();">Edit2</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ContentAd.getId() %>">
</form>

</td>
<td>
<a href="/contentAdAction.html?del=true&id=<%=_ContentAd.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>