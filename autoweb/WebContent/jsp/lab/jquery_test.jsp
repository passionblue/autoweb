<%@ page language="java" import="com.jtrend.util.*,com.jtrend.session.*,com.autosite.*,com.autosite.session.*,com.autosite.ds.*,com.autosite.db.*,com.seox.work.UserBO,java.util.*,com.autosite.util.*"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

This is my JSP page.

<a id="id1" href="http://www.yahoo.com"> yahoo</a> <br/>

<ul id="ul-test">
	<li>item 1</li>
	<li>item 2</li>
	<li>item 3</li>
	<li>item 44</li>
</ul>

<ol id="ol-test">
	<li>item 1</li>
	<li>item 2</li>
	<li>item 4</li>
	<li>item 3</li>
</ol>

<BR/>

<div id="rating">Container2</div>

<br>
<h3> Hide-show exmaple</h3>
<a id="hideit" > hide </a>
<a id="showit" > show </a>

<div id="hidethis"> <p> This will be hide</p> </div>

<h3> Sliede-up exmaple</h3>

<div id="mybutton">
<p>This is</p>
</div> 

<p id="mybutton2">This is 2</p>

<h3> image radio example</h3>

<form name="jqueryTestForm1" method="post" action="/v_style_config_edit.html">

<table id="buttontable" border="0" cellpadding="0">
	<TR>
		<TD class="option-unselected" id="tdoption1">
			<img width="200" height="150" src="images/samples/ohoopee1.jpg"/>
		</TD>
		<TD class="option-unselected" id="tdoption2">
			<img width="200" height="150" src="images/samples/ohoopee2.jpg"/>
		</TD>
	</TR>
</table>

<INPUT id="option" TYPE="HIDDEN" NAME="option" value="none">
<select id="selectDown" >
<option value="">none</option>
<option value="1">1</option>
</select>

<a id="submit" href="javascript:document.jqueryTestForm1.submit();">Submit</a>			

</form>

<h3> AJAX example </h3>

<a id="getit"> get <span id="resp"></span> </a> <br/>

<a id="getit2"> <img src="/images/icons/32px-Crystal_Clear_action_view_bottom.png"/> <span id="resp2"></span> </a> <br/>

<a id="postit" > post <span></span></a>


<span id="resp_out"></span>

<h3> caption example  </h3>

<div class="wrapper">  
     <a href="#"><img src="images/samples/ohoopee1.jpg" alt="" title="" width="200" height="200"/></a>  
     <div class="description">  
         <div class='description_content'> 
         <a href="http://www.yahoo.com" >sssgdfg fa</a>
         </div>
     </div>  
</div>  
<br/>

<h3> Splash Loading</h3>

<a href="#" id="splashLoading"> get with splash </a> <br/>
<span id="splashDisplay"> </span>

<a href="#" id="splashLoading2"> request with splash </a> <br/>
<span id="splashDisplay2"> </span>



<h3> loading example  </h3>

<div id="getit3-wrapper">
<a id="getit3"> <img src="/images/icons/32px-Crystal_Clear_action_view_bottom.png"/>  </a> <br/>
</div>
<div id="wait"></div>
<div id="loadcontent"></div>

<br>

<h3> Mega menu</h3>



<!--Mega Drop Down Menu HTML. Retain given CSS classes-->
<script type="text/javascript">
jkmegamenu.definemenu("megaanchor", "megamenu1", "click")
</script>

<!--Mega Menu Anchor-->
<div id="megamenu1" class="megamenu">
<div class="column">
	<h3>Web Development</h3>
	<ul>
	<li><div id="picked" style="cursor:pointer;">JavaScript Kit</div></li>
	<li><a href="http://www.dynamicdrive.com/">Dynamic Drive</a></li>
	<li><a href="http://www.cssdrive.com">CSS Drive</a></li>
	<li><a href="http://www.codingforums.com">Coding Forums</a></li>
	<li><a href="http://www.javascriptkit.com/domref/">DOM Reference</a></li>
	</ul>
</div>

<div class="column">
	<h3>News Related</h3>
	<ul>
	<li><a href="http://www.cnn.com/">CNN</a></li>
	<li><a href="http://www.msnbc.com">MSNBC</a></li>
	<li><a href="http://www.google.com">Google</a></li>
	<li><a href="http://news.bbc.co.uk">BBC News</a></li>
	</ul>
</div>

<div class="column">
	<h3>Technology</h3>
	<ul>
	<li><a href="http://www.news.com/">News.com</a></li>
	<li><a href="http://www.slashdot.com">SlashDot</a></li>
	<li><a href="http://www.digg.com">Digg</a></li>
	<li><a href="http://www.techcrunch.com">Tech Crunch</a></li>
	</ul>
</div>

<input id="pickedval" name="pickedval" value="" /> <div id="megamenu1" class="megamenu"> </div> 
<input id="pickedva2" name="pickedva2" value="" /> <div id="megamenu1" class="megamenu"> </div>
<input id="pickedva3" name="pickedva3" value="" /> <div id="megamenu1" class="megamenu"> </div>


<div class="column">
	<h3>Web Development</h3>
	<ul>
	<li><div id="picked" style="cursor:pointer;">JavaScript Kit</div></li>
	<li><a href="http://www.dynamicdrive.com/">Dynamic Drive</a></li>
	<li><a href="http://www.cssdrive.com">CSS Drive</a></li>
	<li><a href="http://www.codingforums.com">Coding Forums</a></li>
	<li><a href="http://www.javascriptkit.com/domref/">DOM Reference</a></li>
	</ul>
</div>

<div class="column">
	<h3>News Related</h3>
	<ul>
	<li><a href="http://www.cnn.com/">CNN</a></li>
	<li><a href="http://www.msnbc.com">MSNBC</a></li>
	<li><a href="http://www.google.com">Google</a></li>
	<li><a href="http://news.bbc.co.uk">BBC News</a></li>
	</ul>
</div>

<div class="column">
	<h3>Technology</h3>
	<ul>
	<li><a href="http://www.news.com/">News.com</a></li>
	<li><a href="http://www.slashdot.com">SlashDot</a></li>
	<li><a href="http://www.digg.com">Digg</a></li>
	<li><a href="http://www.techcrunch.com">Tech Crunch</a></li>
	</ul>
</div>


<br style="clear: left" /> <!--Break after 3rd column. Move this if desired-->

<div class="column">
	<h3>Web Development</h3>
	<ul>
	<li><a href="http://www.javascriptkit.com">JavaScript Kit</a></li>
	<li><a href="http://www.dynamicdrive.com/">Dynamic Drive</a></li>
	<li><a href="http://www.cssdrive.com">CSS Drive</a></li>
	<li><a href="http://www.codingforums.com">Coding Forums</a></li>
	<li><a href="http://www.javascriptkit.com/domref/">DOM Reference</a></li>
	</ul>
</div>

<div class="column">
	<h3>News Related</h3>
	<ul>
	<li><a href="http://www.cnn.com/">CNN</a></li>
	<li><a href="http://www.msnbc.com">MSNBC</a></li>
	<li><a href="http://www.google.com">Google</a></li>
	<li><a href="http://news.bbc.co.uk">BBC News</a></li>
	</ul>
</div>

<div class="column">
	<h3>Technology</h3>
	<ul>
	<li><a href="http://www.news.com/">News.com</a></li>
	<li><a href="http://www.slashdot.com">SlashDot</a></li>
	<li><a href="http://www.digg.com">Digg</a></li>
	<li><a href="http://www.techcrunch.com">Tech Crunch</a></li>
	</ul>
</div>

</div>
<div id="megaanchor2">Tech Links</div>
<a href="http://www.javascriptkit.com" id="megaanchor">Tech Links</a>
<br/>
<br/>
<br/>








<script language='javascript' type='text/javascript'>
function go()
{
    location.href="http://www.nytimes.com"
}
</script>

 <a href="javascript:;" onclick="go();">Link text</a>


<br/>
<br/>


<a id="changetest"> changetest </a> <br/>
<a id="remove"> removetest </a> <br/>

<span id="empty"></span>

