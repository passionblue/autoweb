<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String idStr  = request.getParameter("id");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	if (idStr == null) idStr = "0";
    long id = Long.parseLong(idStr);

    ChurIncomeDataHolder _ChurIncome = ChurIncomeDS.getInstance().getById(id);

    if ( _ChurIncome == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_home";

    String _yearValue=  WebUtil.display(_ChurIncome.getYear());
    String _weekValue=  WebUtil.display(_ChurIncome.getWeek());
    String _chur_member_idValue=  WebUtil.display(_ChurIncome.getChurMemberId());
    String _income_item_idValue=  WebUtil.display(_ChurIncome.getIncomeItemId());
    String _ammountValue=  WebUtil.display(_ChurIncome.getAmmount());
    String _time_createdValue=  WebUtil.display(_ChurIncome.getTimeCreated());
%> 

<br>
<div id="churIncomeForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churIncomeFormEdit" method="POST" action="/churIncomeAction.html" >


	<div id="churIncomeForm_year_field">
    <div id="churIncomeForm_year_label" class="formLabel" >Year </div>
    <div id="churIncomeForm_year_dropdown" class="formFieldDropDown" >       
        <select id="field" name="year">
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




	<div id="churIncomeForm_week_field">
    <div id="churIncomeForm_week_label" class="formLabel" >Week </div>
    <div id="churIncomeForm_week_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="week" value="<%=WebUtil.display(_weekValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churIncomeForm_churMemberId_field">
    <div id="churIncomeForm_churMemberId_label" class="formLabel" >Chur Member Id </div>
    <div id="churIncomeForm_churMemberId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="churMemberId" value="<%=WebUtil.display(_chur_member_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churIncomeForm_incomeItemId_field">
    <div id="churIncomeForm_incomeItemId_label" class="formLabel" >Income Item Id </div>
    <div id="churIncomeForm_incomeItemId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="incomeItemId" value="<%=WebUtil.display(_income_item_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churIncomeForm_ammount_field">
    <div id="churIncomeForm_ammount_label" class="formLabel" >Ammount </div>
    <div id="churIncomeForm_ammount_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="ammount" value="<%=WebUtil.display(_ammountValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



        <div id="churIncomeFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churIncomeFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncome.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
