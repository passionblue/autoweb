<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	long id = Long.parseLong(idStr);

	Panel _Panel = PanelDS.getInstance().getById(id);

	if ( _Panel == null ) {
		id = WebParamUtil.getLongValue((String)reqParams.get("id"));
		_Panel = PanelDS.getInstance().getById(id);
		if ( _Panel == null ) {
			return;
		}
	}

	String _column_numValue=  String.valueOf(_Panel.getColumnNum());
	String _panel_titleValue=  _Panel.getPanelTitle();
	String _panel_typeValue=  String.valueOf(_Panel.getPanelType());
	String _panel_subTypeValue=  String.valueOf(_Panel.getPanelSubType());
	String _panel_heightValue=  String.valueOf(_Panel.getPanelHeight());
	String _hideValue=  String.valueOf(_Panel.getHide());
	String _page_onlyValue=  String.valueOf(_Panel.getPageOnly());
	String _page_only_groupValue=  String.valueOf(_Panel.getPageOnlyGroup());
	String _time_createdValue=  String.valueOf(_Panel.getTimeCreated());
	String _top_spaceValue=  String.valueOf(_Panel.getTopSpace()); 
	String _bottom_spaceValue=  String.valueOf(_Panel.getBottomSpace());
	String _left_spaceValue=  String.valueOf(_Panel.getLeftSpace());
	String _right_spaceValue=  String.valueOf(_Panel.getRightSpace());
	String _style_stringValue=  _Panel.getStyleString();
	String _title_style_stringValue=  _Panel.getTitleStyleString();
	String _style_string_2Value=  _Panel.getStyleString2();
	String _style_default_codealue = _Panel.getStyleDefaultCode();
	String _alignValue=  String.valueOf(_Panel.getAlign());
	String _column_countValue = String.valueOf(_Panel.getColumnCount());
    String _show_in_printValue=  WebUtil.display(_Panel.getShowInPrint());
    String _show_only_printValue=  WebUtil.display(_Panel.getShowOnlyPrint());	
	String _feed_idValue=  String.valueOf(_Panel.getFeedId());
	String _admin_onlyValue = String.valueOf(_Panel.getAdminOnly());
    String _option1Value=  WebUtil.display(_Panel.getOption1());
    String _option2Value=  WebUtil.display(_Panel.getOption2());
    String _option3Value=  WebUtil.display(_Panel.getOption3());

	//>>START<<

	List panelStyles = PanelStyleDS.getInstance().getByPanelId(_Panel.getId());
	String panelStyleId = null;
	String panelStyleObjId = null;
	StyleConfig styleConfig = null;
	StyleConfigDS styleConfigDS = StyleConfigDS.getInstance();
	if (panelStyles.size() > 0) {
		PanelStyle panelStyle = (PanelStyle) panelStyles.get(0);
		panelStyleId = ""+panelStyle.getStyleId();
		panelStyleObjId = ""+panelStyle.getId();
		styleConfig = (StyleConfig) styleConfigDS.getById(panelStyleId);
	}


	List panelStylesForSite = PanelStyleDS.getInstance().getBySiteId(site.getId());
	List styleConfigsForSite = 	styleConfigDS.getBySiteId(site.getId());
	
	//==
	
	
	System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	PanelLinkStyle panelLinkStyle = PanelUtil.getPanelLinkStyleObject(_Panel.getId());
	LinkStyleConfig linkStyleConfig = PanelUtil.getPanelLinkStyleConfig(_Panel.getId());	
	System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX2");

	String panelLinkStyleId = null;
	String panelLinkStyleObjId = null;
	if ( panelLinkStyle != null){
		panelLinkStyleId = panelLinkStyle.getStyleId()+"";
		panelLinkStyleObjId = panelLinkStyle.getId()+"";
	}
	
	List panelLinkStylesForSite = PanelLinkStyleDS.getInstance().getBySiteId(site.getId());
	System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX3 " + linkStyleConfig);
	List linkStyleConfigsForSite = 	LinkStyleConfigDS.getInstance().getBySiteId(site.getId());
	System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX4 " + linkStyleConfigsForSite.size());
	
	StyleSet styleSet = StyleSetDS.getInstance().getBySiteIdToName(site.getId(), _Panel.getStyleDefaultCode());
	
	//>>END<<
	
	String returnPage = "panel_edit";
	if ( reqParams.containsKey("returnPage")){ 
		returnPage = (String) reqParams.get("returnPage");    
	}
	
