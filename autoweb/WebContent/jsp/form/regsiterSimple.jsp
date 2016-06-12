<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());

	long id = Long.parseLong(idStr);

	Panel _Panel = PanelDS.getInstance().getById(id);

	if ( _Panel == null ) {
		return;
	}

	String _column_numValue=  String.valueOf(_Panel.getColumnNum());
	String _panel_titleValue=  _Panel.getPanelTitle();
	String _panel_typeValue=  String.valueOf(_Panel.getPanelType());
	String _panel_heightValue=  String.valueOf(_Panel.getPanelHeight());
	String _hideValue=  String.valueOf(_Panel.getHide());
	String _page_onlyValue=  String.valueOf(_Panel.getPageOnly());
	String _time_createdValue=  String.valueOf(_Panel.getTimeCreated());
	String _top_spaceValue=  String.valueOf(_Panel.getTopSpace()); 
	String _bottom_spaceValue=  String.valueOf(_Panel.getBottomSpace());
	String _left_spaceValue=  String.valueOf(_Panel.getLeftSpace());
	String _right_spaceValue=  String.valueOf(_Panel.getRightSpace());
	String _style_stringValue=  _Panel.getStyleString();
	String _title_style_stringValue=  _Panel.getTitleStyleString();
	String _style_string_2Value=  _Panel.getStyleString2();
	String _alignValue=  String.valueOf(_Panel.getAlign());
	

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
	
	
	//>>END<<
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
		<TD>&nbsp;<html:text size="70" property="panelType" value="<%=WebUtil.display(_panel_typeValue)%>"/></TD>
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
		<TD>&nbsp;<html:text size="70" property="styleString2" value="<%=WebUtil.display(_style_string_2Value)%>"/></TD>
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
        </TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.panelFormEdit.submit();">Submit</a> </b>
		</TD>
	</TR>
</TABLE>	

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="pageId" value="<%=_Panel.getPageId()%>">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Panel.getId()%>">

</form>

<BR>
<BR>

<!-- ################################## -->

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
	<TR bgcolor="#ffffff">
		<TD>
			<a href="/v_style_config_add.html?prv_panel_id=<%=_Panel.getId()%>"> Create a new panel for this</a>
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

<!-- ################################## -->
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
	<TR bgcolor="#ffffff">
		<TD>
			<a href="/v_link_style_config_add.html?prv_panel_id=<%=_Panel.getId()%>"> Create a new panel for this</a>
		</TD>	
		<TD>
			<a href="javascript:document.switchLinkStyleForm.submit();">Switch Link Style To</a>
		</TD>

		<TD>
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
	<INPUT TYPE="HIDDEN" NAME="ef" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=panelStyleObjId %>">

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
