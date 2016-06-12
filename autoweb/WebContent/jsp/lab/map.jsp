<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.autosite.db.*,com.seox.work.UserBO,java.util.*,com.autosite.util.*"%>

<script type="text/javascript">
	var geocoder;
	var map;
	window.onload=function() {
		geocoder = new google.maps.Geocoder();
	    var latlng = new google.maps.LatLng(-34.397, 150.644);
	    var myOptions = {
	      zoom: 12,
	      center: latlng,
	      mapTypeId: google.maps.MapTypeId.ROADMAP
	    };
	    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

	    // Second map ================================================================================================
	    var myLatlng2 = new google.maps.LatLng(49.496675,-102.65625);
	    var myOptions2 = {
	      zoom: 4,
	      center: myLatlng2,
	      mapTypeId: google.maps.MapTypeId.ROADMAP
	    }

	    var map2 = new google.maps.Map(document.getElementById("map_canvas2"), myOptions2);

	    var georssLayer = new google.maps.KmlLayer('http://api.flickr.com/services/feeds/geo/?g=322338@N20&lang=en-us&format=feed-georss',{suppressInfoWindows: true});
	    georssLayer.setMap(map2);


	    google.maps.event.addListener(georssLayer, 'click', function(c) {

			//object c has properties like id/status/featureData
			//object c.featureData has props like status/id/name/description/snippet/author/infoWindowHtml/
			// http://code.google.com/apis/maps/documentation/javascript/reference.html#KmlFeatureData
		    
			var names="";
			for(var name in c.featureData) names += name + "/";
		    alert(names);
		    alert(c.featureData.infoWindowHtml);
			showInContentWindow(c.featureData.infoWindowHtml);		    
	    });
	    		    
    }

	var contentString = '<div id="content">'+
    '<div id="siteNotice">'+
    '</div>'+
    '<h1 id="firstHeading" class="firstHeading">Uluru</h1>'+
    '<div id="bodyContent">'+
    '<p><b>Uluru</b>, also referred to as <b>Ayers Rock</b>, is a large ' +
    'sandstone rock formation in the southern part of the '+
    'Northern Territory, central Australia. It lies 335 km (208 mi) '+
    'south west of the nearest large town, Alice Springs; 450 km '+
    '(280 mi) by road. Kata Tjuta and Uluru are the two major '+
    'features of the Uluru - Kata Tjuta National Park. Uluru is '+
    'sacred to the Pitjantjatjara and Yankunytjatjara, the '+
    'Aboriginal people of the area. It has many springs, waterholes, '+
    'rock caves and ancient paintings. Uluru is listed as a World '+
    'Heritage Site.</p>'+
    '<p>Attribution: Uluru, <a href="http://en.wikipedia.org/w/index.php?title=Uluru&oldid=297882194">'+
    'http://en.wikipedia.org/w/index.php?title=Uluru</a> (last visited June 22, 2009).</p>'+
    '</div>'+
    '</div>';

	var infowindow = new google.maps.InfoWindow({
	    content: contentString
	});
	
	function codeAddress() {
	    var address = document.getElementById("address").value;
	    geocoder.geocode( { 'address': address}, function(results, status) {
	      if (status == google.maps.GeocoderStatus.OK) {
		      
	        map.setCenter(results[0].geometry.location);

	        // http://code.google.com/apis/maps/documentation/javascript/reference.html#Marker
	        var marker = new google.maps.Marker({
	            map: map, 
	            position: results[0].geometry.location,
	        });

	    	google.maps.event.addListener(marker, 'click', function() {
				infowindow.open(map,marker);
    		});
	      } else {
		        alert("Geocode was not successful for the following reason: " + status);
	      }
	    });
	  }


	// Second Map ===========================================================================
	function showInContentWindow(text) {
	    var sidediv = document.getElementById('content_window');
	    sidediv.innerHTML = text;
	}
	
</script>


<h2>Map Test Page</h2>


  <div>
    <input id="address" type="textbox" value="29 Stanwood Rd 11040">

    <input type="button" value="Geocode" onclick="codeAddress()">
  </div>


 <div id="map_canvas" style="width:500px; height:600px"></div>
 <div id="map_canvas2" style="width:500px; height:600px"></div>
 
 <div id="content_window" style="width:19%; height:100%; float:left"></div>
 
 