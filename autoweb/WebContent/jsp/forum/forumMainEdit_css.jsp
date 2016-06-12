<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    ForumMain _ForumMain = ForumMainDS.getInstance().getById(id);

    if ( _ForumMain == null ) {
        return;
    }

	String retPage = (String) reqParams.get("returnPage");    

    String _titleValue=  WebUtil.display(_ForumMain.getTitle());
    String _time_createdValue=  WebUtil.display(_ForumMain.getTimeCreated());
%> 

<br>
<div id="forumMainForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="forumMainFormEdit" method="get" action="/forumMainAction.html" >




	<div id="forumMainForm_title_field">
    <div id="forumMainForm_title_label" class="formRequiredLabel" >Title* </div>
    <div id="forumMainForm_title_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="title" value="<%=WebUtil.display(_titleValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="forumMainFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.forumMainFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ForumMain.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
