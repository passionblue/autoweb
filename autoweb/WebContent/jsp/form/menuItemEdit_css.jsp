<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    MenuItem _MenuItem = MenuItemDS.getInstance().getById(id);

    if ( _MenuItem == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "menu_item_home";

    String _panel_idValue=  WebUtil.display(_MenuItem.getPanelId());
    String _parent_idValue=  WebUtil.display(_MenuItem.getParentId());
    String _titleValue=  WebUtil.display(_MenuItem.getTitle());
    String _dataValue=  WebUtil.display(_MenuItem.getData());
    String _data2Value=  WebUtil.display(_MenuItem.getData2());
    String _target_typeValue=  WebUtil.display(_MenuItem.getTargetType());
    String _order_idxValue=  WebUtil.display(_MenuItem.getOrderIdx());
    String _page_idValue=  WebUtil.display(_MenuItem.getPageId());
    String _content_idValue=  WebUtil.display(_MenuItem.getContentId());
    String _hideValue=  WebUtil.display(_MenuItem.getHide());
    String _time_createdValue=  WebUtil.display(_MenuItem.getTimeCreated());
%> 

<br>
<div id="menuItemForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="menuItemFormEdit" method="POST" action="/menuItemAction.html" >




	<div id="menuItemForm_panelId_field">
    <div id="menuItemForm_panelId_label" class="formLabel" >Panel Id </div>
    <div id="menuItemForm_panelId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="panelId" value="<%=WebUtil.display(_panel_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="menuItemForm_parentId_field">
    <div id="menuItemForm_parentId_label" class="formLabel" >Parent Id </div>
    <div id="menuItemForm_parentId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="parentId" value="<%=WebUtil.display(_parent_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="menuItemForm_title_field">
    <div id="menuItemForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="menuItemForm_title_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="menuItemForm_data_field">
    <div id="menuItemForm_data_label" class="formLabel" >Data </div>
    <div id="menuItemForm_data_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="menuItemForm_data2_field">
    <div id="menuItemForm_data2_label" class="formLabel" >Data2 </div>
    <div id="menuItemForm_data2_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="data2" value="<%=WebUtil.display(_data2Value)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div id="menuItemForm_targetType_field">
    <div id="menuItemForm_targetType_label" class="formLabel" >Target Type </div>
    <div id="menuItemForm_targetType_dropdown" class="formFieldDropDown" >       
        <select id="requiredField" name="targetType">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _target_typeValue)%>>XX</option-->
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _target_typeValue)%>>Page</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _target_typeValue)%>>Link</option>
        <option value="2" <%=HtmlUtil.getOptionSelect("2", _target_typeValue)%>>Content</option>
        </select> <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="menuItemForm_orderIdx_field">
    <div id="menuItemForm_orderIdx_label" class="formLabel" >Order Idx </div>
    <div id="menuItemForm_orderIdx_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="orderIdx" value="<%=WebUtil.display(_order_idxValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="menuItemForm_pageId_field">
    <div id="menuItemForm_pageId_label" class="formLabel" >Page Id </div>
    <div id="menuItemForm_pageId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="pageId" value="<%=WebUtil.display(_page_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="menuItemForm_contentId_field">
    <div id="menuItemForm_contentId_label" class="formLabel" >Content Id </div>
    <div id="menuItemForm_contentId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="contentId" value="<%=WebUtil.display(_content_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="menuItemForm_hide_field">
    <div id="menuItemForm_hide_label" class="formLabel" >Hide </div>
    <div id="menuItemForm_hide_dropdown" class="formFieldDropDown" >       
        <select name="hide">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _hideValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _hideValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>





        <div id="menuItemFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.menuItemFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_MenuItem.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