<h3> Tab Example</h3>


		<div id="mytabs" class="tabstyle">
			<ul>
				<li><a href="#tabs-1">First</a></li>
				<li><a href="#tabs-2">Second</a></li>
				<li><a href="/jquery_text/donotuse.html?data=true"><span>container 3</span></a></li>
			</ul>
			<div id="tabs-1">

<%
    String _wpId = WebProcManager.registerWebProcess();

%>
						
				<form name="emailSubsForm" method="post" action="/emailSubsAction.html" >
				<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

				                    <TR bgcolor="#ffffff">
				        <TD align="right" ><b>First Name</b> &nbsp;</TD>
				        <TD>&nbsp;<input type="text" name="firstName" value=""/></TD>
				    </TR>
				                    <TR bgcolor="#ffffff">
				        <TD align="right" ><b>Last Name</b> &nbsp;</TD>
				        <TD>&nbsp;<input type="text" name="lastName" value=""/></TD>
				    </TR>
				
				                    <TR bgcolor="#ffffff">
				        <TD align="right" ><b>Email</b> &nbsp;</TD>
				        <TD>&nbsp;<input type="text" name="email" /></TD>
				    </TR>
				                        <TR bgcolor="#ffffff">
				        <TD></TD>  
				        <TD>
				            <b><a href="javascript:document.emailSubsForm.submit();">Submit</a> </b>
				        </TD>
				    </TR>
				</TABLE>    
				
				<INPUT TYPE="HIDDEN" NAME="subject" value="TEST">
				<INPUT TYPE="HIDDEN" NAME="confirmed" value="1">
				<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId %>">
				
				<INPUT TYPE="HIDDEN" NAME="add" value="true">
				</form>						
						
						
			</div>

			<div id="tabs-2">Phasellus mattis tincidunt nibh. Cras orci urna, blandit id, pretium vel, aliquet ornare, felis. Maecenas scelerisque sem non nisl. Fusce sed lorem in enim dictum bibendum.</div>
		</div>

		<!--  change table mouse over -->
		<div id="mytabs2" class="tabstyle">
			<ul>
				<li><a href="#tabs2-1">First</a></li>
				<li><a href="#tabs2-2">Second</a></li>
				<li><a href="/jquery_text/donotuse.html?data=true"><span>container 3</span></a></li>
			</ul>
			<div id="tabs2-1">

<%
    String _wpId2 = WebProcManager.registerWebProcess();

%>
						
				<form name="emailSubsForm" method="post" action="/emailSubsAction.html" >
				<TABLE border="0" width="100%" bgcolor="#99dae8" align="center" cellspacing="1">

				                    <TR bgcolor="#ffffff">
				        <TD align="right" ><b>First Name</b> &nbsp;</TD>
				        <TD>&nbsp;<input type="text" name="firstName" value=""/></TD>
				    </TR>
				                    <TR bgcolor="#ffffff">
				        <TD align="right" ><b>Last Name</b> &nbsp;</TD>
				        <TD>&nbsp;<input type="text" name="lastName" value=""/></TD>
				    </TR>
				
				                    <TR bgcolor="#ffffff">
				        <TD align="right" ><b>Email</b> &nbsp;</TD>
				        <TD>&nbsp;<input type="text" name="email" /></TD>
				    </TR>
				                        <TR bgcolor="#ffffff">
				        <TD></TD>  
				        <TD>
				            <b><a href="javascript:document.emailSubsForm.submit();">Submit</a> </b>
				        </TD>
				    </TR>
				</TABLE>    
				
				<INPUT TYPE="HIDDEN" NAME="subject" value="TEST">
				<INPUT TYPE="HIDDEN" NAME="confirmed" value="1">
				<INPUT TYPE="HIDDEN" NAME="wpid" value="<%= _wpId2 %>">
				
				<INPUT TYPE="HIDDEN" NAME="add" value="true">
				</form>						
						
						
			</div>

			<div id="tabs2-2">Phasellus mattis tincidunt nibh. Cras orci urna, blandit id, pretium vel, aliquet ornare, felis. Maecenas scelerisque sem non nisl. Fusce sed lorem in enim dictum bibendum.</div>
		</div>

<hr/>
<h1> Shadowbox  </h1>

            <h2><a name="demos">Demos</a></h2>

            <h3>Images</h3>

            <a rel="shadowbox" href="http://farm2.static.flickr.com/1177/1150569783_61dbc56834.jpg" class="option">Single Image (Flickr)</a>

            <h3>Galleries</h3>

            <a rel="shadowbox[Aston Martin];options={slideshowDelay:3}" href="images/samples/ohoopee1.jpg" class="option" title="1">Image Gallery (slideshow)</a>
            <a rel="shadowbox[Aston Martin];options={slideshowDelay:5}" href="images/samples/ohoopee2.jpg" class="hidden" style="display:none" title="2">image</a>
            <a rel="shadowbox[Aston Martin];"                           href="images/samples/ohoopee3.jpg" class="hidden" style="display:none" title="3">image</a>

            <h3>Large</h3>

            <a rel="shadowbox;options={handleOversize:'none'}" href="http://www.shadowbox-js.com/gallery/greatwall.jpg" title="Great Wall of China"><img src="http://www.shadowbox-js.com/gallery/greatwall-thumb.jpg" alt="" class="border"></a>
            <a rel="shadowbox"                                 href="http://www.shadowbox-js.com/gallery/greatwall.jpg" title="Great Wall of China"><img src="http://www.shadowbox-js.com/gallery/greatwall-thumb.jpg" alt="" class="border"></a>
            <a rel="shadowbox;options={handleOversize:'drag'}" href="http://www.shadowbox-js.com/gallery/greatwall.jpg" title="Great Wall of China"><img src="http://www.shadowbox-js.com/gallery/greatwall-thumb.jpg" alt="" class="border"></a>
            <h3>Flash</h3>
            <a rel="shadowbox;width=400;height=300" title="Girl Skipping" href="http://www.shadowbox-js.com/gallery/skip.swf"><img src="http://www.shadowbox-js.com/gallery/skip-thumb.gif" alt="" class="border"></a>
            

                <a class="flash-gallery"        rel="width=520;height=390;options={flashParams:{bgcolor:'#ffffff'}}" title="Caveman" href="http://www.shadowbox-js.com/gallery/caveman.swf"><img src="http://www.shadowbox-js.com/gallery/caveman-thumb.gif" alt="" class="border"></a>
                <a class="flash-gallery hidden" rel="width=600;height=450" title="Hollywood or Bust" href="http://www.shadowbox-js.com/gallery/old_man.swf">swf</a>
                <a class="flash-gallery hidden" rel="width=400;height=300" title="Girl Skipping" href="http://www.shadowbox-js.com/gallery/skip.swf">swf</a>
            
            <a rel="shadowbox;width=600;height=450" title="Alien" href="http://www.shadowbox-js.com/gallery/alien.flv"><img src="http://www.shadowbox-js.com/gallery/alien-thumb.gif" alt="" class="border"></a>
            

                        <td><a rel="shadowbox;width=405;height=340;player=swf" title="Ebon Coast" href="http://www.youtube.com/v/lSnWhsmlGec&amp;hl=en&amp;fs=1&amp;rel=0&amp;autoplay=1"><img src="http://www.shadowbox-js.com/gallery/mckee-thumb.jpg" alt="" class="border"></a></td>
                        <td><a rel="shadowbox;width=480;height=204"            title="The Dark Knight" href="http://movies.apple.com/movies/wb/the_dark_knight/the_dark_knight-tlr2-h.ref.mov"><img src="http://www.shadowbox-js.com/gallery/darkknight-thumb.jpg" alt="" class="border"></a></td>
                        <td><a rel="shadowbox;width=512;height=322;options={flashVars:{id:'v2155043',vid:2021599,autoPlay:1},flashParams:{AllowScriptAccess:'always',allowFullScreen:'true'}}" title="Sweetness" href="http://d.yimg.com/static.video.yahoo.com/yep/YV_YEP.swf?ver=2.2.40"><img src="http://www.shadowbox-js.com/gallery/sweetness-thumb.jpg" alt="" class="border"></a></td>

	<h3> Web Demo</h3>
                <a rel="shadowbox" class="option" title="This page" href="/">This Page</a>
                <a rel="shadowbox" class="option" title="Google.com" href="http://www.google.com/">External Site</a>
                <a class="option" onclick="demoGoogleMaps();">Google Maps</a>

                <a rel="shadowbox;width=200;height=400" class="option" title="Inline content" href="index.html#inline-sample">Inline Content</a>
                <a rel="shadowbox;width=400;height=100" class="option" title="Search Form" href="form.html">Simple Form</a>
                <a class="option" onclick="demoMessage();">Script-triggered Message</a>
                <a class="option" onclick="demoGallery();">Script-triggered Gallery</a>



