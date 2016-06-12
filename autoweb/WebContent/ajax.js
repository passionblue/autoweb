$(document).ready(function(){
   $("#ajaxSubmit2").click(function(){
	   alert("x");
	   var parms = get(document.getElementById('ajaxSubmitForm'));
	   
	   alert(parms);
	   $.ajax({
		   type: "POST",
		   url: "/siteSuggestAction.html",
		   data: parms,
		   success:
			   
			   function(msg) {
		   		alert(msg);
		   		
		   		$("#ajaxSubmitForm").fadeOut(500);
		   		$("#ajaxSubmit").hide();
		   		
//		   		$('#contact_form').html("<div id='message'></div>");
//		        $('#message').html("<h2>Contact Form Submitted!</h2>")
//		        .append("<p>We will be in touch soon.</p>")
//		        .hide()
//		        .fadeIn(1500, function() {
//		          $('#message').append("<img id='checkmark' src='images/check.png' />");
//		        });
	   		}
	   });
	   e.preventDefault();
   });

   //== Based on ec_main formSubmit but controls supposed to have class instead of ID access
   var checkRequired = "true";
   $("a#formSubmit_ajax").each(function (f) {
	   
	   $(this).click(function(e){
		   if (checkRequired != "true") {
			   return;
		   }
		   
		   var clickForm = $(this).parent().parent().attr("name")
		   
		   $("span").text("");
		   $("input[type=text].requiredField").each(function(u){
			   var checkForm = $(this).parent().parent().parent().attr("name");
			   if (clickForm !=  checkForm) return;
			   $(this).css("background-color","transparent");
		   });
		   $("input[type=checkbox].requiredField").each(function(u){
			   var checkForm = $(this).parent().parent().parent().attr("name");
			   if (clickForm !=  checkForm) return;
			   $(this).css("background-color","transparent");
		   });
		   $("input[type=password].requiredField").each(function(u){
			   $(this).css("background-color","transparent");
		   });
		   
		   $("textarea.requiredField").each(function(u){
			   $(this).css("background-color","transparent");
			   
		   });
		   $("select.requiredField").each(function(u){
			   $(this).css("background-color","transparent");
		   });

		   $("input[type=text].requiredField").each(function(i){

			   var checkForm = $(this).parent().parent().parent().attr("name");
			   if (clickForm !=  checkForm) return;
			   
			   if (jQuery.trim($(this).val())== ""){
				   $(this).css("background-color","orange");
				   $(this).parent().children("span").css("color", "red");
				   $(this).parent().children("span").css("font", "normal normal bold 11px verdana");
				   $(this).parent().children("span").html("  REQUIRED<br>");
				   e.preventDefault();
				   return;
			   }
		   });

		   $("input[type=password].requiredField").each(function(i){

			   var checkForm = $(this).parent().parent().parent().attr("name");
			   if (clickForm !=  checkForm) return;
			   
			   if (jQuery.trim($(this).val())== ""){
				   $(this).css("background-color","orange");
				   $(this).parent().children("span").css("color", "red");
				   $(this).parent().children("span").css("font", "normal normal bold 11px verdana");
				   $(this).parent().children("span").html("  REQUIRED<br>");
				   e.preventDefault();
				   return;
			   }
		   });
		   
		   
		   $("input[type=checkbox].requiredField").each(function(i){
			   var checkForm = $(this).parent().parent().parent().attr("name");
			   if (clickForm !=  checkForm) return;

			   if (!$(this).attr('checked')){
				   $(this).css("background-color","orange");
				   $(this).parent().children("span").css("color", "red");
				   $(this).parent().children("span").css("font", "normal normal bold 11px verdana");
				   $(this).parent().children("span").html("  REQUIRED<br>");
				   e.preventDefault();
				   return;
			   }
		   });
		   
		   $("textarea.requiredField").each(function(i){
			   var checkForm = $(this).parent().parent().parent().attr("name");
			   if (clickForm !=  checkForm) return;

			   if (jQuery.trim($(this).val())== ""){
				   $(this).css("background-color","orange");
				   $(this).parent().children("span").css("color", "red");
				   $(this).parent().children("span").css("font", "normal normal bold 11px verdana");
				   $(this).parent().children("span").html("  REQUIRED<br/>");
				   e.preventDefault();
				   return;
			   }
		   });
		   $("select.requiredField").each(function(i){
			   var checkForm = $(this).parent().parent().parent().attr("name");
			   if (clickForm !=  checkForm) return;

			   if (jQuery.trim($(this).val())== ""){
				   $(this).css("background-color","orange");
				   $(this).parent().children("span").css("color", "red");
				   $(this).parent().children("span").css("font", "normal normal bold 11px verdana");
				   $(this).parent().children("span").html("  REQUIRED<br/>");
				   e.preventDefault();
				   return;
			   }
		   });
	   });
   });
   
   $("a#formSubmit_ajax_simpleform").each(function (f) {
	   
	   $(this).click(function(e){
		   
		   $("span").text("");
		   $("input[type=text].requiredField").each(function(u){
			   $(this).css("background-color","transparent");
		   });
		   $("input[type=checkbox].requiredField").each(function(u){
			   $(this).css("background-color","transparent");
		   });
		   $("input[type=password].requiredField").each(function(u){
			   $(this).css("background-color","transparent");
		   });
		   $("textarea.requiredField").each(function(u){
			   $(this).css("background-color","transparent");
			   
		   });
		   $("select.requiredField").each(function(u){
			   $(this).css("background-color","transparent");
		   });

		   var formName = $(this).parent().attr("name");
		   
		   $("input[type=text].requiredField").each(function(i){
			
			   if ($(this).parent().attr("name") != formName) return;
			   
			   if (jQuery.trim($(this).val())== ""){
				   $(this).css("background-color","orange");
				   $(this).next("span").css("color", "red");
				   $(this).next("span").css("font", "normal normal bold 11px verdana");
				   $(this).next("span").text("  REQUIRED");
				   e.preventDefault();
				   return;
			   }
		   });

		   $("input[type=password].requiredField").each(function(i){

			   if ($(this).parent().attr("name") != formName) return;
			   
			   if (jQuery.trim($(this).val())== ""){
				   $(this).css("background-color","orange");
				   $(this).next("span").css("color", "red");
				   $(this).next("span").css("font", "normal normal bold 11px verdana");
				   $(this).next("span").text("  REQUIRED");
				   e.preventDefault();
				   return;
			   }
		   });
		   
		   $("input[type=radio].requiredField").each(function(i){

			   if ($(this).parent().attr("name") != formName) return;
			   
			   if (jQuery.trim($(this).val())== ""){
				   $(this).css("background-color","orange");
				   $(this).next("span").css("color", "red");
				   $(this).next("span").css("font", "normal normal bold 11px verdana");
				   $(this).next("span").text("  REQUIRED");
				   e.preventDefault();
				   return;
			   }
		   });
		   
		   
		   $("input[type=checkbox].requiredField").each(function(i){
			   if ($(this).parent().attr("name") != formName) return;

			   if (!$(this).attr('checked')){
				   $(this).css("background-color","orange");
				   $(this).next("span").css("color", "red");
				   $(this).next("span").css("font", "normal normal bold 11px verdana");
				   $(this).next("span").text("  REQUIRED");
				   e.preventDefault();
				   return;
			   }
		   });
		   
		   $("textarea.requiredField").each(function(i){
			   if ($(this).parent().attr("name") != formName) return;

			   if (jQuery.trim($(this).val())== ""){
				   $(this).css("background-color","orange");
				   $(this).next("span").css("color", "red");
				   $(this).next("span").css("font", "normal normal bold 11px verdana");
				   $(this).next("span").text("  REQUIRED");
				   e.preventDefault();
				   return;
			   }
		   });
		   $("select.requiredField").each(function(i){
			   if ($(this).parent().attr("name") != formName) return;

			   if (jQuery.trim($(this).val())== ""){
				   $(this).css("background-color","orange");
				   $(this).next("span").css("color", "red");
				   $(this).next("span").css("font", "normal normal bold 11px verdana");
				   $(this).next("span").text("  REQUIRED");
				   e.preventDefault();
				   return;
			   }
		   });
	   });
   });
   
   $('#uploadifySubmit').uploadify({
	   'uploader': '/js/uploadify/uploadify.swf',
	   'script': '/fileup.html',
	   'cancelImg': '/js/uploadify/cancel.png',
	   'multi'          : false,
	   'auto'           : false
	});   

   	$("#fileInput1").uploadify({
 	   'uploader': '/js/uploadify/uploadify.swf',
	   'script': '/fileup.html',
	   'cancelImg': '/js/uploadify/cancel.png',
		'folder'         : '/_uploads',
		'multi'          : false
   	});
   
   	//================================================================
   	// Table 	
   	//================================================================
//   	$("a#cellEditLoad").click(function(e){
//
//   		var oldValue = $(this).parent().text();
//   		alert(oldValue);
//   		var cellEditFormText = "<form id=\"\" name=\"\">";
//   		cellEditFormText +="<input type=\"text\" name=\"\" value=\""+oldValue+"\">";
//   		cellEditFormText +="</form>";
//   		cellEditFormText +="<a href=\"#\">Update</a>|";
//   		cellEditFormText +="<a id=\"cellEditCancel\" href=\"#\">Cancel</a>";
//   		
//   		alert(cellEditFormText);
//   		$(this).parent().html(cellEditFormText);
//
//   		// This will cancel update and restore to the old value.
//   	   	$("a#cellEditCancel").click(function(e){
//   	   		//alert("");
//   	   		$(this).parent().html(oldValue + "<a id=\"cellLoadEdit\" href=\"#\" ><img src=\"/images/icons/led/map.png\" style=\"float:right\"/></a>");
//   	   		e.preventDefault();
//   	   	});
//   		
//   		e.preventDefault();
//   	});

   	$("a#cellEditLoad").click(function(e){

   		$(this).parent().hide();
   		$(this).parent().next("div").slideDown(500);
   		e.preventDefault();
   	});
   	
   	$("a#cellEditCancel").click(function(e){

   		$(this).parent().hide();
   		$(this).parent().prev("div").slideDown(500);
   		
   		e.preventDefault();
   	});
   	
   	$(".generalDeleteItem").click(function(e){
   		var menuId = $(this).parent().parent().attr("rel");
   		sendFormAjaxSimple('/menuItemAction.html?ajaxRequest=true&ajaxOut=getmodalstatus&del=true&id=' + menuId, false, callback_delete_parent_parent, this);
   		e.preventDefault();
   	});
   	
});

