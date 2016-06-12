<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@page import="com.autosite.util.chur.ChurUtil"%>
<%@page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");

	String command = request.getParameter("cmd");

    String idStr  = "0";
    ChurIncomeAddDataHolder _ChurIncomeAdd = null;
	
	boolean isEdit = false;	
	String _wpId = null;

	if (command != null && command.equalsIgnoreCase("edit")){
	    long id = WebParamUtil.getLongValue(request.getParameter("id"));
		_ChurIncomeAdd = ChurIncomeAddDS.getInstance().getById(id);
		if ( _ChurIncomeAdd == null) {
			JspUtil.getLogger().error("Object not found by id=" + id);
			isEdit = false;
		} else {
			isEdit = true;
		}
        
	} 

	// If no object found, proceed as a create
	if ( !isEdit) {
	    _ChurIncomeAdd = new ChurIncomeAddDataHolder();// ChurIncomeAddDS.getInstance().getDeafult();
    	_wpId = WebProcManager.registerWebProcess();
    	
    	_ChurIncomeAdd.setWeek(ChurUtil.getWeekString());
	}	 

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "chur_income_add_home";

    String _chur_member_idValue= (reqParams.get("churMemberId")==null?WebUtil.display(_ChurIncomeAdd.getChurMemberId()):WebUtil.display((String)reqParams.get("churMemberId")));
    String _titheValue= (reqParams.get("tithe")==null?WebUtil.display(_ChurIncomeAdd.getTithe()):WebUtil.display((String)reqParams.get("tithe")));
    String _weeklyValue= (reqParams.get("weekly")==null?WebUtil.display(_ChurIncomeAdd.getWeekly()):WebUtil.display((String)reqParams.get("weekly")));
    String _thanksValue= (reqParams.get("thanks")==null?WebUtil.display(_ChurIncomeAdd.getThanks()):WebUtil.display((String)reqParams.get("thanks")));
    String _missionValue= (reqParams.get("mission")==null?WebUtil.display(_ChurIncomeAdd.getMission()):WebUtil.display((String)reqParams.get("mission")));
    String _constructionValue= (reqParams.get("construction")==null?WebUtil.display(_ChurIncomeAdd.getConstruction()):WebUtil.display((String)reqParams.get("construction")));
    String _otherValue= (reqParams.get("other")==null?WebUtil.display(_ChurIncomeAdd.getOther()):WebUtil.display((String)reqParams.get("other")));
    String _weekValue= (reqParams.get("week")==null?WebUtil.display(_ChurIncomeAdd.getWeek()):WebUtil.display((String)reqParams.get("week")));

    long pagestamp = System.nanoTime();
    
    _weekValue = ChurWebUtil.getSelectedWeek(request );
   	int _selectedYear = ChurWebUtil.getSelectedYear(request );

	_wpId = WebProcManager.registerWebProcess();
%> 

<br>
<%
	String str = "";
	List _listChurMember_churMemberId2 = ChurMemberDS.getInstance().getBySiteId(site.getId());
	for(Iterator iter = _listChurMember_churMemberId2.iterator(); iter.hasNext();){
		ChurMember _obj = (ChurMember) iter.next();
		str += "'"+_obj.getFullName() + "',";
%>		
		            
<%	
	} 
	str +="''";
	System.out.println(str);
%>

   
<script type="text/javascript">
	$(document).ready(function() {
		var goods = [<%=str%>];
		$( "#searchAuto" ).autocomplete({
			source: goods
		});
	});	

	function openCloseClick(){
		if ( $("#churIncomeAddForm").is(":hidden") ) 
			$("#churIncomeAddForm").slideDown(500);
		else
			$("#churIncomeAddForm").slideUp(500);
	}
</script>


<!-- why this is here??? what was i doing -140301 
<div id="slideOpenSwitch"> <img style="float:left;" src="/images/dev/PNG/Blue/18/arrow_down.png"/> <p>Open group table</p></div> 
<div id="slideOpenSwitch"> <a href="javascript:openCloseClick()"><img style="float:left;" src="/images/dev/PNG/Blue/18/arrow_down.png"/> </a></div> 
<br>
<br><br>
 -->

