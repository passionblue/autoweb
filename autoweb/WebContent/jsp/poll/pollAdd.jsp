<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    Poll _PollDefault = new Poll();// PollDS.getInstance().getDeafult();
    
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_PollDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _serialValue= (reqParams.get("serial")==null?WebUtil.display(_PollDefault.getSerial()):WebUtil.display((String)reqParams.get("serial")));
    String _typeValue= (reqParams.get("type")==null?WebUtil.display(_PollDefault.getType()):WebUtil.display((String)reqParams.get("type")));
    String _categoryValue= (reqParams.get("category")==null?WebUtil.display(_PollDefault.getCategory()):WebUtil.display((String)reqParams.get("category")));
    String _titleValue= (reqParams.get("title")==null?WebUtil.display(_PollDefault.getTitle()):WebUtil.display((String)reqParams.get("title")));
    String _questionValue= (reqParams.get("question")==null?WebUtil.display(_PollDefault.getQuestion()):WebUtil.display((String)reqParams.get("question")));
    String _hideValue= (reqParams.get("hide")==null?WebUtil.display(_PollDefault.getHide()):WebUtil.display((String)reqParams.get("hide")));
    String _disableValue= (reqParams.get("disable")==null?WebUtil.display(_PollDefault.getDisable()):WebUtil.display((String)reqParams.get("disable")));
    String _allow_multipleValue= (reqParams.get("allowMultiple")==null?WebUtil.display(_PollDefault.getAllowMultiple()):WebUtil.display((String)reqParams.get("allowMultiple")));
    String _random_answerValue= (reqParams.get("randomAnswer")==null?WebUtil.display(_PollDefault.getRandomAnswer()):WebUtil.display((String)reqParams.get("randomAnswer")));
    String _hide_commentsValue= (reqParams.get("hideComments")==null?WebUtil.display(_PollDefault.getHideComments()):WebUtil.display((String)reqParams.get("hideComments")));
    String _hide_resultsValue= (reqParams.get("hideResults")==null?WebUtil.display(_PollDefault.getHideResults()):WebUtil.display((String)reqParams.get("hideResults")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_PollDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_PollDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
    String _time_expiredValue= (reqParams.get("timeExpired")==null?WebUtil.display(_PollDefault.getTimeExpired()):WebUtil.display((String)reqParams.get("timeExpired")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="pollForm" method="post" action="/pollAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>User Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="userId" value="<%=WebUtil.display(_user_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Serial</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="serial" value="<%=WebUtil.display(_serialValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Type</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="type" value="<%=WebUtil.display(_typeValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Category</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="category" value="<%=WebUtil.display(_categoryValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Title</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="title" value="<%=WebUtil.display(_titleValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Question</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="question" value="<%=WebUtil.display(_questionValue)%>"/></TD>
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
        <TD align="right" ><b>Disable</b> &nbsp;</TD>
        <TD>&nbsp;<select name="disable">
            <option value="0" <%=HtmlUtil.getOptionSelect("0", _disableValue)%>>No</option>
            <option value="1" <%=HtmlUtil.getOptionSelect("1", _disableValue)%>>Yes</option>
            </select>
        </TD>
    </TR>
            <TR bgcolor="#ffffff">
        <TD align="right" ><b>Allow Multiple</b> &nbsp;</TD>
        <TD>&nbsp; <input type="checkbox" name="allowMultiple" <%=HtmlUtil.getCheckedBoxValue(_allow_multipleValue)%> />
        </TD>
    </TR>
        
            <TR bgcolor="#ffffff">
        <TD align="right" ><b>Random Answer</b> &nbsp;</TD>
        <TD>&nbsp; <input type="checkbox" name="randomAnswer" <%=HtmlUtil.getCheckedBoxValue(_random_answerValue)%> />
        </TD>
    </TR>
        
            <TR bgcolor="#ffffff">
        <TD align="right" ><b>Hide Comments</b> &nbsp;</TD>
        <TD>&nbsp; <input type="checkbox" name="hideComments" <%=HtmlUtil.getCheckedBoxValue(_hide_commentsValue)%> />
        </TD>
    </TR>
        
            <TR bgcolor="#ffffff">
        <TD align="right" ><b>Hide Results</b> &nbsp;</TD>
        <TD>&nbsp; <input type="checkbox" name="hideResults" <%=HtmlUtil.getCheckedBoxValue(_hide_resultsValue)%> />
        </TD>
    </TR>
        
        	            	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.pollForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = PollDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        Poll _Poll = (Poll) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _Poll.getId() %> </td>

    <td> <%= WebUtil.display(_Poll.getUserId()) %></td>
    <td> <%= WebUtil.display(_Poll.getSerial()) %></td>
    <td> <%= WebUtil.display(_Poll.getType()) %></td>
    <td> <%= WebUtil.display(_Poll.getCategory()) %></td>
    <td> <%= WebUtil.display(_Poll.getTitle()) %></td>
    <td> <%= WebUtil.display(_Poll.getQuestion()) %></td>
    <td> <%= WebUtil.display(_Poll.getHide()) %></td>
    <td> <%= WebUtil.display(_Poll.getDisable()) %></td>
    <td> <%= WebUtil.display(_Poll.getAllowMultiple()) %></td>
    <td> <%= WebUtil.display(_Poll.getRandomAnswer()) %></td>
    <td> <%= WebUtil.display(_Poll.getHideComments()) %></td>
    <td> <%= WebUtil.display(_Poll.getHideResults()) %></td>
    <td> <%= WebUtil.display(_Poll.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_Poll.getTimeUpdated()) %></td>
    <td> <%= WebUtil.display(_Poll.getTimeExpired()) %></td>


<td>
<form name="pollForm<%=_Poll.getId()%>" method="post" action="/v_poll_edit.html" >
    <a href="javascript:document.pollForm<%=_Poll.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _Poll.getId() %>">
</form>
</td>
<td>
<a href="/pollAction.html?del=true&id=<%=_Poll.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>