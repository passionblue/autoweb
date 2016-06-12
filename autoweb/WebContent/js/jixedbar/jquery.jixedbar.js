/*
 * jixedbar - a jQuery fixed bar plugin.
 * http://code.google.com/p/jixedbar/
 * 
 * Version 0.0.3 (Beta)
 * 
 * Copyright (c) 2009-2010 Ryan Yonzon, http://ryan.rawswift.com/
 * Dual licensed under the MIT and GPL licenses:
 * http://www.opensource.org/licenses/mit-license.php
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Last update - July 18, 2010
 */

(function($) {

	// jixedbar plugin
	$.fn.jixedbar = function(options) {
		var constants = {
				constOverflow: "hidden",
				constBottom: "0px"
			};
		var defaults = {
				showOnTop: false,
				transparent: false,
				opacity: 0.9,
				opaqueSpeed: "fast",
				slideSpeed: "fast",
				roundedCorners: true, // only works in FF
				roundedButtons: true, // only works in FF
				menuFadeSpeed: 250,
				tooltipFadeSpeed: "slow",
				tooltipFadeOpacity: 0.8
			};
		var options = $.extend(defaults, options);
		var ie6 = (navigator.appName == "Microsoft Internet Explorer" && parseInt(navigator.appVersion) == 4 && navigator.appVersion.indexOf("MSIE 6.0") != -1);
		var button_active = false;
		var active_button_name = "";
		
		this.each(function() {
			var obj = $(this);
			var screen = jQuery(this);
			var fullScreen = screen.width(); // get screen width
			var centerScreen = (fullScreen/2) * (1); // get screen center
			var hideBar = false;

			if ($(this).checkCookie("JXID")) { // check if cookie already exists
				if ($(this).readCookie("JXHID") == "true") {
					this.hideBar = true; // hide bar
				}
			} else { // else drop cookie
				$(this).createCookie("JXID", $(this).genRandID()); // set random ID and create cookie
				$(this).createCookie("JXHID", false); // set bar hide to false then create cookie
			}
			
			// set html and body style for jixedbar to work
			if ($.browser.msie && ie6) {
                $("html").css({"overflow" : "hidden", "height" : "100%"});
                $("body").css({"margin": "0px", "overflow": "auto", "height": "100%"});
			} else {
				$("html").css({"height" : "100%"});
				$("body").css({"margin": "0px", "height": "100%"});
			}

			if ($.browser.msie && ie6) {
				pos = "absolute";
			} else {
				pos = "fixed";
			}
			
			// create hide container and button
			if ($(".jx-bar-button-right", this).exists()) {
				$("<ul />").attr("id", "jx-hid-con-id").insertBefore($(this).find(".jx-bar-button-right:first"));
			} else {
				$("<ul />").attr("id", "jx-hid-con-id").appendTo(this);
			}

			if (defaults.showOnTop) {
				hideIndicator = "jx-hide-top";
			} else {
				hideIndicator = "jx-hide";
			}
			
			$("#jx-hid-con-id").html('<li alt="Hide toolbar"><a id="jx-hid-btn-id" class="' + hideIndicator + '"></a></li>');
			$("#jx-hid-con-id").addClass("jx-bar-button-right");
			
			$("<span />").attr("id", "jx-hid-sep-id").insertAfter("#jx-hid-con-id");
			$("#jx-hid-sep-id").addClass("jx-hide-separator");
			
			// add click event on hide button
			$("#jx-hid-btn-id").parent().click(function() {
				$("#jx-menu-con-id").fadeOut();
				$(obj).slideToggle(defaults.slideSpeed, function() {
					$(this).createCookie("JXHID", true); // set bar hide to true
					if (!$(this).checkCookie("JXID")) { // check if cookie JXID exists, if not create one
						$(this).createCookie("JXID", $(this).genRandID()); // set random ID and drop cookie
					}
					$("#jx-uhid-con-id").slideToggle(defaults.slideSpeed);
				});
				return false;
			});
			
			// initialize bar
			$(this).css({
				"overflow": constants["constOverflow"],
				"position": pos,
			});
			
			// set location: top or bottom
			if (defaults.showOnTop) {
				$(this).css({
					"top": constants["constBottom"]
				});				
			} else {
				$(this).css({
					"bottom": constants["constBottom"]
				});
			}
			
			// add bar style (theme)
			$(this).addClass("jx-bar");
			
			// rounded corner style (theme)
			if (defaults.roundedCorners) {
				if (defaults.showOnTop) {
					$(this).addClass("jx-bar-rounded-bl jx-bar-rounded-br");
				} else {
					$(this).addClass("jx-bar-rounded-tl jx-bar-rounded-tr");
				}
			}

			// button style (theme)
			$(this).addClass("jx-bar-button");
			
			// rounded button corner style (theme)
			if (defaults.roundedButtons) {
				$(this).addClass("jx-bar-button-rounded");
			}

			// calculate and adjust bar to center
			marginLeft = centerScreen-($(this).width()/2);
			$(this).css({"margin-left": marginLeft});

			// fix image vertical alignment and border
			$("img", obj).css({
				"vertical-align": "bottom",
				"border": "#fff solid 0px" // no border
			});
			
			// check for alt attribute and set it as button text
			$(this).find("img").each(function() {
				if ($(this).attr("alt") != "") {
					altName = "&nbsp;" + $(this).attr("alt");
					$(this).parent().append(altName);
				}
			});

			// check of transparency is enabled
			if (defaults.transparent) {
				$(this).fadeTo(defaults.opaqueSpeed, defaults.opacity); 
			}

			// create menu container first before creating the tooltip container, so tooltip will be on foreground
			$("<div />").attr("id", "jx-menu-con-id").appendTo("body");

			// add transparency effect on menu container if "transparent" is true
			if (defaults.transparent) {
				$("#jx-menu-con-id").fadeTo(defaults.opaqueSpeed, defaults.opacity);
			}
			
			/*
			 * create show/unhide container and button
			 */
			$("<div />").attr("id", "jx-uhid-con-id").appendTo("body"); // create div element and append in html body
			$("#jx-uhid-con-id").addClass("jx-show");
			$("#jx-uhid-con-id").css({
				"overflow": constants["constOverflow"],
				"position": pos,
				"margin-left": ($(this).offset().left + $(this).width()) - $("#jx-uhid-con-id").width()
			});
			
			// set show/unhide location: top or bottom
			if (defaults.showOnTop) {
				$("#jx-uhid-con-id").css({
					"top": constants["constBottom"]
				});				
			} else {
				$("#jx-uhid-con-id").css({
					"bottom": constants["constBottom"]
				});				
			}
			
			// check if we need to add transparency to menu container
			if (defaults.transparent) {
				$("#jx-uhid-con-id").fadeTo(defaults.opaqueSpeed, defaults.opacity); 
			}

			// check if we need to hide this bar
			if (this.hideBar) {
				$(this).css({
					"display": "none"
				});				
			}
			
			// check if we need to hide the show/unhide button
			if (!this.hideBar) {
				$("#jx-uhid-con-id").css({
					"display": "none"
				});
			}
			
			$("<ul />").attr("id", "jx-uhid-itm-id").appendTo($("#jx-uhid-con-id"));
			if (defaults.showOnTop) {
				unhideIndicator = "jx-show-button-top";
			} else {
				unhideIndicator = "jx-show-button";
			}
			$("#jx-uhid-itm-id").html('<li alt="Show toolbar"><a id="jx-uhid-btn-id" class="' + unhideIndicator + '"></a></li>');

			// show/unhide container and button style
			if (defaults.roundedCorners) {
				if (defaults.showOnTop) {
					$("#jx-uhid-con-id").addClass("jx-bar-rounded-bl jx-bar-rounded-br");
				} else {
					$("#jx-uhid-con-id").addClass("jx-bar-rounded-tl jx-bar-rounded-tr");
				}
			}
			$("#jx-uhid-con-id").addClass("jx-bar-button");
			if (defaults.roundedButtons) {
				$("#jx-uhid-con-id").addClass("jx-bar-button-rounded");
			}
			
			// add click event on show/unhide button
			$("#jx-uhid-con-id").click(function() {
				$(this).slideToggle(defaults.slideSpeed, function() {
					$(this).createCookie("JXHID", false); // set bar hide to false
					if (!$(this).checkCookie("JXID")) { // check if cookie JXID exists, if not create one
						$(this).createCookie("JXID", $(this).genRandID()); // set random ID and drop cookie
					}
					$(obj).slideToggle(defaults.slideSpeed);
					if (active_button_name != "") {
						$("#jx-menu-con-id").fadeIn();
					}
				});
				return false;
			});

			// create tooltip container
			$("<div />").attr("id", "jx-ttip-con-id").appendTo("body"); // create div element and append in html body
			$("#jx-ttip-con-id").css({
				"height": "auto",
				"margin-left": "0px",
				"width": "100%", // use entire width
				"overflow": constants["constOverflow"],
				"position": pos
			});
			
			// set tooltip container: top or bottom
			if (defaults.showOnTop) {
				$("#jx-ttip-con-id").css({
					"margin-top": $(this).height() + 3, // put spacing between tooltip container and fixed bar
					"top": constants["constBottom"]
				});
			} else {
				$("#jx-ttip-con-id").css({
					"margin-bottom": $(this).height() + 3, // put spacing between tooltip container and fixed bar
					"bottom": constants["constBottom"]
				});
			}
			
			// prevent browser from showing tooltip; replace title tag with alt tag; comply with w3c standard
			$("li", obj).each(function() {
				var _title = $(this).attr("title");
				if (_title != "") {
					$(this).removeAttr("title");
					$(this).attr("alt", _title);
				}
			});
			
			// bar container hover in and out event handler
			$("li", obj).hover(
				function () { // in/over event
					var elemID = $(this).attr("id"); // get ID (w/ or w/o ID, get it anyway)					
					var barTooltipID = elemID + "jx-ttip-id"; // set a tooltip ID
					var tooltipTitle = $(this).attr("title");
					
					if (tooltipTitle == "") { // if no 'title' attribute then try 'alt' attribute
						tooltipTitle = $(this).attr("alt"); // this prevents IE from showing its own tooltip
					}
					
					if (tooltipTitle != "") { // show a tooltip if it is not empty
						// create tooltip wrapper; fix IE6's float double-margin bug
						barTooltipWrapperID = barTooltipID + "_wrapper";
						$("<div />").attr("id", barTooltipWrapperID).appendTo("#jx-ttip-con-id");
						// create tooltip div element and put it inside the wrapper
						$("<div />").attr("id", barTooltipID).appendTo("#" + barTooltipWrapperID);
						
						// tooltip default style
						$("#" + barTooltipID).css({
							"float": "left"
						});
						
						// theme for tooltip (theme)
						if (defaults.showOnTop) {
							$("<div />").addClass("jx-tool-point-dir-up").appendTo("#" + barTooltipID);
						}
							$("<div />").html(tooltipTitle).addClass("jx-bar-button-tooltip").appendTo("#" + barTooltipID);
							
						if (!defaults.showOnTop) {
							$("<div />").addClass("jx-tool-point-dir-down").appendTo("#" + barTooltipID);
						}
						
						// fix tooltip wrapper relative to the associated button
						lft_pad = parseInt($(this).css("padding-left"));
						$("#" + barTooltipWrapperID).css({
							"margin-left": ($(this).offset().left - ($("#" + barTooltipID).width() / 2)) + ($(this).width()/2) + lft_pad  
						});
						
						if ((($(this).find("a:first").attr("name") == "") || (button_active == false))) {
							$("#" + barTooltipID).fadeTo(defaults.tooltipFadeSpeed, defaults.tooltipFadeOpacity);
						} else if (active_button_name != $(this).find("a:first").attr("name")) {
							$("#" + barTooltipID).fadeTo(defaults.tooltipFadeSpeed, defaults.tooltipFadeOpacity);
						} else {
							$("#" + barTooltipID).css({ // prevent the tooltip from showing; if button if currently on-clicked mode
								"display": "none"
							});
						}
						
					}
				}, 
				function () { // out event
					var elemID = $(this).attr("id"); // get ID (whether there is an ID or none)					
					var barTooltipID = elemID + "jx-ttip-id"; // set a tooltip ID
					var barTooltipWrapperID = barTooltipID + "_wrapper";
					$("#" + barTooltipID).remove(); // remove tooltip element
					$("#" + barTooltipWrapperID).remove(); // remove tooltip's element DIV wrapper
				}
			);
			
			// show/unhide container hover in and out event handler
			$("li", $("#jx-uhid-con-id")).hover(
				function () { // in/over event
					var elemID = $(this).attr("id"); // get ID (w/ or w/o ID, get it anyway)					
					var barTooltipID = elemID + "jx-ttip-id"; // set a tooltip ID
					var tooltipTitle = $(this).attr("title");
					
					if (tooltipTitle == "") { // if no 'title' attribute then try 'alt' attribute
						tooltipTitle = $(this).attr("alt"); // this prevents IE from showing its own tooltip
					}
					
					if (tooltipTitle != "") { // show a tooltip if it is not empty
						// create tooltip wrapper; fix IE6's float double-margin bug
						barTooltipWrapperID = barTooltipID + "_wrapper";
						$("<div />").attr("id", barTooltipWrapperID).appendTo("#jx-ttip-con-id");
						// create tooltip div element and put it inside the wrapper
						$("<div />").attr("id", barTooltipID).appendTo("#" + barTooltipWrapperID);
						
						// tooltip default style
						$("#" + barTooltipID).css({
							"float": "left"
						});
						
						// theme for show/unhide tooltip
						if (defaults.showOnTop) {
							$("<div />").addClass("jx-tool-point-dir-up").appendTo("#" + barTooltipID);
						}
							$("<div />").html(tooltipTitle).addClass("jx-bar-button-tooltip").appendTo("#" + barTooltipID);
						if (!defaults.showOnTop) {
							$("<div />").addClass("jx-tool-point-dir-down").appendTo("#" + barTooltipID);
						}
						
						// fix tooltip wrapper relative to the associated button
						ulft_pad = parseInt($(this).css("padding-left"));
						$("#" + barTooltipWrapperID).css({
							"margin-left": ($(this).offset().left - ($("#" + barTooltipID).width() / 2)) + ($(this).width()/2) + ulft_pad
						});
						
						if ((($(this).find("a:first").attr("name") == "") || (button_active == false))) {
							$("#" + barTooltipID).fadeTo(defaults.tooltipFadeSpeed, defaults.tooltipFadeOpacity);
						} else if (active_button_name != $(this).find("a:first").attr("name")) {
							$("#" + barTooltipID).fadeTo(defaults.tooltipFadeSpeed, defaults.tooltipFadeOpacity);
						} else {
							// prevent the tooltip from showing; if button if currently on clicked mode
							$("#" + barTooltipID).css({
								"display": "none"
							});
						}
						
					}
				}, 
				function () { // out event
					var elemID = $(this).attr("id"); // get ID (whether there is an ID or none)					
					var barTooltipID = elemID + "jx-ttip-id"; // set a tooltip ID
					var barTooltipWrapperID = barTooltipID + "_wrapper";
					$("#" + barTooltipID).remove(); // remove tooltip element
					$("#" + barTooltipWrapperID).remove(); // remove tooltip's element DIV wrapper
				}
			);

			// fix PNG transparency problem in IE6
			if ($.browser.msie && ie6) {
				$(this).find("li").each(function() {
					$(this).find("img").each(function() {
						imgPath = $(this).attr("src");
						altName = $(this).attr("alt");
						srcText = $(this).parent().html();
						$(this).parent().html( // wrap with span element
							'<span style="cursor:pointer;display:inline-block;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=\'' + imgPath + '\');">' + srcText + '</span>' + altName
						);
					});
					$(this).find("img").each(function() {
						$(this).attr("style", "filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0);"); // show image
					})
				});
			}
			
			// adjust bar on window resize event
			$(window).resize(
				function(){
					var screen = jQuery(this);
					var screenWidth = screen.width(); // get current screen width
					var centerScreen = (screenWidth / 2) * (1); // get current screen center
					var marginLeft = centerScreen - ($(obj).width() / 2); // re-calculate and adjust bar's position
					$(obj).css({"margin-left": marginLeft});
				}
			);
			
			/**
			 * Element click events
			 */
		
			// hide first level menu
			$("li", obj).find("ul").each(function() {
				$(this).css({"display": "none"});
			});

			// create menu ID
			i = 1;
			$("li", obj).find("ul").each(function() {
				$(this).attr("id", "nav-" + i);
				$(this).parent().find("a:first").attr("href", "#"); // replace href attribute
				$(this).parent().find("a:first").attr("name", "nav" + i); // replace href attribute				

				if (defaults.showOnTop) {
					buttonIndicator = "jx-arrow-down";
				} else {
					buttonIndicator = "jx-arrow-up";
				}
				$("<div />").attr("class", buttonIndicator).insertAfter($(this).parent().find("a"));
				
				// add click event
				$(this).parent().find("a:first").click(function() {
					var elemID = $(this).attr("id"); // get ID (whether there is an ID or none)					
					var barTooltipID = elemID + "jx-ttip-id"; // set a tooltip ID
					var barTooltipWrapperID = barTooltipID + "_wrapper";
					
					$("#" + barTooltipID).remove(); // remove tooltip element
					$("#" + barTooltipWrapperID).remove(); // remove tooltip's element DIV wrapper

					if ((button_active) && (active_button_name == $(this).attr("name"))) {
						if (defaults.showOnTop) {
							buttonIndicator = "jx-arrow-down";
						} else {
							buttonIndicator = "jx-arrow-up";
						}
						$(this).parent().find("div").attr("class", buttonIndicator);
						
						$("#jx-menu-con-id").fadeOut(defaults.menuFadeSpeed); // remove menu
						$(this).parent().removeClass("jx-nav-menu-active");

						if (defaults.roundedButtons) {
							$(this).parent().removeClass("jx-nav-menu-active-rounded");
						}
						
						button_active = false;
						active_button_name = "";
						$(this).blur(); // unfocus link/href
					} else {
						if (defaults.showOnTop) {
							buttonIndicator = "jx-arrow-up";
						} else {
							buttonIndicator = "jx-arrow-down";
						}
						$(this).parent().find("div").attr("class", buttonIndicator);
						
						$("#jx-menu-con-id").css({"display": "none"});
						$("#jx-menu-con-id").html("<ul>" + $(this).parent().find("ul").html() + "</ul>");
						$("#jx-menu-con-id").css({
												"overflow": constants["constOverflow"],
												"position": pos,
												"margin-left": $(this).parent().offset().left
											});

						// set menu container location: top or bottom
						if (defaults.showOnTop) {
							$("#jx-menu-con-id").css({
								"top": constants["constBottom"],
								"margin-top": $(obj).height() + 6
							});
						} else {
							$("#jx-menu-con-id").css({
								"bottom": constants["constBottom"],
								"margin-bottom": $(obj).height() + 6
							});
						}
						
						$("#jx-menu-con-id").addClass("jx-nav-menu");

						if (defaults.roundedButtons) {
							$("#jx-menu-con-id").addClass("jx-nav-menu-rounded");
						}
						
						$(this).parent().addClass("jx-nav-menu-active");
						if (defaults.roundedButtons) {
							$(this).parent().addClass("jx-nav-menu-active-rounded");
						}
						if (active_button_name != "") {
							$("a[name='" + active_button_name + "']").parent().removeClass("jx-nav-menu-active");
							$("a[name='" + active_button_name + "']").parent().removeClass("jx-nav-menu-active-rounded");
							
							if (defaults.showOnTop) {
								buttonIndicator = "jx-arrow-down";
							} else {
								buttonIndicator = "jx-arrow-up";
							}
							$("a[name='" + active_button_name + "']").parent().find("div").attr("class", buttonIndicator);
						}
						
						button_active = true;
						active_button_name = $(this).attr("name");
						$(this).blur(); // unfocus link/href
						
						$("#jx-menu-con-id").fadeIn(defaults.menuFadeSpeed);
					}
					return false;
				});
				
				i = i + 1;
			});
			
			// nav items click event
			$("li", obj).click(function () {
				if ($("ul", this).exists()) {
					$(this).find("a:first").click();
					return false;
				} else if ($(this).parent().attr("id") == "jx-hid-con-id") {
					// do nothing
					return false;
				}
				window.location = $(this).find("a:first").attr("href");
				return false;
			});
			
		});
		
		return this;
		
	};
	
})(jQuery);