%> 

<br>
<form name="panelFormEdit" method="post" action="/panelAction.html" >
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
	List dropMenusType = DropMenuUtil.getDropMenus("PanelType");
	for(Iterator iterItems = dropMenusType.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
					<option value="<%=it.getValue() %>"  <%=HtmlUtil.getOptionSelect(it.getValue(), _panel_typeValue)%>><%=it.getDisplay() %></option>
<%} %>
			</select>

            &nbsp;<select name="panelSubType">
<%

	String panelMenuLoadStr = "PanelSubTypeFor";

	if ( _Panel.getPanelType() == 2 || _Panel.getPanelType() == 4){
	    panelMenuLoadStr += _Panel.getPanelType();
	}

	List dropMenus = DropMenuUtil.getDropMenus( panelMenuLoadStr );
	for(Iterator iterItems = dropMenus.iterator(); iterItems.hasNext();){
		DropMenuItem it = (DropMenuItem) iterItems.next();
%>				
		<option value="<%=it.getValue() %>" <%=HtmlUtil.getOptionSelect(it.getValue(), _Panel.getPanelSubType())%>><%=it.getDisplay() %></option>
<%} %>
		<option value="3"  >new</option> 


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
		<TD> &nbsp;<select name="styleString2">
            <option value="" <%=HtmlUtil.getOptionSelect(_style_string_2Value, "")%>>- Please Select-</option>
<%
	List styleConfigs = StyleConfigDS.getInstance().getBySiteId(site.getId());
	StyleConfig associatedStyle = StyleConfigDS.getInstance().getObjectByStyleKey(_style_string_2Value);
	for(Iterator iter = styleConfigs.iterator(); iter.hasNext();){
		StyleConfig styleCfg = (StyleConfig) iter.next();
%>		
            <option value="<%=styleCfg.getStyleKey()%>" <%=HtmlUtil.getOptionSelect(_style_string_2Value, styleCfg.getStyleKey())%> ><%=styleCfg.getStyleKey() + "(" + styleCfg.getId() + ")" %></option>
<%	} %>            
            </select>
<%	if (associatedStyle != null) {%>
		<a href="/v_style_config_edit.html?id=<%=associatedStyle.getId() %>"> Edit Style</a>
<% 	} %>		
		</TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD align="right" ><b>Style Default Code</b> &nbsp;</TD>
		<TD>&nbsp;<html:text size="70" property="styleDefaultCode" value="<%=WebUtil.display(_style_default_codealue)%>"/>
		
<% 	if (styleSet != null ) {%>
		<a href="/v_style_set_form.html?cmd=edit&id=<%=styleSet.getId()%>&prv_returnPage=panel_edit,id=<%=_Panel.getId()%>">Edit</a>		
<%	} %>		
		</TD>
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
		<TD align="right" ><b>Page Only View</b> &nbsp;</TD>
		<TD>&nbsp;<select name="pageOnly">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _page_onlyValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _page_onlyValue)%>>Yes</option>
            </select>
			&nbsp;<select name="pageId">
            <option value="" >-- Please Select --</option>
            
<%
	//>>START<<
	List dynPages = PageDS.getInstance().getBySiteId(site.getId());
	long curId = _Panel.getPageId();
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
			<b><a href="javascript:document.panelFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="fwdTo" value="/v_panel_edit.html?id=<%=_Panel.getId()%>">
<INPUT TYPE="HIDDEN" NAME="feedId" value="<%=_feed_idValue %>">
<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Panel.getId()%>">

</form>

<BR>
<BR>

<!-- ################################## -->

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
	<TR bgcolor="#ffffff">
		<TD width="40">
			<a href="/v_style_config_add.html?prv_panel_id=<%=_Panel.getId()%>"> Create New</a>
		</TD>
		<TD>
		<a href="javascript:document.switchStyleForm.submit();">Switch Style To</a>
