<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
	SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	long blogMainId = WebParamUtil.getLongValue(request.getParameter("blogMainId"));	

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    BlogMain _BlogMainDefault = new BlogMain();// BlogMainDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_BlogMainDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _blog_nameValue= (reqParams.get("blogName")==null?WebUtil.display(_BlogMainDefault.getBlogName()):WebUtil.display((String)reqParams.get("blogName")));
    String _blog_titleValue= (reqParams.get("blogTitle")==null?WebUtil.display(_BlogMainDefault.getBlogTitle()):WebUtil.display((String)reqParams.get("blogTitle")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_BlogMainDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _main_design_selectValue= (reqParams.get("mainDesignSelect")==null?WebUtil.display(_BlogMainDefault.getMainDesignSelect()):WebUtil.display((String)reqParams.get("mainDesignSelect")));
    String _use_custom_designValue= (reqParams.get("useCustomDesign")==null?WebUtil.display(_BlogMainDefault.getUseCustomDesign()):WebUtil.display((String)reqParams.get("useCustomDesign")));
    String _custom_design_fileValue= (reqParams.get("customDesignFile")==null?WebUtil.display(_BlogMainDefault.getCustomDesignFile()):WebUtil.display((String)reqParams.get("customDesignFile")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="blogMainForm" method="post" action="/blogMainAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>User Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="userId" value="<%=WebUtil.display(_user_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Blog Name</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="blogName" value="<%=WebUtil.display(_blog_nameValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Blog Title</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="blogTitle" value="<%=WebUtil.display(_blog_titleValue)%>"/></TD>
	    </TR>
	            	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Main Design Select</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="mainDesignSelect" value="<%=WebUtil.display(_main_design_selectValue)%>"/></TD>
	    </TR>
	                <TR bgcolor="#ffffff">
        <TD align="right" ><b>Use Custom Design</b> &nbsp;</TD>
        <TD>&nbsp;<select name="useCustomDesign">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _use_custom_designValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _use_custom_designValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
        	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Custom Design File</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="customDesignFile" value="<%=WebUtil.display(_custom_design_fileValue)%>"/></TD>
	    </TR>
	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.blogMainForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = BlogMainDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        BlogMain _BlogMain = (BlogMain) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _BlogMain.getId() %> </td>

    <td> <%= WebUtil.display(_BlogMain.getUserId()) %></td>
    <td> <%= WebUtil.display(_BlogMain.getBlogName()) %></td>
    <td> <%= WebUtil.display(_BlogMain.getBlogTitle()) %></td>
    <td> <%= WebUtil.display(_BlogMain.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_BlogMain.getMainDesignSelect()) %></td>
    <td> <%= WebUtil.display(_BlogMain.getUseCustomDesign()) %></td>
    <td> <%= WebUtil.display(_BlogMain.getCustomDesignFile()) %></td>


<td>
<form name="blogMainForm<%=_BlogMain.getId()%>" method="post" action="/v_blog_main_edit.html" >
    <a href="javascript:document.blogMainForm<%=_BlogMain.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _BlogMain.getId() %>">
</form>
</td>
<td>
<a href="/blogMainAction.html?del=true&id=<%=_BlogMain.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>