<h3>Accordion Menu Test</h3>


<ul id="accordion">
	<li class="hasSub">Sports</li>
	<ul>
		<li><a href="#">Golf</a></li>
		<li><a href="#">Cricket</a></li>
		<li><a href="#">Football</a></li>
	</ul>
	<li class="hasSub">Technology</li>
	<ul>
		<li><a href="#">iPhone</a></li>
		<li><a href="#">Facebook</a></li>
		<li><a href="#">Twitter</a></li>
	</ul>
	<li class="hasSub">Latest</li>
	<ul>
		<li><a href="#">Obama</a></li>
		<li><a href="#">Iran Election</a></li>
		<li><a href="#">Health Care</a></li>
	</ul>
	<li class="noSub">Yes</li>
</ul>




<div class="glossymenu">
<a class="menuitem" href="http://www.dynamicdrive.com/">Dynamic Drive</a>
<a class="menuitem submenuheader" href="http://www.dynamicdrive.com/style/" >CSS Examples</a>
<div class="submenu">
	<ul>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/category/C1/">Horizontal CSS Menus</a></li>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/category/C2/">Vertical CSS Menus</a></li>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/category/C4/">Image CSS</a></li>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/category/C6/">Form CSS</a></li>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/category/C5/">DIVs and containers</a></li>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/category/C7/">Links & Buttons</a></li>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/category/C8/">Other</a></li>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/all/">Browse All</a></li>
	</ul>
</div>
<a class="menuitem" href="http://www.javascriptkit.com/jsref/">JavaScript Reference</a>
<a class="menuitem" href="http://www.javascriptkit.com/domref/">DOM Reference</a>
<a class="menuitem submenuheader" href="http://www.cssdrive.com">CSS Drive</a>
<div class="submenu">
	<ul>
	<li><a href="http://www.cssdrive.com">CSS Gallery</a></li>
	<li><a href="http://www.cssdrive.com/index.php/menudesigns/">Menu Gallery</a></li>
	<li><a href="http://www.cssdrive.com/index.php/news/">Web Design News</a></li>
	<li><a href="http://www.cssdrive.com/index.php/examples/">CSS Examples</a></li>
	<li><a href="http://www.cssdrive.com/index.php/main/csscompressor/">CSS Compressor</a></li>
	<li><a href="http://www.dynamicdrive.com/forums/forumdisplay.php?f=6">CSS Forums</a></li>
	</ul>
	<img src="http://i27.tinypic.com/sy7295.gif" style="margin: 10px 5px" />
</div>
<a class="menuitem" href="http://www.codingforums.com/" style="border-bottom-width: 0">Coding Forums</a>		
</div>

<div class="glossymenu">
<a class="menuitem" href="http://www.dynamicdrive.com/">Dynamic Drive</a>
<a class="menuitem submenuheader" href="http://www.dynamicdrive.com/style/" >CSS Examples</a>
<div class="submenu">
	<ul>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/category/C1/">Horizontal CSS Menus</a></li>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/category/C2/">Vertical CSS Menus</a></li>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/category/C4/">Image CSS</a></li>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/category/C6/">Form CSS</a></li>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/category/C5/">DIVs and containers</a></li>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/category/C7/">Links & Buttons</a></li>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/category/C8/">Other</a></li>
	<li><a href="http://www.dynamicdrive.com/style/csslibrary/all/">Browse All</a></li>
	</ul>
</div>
<a class="menuitem" href="http://www.javascriptkit.com/jsref/">JavaScript Reference</a>
<a class="menuitem" href="http://www.javascriptkit.com/domref/">DOM Reference</a>
<a class="menuitem submenuheader" href="http://www.cssdrive.com">CSS Drive</a>
<div class="submenu">
	<ul>
	<li><a href="http://www.cssdrive.com">CSS Gallery</a></li>
	<li><a href="http://www.cssdrive.com/index.php/menudesigns/">Menu Gallery</a></li>
	<li><a href="http://www.cssdrive.com/index.php/news/">Web Design News</a></li>
	<li><a href="http://www.cssdrive.com/index.php/examples/">CSS Examples</a></li>
	<li><a href="http://www.cssdrive.com/index.php/main/csscompressor/">CSS Compressor</a></li>
	<li><a href="http://www.dynamicdrive.com/forums/forumdisplay.php?f=6">CSS Forums</a></li>
	</ul>
	<img src="http://i27.tinypic.com/sy7295.gif" style="margin: 10px 5px" />
</div>
<a class="menuitem" href="http://www.codingforums.com/" style="border-bottom-width: 0">Coding Forums</a>		
</div>


<h3> Ajaxify</h3>

<a class="ajaxify" href="/jquery_text/donotuse.html?html=true" target="#container"> 	The easy way</a>
<a id="options" href="#" > 	The easy way2</a>

<div id="container" style="min-height: 300px" style="border: 1px solid red; width: 500px; height: 100px">
</div>
<br/>

<%
	long init = System.nanoTime();
%>

<a id="options2" href="#" >	Okay </a>

<div id="container2" style="min-height: 300px" style="border: 1px solid red; width: 500px; height: 100px">
<%= ""+init %>
</div>



<a href="http://colorpowered.com/colorbox/" target="_blank"> <h1>ColorBox Demonstration</h1> </a>


		<h2>elastic transition</h2>
		<p><a href="js/content/ohoopee1.jpg" rel="cbexample1" title="Me and my grandfather on the Ohoopee.">Grouped Photo 1</a></p>

		<p><a href="js/content/ohoopee2.jpg" rel="cbexample1" title="On the Ohoopee as a child">Grouped Photo 2</a></p>
		<p><a href="js/content/ohoopee3.jpg" rel="cbexample1" title="On the Ohoopee as an adult">Grouped Photo 3</a></p>
		
		<h2>fade transition</h2>
		<p><a href="js/content/ohoopee1.jpg" rel="cbexample2" title="Me and my grandfather on the Ohoopee">Grouped Photo 1</a></p>
		<p><a href="js/content/ohoopee2.jpg" rel="cbexample2" title="On the Ohoopee as a child">Grouped Photo 2</a></p>
		<p><a href="js/content/ohoopee3.jpg" rel="cbexample2" title="On the Ohoopee as an adult">Grouped Photo 3</a></p>

		
		<h2>no transition + fixed width and height (75% of screen size)</h2>
		<p><a href="js/content/ohoopee1.jpg" rel="cbexample3"  title="Me and my grandfather on the Ohoopee.">Grouped Photo 1</a></p>
		<p><a href="js/content/ohoopee2.jpg" rel="cbexample3"  title="On the Ohoopee as a child">Grouped Photo 2</a></p>
		<p><a href="js/content/ohoopee3.jpg" rel="cbexample3"  title="On the Ohoopee as an adult">Grouped Photo 3</a></p>
		
		<h2>slideshow</h2>
		<p><a href="js/content/ohoopee1.jpg" rel="cbexample4"  title="Me and my grandfather on the Ohoopee.">Grouped Photo 1</a></p>

		<p><a href="js/content/ohoopee2.jpg" rel="cbexample4"  title="On the Ohoopee as a child">Grouped Photo 2</a></p>
		<p><a href="js/content/ohoopee3.jpg" rel="cbexample4"  title="On the Ohoopee as an adult">Grouped Photo 3</a></p>
		
		<h2>Single photo with callback example</h2>
		<p><a class='single' href="js/content/marylou.jpg" title="Marylou on Cumberland Island">Single Photo</a></p>
		
		
		<h2>Other Content Types</h2>
		<p><a class='cbexample5' href="/devNoteAction.html?ajaxRequest=true&ajaxOut=getlisthtml" title="Homer Defined">Outside HTML (Ajax)</a></p>
		<p><a class='cbexample5' href="/home.html" title="Royksopp: Remind Me">Flash / Video (Ajax/Embedded)</a></p>
		<p><a class='cbexample6' href="http://www.youtube.com/v/617ANIA5Rqs" title="The Knife: We Share Our Mother's Health">Flash / Video (Iframe/Direct Link To YouTube)</a></p>

		<p><a class='cbexample7' href="http://www.yahoo.com">Outside Webpage (Iframe)</a></p>
		<p><a class='cbexample8' href="#">Inline HTML</a></p>
		
		<h2>Demonstration of using callbacks</h2>
		<p><a class='cbexample9' href="js/content/ohoopee3.jpg" title="Marylou on Cumberland Island">Example with alerts</a>. Callbacks and event-hooks allow users to extend functionality without having to rewrite parts of the plugin.</p>
		
		<!-- This contains the hidden content for inline calls -->
		<div style='display:none'>

			<div id='inline_example1' style='padding:10px; background:#fff;'>
			<p><strong>This content comes from a hidden element on this page.</strong></p>
			<p>The inline option preserves bound JavaScript events and changes, and it puts the content back where it came from when it is closed.<br />
			<a id="cbclick" href="#" style='padding:5px; background:#ccc;'>Click me, it will be preserved!</a></p>
			
			<p><strong>If you try to open a new ColorBox while it is already open, it will update itself with the new content.</strong></p>
			<p>Updating Content Example:<br />

			<a class="cbexample5" href="/devNoteAction.html?ajaxRequest=true&ajaxOut=getlisthtml">Click here to load new content</a></p>
			</div>
		</div>

		



