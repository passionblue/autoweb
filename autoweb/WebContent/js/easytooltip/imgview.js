/*
 * Image preview script 
 * powered by jQuery (http://www.jquery.com)
 * 
 * written by Alen Grakalic (http://cssglobe.com)
 * 
 * for more info visit http://cssglobe.com/post/1695/easiest-tooltip-and-image-preview-using-jquery
 *
 */
 
this.imagePreview = function(){	
	/* CONFIG */
		
		xOffset = 10;
		yOffset = 300;
		
		// these 2 variable determine popup's distance from the cursor
		// you might want to adjust to get the right result
		
	/* END CONFIG */
	$("a.jttPreview").hover(function(e){
		this.t = this.title;
		this.title = "";	
		var c = (this.t != "") ? "<br/>" + this.t : "";
		$("body").append("<p id='preview'><img src='"+ this.href +"' alt='Image preview' />"+ c +"</p>");								 
		$("#preview")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px")
			.show();						
    },

    function(){
		this.title = this.t;	
		$("#preview").remove();
    });	

	$("a.jttPreview").mousemove(function(e){
		$("#preview")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px");
	});			
};

this.popMenu = function(){	
	/* CONFIG */
		
		xOffset = 10;
		yOffset = 100;
		
		// these 2 variable determine popup's distance from the cursor
		// you might want to adjust to get the right result
		
	/* END CONFIG */
		
		
	var hoverInMenu = false;
	var hoverInSub = false;
	
	$("a.popMenu").hover(function(e){
		var el = $(this);
        var pos = el.position();
        var idPart = $(this).attr("rel");
        
		var html = "<p id='popMenuContent'>";
		html += "<a href=\"/v_add_site_post.html?prv_panelId="+ idPart +"\">Add Post</a>";
		html += "<br/>";
		html += "<a href=\"/v_panel_edit.html?id="+ idPart +"&hide=1&ef=true\">Edit Panel Config</a>";
		html += "</p>";
		$("body").append(html);
		
		hoverInSub = true; 
		
		$("#popMenuContent").hover(function(e){
		}, function(){
			hoverInSub = false;
			if (hoverInMenu == false){
				$("#popMenuContent").remove();
			}
		});
		
		$("#popMenuContent")
			.css("top",(pos.top + $(this).height()-1) + "px")
			.css("left",(pos.left) + "px")
			.show();
		hoverInMenu = true;
    },

    function(){
    	if (hoverInSub == false) {
			$("#popMenuContent").remove();
			alert("xx");
    	} else {
    		setTimeout("animateFade(" + new Date().getTime() + ",'" + eid + "','" + timeToFade + "')", 500);
    	}
    	hoverInMenu = false;
    });	
	
	$("a.popMenu").click(function(e){
		if ($("#popMenuContent") ) {
			$("#popMenuContent").remove();
			e.preventDefault();
		}
	});
	
};

// starting the script on page load
$(document).ready(function(){
	imagePreview();
	popMenu();
	
   $("a.jttPreview").click(function(e){
	   var tg = "/home.html?view=" + $(this).attr("href");
	   location.href = tg;
	   e.preventDefault();
   });
});