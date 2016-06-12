$(document).ready(function(){

   //================================================================
   // Initialization of answer fields. First add 3 fields and can be addable in the future by user's will.
   //================================================================
   
   var numEntry = -1;
   
   if ( document.getElementById("empty") != null){
   for ( var i = 1; i <= 3; i++ ) {
	   $("#empty").append('<div id="pollForm_question_field">  <div id="pollForm_question_label" class="formLabel" > ' + i + '</div>');
	   $("#empty").append('<div id="pollForm_question_text" class="formFieldText" > <span></span>');      
	   $("#empty").append('<input id="pollAnswer'+i+'" type="text" size="70" name="a'+i+'" value=""/>');
//	   $("#empty").append('<a href="/fileup.html?ajaxRequest=true&ajaxOut=getmodalform" id="imgOpen'+i+'Ajax" rel="facebox">Upload Image</a>').append(" | ");
	   $("#empty").append('<a href="#" id="imgOpen'+i+'" class="imgUploadOpen">Upload</a>').append(" | ");
	   $("#empty").append('<a href="#" id="imgOpen'+i+'" class="imgLinkOpen">Add Link</a></div>');
	   $("#empty").append('<div><input id="imgOpen'+i+'File" type="FILE" size="50" name="mu'+i+'" style="display:none;" value=""/></div>');
	   $("#empty").append('<div><input id="imgOpen'+i+'Link" type="text" size="50" name="ml'+i+'" style="display:none;" value=""/></div>');
	   $("#empty").append('</div><div class="clear"></div>');

	   $("a#imgOpen"+i+"Ajax").facebox({
	        loading_image : '/js/facebox/loading.gif',
	        close_image   : '/js/facebox/closelabel.gif'
	   }); 
	   // has to init for addition. editing will read from html tags if numEntry == -1.
	   numEntry = i + 1;
   }
   }
   
   // Adding one answer by a click. 
   $("#addEntry").click(function (e) {
	   var i = numEntry;
	   
	   if (i == -1) {
		   i = parseInt($("#_nextNumAnswers").text());
	   }
	   alert(i);
	   
	   $("#empty2").append('<div id="pollForm_question_field">  <div id="pollForm_question_label" class="formLabel" > ' + i + '</div>');
	   $("#empty2").append('<div id="pollForm_question_text" class="formFieldText" > <span></span>');       
	   $("#empty2").append('<input id="field" type="text" size="70" name="a'+i+'" value=""/>');
	   $("#empty2").append('<a href="#" id="imgOpen'+i+'" class="imgUploadOpen">Upload</a>').append(" | ");
	   $("#empty2").append('<a href="#" id="imgOpen'+i+'" class="imgLinkOpen">Add Link</a></div>');
	   $("#empty2").append('<div><input id="imgOpen'+i+'File" type="FILE" size="50" name="mu'+i+'" style="display:none;" value=""/></div>');
	   $("#empty2").append('<div><input id="imgOpen'+i+'Link" type="text" size="50" name="ml'+i+'" style="display:none;" value=""/></div>');
	   $("#empty2").append('</div><div class="clear"></div>');
	   
	   $("input[name='answerIdxMax']").val(i);
	   $("a#imgOpen"+i+"Ajax").facebox({
	        loading_image : '/js/facebox/loading.gif',
	        close_image   : '/js/facebox/closelabel.gif'
	   }); 

	   $("#imgOpen" + i + ".imgUploadOpen").click(function(e){
		   var id = $(this).attr("id");
		   $("input#" + id + "File").toggle();
		   $("input#" + id + "Link").hide();
		   $("input#" + id + "Link").val("");

		   if ($("input#" + id + "File").is(":hidden") ){
			   $("input#" + id + "File").val("");
		   } 
		   e.preventDefault();
	   });
	   
	   $("#imgOpen" + i + ".imgLinkOpen").click(function(e){
		   var id = $(this).attr("id");
		   $("input#" + id + "Link").toggle();
		   $("input#" + id + "File").hide();
		   $("input#" + id + "File").val("");

		   if ($("input#" + id + "Link").is(":hidden") ){
			   $("input#" + id + "Link").val("");
		   } else {
			   $("input#" + id + "Link").val("http://");
		   }
		   e.preventDefault();
	   });
	   
	   i = i+1;
	   $("#_nextNumAnswers").text(""+i);
	   
	   e.preventDefault();
   });
   
   $(".imgUploadOpen").click(function(e){
	   var id = $(this).attr("id");
	   $("input#" + id + "File").toggle();
	   $("input#" + id + "Link").hide();
	   $("input#" + id + "Link").val("");

	   if ($("input#" + id + "File").is(":hidden") ){
		   $("input#" + id + "File").val("");
	   } 
	   
	   
	   e.preventDefault();
   });
   
   $(".imgLinkOpen").click(function(e){
	   var id = $(this).attr("id");
	   $("input#" + id + "Link").toggle();
	   $("input#" + id + "File").hide();
	   $("input#" + id + "File").val("");

	   if ($("input#" + id + "Link").is(":hidden") ){
		   $("input#" + id + "Link").val("");
	   } else {
		   $("input#" + id + "Link").val("http://");
	   }
	   e.preventDefault();
   });
   
   
   //================================================================
   // Image Radio Button
   //================================================================
	pollImgPreviewInit();
   
   $("div#radioItem").click(function(){
	   
	   $(this).parent().children("div#radioItem").each(function(u){
		   $(this).css("background-color","transparent");
		   jQuery("img:first", this).removeClass("pollOptionImgSelected");
//		   $(this).addClass("pollOptionImg");
	   });
	   
	   jQuery("img:first", this).addClass("pollOptionImgSelected");
	   
	   var pollId = jQuery("span:first", this).text();
	   $(this).children("span#radioIndex").each(function(u){
		   var se = $(this).text();
		   var idTxt = $("span#poll" + pollId).text();
		   $("input#radioAnswer" + idTxt).val(se);
	   });
   });

   $("img#radioItemImgxx").click(function(){
	   
	   $(this).parent().parent().children("img#radioItemImg").each(function(u){
		   
		   $(this).css("background-color","transparent");
		   $(this).removeClass("pollOptionImgSelected");
		   $(this).addClass("pollOptionImg");
	   });
	   
	   $(this).addClass("pollOptionImgSelected");
	   
	   var pollId = jQuery("span:first", $(this).parent()).text();
	   $(this).parent().children("span#radioIndex").each(function(u){
		   var se = $(this).text();
		   var idTxt = $("span#poll" + pollId).text();
		   $("input#radioAnswer" + idTxt).val(se);
	   });
   });
   
   
   $("a.pollImgPreview").click(function(){
	   e.preventDefault();
	   return;
   });

	//================================================================
	// Check/Radio text button selection
	//================================================================

   $("div.pollDispAnswerControl input[type=checkbox]").attr("checked", false);
   $("div.pollDispAnswerControl input[type=radio]").attr("checked", false);
   
   // This function allows user to click on the text section instead of having to click on the html controls to select the answer
   // 
   $("div.pollDispAnswer").click(function(){
	   
	   var id = $(this).attr("id");
	   var pollId = jQuery("span:first", this).text();
	   var numAns  = jQuery("span:last", this).text();

	   for ( var i = 1; i <= numAns; i++ ) {
		   var type = 	   $("input#" + id + "-input").attr("type");
		   if (type == "checkbox"){
		   
		   } else {
			   $("div#ans-" + pollId + "-" + i + "-input").removeClass("pollDispAnswerTextSelected");
		   }
	   }	   
	   
	   if ( $("input#" + id + "-input").attr("checked")) {
		   var type = 	   $("input#" + id + "-input").attr("type");
		   if (type == "checkbox"){
			   $("input#" + id + "-input").attr("checked", false);
			   $("div#" + id + "-input").removeClass("pollDispAnswerTextSelected");
		   }
		   
	   } else {
		   $("input#" + id + "-input").attr("checked", true);
		   $("div#" + id + "-input").addClass("pollDispAnswerTextSelected");
	   }
   });

   $("div.pollDispAnswerYesNo").click(function(){
	   
	   var id = $(this).attr("id");
	   var pollId = jQuery("span:first", this).text();
	   var numAns  = jQuery("span:last", this).text();

	   for ( var i = 1; i <= numAns; i++ ) {
		   var type = 	   $("input#" + id + "-input").attr("type");
		   if (type == "checkbox"){
		   
		   } else {
			   $("div#ans-" + pollId + "-" + i + "-input").removeClass("pollDispAnswerTextYesNoSelected");
		   }
	   }	   
	   
	   if ( $("input#" + id + "-input").attr("checked")) {
		   var type = 	   $("input#" + id + "-input").attr("type");
		   if (type == "checkbox"){
			   $("input#" + id + "-input").attr("checked", false);
			   $("div#" + id + "-input").removeClass("pollDispAnswerTextYesNoSelected");
		   }
		   
	   } else {
		   $("input#" + id + "-input").attr("checked", true);
		   $("div#" + id + "-input").addClass("pollDispAnswerTextYesNoSelected");
	   }
   });

   $("div.pollDispAnswerScale").click(function(){
	   
	   var id = $(this).attr("id");
	   var pollId = jQuery("span:first", this).text();
	   var numAns  = jQuery("span:last", this).text();

	   for ( var i = 1; i <= numAns; i++ ) {
		   var type = 	   $("input#" + id + "-input").attr("type");
		   $("div#ans-" + pollId + "-" + i + "-input").removeClass("pollDispAnswerScaleTextSelected");
	   }	   
	   
	   if ( $("input#" + id + "-input").attr("checked")) {
		   var type = 	   $("input#" + id + "-input").attr("type");
		   if (type == "checkbox"){
			   $("input#" + id + "-input").attr("checked", false);
			   $("div#" + id + "-input").removeClass("pollDispAnswerScaleTextSelected");
		   }
		   
	   } else {
		   $("input#" + id + "-input").attr("checked", true);
		   $("div#" + id + "-input").addClass("pollDispAnswerScaleTextSelected");
	   }
   });

   //================================================================
   // Form Submit button based on general formSubmit defined in ec_main. 
   // For the poll submission, need to check some extra things. 
   //================================================================
   var checkRequired = "true";
	   
   $("a#formSubmitPoll").click(function(e){
	   if (checkRequired != "true") {
		   return;
	   }
	   var clickForm = $(this).parent().parent().attr("name")
	   // Clear the form first
	   
	   $("span").text("");
	   $("input[type=text]#requiredField").each(function(u){
		   var checkForm = $(this).parent().parent().parent().attr("name");
		   if (clickForm !=  checkForm) return;
		   $(this).css("background-color","white");
	   });
	   $("input[type=checkbox]#requiredField").each(function(u){
		   var checkForm = $(this).parent().parent().parent().attr("name");
		   if (clickForm !=  checkForm) return;
		   $(this).css("background-color","white");
	   });
	   $("textarea#requiredField").each(function(u){
		   $(this).css("background-color","white");
	   });
	   $("select#requiredField").each(function(u){
		   $(this).css("background-color","white");
	   });

	   // Following section will check required field
	   $("input[type=text]#requiredField").each(function(i){

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

	   $("input[type=password]#requiredField").each(function(i){

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
	   
	   
	   $("input[type=checkbox]#requiredField").each(function(i){
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

	   $("textarea#requiredField").each(function(i){
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
	   $("select#requiredField").each(function(i){
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
	   
	   // The following section will check answers part only. 
	   // Two answers need to added. If not error text will be displayed/ 
	   // Not like other generic fields, answers specially customized for poll only. 
	   // so manipulating styles will be done here. 
	 
	   var relVal = $(this).attr("rel");

	   if (relVal == "add") {
		   var answersAdded = true;
		   for ( var i = 1; i <= 2; i++ ) {
	
			   var paval = $("input[id='pollAnswer"+i+"']").val();
			   
			   if (jQuery.trim(paval)== ""){
				   $("input[id='pollAnswer"+i+"']").css("background-color","orange");
				   answersAdded = false;
			   } else {
				   $("input[id='pollAnswer"+i+"']").css("background-color","white");
			   }
		   }	
		   
		   if (answersAdded == false){
			   $("#answerErrorText").css("color", "red");
			   $("#answerErrorText").css("font", "normal normal bold 11px verdana");
			   $("#answerErrorText").html("At least two answers need to be added");
			   e.preventDefault();
			   return;
		   } else {
			   $("#answerErrorText").html("");
		   }
	   }
   });

   
   //================================================================
   // Ajax Buttons
   //================================================================
   
   $("a#voteSubmitAjax_old").click(function(){
	   
	   var link = $(this).attr("rel");
	   var id = $(this).attr("ref");

	   $("input[id*='" + id +"']").each(function(u){
		   if ($(this).attr("checked") && $(this).attr("type")=="radio"){
			   link = link + "&answer=" + $(this).val();
		   } else if ($(this).attr("checked") && $(this).attr("type")=="checkbox"){
			   link = link + "&" + $(this).attr("name") +"="+ $(this).val();
		   }

		   // Image button check
		   if ( $(this).attr("type")=="hidden" && $(this).attr("name") =="answer") {
			   link = link + "&answer=" + $(this).val();
		   }
	   });	   
	   
	   $("div#poll" + id).children("input").each(function(u){
		   
		   if ($(this).attr("checked") && $(this).attr("type")=="radio"){
			   link = link + "&answer=" + $(this).val();
		   } else if ($(this).attr("checked") && $(this).attr("type")=="checkbox"){
			   link = link + "&" + $(this).attr("name") +"="+ $(this).val();
		   }

		   // Image button check
		   if ( $(this).attr("type")=="hidden" && $(this).attr("name") =="answer") {
			   link = link + "&answer=" + $(this).val();
		   }
	   });

	   
	   $.post("/pollVoteAction.html?" + link, function(data){
//		   alert("Data Loaded: " + data);
		   $("div#pollResultDetail" + id).hide().html(data).fadeIn(1000);
	   });
	   
//	   $.getJSON("/pollVoteAction.html?" + link, function(data){
//		   alert(data);
//		   
//		   var myFirstJSON = { "firstName" : "John", "lastName"  : "Doe", "age" : "23" };
//
//		   var r = "{ \"pollId\": 99, \"totalVotes\" : 10, \"results\" : [ \"1\":1, \"2\":2, \"3\":3 ] }";
//		   alert(r);
//		   
//	   });

	   e.preventDefault();
   });
   

   $("a#voteSubmitAjax").click(function(){
	   
	   var link = $(this).attr("rel");
	   var id = $(this).attr("ref");

	   $("input[id*='" + id +"']").each(function(u){
		   if ($(this).attr("checked") && $(this).attr("type")=="radio"){
			   link = link + "&answer=" + $(this).val();
		   } else if ($(this).attr("checked") && $(this).attr("type")=="checkbox"){
			   link = link + "&" + $(this).attr("name") +"="+ $(this).val();
		   }

		   // Image button check
		   if ( $(this).attr("type")=="hidden" && $(this).attr("name") =="answer") {
			   link = link + "&answer=" + $(this).val();
		   }
	   });	   

	   var ownAnswerVal= $("#ownAnswer" + id).val();
	   
	   if (ownAnswerVal != null) {
		   link = link + "&ownAnswer=" + ownAnswerVal;
	   }
	   
	   
	   //	   alert(link);
	   
	   $.getJSON("/pollVoteAction.html?" + link, function(data){
		   $("div#pollResultDetail" + id).hide();
		   $("div#pollResultDetail" + id).html("");
		   $("div#pollResultSummary" + id).hide();
		   $("div#pollResultSummary" + id).html("");

		   $("div#pollResultSummary" + id).html("Total Votes :" + data.totalVotes);
		   $.each(data.answers, function(i,answer){
			   	$("div#pollResultDetail" + id).append("<div class='pollResultDetailLine'>");
	       		$("div#pollResultDetail" + id).append("<div class='pollResultDetailText'>" + answer.text + "</div>" );
	       		$("div#pollResultDetail" + id).append("<div class='pollResultDetailCount'>" + answer.count + "</div>" );
	       		$("div#pollResultDetail" + id).append("</div>");
	       		$("div#pollResultDetail" + id).append("<div class='clear'></div>" );
	       	});
		   
		   $("div#pollResultDetail" + id).fadeIn(1000);
		   $("div#pollResultSummary" + id).fadeIn(1000);
	   });

	   e.preventDefault();
   });
   
   $("a#voteResultAjax").click(function(){
	   
	   var link = $(this).attr("rel");
	   var id = $(this).attr("ref");

//	   alert(link);
	   
//	   $.post("/pollVoteAction.html?" + link, function(data){
//		   alert("Data Loaded: " + data);
//		   $("div#pollResultDetail" + id).hide().html(data).fadeIn(1000);
//	   });
	   
	   $.getJSON("/pollVoteAction.html?" + link, function(data){
		   $("div#pollResultDetail" + id).hide();
		   $("div#pollResultDetail" + id).html("");
		   $("div#pollResultSummary" + id).hide();
		   $("div#pollResultSummary" + id).html("");

		   $("div#pollResultSummary" + id).html("Total Votes :" + data.totalVotes);
		   $.each(data.answers, function(i,answer){
			   	$("div#pollResultDetail" + id).append("<div class='pollResultDetailLine'>");
	       		$("div#pollResultDetail" + id).append("<div class='pollResultDetailText'>" + answer.text + "</div>" );
	       		$("div#pollResultDetail" + id).append("<div class='pollResultDetailCount'>" + answer.count + "</div>" );
	       		$("div#pollResultDetail" + id).append("</div>");
	       		$("div#pollResultDetail" + id).append("<div class='clear'></div>" );
	       	});
		   
		   $("div#pollResultDetail" + id).fadeIn(1000);
		   $("div#pollResultSummary" + id).fadeIn(1000);
	   });

	   e.preventDefault();
   });

   
   $("div.openEmbedCode").click(function(){
		   var id = $(this).next("span").text();
		   $("#pollScriptEmbed"+id).toggle();
   });   
});

//================================================================
// Image Tooltip Support
//================================================================

this.pollImgPreviewInit = function(){	
	/* CONFIG */
		
		xOffset = 10;
		yOffset = 30;
		
		// these 2 variable determine popup's distance from the cursor
		// you might want to adjust to get the right result
		
	/* END CONFIG */
	$("div.pollImgPreviewClick").click(function(e){
		this.t = this.title;
		this.title = "";	
		var c = (this.t != "") ? "<br/>" + this.t : "";
		$("body").append("<p id='preview'><img src='"+ $(this).attr("href") +"' alt='Image preview' />"+ c +"</p>");								 
		$("#preview")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px")
			.fadeIn("fast");						
    });	

	$("div.pollImgPreviewClick").hover(
	function(e){
    },
	function(){
		this.title = this.t;	
		$("#preview").remove();
    });	
	
	
	
	$("div.pollImgPreview").hover(function(e){
		this.t = this.title;
		this.title = "";	
		var c = (this.t != "") ? "<br/>" + this.t : "";
		$("body").append("<p id='preview'><img src='"+ $(this).attr("href") +"' alt='Image preview' />"+ c +"</p>");								 
		$("#preview")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px")
			.fadeIn("fast");						
    },
	function(){
		this.title = this.t;	
		$("#preview").remove();
    });	
	$("div.pollImgPreview").mousemove(function(e){
		$("#preview")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px");
	});			
	
	
};

