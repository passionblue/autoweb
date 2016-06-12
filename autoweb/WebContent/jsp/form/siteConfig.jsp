<%@page language="java" import="com.jtrend.session.SessionContext,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
 
<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName(), false);
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	if (siteConfig == null) siteConfig = SiteConfigDS.getDefaultSiteConfig();

	String keywords = "";
	String meta = "";
	String header = "";
	String googleTrack = "";
    String colCount = "";
    String clrSubmenuBg = "";
    String clrBorders = "";
    String contactInfo = "";
    String headerImage = "";
    	
	if (siteConfig !=null) {	
		keywords = siteConfig.getKeywords();
		meta = siteConfig.getMeta();
		googleTrack = siteConfig.getSiteTrackGoogle();
		colCount = String.valueOf(siteConfig.getHomeColCount());
		clrSubmenuBg = siteConfig.getColorSubmenuBg();
		clrBorders = siteConfig.getColorBorders();
		contactInfo = siteConfig.getContactInfo();
		headerImage = siteConfig.getHeaderImage();
	}
	
	if (keywords == null) keywords = "";
	if (meta == null) meta = "";
	if (googleTrack == null) googleTrack = "";
	if (headerImage == null) headerImage = "";
	
	StyleConfig styleConfigDefaultObj = StyleConfigDS.getInstance().getObjectByStyleKey("SiteDefault:" + site.getId());
	LinkStyleConfig linkStyleConfigDefaultObj = LinkStyleConfigDS.getInstance().getObjectByStyleKey("SiteDefaultLink:" + site.getId());
	LinkStyleConfig hMenuConfigDefaultObj = LinkStyleConfigDS.getInstance().getObjectByStyleKey("SiteDefaulHMenu:" + site.getId());
	LinkStyleConfig vMenuConfigDefaultObj = LinkStyleConfigDS.getInstance().getObjectByStyleKey("SiteDefaulVMenu:" + site.getId());
	SiteFeatureConfig siteFeatureConfig = SiteFeatureConfigDS.getInstance().getObjectBySiteId(site.getId());
	
%> 
 