//================================================================
//================================================================
//================================================================

function callback_delete(obj){
	if(obj) $(obj).remove();
}

function callback_delete_parent(obj){
	if(obj) $(obj).parent().remove();
}

function callback_delete_parent_parent(obj){
	if(obj) $(obj).parent().parent().remove();
}

//================================================================
// example <form action="javascript:get(document.getElementById('myform'));" name="myform" id="myform">
// http://www.captain.at/howto-ajax-form-post-get.php
//================================================================

function get(obj) {
	var getstr = "";
    for (i=0; i<obj.childNodes.length; i++) {
       if (obj.childNodes[i].tagName == "INPUT") {
           if (obj.childNodes[i].type == "text") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "password") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "hidden") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "file") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
          if (obj.childNodes[i].type == "checkbox") {
             if (obj.childNodes[i].checked) {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
             } else {
                getstr += obj.childNodes[i].name + "=&";
             }
          }
          if (obj.childNodes[i].type == "radio") {
             if (obj.childNodes[i].checked) {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
             }
          }
       }   
       if (obj.childNodes[i].tagName == "SELECT") {
           var sel = obj.childNodes[i];
           getstr += sel.name + "=" + sel.options[sel.selectedIndex].value + "&";
        }
       if (obj.childNodes[i].tagName == "TEXTAREA") {
           getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
        }
    }
	//alert(getstr);
    
    return getstr;
}



