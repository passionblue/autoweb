$(document).ready(function(){
	
	/*  */
	
	$("a#buttonSetToInput").click(function(e){
		text = $(this).text();
		current = $(this).parent().parent().find("input").val();
		if (current == ''|| text=='') {
			$(this).parent().parent().find("input").val(text);
		} else {
			var sum = parseFloat(text) + parseFloat(current);
			$(this).parent().parent().find("input").val(sum+'');
		}
	});
	
	/* copy defined name and values  Name/id/1/2/3/4/5/6 */
	$("a#buttonSetToInputNameShortcut").click(function(e){
//		var text = $(this).text();
		var text = $(this).attr("rel");
		
//		alert(text);
		var predef = text.split("/");
		
		var element = document.getElementById('churMemberId');
	    element.value = predef[1];
	    element = document.getElementById('tithe');
	    element.value = predef[2];
	    element = document.getElementById('weekly');
	    element.value = predef[3];
	    element = document.getElementById('thanks');
	    element.value = predef[4];
	    element = document.getElementById('mission');
	    element.value = predef[5];
	    element = document.getElementById('construction');
	    element.value = predef[6];
	    element = document.getElementById('other');
	    element.value = predef[7];
	});
	
	
});
