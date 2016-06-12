$(document).ready(function(){
	
	$("#devNoteTable td img#deleteRow").click(function () {
	    $(this).parent().parent().parent().fadeTo(1000, 0, function () { 
	        $(this).remove();
	    });
	});

});

