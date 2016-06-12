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

    ChurIncomeAddDataHolder _ChurIncomeAdd = ChurIncomeAddDS.getInstance().getById(id);

    if ( _ChurIncomeAdd == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_add_home";

    String _chur_member_idValue=  WebUtil.display(_ChurIncomeAdd.getChurMemberId());
    String _titheValue=  WebUtil.display(_ChurIncomeAdd.getTithe());
    String _weeklyValue=  WebUtil.display(_ChurIncomeAdd.getWeekly());
    String _thanksValue=  WebUtil.display(_ChurIncomeAdd.getThanks());
    String _missionValue=  WebUtil.display(_ChurIncomeAdd.getMission());
    String _constructionValue=  WebUtil.display(_ChurIncomeAdd.getConstruction());
    String _otherValue=  WebUtil.display(_ChurIncomeAdd.getOther());
    String _weekValue=  WebUtil.display(_ChurIncomeAdd.getWeek());
    String _yearValue=  WebUtil.display(_ChurIncomeAdd.getYear());
%> 

<br>
<div id="churIncomeAddForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churIncomeAddFormEdit" method="POST" action="/churIncomeAddAction.html" >




	<div id="churIncomeAddForm_churMemberId_field">
    <div id="churIncomeAddForm_churMemberId_label" class="formLabel" >Chur Member Id </div>
    <div id="churIncomeAddForm_churMemberId_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="churMemberId" value="<%=WebUtil.display(_chur_member_idValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churIncomeAddForm_tithe_field">
    <div id="churIncomeAddForm_tithe_label" class="formLabel" >Tithe </div>
    <div id="churIncomeAddForm_tithe_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="tithe" value="<%=WebUtil.display(_titheValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churIncomeAddForm_weekly_field">
    <div id="churIncomeAddForm_weekly_label" class="formLabel" >Weekly </div>
    <div id="churIncomeAddForm_weekly_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="weekly" value="<%=WebUtil.display(_weeklyValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churIncomeAddForm_thanks_field">
    <div id="churIncomeAddForm_thanks_label" class="formLabel" >Thanks </div>
    <div id="churIncomeAddForm_thanks_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="thanks" value="<%=WebUtil.display(_thanksValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churIncomeAddForm_mission_field">
    <div id="churIncomeAddForm_mission_label" class="formLabel" >Mission </div>
    <div id="churIncomeAddForm_mission_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="mission" value="<%=WebUtil.display(_missionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churIncomeAddForm_construction_field">
    <div id="churIncomeAddForm_construction_label" class="formLabel" >Construction </div>
    <div id="churIncomeAddForm_construction_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="construction" value="<%=WebUtil.display(_constructionValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churIncomeAddForm_other_field">
    <div id="churIncomeAddForm_other_label" class="formLabel" >Other </div>
    <div id="churIncomeAddForm_other_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="other" value="<%=WebUtil.display(_otherValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churIncomeAddForm_week_field">
    <div id="churIncomeAddForm_week_label" class="formLabel" >Week </div>
    <div id="churIncomeAddForm_week_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="week" value="<%=WebUtil.display(_weekValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="churIncomeAddForm_year_field">
    <div id="churIncomeAddForm_year_label" class="formLabel" >Year </div>
    <div id="churIncomeAddForm_year_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="year" value="<%=WebUtil.display(_yearValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="churIncomeAddFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churIncomeAddFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncomeAdd.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
