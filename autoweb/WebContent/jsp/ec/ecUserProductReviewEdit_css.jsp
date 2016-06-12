<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 

    String idStr  = request.getParameter("id");
    Site site = SiteDS.getInstance().registerSite(request.getServerName());

	if (idStr == null) idStr = "0";
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
<div id="ecUserProductReviewForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="ecUserProductReviewFormEdit" method="get" action="/ecUserProductReviewAction.html" >


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
    <div id="ecUserProductReviewForm_review_text" class="formFieldText" >       
        <input id="requiredField" type="text" size="30" name="review" value="<%=WebUtil.display(_reviewValue)%>"/> 
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



        <div id="ecUserProductReviewFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.ecUserProductReviewFormEdit.submit();">Submit</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_EcUserProductReview.getId()%>">
</form>
</div> <!-- form -->