<div id="churIncomeAddForm" class="formFrame">

<div id="churIncomeAddFormInnerFrame" class="formInnerFrame">

	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김장환/95/0/20/0/0/0/0">김장환</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김태복/96/0/20/0/0/0/0">김태복</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김기호/94/0/20/20/40/0/0">김기호</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/97/90/10/30/30/0/0">김경희</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/158/0/0/0/0/0/0">강성규,은정희</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/215/0/0/0/0/0/0">김정업,이혜정</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/98/0/0/0/0/0/0">강민수</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/99/0/0/0/0/0/0">강현주</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/105/0/0/0/0/0/0">김문경</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/134/0/0/0/0/0/0">김선근</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/106/0/0/0/0/0/0">김인순</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/248/0/0/0/0/0/0">김예진</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/146/90/0/0/0/0/0">문영례</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/188/0/11/0/0/0/0">박정민,상현,상준</a>
    </div> 

	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/120/70/0/0/0/0/0">서윤경,김애리</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/132/0/0/0/0/0/0">손유복</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/161/0/0/0/0/0/0">안동규</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/93/0/20/0/0/0/0">유현궁</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/187/0/20/0/0/0/0">이천식</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/123/0/0/0/0/0/0">이천식,영주,요셉</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/246/100/0/0/0/0/0">이장세</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/256/100/0/0/0/0/0">이경림</a>
    </div> 

    
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/156/0/0/0/0/0/0">장주희</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/128/0/20/0/0/0/0">정갑진</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/130/0/0/0/0/0/0">정은비</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/129/0/0/0/0/0/0">정평화</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/131/0/0/0/0/0/0">최기성</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/133/0/0/0/0/0/0">최기성,손유복</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/143/0/0/0/0/0/0">최은수</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/162/0/0/0/0/0/0">최인례</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/136/0/0/0/0/0/0">최정윤</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/203/100/0/0/30/0/0">석대영</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/202/0/20/20/0/0/0">임자윤</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/234/0/20/0/0/0/0">석윤영</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/235/0/10/0/0/0/0">석연아</a>
    </div> 

	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/196/0/0/0/0/0/0">황성민</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/201/0/3/0/0/0/0">임영광</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/147/0/0/0/0/0/0">무명</a>
    </div> 
	<div class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
		<a id="buttonSetToInputNameShortcut" style="width: 100px; height: 15px; padding: 2px; "  rel="김경희/121/0/0/0/0/0/0">서영주</a>
    </div> 


    


	<div class="clear"></div>
</div>

<br>

<div id="churIncomeAddFormInnerFrame" class="formInnerFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="churIncomeAddForm_Form" method="POST" action="/churIncomeAddAction.html" id="churIncomeAddForm_Form">

	<div id="churIncomeAddForm_churMemberId_field" class="formFieldFrame">
    <div id="churIncomeAddForm_churMemberId_label" class="formLabel" >교인 </div>
    <div id="churIncomeAddForm_churMemberId_dropdown" class="formFieldDropDown" >       
        <select class="field" name="churMemberId" id="churMemberId">
        <option value="" >- Please Select -</option>
