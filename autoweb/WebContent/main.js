$(document).ready(function(){
	
	$("#openfield").click(function (e) {
		$("#fieldd").slideToggle();
		e.preventDefault();
	});

	
	//==================================================================================
	// Reorder Functions 
	//==================================================================================
	$("a#panelMenuOrderUp").click(function (e) {
		
		var idx = parseInt($(this).attr("rel"));
		
		if (idx == 1 ) return false;
		var upIdx = idx -1;
		
		var thisHtml  = $("div#panelMenuOrderItem"+idx).html();
		var upHtml  = $("div#panelMenuOrderItem"+upIdx).html();

		$("div#panelMenuOrderItem"+idx).html("");
		$("div#panelMenuOrderItem"+upIdx).html("");
		
		$("div#panelMenuOrderItem"+idx).hide().html(upHtml).fadeIn(1000);
		$("div#panelMenuOrderItem"+upIdx).hide().html(thisHtml).fadeIn(1000);

		$("#paneMenuReorderChanged").text("true");

		e.preventDefault();
	});
	
	$("a#panelMenuOrderDown").click(function (e) {
		var idx = parseInt($(this).attr("rel"));
		var downIdx = idx + 1;
		
		if ( $("div#panelMenuOrderItem"+downIdx).size() == 0)
			return false;
		
		var thisHtml  = $("div#panelMenuOrderItem"+idx).html();
		var downHtml  = $("div#panelMenuOrderItem"+downIdx).html();
		
		$("div#panelMenuOrderItem"+idx).hide().html(downHtml).fadeIn(1000);
		$("div#panelMenuOrderItem"+downIdx).hide().html(thisHtml).fadeIn(1000);

		$("#paneMenuReorderChanged").text("true");
		
		e.preventDefault();
	});

	//==================================================================================
	// Slid open table name should be slideOpenTable
	
	$("div#slideOpenSwitch").click(function (e) {
	});
	
	//==================================================================================
	// Slid open table name should be slideOpenTable
	
    //################# facebox ################################
	//1.2			 
				$('a[rel*=facebox]').facebox({
				    loading_image : '/js/facebox-1.2/loading.gif',
				    close_image   : '/js/facebox-1.2/closelabel.gif'
				});
				 
				//1.3
//				 $('a[rel*=facebox]').facebox({
//				        loadingImage : '/js/facebox/loading.gif',
//				        closeImage   : '/js/facebox/closelabel.png'
//				 });	
	
});

//==================================================================================
// Bookmark 
//==================================================================================
function bookmark(url,title){
	if ((navigator.appName == "Microsoft Internet Explorer") && (parseInt(navigator.appVersion) >= 4)) {
		window.external.AddFavorite(url,title);
	} else if (navigator.appName == "Netscape") {
		window.sidebar.addPanel(title,url,"");
	} else {
		alert("Press CTRL-D (Netscape) or CTRL-T (Opera) to bookmark");
	}
}

//==================================================================================
// Reorder Functions 
//==================================================================================
function savePanelMenuOrder(numItems, panelId){

	//alert($("#paneMenuReorderChanged").text());
	if ($("#paneMenuReorderChanged").text() =="false") {
		alert("Nothing changed.");
		return false;
	}

	var ids = "";
	for(var i = 1; i<=numItems;i++){
		ids += jQuery.trim($("#panelMenuOrderItem"+i + " #menuIdHold").text()) + "-";
	}
	//alert(ids);
	
	
	location.href="/panelMenuReorder.html?panelId=" + panelId + "&ids=" + ids + "&fwdTo=/v_panel_menu_reorder.html?panelId=" + panelId;
}



