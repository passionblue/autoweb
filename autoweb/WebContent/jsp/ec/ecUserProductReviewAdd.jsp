<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    EcUserProductReview _EcUserProductReviewDefault = new EcUserProductReview();// EcUserProductReviewDS.getInstance().getDeafult();
    
    String _product_idValue= (reqParams.get("productId")==null?WebUtil.display(_EcUserProductReviewDefault.getProductId()):WebUtil.display((String)reqParams.get("productId")));
    String _user_idValue= (reqParams.get("userId")==null?WebUtil.display(_EcUserProductReviewDefault.getUserId()):WebUtil.display((String)reqParams.get("userId")));
    String _rateValue= (reqParams.get("rate")==null?WebUtil.display(_EcUserProductReviewDefault.getRate()):WebUtil.display((String)reqParams.get("rate")));
    String _reviewValue= (reqParams.get("review")==null?WebUtil.display(_EcUserProductReviewDefault.getReview()):WebUtil.display((String)reqParams.get("review")));
    String _track_backValue= (reqParams.get("trackBack")==null?WebUtil.display(_EcUserProductReviewDefault.getTrackBack()):WebUtil.display((String)reqParams.get("trackBack")));
    String _num_vote_yesValue= (reqParams.get("numVoteYes")==null?WebUtil.display(_EcUserProductReviewDefault.getNumVoteYes()):WebUtil.display((String)reqParams.get("numVoteYes")));
    String _num_vote_noValue= (reqParams.get("numVoteNo")==null?WebUtil.display(_EcUserProductReviewDefault.getNumVoteNo()):WebUtil.display((String)reqParams.get("numVoteNo")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_EcUserProductReviewDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<form name="ecUserProductReviewForm" method="post" action="/ecUserProductReviewAction.html" >
<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

    	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Product Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="productId" value="<%=WebUtil.display(_product_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>User Id</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="userId" value="<%=WebUtil.display(_user_idValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Rate</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="rate" value="<%=WebUtil.display(_rateValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Review</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="review" value="<%=WebUtil.display(_reviewValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Track Back</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="trackBack" value="<%=WebUtil.display(_track_backValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Num Vote Yes</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="numVoteYes" value="<%=WebUtil.display(_num_vote_yesValue)%>"/></TD>
	    </TR>
	            	    	    <TR bgcolor="#ffffff">
	        <TD align="right" ><b>Num Vote No</b> &nbsp;</TD>
	        <TD>&nbsp;<html:text size="70" property="numVoteNo" value="<%=WebUtil.display(_num_vote_noValue)%>"/></TD>
	    </TR>
	            	            <TR bgcolor="#ffffff">
        <TD></TD>  
        <TD>
            <b><a href="javascript:document.ecUserProductReviewForm.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = EcUserProductReviewDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        EcUserProductReview _EcUserProductReview = (EcUserProductReview) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _EcUserProductReview.getId() %> </td>

    <td> <%= WebUtil.display(_EcUserProductReview.getProductId()) %></td>
    <td> <%= WebUtil.display(_EcUserProductReview.getUserId()) %></td>
    <td> <%= WebUtil.display(_EcUserProductReview.getRate()) %></td>
    <td> <%= WebUtil.display(_EcUserProductReview.getReview()) %></td>
    <td> <%= WebUtil.display(_EcUserProductReview.getTrackBack()) %></td>
    <td> <%= WebUtil.display(_EcUserProductReview.getNumVoteYes()) %></td>
    <td> <%= WebUtil.display(_EcUserProductReview.getNumVoteNo()) %></td>
    <td> <%= WebUtil.display(_EcUserProductReview.getTimeCreated()) %></td>


<td>
<form name="ecUserProductReviewForm<%=_EcUserProductReview.getId()%>" method="post" action="/v_ec_user_product_review_edit.html" >
    <a href="javascript:document.ecUserProductReviewForm<%=_EcUserProductReview.getId()%>.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcUserProductReview.getId() %>">
</form>
</td>
<td>
<a href="/ecUserProductReviewAction.html?del=true&id=<%=_EcUserProductReview.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>