function get2(obj) {
	var getstr = "";
    for (i=0; i<obj.childNodes.length; i++) {
       if (obj.childNodes[i].tagName == "INPUT") {
           if (obj.childNodes[i].type == "text") {
               getstr += obj.childNodes[i].name + "=" + escape(obj.childNodes[i].value) + "&";
           }
           if (obj.childNodes[i].type == "password") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "hidden") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "file") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
          if (obj.childNodes[i].type == "checkbox") {
             if (obj.childNodes[i].checked) {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
             } else {
                getstr += obj.childNodes[i].name + "=&";
             }
          }
          if (obj.childNodes[i].type == "radio") {
             if (obj.childNodes[i].checked) {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
             }
          }
       }   
       if (obj.childNodes[i].tagName == "SELECT") {
           var sel = obj.childNodes[i];
           getstr += sel.name + "=" + sel.options[sel.selectedIndex].value + "&";
        }
       if (obj.childNodes[i].tagName == "TEXTAREA") {
           getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
        }
    }
	//alert(getstr);
    
    return getstr;
}

//recursively find 

function findAndGet(obj){
	
	var getstr = "";
	if (obj.tagName == "INPUT") {
        if (obj.type == "text") {
            getstr = obj.name + "=" + escape(obj.value) + "&";
        } else if (obj.type == "password") {
            getstr = obj.name + "=" + obj.value + "&";
        } else if (obj.type == "hidden") {
            getstr = obj.name + "=" + obj.value + "&";
        } else if (obj.type == "file") {
            getstr = obj.name + "=" + obj.value + "&";
        } else if (obj.type == "checkbox") {
          if (obj.checked) {
             getstr = obj.name + "=" + obj.value + "&";
          } else {
             getstr = obj.name + "=&";
          }
       } else if (obj.type == "radio") {
          if (obj.checked) {
             getstr = obj.name + "=" + obj.value + "&";
          }
       }
	} else if (obj.tagName == "SELECT") {
        var sel = obj.childNodes[i];
        getstr = sel.name + "=" + sel.options[sel.selectedIndex].value + "&";
	} else if (obj.tagName == "BR") {
		return "";
	} else if (obj.tagName == "TEXTAREA") {
        getstr = obj.name + "=" + obj.value + "&";
	} else if (obj.tagName == "DIV") {
    	 if ( obj.childNodes.length > 0 ) {
    		 for (i=0; i<obj.childNodes.length; i++) {
    		  	getstr += findAndGet(obj.childNodes[i]);
    		 }
    	 }
    	 else 
    		 getstr = "";
	} 
	
	return getstr;
}



