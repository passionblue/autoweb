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

    ContentFeedConfig _ContentFeedConfig = ContentFeedConfigDS.getInstance().getById(id);

    if ( _ContentFeedConfig == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _feed_categoryValue=  WebUtil.display(_ContentFeedConfig.getFeedCategory());
    String _feed_typeValue=  WebUtil.display(_ContentFeedConfig.getFeedType());
    String _display_typeValue=  WebUtil.display(_ContentFeedConfig.getDisplayType());
    String _time_createdValue=  WebUtil.display(_ContentFeedConfig.getTimeCreated());
%> 

<br>
<div id="contentFeedConfigForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="contentFeedConfigFormEdit" method="get" action="/contentFeedConfigAction.html" >




	<div id="contentFeedConfigForm_feedCategory_field">
    <div id="contentFeedConfigForm_feedCategory_label" class="formRequiredLabel" >Feed Category* </div>
    <div id="contentFeedConfigForm_feedCategory_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="feedCategory" value="<%=WebUtil.display(_feed_categoryValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="contentFeedConfigForm_feedType_field">
    <div id="contentFeedConfigForm_feedType_label" class="formLabel" >Feed Type </div>
    <div id="contentFeedConfigForm_feedType_dropdown" class="formFieldDropDown" >       
        <select id="field" name="feedType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _feed_typeValue)%>>XX</option-->
        <option value="{displayString=0, internalValue=0}" <%=HtmlUtil.getOptionSelect("{displayString=0, internalValue=0}", _feed_typeValue)%>>{displayString=0, internalValue=0}</option>
        <option value="{displayString=1, internalValue=1}" <%=HtmlUtil.getOptionSelect("{displayString=1, internalValue=1}", _feed_typeValue)%>>{displayString=1, internalValue=1}</option>
        <option value="{displayString=2, internalValue=2}" <%=HtmlUtil.getOptionSelect("{displayString=2, internalValue=2}", _feed_typeValue)%>>{displayString=2, internalValue=2}</option>
        <option value="{displayString=3, internalValue=3}" <%=HtmlUtil.getOptionSelect("{displayString=3, internalValue=3}", _feed_typeValue)%>>{displayString=3, internalValue=3}</option>
        <option value="{displayString=4, internalValue=4}" <%=HtmlUtil.getOptionSelect("{displayString=4, internalValue=4}", _feed_typeValue)%>>{displayString=4, internalValue=4}</option>
        <option value="{displayString=5, internalValue=5}" <%=HtmlUtil.getOptionSelect("{displayString=5, internalValue=5}", _feed_typeValue)%>>{displayString=5, internalValue=5}</option>
        <option value="{displayString=6, internalValue=6}" <%=HtmlUtil.getOptionSelect("{displayString=6, internalValue=6}", _feed_typeValue)%>>{displayString=6, internalValue=6}</option>
        <option value="{displayString=7, internalValue=7}" <%=HtmlUtil.getOptionSelect("{displayString=7, internalValue=7}", _feed_typeValue)%>>{displayString=7, internalValue=7}</option>
        <option value="{displayString=8, internalValue=8}" <%=HtmlUtil.getOptionSelect("{displayString=8, internalValue=8}", _feed_typeValue)%>>{displayString=8, internalValue=8}</option>
        <option value="{displayString=9, internalValue=9}" <%=HtmlUtil.getOptionSelect("{displayString=9, internalValue=9}", _feed_typeValue)%>>{displayString=9, internalValue=9}</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="contentFeedConfigForm_displayType_field">
    <div id="contentFeedConfigForm_displayType_label" class="formLabel" >Display Type </div>
    <div id="contentFeedConfigForm_displayType_dropdown" class="formFieldDropDown" >       
        <select id="field" name="displayType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _display_typeValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




        <div id="contentFeedConfigFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.contentFeedConfigFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ContentFeedConfig.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
