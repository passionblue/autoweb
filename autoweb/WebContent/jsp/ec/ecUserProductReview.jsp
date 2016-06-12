<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    String _product_idValue= "";
    String _user_idValue= "";
    String _rateValue= "";
    String _reviewValue= "";
    String _track_backValue= "";
    String _num_vote_yesValue= "";
    String _num_vote_noValue= "";
    String _time_createdValue= "";
%> 

<TABLE border="1" width="100%" bgcolor="transparent" align="center" cellspacing="0" style="border-width: 1px; border-style: solid">
    <TR>
        <TD>
            <a href="t_ec_user_product_review_add.html"> Add New</a>
            <a href="t_ec_user_product_review_add2.html"> Add New 2</a>
        </TD>
    </TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%

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
    <form name="ecUserProductReviewForm<%=_EcUserProductReview.getId()%>" method="get" action="/v_ec_user_product_review_edit.html" >
        <a href="javascript:document.ecUserProductReviewForm<%=_EcUserProductReview.getId()%>.submit();">Edit</a>           
        <INPUT TYPE="HIDDEN" NAME="id" value="<%= _EcUserProductReview.getId() %>">
    </form>
    <form name="ecUserProductReviewForm<%=_EcUserProductReview.getId()%>2" method="get" action="/v_ec_user_product_review_edit2.html" >
        <a href="javascript:document.ecUserProductReviewForm<%=_EcUserProductReview.getId()%>2.submit();">Edit2</a>           
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