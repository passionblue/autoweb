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
<div id="ecUserProductReviewForm_topArea" class="formTopArea"></div>
<div id="ecUserProductReviewForm" class="formFrame">
<div class="fieldSideText" style="font-size:12px;color:red;">* Required fields</div>
<form name="ecUserProductReviewForm" method="get" action="/ecUserProductReviewAction.html" >

    <INPUT TYPE="HIDDEN" NAME="productId" value="<%=WebUtil.display(_product_idValue)%>" />


    <INPUT TYPE="HIDDEN" NAME="userId" value="<%=WebUtil.display(_user_idValue)%>" />


	<div id="ecUserProductReviewForm_rate_field">
    <div id="ecUserProductReviewForm_rate_label" class="formLabel" >Rate </div>
    <div id="ecUserProductReviewForm_rate_dropdown" class="formFieldDropDown" >       
        <select id="field" name="rate">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _rateValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="ecUserProductReviewForm_review_field">
    <div id="ecUserProductReviewForm_review_label" class="formRequiredLabel" >Review* </div>
    <div id="ecUserProductReviewForm_review_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="requiredField" NAME="review" COLS="50" ROWS="8" ><%=WebUtil.display(_reviewValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>



	<div id="ecUserProductReviewForm_trackBack_field">
    <div id="ecUserProductReviewForm_trackBack_label" class="formLabel" >Track Back </div>
    <div id="ecUserProductReviewForm_trackBack_text" class="formFieldText" >       
        <input id="field" type="text" size="50" name="trackBack" value="<%=WebUtil.display(_track_backValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="ecUserProductReviewForm_numVoteYes_field">
    <div id="ecUserProductReviewForm_numVoteYes_label" class="formLabel" >Num Vote Yes </div>
    <div id="ecUserProductReviewForm_numVoteYes_text" class="formFieldText" >       
        <input id="field" type="text" size="5" name="numVoteYes" value="<%=WebUtil.display(_num_vote_yesValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="ecUserProductReviewForm_numVoteNo_field">
    <div id="ecUserProductReviewForm_numVoteNo_label" class="formLabel" >Num Vote No </div>
    <div id="ecUserProductReviewForm_numVoteNo_text" class="formFieldText" >       
        <input id="field" type="text" size="5" name="numVoteNo" value="<%=WebUtil.display(_num_vote_noValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="ecUserProductReviewForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecUserProductReviewForm.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
</form>
</div> <!-- form -->
<div id="ecUserProductReviewForm_bottomArea" class="formBottomArea"></div>


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