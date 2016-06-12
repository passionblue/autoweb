$(document).ready(function(){
	
	var pageType = $("div#pageType").text();
	
	// == Clean up variables ==
	$("input[name*='quarterFinalTeam_']").each(function (f) {
		if ( pageType == "NEWBET") {
			$(this).val("");
		}

		if ( pageType == "EDIT") {
			var targetName = $(this).attr("id");
			var code = $("#"+targetName+"_div").attr("code");
			$(this).val(code);
		}
	
	});

	$("input[name*='semiFinalTeam_']").each(function (f) {
		if ( pageType == "NEWBET") {
			$(this).val("");
		}
	});

	$("input[name*='finalTeam_']").each(function (f) {
		if ( pageType == "NEWBET") {
			$(this).val("");
		}
	});

	$("input[name*='championTeam_']").each(function (f) {
		if ( pageType == "NEWBET") {
			$(this).val("");
		}
	});
	
	var fieldTarget="";
	
	$("img#addImage").each(function (f) {
		$(this).click(function(){
			fieldTarget=$(this).attr("codeTarget");
		});
	});
	
	
	$("div#selectTeam").each(function (f) {
		$(this).click(function(){
			var teamCode = $(this).attr("code");
			var teamCountry = $(this).attr("country");
//			alert(teamCode + "-" + fieldTarget);
			document.getElementById(fieldTarget).value=teamCode;
			$("#"+fieldTarget+"_div").html("<img style=\"float:left;\" src=\"/images/worldcup/" + teamCode +".gif\"/> &nbsp;" + teamCountry);
		});
	});

    // Clear button
	$("a#betClear").click(function(){
		
		$("div[class='selectedField']").each(function (f) {
			$(this).text("Select Team");
		});
				
		// == Clean up variables ==
		$("input[name*='quarterFinalTeam_']").each(function (f) {
			$(this).val("");
		});

		$("input[name*='semiFinalTeam_']").each(function (f) {
			$(this).val("");
		});

		$("input[name*='finalTeam_']").each(function (f) {
			$(this).val("");
		});

		$("input[name*='championTeam_']").each(function (f) {
			$(this).val("");
		});
		
		e.preventDefault();
	    return;
	});
	
	   var checkBetsRequired = "true";

	   $("a#betSubmit").each(function (f) {
		   
		   $(this).click(function(e){
			   if (checkBetsRequired != "true") {
				   return;
			   }
			   $("span").text("");
			   $("input[type=text].requiredField").each(function(u){
				   $(this).css("background-color","transparent");
			   });
			   $("input[type=checkbox]#requiredField").each(function(u){
				   $(this).css("background-color","transparent");
			   });
			   $("textarea#requiredField").each(function(u){
				   $(this).css("background-color","transparent");
				   
			   });
			   $("select#requiredField").each(function(u){
				   $(this).css("background-color","transparent");
			   });

			   $("input[type=text]#requiredField").each(function(i){
				   if (jQuery.trim($(this).val())== ""){
					   $(this).css("background-color","orange");
					   $(this).parent().children("span").css("color", "red");
					   $(this).parent().children("span").css("font", "normal normal bold 11px verdana");
					   $(this).parent().children("span").html("  REQUIRED<br>");
					   e.preventDefault();
					   return;
				   }
			   });
			   $("input[type=checkbox]#requiredField").each(function(i){
				   if (!$(this).attr('checked')){
					   $(this).css("background-color","orange");
					   $(this).parent().children("span").css("color", "red");
					   $(this).parent().children("span").css("font", "normal normal bold 11px verdana");
					   $(this).parent().children("span").html("  REQUIRED<br>");
					   e.preventDefault();
					   return;
				   }
			   });
			   
			   $("textarea#requiredField").each(function(i){
				   if (jQuery.trim($(this).val())== ""){
					   $(this).css("background-color","orange");
					   $(this).parent().children("span").css("color", "red");
					   $(this).parent().children("span").css("font", "normal normal bold 11px verdana");
					   $(this).parent().children("span").html("  REQUIRED<br/>");
					   e.preventDefault();
					   return;
				   }
			   });
			   $("select#requiredField").each(function(i){
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
	   //alert("x");
	   
		$("div#closeMatch").hide();
		$("div#openMatch").click(function(){
			$("div#matchTable").slideDown();
			$(this).hide();
			$("div#closeMatch").fadeIn(1000);
		});	   
		$("div#closeMatch").click(function(){
			$("div#matchTable").slideUp();
			$(this).hide();
			$("div#openMatch").fadeIn(1000);
		});	   
});
