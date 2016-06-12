<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.seox.util.*,com.autosite.db.*,com.seox.work.UserBO,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
 <br/><br/>

<%
	String uri = (String)session.getAttribute("k_org_uri");
	String query = (String)session.getAttribute("k_org_query");
	
    String ret = "";
    

    
    if (WebUtil.isNotNull(uri)) ret += uri;
    
    if (WebUtil.isNotNull(query)) {
        ret += "?" + query;
    }
	
	
%>

<div id="registerSimpleForm" class="formFrame">
<div style="font-size:12px;color:red;margin-left:170px;">* Required fields</div>
<form name="loginFormSubmitForm" method="get" action="/loginFormSubmit.html" >

	<div id="registerSimpleForm_username_field">
    <div id="registerSimpleForm_username_label" class="formRequiredLabel" >Username* </div>
    <div id="registerSimpleForm_username_text" class="formFieldText" > <span></span>     
        <input id="requiredField" type="text" style="width: 150px;" name="username" value=""/> 
    </div>      
	</div><div class="clear"></div>


	<div id="registerSimpleForm_password_field">
    <div id="registerSimpleForm_password_label" class="formRequiredLabel" >Password* </div>
    <div id="registerSimpleForm_password_text" class="formFieldText" >  <span></span>      
        <input id="requiredField" type="password" style="width: 150px;" name="password" value=""/>
    </div>      
	</div><div class="clear"></div>

        <div id="registerSimpleForm_submit" class="formSubmit" >       
            <a id="formSubmit" href="javascript:document.loginFormSubmitForm.submit();">Submit</a>
        </div>      

        <input id="requiredField" type="hidden" name="fwdTo" value="<%=ret %>"/>

</form>
</div> <!-- form -->
	