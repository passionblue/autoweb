<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%
    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	String catchString =  String.valueOf(System.currentTimeMillis());
	String	_wpId = WebProcManager.registerWebProcess();

    String _yearValue= "";
    String _weekValue= "";
    String _chur_member_idValue= "";
    String _income_item_idValue= "";
    String _ammountValue= "";
    String _time_createdValue= "";

%>

<div id="partitionFormFrame_chur_income_<%= catchString %>" class="partitionFormFrame"><!-- This is loaded from /ChurIncome_artition.jsp -->

	<script type="text/javascript">
		function sendForm_chur_income_<%= catchString %>() { 
			sendFormAjax('/userManageAction.html', 'ajaxSubmitForm<%= catchString %>', 'ajaxSubmit<%= catchString %>', 'ajaxSubmitResult<%= catchString %>')
		}
	</script>
	<form name="ajaxSubmitForm<%= catchString %>" method="POST" action="/userManageAction.html" id="ajaxSubmitForm272559">



	<div id="churIncomeForm_year_field" class="formFieldFrame">
    <div id="churIncomeForm_year_label" class="formLabel" >Year </div>
    <div id="churIncomeForm_year_dropdown" class="formFieldDropDown" >       
        <select class="field" name="year" id="year">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _yearValue)%>>XX</option-->
        <option value="2012" <%=HtmlUtil.getOptionSelect("2012", _yearValue)%>>2012</option>
        <option value="2013" <%=HtmlUtil.getOptionSelect("2013", _yearValue)%>>2013</option>
        <option value="2014" <%=HtmlUtil.getOptionSelect("2014", _yearValue)%>>2014</option>
        <option value="2015" <%=HtmlUtil.getOptionSelect("2015", _yearValue)%>>2015</option>
        <option value="2016" <%=HtmlUtil.getOptionSelect("2016", _yearValue)%>>2016</option>
        <option value="2017" <%=HtmlUtil.getOptionSelect("2017", _yearValue)%>>2017</option>
        <option value="2018" <%=HtmlUtil.getOptionSelect("2018", _yearValue)%>>2018</option>
        <option value="2019" <%=HtmlUtil.getOptionSelect("2019", _yearValue)%>>2019</option>
        <option value="2020" <%=HtmlUtil.getOptionSelect("2020", _yearValue)%>>2020</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>





	<div id="churIncomeForm_week_field" class="formFieldFrame">
    <div id="churIncomeForm_week_label" class="formLabel" >Week </div>
    <div id="churIncomeForm_week_text" class="formFieldText" >       
        <input id="week" class="field" type="text" size="70" name="week" value="<%=WebUtil.display(_weekValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="churIncomeForm_churMemberId_field" class="formFieldFrame">
    <div id="churIncomeForm_churMemberId_label" class="formLabel" >Chur Member Id </div>
    <div id="churIncomeForm_churMemberId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="churMemberId" id="churMemberId">
        <option value="" >- Please Select -</option>
<%
	List _listChurMember_churMemberId = ChurMemberDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurMember_churMemberId.iterator(); iter.hasNext();){
		ChurMember _obj = (ChurMember) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_chur_member_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getFullName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>


	<div id="churIncomeForm_incomeItemId_field" class="formFieldFrame">
    <div id="churIncomeForm_incomeItemId_label" class="formLabel" >Income Item Id </div>
    <div id="churIncomeForm_incomeItemId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="incomeItemId" id="incomeItemId">
        <option value="" >- Please Select -</option>
<%
	List _listChurIncomeItem_incomeItemId = ChurIncomeItemDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurIncomeItem_incomeItemId.iterator(); iter.hasNext();){
		ChurIncomeItem _obj = (ChurIncomeItem) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_income_item_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getIncomeItem() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeForm_ammount_field" class="formFieldFrame">
    <div id="churIncomeForm_ammount_label" class="formLabel" >Ammount </div>
    <div id="churIncomeForm_ammount_text" class="formFieldText" >       
        <input id="ammount" class="field" type="text" size="70" name="ammount" value="<%=WebUtil.display(_ammountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



		<!--
		<div class="ajaxFormLabel" style="font-weight:bold;">ExtraString</div>
		<INPUT NAME="extString" type="text" size="3" value=""></INPUT><br />

		<div class="ajaxFormLabel" style="font-weight:bold;">Ext Int</div>
		<INPUT NAME="extInt" type="text" size="70" value=""></INPUT><br /> 
		-->
		<INPUT TYPE="HIDDEN" NAME="ajxr" value="getmodalstatus">
		<INPUT TYPE="HIDDEN" NAME="add" value="true">
		<INPUT TYPE="HIDDEN" NAME="wpid" value="<%=_wpId%>">

	</form>

	<span id="ajaxSubmitResult<%= catchString %>"></span> 
	<a id="ajaxSubmit<%= catchString %>" href="javascript:sendForm_chur_income_<%= catchString %>;">Submit</a>
	<br>
	<br>
	<a href="/home.html">Home</a> <img src="/images/ocean.jpg" />
</div>