<%
	if (panelStyleId == null) {
		String _wpId = WebProcManager.registerWebProcess();
%>
<form name="switchStyleForm" method="get" action="/panelStyleAction.html">

	<select name="styleId">
	
    <option value="0" selected> Select Style</option>
    
    
<%
	for(Iterator iter = styleConfigsForSite.iterator();iter.hasNext();){
		StyleConfig styleCfg = (StyleConfig) iter.next();
%>	
            <option value="<%=styleCfg.getId()%>" <%=HtmlUtil.getOptionSelect(panelStyleId, styleCfg.getId())%>> <%= styleCfg.getStyleKey() %></option>
<%	
	}
%>
    </select>
	<INPUT TYPE="HIDDEN" NAME="fwdTo" value="/v_panel_edit.html?id=<%=_Panel.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="panelId" value="<%=_Panel.getId() %>">
	<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
<%
	} else {
%>
<form name="switchStyleForm" method="get" action="/panelStyleAction.html">

	<select name="styleId">
    
<%
	for(Iterator iter = styleConfigsForSite.iterator();iter.hasNext();){
		StyleConfig styleCfg = (StyleConfig) iter.next();
%>	
            <option value="<%=styleCfg.getId()%>" <%=HtmlUtil.getOptionSelect(panelStyleId, styleCfg.getId())%>> <%= styleCfg.getStyleKey() %></option>
<%	
	}
%>
    </select>
	<INPUT TYPE="HIDDEN" NAME="fwdTo" value="/v_panel_edit.html?id=<%=_Panel.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="ef" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=panelStyleObjId %>">
</form>
<%
	}
%>
	</TD>


<%
	if (panelStyleId != null) {
%>

<TD><a href="/v_style_config_edit.html?id=<%=panelStyleId%>"> Update the current Style </a></TD>
<TD><a href="/panelStyleAction.html?del=true&id=<%=panelStyleObjId%>"> Drop the current Style <%= styleConfig.getStyleKey() %></a></TD>
<%
	}
%>
</TR>
</TABLE>
<BR/>

<!-- ########################################## ################################## ################################## -->

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
	<TR bgcolor="#ffffff">
		<TD width="40">
			<a href="/v_link_style_config_add.html?prv_panel_id=<%=_Panel.getId()%>"> Create New</a>
		</TD>	
		<TD>
			<a href="javascript:document.switchLinkStyleForm.submit();">Switch Link Style To</a>
<%
	if (linkStyleConfig == null) {
		String _wpId = WebProcManager.registerWebProcess();
%>
<form name="switchLinkStyleForm" method="get" action="/panelLinkStyleAction.html">

	<select name="styleId">
	
    <option value="0" selected> Select Style</option>
    
    
<%
	for(Iterator iter = linkStyleConfigsForSite.iterator();iter.hasNext();){
		LinkStyleConfig linkStyleCfg = (LinkStyleConfig) iter.next();
%>	
            <option value="<%=linkStyleCfg.getId()%>" <%=HtmlUtil.getOptionSelect(panelLinkStyleId, linkStyleCfg.getId())%>> <%= linkStyleCfg.getStyleKey() %></option>
<%	
	}
%>
    </select>
	<INPUT TYPE="HIDDEN" NAME="fwdTo" value="/v_panel_edit.html?id=<%=_Panel.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="panelId" value="<%=_Panel.getId() %>">
	<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

</form>


<%
	} else {
%>

<form name="switchLinkStyleForm" method="get" action="/panelLinkStyleAction.html">

	<select name="styleId">
    
<%
	for(Iterator iter = linkStyleConfigsForSite.iterator();iter.hasNext();){
		LinkStyleConfig linkStyleCfg = (LinkStyleConfig) iter.next();
%>	
            <option value="<%=linkStyleCfg.getId()%>" <%=HtmlUtil.getOptionSelect(panelLinkStyleId, linkStyleCfg.getId())%>> <%= linkStyleCfg.getStyleKey() %></option>
<%	
	}
%>
    </select>
	<INPUT TYPE="HIDDEN" NAME="fwdTo" value="/v_panel_edit.html?id=<%=_Panel.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="ef" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=panelLinkStyleObjId %>">

</form>

<%
	}
%>
	</TD>

<%
	if (linkStyleConfig != null) {
%>
<TD><a href="/v_link_style_config_edit.html?id=<%=panelLinkStyleId%>"> Update the current Style </a></TD>
<TD><a href="/panelLinkStyleAction.html?del=true&id=<%=panelLinkStyleObjId%>"> Drop the current Style <%= linkStyleConfig.getStyleKey() %></a></TD>

<%
	}
%>
</TR>
</TABLE>
