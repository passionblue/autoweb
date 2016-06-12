<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	String listAllByAdmin = request.getParameter("listAllByAdmin");
	
	boolean showListAllByAdmin = false;
	if ( sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)){
		showListAllByAdmin = true;
	}

	List list = new ArrayList();
	SweepWorldcup2014DS ds = SweepWorldcup2014DS.getInstance();    

	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

%> 

<form name="sweepWorldcup2014FormEditField_Player" method="get" action="/sweepWorldcup2014Action.html" >


	<div id="sweepWorldcup2014Form_player_field">
    <div id="sweepWorldcup2014Form_player_text" class="formFieldText" >       
        <input id="field" type="text" size="20" name="code" /> <span></span>
    </div>      
	</div><div class="clear"></div>

    <div id="sweepWorldcup2014Form_submit" class="formButton" >       
        <a id="formSubmit2" href="javascript:document.sweepWorldcup2014FormEditField_Player.submit();">Submit</a>
    </div>      

	<br> <!-- update by ajax call -->
	<br> <!-- update by ajax call -->

	<INPUT TYPE="HIDDEN" NAME="cmd" value="verify">
	<INPUT TYPE="HIDDEN" NAME="returnPage" value="sweep_worldcup2014_verify">
</form>
    
<br/>
<br/>
    