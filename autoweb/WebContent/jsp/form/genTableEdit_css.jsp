<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
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

    GenTableDataHolder _GenTable = GenTableDS.getInstance().getById(id);

    if ( _GenTable == null ) {
        return;
    }


	String retPage = (String) reqParams.get("returnPage");    
	String cancelPage = (String) reqParams.get("cancelPage");    
	if (cancelPage == null) cancelPage = "gen_table_home";

    String _countryValue=  WebUtil.display(_GenTable.getCountry());
    String _ageValue=  WebUtil.display(_GenTable.getAge());
    String _disabledValue=  WebUtil.display(_GenTable.getDisabled());
    String _commentsValue=  WebUtil.display(_GenTable.getComments());
    String _time_createdValue=  WebUtil.display(_GenTable.getTimeCreated());
    String _time_updatedValue=  WebUtil.display(_GenTable.getTimeUpdated());
    String _ext_stringValue=  WebUtil.display(_GenTable.getExtString());
    String _ext_intValue=  WebUtil.display(_GenTable.getExtInt());
%> 

<br>
<div id="genTableForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="genTableFormEdit" method="POST" action="/genTableAction.html" >


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
    <div id="genTableForm_comments_text" class="formFieldText" >       
        <span></span>
		<TEXTAREA id="field" NAME="comments" COLS="50" ROWS="8" ><%=WebUtil.display(_commentsValue)%></TEXTAREA>
    </div>      
	</div><div class="clear"></div>







	<div id="genTableForm_extString_field">
    <div id="genTableForm_extString_label" class="formLabel" >ExtraString </div>
    <div id="genTableForm_extString_text" class="formFieldText" >       
        <input id="field" type="text" size="3" name="extString" value="<%=WebUtil.display(_ext_stringValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>



	<div id="genTableForm_extInt_field">
    <div id="genTableForm_extInt_label" class="formLabel" >Ext Int </div>
    <div id="genTableForm_extInt_text" class="formFieldText" >       
        <input id="field" type="text" size="70" name="extInt" value="<%=WebUtil.display(_ext_intValue)%>"/> <span></span>
    </div>      
	</div><div class="clear"></div>

        <div id="genTableFormEdit_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.genTableFormEdit.submit();">Submit</a>
        </div>      

        <div id="styleSetContentForm_cancel" class="formCancel" >       
            <a href="/moveTo.html?dest=<%=cancelPage%>">Cancel</a>
        </div>      

<INPUT TYPE="HIDDEN" NAME="edit" value="true">
<INPUT TYPE="HIDDEN" NAME="id" value="<%=_GenTable.getId()%>">
<% if (!WebUtil.isNull(retPage)) {%>
<INPUT TYPE="HIDDEN" NAME="returnPage" value="<%=retPage%>">
<%} %>
</form>
</div> <!-- form -->