<br/><br/><hr/>
<!-- ============================================================================================================ -->
<a href="http://fancybox.net/home"><h1> Fancy Box Example </h1></a>		

	<hr />

	<p>
		Different animations - 'fade', 'elastic' and 'none'<br />

		<a id="fbexample1" href="/js/fancybox/example/1_b.jpg"><img alt="example1" src="/js/fancybox/example/1_s.jpg" /></a>

		<a id="fbexample2" href="/js/fancybox/example/2_b.jpg"><img alt="example2" src="/js/fancybox/example/2_s.jpg" /></a>

		<a id="fbexample3" href="/js/fancybox/example/3_b.jpg"><img alt="example3" src="/js/fancybox/example/3_s.jpg" /></a>

	</p>

	<p>
		Different title positions - 'outside', 'inside' and 'over'<br />

		<a id="fbexample4" href="/js/fancybox/example/4_b.jpg" title="Lorem ipsum dolor sit amet"><img alt="example4" src="/js/fancybox/example/4_s.jpg" /></a>

		<a id="fbexample5" href="/js/fancybox/example/5_b.jpg" title="Cras neque mi, semper at interdum id, dapibus in leo. Suspendisse nunc leo, eleifend sit amet iaculis et, cursus sed turpis."><img alt="example5" src="/js/fancybox/example/5_s.jpg" /></a>

		<a id="fbexample6" href="/js/fancybox/example/6_b.jpg" title="Sed vel sapien vel sem tempus placerat eu ut tortor. Nulla facilisi. Sed adipiscing, turpis ut cursus molestie, sem eros viverra mauris, quis sollicitudin sapien enim nec est. ras pulvinar placerat diam eu consectetur."><img alt="example6" src="/js/fancybox/example/6_s.jpg" /></a>

	</p>

	<p>
		Image gallery (ps, try using mouse scroll wheel)<br />

		<a rel="example_group" href="/js/fancybox/example/7_b.jpg" title="Lorem ipsum dolor sit amet"><img alt="" src="/js/fancybox/example/7_s.jpg" /></a>

		<a rel="example_group" href="/js/fancybox/example/8_b.jpg" title=""><img alt="" src="/js/fancybox/example/8_s.jpg" /></a>

		<a rel="example_group" href="/js/fancybox/example/9_b.jpg" title=""><img alt="" src="/js/fancybox/example/9_s.jpg" /></a>

	</p>

	<p>
		Various examples
	</p>

	<ul>
		<li><a id="fbvarious1" href="#fbinline1" title="Lorem ipsum dolor sit amet">Inline</a></li>
		<li><a id="fbvarious2" href="/devNoteAction.html?ajaxRequest=true&ajaxOut=getlisthtml">Ajax</a></li>

		<li><a id="fbvarious3" href="http://google.ca">Iframe</a></li>
		<li><a id="fbvarious4" href="http://www.adobe.com/jp/events/cs3_web_edition_tour/swfs/perform.swf">Swf</a></li>
	</ul>

	<div style="display: none;">
		<div id="fbinline1" style="width:400px;height:100px;overflow:auto;">
			Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam quis mi eu elit tempor facilisis id et neque. Nulla sit amet sem sapien. Vestibulum imperdiet porta ante ac ornare. Nulla et lorem eu nibh adipiscing ultricies nec at lacus. Cras laoreet ultricies sem, at blandit mi eleifend aliquam. Nunc enim ipsum, vehicula non pretium varius, cursus ac tortor. Vivamus fringilla congue laoreet. Quisque ultrices sodales orci, quis rhoncus justo auctor in. Phasellus dui eros, bibendum eu feugiat ornare, faucibus eu mi. Nunc aliquet tempus sem, id aliquam diam varius ac. Maecenas nisl nunc, molestie vitae eleifend vel, iaculis sed magna. Aenean tempus lacus vitae orci posuere porttitor eget non felis. Donec lectus elit, aliquam nec eleifend sit amet, vestibulum sed nunc.
		</div>
	</div>

	<p>
		Ajax example will not run from your local computer and requires a server to run.
	</p>
	<p>
		Photo Credit: <a href="http://www.flickr.com/people/kharied/">Katie Harris</a>
	</p>



<br/><br/><hr/>
<!-- ============================================================================================================ -->
<a href="http://fancybox.net/home"><h1> Json Example </h1></a>		

<div id="jsonimages">
</div>

<br/><br/><hr/>
<!-- ============================================================================================================ -->
<a href="http://fancybox.net/home"><h1> Popmenu </h1></a>		

<p style="text-align:left"><a href="http://www.dynamicdrive.com" data-popupmenu="popmenu1">Dynamic Drive</a></p>
<p style="text-align:left"><a href="http://www.dynamicdrive.com" data-popupmenu="popmenu2">Webmaster Resources</a></p>


<!--HTML for popup Menu 1-->
<ul id="popmenu1" class="jqpopupmenu">
<li><a href="#">Item 1a</a></li>
<li><a href="#">Item 2a</a></li>
<li><a href="#">Item Folder 3a</a>
	<ul>
	<li><a href="#">Sub Item 3.1a</a></li>
	<li><a href="#">Sub Item 3.2a</a></li>
	<li><a href="#">Sub Item 3.3a</a></li>
	<li><a href="#">Sub Item 3.4a</a></li>
	</ul>
</li>
<li><a href="#">Item 4a</a></li>
<li><a href="#">Item Folder 5a</a>
	<ul>
	<li><a href="#">Sub Item 5.1a</a></li>
	<li><a href="#">Item Folder 5.2a</a>
		<ul>
		<li><a href="#">Sub Item 5.2.1a</a></li>
		<li><a href="#">Sub Item 5.2.2a</a></li>
		<li><a href="#">Sub Item 5.2.3a</a></li>
		<li><a href="#">Sub Item 5.2.4a</a></li>
		</ul>
	</li>
	</ul>
</li>
<li><a href="#">Item 6a</a></li>
</ul>


<!--HTML for popup Menu 2-->
<ul id="popmenu2" class="jqpopupmenu">
<li><a href="http://www.dynamicdrive.com/">Dynamic Drive</a></li>
<li><a href="http://www.cssdrive.com">CSS Drive</a></li>
<li><a href="http://www.javascriptkit.com">JavaScript Kit</a></li>
<li><a href="http://www.codingforums.com">Coding Forums</a></li>
<li><a href="http://www.javascriptkit.com/domref/">DOM Reference</a></li>
</ul>

