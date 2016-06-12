<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    MenuItem _MenuItem = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_MenuItem = MenuItemDS.getInstance().getById(id);
		if ( _MenuItem == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _MenuItem = new MenuItem();// MenuItemDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();

	}	


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "menu_item_home";

    String _panel_idValue= (reqParams.get("panelId")==null?WebUtil.display(_MenuItem.getPanelId()):WebUtil.display((String)reqParams.get("panelId")));
    String _parent_idValue= (reqParams.get("parentId")==null?WebUtil.display(_MenuItem.getParentId()):WebUtil.display((String)reqParams.get("parentId")));
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_MenuItem.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_MenuItem.getData()):WebUtil.display((String)reqParams.get("data")));
    String _data2Value= (reqParams.get("data2")==null?WebUtil.display(_MenuItem.getData2()):WebUtil.display((String)reqParams.get("data2")));
    String _target_typeValue= (reqParams.get("targetType")==null?WebUtil.display(_MenuItem.getTargetType()):WebUtil.display((String)reqParams.get("targetType")));
    String _order_idxValue= (reqParams.get("orderIdx")==null?WebUtil.display(_MenuItem.getOrderIdx()):WebUtil.display((String)reqParams.get("orderIdx")));
    String _page_idValue= (reqParams.get("pageId")==null?WebUtil.display(_MenuItem.getPageId()):WebUtil.display((String)reqParams.get("pageId")));
    String _content_idValue= (reqParams.get("contentId")==null?WebUtil.display(_MenuItem.getContentId()):WebUtil.display((String)reqParams.get("contentId")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_MenuItem.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_MenuItem.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
%> 

<br>
<div id="menuItemForm" class="formFrame-style2">
<div id="menuItemFormInnerFrame" class="formInnerFrame-style2">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="menuItemForm_Form" method="POST" action="/menuItemAction.html" id="menuItemForm_Form">





	<div id="menuItemForm_panelId_field" class="formFieldFrame-style2">
    <div id="menuItemForm_panelId_label" class="formLabel-style2" >Panel Id </div>
    <div id="menuItemForm_panelId_text" class="formFieldText-style2" >       
        <input id="panelId" class="field" type="text" size="70" name="panelId" value="<%=WebUtil.display(_panel_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="menuItemForm_parentId_field" class="formFieldFrame-style2">
    <div id="menuItemForm_parentId_label" class="formLabel-style2" >Parent Id </div>
    <div id="menuItemForm_parentId_text" class="formFieldText-style2" >       
        <input id="parentId" class="field" type="text" size="70" name="parentId" value="<%=WebUtil.display(_parent_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="menuItemForm_title_field" class="formFieldFrame-style2">
    <div id="menuItemForm_title_label" class="formRequiredLabel-style2" >Title* </div>
    <div id="menuItemForm_title_text" class="formFieldTex-style2t" >       
        <input id="title" class="requiredField" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="menuItemForm_data_field" class="formFieldFrame-style2">
    <div id="menuItemForm_data_label" class="formLabel-style2" >Data </div>
    <div id="menuItemForm_data_text" class="formFieldText-style2" >       
        <input id="data" class="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="menuItemForm_data2_field" class="formFieldFrame-style2">
    <div id="menuItemForm_data2_label" class="formLabel-style2" >Data2 </div>
    <div id="menuItemForm_data2_text" class="formFieldText-style2" >       
        <input id="data2" class="field" type="text" size="70" name="data2" value="<%=WebUtil.display(_data2Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="menuItemForm_targetType_field" class="formFieldFrame-style2">
    <div id="menuItemForm_targetType_label" class="formLabel-style2" >Target Type </div>
    <div id="menuItemForm_targetType_dropdown" class="formFieldDropDown-style2" >       
        <select class="requiredField" name="targetType" id="targetType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _target_typeValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _target_typeValue)%>>Page</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _target_typeValue)%>>Link</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _target_typeValue)%>>Content</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>





	<div id="menuItemForm_orderIdx_field" class="formFieldFrame-style2">
    <div id="menuItemForm_orderIdx_label" class="formLabel-style2" >Order Idx </div>
    <div id="menuItemForm_orderIdx_text" class="formFieldText-style2" >       
        <input id="orderIdx" class="field" type="text" size="70" name="orderIdx" value="<%=WebUtil.display(_order_idxValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="menuItemForm_pageId_field" class="formFieldFrame-style2">
    <div id="menuItemForm_pageId_label" class="formLabel-style2" >Page Id </div>
    <div id="menuItemForm_pageId_text" class="formFieldText-style2" >       
        <input id="pageId" class="field" type="text" size="70" name="pageId" value="<%=WebUtil.display(_page_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="menuItemForm_contentId_field" class="formFieldFrame-style2">
    <div id="menuItemForm_contentId_label" class="formLabel-style2" >Content Id </div>
    <div id="menuItemForm_contentId_text" class="formFieldText-style2" >       
        <input id="contentId" class="field" type="text" size="70" name="contentId" value="<%=WebUtil.display(_content_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="menuItemForm_hide_field" class="formFieldFrame-style2">
    <div id="menuItemForm_hide_label" class="formLabel-style2" >Hide </div>
    <div id="menuItemForm_hide_dropdown" class="formFieldDropDown-style2" >       
        <select name="hide">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hideValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hideValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





	<div class="submitFrame">

        <div id="menuItemForm_submit" class="formSubmit-style2" >       
            <a id="formSubmit2" href="javascript:document.menuItemForm_Form.submit();">Submit</a>
        </div>      

        <div id="menuItemForm_submit_cancel" class="formCancel-style2" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="menuItemForm_submit_ext" class="formSubmitExt-style2" >       
            <a href="#">Ext</a>
        </div>      
	<div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MenuItem.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>


<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> 
</div> <!-- form -->
