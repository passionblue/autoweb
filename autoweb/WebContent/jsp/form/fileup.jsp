<%@page language="java" import="com.jtrend.session.*,com.jtrend.util.*,com.autosite.ds.*,com.autosite.db.*,com.autosite.content.*,java.util.*,com.autosite.util.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<%
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	
	List uploads = ResourceListDS.getInstance().getBySiteId(site.getId());
%>

<FORM Name="fileupForm" ACTION="/fileup.html" ENCTYPE="multipart/form-data" METHOD=POST id="fileupForm" enctype="multipart/form-data">

	<input type="submit" value=Submit>
	<INPUT id="fileupFile" TYPE="FILE" NAME="formFile"><br>
	<input id="fileupType" type="radio" name="fileType" value="0"> Other
	<input id="fileupType" type="radio" name="fileType" value="1"> Image
	<input id="fileupType" type="radio" name="fileType" value="2"> Text File
</FORM>

<a id="ajaxSubmit" href="javascript:sendFormAjax('/fileup.html','fileupForm', 'ajaxSubmit', 'ajaxSubmitResult');">Submit</a>

<a id="ajaxSubmit" href="javascript:sendFileSubmit('/fileup.html');">Submit</a>

<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

<%
	String webRoot = UploadResourceManager.Instance().getWebRoot();
	for(Iterator iter = uploads.iterator();iter.hasNext();) {
		ResourceList _resourceList = (ResourceList) iter.next();

%>

	<TR bgcolor="#ffffff" valign="top">
		<td> <%= WebUtil.display(_resourceList.getOriginalName())%> </td>
		<td> <%= WebUtil.display(_resourceList.getUrl())%> </td>
		<td> <%= WebUtil.display(UploadResourceManager.Instance().getResourceTypeString(_resourceList.getResourceType()))%> </td>
		<td> <%= WebUtil.display(_resourceList.getImageWidth())%> </td>
		<td> <%= WebUtil.display(_resourceList.getImageHight())%> </td>
		<td> <%= WebUtil.display(_resourceList.getTimeUpdated())%> </td>
		<td><a href="/resourceListAction.html?del=true&id=<%=_resourceList.getId()%>"> Delete </a> </td>
	</TR>

<%  if ( _resourceList.getResourceType() == 1 ) {%>
	<TR >
		<TD colspan="7">
			<img width="200" and height="200" src="<%=webRoot +"/"+_resourceList.getUrl() %>"/>
		</TD>
	</TR>
		
<% } %>		


<% } %>	
	
</TABLE>

<%-- ===================================================================================================================== --%>
<%-- ===================================================================================================================== --%>
<%-- ===================================================================================================================== --%>

<br>
<br>

<div id="container">
     <div id="header"><div id="header_left"></div>
     <div id="header_main">Max's AJAX File Uploader</div><div id="header_right"></div></div>

     <div id="content">
         <form action="/fileup.html" method="post" enctype="multipart/form-data" target="upload_target" onsubmit="startUpload();" >
            <p id="f1_upload_process">Loading...<br/><img src="/js/ajaxupload/loader.gif" /><br/></p>
            <p id="f1_upload_form" align="center">
            	<input type="submit" name="submitBtn" class="sbtn" value="Upload" /><input name="formFile" type="file" size="30" />
            </p>
			<INPUT TYPE="HIDDEN" NAME="ajx" value="true">
			<INPUT TYPE="HIDDEN" NAME="upload" value="true">
			<INPUT TYPE="HIDDEN" NAME="ajaxOut" value="fileloadstop">
            
            <iframe id="upload_target" name="upload_target" src="#" style="width:0;height:0;border:0px solid #fff;" onLoad='uploadDone("upload_target")'>
            </iframe>
        </form>
    </div>
    <div id="footer"><a href="http://www.ajaxf1.com" target="_blank">Powered by AJAX F1</a></div>
</div>
                 
<script language="javascript" type="text/javascript">
<!--
function startUpload(){
      document.getElementById('f1_upload_process').style.visibility = 'visible';
      document.getElementById('f1_upload_form').style.visibility = 'hidden';
      return true;
}

function stopUpload(success){
	  alert('');
      var result = '';
      if (success == 1){
         result = '<span class="msg">The file was uploaded successfully!<\/span><br/><br/>';
      }
      else {
         result = '<span class="emsg">There was an error during file upload!<\/span><br/><br/>';
      }
      document.getElementById('f1_upload_process').style.visibility = 'hidden';
      document.getElementById('f1_upload_form').innerHTML = result + '<label>File: <input name="myfile" type="file" size="30" /><\/label><label><input type="submit" name="submitBtn" class="sbtn" value="Upload" /><\/label>';
      document.getElementById('f1_upload_form').style.visibility = 'visible';      
      return true;   
}

function uploadDone(name) {
	alert(name);
	var f = document.getElementById(name);
	if (f){
		  alert(f.innerHTML);//TODO
	      document.getElementById('f1_upload_process').style.visibility = 'hidden';
	      //document.getElementById('f1_upload_form').innerHTML = result + '<label>File: <input name="myfile" type="file" size="30" /><\/label><label><input type="submit" name="submitBtn" class="sbtn" value="Upload" /><\/label>';
	      document.getElementById('f1_upload_form').style.visibility = 'visible';      
	}
	return true;
}
//-->
</script> 

<hr>

<%-- ===================================================================================================================== --%>
<%-- ===================================================================================================================== --%>
<%-- ===================================================================================================================== --%>

<h1>http://malsup.com/jquery/form/#file-upload <a href="http://malsup.com/jquery/form/#file-upload"> click</a></h1>

<style>
	.form { display: block; margin: 20px auto; background: #eee; border-radius: 10px; padding: 15px }
	.progress { position:relative; width:400px; border: 1px solid #ddd; padding: 1px; border-radius: 3px; }
	.bar { background-color: #B4F5B4; width:0%; height:20px; border-radius: 3px; }
	.percent { position:absolute; display:inline-block; top:3px; left:48%; }
</style>


<form action="/fileup.html" method="post" enctype="multipart/form-data">
    <input type="file" name="formFile"><br>
    <input type="submit" value="Upload File to Server">
	<input TYPE="HIDDEN" NAME="ajx" value="true">
	<input TYPE="HIDDEN" NAME="upload" value="true">
	<input TYPE="HIDDEN" NAME="ajaxOut" value="fileloadstop">
</form>

<div class="progress">
	<div class="bar"></div >
	<div class="percent">0%</div >
</div>

<script>
(function() {
    
var bar = $('.bar');
var percent = $('.percent');
var status = $('#status');
   
$('form').ajaxForm({
    beforeSend: function() {
        status.empty();
        var percentVal = '0%';
        bar.width(percentVal)
        percent.html(percentVal);
    },
    uploadProgress: function(event, position, total, percentComplete) {
        var percentVal = percentComplete + '%';
        bar.width(percentVal)
        percent.html(percentVal);
    },
	complete: function(xhr) {
		status.html(xhr.responseText);
        bar.width('100%');
        percent.html('100%');
	}
}); 

})();       
</script>

<%-- ===================================================================================================================== --%>
<%-- ===================================================================================================================== --%>
<%-- ===================================================================================================================== --%>



