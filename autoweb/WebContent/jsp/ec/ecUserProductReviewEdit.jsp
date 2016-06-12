<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

    long id = Long.parseLong(idStr);

    EcUserProductReview _EcUserProductReview = EcUserProductReviewDS.getInstance().getById(id);

    if ( _EcUserProductReview == null ) {
        return;
    }

    String _product_idValue=  WebUtil.display(_EcUserProductReview.getProductId());
    String _user_idValue=  WebUtil.display(_EcUserProductReview.getUserId());
    String _rateValue=  WebUtil.display(_EcUserProductReview.getRate());
    String _reviewValue=  WebUtil.display(_EcUserProductReview.getReview());
    String _track_backValue=  WebUtil.display(_EcUserProductReview.getTrackBack());
    String _num_vote_yesValue=  WebUtil.display(_EcUserProductReview.getNumVoteYes());
    String _num_vote_noValue=  WebUtil.display(_EcUserProductReview.getNumVoteNo());
    String _time_createdValue=  WebUtil.display(_EcUserProductReview.getTimeCreated());
%> 

<br>
<form name="ecUserProductReviewFormEdit" method="post" action="/ecUserProductReviewAction.html" >
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
            <b><a href="javascript:document.ecUserProductReviewFormEdit.submit();">Submit</a> </b>
        </TD>
    </TR>
</TABLE>    

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcUserProductReview.getId()%>">
</form>