// fields are under div 
function get3(obj) {
	var getstr = "";
    for (i=0; i<obj.childNodes.length; i++) {
    	alert(obj.childNodes[i].tagName);
    	getstr += findAndGet(obj.childNodes[i]);

/*    	
    	if (obj.childNodes[i].tagName == "INPUT") {
           if (obj.childNodes[i].type == "text") {
               getstr += obj.childNodes[i].name + "=" + escape(obj.childNodes[i].value) + "&";
           }
           if (obj.childNodes[i].type == "password") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "hidden") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
           if (obj.childNodes[i].type == "file") {
               getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
           }
          if (obj.childNodes[i].type == "checkbox") {
             if (obj.childNodes[i].checked) {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
             } else {
                getstr += obj.childNodes[i].name + "=&";
             }
          }
          if (obj.childNodes[i].type == "radio") {
             if (obj.childNodes[i].checked) {
                getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
             }
          }
       } else if (obj.childNodes[i].tagName == "SELECT") {
           var sel = obj.childNodes[i];
           getstr += sel.name + "=" + sel.options[sel.selectedIndex].value + "&";
        } else if (obj.childNodes[i].tagName == "TEXTAREA") {
           getstr += obj.childNodes[i].name + "=" + obj.childNodes[i].value + "&";
        } else if (obj.childNodes[i].tagName == "DIV") {
        	getstr += findAndGet(obj.cheldNodes[i]);
        }
*/        
    }
    alert(getstr);
    return getstr;
}

function  animateFade(lastTick, eid, timeToFade)
{  
  var curTick = new Date().getTime();
  var elapsedTicks = curTick - lastTick;
 
  var element = document.getElementById(eid);
 
  if(element.FadeTimeLeft <= elapsedTicks)
  {
    element.style.opacity = element.FadeState == 1 ? '1' : '0';
    element.style.filter = 'alpha(opacity = '
        + (element.FadeState == 1 ? '100' : '0') + ')';
    element.FadeState = element.FadeState == 1 ? 2 : -2;
	document.getElementById(eid).style.display = 'none';
	element.callbackAfter();
    return;
  }
 
  element.FadeTimeLeft -= elapsedTicks;
  var newOpVal = element.FadeTimeLeft/timeToFade;
  if(element.FadeState == 1)
    newOpVal = 1 - newOpVal;

  element.style.opacity = newOpVal;
  element.style.filter = 'alpha(opacity = ' + (newOpVal*100) + ')';
 
  setTimeout("animateFade(" + curTick + ",'" + eid + "','" + timeToFade + "')", 33);
}

//var  TimeToFade = 1000.0;