<%
	List _listChurMember_churMemberId = ChurMemberDS.getInstance().getBySiteId(site.getId());
	Collections.sort(_listChurMember_churMemberId, new ChurMemberNameComparator());	

	for(Iterator iter = _listChurMember_churMemberId.iterator(); iter.hasNext();){
		ChurMember _obj = (ChurMember) iter.next();
%>		
            <option value="<%=_obj.getId()%>" <%=HtmlUtil.getOptionSelect(_chur_member_idValue, String.valueOf(_obj.getId()))%> ><%=_obj.getFullName() + " (" + _obj.getId() + ")" %></option>
<%	} %>            
        </select>  <span></span>
    	<input type="text" id="searchAuto" autocomplete="on">
    
    </div>
          
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_tithe_field" class="formFieldFrame">
	
    <div id="churIncomeAddForm_tithe_label" class="formLabel" >십일조 </div>
    <div id="churIncomeAddForm_tithe_text" class="formFieldText" >       
        <input id="tithe" class="field" type="text" size="10" name="tithe" value="<%=WebUtil.display(_titheValue)%>"/> <span></span>
    </div>      
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " ></a>
    </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >1</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >2</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; ">5</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >10</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >20</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >50</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 25px; height: 15px; padding: 2px; " >100</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	       <a id="buttonSetToInput" style="width: 30px; height: 15px; padding: 2px; " >&nbsp;-10</a>
    </div>        
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_weekly_field" class="formFieldFrame">
    <div id="churIncomeAddForm_weekly_label" class="formLabel" >주일 </div>
    <div id="churIncomeAddForm_weekly_text" class="formFieldText" >       
        <input id="weekly" class="field" type="text" size="10" name="weekly" value="<%=WebUtil.display(_weeklyValue)%>"/> <span></span>
    </div>      
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " ></a>
    </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >1</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >2</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; ">5</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >10</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >20</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >50</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 25px; height: 15px; padding: 2px; " >100</a>
       </div>        
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_thanks_field" class="formFieldFrame">
    <div id="churIncomeAddForm_thanks_label" class="formLabel" >감사 </div>
    <div id="churIncomeAddForm_thanks_text" class="formFieldText" >       
        <input id="thanks" class="field" type="text" size="10" name="thanks" value="<%=WebUtil.display(_thanksValue)%>"/> <span></span>
    </div>      
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " ></a>
    </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >1</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >2</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; ">5</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >10</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >20</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >50</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 25px; height: 15px; padding: 2px; " >100</a>
       </div>        
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_mission_field" class="formFieldFrame">
    <div id="churIncomeAddForm_mission_label" class="formLabel" >선교 </div>
    <div id="churIncomeAddForm_mission_text" class="formFieldText" >       
        <input id="mission" class="field" type="text" size="10" name="mission" value="<%=WebUtil.display(_missionValue)%>"/> <span></span>
    </div>      
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " ></a>
    </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >1</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >2</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; ">5</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >10</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >20</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >50</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 25px; height: 15px; padding: 2px; " >100</a>
       </div>        
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_construction_field" class="formFieldFrame">
    <div id="churIncomeAddForm_construction_label" class="formLabel" >건축 </div>
    <div id="churIncomeAddForm_construction_text" class="formFieldText" >       
        <input id="construction" class="field" type="text" size="10" name="construction" value="<%=WebUtil.display(_constructionValue)%>"/> <span></span>
    </div>      
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " ></a>
    </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >1</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >2</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; ">5</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >10</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >20</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >50</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 25px; height: 15px; padding: 2px; " >100</a>
       </div>        
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_other_field" class="formFieldFrame">
    <div id="churIncomeAddForm_other_label" class="formLabel" >기타 </div>
    <div id="churIncomeAddForm_other_text" class="formFieldText" >       
        <input id="other" class="field" type="text" size="10" name="other" value="<%=WebUtil.display(_otherValue)%>"/> <span></span>
    </div>      
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " ></a>
    </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >1</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >2</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; ">5</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >10</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >20</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >50</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 25px; height: 15px; padding: 2px; " >100</a>
       </div>        
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_other_field" class="formFieldFrame">
    <div id="churIncomeAddForm_other_label" class="formLabel" >기타 </div>
    <div id="churIncomeAddForm_other_text" class="formFieldText" >       
        <input id="other" class="field" type="text" size="10" name="easter" value="<%=WebUtil.display(_otherValue)%>"/> <span></span>
    </div>      
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " ></a>
    </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >1</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >2</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; ">5</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >10</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >20</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 20px; height: 15px; padding: 2px; " >50</a>
       </div>        
	<div id="churIncomeAddForm_submit" class="formButton" style="float:left; top: 0px; margin: 0px; padding: 0px;">       
  	        <a id="buttonSetToInput" style="width: 25px; height: 15px; padding: 2px; " >100</a>
       </div>        
	</div><div class="clear"></div>




	<div id="churIncomeAddForm_week_field" class="formFieldFrame">
    <div id="churIncomeAddForm_week_label" class="formLabel" >Week </div>
    <div id="churIncomeAddForm_week_text" class="formFieldText" >       
        <input id="week" class="field" type="text" size="10" name="week" value="<%=WebUtil.display(_weekValue)%>" readonly="readonly"/> <span></span>
    </div>      
	</div><div class="clear"></div>

	<div class="submitFrame">

        <div id="churIncomeAddForm_submit" class="formButton" >       
            <a id="formSubmit2" href="javascript:document.churIncomeAddForm_Form.submit();">Submit</a>
        </div>      
