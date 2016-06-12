$(document).ready(function(){

	$("a.refreshPanel").click(function(e){
		var id = $(this).attr("rel");
		var pathname = window.location.pathname;
	    resetPanel(pathname +"?", id);
	    e.preventDefault();
	});
});

function resetPanel(url, id){
	//alert(url + "&fmt=getpart&partType=panel&partId=" + id);
	var pathname = window.location.pathname;
	
	$.get(pathname + "?fmt=getpart&partType=panel&partId=" + id, function(data){
		alert(data);
		if ( $("div#panel-container-" + id) ){
			$("div#panel-container-" + id).html(data);
		   	$(".generalDeleteItem").click(function(e){
		   		var menuId = $(this).parent().parent().attr("rel");
		   		sendFormAjaxSimple('/menuItemAction.html?ajaxRequest=true&ajaxOut=getmodalstatus&del=true&id=' + menuId, false, callback_delete_parent_parent, this);
		   		e.preventDefault();
		   	});
		}
	});
}