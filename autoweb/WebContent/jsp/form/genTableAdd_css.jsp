<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    GenTableDataHolder _GenTableDefault = new GenTableDataHolder();// GenTableDS.getInstance().getDeafult();
    
    String _countryValue= (reqParams.get("country")==null?WebUtil.display(_GenTableDefault.getCountry()):WebUtil.display((String)reqParams.get("country")));
    String _ageValue= (reqParams.get("age")==null?WebUtil.display(_GenTableDefault.getAge()):WebUtil.display((String)reqParams.get("age")));
    String _disabledValue= (reqParams.get("disabled")==null?WebUtil.display(_GenTableDefault.getDisabled()):WebUtil.display((String)reqParams.get("disabled")));
    String _commentsValue= (reqParams.get("comments")==null?WebUtil.display(_GenTableDefault.getComments()):WebUtil.display((String)reqParams.get("comments")));
    String _time_createdValue= (reqParams.get("timeCreated")==null?WebUtil.display(_GenTableDefault.getTimeCreated()):WebUtil.display((String)reqParams.get("timeCreated")));
    String _time_updatedValue= (reqParams.get("timeUpdated")==null?WebUtil.display(_GenTableDefault.getTimeUpdated()):WebUtil.display((String)reqParams.get("timeUpdated")));
    String _ext_stringValue= (reqParams.get("extString")==null?WebUtil.display(_GenTableDefault.getExtString()):WebUtil.display((String)reqParams.get("extString")));
    String _ext_intValue= (reqParams.get("extInt")==null?WebUtil.display(_GenTableDefault.getExtInt()):WebUtil.display((String)reqParams.get("extInt")));

	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "gen_table_home";

    String _wpId = WebProcManager.registerWebProcess();

%> 

<br>
<div id="genTableForm_topArea" class="formTopArea"></div>
<div id="genTableForm" class="formFrame">
<div class="fieldSideText" >* Required fields</div>
<form name="genTableForm" method="POST" action="/genTableAction.html" >



	<div id="genTableForm_country_field">
    <div id="genTableForm_country_label" class="formLabel" >Country </div>
    <div id="genTableForm_country_dropdown" class="formFieldDropDown" >       
        <select id="field" name="country">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _countryValue)%>>XX</option-->
        <option value="usa" <%=HtmlUtil.getOptionSelect("usa", _countryValue)%>>United States</option>
        <option value="korea" <%=HtmlUtil.getOptionSelect("korea", _countryValue)%>>SouthKorea</option>
        <option value="congo" <%=HtmlUtil.getOptionSelect("congo", _countryValue)%>>Congo</option>
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="genTableForm_age_field">
    <div id="genTableForm_age_label" class="formLabel" >Age </div>
    <div id="genTableForm_age_dropdown" class="formFieldDropDown" >       
        <select id="field" name="age">
        <option value="" >- Please Select -</option>
        <!--option value="XX" <%=HtmlUtil.getOptionSelect("XX", _ageValue)%>>XX</option-->
        </select>  <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="genTableForm_disabled_field">
    <div id="genTableForm_disabled_label" class="formLabel" >Disabled </div>
    <div id="genTableForm_disabled_dropdown" class="formFieldDropDown" >       
        <select name="disabled">
        <option value="0" <%=HtmlUtil.getOptionSelect("0", _disabledValue)%>>No</option>
        <option value="1" <%=HtmlUtil.getOptionSelect("1", _disabledValue)%>>Yes</option>
        </select>
    </div>      
	</div><div class="clear"></div>




	<div id="genTableForm_comments_field">
    <div id="genTableForm_comments_label" class="formLabel" >Comments </div>
    <div id="genTableForm_comments_textarea" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="comments" COLS="50" ROWS="8" ><%=WebUtil.display(_commentsValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>













	<div id="genTableForm_extString_field">
    <div id="genTableForm_extString_label" class="formLabel" >ExtraString </div>
    <div id="genTableForm_extString_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="3" name="extString" value="<%=WebUtil.display(_ext_stringValue)%>"/>
    </div>      
	</div><div class="clear"></div>




	<div id="genTableForm_extInt_field">
    <div id="genTableForm_extInt_label" class="formLabel" >Ext Int </div>
    <div id="genTableForm_extInt_text" class="formFieldText" > <span></span>      
        <input id="field" type="text" size="70" name="extInt" value="<%=WebUtil.display(_ext_intValue)%>"/>
    </div>      
	</div><div class="clear"></div>

        <div id="genTableForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.genTableForm.submit();">Submit</a>
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
<div id="genTableForm_bottomArea" class="formBottomArea"></div>


<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">
<%
    if (false) return;

    List list = GenTableDS.getInstance().getBySiteId(site.getId());
 
    for(Iterator iter = list.iterator();iter.hasNext();) {
        GenTableDataHolder _GenTable = (GenTableDataHolder) iter.next();
%>
<TR bgcolor="#ffffff" valign="top">

    <td> <%= _GenTable.getId() %> </td>

    <td> <%= WebUtil.display(_GenTable.getCountry()) %></td>
    <td> <%= WebUtil.display(_GenTable.getAge()) %></td>
    <td> <%= WebUtil.display(_GenTable.getDisabled()) %></td>
    <td> <%= WebUtil.display(_GenTable.getComments()) %></td>
    <td> <%= WebUtil.display(_GenTable.getTimeCreated()) %></td>
    <td> <%= WebUtil.display(_GenTable.getTimeUpdated()) %></td>
    <td> <%= WebUtil.display(_GenTable.getExtString()) %></td>
    <td> <%= WebUtil.display(_GenTable.getExtInt()) %></td>


<td>
<form name="genTableForm<%=_GenTable.getId()%>2" method="get" action="/v_gen_table_edit2.html" >
    <a href="javascript:document.genTableForm<%=_GenTable.getId()%>2.submit();">Edit</a>           
    <INPUT TYPE="HIDDEN" NAME="id" value="<%= _GenTable.getId() %>">
</form>

</td>
<td>
<a href="/genTableAction.html?del=true&id=<%=_GenTable.getId()%>"> Delete </a>
</td>
</TR>

<%
    }
%>
</TABLE>