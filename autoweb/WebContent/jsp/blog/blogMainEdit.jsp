<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    BlogMain _BlogMain = BlogMainDS.getInstance().getById(id);

    if ( _BlogMain == null ) {
        return;
    }

    String _user_idValue=  WebUtil.display(_BlogMain.getUserId());
    String _blog_nameValue=  WebUtil.display(_BlogMain.getBlogName());
    String _blog_titleValue=  WebUtil.display(_BlogMain.getBlogTitle());
    String _time_createdValue=  WebUtil.display(_BlogMain.getTimeCreated());
    String _main_design_selectValue=  WebUtil.display(_BlogMain.getMainDesignSelect());
    String _use_custom_designValue=  WebUtil.display(_BlogMain.getUseCustomDesign());
    String _custom_design_fileValue=  WebUtil.display(_BlogMain.getCustomDesignFile());
%> 

<br>
<form name="blogMainFormEdit" method="post" action="/blogMainAction.html" >
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
            <b><a href="javascript:document.blogMainFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_BlogMain.getId()%>">
</form>