<!--
        <div id="churIncomeAddForm_submit_cancel" class="formButton" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

        <div id="churIncomeAddForm_submit_ext" class="formButton" >       
            <a href="#">Ext</a>
        </div>      
-->
        <div id="churIncomeAddForm_submit_cancel" class="formButton" >       
            <a href="javascript:submit_cancel_<%=pagestamp%>();">Cancel</a>
        </div>      

        <div id="churIncomeAddForm_submit_ext" class="formButton" >       
            <a href="javascript:submit_extent_<%=pagestamp%>();">Ext</a>
        </div>      

	</div>

<% if (isEdit) { %>

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_ChurIncomeAdd.getId()%>">

<% } else { %>
<INPUT TYPE="HIDDEN" NAME="add" value="true">
<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">

<% } %>
<%	
	Map resTransMap = (Map) session.getAttribute("k_reserve_xfer_params");
	for(Iterator iter =  resTransMap.keySet().iterator();iter.hasNext();){
	    String key = (String) iter.next();
    	String val = (String) resTransMap.get(key);
%>
	    <INPUT TYPE="HIDDEN" NAME="<%=key %>" value="<%=val%>">

<% } %>

<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>

<INPUT TYPE="HIDDEN" NAME="year" value="<%=_selectedYear%>">

</form>
</div> 
</div> <!-- form -->

<!-- ========================================================================================================= -->
<TABLE class="mytable1">

<TR >
	<td> 이름</td>
	<td> <b>십일조</b></td>
	<td> <b>주일</b></td>
	<td> <b>감사</b></td>
	<td> <b>선교</b></td>
	<td> <b>건축</b></td>
	<td> <b>기타</b></td>
	<td> <b>개인합계</b></td>
</TR>


<%

	List<Map<String,Object>> lst = ChurReportUtil.getFormatIncomeList(site.getId(), _selectedYear, _weekValue); //, ChurUtil.getWeekString());

	Map itemTotal = new HashMap();;

	for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
    	Map<String, Object> map = (Map<String, Object>) iterator.next();

    	if ( map.containsKey("isTotalLine")) {
    	    itemTotal = map;
    	    continue;
    	}
    	
    	ChurMember member = (ChurMember) map.get("member");
    	
    	ChurIncome ti = (ChurIncome)map.get("tithe"); 
        ChurIncome we = (ChurIncome)map.get("weekly");
        ChurIncome th = (ChurIncome)map.get("thanks");
        ChurIncome mi = (ChurIncome)map.get("mission");
        ChurIncome co = (ChurIncome)map.get("construction");
        ChurIncome ot = (ChurIncome)map.get("construction");
        
        Double memberTotal  = (Double)map.get("memberTotal");
%>

<TR>
	<td> <%= member.getFullName() %></td>
    <td> <%= WebUtil.display(ti==null?0.0:ti.getAmmount()) %> </td>
    <td> <%= WebUtil.display(we==null?0.0:we.getAmmount()) %> </td>
    <td> <%= WebUtil.display(th==null?0.0:th.getAmmount()) %> </td>
    <td> <%= WebUtil.display(mi==null?0.0:mi.getAmmount()) %> </td>
    <td> <%= WebUtil.display(co==null?0.0:co.getAmmount()) %> </td>
    <td> <%= WebUtil.display(ot==null?0.0:ot.getAmmount()) %> </td>
    <td> <%= WebUtil.display(memberTotal) %> </td>
    
