<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.util.web.*,com.autosite.db.*,com.autosite.content.*,java.util.*, com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String _column_numValue= WebUtil.display((String)reqParams.get("columnNum"));;
	String _panel_titleValue= WebUtil.display((String)reqParams.get("panelTitle"));;
	String _panel_typeValue= WebUtil.display((String)reqParams.get("panelType"));;
	String _page_idValue= WebUtil.display((String)reqParams.get("pageId"));;
	String _page_onlyValue= WebUtil.display((String)reqParams.get("pageOnly"));;
    String _page_only_groupValue= WebUtil.display((String)reqParams.get("pageOnlyGroup"));;
	String _panel_heightValue= WebUtil.display((String)reqParams.get("panelHeight"));;
	String _hideValue= WebUtil.display((String)reqParams.get("hide"));;
	String _time_createdValue= WebUtil.display((String)reqParams.get("timeCreated"));;
	String _top_spaceValue= WebUtil.display((String)reqParams.get("topSpace"));;
	String _bottom_spaceValue= WebUtil.display((String)reqParams.get("bottomSpace"));;
	String _left_spaceValue= WebUtil.display((String)reqParams.get("leftSpace"));;
	String _right_spaceValue= WebUtil.display((String)reqParams.get("rightSpace"));;
	String _style_stringValue= WebUtil.display((String)reqParams.get("styleString"));;
	String _title_style_stringValue= WebUtil.display((String)reqParams.get("titleStyleString"));;
	String _style_string_2Value= WebUtil.display((String)reqParams.get("styleString2"));;
	String _style_default_codealue= WebUtil.display((String)reqParams.get("styleDefaultCode"));;
	String _alignValue= WebUtil.display((String)reqParams.get("align"));;
    
	String _column_countValue= (WebUtil.display((String)reqParams.get("columnCount")));
    String _page_display_summaryValue= (WebUtil.display((String)reqParams.get("pageDisplaySummary")));

    String _show_in_printValue= WebUtil.display((String)reqParams.get("showInPrint"));
    String _show_only_printValue= WebUtil.display((String)reqParams.get("showOnlyPrint"));
    String _admin_onlyValue= WebUtil.display((String)reqParams.get("adminOnly"));
	String _feed_idValue= WebUtil.display((String)reqParams.get("feedId"));;
    String _option1Value=  WebUtil.display((String)reqParams.get("option1"));
    String _option2Value=  WebUtil.display((String)reqParams.get("option2"));
    String _option3Value=  WebUtil.display((String)reqParams.get("option3"));



	String _wpId = WebProcManager.registerWebProcess();

	//>>START<<
	// Page view check. 
//	String pageName = (String) session.getAttribute("k_page_name");
//	Page dynPage = PageDS.getInstance().getBySiteIdPageName(site.getId(), pageName);
	Page dynPage = DynPageUtil.getCurrentPage(request, site.getId());
	long pageId  = (dynPage!=null?dynPage.getId():0);
	
	if (_page_idValue == null || _page_idValue.length() == 0){
		_page_idValue = ""+pageId;
	}
	
	if (_top_spaceValue == null || _top_spaceValue.length() == 0) {
		_top_spaceValue = "15";
	}

	//>>END<<

%> 

<br>
<form name="panelForm" method="post" action="/panelAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

			<TR bgcolor="#ffffff">
		<TD align="right" ><b>Column Num</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="columnNum" value="<%=WebUtil.display(_column_numValue)%>"/></TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Panel Title</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="panelTitle" value="<%=WebUtil.display(_panel_titleValue)%>"/></TD>
	</TR>


	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Panel Type</b> &nbsp;</TD>
		<TD>&nbsp;<!-- select name="panelType-old">
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _panel_typeValue)%>>Default(Contents)</option>
            <option value="2" <%=HtmlUtil.getOptionSelect("2", _panel_typeValue)%>>Menu</option>
            <option value="3" <%=HtmlUtil.getOptionSelect("3", _panel_typeValue)%>>Header</option>
            <option value="4" <%=HtmlUtil.getOptionSelect("4", _panel_typeValue)%>>Header Menu</option>
            <option value="5" <%=HtmlUtil.getOptionSelect("5", _panel_typeValue)%>>Divider</option>
            <option value="6" <%=HtmlUtil.getOptionSelect("6", _panel_typeValue)%>>Footer Menu</option>
            <option value="10" <%=HtmlUtil.getOptionSelect("10", _panel_typeValue)%>>Content Panel</option>
            <option value="11" <%=HtmlUtil.getOptionSelect("11", _panel_typeValue)%>>Content Mid Panel</option>
            <option value="31" <%=HtmlUtil.getOptionSelect("31", _panel_typeValue)%>>Page Category </option>
            <option value="32" <%=HtmlUtil.getOptionSelect("32", _panel_typeValue)%>>Content Links </option>
            <option value="99" <%=HtmlUtil.getOptionSelect("99", _panel_typeValue)%>>Free</option>
            </select-->
	 		<select name="panelType">
<%
	List dropMenus = DropMenuUtil.getDropMenus("PanelType");
	for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
					<option value="<%=it.getValue() %>" ><%=it.getDisplay() %></option>
