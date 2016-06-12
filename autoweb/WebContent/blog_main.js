$(document).ready(function(){
	$('a#openComment').ajaxify({
		onStart:function(options){
		alert("onstart" + $(this).value);
	    },
		link:'/jquery_text/donotuse.html?random=true',
		target: '#blogPostCommentFrame' + $(this).attr('title'),
		loading_img:'images/loader/arrows16.gif',
		title:'', // change page title. Since v2.0
		method:'GET'});
	
	
	$("a#blogOpenComments").click(function(e){
		//alert("onstart" + $(this).attr("title"));
		var id = $(this).attr("title");
		var commentSwitch = $("#blogPostCommentFrame" + id + "OpenClose").text();
		
		if (commentSwitch == "0" || commentSwitch== null) {
		
//		    $.get("/blogCommentAction.html?ajaxRequest=true&ajaxOut=getListHtml&blogPostId=" + id, function(html) {
//		    	 $("#blogPostCommentFrame" + id ).hide().html(html).slideDown();
//		    	 $("#blogPostCommentFrame" + id + "OpenClose").text("1");
//		    });
		    
		     $.ajax({
		    	 type: "GET",
		    	 url: "/blogCommentAction.html?ajaxRequest=true&ajaxOut=getListHtml&blogPostId=" + id,
			     data: "data=true",
			     beforeSend: function() { 
		    	 	$("#commentLoading"+id).show();
		     	 },
			     complete: function() { 
			    	 	$("#commentLoading"+id).hide();
			     },
		     	success: function(html) {
				    	 $("#blogPostCommentFrame" + id ).hide().html(html).slideDown();
				    	 $("#blogPostCommentFrame" + id + "OpenClose").text("1");
				    	 	$("#commentLoading"+id).hide();
			     	}
		     })
		    
		    
		} else if (commentSwitch == "1") {
	    	 $("#blogPostCommentFrame" + id ).slideUp();
	    	 $("#blogPostCommentFrame" + id + "OpenClose").text("2");
			
		} else if (commentSwitch == "2") {
	    	 $("#blogPostCommentFrame" + id ).slideDown();
	    	 $("#blogPostCommentFrame" + id + "OpenClose").text("1");
		}			
		
		e.preventDefault();
	});
	
	$("a#blogCommentDelete").click(function(e){
		//alert("onstart" + $(this).attr("title"));
		var id = $(this).attr("rel");
		
		$.get("/blogCommentAction.html?del=true&id=" + id + "&ajaxRequest=true&ajaxOut=getmodalstatus", function(text) {
			$("#blogCommentFrame" + id ).fadeOut(1000, function () {
		        $(this).remove();
		      });

	    });
		
		
		e.preventDefault();
	});
	
});

function deleteThis(deleteUrl, id, deleteTagId) {

	$.get(deleteUrl+"?del=true&id=" + id + "&ajaxRequest=true&ajaxOut=getmodalstatus", function(text) {
		$("#"+ deleteTagId ).fadeOut(1000, function () {
	        $(this).remove();
	      });
    });
}

