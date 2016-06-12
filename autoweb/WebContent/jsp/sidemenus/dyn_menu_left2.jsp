<%@page language="java" import="com.jtrend.util.*,java.util.*,com.autosite.db.*,com.autosite.ds.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<%

	String pageName = (String) session.getAttribute("k_page_name");
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
	List pages = PageDS.getInstance().getBySiteId(site.getId());
	
	if (pages == null) {
		return;
	}


    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	if (siteConfig ==null) {
		siteConfig = SiteConfigDS.getDefaultSiteConfig();  
	}
	
	if ( siteConfig.getMenuReverse() == 1) {
		Collections.reverse(pages);
	}
	
	
	String lineColor = WebUtil.display(siteConfig.getMenuLineColor(), "#e0e0e0");
	String frontColor = WebUtil.display(siteConfig.getMenuFrontColor(), "orange");
%>


<!--1st anchor link and menu -->

<p><a href="http://www.dynamicdrive.com" class="anchorclass" rel="submenu1">Default Example</a></p>

                                                    
<div id="submenu1" class="anylinkcss">
<ul>
<li><a href="http://www.dynamicdrive.com/">Dynamic Drive</a></li>
<li><a href="http://www.cssdrive.com">CSS Drive</a></li>
<li><a href="http://www.javascriptkit.com">JavaScript Kit</a></li>
<li><a href="http://www.codingforums.com">Coding Forums</a></li>
<li><a href="http://www.javascriptkit.com/jsref/">JavaScript Reference</a></li>
</ul>
</div>






<!--2nd anchor link and menu -->

<p><a href="http://www.dynamicdrive.com" class="anchorclass someotherclass" rel="submenu2[click]" rev="lr">Right dropping menu</a> (click to reveal)</p>
                                                    
<div id="submenu2" class="anylinkcss" style="width: 150px; background-color:#E9FECB">
<ul>
<li><a href="http://www.cnn.com/">CNN</a></li>
<li><a href="http://www.msnbc.com">MSNBC</a></li>
<li><a href="http://www.google.com">Google</a></li>
<li><a href="http://news.bbc.co.uk">BBC News</a></li>
</ul>
</div>








<!--3rd anchor link and menu -->

<p style="text-align:center"><a href="http://www.dynamicdrive.com" class="anchorclass myownclass" rel="submenu3">Menu with multiple columns</a></p>
                                                    
<div id="submenu3" class="anylinkcsscols">

<div class="column">
    <b>Web Development</b>
    <ul>
    <li><a href="http://www.dynamicdrive.com/">Dynamic Drive</a></li>
    <li><a href="http://www.cssdrive.com">CSS Drive</a></li>
    <li><a href="http://www.javascriptkit.com">JavaScript Kit</a></li>
    <li><a href="http://www.codingforums.com">Coding Forums</a></li>
    <li><a href="http://www.javascriptkit.com/domref/">DOM Reference</a></li>
    </ul>
</div>

<div class="column">
    <b>News Related</b>
    <ul>
    <li><a href="http://www.cnn.com/">CNN</a></li>
    <li><a href="http://www.msnbc.com">MSNBC</a></li>
    <li><a href="http://www.google.com">Google</a></li>
    <li><a href="http://news.bbc.co.uk">BBC News</a></li>
    </ul>
</div>

<div class="column">
    <b>Technology</b>
    <ul>
    <li><a href="http://www.news.com/">News.com</a></li>
    <li><a href="http://www.slashdot.com">SlashDot</a></li>
    <li><a href="http://www.digg.com">Digg</a></li>
    <li><a href="http://www.techcrunch.com">Tech Crunch</a></li>
    </ul>
</div>

</div>




<!--4th anchor link-->

<p><a href="http://www.dynamicdrive.com" class="anchorclass" rel="submenu4" data-image="http://i44.tinypic.com/2mni3yx.gif" data-overimage="http://i43.tinypic.com/24fycd5.gif"><img src="http://i44.tinypic.com/2mni3yx.gif" style="border-width:0" /></a></p>

<div id="submenu4" class="anylinkcsscols" style="background: #FEEBAB">

<div class="column">
    <b>Web Development</b>
    <ul>
    <li><a href="http://www.dynamicdrive.com/">Dynamic Drive</a></li>
    <li><a href="http://www.cssdrive.com">CSS Drive</a></li>
    <li><a href="http://www.javascriptkit.com">JavaScript Kit</a></li>
    <li><a href="http://www.codingforums.com">Coding Forums</a></li>
    <li><a href="http://www.javascriptkit.com/domref/">DOM Reference</a></li>
    </ul>
</div>

<div class="column">
    <b>News Related</b>
    <ul>
    <li><a href="http://www.cnn.com/">CNN</a></li>
    <li><a href="http://www.msnbc.com">MSNBC</a></li>
    <li><a href="http://www.google.com">Google</a></li>
    <li><a href="http://news.bbc.co.uk">BBC News</a></li>
    </ul>
</div>

<div class="column">
    <b>Technology</b>
    <ul>
    <li><a href="http://www.news.com/">News.com</a></li>
    <li><a href="http://www.slashdot.com">SlashDot</a></li>
    <li><a href="http://www.digg.com">Digg</a></li>
    <li><a href="http://www.techcrunch.com">Tech Crunch</a></li>
    </ul>
</div>

</div>



<script type="text/javascript">

//anylinkcssmenu.init("menu_anchors_class") //call this function at the very *end* of the document!
anylinkcssmenu.init("anchorclass")
</script>