<html:form action="/siteConfig"> 
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1" cellpadding="1">
	<TR bgcolor="#ffffff">
		<TD width="150"><b>Keywords :</b></TD>
		<TD><html:text size="70" property="keywords" value="<%= keywords %>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD><b>Title :</b> </TD>
		<TD><html:text size="70" property="meta" value="<%= meta %>" /><br/></TD>
		
	</TR>

	<TR bgcolor="#ffffff">
		<TD>Google <br>Tracking <br>code : </TD>
		<TD>&nbsp;<TEXTAREA NAME="track_google" COLS="60" ROWS="5" style="width: 100%"><%=googleTrack%></TEXTAREA><br></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD>width :</TD>                    
		<TD><html:text property="width" value="<%= WebUtil.display(siteConfig.getWidth()) %>"/>
		    Alignment : 
		    <select name="bodyAlign">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getShowMidColumn())%>>Center</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getShowMidColumn())%>>Left</option>
            <option value="2" <%=HtmlUtil.getOptionSelect("1", siteConfig.getShowMidColumn())%>>Right</option>
            </select>
		
		</TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD> Home Content Column Num : </TD>  
		<TD><html:text property="colCount" value="<%= colCount %>"/><html:errors property="colCount"/></TD>
	</TR>
	<TR bgcolor="#ffffff">
		<TD>Submenu Background Color :</TD>  
		<TD> 
	<html:text property="clrSubmenuBg"  value="<%= clrSubmenuBg %>"/><html:errors property="clrSubmenuBg"/></TD>
	</TR >
	<TR bgcolor="#ffffff">
		<TD>Border Color : </TD>  
		<TD>            
	<html:text property="clrBorders"  value="<%= clrBorders %>"/><html:errors property="clrBorders"/></TD>
	</TR>
	<TR bgcolor="#ffffff">
		<TD>Contact Info : </TD>  
		<TD>            
	<html:text property="contactInfo"  value="<%= contactInfo %>"/><html:errors property="contactInfo"/><br/></TD>
	</TR>
	<TR bgcolor="#ffffff">
		<TD>Site Group : </TD>  
		<TD>              
	<html:text property="siteGroup"  value="<%=WebUtil.display(siteConfig.getSiteGroup())  %>"/><html:errors property="siteGroup"/><br/></TD>
	</TR>
	<TR bgcolor="#ffffff">
		<TD>Header Image : </TD>  
		<TD>              
		<html:text size="60" property="headerImage"  value="<%= headerImage %>"/>
		H:<html:text size="5" property="headerImageHeight"  value="<%= WebUtil.display(siteConfig.getHeaderImageHeight()) %>"/>
		W:<html:text size="5" property="headerImageWidth"  value="<%= WebUtil.display(siteConfig.getHeaderImageWidth()) %>"/>
		<TEXTAREA NAME="headerAd" COLS="60" ROWS="3"><%=WebUtil.display(siteConfig.getHeaderAd())%></TEXTAREA><br>
	</TD>
	</TR>
	<TR bgcolor="#ffffff">
		<TD>Left Menu section : </TD>  
		<TD>
		   Width:              
	       <html:text property="menuWidth"  value="<%= WebUtil.display(siteConfig.getMenuWidth()) %>"/>
	       Show :
	        <select name="showMenuColumn">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getShowMenuColumn())%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getShowMenuColumn())%>>Yes</option>
            </select>

		Home Page :
	 		<select name="showHomeMenu">
			<option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getShowHomeMenu())%>>No</option>
			<option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getShowHomeMenu())%>>Yes</option>
			</select>
	       
	</TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD>Right Ad section : </TD>  
		<TD> Width:             
	       <html:text property="adWidth"  value="<%= WebUtil.display(siteConfig.getAdWidth()) %>"/>
	       Show : 
            <select name="showAdColumn">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getShowAdColumn())%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getShowAdColumn())%>>Yes</option>
            </select>

		Home Page :
	 		<select name="showHomeAd">
			<option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getShowHomeAd())%>>No</option>
			<option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getShowHomeAd())%>>Yes</option>
			</select>

	</TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD>Mid Column : </TD>  
		<TD> Width:             
	<html:text property="midColumnWidth"  value="<%= WebUtil.display(siteConfig.getMidColumnWidth()) %>"/>
		Show :
	 		<select name="showMidColumn">
			<option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getShowMidColumn())%>>No</option>
			<option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getShowMidColumn())%>>Yes</option>
			</select>

		Home Page :
	 		<select name="showHomeMid">
			<option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getShowHomeMid())%>>No</option>
			<option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getShowHomeMid())%>>Yes</option>
			</select>
	
	</TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD>Show Top Menu : </TD>  
		<TD>              
	 		<select name="showTopMenu">
			<option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getShowTopMenu())%>>No</option>
			<option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getShowTopMenu())%>>Yes</option>
			</select>
		</TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD> Menu colors : </TD>  
		<TD>              
	Front<html:text property="menuFrontColor"  value="<%= WebUtil.display(siteConfig.getMenuFrontColor()) %>"/>
	Back<html:text property="menuBackColor"  value="<%= WebUtil.display(siteConfig.getMenuBackColor()) %>"/>
	Line<html:text property="menuLineColor"  value="<%= WebUtil.display(siteConfig.getMenuLineColor()) %>"/></TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD>Menu reverse : </TD>  
		<TD>              
	 		<select name="menuReverse">
			<option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getMenuReverse())%>>No</option>
			<option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getMenuReverse())%>>Yes</option>
			</select>
		</TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD>WYSIWYG Editors  </TD>  
		<TD>
			Use For Content :             
	 		<select name="useWysiwygContent">
			<option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getUseWysiwygContent())%>>No</option>
			<option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getUseWysiwygContent())%>>Yes</option>
			</select>
			Use For Site Post :              
	 		<select name="useWysiwygPost">
			<option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getUseWysiwygPost())%>>No</option>
			<option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getUseWysiwygPost())%>>Yes</option>
			</select>
		</TD>
	</TR>


	<TR bgcolor="#ffffff">
		<TD>Display All Home (for Default): </TD>  
		<TD>              
		            <select name="displayAllHome">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getDisplayAllHome())%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getDisplayAllHome())%>>Yes</option>
            </select>
		
		</TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD>Layout Page : </TD>  
		<TD>              
		<html:text property="layoutPage"  value="<%= WebUtil.display(siteConfig.getLayoutPage()) %>"/></TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD>Icon Url : </TD>  
		<TD>              
		<html:text property="siteIconUrl"  value="<%= WebUtil.display(siteConfig.getSiteIconUrl()) %>"/></TD>
	</TR>

	
	<TR bgcolor="#ffffff">
		<TD>Underlying Home : </TD>  
		<TD>              

			<html:text property="underlyingHome"  value="<%= WebUtil.display(siteConfig.getUnderlyingHome()) %>"/>
			
			Use Dyanmic For Home :
	 		<select name="underlyingDynamicPage">
			<option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getUnderlyingDynamicPage())%>>No</option>
			<option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getUnderlyingDynamicPage())%>>Yes</option>
			</select>
			
		</TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD>ContentFoward Site : </TD>  
		<TD>              
		<html:text property="contentForwardSite"  value="<%= WebUtil.display(siteConfig.getContentForwardSite()) %>"/></TD>
	</TR>
	
	
	<TR bgcolor="#ffffff">
		<TD>Site Background Color: </TD>  
		<TD>              
		<html:text property="backgroundColor"  value="<%= WebUtil.display(siteConfig.getBackgroundColor()) %>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD>Site Background Image: </TD>  
		<TD>              
		<html:text size="70" property="backgroundImage"  value="<%= WebUtil.display(siteConfig.getBackgroundImage()) %>"/></TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD>Site Background Repeat: </TD>  
		<TD>              
	 		<select name="backgroundRepeat">
			<option value="no-repeat" <%=HtmlUtil.getOptionSelect("no-repeat", siteConfig.getBackgroundRepeat())%>>No Repeat</option>
			<option value="repeat" <%=HtmlUtil.getOptionSelect("repeat", siteConfig.getBackgroundRepeat())%>>Repeat</option>
			<option value="repeat-x" <%=HtmlUtil.getOptionSelect("repeat-x", siteConfig.getBackgroundRepeat())%>>Repeat X(H)</option>
			<option value="repeat-y" <%=HtmlUtil.getOptionSelect("repeat-y", siteConfig.getBackgroundRepeat())%>>Repeat Y(V)</option>
			</select>
		</TD>
	</TR>
	
	<TR bgcolor="#ffffff">
		<TD>Site Background Attachment: </TD>  
		<TD>              
	 		<select name="backgroundAttach">
			<option value="" <%=HtmlUtil.getOptionSelect("", siteConfig.getBackgroundAttach())%>></option>
			<option value="scroll" <%=HtmlUtil.getOptionSelect("scroll", siteConfig.getBackgroundAttach())%>>Scroll</option>
			<option value="fixed" <%=HtmlUtil.getOptionSelect("fixed", siteConfig.getBackgroundAttach())%>>Fixed</option>
			</select>
		</TD>
	</TR>



	<TR bgcolor="#ffffff">
		<TD>Site Background Position: </TD>  
		<TD>              
	 		<select name="backgroundPosition">
			<option value="" <%=HtmlUtil.getOptionSelect("", siteConfig.getBackgroundPosition())%>></option>
			<option value="top center" <%=HtmlUtil.getOptionSelect("top center", siteConfig.getBackgroundPosition())%>>Top Center</option>
			<option value="top left" <%=HtmlUtil.getOptionSelect("top left", siteConfig.getBackgroundPosition())%>>Top Left</option>
			<option value="top right" <%=HtmlUtil.getOptionSelect("top right", siteConfig.getBackgroundPosition())%>>Top Right</option>
			<option value="center center" <%=HtmlUtil.getOptionSelect("center center", siteConfig.getBackgroundPosition())%>>Center Center</option>
			<option value="center left" <%=HtmlUtil.getOptionSelect("center left", siteConfig.getBackgroundPosition())%>>Center Left</option>
			<option value="center right" <%=HtmlUtil.getOptionSelect("center right", siteConfig.getBackgroundPosition())%>>Center Right</option>
			<option value="bottom center" <%=HtmlUtil.getOptionSelect("bottom center", siteConfig.getBackgroundPosition())%>>Bottom Center</option>
			<option value="bottom left" <%=HtmlUtil.getOptionSelect("bottom left", siteConfig.getBackgroundPosition())%>>Bottom Left</option>
			<option value="bottom right" <%=HtmlUtil.getOptionSelect("bottom right", siteConfig.getBackgroundPosition())%>>Bottom Right</option>
			</select>
		</TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD>Content Background Color: </TD>  
		<TD>              
		<html:text property="contentBgColor"  value="<%= WebUtil.display(siteConfig.getContentBgColor()) %>"/></TD>
	</TR>

	
	<TR bgcolor="#ffffff">
		<TD>Absolute Position: </TD>  
		<TD>              
	 		<select name="absolutePosition">
			<option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getAbsolutePosition())%>>No</option>
			<option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getAbsolutePosition())%>>Yes</option>
			<option value="2" <%=HtmlUtil.getOptionSelect("2", siteConfig.getAbsolutePosition())%>>Full</option>
			</select>
		</TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD>Absolute Position matrix: </TD>  
		<TD>              
		Top:<html:text size="10" property="absoluteTop"  value="<%= WebUtil.display(siteConfig.getAbsoluteTop()) %>"/>
		Left:<html:text size="10" property="absoluteLeft"  value="<%= WebUtil.display(siteConfig.getAbsoluteLeft()) %>"/>
		</TD>
	</TR>


	<TR bgcolor="#ffffff">
		<TD>Functional Type: </TD>  
		<TD>              
	 		<select name="functionalType">
			<option value="0" <%=HtmlUtil.getOptionSelect("0", siteConfig.getFunctionalType())%>>Regular</option>
			<option value="1" <%=HtmlUtil.getOptionSelect("1", siteConfig.getFunctionalType())%>>Store</option>
			<option value="2" <%=HtmlUtil.getOptionSelect("1", siteConfig.getFunctionalType())%>>Blogs</option>
			<option value="3" <%=HtmlUtil.getOptionSelect("1", siteConfig.getFunctionalType())%>>News Site</option>
			<option value="4" <%=HtmlUtil.getOptionSelect("1", siteConfig.getFunctionalType())%>>Forum</option>
			</select>
		</TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD>Ec Enabled: </TD>  
		<TD>              
			<input type="checkbox" name="ecEnabled" <%=HtmlUtil.getCheckedBoxValue(siteConfig.getEcEnabled())%> />
		</TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD>FrontDisplayFeed </TD>  
		<TD>              
			<input type="checkbox" name="frontDisplayFeed" <%=HtmlUtil.getCheckedBoxValue(siteConfig.getFrontDisplayFeed())%> />
			This only applies to the site that was not registered or init to display feed contents. 
		</TD>
	</TR>

	<TR bgcolor="#ffffff">
		<TD></TD>  
		<TD>
			<b><a href="javascript:document.siteConfigForm.submit();">Submit</a> </b> 
		</TD>
	</TR>
