<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.access.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	if ( sessionContext.getUserObject() == null) return;
	
%>
<h3> Member List</h3>
<a href="/v_chur_member_form.html"> Member</a><br/>

<h3> 헌금 관리</h3>


<div class="nav-buttons-frame">
	<div id="churMemberForm_submit" class="nav-buttons" >       
	    <a id="formSubmit2" href="/v_chur_income_category_form.html" >헌금 카테고리</a>
	</div>
	<div id="churMemberForm_submit" class="nav-buttons" >       
	    <a id="formSubmit2" href="/v_chur_income_category_form.html" >헌금 카테고리</a>
	</div>
	<div id="churMemberForm_submit" class="nav-buttons" >       
	    <a id="formSubmit2" href="/v_chur_income_category_form.html">헌금 카테고리</a>
	</div>
	<div id="churMemberForm_submit" class="nav-buttons" >       
	    <a id="formSubmit2" href="/v_chur_income_category_form.html" >헌금 카테고리</a>
	</div>
	<div id="churMemberForm_submit" class="nav-buttons" >       
	    <a id="formSubmit2" href="/v_chur_income_category_form.html" >헌금 카테고리</a>
	</div>
	<div id="churMemberForm_submit" class="nav-buttons" >       
	    <a id="formSubmit2" href="/v_chur_income_category_form.html" >헌금 카테고리</a>
	</div>
	<div id="churMemberForm_submit" class="nav-buttons" >       
	    <a id="formSubmit2" href="/v_chur_income_category_form.html" >헌금 카테고리</a>
	</div>
</div>
<div class="clear"></div>
<table class="churMenuTable" >
<%
    AccessConfigManager3 churAccessMgr = AccessConfigManager3.registerInstanceForCustomTitle("ChurApp", "conf/churapp_access_config.properties" );

if ( AccessUtil.hasAccess(sessionContext.getUserObject(), churAccessMgr.getSystemRoleByResource("ChurApp", "income_update", AccessDef.SystemRole.SiteAdmin))){
%>
<tr>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/v_chur_income_category_form.html" style="width: 100px;">헌금 카테고리</a>
		</div>
	</td>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/v_chur_income_item_form.html" style="width: 100px;">헌금 아이템</a>
		</div>
	</td>
</tr>

<tr>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/v_chur_income_form.html" style="width: 100px;">헌금개별입력</a>
		</div>
	</td>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/v_chur_income_add_form.html" style="width: 100px;">헌금 입력</a>
		</div>
	</td>
</tr>

<% } %>

<% if ( AccessUtil.hasAccess(sessionContext.getUserObject(), churAccessMgr.getSystemRoleByResource("ChurApp", "income_report", AccessDef.SystemRole.SiteAdmin))){ %>

<tr>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/v_chur_income_update.html" style="width: 100px;">정정</a>
		</div>
	</td>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/v_chur_income_weekly_report.html" style="width: 100px;">리포트 아이템</a>
		</div>
	</td>
</tr>
<% } %>

</table>
<br>

<h3> 지출 관리</h3>

<table class="churMenuTable" >
<% 

if ( AccessUtil.hasAccess(sessionContext.getUserObject(), churAccessMgr.getSystemRoleByResource("ChurApp", "income_update", AccessDef.SystemRole.SiteAdmin))){ 
%>


<tr>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/v_chur_expense_category_form.html" style="width: 100px;">지출 카테고리</a>
		</div>
	</td>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/v_chur_expense_item_form.html" style="width: 100px;">지출 아이템</a>
		</div>
	</td>
</tr>
<tr>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/v_chur_expense_form.html" style="width: 100px;">지출입력</a>
		</div>
	</td>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/v_chur_payee_form.html" style="width: 100px;">지급인 리스트</a>
		</div>
	</td>
</tr>
<%
}
%>

<tr>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/v_chur_expense_weekly_report.html" style="width: 100px;">지출 주간 리포트</a>
		</div>
	</td>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/v_chur_payee_form.html" style="width: 100px;">헌금 아이템</a>
		</div>
	</td>
</tr>
</table>

<br/>

<h3> 리포트 </h3>

<table class="churMenuTable" >
<% if ( AccessUtil.hasAccess(sessionContext.getUserObject(), churAccessMgr.getSystemRoleByResource("ChurApp", "income_report", AccessDef.SystemRole.SiteAdmin))){ %>
<tr>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/_page/chur_ytd_report.html" style="width: 100px;">연간통계</a>
		</div>
	</td>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/_page/chur_ytd_report_itemized.html" style="width: 100px;">지출 아이템 연간통계</a>
		</div>
	</td>
</tr>
<tr>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/_page/chur_ytd_income_report_itemized.html" style="width: 100px;">헌금 아이템 연간통계</a>
		</div>
	</td>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/_page/chur_ytd_report_expense_full_list.html" style="width: 100px;">지출 몽땅</a>
		</div>
	</td>
</tr>
<tr>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/_page/chur_ytd_income_report_itemized_by_member.html" style="width: 100px;">헌금 아이템 연간통계</a>
		</div>
	</td>
	<td style="font: normal normal bold 18px verdana; ">
		<div id="churMemberForm_submit" class="formButton" >       
		    <a id="formSubmit2" href="/_page/chur_ytd_report_expense_full_list.html" style="width: 100px;">지출 몽땅</a>
		</div>
	</td>
</tr>
<%
}
%>
</table>
<br>
<br>

