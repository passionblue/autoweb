<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    ChurIncomeAddDataHolder _ChurIncomeAddDefault = new ChurIncomeAddDataHolder();// ChurIncomeAddDS.getInstance().getDeafult();
    
    String _chur_member_idValue= (reqParams.get("churMemberId")==null?WebUtil.display(_ChurIncomeAddDefault.getChurMemberId()):WebUtil.display((String)reqParams.get("churMemberId")));
    String _titheValue= (reqParams.get("tithe")==null?WebUtil.display(_ChurIncomeAddDefault.getTithe()):WebUtil.display((String)reqParams.get("tithe")));
    String _weeklyValue= (reqParams.get("weekly")==null?WebUtil.display(_ChurIncomeAddDefault.getWeekly()):WebUtil.display((String)reqParams.get("weekly")));
    String _thanksValue= (reqParams.get("thanks")==null?WebUtil.display(_ChurIncomeAddDefault.getThanks()):WebUtil.display((String)reqParams.get("thanks")));
    String _missionValue= (reqParams.get("mission")==null?WebUtil.display(_ChurIncomeAddDefault.getMission()):WebUtil.display((String)reqParams.get("mission")));
    String _constructionValue= (reqParams.get("construction")==null?WebUtil.display(_ChurIncomeAddDefault.getConstruction()):WebUtil.display((String)reqParams.get("construction")));
    String _otherValue= (reqParams.get("other")==null?WebUtil.display(_ChurIncomeAddDefault.getOther()):WebUtil.display((String)reqParams.get("other")));
    String _weekValue= (reqParams.get("week")==null?WebUtil.display(_ChurIncomeAddDefault.getWeek()):WebUtil.display((String)reqParams.get("week")));
    String _yearValue= (reqParams.get("year")==null?WebUtil.display(_ChurIncomeAddDefault.getYear()):WebUtil.display((String)reqParams.get("year")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_add_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="churIncomeAddForm_topArea" class="formTopArea"></div>
<div id="churIncomeAddForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="churIncomeAddForm" method="POST" action="/churIncomeAddAction.html" >




	<div id="churIncomeAddForm_churMemberId_field">
    <div id="churIncomeAddForm_churMemberId_label" class="formLabel" >Chur Member Id </div>
    <div id="churIncomeAddForm_churMemberId_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="churMemberId" value="<%=WebUtil.display(_chur_member_idValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_tithe_field">
    <div id="churIncomeAddForm_tithe_label" class="formLabel" >Tithe </div>
    <div id="churIncomeAddForm_tithe_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="tithe" value="<%=WebUtil.display(_titheValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_weekly_field">
    <div id="churIncomeAddForm_weekly_label" class="formLabel" >Weekly </div>
    <div id="churIncomeAddForm_weekly_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="weekly" value="<%=WebUtil.display(_weeklyValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_thanks_field">
    <div id="churIncomeAddForm_thanks_label" class="formLabel" >Thanks </div>
    <div id="churIncomeAddForm_thanks_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="thanks" value="<%=WebUtil.display(_thanksValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_mission_field">
    <div id="churIncomeAddForm_mission_label" class="formLabel" >Mission </div>
    <div id="churIncomeAddForm_mission_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="mission" value="<%=WebUtil.display(_missionValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_construction_field">
    <div id="churIncomeAddForm_construction_label" class="formLabel" >Construction </div>
    <div id="churIncomeAddForm_construction_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="construction" value="<%=WebUtil.display(_constructionValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_other_field">
    <div id="churIncomeAddForm_other_label" class="formLabel" >Other </div>
    <div id="churIncomeAddForm_other_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="other" value="<%=WebUtil.display(_otherValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_week_field">
    <div id="churIncomeAddForm_week_label" class="formLabel" >Week </div>
    <div id="churIncomeAddForm_week_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="week" value="<%=WebUtil.display(_weekValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_year_field">
    <div id="churIncomeAddForm_year_label" class="formLabel" >Year </div>
    <div id="churIncomeAddForm_year_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="year" value="<%=WebUtil.display(_yearValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="churIncomeAddForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.churIncomeAddForm.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      
            

<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

</form>
</div> <!-- form -->
<div id="churIncomeAddForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = ChurIncomeAddDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurIncomeAddDataHolder _ChurIncomeAdd = (ChurIncomeAddDataHolder) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _ChurIncomeAdd.getId() %> </td>

    <td> <%= WebUtil.display(_ChurIncomeAdd.getChurMemberId()) %></td>
    <td> <%= WebUtil.display(_ChurIncomeAdd.getTithe()) %></td>
    <td> <%= WebUtil.display(_ChurIncomeAdd.getWeekly()) %></td>
    <td> <%= WebUtil.display(_ChurIncomeAdd.getThanks()) %></td>
    <td> <%= WebUtil.display(_ChurIncomeAdd.getMission()) %></td>
    <td> <%= WebUtil.display(_ChurIncomeAdd.getConstruction()) %></td>
    <td> <%= WebUtil.display(_ChurIncomeAdd.getOther()) %></td>
    <td> <%= WebUtil.display(_ChurIncomeAdd.getWeek()) %></td>
    <td> <%= WebUtil.display(_ChurIncomeAdd.getYear()) %></td>


<td>
<form name="churIncomeAddForm<%=_ChurIncomeAdd.getId()%>2" method="get" action="/v_${useDbTable}_edit2.html" >
    <a href="javascript:document.churIncomeAddForm<%=_ChurIncomeAdd.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _ChurIncomeAdd.getId() %>">
</form>

</td>
<td>
<a href="/churIncomeAddAction.html?del=true&id=<%=_ChurIncomeAdd.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>