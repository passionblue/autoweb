<%@page language="java"
	import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.util.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    String listAllByAdmin = request.getParameter("listAllByAdmin");

    boolean showListAllByAdmin = false;
    if (sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)) {
        showListAllByAdmin = true;
    }

    List list = new ArrayList();
    SiteSuggestDS ds = SiteSuggestDS.getInstance();

    if (showListAllByAdmin)
        list = ds.getAll();
    else
        list = ds.getBySiteId(site.getId());
    SiteDS siteDS = SiteDS.getInstance();
%>

<!-- =================== PAGING =================== -->
<%
    String uri = (String) session.getAttribute("k_request_uri");

    int defaultNumDisplay = 10;
    int numDisplayInPage = WebParamUtil.getIntValue(request.getParameter("numInPage"), defaultNumDisplay);;
    int listPage = WebParamUtil.getIntValue(request.getParameter("listPage"), 1);
    String optionQueryStr = "";
    if (showListAllByAdmin)
        optionQueryStr += "&listAllByAdmin=true";

    PagingInfo pagingInfo = PagingUtil.getPageDisplayInfo(list, numDisplayInPage, listPage);

    list = PagingUtil.getPagedList(pagingInfo, list);
    String html = PagingUtil.createPagingHtmlFromPagingInfo(pagingInfo, uri, optionQueryStr, listPage);
%>
<%=html%>

<!-- =================== END PAGING =================== -->


<TABLE border="1" width="100%" bgcolor="transparent" align="center"
	cellspacing="0" style="border-width: 1px; border-style: solid">
	<TR>
		<TD>
			<a href="v_site_suggest_form.html?prv_returnPage=site_suggest_home">
				Add New </a>
		</TD>
	</TR>
</TABLE>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center"
	cellspacing="1">

	<TR bgcolor="#ffffff" valign="top">

		<td>
			ID
		</td>

		<td>
			<div id="siteSuggestForm_category_label"
				style="font-size: normal normal bold 10px verdana;">
				Category
			</div>
		</td>
		<td>
			<div id="siteSuggestForm_suggest_label"
				style="font-size: normal normal bold 10px verdana;">
				Suggest
			</div>
		</td>
		<td>
			<div id="siteSuggestForm_resolved_label"
				style="font-size: normal normal bold 10px verdana;">
				Resolved
			</div>
		</td>
		<td></td>
		<td></td>
	</TR>


	<%
	    for (Iterator iter = list.iterator(); iter.hasNext();) {
	        SiteSuggest _SiteSuggest = (SiteSuggest) iter.next();
	%>



	<TR bgcolor="#ffffff" valign="top">

		<td>
			<%=_SiteSuggest.getId()%>
		</td>


		<td>
			<form
				name="siteSuggestFormEditField_Category_<%=_SiteSuggest.getId()%>"
				method="get" action="/siteSuggestAction.html">


				<div id="siteSuggestForm_category_field">
					<div id="siteSuggestForm_category_text" class="formFieldText">
						<input id="field" type="text" size="20" name="category"
							value="<%=WebUtil.display(_SiteSuggest.getCategory())%>" />
						<span></span>
					</div>
				</div>
				<div class="clear"></div>

				<br>
				<a id="formSubmit"
					href="javascript:document.siteSuggestFormEditField_Category_<%=_SiteSuggest.getId()%>.submit();">Submit</a>

				<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
				<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SiteSuggest.getId()%>">
				<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_suggest_home">
			</form>


		</td>

		<td>
			<form
				name="siteSuggestFormEditField_Suggest_<%=_SiteSuggest.getId()%>"
				method="get" action="/siteSuggestAction.html">


				<div id="siteSuggestForm_suggest_field">
					<div id="siteSuggestForm_suggest_text" class="formFieldText">
						<input id="field" type="text" size="20" name="suggest"
							value="<%=WebUtil.display(_SiteSuggest.getSuggest())%>" />
						<span></span>
					</div>
				</div>
				<div class="clear"></div>

				<br>
				<a id="formSubmit"
					href="javascript:document.siteSuggestFormEditField_Suggest_<%=_SiteSuggest.getId()%>.submit();">Submit</a>

				<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
				<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SiteSuggest.getId()%>">
				<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_suggest_home">
			</form>


		</td>

		<td>
			<form
				name="siteSuggestFormEditField_Resolved_<%=_SiteSuggest.getId()%>"
				method="get" action="/siteSuggestAction.html">


				<div id="siteSuggestForm_resolved_field">
					<div id="siteSuggestForm_resolved_dropdown"
						class="formFieldDropDown">
						<select name="resolved">
							<option value="0"
								<%=HtmlUtil.getOptionSelect("0", _SiteSuggest.getResolved())%>>
								No
							</option>
							<option value="1"
								<%=HtmlUtil.getOptionSelect("1", _SiteSuggest.getResolved())%>>
								Yes
							</option>
						</select>
					</div>
				</div>
				<div class="clear"></div>

				<br>
				<a id="formSubmit"
					href="javascript:document.siteSuggestFormEditField_Resolved_<%=_SiteSuggest.getId()%>.submit();">Submit</a>

				<INPUT TYPE="HIDDEN" NAME="editfield" value="true">
				<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SiteSuggest.getId()%>">
				<INPUT TYPE="HIDDEN" NAME="returnPage" value="site_suggest_home">
			</form>


		</td>



		<td>
			<form name="siteSuggestForm<%=_SiteSuggest.getId()%>2" method="get"
				action="/v_site_suggest_form.html">
				<a
					href="javascript:document.siteSuggestForm<%=_SiteSuggest.getId()%>2.submit();">Edit</a>
				<INPUT TYPE="HIDDEN" NAME="id" value="<%=_SiteSuggest.getId()%>">
				<INPUT TYPE="HIDDEN" NAME="cmd" value="edit">
				<INPUT TYPE="HIDDEN" NAME="prv_returnPage" value="site_suggest_home">
			</form>
		</td>

		<td>
			<a
				href="/siteSuggestAction.html?del=true&id=<%=_SiteSuggest.getId()%>">
				Delete </a>
		</td>
	</TR>

	<%
	    }
	%>
</TABLE>


<!-- 
<a href="#info" rel="facebox">load div inline</a> <br/>

<div id="info" style="display:none;">
<form name="ajaxSubmitForm" method="POST" action="/siteSuggestAction.html" id="ajaxSubmitForm">
	<select id="requiredField" name="category">
		<option value="">
			- Please Select -
		</option>
		<option value="problem_report">
			Problem Report
		</option>
		<option value="suggestion">
			Suggestion
		</option>
		<option value="misc">
			Misc
		</option>
	</select><br/>
	<TEXTAREA NAME="suggest" COLS="50" ROWS="8"></TEXTAREA>
	<INPUT TYPE="HIDDEN" NAME="add" value="true">
	<INPUT TYPE="HIDDEN" NAME="wpid" value="4:1970288802226266">
	<INPUT TYPE="HIDDEN" NAME="ajaxOut" value="getmodalstatus">
	<INPUT TYPE="HIDDEN" NAME="ajaxRequest" value="true">

</form>
<span id="ajaxSubmitResult"></span>
<a id="ajaxSubmit" href="javascript:sendFormAjax('/siteSuggestAction.html','ajaxSubmitForm', 'ajaxSubmit', 'ajaxSubmitResult');">Submit</a>
</div>

-->
<a href="/siteSuggestAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Test</a> 
 