<br/><br/><hr/>
<!-- ============================================================================================================ -->
<a href="http://catcubed.com/2008/12/23/ceebox-a-thickboxvideobox-mashup/"><h1> CeeBox </h1></a>

    <ul class="ceebox mixed {videoWidth:200}">
    	<li><a href="http://www.youtube.com/watch?v=G_5htGZkp9g" title="Balsa Man 2009 Video" >YouTube: balsa man vid</a></li>

        <li><a href="http://www.youtube.com/watch?v=__GhJl_UQg0" title="Machine with oil" class="{videoGallery:false}">Machine with Oil</a> set to not be in gallery</li>
        <li><a href="http://www.vimeo.com/5606758" title="Awesome Aquarium">link to vimeo video</a></li>
    	<li><a href="http://www.facebook.com/video/video.php?v=238358730483" class="{videoWidth:400,boxColor:'#bbf',borderColor:'#db0 #aa0',borderWidth:'12px'}" title="Lame Facebook Video">link to facebook video</a>  (videoWidth set to 400, boxColor set to a light blue, and borderColor set to orange via metadata)</li>
        <li><a href="http://farm3.static.flickr.com/2642/3894718411_88fe1d2968.jpg" title="16th Indian Tacos (photo by sninky-chan)">16th Indian Tacos</a></li>
        <li><a href="http://www.catcubed.com" title="Catcubed: My blog">Catcubed</a></li>

        <li><a href="http://www.spike.com/video/crazy-heart/3292468" title="Crazy heart" rel="width:600" class="{video:false,html:false}">Spike.com: Crazy Heart Trailer</a> Set to not display via metadata</li>
        <li><a href="http://www.ifilm.com/video/2861849">iFilm: Swear Jar</a> Note: this is an old link. iFilm forwards to spike.com but CeeBox still works </li>
        <li><a href="http://us.cnn.com/video/?/video/world/2009/11/18/amanpour.rwanda.cnn" >CNN: Rwanda Then and Now</a></li>
        <li><a href="http://www.metacafe.com/watch/3740158/the_wolfman_movie_trailer/" >Metacafe: The Wolfman Movie Trailer</a></li>
        <li><a href="http://www.colbertnation.com/the-colbert-report-videos/256451/november-19-2009/ak-47-designer-celebrates-his-birthday---john-pike" rel="ratio:1.3333">Colbert: AK-47 designers birthday</a> Colbert supported via the humor.json</li>

        <li><a href="http://www.thedailyshow.com/watch/tue-november-17-2009/submission-accomplished" rel="videoId:256344 ratio:4:3">Daily Show: Submission Accomplished</a> NOTE: ceebox does not support Daily Show videos natively. It requires the humor.json file to be loaded and a videoId to be on the rel</li>
        <li><a href="http://www.uctv.tv/search-details.aspx?showID=16803">uctv</a></li>
    </ul>

    <h2>Image Gallery with next/prev</h2>
    <ul class="ceebox images">
        <li><a href="http://catcubed.com/images/kurtvon.jpg" title="drawing of Kurt Vonnegut">Kurt</a></li>
        <li><a href="http://catcubed.com/images/zombie_puppetmaster.jpg" title="Zombie Sock Puppet Cee" class="{boxColor:'#130',textColor:'#dfd'}">Zombie Puppet</a> color of background at text set via metadata</li>
        <li><a href="http://catcubed.com/images/balsa-man.jpg" title="Balsa Man 2008 as the sunsets. Oh boy isn't it pretty! Didn't get as nice of a picture this time.">Balsa Man</a></li>

        <li><a href="http://farm3.static.flickr.com/2642/3894718411_88fe1d2968.jpg" title="16th Indian Tacos (photo by sninky-chan)">16th Indian Tacos</a></li>
        <li><a href="http://tr-tr.facebook.com/profile/pic.php?oid=AAAAAQAQ_W8eL0hDl1i9pyC5HlMBWwAAAA_UlK1k0GmRW2Jc1JOQvcPL&size=normal" rel="image">photo</a> photo link does not contain jpg but we are forcing CeeBox to recognize it as an image with rel="image"</li>
    </ul>


    <h2>Image single link called by class</h2>
    <p class="ceebox">