function fade(eid, timeToFade, callback)
{
  var element = document.getElementById(eid);
  if(element == null)
    return;
   
  if(element.FadeState == null)
  {
    if(element.style.opacity == null
        || element.style.opacity == ''
        || element.style.opacity == '1')
    {
      element.FadeState = 2;
    }
    else
    {
      element.FadeState = -2;
    }
  }
   
  if(element.FadeState == 1 || element.FadeState == -1)
  {
    element.FadeState = element.FadeState == 1 ? -1 : 1;
    element.FadeTimeLeft = timeToFade - element.FadeTimeLeft;
  }
  else
  {
    element.FadeState = element.FadeState == 2 ? -1 : 1;
    element.FadeTimeLeft = timeToFade;
    element.callbackAfter = callback;
    setTimeout("animateFade(" + new Date().getTime() + ",'" + eid + "','" + timeToFade + "')", 33);
  }  
}

// this is used in a from the dynamic loading from ajax and sending via ajax. 
function sendFormAjax(url, formName, submitName, resultDisplayId, callback) {
	
	alert("send " + formName);
	var obj = document.getElementById(formName);
	if (obj == null ) return;
	alert("send " + formName);
	
	var parms = get2(obj);
	alert(parms);
	
	$.ajax({
		type: "POST",
		url: url,
		data: parms,
		contentType: "application/x-www-form-urlencoded;charset=ISO-8859-15",
		success: 
			function(msg) {
				if ( submitName ) {
					$("#" + submitName).hide();
				}
				
				$(obj).fadeOut(1000, function () {
					$("#" + resultDisplayId).hide().text(msg).fadeIn(1000);
				});
				if (callback) callback(msg);
		   	}
	});
}

function sendFormAjaxSimple(url, showResultInPopup, callback, callBackParams) {
	//alert(url);
	$.ajax({
		type: "GET",
		url: url,
		success:
			function(msg) {
			if (showResultInPopup){
				alert(msg);
			}
			if (callback != null) {
				if (callBackParams != null)
					callback(callBackParams);
				else 
					callback(msg);
			}
	   	}
	});
}

//=============================================================================================================
// Submitting the file 
//=============================================================================================================

function sendFileSubmit(url, forid) {

	var filename = $("form#"+ forid+" #fileNameOption").val();
	var file = $("form#"+ forid+" #fileupFile").val();
	//var type = $("form#"+ forid+" input[@id='fileupType']:checked").val();
	var parm= "formFile=" + file + "&fileType=1";

	
	$.ajax({
		type: "POST",
		url: url,
		data: parm,
		success: function(msg) {
			alert(msg);
	   	}
	});
}

//function ajaxFileUpload(fileupUrl, elementId)
//{
//	
//	//starting setting some animation when the ajax starts and completes
////	if (document.getElementById("loading") != null){
////		$("#loading")
////			.ajaxStart(function(){
////				$(this).show();
////			})
////			.ajaxComplete(function(){
////				$(this).hide();
////		});
////	}
//	
//	$.ajaxFileUpload
//	(
//		{
//			url:fileupUrl, 
//			secureuri:false,
//			fileElementId: elementId,
//			dataType: 'json',
//			success: function (data, status)
//			{
//				alert(data);
//				if(typeof(data.error) != 'undefined')
//				{
//					if(data.error != '')
//					{
//						alert(data.error);
//					}else
//					{
//						alert(data.msg);
//					}
//				}
//			},
//			error: function (data, status, e)
//			{
//				alert(e);
//			}
//		}
//	)
//	
//	return false;
// 
//}  


//=============================================================================================================
//For Generated Ajax Forms 
//=============================================================================================================
// Following functions are created for generated forms of ajax pages. 
// So do not use for other purpose. It expects special id format for the proper use. 

// this button is used in ajax sampler does not fade out. 
// This one sends and receives in JSON. Once data received, it callsback the called. 
function sendFormAjax2(url, formName, submitName, appPrefix, dataCallback, formParamCallback) {
	
	//alert("send " + formName);
	var obj = document.getElementById(formName);
	if (obj == null ) {
		alert("Internal Error: Form has invalid object. Abroting the current request");
		return;
	}
	
	var parms = "";
	if (formParamCallback != null)
		parms = formParamCallback();
	else 
		parms = getDefaultFormValues();
	
	parms = parms + "ajaxRequest=true&ajaxOut=getjson&ajaxOutArg=last&add=true" 
	//alert(parms);

	$.getJSON(url+"?" + parms, function(data){
		
		if (dataCallback != null)
			dataCallback(data);
		else {
			//alert("received id " + data.id);
	
			var html = "";
			html = "<div id='"+appPrefix+"Frame"+data.id+"' style=\"display:none;\">";
			$.each(data.fields, function(i,field){
				html += "<div id='"+field.name+"' >";
				html += field.value;
			    html +="</div>";
			});
	
			html +="<a id=\""+appPrefix+"\" href=\"javascript:deleteThis('/"+appPrefix+"Action.html'," + data.id +",'"+appPrefix+"Frame" + data.id +"' );\" >";
			html +="<img src=\"/images/icons/led/cancel.png\" />";
			html +="</a>";
		    html +="</div>";
		    $("#"+appPrefix+"List").append(html);
		    $("#"+appPrefix+"Frame"+data.id).fadeIn(2000);
		}
    	//TODO should clear the form.
	});
}

