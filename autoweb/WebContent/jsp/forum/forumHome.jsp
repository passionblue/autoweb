<%@page language="java" import="com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.jtrend.session.SessionContext,com.autosite.ds.*,java.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<html:link page="/v_forum_section.html" > Add Section</html:link> <br>
<html:link page="/v_forum_category.html" > Add Category</html:link> <br>
<html:link page="/v_forum_subject.html" > Add Subject</html:link> <br>
<html:link page="/v_forum_post.html" > Add Post</html:link> <br>


<%
	if (false) return;

	List list = ForumCategoryDS.getInstance().getAll();
 
 	for(Iterator iter = list.iterator();iter.hasNext();) {
		ForumCategory _ForumCategory = (ForumCategory) iter.next();
%>

<table>
<tr><td>
<form name="forumCategoryForm<%=_ForumCategory.getId()%>" method="post" action="/v_forum_category_edit.html" >
	<%= _ForumCategory.getId() %>			
	<a href="javascript:document.forumCategoryForm<%=_ForumCategory.getId()%>.submit();">Edit</a>			
	<INPUT TYPE=HIDDEN NAME="id" value="<%= _ForumCategory.getId() %>">
</form>
</td></tr>
</table>
<%
	}
%>

