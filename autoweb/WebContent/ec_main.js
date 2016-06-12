$(document).ready(function(){
	
    $("div#listProdFrame").each(function(){
        $(this).addClass("nohover");
      });

    $("div#listProdFrame").hover(
      function () {
        $(this).removeClass("nohover");
        $(this).addClass("hover");
      }, 
      function () {
        $(this).removeClass("hover");
        $(this).addClass("nohover");
      }
    );
    
    
   $("a#addToCart").click(function (e) {
	   var value = $("select#sizeSelect").val();
	   $("span#sizeLabelInset").text(""); 
	   $("span#colorLabelInset").text("");

	   var result = "okay";
	   if (value == "" ){
		   $("span#sizeLabelInset").text("- Please select size");
		   result = "no";
	   } 
	   
	   var value = $("select#colorSelect").val();
	   if (value == "" ){
		   $("span#colorLabelInset").text("- Please select color"); 
		   result = "no";
	   }

	   if (result != "okay"){
		   e.preventDefault();
		   return;
	   }
   });	   
   
   //== Checking required field
   var checkRequired = "true";
   $("a#formSubmit").each(function (f) {
	   
	   $(this).click(function(e){
		   if (checkRequired != "true") {
			   return;
		   }
		   var clickForm = $(this).parent().parent().attr("name")
		   
		   $("span").text("");
		   $("input[type=text]#requiredField").each(function(u){
			   var checkForm = $(this).parent().parent().parent().attr("name");
			   if (clickForm !=  checkForm) return;
			   $(this).css("background-color","transparent");
		   });
		   $("input[type=checkbox]#requiredField").each(function(u){
			   var checkForm = $(this).parent().parent().parent().attr("name");
			   if (clickForm !=  checkForm) return;
			   $(this).css("background-color","transparent");
		   });
		   $("textarea#requiredField").each(function(u){
			   $(this).css("background-color","transparent");
		   });
		   $("select#requiredField").each(function(u){
			   $(this).css("background-color","transparent");
		   });

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
	   });
   });

   var checkRequired2 = "true";
   $("a#formSubmit2").each(function (f) {
	   $(this).click(function(e){
		   if (checkRequired2 != "true") {
			   return;
		   }
		   var clickForm = $(this).parent().parent().attr("name")
		   
		   $("span").text("");
		   $("input[type=text].requiredField").each(function(u){
			   var checkForm = $(this).parent().parent().parent().parent().attr("name");
			   if (clickForm !=  checkForm) return;
			   $(this).css("background-color","transparent");
		   });
		   $("input[type=checkbox].requiredField").each(function(u){
			   var checkForm = $(this).parent().parent().parent().parent().attr("name");
			   if (clickForm !=  checkForm) return;
			   $(this).css("background-color","transparent");
		   });
		   $("textarea.requiredField").each(function(u){
			   $(this).css("background-color","transparent");
		   });
		   $("select.requiredField").each(function(u){
			   $(this).css("background-color","transparent");
		   });

		   $("input[type=text].requiredField").each(function(i){

			   var checkForm = $(this).parent().parent().parent().parent().attr("name");
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

			   var checkForm = $(this).parent().parent().parent().parent().attr("name");
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
			   var checkForm = $(this).parent().parent().parent().parent().attr("name");
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
			   var checkForm = $(this).parent().parent().parent().parent().attr("name");
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
			   var checkForm = $(this).parent().parent().parent().parent().attr("name");
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
   
   
   
   
   //============ checkout Account Info Form ========================
   $("div#ecCheckoutAccountInfoForm_useBilling_checkbox input").attr('checked', true);
   $("div#addressBillingForm").hide();
   $("div#ecCheckoutAccountInfoForm_useBilling_checkbox input").click(function () {

	   if ($(this).attr('checked')) {
		   $("div#addressBillingForm").slideUp();
		} else {
		   $("div#addressBillingForm").slideDown();
		}
	   
    });

   $("a#formSubmitLogin").each(function (f) {
	   $(this).click(function(e){
		   $("span").text("");
		   $("input#requiredFieldLogin").each(function(u){
			   $(this).css("background-color","transparent");
		   });
		   $("select#requiredFieldLogin").each(function(u){
			   $(this).css("background-color","transparent");
		   });

		   $("input#requiredFieldLogin").each(function(i){
			   if (jQuery.trim($(this).val())== ""){
				   $(this).css("background-color","orange");
				   $(this).parent().children("span").css("color", "red");
				   $(this).parent().children("span").css("font", "normal normal bold 11px verdana");
				   $(this).parent().children("span").html("  REQUIRED<br>");
				   e.preventDefault();
				   return;
			   }
		   });
		   $("select#requiredFieldLogin").each(function(i){
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
   

   $("div#siteRegPaymentInfoForm_skip_checkbox input").attr('checked', false);

   $("div#siteRegPaymentInfoForm_skip_checkbox input").click(function () {
	   if ($(this).attr('checked')) {
		   alert("You can now click on submit without entering payment information.");
		   checkRequired = "false";
		} else {
			checkRequired = "true";
		}
    });
});