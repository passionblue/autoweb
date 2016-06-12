$(document).ready(function(){
	
	$("#orderedlist li:list").hover(function() {
	     $(this).addClass("green");
	   },function(){
	     $(this).removeClass("green");
	 });

	 $(document).ready(function() {
	   $("#ul-test > li").addClass("blue");
	 });

	 $("#ul-test").find("li").each(function(i) {
	     $(this).append( " BAM! " + i );
	 });
	
	
	// generate markup
	   $("#rating").append("Please rate: ");
	   
	   for ( var i = 1; i <= 5; i++ )
	     $("#rating").append("<a href='#'>" + i + "</a> ");
	   
	   // add markup to container and apply click handlers to anchors
	   $("#rating a").click(function(e){
	     // stop normal link click
	     e.preventDefault();
	     
	     // send request
	     $.post("rate.php", {rating: $(this).html()}, function(xml) {
	       // format and output result
	       $("#rating").html(
	         "Thanks for rating, current average: " +
	         $("average", xml).text() +
	         ", number of votes: " +
	         $("count", xml).text()
	       );
	     });
	   });
	   
	   //################# Hide-show example 
	   
	   $("#hideit").click(function () {
		      $("#hidethis").hide();
			   $.get("jquery_text/donotuse.html");
		      return false;
	    });
	   
	   $("#showit").click(function () {
		      $("#hidethis").show();
		      return false;
		    });

	   //################# Slide-up example ################################ 
	   
	   $("#mybutton p").click(function () {
		   $(this).slideUp(); 
	    });

	   $("p#mybutton2").click(function () {
		   $(this).slideUp(); 
	    });
	   
	   //################# Image radio exmaple ################################ 
	   // Image Selection related  
	   $("input#option").val("none");
	   $("a#submit").click(function (e) {

		   var value = $("input#option").val();
		   if (value == "none" ){
			   alert("ddd");
			   e.preventDefault();
			   return;
		   }
		   
		   var value = $("select#selectDown").val();
		   alert(value);
		   if (value == "1" ){
			   alert("dddd");
			   e.preventDefault();
			   return;
		   }
		   
	    });	   
	   
	   $("table#buttontable td#tdoption1").click(function () {
		   $("table#buttontable #tdoption2").removeClass("option-selected"); 
		   $("table#buttontable #tdoption2").addClass("option-unselected"); 
		   $("table#buttontable #tdoption1").addClass("option-selected");
		   $("input#option").val("option1");
	    });

	   $("table#buttontable td#tdoption2").click(function () {
		   $("table#buttontable #tdoption1").removeClass("option-selected"); 
		   $("table#buttontable #tdoption1").addClass("option-unselected");
		   $("table#buttontable #tdoption2").addClass("option-selected"); 
		   $("input#option").val("option2");
	    });
	   //################# AJAX exmaple ################################ 

	   $("#getit").click(function () {
		     // send request
		     $.get("jquery_text/donotuse.html", function() {
		       // format and output result
		       $("#resp").html("Thanks for rasting, current average: ");
		     });
		      return false;
	    });

	   $("#getit2").click(function () {
		     // send request
		     $.get("jquery_text/donotuse.html", function(xml) {
		       // format and output result
			       $("#resp2").html("<img src=\"/images/icons/32px-Crystal_Clear_action_view_bottom.png\"/>Thanks for rasting, current average: " + $("result", xml).text()).hide().fadeIn(1500);
			       $("#resp_out").html("Thanks for rasting, current average: " + $("result", xml).text()).hide().fadeIn(1500);
		     });
		      return false;
	    });
	   
	   $("#splashLoading").click(function (e) {
		   
		   $.ajax({
			   type: "GET",
			   url: "/jquery_text/donotuse.html?data=true&pause=5000",
//			   data: "name=John&location=Boston",
			   beforeSend: function(msg){
				   	$("#splashDisplay").append("beforeSend/");
				   	$('#dialog2').jqmShow(); 
 			   },
		   	   ajaxSend: function(msg){
				   	$("#splashDisplay").append("ajaxSend/");
			   },
		   	   success: function(msg){
				   	$("#splashDisplay").append("success/");
				   	alert(msg);
			   },
		   	   ajaxSuccess: function(msg){
				   	$("#splashDisplay").append("ajaxSuccess/");
			   },
		   	   error: function(msg){
				   	$("#splashDisplay").append("error/");
			   },
		   	   ajaxError: function(msg){
				   	$("#splashDisplay").append("ajaxError/");
			   },
		   	   complete: function(msg){
				   	$("#splashDisplay").append("complete/");
				   	$('#dialog2').jqmHide(); 
			   },
		   	   ajaxComplete: function(msg){
				   	$("#splashDisplay").append("ajaxComplete/");
			   }
			 });
		   
		   		
		   
		   e.preventDefault();
	   });
	   
	   $("#splashLoading2").click(function (e) {
		   
		   $.ajax({
			   type: "GET",
//			   url: "/jquery_text/donotuse.html?data=true&pause=1000",
			   url: "/home.html",
//			   data: "name=John&location=Boston",
			   beforeSend: function(msg){
				   	$("#splashDisplay2").append("beforeSend/");
				   	$('#dialog2').jqmShow(); 
 			   },
		   	   ajaxSend: function(msg){
				   	$("#splashDisplay2").append("ajaxSend/");
			   },
		   	   success: function(msg){
				   	$("#splashDisplay2").append("success/");
				   	
				   	
				   	try //Internet Explorer
		              {
		              xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
		              xmlDoc.async="false";
		              xmlDoc.loadXML(msg);
		              }
		            catch(e)
		              {
		              try // Firefox, Mozilla, Opera, etc.
		                {
		                parser=new DOMParser();
		                xmlDoc=parser.parseFromString(msg,"text/xml");
		                }
		              catch(e)
		                {
		                alert(e.message);
		                return;
		                }
		              }

				   	
			   },
		   	   ajaxSuccess: function(msg){
				   	$("#splashDisplay2").append("ajaxSuccess/");
			   },
		   	   error: function(msg){
				   	$("#splashDisplay2").append("error/");
			   },
		   	   ajaxError: function(msg){
				   	$("#splashDisplay2").append("ajaxError/");
			   },
		   	   complete: function(msg){
				   	$("#splashDisplay2").append("complete/");
				   	$('#dialog2').jqmHide(); 
			   },
		   	   ajaxComplete: function(msg){
				   	$("#splashDisplay2").append("ajaxComplete/");
			   }
			 });
		   
		   		
		   
		   e.preventDefault();
	   });
	 //################# AJAX exmaple ################################
	   
	 //for each description div...
		$('div.description').each(function(){
			//...set the opacity to 0...
			$(this).css('opacity', 0);
			//..set width same as the image...
			$(this).css('width', $(this).siblings('a').find('img').width());
			//...get the parent (the wrapper) and set it's width same as the image width... '
			$(this).parent().css('width', $(this).siblings('img').width());
			//...set the display to block
			$(this).css('display', 'block');
			
			//alert($(this).siblings('a').find('img').width());
		});
		
		$('div.wrapper').hover(function(){
			//when mouse hover over the wrapper div
			//get it's children elements with class descriptio
			//and show it using fadeTo
			$(this).children('.description').stop().fadeTo(500, 0.6);
		},function(){
			//when mouse out of the wrapper div
			//use fadeTo to hide the div
			$(this).children('.description').stop().fadeTo(500, 0);
		});

		 //################# AJAX exmaple ################################
   		   $("#wait").hide(); 
	 		$("#loadcontent").hide();
		   $("#getit3").click(function () {
			   
			     // send request
			     $.ajax({
			    	type: "GET",
			     	url: "/jquery_text/donotuse.html",
			     	data: "data=true",
			     	beforeSend: function() { 
			    	 		$("#loadcontent").hide();
  		    	   		   $("#getit3-wrapper").hide(); 
			    	 		$("#wait").show(); 
			    	 },
			     	complete: function() { 
			    	 			$('#wait').hide(); 
			    	 },
			     	success: function(text) {
	    	   		   $("#getit3-wrapper").show(); 
			     		$("#loadcontent").show("slow");
			     		$("#loadcontent").html(text);
			     	}
			     })
			     
   	   		   $('#getit3').show(); 

			     return false;
		    });
		   
		   //################# AJAXIFY exmaple ################################
			$('.ajaxify').ajaxify();
			$('a#options').ajaxify({
		              link:'/jquery_text/donotuse.html?data=true',
		              target: '#container',
		              loading_img:'images/wait.gif',
		              title:'Setting up options', // change page title. Since v2.0
		              method:'GET'});
			
			$('a#options2').ajaxify({
	              link:'/jquery_text/donotuse.html?random=true',
	              target: '#container2',
	              loading_img:'images/loader/arrows16.gif',
	              title:'Adding cart', // change page title. Since v2.0
	              method:'GET'});

			//################# FORM CHANGE exmaple ################################

		   $("#changetest").click(function () {
			   $("#empty").html('<a href="javascript:;" onclick="go();">Link text</a>').show("normal");
		   });
		   
		   $("#remove").click(function () {
			   $("#xxx").remove();
		   });
		   
		   //### TABS by jquery UI ##############
			$('#mytabs').tabs();
			$('#mytabs2').tabs({ event: 'mouseover' });

			
			
			//################# Accordion Menu Sample ################################
			$("#accordion > li").hover(function(){

				if(false == $(this).next().is(':visible')) {
					$('#accordion > ul').slideUp(300);
				}
				$(this).next().slideToggle(300);
			});

			//$('#accordion > ul:eq(0)').show();
			
			 //################# Color box ################################
			//Examples of how to assign the ColorBox event to elements
			//Examples of how to assign the ColorBox event to elements
			$("a[rel='cbexample1']").colorbox();
			$("a[rel='cbexample2']").colorbox({transition:"fade"});
			$("a[rel='cbexample3']").colorbox({transition:"none", width:"75%", height:"75%"});
			$("a[rel='cbexample4']").colorbox({slideshow:true});
			$(".cbexample5").colorbox();
			$(".cbexample6").colorbox({iframe:true, innerWidth:425, innerHeight:344});
			$(".cbexample7").colorbox({width:"80%", height:"80%", iframe:true});
			$(".cbexample8").colorbox({width:"50%", inline:true, href:"#inline_example1"});
			$(".cbexample9").colorbox({
				onOpen:function(){ alert('onOpen: colorbox is about to open'); },
				onLoad:function(){ alert('onLoad: colorbox has started to load the targeted content'); },
				onComplete:function(){ alert('onComplete: colorbox has displayed the loaded content'); },
				onCleanup:function(){ alert('onCleanup: colorbox has begun the close process'); },
				onClosed:function(){ alert('onClosed: colorbox has completely closed'); }
			});
			
			//Example of preserving a JavaScript event for inline calls.
			$("#cbclick").click(function(){ 
				$('#cbclick').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Open this window again and this message will still be here.");
				return false;
			});
			
			//========================================================================================
			//################# Fancy Box Example ####################################################
			//========================================================================================
			
			if ( document.getElementById("fbexample1")!= null){

			 /* This is basic - uses default settings */ 
			$("a#fbexample1").fancybox({
				'titleShow'		: false
			});

			$("a#fbexample2").fancybox({
				'titleShow'		: false,
				'transitionIn'	: 'elastic',
				'transitionOut'	: 'elastic'
			});

			$("a#fbexample3").fancybox({
				'titleShow'		: false,
				'transitionIn'	: 'none',
				'transitionOut'	: 'none'
			});

			$("a#fbexample4").fancybox();

			$("a#fbexample5").fancybox({
				'titlePosition'	: 'inside'
			});

			$("a#fbexample6").fancybox({
				'titlePosition'	: 'over'
			});
			$("a[rel=example_group]").fancybox({
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
				'titlePosition' 	: 'over',
				'titleFormat'		: function(title, currentArray, currentIndex, currentOpts) {
					return '<span id="fancybox-title-over">Image ' + (currentIndex + 1) + ' / ' + currentArray.length + (title.length ? ' &nbsp; ' + title : '') + '</span>';
				}
			});

			$("#fbvarious1").fancybox({
				'titlePosition'		: 'inside',
				'transitionIn'		: 'none',
				'transitionOut'		: 'none'
			});

			$("#fbvarious2").fancybox();

			$("#fbvarious3").fancybox({
				'width'				: '75%',
				'height'			: '75%',
				'autoScale'			: false,
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
				'type'				: 'iframe'
			});

			$("#fbvarious4").fancybox({
				'padding'			: 0,
				'autoScale'			: false,
				'transitionIn'		: 'none',
				'transitionOut'		: 'none'
			});
			
			}

			
			//========================================================================================
			//################# JSON Example
			// return example from flickr
			//========================================================================================
/*			
			jsonp1259853705249({
				"title": "Recent Uploads tagged cat",
				"link": "http://www.flickr.com/photos/tags/cat/",
				"description": "",
				"modified": "2009-12-03T15:11:26Z",
				"generator": "http://www.flickr.com/",
				"items": [
				   {
				"title": "The stuff on the piano...",
				"link": "http://www.flickr.com/photos/hulpy/4155015053/",
				"media": {"m":"http://farm3.static.flickr.com/2712/4155015053_a23fde9588_m.jpg"},
				"date_taken": "2009-12-01T20:02:50-08:00",
				"description": "<p><a href=\"http://www.flickr.com/people/hulpy/\">Hulpy (Carmen)<\/a> posted a photo:<\/p> <p><a href=\"http://www.flickr.com/photos/hulpy/4155015053/\" title=\"The stuff on the piano...\"><img src=\"http://farm3.static.flickr.com/2712/4155015053_a23fde9588_m.jpg\" width=\"240\" height=\"160\" alt=\"The stuff on the piano...\" /><\/a><\/p> ",
				"published": "2009-12-03T15:11:26Z",
				"author": "nobody@flickr.com (Hulpy (Carmen))",
				"author_id": "38510803@N06",
				"tags": "pictures cat photo frames chat foto photos tabby decoration picture gato fotos frame katze deco gatto kater rahmen pictureframe deko dekoration getigert bilderrahmen fotorahmen"
				   },
				   {
				"title": "IMG_1176",
				"link": "http://www.flickr.com/photos/ch3nks/4155012803/",
				"media": {"m":"http://farm3.static.flickr.com/2541/4155012803_72484940ba_m.jpg"},
				"date_taken": "2009-12-03T15:53:24-08:00",
				"description": "<p><a href=\"http://www.flickr.com/people/ch3nks/\">Ch3nks<\/a> posted a photo:<\/p> <p><a href=\"http://www.flickr.com/photos/ch3nks/4155012803/\" title=\"IMG_1176\"><img src=\"http://farm3.static.flickr.com/2541/4155012803_72484940ba_m.jpg\" width=\"240\" height=\"180\" alt=\"IMG_1176\" /><\/a><\/p> ",
				"published": "2009-12-03T15:10:20Z",
				"author": "nobody@flickr.com (Ch3nks)",
				"author_id": "23765769@N02",
				"tags": "cat"
				   }
				        ]
				})
*/			
			   //################# JSON exmaple ################################
			   
			$.getJSON("http://api.flickr.com/services/feeds/photos_public.gne?tags=cat&tagmode=any&format=json&jsoncallback=?",
				function(data){
			       	$.each(data.items, function(i,item){
			       		$("<img width='100px'/>").attr("src", item.media.m).appendTo("#jsonimages");
			       		if ( i == 3 ) return false;
			       	});
			    });

			//alert("end");
			
			// ### CEEBOX
			debugging = true;

			$.fn.ceebox.videos.base.param.allowScriptAccess = "sameDomain" //added to kill the permissions problem
			$.extend($.fn.ceebox.videos,{
				uctv:{
					siteRgx: /uctv\.tv\/search\-details/i, 
					idRgx: /(?:showID=)([0-9]+)/i, 
					src: "http://www.uctv.tv/player/player_uctv_bug.swf",
					flashvars: {previewImage : "http://www.uctv.tv/images/programs/[id].jpg", movie : "rtmp://webcast.ucsd.edu/vod/mp4:[id]",videosize:0,buffer:1,volume:50,repeat:false,smoothing:true}
				}
			});
			//$().ceebox(); //used to test to make sure the init call works.
			//$(".ceebox").ceebox({boxColor:'#fff',borderColor:'#525252',textColor:'#333',videoJSON:"js/humor.json"});
			$(".ceebox").ceebox({borderColor:'#dcdcdc',boxColor:"#fff"});
			//$("map").ceebox({fadeOut:"slow",fadeIn:"slow",onload:function(){$("#cee_box").animate({backgroundColor:"#F00"},function(){$(this).animate({backgroundColor:"#fff"})});}});		
			$("map").ceebox();		
			$(".ceebox2").ceebox({unload:function(){$("body").css({background:"#ddf"})}});
			//window.console.log($.fn.ceebox.videos.colbertFull)
			//$("body").ceebox(); //uncomment and every link on the page is in one gallery
			var testhtml = "<a href='http://balsaman.org' title='Balsa Man'>Balsa Man</a>"
			var testhtml2 = "<div style='padding:20px;text-align:center'><h2>Hi I am some content built as a javascript variable!</h2><p><a href='#' class='cee_close'>Close Me</a></p></div>"
			$("#testlink").click(function(){
				$.fn.ceebox.overlay();
				$.fn.ceebox.popup(testhtml,{onload:true,htmlWidth:600,htmlHeight:450});
				return false;		  
			});
			$("#testlink2").click(function(){
				$.fn.ceebox.overlay();
				$.fn.ceebox.popup(testhtml2,{width:600,height:400});
				return false;
			});
			//$.fn.ceebox.popup(testhtml,{onload:true,htmlWidth:600,htmlHeight:450});

			
			// =========================== dIALOG BY UI ================================
			// a workaround for a flaw in the demo system (http://dev.jqueryui.com/ticket/4375), ignore!
			$("#dialog").dialog("destroy");


			
			$("#dialog-form").dialog({
				autoOpen: false,
				modal: true,
				buttons: {
					'Create an account': function() {
						alert("create");
					$(this).dialog('close');
					},
					Cancel: function() {
						$(this).dialog('close');
					}
				},
				close: function() {
					$("#dialog-input").val("xxx");
				},
				
				open: function() {
					$("#dialog-form #loading-dialog").show();
					// The example shows how to resize and center with jquery but has to add 26 to accurately stablize the size. 
					// the best way is expect the data type and fixed window size from the beginning. 
					 //$.get("/devNoteAction.html?ajaxRequest=true&ajaxOut=getlisthtml&formfieldlist=note,completed", function(data){
					 $.get("/testDataDepotAction.html?ajaxRequest=true&ajaxOut=getfieldbyname&fieldname=data&id=4", function(data){
						 var dw = $("#dialog-form").width();

						 $("#dialog-form #hereItGoes").html("");
						 $("#dialog-form #hereItGoes").html(data);
							
						 $("#dialog-form #loading-dialog").hide();
						 var dataw = $("#dialog-form #hereItGoes").width();

						 var newboxwidth = dataw + 200;
						
						 if ( dw >= dataw) {
							 newboxwidth = 	dw;
						}

						$("#dialog-form").dialog("option", "width", newboxwidth+26);
						$("#dialog-form").dialog("option", "height", $('#dialog-form #hereItGoes').height()+200);
						$("#dialog-form").dialog( "option", "position", 'center' );
					 });
					 $("#devNoteAjaxTable").dataTable();
					 
				}
			});
			
			
			$('#create-user')
				.button()
				.click(function() {
					$('#dialog-form').dialog('open');
				});
			

			$("#picked").click(function(){
				document.getElementById("pickedval").value="xxx";
			});
//			alert('x');

			//################# jqModal Confirmation ################################

		  $('#confirm').jqm({overlay: 88, modal: true, trigger: false});
		  $('#dialog2').jqm({overlay: 50, modal: true, trigger: false,
			  onShow: function(h) {
			 
			  	//How to center the dialog 
			    var hi =  ( $(window).height() - h.w.height() ) / 2 + $(window).scrollTop() + "px";
			    var wi =  ( $(window).width() - h.w.width() ) / 2 + $(window).scrollLeft() + "px";
			    
			    h.w.css("position","absolute");
			    h.w.css("top",hi);
			    h.w.css("left",wi);
			    
		        h.w.css('opacity',0.92).fadeIn(1000); 
		        },
		      onHide: function(h) {
		        /* callback executed on window hide. Hide notice, overlay. */
		        h.w.fadeOut(1000,function() { if(h.o) h.o.remove(); }); } 
		  });
		  // trigger a confirm whenever links of class alert are pressed.
		  $('a.confirm').click(function() { 
			  confirm('About to visit: '+this.href+' !',this.href); 
			  return false;
		  });
		  
		  
		  
		  
			//################# cluetip Confirmation ################################
		  $('a.tips').cluetip();
		  
		  $('#houdini').cluetip({
		    splitTitle: '|', // use the invoking element's title attribute to populate the clueTip...
		                     // ...and split the contents into separate divs where there is a "|"
		    showTitle: false // hide the clueTip's heading
		  });

		  $('a.cttitle').cluetip({splitTitle: '|'});

		  
		  //################# Sliding Menu ################################

		  $("div#sliderOpenClose").hide();
		    $("div#sliderSwitch").click( function() {
		        if (!$("div#sliderOpenClose").is(":visible")) {
		            $("#slider").animate({
		            	top: "0px"
		                }, 500 );
		            $("div#sliderOpenClose").show();
		        } else {
		            $("#slider").animate({
		            	top: "-50px"
		                }, 500 );
		            $("div#sliderOpenClose").hide();
		        }
		    });  		  


			//############ jquery tools ############
			$("#jtdemo img[title]").tooltip();
			$(":date").dateinput();
			$(".scrollable").scrollable();

			
			//========================================================================================
			//################# Dta Table ####################################################
			// http://www.datatables.net/usage/server-side
			//========================================================================================
			
			$('#dt-table-example1').dataTable();

			//#################
			// seems like dataTable's column reads from html header section only. can't add column at this moment. 
			// return data from the server should match with columns in place.  
			

			$('#dt-table-example2').dataTable( {
				"bProcessing": true,
				"bServerSide": true,
				"sAjaxSource": "/pollAction.html?ajaxRequest=true&ajaxOut=getjsondt&fieldlist=type,category,title,question,hide",
				"aoColumns": [
				  			{ "sName": "engine" },
				  			{ "sName": "browser" },
				  			{ "sName": "platform" },
				  			{ "sName": "version" },
				  			{ "sName": "grade" }
				  		],
				"fnServerData": function ( sSource, aoData, fnCallback ) {
				aoData.push( { "name": "more_data", "value": "my_value" } );
				aoData.push( { "name": "more_data2", "value": "my_value" } );
					$.getJSON( sSource, aoData, function (json) { 
						fnCallback(json);
					} );
				}
			});
			
			
			//========================================================================================
			//################# Flex Grid Example ####################################################
			//========================================================================================
			//$('.flexme1').flexigrid();

			///testDataDepotAction.html?ajaxRequest=true&ajaxOut=getfieldbyname&fieldname=data&id=8
			
/*
			$("#flexiex3").flexigrid
			(
			{
			url: 'post2.php',
			dataType: 'json',
			colModel : [
				{display: 'ISO', name : 'iso', width : 40, sortable : true, align: 'center'},
				{display: 'Name', name : 'name', width : 180, sortable : true, align: 'left'},
				{display: 'Printable Name', name : 'printable_name', width : 120, sortable : true, align: 'left'},
				{display: 'ISO3', name : 'iso3', width : 130, sortable : true, align: 'left', hide: true},
				{display: 'Number Code', name : 'numcode', width : 80, sortable : true, align: 'right'}
				],
			buttons : [
				{name: 'Add', bclass: 'add', onpress : test},
				{name: 'Delete', bclass: 'delete', onpress : test},
				{separator: true}
				],
			searchitems : [
				{display: 'ISO', name : 'iso'},
				{display: 'Name', name : 'name', isdefault: true}
				],
			sortname: "iso",
			sortorder: "asc",
			usepager: true,
			singleSelect: true,
			title: 'Countries',
			useRp: true,
			rp: 15,
			showTableToggleBtn: true,
			width: 700,
			height: 200
			}
			);   
*/

			// Server getting following params 
			// id=8,sortname=iso,rp=15,fieldname=data,page=3,sortorder=asc,query=,ajaxRequest=true,ajaxOut=getfieldbyname,qtype=name
			
			$("#flexiex3").flexigrid
			(
			{
				url: 'testDataDepotAction.html?ajaxRequest=true&ajaxOut=getfieldbyname&fieldname=data&id=8',
				dataType: 'json',
				colModel : [
					{display: 'ISO', name : 'iso', width : 40, sortable : true, align: 'center'},
					{display: 'Name', name : 'name', width : 180, sortable : true, align: 'left'},
					{display: 'Printable Name', name : 'printable_name', width : 120, sortable : true, align: 'left'},
					{display: 'ISO3', name : 'iso3', width : 130, sortable : true, align: 'left', hide: true},
					{display: 'Number Code', name : 'numcode', width : 80, sortable : true, align: 'right'},
					{display: 'Processed Data', name : 'numcode2', width : 80, sortable : true, align: 'right', displayProc: displayProcTest}
					],
				buttons : [
					{name: 'Add', bclass: 'add', onpress : fleximenutest},
					{name: 'Delete', bclass: 'delete', onpress : fleximenutest},
					{separator: true}
					],
				searchitems : [
					{display: 'ISO', name : 'iso'},
					{display: 'Name', name : 'name', isdefault: true}
					],
				sortname: "iso",
				sortorder: "asc",
				
				usepager: true,
				singleSelect: true,
				title: 'Countries',
				useRp: true,
				rp: 15,
				showTableToggleBtn: true
				}
			);   

			$('b.top').click
			(
				function ()
					{
						$(this).parent().toggleClass('fh');
					}
			);
			
			$("table#flexiex3 tr").click(function(){
				alert("");
			});

});