<a href="http://farm3.static.flickr.com/2616/3897136195_42c708467a_b.jpg" class="{boxColor:'#000',textColor:'#ddd'}" title="UN at Balsa Man 2009">UN at Balsa Man</a> ceebox bg color set to black via metadata plugin</p>

    <h3>Image Map with image links</h3>
    <p>Note: these links are set to fade "slow" and also have an onload function called that makes them flash red</p>
            <p><img src="http://www.w3schools.com/TAGS/planets.gif" width="145" height="126" alt="Planets" usemap="#planetmapImages" />
       </p>
    <h2>Daily Motion video</h2>
    <ul class="ceebox"><li><a title="BMW M3 auf dem Nürburgring" rel="600 400" href="http://www.dailymotion.com/video/x3j545_bmw-m3-auf-dem-nurburgring_auto">
      <img class="video-thumb" alt="BMW M3 auf dem Nürburgring" src="http://ak2.static.dailymotion.com/static/video/149/139/5931941:jpeg_preview_medium.jpg?20090120063245"/>

      </a></li>
    </ul>
    <h2>iFrame</h2>
    <ul class="ceebox html">
    	<li><a href="http://balsaman.org" title="Balsa Man">Balsa Man</a></li>
        <li><a rel="modal:false" href="http://www.laughingsquid.com" title="Laughing Squid">Laughing Squid</a></li>
        <li><a rel="iframe modal:true" href="http://www.laughingsquid.com" title="Laughing Squid modal box">Laughing Squid</a> (modal version; Note as there is no cee_close button on this page you'll need to reload after clicking on this link)</li>

   </ul>
            <h3>Image Map with iFrame links</h3>
            <p>Note: these links are set to fade "slow" and also have an onload function called that makes them flash red once all the iframe content is done loading</p>
            <p><img src="http://www.w3schools.com/TAGS/planets.gif" width="145" height="126" alt="Planets" usemap="#planetmap" /></p>


    <h2>AJAX</h2>
    
    <div class="ceebox">
    <a rel="width:500 height:300" href="/siteSuggestAction.html?ajaxRequest=true&ajaxOut=getmodalform" >siteSuggest</a>
    </div>
    <ul class="ceebox">    
    	<li><a rel="width:500 height:300" href="/siteSuggestAction.html?ajaxRequest=true&ajaxOut=getform" title="AJAX box">link to ajax content</a></li>
        <li><a rel="width:500 height:200" href="test.html#ajaxcontent" title="AJAX box">link to ajax content</a></li>

        <li><a rel="modal:true width:300 height:150" href="test.html#ajaxcontent" title="AJAX modal box">link to ajax content</a> (modal version; made to be a small box)</li>
        <li><a rel="modal:true width:400" href="test.html#ajaxcontent2" class="{htmlWidth:600,htmlHeight:300,boxColor:'#aad',titles:false}" title="AJAX modal box 2">link to ajax content 2</a> (set to load as iframe. some settings set via metadata including color, size and titles are turned off)</li>
    </ul>
    <h2>SWF Test</h2>
    <p><a href="http://www.thepcmanwebsite.com/media/pacman_flash/pacman-flash.swf" class="ceebox {boxColor:'#000',videoRatio:'0.8'}">Pacman Flash Game</a> (boxColor and videoRatio changed via metadata. <em>swf link thanks to the <a href="http://www.thepcmanwebsite.com">Pacman Website</a>)</em></p>

    <h2>CeeBox Public Function</h2>
    <p id="tester">The CeeBox popup function is public so it can be called from your script. You can sent it any piece of html content and it will include it in a ceebox style popup (see head of document for onclick code). <br/>
    <a id="testlink" href="#">link with public function attached onclick</a> link to balsaman <br/>
    <a id="testlink2" href="#">another link with public function attached onclick</a> generic html content</p>
    <hr/>
    <div id="ajaxcontent"><h2>Div content for the Ajax link test</h2><p>Hi this is content in a div on this very page!</p> <a href="#" class="cee_close">Close Me</a></div>

    <div id="ajaxcontent2"><h2>Another Div content for the Ajax</h2><p>Hi this is the second content in a div on this very page!</p> <a href="#" class="cee_close">Close Me Too</a></div>
    <div id="ajaxtest"></div>

    <map name="planetmap" class="iframe">
      <area shape="rect" coords="0,0,82,126" alt="Sun" href="http://en.wikipedia.org/wiki/Sun" title="It's the Sun!"/>
      <area shape="circle" coords="90,58,3" alt="Mercury" href="http://en.wikipedia.org/wiki/Mercury_(planet)" title="Welcome to Mercury. It is a tad hot"/>
      <area shape="circle" coords="124,58,8" alt="Venus" href="http://en.wikipedia.org/wiki/Venus"/>

    </map>
    <map name="planetmapImages" class="images">
      <area shape="rect" coords="0,0,82,126" alt="Sun" href="http://z.about.com/d/space/1/5/Y/Q/sun_tour.jpg" title="It's the Sun!"/>
      <area shape="circle" coords="90,58,3" alt="Mercury" href="http://www.trinity.edu/jdunn/images/Galaxy/mercury/mercury.gif" title="Welcome to Mercury. It is a tad hot"/>
      <area shape="circle" coords="124,58,8" alt="Venus" href="http://martianchronicles.files.wordpress.com/2008/12/venus_magellan.jpg"/>
    </map>
<br/><br/>


<!-- ============================================================================================================ -->
<a href="http://www.dynamicdrive.com/dynamicindex1/popit.htm/"><h1> Pop-it menu (Dynamic Drive) </h1></a>


<a href="#" onclick="showmenu(event,linkset[0])" onMouseout="delayhidemenu()">Webmaster Links</a><br>
<a href="#" onclick="showmenu(event,linkset[1], '180px')" onMouseout="delayhidemenu()">News sites</a>


<hr/>
<!-- ============================================================================================================ -->
<a href="http://www.dynamicdrive.com/dynamicindex1/dropmenuindex.htm"><h1> Anylink Menu </h1></a>

<!--1st anchor link-->

<p><a href="http://www.dynamicdrive.com" class="menuanchorclass" rel="anylinkmenu1[click]">Default Example</a></p>


<!--2nd anchor link-->

<p><a href="http://www.dynamicdrive.com" class="menuanchorclass someotherclass" rel="anylinkmenu2[click]" rev="lr">Right dropping menu</a> (click to reveal)</p>
                                                    

<!--3rd anchor link-->

<p style="text-align:center"><a href="http://www.dynamicdrive.com" class="menuanchorclass myownclass" rel="anylinkmenu3">Menu with multiple columns</a></p>
                                                    
<!--4th anchor link-->

<p><a href="http://www.dynamicdrive.com" class="menuanchorclass" rel="anylinkmenu4" data-image="http://i44.tinypic.com/2mni3yx.gif" data-overimage="http://i43.tinypic.com/24fycd5.gif"><img src="http://i44.tinypic.com/2mni3yx.gif" style="border-width:0" /></a></p>

<p><a href="http://www.dynamicdrive.com" class="menuanchorclass" rel="anylinkmenu1[click]">Default Example</a></p>


<p><a href="http://www.dynamicdrive.com" class="menuanchorclass" rel="configMenu[click]"><img src="/images/icons/arrow_57.gif"/></a></p>

<p><a href="http://www.dynamicdrive.com" class="menuanchorclass" rel="configMenu"><img src="/images/icons/arrow_57.gif"/></a></p>

<p><a href="http://www.dynamicdrive.com" class="menuanchorclass" rel="configMenu" data-image="/images/icons/arrow_57.gif" data-overimage="/images/icons/arrow_58.gif"><img src="/images/icons/arrow_57.gif" style="border-width:0" /></a></p>



<!-- ============================================================================================================ -->
<HR/>
<a href="http://jqueryui.com/demos/dialog//"><h1> Dialog by UI </h1></a>

<div id="dialog-form" title="Create new user">
	<p class="validateTips">All form fields are required.</p>
	<div id="loading-dialog" style="display:none;"><img src="/images/loading/loading1.gif"></div>
	<div ><span id="hereItGoes"></span>
	</div>
</div>

<button id="create-user">Create new user</button>
<input type="text" name="dialog-input" id="dialog-input"/>

<br/>
<!-- ============================================================================================================ -->
<a href="http://onehackoranother.com/projects/jquery/boxy"><h1> Boxy </h1></a>
<h5>Demo has style conflict with dataTables below. .bottom class are from this and demo_tables.css for that.</h5>
<script type='text/javascript'>
	$(function() {
	  $('.boxy').boxy();
	});
</script>

  	<a href='#foobar' class='boxy' title='Inline Content Demo'>Inline content (div#foobar)</a> |
  	<a href='/siteSuggestAction.html?ajaxRequest=true&ajaxOut=getmodalform' class='boxy' title='AJAX Content Demo'>Remote content (partial.html)</a> |

  	<div id='foobar' style='display: none; background-color: green; color: white; font-size: 30px; color: white; padding: 15px'>This is inline contentww</div>

	<form method='post' action='http://onehackoranother.com/projects/jquery/boxy/index.php#form' id='form' class='boxy' style='background-color: #efefef; border: 1px solid #505050; padding: 0.5em'>
    	<input type='submit' value='Submit with confirm' />

	</form>

<ul>
  <li><a href='#' onclick='Boxy.load("/jquery_text/donotuse.html?data=true");'>Test 1</a></li>
  <li><a href='#' onclick='Boxy.load("http://www.slashdot.com", {cache:true});'>Test 2 - cache enabled</a></li>
  <li><a href='#' onclick='Boxy.load("test-3.html", {filter: "#inner"});'>Test 3 - filtering</a></li>
</ul>

<a href='' onclick='Boxy.ask("Question", ["A", "B", "C"], function(r) { alert(r); });'>Question, response array</a>


<!-- ============================================================================================================ -->
<hr/>
<a href="http://dev.iceburg.net/jquery/jqModal"><h1> jqModal </h1></a>

<h3>Confirm example </h3>
<a href="http://www.jquery.com/" class="confirm">view</a>

<!-- Confirm Dialog -->
<div class="jqmConfirm" id="confirm">

<div id="ex3b" class="jqmConfirmWindow">
    <div class="jqmConfirmTitle clearfix">
    <h1>Confirmation por favor...</h1><a href="#" class="jqmClose"><em>Close</em></a>
  </div>
  
  <div class="jqmConfirmContent">
  <p class="jqmConfirmMsg"></p>
  <p>Continue?</p>
  </div>
  
  <input type="submit" value="no" />
  <input type="submit" value="yes" />
  
</div>

</div>


<div class="myjqmWindow" id="dialog2" >

<div style="background-color: white;">
<img src="/images/loading/loading41.gif"/>
</div>
</div>


<div class="jqmWindow" id="dialog3" >

<a href="#" class="jqmClose">Close</a>
<hr>
<em>READ ME</em> -->
This is a "vanilla plain" jqModal window. Behavior and appeareance extend far beyond this.
The demonstrations on this page will show off a few possibilites. I recommend walking
through each one to get an understanding of jqModal <em>before</em> using it.

<br /><br />
You can view the sourcecode of examples by clicking the Javascript, CSS, and HTML tabs.
Be sure to checkout the <a href="README">documentation</a> too!

<br /><br />
<em>NOTE</em>; You can close windows by clicking the tinted background known as the "overlay".
Clicking the overlay will have no effect if the "modal" parameter is passed, or if the
overlay is disabled.
</div>

<hr/>
<!-- ============================================================================================================ -->
<a href="http://trentrichardson.com/Impromptu/index.php"><h1> impromptu </h1></a>

<script type="text/javascript">

	function removeUser(id){
	    
		var txt = 'Are you sure you want to remove this?<input type="hidden" id="userid" name="userid" value="'+ id +'" />';
		
		$.prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					var uid = f.userid;
					location.href="/linktoAction.html?del=true&id=" + uid;
	
	//				$.post("/linktoAction.html?", { id: uid, del: "true" },
	//						   function(data){
	//						     alert("Data Loaded: " + data);
	//						   });
				}
			}
		});
	}
	
	function removeUser3(goTarget, txt){
		
		$.prompt(txt,{ 
			buttons:{Delete:true, Cancel:false},
			callback: function(v,m,f){
				if(v){
					location.href=goTarget;
				}
			}
		});
	}

</script>