</TR>
<%
	}
	
	Double ti = (Double)itemTotal.get("tithe"); 
	Double we = (Double)itemTotal.get("weekly");
	Double th = (Double)itemTotal.get("thanks");
	Double mi = (Double)itemTotal.get("mission");
	Double co = (Double)itemTotal.get("construction");
	Double ot = (Double)itemTotal.get("construction");
	
%>

<TR >
	<td> <b><%= "TOTAL" %></b></td>
    <td> <%= WebUtil.display(ti==null?0.0:ti.doubleValue()) %> </td>
    <td> <%= WebUtil.display(we==null?0.0:we.doubleValue()) %> </td>
    <td> <%= WebUtil.display(th==null?0.0:th.doubleValue()) %> </td>
    <td> <%= WebUtil.display(mi==null?0.0:mi.doubleValue()) %> </td>
    <td> <%= WebUtil.display(co==null?0.0:co.doubleValue()) %> </td>
    <td> <%= WebUtil.display(ot==null?0.0:ot.doubleValue()) %> </td>
    <td> <%= "" %> </td>
</TR>

</TABLE>

<!-- ========================================================================================================= -->
<TABLE class="mytable1">

<TR >
    <td class="columnTitle"> ID </td>
<%	
	boolean  showListAllByAdmin = true;
	if (showListAllByAdmin) {
%>
    <td class="columnTitle"> Site </td> 
	
<% 	} %>
    <td class="columnTitle">  Member </td> 
    <td class="columnTitle">  Tithe </td> 
    <td class="columnTitle">  Weekly </td> 
    <td class="columnTitle">  Thanks </td> 
    <td class="columnTitle">  Mission </td> 
    <td class="columnTitle">  Construction </td> 
    <td class="columnTitle">  Other </td> 
    <td class="columnTitle">  Week </td> 
	<td class="columnTitle"> DEL </td>
</TR>

<%
   	List list = ChurIncomeAddDS.getInstance().getBySiteId(site.getId());

    for(Iterator iter = list.iterator();iter.hasNext();) {
        ChurIncomeAddDataHolder _oChurIncomeAdd = (ChurIncomeAddDataHolder) iter.next();
        ChurMember _member = ChurMemberDS.getInstance().getById(_oChurIncomeAdd.getChurMemberId());
%>

<TR>
    <td> <%= _oChurIncomeAdd.getId() %> </td>

<%	
	if (showListAllByAdmin) {
	long siteId = _oChurIncomeAdd.getSiteId();
	Site st = SiteDS.getInstance().getById(siteId);
	
	String siteName = (st == null? "XXX="+siteId: st.getSiteUrl());	
	
%>
    <td><%= siteName %></td> 
	
<% 	} %>
    
	<td> <%= (_member==null?"":_member.getFullName())  %> </td>
	<td> <%= _oChurIncomeAdd.getTithe()  %> </td>
	<td> <%= _oChurIncomeAdd.getWeekly()  %> </td>
	<td> <%= _oChurIncomeAdd.getThanks()  %> </td>
	<td> <%= _oChurIncomeAdd.getMission()  %> </td>
	<td> <%= _oChurIncomeAdd.getConstruction()  %> </td>
	<td> <%= _oChurIncomeAdd.getOther()  %> </td>
	<td> <%= _oChurIncomeAdd.getWeek()  %> </td>
	<td> <a href="javascript:sendFormAjaxSimple('/churIncomeAddAction.html?del=true&id=<%=_oChurIncomeAdd.getId() %>&ajaxRequest=true&ajaxOut=getModalStatus', false, updateVal );"><img id="deleteRow" src="/images/icons/led/cross.png"/></a></td>
</TR>

<%
    }
%>
</TABLE>

<script type="text/javascript">
	function updateVal(msg){
	}
	function submit_cancel_<%=pagestamp%>(){
		location.href='/moveTo.html?dest=<%=cancelPage%>';
	}	
	function submit_extent_<%=pagestamp%>(){
	}
</script>