function procTest(tdDiv,pid){
	alert(pid);
}


function rowClickProcess(row){
	alert(row[0]);
}
function displayProcTest(row){
	//idx number is based on column definition
	var num = parseInt(row.cell[4]);
	return "<b>test "+(num*100)+"</b>";
}


function fleximenutest(com,grid)
{
	if (com=='Delete')
		{
			confirm('Delete ' + $('.trSelected',grid).length + ' items?')
		}
	else if (com=='Add')
		{
			alert('Add New Item');
		}			
}



function confirm(msg,callback) {
	  $('#confirm')
	    .jqmShow()
	    .find('p.jqmConfirmMsg')
	      .html(msg)
	    .end()
	    .find(':submit:visible')
	      .click(function(){
	        if(this.value == 'yes')
	          (typeof callback == 'string') ?
	            window.location.href = callback :
	            callback();
	        $('#confirm').jqmHide();
	      });
}

function mycallbackfunc(v,m,f){
	$.prompt('i clicked ' + v);
}

function removeUser2(id){
    
	var txt = 'Are you sure you want to remove this?<input type="hidden" id="userid" name="userid" value="'+ id +'" />';
	
	$.prompt(txt,{ 
		buttons:{Delete:true, Cancel:false},
		callback: function(v,m,f){
			if(v){
				var uid = f.userid;
				$.post("/linktoAction.html?", { id: uid, del: "true" },
						   function(data){
						     alert("Data Loaded: " + data);
						   });
			}
		}
	});
}



