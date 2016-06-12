<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    MenuItem _MenuItemDefault = new MenuItem();// MenuItemDS.getInstance().getDeafult();
    
    String _panel_idValue= (reqParams.get("panelId")==null?WebUtil.display(_MenuItemDefault.getPanelId()):WebUtil.display((String)reqParams.get("panelId")));
    String _parent_idValue= (reqParams.get("parentId")==null?WebUtil.display(_MenuItemDefault.getParentId()):WebUtil.display((String)reqParams.get("parentId")));
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_MenuItemDefault.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _dataValue= (reqParams.get("data")==null?WebUtil.display(_MenuItemDefault.getData()):WebUtil.display((String)reqParams.get("data")));
    String _data2Value= (reqParams.get("data2")==null?WebUtil.display(_MenuItemDefault.getData2()):WebUtil.display((String)reqParams.get("data2")));
    String _target_typeValue= (reqParams.get("targetType")==null?WebUtil.display(_MenuItemDefault.getTargetType()):WebUtil.display((String)reqParams.get("targetType")));
    String _order_idxValue= (reqParams.get("orderIdx")==null?WebUtil.display(_MenuItemDefault.getOrderIdx()):WebUtil.display((String)reqParams.get("orderIdx")));
    String _page_idValue= (reqParams.get("pageId")==null?WebUtil.display(_MenuItemDefault.getPageId()):WebUtil.display((String)reqParams.get("pageId")));
    String _content_idValue= (reqParams.get("contentId")==null?WebUtil.display(_MenuItemDefault.getContentId()):WebUtil.display((String)reqParams.get("contentId")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_MenuItemDefault.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_MenuItemDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "menu_item_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="menuItemForm_topArea" class="formTopArea"></div>
<div id="menuItemForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="menuItemForm" method="POST" action="/menuItemAction.html" >




	<div id="menuItemForm_panelId_field">
    <div id="menuItemForm_panelId_label" class="formLabel" >Panel Id </div>
    <div id="menuItemForm_panelId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="panelId" value="<%=WebUtil.display(_panel_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="menuItemForm_parentId_field">
    <div id="menuItemForm_parentId_label" class="formLabel" >Parent Id </div>
    <div id="menuItemForm_parentId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="parentId" value="<%=WebUtil.display(_parent_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="menuItemForm_title_field">
    <div id="menuItemForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="menuItemForm_title_text" class="formFieldText" > <span></span>      
        <input id="requiredField" type="text" size="70" name="title" value="<%=WebUtil.display(_titleValue)%>"/> 
    </div>      
	</div><div class="clear"></div>




	<div id="menuItemForm_data_field">
    <div id="menuItemForm_data_label" class="formLabel" >Data </div>
    <div id="menuItemForm_data_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="data" value="<%=WebUtil.display(_dataValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="menuItemForm_data2_field">
    <div id="menuItemForm_data2_label" class="formLabel" >Data2 </div>
    <div id="menuItemForm_data2_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="data2" value="<%=WebUtil.display(_data2Value)%>"/>
    </div>      
	</div><div class="clear"></div>



	<div id="menuItemForm_targetType_field">
    <div id="menuItemForm_targetType_label" class="formRequiredLabel" >Target Type* </div>
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
    <div id="menuItemForm_orderIdx_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="orderIdx" value="<%=WebUtil.display(_order_idxValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="menuItemForm_pageId_field">
    <div id="menuItemForm_pageId_label" class="formLabel" >Page Id </div>
    <div id="menuItemForm_pageId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="pageId" value="<%=WebUtil.display(_page_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="menuItemForm_contentId_field">
    <div id="menuItemForm_contentId_label" class="formLabel" >Content Id </div>
    <div id="menuItemForm_contentId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="contentId" value="<%=WebUtil.display(_content_idValue)%>"/>
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






        <div id="menuItemForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.menuItemForm.submit();">Submit</a>
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
<div id="menuItemForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = MenuItemDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        MenuItem _MenuItem = (MenuItem) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _MenuItem.getId() %> </td>

    <td> <%= WebUtil.display(_MenuItem.getPanelId()) %></td>
    <td> <%= WebUtil.display(_MenuItem.getParentId()) %></td>
    <td> <%= WebUtil.display(_MenuItem.getTitle()) %></td>
    <td> <%= WebUtil.display(_MenuItem.getData()) %></td>
    <td> <%= WebUtil.display(_MenuItem.getData2()) %></td>
    <td> <%= WebUtil.display(_MenuItem.getTargetType()) %></td>
    <td> <%= WebUtil.display(_MenuItem.getOrderIdx()) %></td>
    <td> <%= WebUtil.display(_MenuItem.getPageId()) %></td>
    <td> <%= WebUtil.display(_MenuItem.getContentId()) %></td>
    <td> <%= WebUtil.display(_MenuItem.getHide()) %></td>
    <td> <%= WebUtil.display(_MenuItem.getTimeCreated()) %></td>


<td>
<form name="menuItemForm<%=_MenuItem.getId()%>2" method="get" action="/v_menu_item_edit2.html" >
    <a href="javascript:document.menuItemForm<%=_MenuItem.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _MenuItem.getId() %>">
</form>

</td>
<td>
<a href="/menuItemAction.html?del=true&id=<%=_MenuItem.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>