/*
 * Image preview script 
 * powered by jQuery (http://www.jquery.com)
 * 
 * written by Alen Grakalic (http://cssglobe.com)
 * 
 * for more info visit http://cssglobe.com/post/1695/easiest-tooltip-and-image-preview-using-jquery
 *
 */

// Based on js/easytooltib/imgview.js for panel menu popup.  

var hoverInMenu = false;
var hoverInSub = false;
var openSub = false;

var pc_hoverInMenu = false;
var pc_hoverInSub = false;
var pc_openSub = false;

//starting the script on page load
$(document).ready(function(){
	//alert("");
	popMenu();
});

this.popMenu = function(){	
	
	//-------------------------------------------------------------------------------
	$("a.popMenu").hover(function(e){
		var el = $(this);
        var pos = el.position();
        var arg = $(this).attr("rel");
        
		var html = createPopupContent(arg);
		$("body").append(html);
		
		hoverInMenu = true;
		openSub = true;
		
		$("#popMenuContent").hover(function(e){
			hoverInSub = true;
		}, function(){
			hoverInSub = false;
			openSub = false;
			if (hoverInMenu == false){
				$("#popMenuContent").remove();
			}
		});
		
		//alert(pos.top + ' ' + pos.left);
		$("#popMenuContent")
			.css("position", "absolute")
			.css("top",(pos.top + $(this).height()-1) + "px")
			.css("left",(pos.left) + "px")
			.css("display","block")
			.show();
    },

    function(){
    	hoverInMenu = false;
    	if (openSub == false) {
			$("#popMenuContent").remove();
    	} else {
    		setTimeout(checkAndSet, 300);
    	}
    });	
	
	$("a.popMenu").click(function(e){
		if ($("#popMenuContent") ) {
			$("#popMenuContent").remove();
			e.preventDefault();
		}
	});

	
	//-------------------------------------------------------------------------------
	
	$("a.popClickMenu").click(function(e){
		if (pc_openSub ) {
//			if ( $("#popClickMenuContent"))
//				$("#popClickMenuContent").remove();
//			pc_openSub = false;
			e.preventDefault();
		} else {

			var el = $(this);
	        var pos = el.position();
	        var arg = $(this).attr("rel");
	        
			var html = createPopupContent(arg, "popClickMenuContent");
			
			$("body").append(html);
			
			
			$("#popClickMenuContent").hover(function(e){
				pc_hoverInSub = true;
			}, function(){
				if (pc_hoverInMenu == false){
					setTimeout(checkAndSetClickMenu, 1000);
				}
				pc_hoverInSub = false;
			});
//			alert(pos.top);
			$("#popClickMenuContent")
				.css("top",(pos.top + $(this).height()-1) + "px")
				.css("left",(pos.left) + "px")
				.css("display","block")
				.show();

			$("a#menuPageAddLink").click(function(e){
				if ($("#popClickMenuContent")) {
					$("#popClickMenuContent").remove();
					pc_openSub = false;
					pc_hoverInMenu = false;
					pc_hoverInSub = false;
				}
				
				var panelId = $(this).attr("rel");
				resetPanel("", panelId);
			});
			
			
			pc_openSub = true;
			e.preventDefault();
		}
	});
	
	$("a.popClickMenu").hover(function(e){
		pc_hoverInMenu = true;
	},
    function(){
    	pc_hoverInMenu = false;
    	if (pc_openSub == false) {
    		if ($("#popClickMenuContent"))
    			$("#popClickMenuContent").remove();
    	} else {
    		setTimeout(checkAndSetClickMenu, 1000);
    	}
	});	
};

function checkAndSet(){
	if (hoverInMenu == false && hoverInSub == false ) {
		if ($("#popMenuContent")) {
			$("#popMenuContent").remove();
			openSub = false;
		}
	}	
}

function checkAndSetClickMenu(){
	if (pc_hoverInMenu == false && pc_hoverInSub == false ) {
		if ($("#popClickMenuContent")) {
			$("#popClickMenuContent").remove();
			pc_openSub = false;
		}
	}	
}

function createPopupContent(arg, contentId){

	var params = arg.split(":");
	var html = "";	

	var cid = "popMenuContent";
	if ( contentId ) {
		cid = contentId;
	} 
	
	if (params[0] == "menu") {
		html += "<p id='"+cid+"'>";
		html += "<a href=\"/v_add_site_post.html?prv_panelId="+ params[1] +"\">Add Post</a>";
		html += "<br/>";
		html += "<a href=\"/v_panel_edit.html?id="+ params[1] +"&hide=1&ef=true\">Edit Panel Config</a>";
		html += "</p>";

	} else if (params[0] == "TEST") {

		html += "<div id='"+cid+"'>";
		html += "<table class='mytable1'>";
		
		$("div#main-content").find("div").each(function(u){
			if ($(this).is("div")){
				if ($(this).attr("id") || $(this).attr("class")) {

					html += "<tr><td>";
					var elem = this;
					var i = 0;
					// TRying to indent based on depth of a child
					while(true){
						if ( $(elem).attr("id") == "main-content"){
							break;
						} else {
							html += "&nbsp;&nbsp;&nbsp;&nbsp;";
						}
						elem = $(elem).parent();
						if ( i == 10) break;
						i++;
					}
					html += "<b>" + $(this).attr("id") +"</b>/" + $(this).attr("class")+ "</td></td>";
				}
			}
		});
		
		html += "</table>";
		html += "</div>";
		
	} else if (params[0] == "LoadHtml") {
		// Load the div section in the same page
		html += "<div id='"+cid+"'>";
		if ( $("#" + params[1])){
			html += $("#" + params[1]).html();
		} else {
			html += "Error: Element Not Found. " + params[1];
		}
		html += "</div>";

	} else if (params[0] == "LoadPageAdd") {
		// 1 = div id for list of pages
		// 2 = panel Id
		var textVal = "";
		if ( $("#" + params[1])){
			textVal = $("#" + params[1]).text();
		}

		var pageInfo = textVal.split(":");

		html += "<div id='"+cid+"'>";
		for(i=0; i<pageInfo.length; i++) {
			
			var nvp = pageInfo[i].split("/");
			
			html +="<div ><a rel=\""+ params[2] +"\" id=\"menuPageAddLink\"href=\"javascript:sendFormAjaxSimple('/menuItemAction.html?targetType=0&ajaxRequest=true&ajaxOut=getmodalstatus&add=true&panelId="+ params[2]+"&title="+ nvp[1]+"&pageId="+ nvp[0]+"',false)\">"+ nvp[1]+"</a></div>";		
		}
		html += "</div>";
		
	} else {
		html += "XXXXXXXXXXX";
	}
	
	return html;
}