<a href="javascript:;" title="Delete User" class="deleteuser" onclick="removeUser(7);">Delete</a>
<a href="javascript:;" title="Delete User" class="deleteuser" onclick="removeUser3('/linktoAction.html?del=true&id=8', 'Are you sure you want to remove this?');">removeExample3</a>

<div class="exlink">
	<button onclick="removeUser(7);" title="Example 8VV">Example Remove User</button>
</div>

<div class="exlink">
	<button onclick="$.prompt('Example 8',{buttons:{Delete:true, Cancel:false},callback: mycallbackfunc });" title="Example 8VV">Example 8</button>
</div>

<!-- ============================================================================================================ -->
<hr/>


<h1>Easy Image Preview with jQuery</h1>
	
<h4>Image gallery  (without caption)</h4>

	<a href="/images/ocean.jpg" class="jttPreview" ><img width=100 height=100 src="/images/ocean.jpg" alt="gallery thumbnail" /></a>

<h4>Image gallery (with caption)</h4>

	<a href="/images/ocean.jpg" class="jttPreview" title="Lake and a mountain"><img width=100 height=100 src="/images/ocean.jpg" alt="gallery thumbnail" /></a>



<!-- ============================================================================================================ -->
<hr/>
<a href="http://plugins.learningjquery.com/cluetip/demo/"><h1> cluetip </h1></a>


 <!-- use ajax/ahah to pull content from fragment.html: -->
  <p><a class="tips" href="http://www.yahoo.com" rel="/siteSuggestAction.html?ajaxRequest=true&ajaxOut=getmodalform">show me the cluetip!</a></p> 
 
  <!-- use title attribute for clueTip contents, but don't include anything in the clueTip's heading -->
  <p><a id="houdini" href="houdini.html" title="|Houdini was an escape artist.|He was also adept at prestidigitation.">Houdini</a></p>
  
  
  <P><a class="cttitle" href="#" title="This is |the title|The first set of |contents comes after the first delimiter in the title.|In this case, the delimiter is a pipe">test</a></P>
  
  <a class="tips" href="/jquery_text/donotuse.html?html=true" rel="/jquery_text/donotuse.html?html=true"> yahoo.com</a>
	
<!-- ============================================================================================================ -->
<a href="http://defunkt.github.com/facebox/"><h1> Facebox </h1></a>


<a href="/images/ocean.jpg" rel="facebox">load image</a> <br/>

<a href="#info" rel="facebox">load div inline</a> <br/>

<div id="info" style="display:none;">
<form name="siteSuggestFormEdit" method="POST" action="/siteSuggestAction.html">
	<select id="requiredField" name="category">
		<option value="">
			- Please Select -
		</option>
		<option value="problem_report">
			Problem Report
		</option>
		<option value="suggestion">
			Suggestion
		</option>
		<option value="misc">
			Misc
		</option>
	</select>
	<TEXTAREA NAME="suggest" COLS="100" ROWS="8"></TEXTAREA>
		<INPUT TYPE="HIDDEN" NAME="add" value="true">
		<INPUT TYPE="HIDDEN" NAME="wpid" value="4:1970288802226266">
</form>
<a id="modalAjaxSubmit" href="javascript:document.siteSuggestFormEdit.submit();">Submit</a>
</div>

<a href="/siteSuggestAction.html?ajaxRequest=true&ajaxOut=getmodalform" rel="facebox">Ajax Test</a>

<hr/>
<!-- ============================================================================================================ -->
<a href="http://flowplayer.org/tools/demos/index.html"><h1> JWuery Tools </h1></a>

<div id="jtdemo">
	<img height="100" width="100" src="images/samples/ohoopee1.jpg" title="The tooltip text #1"/>
	<img height="100" width="100" src="images/samples/ohoopee2.jpg" title="The tooltip text #2"/>
</div>

<input type="date" />

<!-- "previous page" action -->
<a class="prev browse left"></a>

<!-- root element for scrollable -->
<div class="scrollable">   
   
   <!-- root element for the items -->
   <div class="items">
   
      <!-- 1-5 -->
      <div>
         <img src="http://farm1.static.flickr.com/143/321464099_a7cfcb95cf_t.jpg" />
         <img src="http://farm4.static.flickr.com/3089/2796719087_c3ee89a730_t.jpg" />
         <img src="http://farm1.static.flickr.com/79/244441862_08ec9b6b49_t.jpg" />
         <img src="http://farm1.static.flickr.com/28/66523124_b468cf4978_t.jpg" />
         <img src="http://farm1.static.flickr.com/164/399223606_b875ddf797_t.jpg" />
      </div>
      
      <!-- 5-10 -->
      <div>
         <img src="http://farm1.static.flickr.com/163/399223609_db47d35b7c_t.jpg" />
         <img src="http://farm1.static.flickr.com/135/321464104_c010dbf34c_t.jpg" />
         <img src="http://farm1.static.flickr.com/40/117346184_9760f3aabc_t.jpg" />
         <img src="http://farm1.static.flickr.com/153/399232237_6928a527c1_t.jpg" />
         <img src="http://farm1.static.flickr.com/50/117346182_1fded507fa_t.jpg" />
      </div>
      
      <!-- 10-15 -->
      <div>
         <img src="http://farm4.static.flickr.com/3629/3323896446_3b87a8bf75_t.jpg" />
         <img src="http://farm4.static.flickr.com/3023/3323897466_e61624f6de_t.jpg" />
         <img src="http://farm4.static.flickr.com/3650/3323058611_d35c894fab_t.jpg" />
         <img src="http://farm4.static.flickr.com/3635/3323893254_3183671257_t.jpg" />
         <img src="http://farm4.static.flickr.com/3624/3323893148_8318838fbd_t.jpg" />
      </div>
      
   </div>
   
</div>

<!-- "next page" action -->
<a class="next browse right"></a>

<div class="clear"></div>
<br/>
<br/>

<hr/>
<!-- ============================================================================================================ -->
<a href="http://www.datatables.net"><h1> Data Tables </h1></a>

<table cellpadding="0" cellspacing="0" border="0" class="display" id="#dt-table-example1">
	<thead>
		<tr>

			<th>Rendering engine</th>
			<th>Browser</th>
			<th>Platform(s)</th>
			<th>Engine version</th>
			<th>CSS grade</th>
		</tr>

	</thead>
	<tbody>
		<tr class="gradeX">
			<td>Trident</td>
			<td>Internet
				 Explorer 4.0</td>
			<td>Win 95+</td>
			<td class="center">4</td>

			<td class="center">X</td>
		</tr>
		<tr class="gradeC">
			<td>Trident</td>
			<td>Internet
				 Explorer 5.0</td>
			<td>Win 95+</td>
			<td class="center">5</td>

			<td class="center">C</td>
		</tr>
		<tr class="gradeA">
			<td>Trident</td>
			<td>Internet
				 Explorer 5.5</td>
			<td>Win 95+</td>
			<td class="center">5.5</td>

			<td class="center">A</td>
		</tr>
		<tr class="gradeA">
			<td>Trident</td>
			<td>Internet
				 Explorer 6</td>
			<td>Win 98+</td>
			<td class="center">6</td>

			<td class="center">A</td>
		</tr>
		<tr class="gradeA">
			<td>Trident</td>
			<td>Internet Explorer 7</td>
			<td>Win XP SP2+</td>
			<td class="center">7</td>

			<td class="center">A</td>
		</tr>
		<tr class="gradeA">
			<td>Trident</td>
			<td>AOL browser (AOL desktop)</td>
			<td>Win XP</td>
			<td class="center">6</td>

			<td class="center">A</td>
		</tr>
		<tr class="gradeA">
			<td>Gecko</td>
			<td>Firefox 1.0</td>
			<td>Win 98+ / OSX.2+</td>
			<td class="center">1.7</td>

			<td class="center">A</td>
		</tr>
		<tr class="gradeA">
			<td>Gecko</td>
			<td>Firefox 1.5</td>
			<td>Win 98+ / OSX.2+</td>
			<td class="center">1.8</td>

			<td class="center">A</td>
		</tr>
		<tr class="gradeA">
			<td>Gecko</td>
			<td>Firefox 2.0</td>
			<td>Win 98+ / OSX.2+</td>
			<td class="center">1.8</td>

			<td class="center">A</td>
		</tr>
		<tr class="gradeA">
			<td>Gecko</td>
			<td>Firefox 3.0</td>
			<td>Win 2k+ / OSX.3+</td>
			<td class="center">1.9</td>

			<td class="center">A</td>
		</tr>
		<tr class="gradeA">
			<td>Gecko</td>
			<td>Camino 1.0</td>
			<td>OSX.2+</td>
			<td class="center">1.8</td>

			<td class="center">A</td>
		</tr>
