/**
 * Note: The functions contained in this file are for demonstration purposes
 * only and are not necessary in your own project.
 */

function initDemos(){
    // setup flash gallery demo, use a css selector
    Shadowbox.setup('a.flash-gallery', {
        gallery:        'Flash',
        continuous:     true,
        counterType:    'skip',
        animSequence:   'sync'
    });

    // setup image map demo
    Shadowbox.setup(document.getElementById('hongkongmap').getElementsByTagName('area'));
}

function demoGoogleMaps(){
    Shadowbox.open({
        player:     'html',
        content:    '',
        height:     300,
        width:      500,
        options:    {
            onFinish: function(item){
                if(GBrowserIsCompatible()){
                    var body = document.getElementById(Shadowbox.contentId());
                    var map = new GMap2(body);
                    map.setCenter(new GLatLng(37.4419, -122.1419), 13);

                    // add 10 markers to the map at random locations
                    var bounds = map.getBounds();
                    var southWest = bounds.getSouthWest();
                    var northEast = bounds.getNorthEast();
                    var lngSpan = northEast.lng() - southWest.lng();
                    var latSpan = northEast.lat() - southWest.lat();
                    for(var i = 0; i < 10; i++){
                        var point = new GLatLng(southWest.lat() + latSpan * Math.random(),
                            southWest.lng() + lngSpan * Math.random());
                        map.addOverlay(new GMarker(point));
                    }

                    // add some simple controls
                    map.addControl(new GSmallMapControl());
                    map.addControl(new GMapTypeControl());
                }
            }
        }
    });
}

function demoMessage(){
    Shadowbox.open({
        player:     'html',
        content:    '<div style="text-align:center;padding-top:10px;">You\'re using <a href="http://www.shadowbox-js.com">Shadowbox</a>!</div>',
        height:     100,
        width:      200
    });
}

function demoGallery(){
    var options = {
        continuous: true
    };

    var img = {
        player:     'img',
        content:    'gallery/aston_martin/vanquish.jpg',
        options:    options
    };

    var video = {
        player:     'iframe',
        content:    'http://www.youtube.com/v/y8Kyi0WNg40&amp;autoplay=1',
        height:     340,
        width:      405,
        title:      'Dramatic Look',
        options:    options
    };

    Shadowbox.open([img, video]);
}

window.onload = initDemos;