// Genera function to delete a component. 
// id is the id to be deleted. deleteTagId is id - usually frame div - to be removed from the view if got deleted from the server. 
function deleteThis(deleteUrl, id, deleteTagId) {
	$.ajax({
		type: "GET",
		url: deleteUrl+"?del=true&id=" + id + "&ajaxRequest=true&ajaxOut=getmodalstatus",
		success:
			function(msg) {
				$("#"+ deleteTagId ).fadeOut(1000, function () {
					$(this).remove();
				});
	   		}
	});
}

// It reads the form fields specially created for ajax purpose. So the all names should starts with "_ffd_" for the scanning. 
// Then returns parameter string for other use. 

function getDefaultFormValues() {

	var getstr = "";

	$("input[id^='_ffd_']").each(function(){
		getstr += $(this).attr("name")+"="+ $(this).val() + "&";
	});		
	
	$("textarea[id^='_ffd_']").each(function(){
		getstr += $(this).attr("name")+"="+ $(this).val() + "&";
	});		

	$("select[id^='_ffd_']").each(function(){
		getstr += $(this).attr("name")+"="+ $(this).val() + "&";
	});		
	//alert(getstr);
    return getstr;
}

//=============================================================================================================
// non-jquery ajax submission.
//=============================================================================================================

function xmlhttpPost(formName, target, dispElementId, dispFieldList,  callback) {
	
	if (document.getElementById(formName) == null){
		alert("Client side Error occurred. Please try again.")
		return;
	}
	
	var parms = get(document.getElementById(formName));
	parms += "&ajaxRequest=true&ajaxOut=getlisthtml&ajaxOutArg=last&formfieldlist="+dispFieldList;
	
    var xmlHttpReq = false;
    var self = this;
    
    // Mozilla/Safari
    if (window.XMLHttpRequest) {
        self.xmlHttpReq = new XMLHttpRequest();
    }
    // IE
    else if (window.ActiveXObject) {
        self.xmlHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
    }
    var strURL = target+ "?" + parms;
    alert(strURL);
    
    self.xmlHttpReq.open('POST', target, true);
    self.xmlHttpReq.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    self.xmlHttpReq.onreadystatechange = function() {
    	if (self.xmlHttpReq.readyState == 4) {
        	//alert(self.xmlHttpReq.responseText);
        	fade(formName, 1000, function() {
        		if (callback == null )
        			updatepage(dispElementId, "resultDisplay", self.xmlHttpReq.responseText);
        		else
        			callback(self.xmlHttpReq.responseText);
        	});
        }
    }
    self.xmlHttpReq.send(parms);
}

function updatepage(eid, str){
	document.getElementById(eid).innerHTML = str;
}


//==================================================================================
// Proprietary AJAX reply Processing functions for get2 ajax 
//==================================================================================

//returns true/false. reply starts with error or success.
// In get2 implementation, reply would be [success|error]:<message text>
//

var _successCode = "success:";
var _errorCode   = "error:";

function isSuccess(reply){

	if (reply == null || reply.lenth == 0) 
		return false;
	
	if ( reply.indexOf(_successCode) == 0)
		return true;
	
	return false;
}

function getSuccessData(reply){

	if (reply == null) return null;
	
	var pos = reply.indexOf(_successCode);
	if ( pos >= 0) {

		if ( reply.length == _successCode.length)
			return "";

		var retStr = reply.substr(_successCode.length);
		return retStr.trim();
	}
	return null;
}
function getErrorData(reply){

	if (reply == null) return null;
	var pos = reply.indexOf(_errorCode);
	if ( pos >= 0) {
		var retStr = reply.substr(_errorCode.length);
		return retStr.trim();
	}
	return null;
}