</tbody>
	<tfoot>
		<tr>
			<th>Rendering engine</th>

			<th>Browser</th>
			<th>Platform(s)</th>
			<th>Engine version</th>
			<th>CSS grade</th>
		</tr>
	</tfoot>
</table>

<br/><br/>

<table cellpadding="0" cellspacing="0" border="0" class="display" id="dt-table-example2">
	<thead>

		<tr>
			<th width="20%">Rendering engine</th>
			<th width="25%">Browser</th>
			<th width="25%">Platform(s)</th>
			<th width="15%">Engine version</th>
			<th width="15%">CSS grade</th>

		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="5" class="dataTables_empty">Loading data from server</td>
		</tr>
	</tbody>
	<tfoot>
		<tr>
			<th>Rendering engine</th>
			<th>Browser</th>
			<th>Platform(s)</th>
			<th>Engine version</th>
			<th>CSS grade</th>

		</tr>
	</tfoot>
</table>



<br/><br/><hr/>
<!-- ============================================================================================================ -->
<a href="http://flexigrid.info/"><h1> Flex Grid Tables </h1></a>


<b>Example 1</b>
<p>
The most basic example with the zero configuration, with a table converted into flexigrid 
(<a href="#" onclick="$(this).parent().next().toggle(); return false;">Show sample code</a>)
</p>
<div class="code">
	<pre>$('.flexme').flexigrid();</pre>

</div>
<table class="flexme1">
	<thead>
    		<tr>
            	<th width="100">Col 1</th>
            	<th width="100">Col 2</th>
            	<th width="100">Col 3 is a long header name</th>
            	<th width="300">Col 4</th>

            </tr>
    </thead>
    <tbody>
    		<tr>
            	<td>This is data 1 with overflowing content</td>
            	<td>This is data 2</td>
            	<td>This is data 3</td>

            	<td>This is data 4</td>
            </tr>
    		<tr>
            	<td>This is data 1</td>
            	<td>This is data 2</td>
            	<td>This is data 3</td>
            	<td>This is data 4</td>

            </tr>
    		<tr>
            	<td>This is data 1</td>
            	<td>This is data 2</td>
            	<td>This is data 3</td>
            	<td>This is data 4</td>
            </tr>

    		<tr>
            	<td>This is data 1</td>
            	<td>This is data 2</td>
            	<td>This is data 3</td>
            	<td>This is data 4</td>
            </tr>
    		<tr>

            	<td>This is data 1</td>
            	<td>This is data 2</td>
            	<td>This is data 3</td>
            	<td>This is data 4</td>
            </tr>
    		<tr>
            	<td>This is data 1</td>

            	<td>This is data 2</td>
            	<td>This is data 3</td>
            	<td>This is data 4</td>
            </tr>
    		<tr>
            	<td>This is data 1</td>
            	<td>This is data 2</td>

            	<td>This is data 3</td>
            	<td>This is data 4</td>
            </tr>
    		<tr>
            	<td>This is data 1</td>
            	<td>This is data 2</td>
            	<td>This is data 3</td>

            	<td>This is data 4</td>
            </tr>
    		<tr>
            	<td>This is data 1</td>
            	<td>This is data 2</td>
            	<td>This is data 3</td>
            	<td>This is data 4</td>

            </tr>
    		<tr>
            	<td>This is data 1</td>
            	<td>This is data 2</td>
            	<td>This is data 3</td>
            	<td>This is data 4</td>
            </tr>

    		<tr>
            	<td>This is data 1</td>
            	<td>This is data 2</td>
            	<td>This is data 3</td>
            	<td>This is data 4</td>
            </tr>
    		<tr>

            	<td>This is data 1</td>
            	<td>This is data 2</td>
            	<td>This is data 3</td>
            	<td>This is data 4</td>
            </tr>
            
    </tbody>
</table>

<br />

<b>Example 3</b>
<p>
Flexigrid with a dynamic data, paging, search, toolbar, and connected to an JSON file.
(<a href="#" onclick="$(this).parent().next().toggle(); return false;">Show sample code</a>)
</p>
<div class="code">

	<style>
        
        .flexigrid div.fbutton .add
            {
                background: url(css/images/add.png) no-repeat center left;
            }	
    
        .flexigrid div.fbutton .delete
            {
                background: url(css/images/close.png) no-repeat center left;
            }	
    
    </style>


	<pre>
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
	</pre>
</div>



<table id="flexiex3" style="display:none"></table>



<hr/>

<h1> UL and border testing. </h1>


<div class="ttt-frame">

<ul class="ttt-ul">
	<li class="ttt-li"> Item 1 </li>
	<li class="ttt-li"> Item 2 </li>
	<li class="ttt-li"> Item 3 </li>
	<li class="ttt-li"> Item 4 </li>
</ul>

</div>
<br/><br/><br/>
<div class="ttt-frame">

<ul class="ttt-ul">
	<li  class="ttt-li" style="float:left" > Item H 1 </li>
	<li  class="ttt-li" style="float:left" > Item H 2 </li>
	<li  class="ttt-li" style="float:left"> Item H 3 </li>
	<li  class="ttt-li" style="float:left"> Item H 4 </li>
		
</ul>
<div class="clear"></div>
</div>

<hr/>
<h1> <a href="http://www.digitalia.be/software/slimbox2">Lightbox/Slimbox </a> </h1>

<a href="/images/ocean.jpg" rel="lightbox" title="Beautiful, isn't it?">Click here</a>


<a href="/images/ocean.jpg" rel="lightbox a" title="Beautiful, isn't it?">Click here</a> to view a picture inside Slimbox.
<a href="/images/ocean.jpg" rel="lightbox a" title="Beautiful, isn't it?">Click here</a> to view a picture inside Slimbox.
<a href="/images/ocean.jpg" rel="lightbox a" title="Beautiful, isn't it?">Click here</a> to view a picture inside Slimbox.

<hr/>


<div style="border: 0px solid red; width:100%; display:block;; height:50px">
<div style="float:left;border-left: 5px solid blue; border-top: 5px solid blue; display: block; height:50px"><div style=" background-color: yellow"> xx </div></div>
<div style="float:left;border-left: 5px solid blue; border-top: 5px solid blue; display: block; height:50px"><div style=" background-color: yellow"> mmmmm </div></div>
<div style="float:left;border-left: 5px solid blue; border-top: 5px solid blue; display: block; height:50px"><div style=" background-color: yellow"> xxxxddx </div></div>
<div style="float:left;display: block; "><div style="background-color: blue; height:55px; width: 5px;"> </div></div>
<div class="clear"></div>
</div>

<h1> Autocomplete </h1>
<a href="http://it.moyiza.com/?mid=programming&category=68&document_srl=323" rel="lightbox" title="Beautiful, isn't it?">korean site</a><br>

<a href="http://www.xpdl.org/nugen/p/tomcatconfiguration/public.htm" rel="lightbox" title="Beautiful, isn't it?">Tomcat utf informat</a><br>



<script type="text/javascript">
$(document).ready(function() {
 var goods = ["유현궁", "감자골", "주병진", "강호동"];
 
 $( "#searchAuto" ).autocomplete({
		source: goods
	});
});
</script>

<script>
	$(function() {
		var availableTags = [
			"ActionScript",
			"AppleScript",
			"Asp",
			"BASIC",
			"C",
			"C++",
			"Clojure",
			"COBOL",
			"ColdFusion",
			"Erlang",
			"Fortran",
			"Groovy",
			"Haskell",
			"Java",
			"JavaScript",
			"Lisp",
			"Perl",
			"PHP",
			"Python",
			"Ruby",
			"Scala",
			"Scheme"
		];
		$( "#tags" ).autocomplete({
			source: availableTags
		});
	});
	</script>


<input type="text" id="searchAuto" autocomplete="on"> <input id="tags">