<%} %>
			</select>
            
            &nbsp;Panel SubType &nbsp;<select name="panelSubType">
            <option value="0" >0</option>
            <option value="1" >1</option>
            <option value="2" >2</option>
            <option value="2" >3</option>
            <option value="2" >4</option>
            </select>
        </TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Panel Height</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="panelHeight" value="<%=WebUtil.display(_panel_heightValue)%>"/></TD>
	</TR>
	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Top Space</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="topSpace" value="<%=WebUtil.display(_top_spaceValue)%>"/></TD>
	</TR>
	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Bottom Space</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="bottomSpace" value="<%=WebUtil.display(_bottom_spaceValue)%>"/></TD>
	</TR>
	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Left Space</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="leftSpace" value="<%=WebUtil.display(_left_spaceValue)%>"/></TD>
	</TR>
	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Right Space</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="rightSpace" value="<%=WebUtil.display(_right_spaceValue)%>"/></TD>
	</TR>
	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Style String</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="styleString" value="<%=WebUtil.display(_style_stringValue)%>"/></TD>
	</TR>
	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Title Style String</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="titleStyleString" value="<%=WebUtil.display(_title_style_stringValue)%>"/></TD>
	</TR>
	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Style String 2</b> &nbsp;</TD>
		<TD>
		&nbsp;<select name="styleString2">
            <option value="" >- Please Select-</option>
<%
	List styleConfigs = StyleConfigDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = styleConfigs.iterator(); iter.hasNext();){
		StyleConfig styleCfg = (StyleConfig) iter.next();
%>		
            <option value="<%=styleCfg.getStyleKey()%>" ><%=styleCfg.getStyleKey() + "(" + styleCfg.getId() + ")" %></option>
<%	} %>            
            </select>
		</TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Style Default Code</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="styleDefaultCode" value="<%=WebUtil.display(_style_default_codealue)%>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Align</b> &nbsp;</TD>
		<TD>&nbsp;<select name="align">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _alignValue)%>>Vertical</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _alignValue)%>>Horizontal</option>
            </select>
        </TD>

	</TR>
	
	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Column Count</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="columnCount" value="<%=WebUtil.display(_column_countValue)%>"/></TD>
	    </TR>

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Hide</b> &nbsp;</TD>
		<TD>&nbsp;<select name="hide">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _hideValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _hideValue)%>>Yes</option>
            </select>
        </TD>
	</TR> 

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Page View Only</b> &nbsp;</TD>
		<TD>&nbsp;<select name="pageOnly">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _page_onlyValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _page_onlyValue)%>>Yes</option>

            </select>
			&nbsp;<select name="pageId">
            <option value="" >-- Please Select --</option>
            
<%
	//>>START<<
	List dynPages = PageDS.getInstance().getBySiteId(site.getId());
	long curId = WebParamUtil.getLongValue(_page_idValue);
	for(Iterator iter = dynPages.iterator(); iter.hasNext();){
		Page p = (Page) iter.next();
%>

            <option value="<%=p.getId() %>" <%=HtmlUtil.getOptionSelect(p.getId(), curId)%>><%= p.getPageMenuTitle() + "(" + p.getId() + ")"%></option>

<%
	}
	//>>END<<
%>
            </select>
			Page Only Group
			<select name="pageOnlyGroup">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _page_only_groupValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _page_only_groupValue)%>>Yes</option>

            </select>

        </TD>
	</TR> 

	<TR bgcolor="#ffffff">
        <TD align="right" ><b>Show In Print</b> &nbsp;</TD>
        <TD>&nbsp;<select name="showInPrint">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _show_in_printValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _show_in_printValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Show Only Print</b> &nbsp;</TD>
        <TD>&nbsp;<select name="showOnlyPrint">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _show_only_printValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _show_only_printValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
    <TR bgcolor="#ffffff">
        <TD align="right" ><b>Admin Only</b> &nbsp;</TD>
        <TD>&nbsp;<select name="adminOnly">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _admin_onlyValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _admin_onlyValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
    
	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Option 1</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="option1" value="<%=WebUtil.display(_option1Value)%>"/></TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Option 2</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="option2" value="<%=WebUtil.display(_option2Value)%>"/></TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Option 3</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="option3" value="<%=WebUtil.display(_option3Value)%>"/></TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.panelForm.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="feedId" value="<%=WebUtil.display(_feed_idValue) %>">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
	if (false) return;

	List list = PanelDS.getInstance().getBySiteId(site.getId());
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		Panel _Panel = (Panel) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

	<td> <%= _Panel.getId() %> </td>

    <td> <%= WebUtil.display(_Panel.getColumnNum()) %></td>
    <td> <%= WebUtil.display(_Panel.getPanelTitle()) %></td>
    <td> <%= WebUtil.display(_Panel.getPanelType()) %></td>
    <td> <%= WebUtil.display(_Panel.getPanelHeight()) %></td>
    <td> <%= WebUtil.display(_Panel.getHide()) %></td>
    <td> <%= WebUtil.display(_Panel.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_Panel.getTopSpace()) %></td>
    <td> <%= WebUtil.display(_Panel.getBottomSpace()) %></td>
    <td> <%= WebUtil.display(_Panel.getLeftSpace()) %></td>
    <td> <%= WebUtil.display(_Panel.getRightSpace()) %></td>
    <td> <%= WebUtil.display(_Panel.getTitleStyleString()) %></td>
    <td> <%= WebUtil.display(_Panel.getStyleString2()) %></td>
    <td> <%= WebUtil.display(_Panel.getStyleString()) %></td>
    <td> <%= WebUtil.display(_Panel.getAlign()) %></td>


<td>
<form name="panelForm<%=_Panel.getId()%>" method="post" action="/v_panel_edit.html" >
	<a href="javascript:document.panelForm<%=_Panel.getId()%>.submit();">Edit</a>			
	<INPUT TYPE="HIDDEN" NAME="id" value="<%= _Panel.getId() %>">
</form>
</td>
<td>
<a href="/panelAction.html?del=true&id=<%=_Panel.getId()%>"> Delete </a>
</td>
</TR>

<%
	}
%>
</TABLE>