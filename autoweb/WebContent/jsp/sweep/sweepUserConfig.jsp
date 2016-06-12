<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	List list = new ArrayList();
	SweepUserConfigDS ds = SweepUserConfigDS.getInstance();    
    list = ds.getBySiteId(site.getId());

%> 

<!-- =================== PAGING =================== -->
<%
	int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), 20);;
	int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);

	PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

	String prevLinkStr = "[go prev]";
	if (pagingInfo.isHasPrev()) 
		prevLinkStr = "<a href=\"/v_sweep_user_config_home.html?listPage=" +(listPage -1)+ "\">"+ prevLinkStr + "</a>";

	String nextLinkStr = "[go next]";
	if (pagingInfo.isHasNext()) 
		nextLinkStr = "<a href=\"/v_sweep_user_config_home.html?listPage=" +(listPage +1)+ "\">"+ nextLinkStr + "</a>";

	
	String pageIndexShortcut[] = new String[pagingInfo.getTotalNumPages()];
	for (int p = 1; p <= pagingInfo.getTotalNumPages(); p++){
		if ( p == pagingInfo.getCurDisplayPage())
			pageIndexShortcut[p-1] = "<a href=\"/v_sweep_user_config_home.html?listPage=" +(p)+ "\"><b>"+ (p) + "</b></a>";
		else 
			pageIndexShortcut[p-1] = "<a href=\"/v_sweep_user_config_home.html?listPage=" +(p)+ "\">"+ (p) + "</a>";
	}

	List pageList = new ArrayList();
	for(int i = pagingInfo.getBeginIdx() ; i < pagingInfo.getEndIdx();i++){
		pageList.add(list.get(i));
	}
	list = pageList;
%>
	<%= prevLinkStr %>
<%
	for(int p = 0 ; p< pageIndexShortcut.length; p++){
%>
	<%=pageIndexShortcut[p] + (p>=pageIndexShortcut.length-1?"":"/")%>
<%
	}
%>	
	<%= nextLinkStr %>
<!-- =================== END PAGING =================== -->


<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_sweep_user_config_add2.html?prv_returnPage=sweep_user_config_home"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<TR bgcolor="#ffffff" valign="top">

    <td> ID </td>

    <td> 
	    <div id="sweepUserConfigForm_userId_label" style="font-size: normal normal bold 10px verdana;" >User Id </div>
    </td> 
    <td> 
	    <div id="sweepUserConfigForm_maxSweepAllowed_label" style="font-size: normal normal bold 10px verdana;" >Max Sweep Allowed </div>
    </td> 
	<td></td>
	<td></td>
</TR>


<%

    for(Iterator iter = list.iterator();iter.hasNext();) {
        SweepUserConfig _SweepUserConfig = (SweepUserConfig) iter.next();
%>



<TR bgcolor="#ffffff" valign="top">

    <td> <%= _SweepUserConfig.getId() %> </td>


    <td> 
	<form name="sweepUserConfigFormEditField_UserId_<%=_SweepUserConfig.getId()%>" method="get" action="/sweepUserConfigAction.html" >


		<div id="sweepUserConfigForm_userId_field">
	    <div id="sweepUserConfigForm_userId_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="userId" value="<%=WebUtil.display(_SweepUserConfig.getUserId())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepUserConfigFormEditField_UserId_<%=_SweepUserConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepUserConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_user_config_home">
	</form>
    
    
    </td>

    <td> 
	<form name="sweepUserConfigFormEditField_MaxSweepAllowed_<%=_SweepUserConfig.getId()%>" method="get" action="/sweepUserConfigAction.html" >


		<div id="sweepUserConfigForm_maxSweepAllowed_field">
	    <div id="sweepUserConfigForm_maxSweepAllowed_text" class="formFieldText" >       
	        <input id="field" type="text" size="20" name="maxSweepAllowed" value="<%=WebUtil.display(_SweepUserConfig.getMaxSweepAllowed())%>"/> <span></span>
	    </div>      
		</div><div class="clear"></div>

		<br>
            <a id="formSubmit" href="javascript:document.sweepUserConfigFormEditField_MaxSweepAllowed_<%=_SweepUserConfig.getId()%>.submit();">Submit</a>

	<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
	<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SweepUserConfig.getId()%>">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_user_config_home">
	</form>
    
    
    </td>

    <td>
    <form name="sweepUserConfigForm<%=_SweepUserConfig.getId()%>" method="get" action="/v_sweep_user_config_edit.html" >
        <a href="javascript:document.sweepUserConfigForm<%=_SweepUserConfig.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepUserConfig.getId() %>">
    </form>
    <form name="sweepUserConfigForm<%=_SweepUserConfig.getId()%>2" method="get" action="/v_sweep_user_config_edit2.html" >
        <a href="javascript:document.sweepUserConfigForm<%=_SweepUserConfig.getId()%>2.submit();">Edit2</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _SweepUserConfig.getId() %>">
    </form>
    </td>

    <td>
    <a href="/sweepUserConfigAction.html?del=true&id=<%=_SweepUserConfig.getId()%>"> Delete </a>
    </td>
</TR>

<%
    }
%>
</TABLE>