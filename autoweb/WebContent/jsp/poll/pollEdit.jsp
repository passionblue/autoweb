<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    Poll _Poll = PollDS.getInstance().getById(id);

    if ( _Poll == null ) {
        return;
    }

    String _user_idValue=  WebUtil.display(_Poll.getUserId());
    String _serialValue=  WebUtil.display(_Poll.getSerial());
    String _typeValue=  WebUtil.display(_Poll.getType());
    String _categoryValue=  WebUtil.display(_Poll.getCategory());
    String _titleValue=  WebUtil.display(_Poll.getTitle());
    String _questionValue=  WebUtil.display(_Poll.getQuestion());
    String _hideValue=  WebUtil.display(_Poll.getHide());
    String _disableValue=  WebUtil.display(_Poll.getDisable());
    String _allow_multipleValue=  WebUtil.display(_Poll.getAllowMultiple());
    String _random_answerValue=  WebUtil.display(_Poll.getRandomAnswer());
    String _hide_commentsValue=  WebUtil.display(_Poll.getHideComments());
    String _hide_resultsValue=  WebUtil.display(_Poll.getHideResults());
    String _time_createdValue=  WebUtil.display(_Poll.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_Poll.getTimeUpdated());
    String _time_expiredValue=  WebUtil.display(_Poll.getTimeExpired());
%> 

<br>
<form name="pollFormEdit" method="post" action="/pollAction.html" >
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
            <b><a href="javascript:document.pollFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_Poll.getId()%>">
</form>
