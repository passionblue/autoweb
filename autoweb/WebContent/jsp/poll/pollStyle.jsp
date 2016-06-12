<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	String listAllByAdmin = request.getParameter("listAllByAdmin");
	
	boolean showListAllByAdmin = false;
	if ( sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)){
		showListAllByAdmin = true;
	}

	List list = new ArrayList();
	PollStyleDS ds = PollStyleDS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 

<!-- =================== PAGING =================== -->
<%
	String uri = (String) session.getAttribute("k_request_uri");

	int defaultNumDisplay = 10;
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
	String optionQueryStr = "";
	if (showListAllByAdmin) optionQueryStr += "&listAllByAdmin=true";

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	list = PagingUtil.getPagedList(pagingInfo, list);
	String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
	
%>	
<%= html %>

<!-- =================== END PAGING =================== -->


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_poll_style_add2.html?prv_returnPage=poll_style_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="pollStyleForm_userId_label" style="font-size: normal normal bold 10px verdana;" >User Id </div>
    </td> 
    <td> 
	    <div id="pollStyleForm_pollId_label" style="font-size: normal normal bold 10px verdana;" >Poll Id </div>
    </td> 
    <td> 
	    <div id="pollStyleForm_color_label" style="font-size: normal normal bold 10px verdana;" >Color </div>
    </td> 
    <td> 
	    <div id="pollStyleForm_background_label" style="font-size: normal normal bold 10px verdana;" >Background </div>
    </td> 
    <td> 
	    <div id="pollStyleForm_border_label" style="font-size: normal normal bold 10px verdana;" >Border </div>
    </td> 
    <td> 
	    <div id="pollStyleForm_font_label" style="font-size: normal normal bold 10px verdana;" >Font </div>
    </td> 
    <td> 
	    <div id="pollStyleForm_margin_label" style="font-size: normal normal bold 10px verdana;" >Margin </div>
    </td> 
    <td> 
	    <div id="pollStyleForm_padding_label" style="font-size: normal normal bold 10px verdana;" >Padding </div>
    </td> 
    <td> 
	    <div id="pollStyleForm_floating_label" style="font-size: normal normal bold 10px verdana;" >Floating </div>
    </td> 
    <td> 
	    <div id="pollStyleForm_textAlign_label" style="font-size: normal normal bold 10px verdana;" >Text Align </div>
    </td> 
    <td> 
	    <div id="pollStyleForm_textIndent_label" style="font-size: normal normal bold 10px verdana;" >Text Indent </div>
    </td> 
    <td> 
	    <div id="pollStyleForm_height_label" style="font-size: normal normal bold 10px verdana;" >Height </div>
    </td> 
    <td> 
	    <div id="pollStyleForm_width_label" style="font-size: normal normal bold 10px verdana;" >Width </div>
    </td> 
    <td> 
	    <div id="pollStyleForm_extra_label" style="font-size: normal normal bold 10px verdana;" >Extra </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        PollStyle _PollStyle = (PollStyle) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _PollStyle.getId() %> </td>


    <td> 
	<form name="pollStyleFormEditField_UserId_<%=_PollStyle.getId()%>" method="get" action="/pollStyleAction.html" >


		<div id="pollStyleForm_userId_field">
	    <div id="pollStyleForm_userId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="userId" value="<%=WebUtil.display(_PollStyle.getUserId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollStyleFormEditField_UserId_<%=_PollStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_style_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollStyleFormEditField_PollId_<%=_PollStyle.getId()%>" method="get" action="/pollStyleAction.html" >


		<div id="pollStyleForm_pollId_field">
	    <div id="pollStyleForm_pollId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="pollId" value="<%=WebUtil.display(_PollStyle.getPollId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollStyleFormEditField_PollId_<%=_PollStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_style_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollStyleFormEditField_Color_<%=_PollStyle.getId()%>" method="get" action="/pollStyleAction.html" >


		<div id="pollStyleForm_color_field">
	    <div id="pollStyleForm_color_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="color" value="<%=WebUtil.display(_PollStyle.getColor())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollStyleFormEditField_Color_<%=_PollStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_style_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollStyleFormEditField_Background_<%=_PollStyle.getId()%>" method="get" action="/pollStyleAction.html" >


		<div id="pollStyleForm_background_field">
	    <div id="pollStyleForm_background_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="background" value="<%=WebUtil.display(_PollStyle.getBackground())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollStyleFormEditField_Background_<%=_PollStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_style_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollStyleFormEditField_Border_<%=_PollStyle.getId()%>" method="get" action="/pollStyleAction.html" >


		<div id="pollStyleForm_border_field">
	    <div id="pollStyleForm_border_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="border" value="<%=WebUtil.display(_PollStyle.getBorder())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollStyleFormEditField_Border_<%=_PollStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_style_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollStyleFormEditField_Font_<%=_PollStyle.getId()%>" method="get" action="/pollStyleAction.html" >


		<div id="pollStyleForm_font_field">
	    <div id="pollStyleForm_font_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="font" value="<%=WebUtil.display(_PollStyle.getFont())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollStyleFormEditField_Font_<%=_PollStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_style_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollStyleFormEditField_Margin_<%=_PollStyle.getId()%>" method="get" action="/pollStyleAction.html" >


		<div id="pollStyleForm_margin_field">
	    <div id="pollStyleForm_margin_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="margin" value="<%=WebUtil.display(_PollStyle.getMargin())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollStyleFormEditField_Margin_<%=_PollStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_style_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollStyleFormEditField_Padding_<%=_PollStyle.getId()%>" method="get" action="/pollStyleAction.html" >


		<div id="pollStyleForm_padding_field">
	    <div id="pollStyleForm_padding_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="padding" value="<%=WebUtil.display(_PollStyle.getPadding())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollStyleFormEditField_Padding_<%=_PollStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_style_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollStyleFormEditField_Floating_<%=_PollStyle.getId()%>" method="get" action="/pollStyleAction.html" >


		<div id="pollStyleForm_floating_field">
	    <div id="pollStyleForm_floating_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="floating" value="<%=WebUtil.display(_PollStyle.getFloating())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollStyleFormEditField_Floating_<%=_PollStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_style_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollStyleFormEditField_TextAlign_<%=_PollStyle.getId()%>" method="get" action="/pollStyleAction.html" >


		<div id="pollStyleForm_textAlign_field">
	    <div id="pollStyleForm_textAlign_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="textAlign" value="<%=WebUtil.display(_PollStyle.getTextAlign())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollStyleFormEditField_TextAlign_<%=_PollStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_style_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollStyleFormEditField_TextIndent_<%=_PollStyle.getId()%>" method="get" action="/pollStyleAction.html" >


		<div id="pollStyleForm_textIndent_field">
	    <div id="pollStyleForm_textIndent_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="textIndent" value="<%=WebUtil.display(_PollStyle.getTextIndent())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollStyleFormEditField_TextIndent_<%=_PollStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_style_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollStyleFormEditField_Height_<%=_PollStyle.getId()%>" method="get" action="/pollStyleAction.html" >


		<div id="pollStyleForm_height_field">
	    <div id="pollStyleForm_height_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="height" value="<%=WebUtil.display(_PollStyle.getHeight())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollStyleFormEditField_Height_<%=_PollStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_style_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollStyleFormEditField_Width_<%=_PollStyle.getId()%>" method="get" action="/pollStyleAction.html" >


		<div id="pollStyleForm_width_field">
	    <div id="pollStyleForm_width_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="width" value="<%=WebUtil.display(_PollStyle.getWidth())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollStyleFormEditField_Width_<%=_PollStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_style_home">
	</form>
    
    
    </td>

    <td> 
	<form name="pollStyleFormEditField_Extra_<%=_PollStyle.getId()%>" method="get" action="/pollStyleAction.html" >


		<div id="pollStyleForm_extra_field">
	    <div id="pollStyleForm_extra_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="extra" value="<%=WebUtil.display(_PollStyle.getExtra())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.pollStyleFormEditField_Extra_<%=_PollStyle.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_PollStyle.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="poll_style_home">
	</form>
    
    
    </td>



    <td>
    <form name="pollStyleForm<%=_PollStyle.getId()%>" method="get" action="/v_poll_style_edit.html" >
        <a href="javascript:document.pollStyleForm<%=_PollStyle.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollStyle.getId() %>">
    </form>
    <form name="pollStyleForm<%=_PollStyle.getId()%>2" method="get" action="/v_poll_style_edit2.html" >
        <a href="javascript:document.pollStyleForm<%=_PollStyle.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _PollStyle.getId() %>">
    </form>
    </td>

    <td>
    <a href="/pollStyleAction.html?del=true&id=<%=_PollStyle.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>