</TABLE>	
</html:form>

<div id="button">
	Default Site Config : 
<%	if ( styleConfigDefaultObj == null) { %>
	<a href="/v_style_config_add.html?cmd=siteDefaultStyle"> Create </a>
<%	} else { %>
	<a href="/v_style_config_edit.html?id=<%=styleConfigDefaultObj.getId() %>"> Update  </a>&nbsp;|&nbsp;
	<a href="/styleConfigAction.html?del=true&id=<%=styleConfigDefaultObj.getId()%>"> Delete </a>
<%	} %>
	<br/>
	
	Default Link Style Config : 
<%	if ( linkStyleConfigDefaultObj == null) { %>
	<a href="/v_link_style_config_add.html?cmd=siteDefaultLinkStyle"> Create </a>
<%	} else { %>
	<a href="/v_link_style_config_edit.html?id=<%=linkStyleConfigDefaultObj.getId() %>"> Update </a>&nbsp;|&nbsp;
	<a href="/linkStyleConfigAction.html?del=true&id=<%=linkStyleConfigDefaultObj.getId()%>"> Delete </a>
<%	} %>
	<br/>
	
	Default H Menu Config :
<%	if ( hMenuConfigDefaultObj == null) { %>
	<a href="/v_link_style_config_add.html?cmd=siteDefaultHMenuStyle"> Create </a>
<%	} else { %>
	<a href="/v_link_style_config_edit.html?id=<%=hMenuConfigDefaultObj.getId() %>"> Update </a>&nbsp;|&nbsp;
	<a href="/linkStyleConfigAction.html?del=true&id=<%=hMenuConfigDefaultObj.getId()%>"> Delete </a>
<%	} %>
	<br/>

	Default V Menu Config :
<%	if ( vMenuConfigDefaultObj == null) { %>
	<a href="/v_link_style_config_add.html?cmd=siteDefaultVMenuStyle"> Create</a><br/>
<%	} else { %>
	<a href="/v_link_style_config_edit.html?id=<%=vMenuConfigDefaultObj.getId() %>"> Update</a>&nbsp;|&nbsp;
	<a href="/linkStyleConfigAction.html?del=true&id=<%=vMenuConfigDefaultObj.getId()%>"> Delete </a><br/>
<%	} %>


	Site Feature Config :
<%	if ( siteFeatureConfig == null) { %>
	<a href="/v_site_feature_config_add2.html?prv_returnPage=site_config"> Create</a><br/>
<%	} else { %>
	<a href="/v_site_feature_config_edit2.html?id=<%=siteFeatureConfig.getId() %>&prv_returnPage=site_config"> Update</a>&nbsp;|&nbsp;
	<a href="/siteFeatureConfigAction.html?del=true&id=<%=siteFeatureConfig.getId()%>&returnPage=site_config"> Delete </a><br/>
<%	} %>



 
</div>