jQuery.fn.exists = function(){return jQuery(this).length>0;};

/**
 * Create a cookie
 */
jQuery.fn.createCookie = function(cookie_name, value) {
	var expiry_date = new Date(2037, 01, 01); // virtually, never expire!
	document.cookie = cookie_name + "=" + escape(value) + ";expires=" + expiry_date.toUTCString();
};

/**
 * Check cookie
 */
jQuery.fn.checkCookie = function(cookie_name) {
	if (document.cookie.length > 0) {
  		cookie_start = document.cookie.indexOf(cookie_name + "=");
  			if (cookie_start != -1) {
    			cookie_start = cookie_start + cookie_name.length + 1;
    			cookie_end = document.cookie.indexOf(";", cookie_start);
    			if (cookie_end == -1) cookie_end = document.cookie.length
    				return true;
			}
  	}
	return false;
}

/**
 * Extract cookie value
 */
jQuery.fn.extractCookieValue = function(value) {
	  if ((endOfCookie = document.cookie.indexOf(";", value)) == -1) {
	     endOfCookie = document.cookie.length;
	  }
	  return unescape(document.cookie.substring(value, endOfCookie));
}

/**
 * Read cookie
 */
jQuery.fn.readCookie = function(cookie_name) {
	  var numOfCookies = document.cookie.length;
	  var nameOfCookie = cookie_name + "=";
	  var cookieLen = nameOfCookie.length;
	  var x = 0;
	  while (x <= numOfCookies) {
	        var y = (x + cookieLen);
	        if (document.cookie.substring(x, y) == nameOfCookie)
	           return (this.extractCookieValue(y));
	           x = document.cookie.indexOf(" ", x) + 1;
	           if (x == 0){
	              break;
	           }
	  }
	  return (null);
}	

/**
 * Generate random ID
 */
jQuery.fn.genRandID = function() {
	var id = "";
	var str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	for(var i=0; i < 24; i++) {
		id += str.charAt(Math.floor(Math.random() * str.length));
	}
    return id;
}

// end jixedbar plugin