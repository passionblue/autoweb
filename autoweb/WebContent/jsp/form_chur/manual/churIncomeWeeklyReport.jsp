<%@page import="com.autosite.util.chur.ChurUtil"%>
<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>

<% 
	Site site = SiteDS.getInstance().registerSite(request.getServerName());
    SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());
    
	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
    Map reqParams = (Map) session.getAttribute("k_reserved_params");
    
   	int _selectedYear = ChurWebUtil.getSelectedYear(request );
	String _selectedWeek = ChurWebUtil.getSelectedWeek(request );

%> 

<br>

<h3> 주양장로교회 헌금통계 (<%= _selectedWeek %>)</h3>
<br>



<!-- ========================================================================================================= -->
<!-- TABLE class="mytable1" style="border: 1px #6699CC solid; border-collapse: collapse; width: 600px;" -->

<TABLE class="tablesorter" cellspacing="0">
    <thead> <!-- for the table sorter, need to use thead and th -->
<TR >
	<th style="border: 1px #6699CC solid;" >이름</th>
	<th style="border: 1px #6699CC solid;" > <b>십일조</b></th>
	<th style="border: 1px #6699CC solid;" > <b>주일</b></th>
	<th style="border: 1px #6699CC solid;" > <b>감사</b></th>
	<th style="border: 1px #6699CC solid;" > <b>선교</b></th>
	<th style="border: 1px #6699CC solid;" > <b>건축</b></th>
	<th style="border: 1px #6699CC solid;" > <b>기타</b></th>
	<th style="border: 1px #6699CC solid;" > <b>개인합계</b></th>
</TR>
    </thead> 

<%
	Double ti ; 
	Double we;
	Double th;
	Double mi;
	Double co;
	Double ot;

	List<Map<String,Object>> lst = ChurReportUtil.getFormatIncomeList(site.getId(),_selectedYear, _selectedWeek);

	Map itemTotal = new HashMap();;

	for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
    	Map<String, Object> map = (Map<String, Object>) iterator.next();

    	if ( map.containsKey("isTotalLine")) {
    	    itemTotal = map;
    	    continue;
    	}
    	
    	ChurMember member = (ChurMember) map.get("member");
    	
    	ChurIncome tiInc = (ChurIncome)map.get("tithe"); 
        ChurIncome weInc = (ChurIncome)map.get("weekly");
        ChurIncome thInc = (ChurIncome)map.get("thanks");
        ChurIncome miInc = (ChurIncome)map.get("mission");
        ChurIncome coInc = (ChurIncome)map.get("construction"); 
        ChurIncome otInc = (ChurIncome)map.get("other");
        
        Double memberTotal  = (Double)map.get("memberTotal");
%>

<TR>
	<td style="border: 1px #6699CC solid;" > <%= member.getFullName() %></td>
    <td style="border: 1px #6699CC solid;" > <%= WebUtil.display(tiInc==null?0.0:tiInc.getAmmount()) %> </td>
    <td style="border: 1px #6699CC solid;" > <%= WebUtil.display(weInc==null?0.0:weInc.getAmmount()) %> </td>
    <td style="border: 1px #6699CC solid;" > <%= WebUtil.display(thInc==null?0.0:thInc.getAmmount()) %> </td>
    <td style="border: 1px #6699CC solid;" > <%= WebUtil.display(miInc==null?0.0:miInc.getAmmount()) %> </td>
    <td style="border: 1px #6699CC solid;" > <%= WebUtil.display(coInc==null?0.0:coInc.getAmmount()) %> </td>
    <td style="border: 1px #6699CC solid;" > <%= WebUtil.display(otInc==null?0.0:otInc.getAmmount()) %> </td>
    <td style="border: 1px #6699CC solid;" > <%= WebUtil.display(memberTotal) %> </td>
    
</TR>
<%
	}
	
	double totalDay = 0.0;
	
	ti = (Double)itemTotal.get("tithe");
	we = (Double)itemTotal.get("weekly");
	th = (Double)itemTotal.get("thanks");
	mi = (Double)itemTotal.get("mission");
	co = (Double)itemTotal.get("construction");
	ot = (Double)itemTotal.get("other");

	totalDay += (ti==null?0.0:ti.doubleValue());
	totalDay += (we==null?0.0:we.doubleValue());
	totalDay += (th==null?0.0:th.doubleValue());
	totalDay += (mi==null?0.0:mi.doubleValue());
	totalDay += (co==null?0.0:co.doubleValue());
	totalDay += (ot==null?0.0:ot.doubleValue());
	
%>
<tfoot>
<TR >
	<td style="border: 1px #6699CC solid; padding:4px;" > <b><%= "TOTAL" %></b></td>
    <td style="border: 1px #6699CC solid; padding:4px;" > <%= WebUtil.display(ti==null?0.0:ti.doubleValue()) %> </td>
    <td style="border: 1px #6699CC solid; padding:4px;" > <%= WebUtil.display(we==null?0.0:we.doubleValue()) %> </td>
    <td style="border: 1px #6699CC solid; padding:4px;" > <%= WebUtil.display(th==null?0.0:th.doubleValue()) %> </td>
    <td style="border: 1px #6699CC solid; padding:4px;" > <%= WebUtil.display(mi==null?0.0:mi.doubleValue()) %> </td>
    <td style="border: 1px #6699CC solid; padding:4px;" > <%= WebUtil.display(co==null?0.0:co.doubleValue()) %> </td>
    <td style="border: 1px #6699CC solid; padding:4px;" > <%= WebUtil.display(ot==null?0.0:ot.doubleValue()) %> </td>
    <td style="border: 1px #6699CC solid; padding:4px;" > <b><%= WebUtil.display(totalDay) %></b> </td>
</TR>
</tfoot>
</TABLE>
<br>
<br>
<br>


<%
	//######################################################################################################################################

	List items2 = ChurIncomeItemDS.getInstance().getBySiteId(site.getId());
	for (Iterator iterator = items2.iterator(); iterator.hasNext();) {
	    
		ChurIncomeItem item = (ChurIncomeItem)  iterator.next();
		List<Map<String,Object>> memListForItem = ChurReportUtil.getSortedMemberListForItem(site.getId(),_selectedYear, _selectedWeek, item.getIncomeItem());
		
		if (memListForItem.size() == 0  ||
		        item.getIncomeItem().equals("tithe") || 
		        item.getIncomeItem().equals("weekly") || 
		        item.getIncomeItem().equals("thanks") || 
		        item.getIncomeItem().equals("mission") ||
		        item.getIncomeItem().equals("construction") )
		    continue;
		
%>

<TABLE class="tablesorter" cellspacing="0">
    <thead> <!-- for the table sorter, need to use thead and th -->
		<TR >
			<th style="border: 1px #6699CC solid;" >이름</th>
			<th style="border: 1px #6699CC solid;" > <b> <%=item.getDisplay() %></b></th>
			<th style="border: 1px #6699CC solid;" > <b>합계</b></th>
		</TR>
    </thead> 

<%
	double totalForThis = 0.0; 

	Map itemTotal2 = new HashMap();;

	for (Iterator iterator2 = lst.iterator(); iterator2.hasNext();) {
    	Map<String, Object> map = (Map<String, Object>) iterator2.next();

    	if ( map.containsKey("isTotalLine")) {
    	    itemTotal2 = map;
    	    continue;
    	}
    	
    	ChurMember 	member 			= (ChurMember) map.get("member");
    	ChurIncome 	churIncome 		= (ChurIncome) map.get(item.getIncomeItem()); 
        Double 		memberTotal  	= (Double)     map.get("memberTotal");
        
        
        if( churIncome == null || churIncome.getAmmount() == 0.0)
            continue;
        
        totalForThis += churIncome==null?0.0:churIncome.getAmmount();
        
%>
		<TR>
			<td style="border: 1px #6699CC solid;" > <%= member.getFullName() %></td>
		    <td style="border: 1px #6699CC solid;" > <%= WebUtil.display(churIncome==null?0.0:churIncome.getAmmount()) %> </td>
		    <td style="border: 1px #6699CC solid;" > <%= ""  %> </td>
		</TR>
<%
	}
%>

</TABLE>
<br>

<%		
	}
%>


<!-- ========================================================================================================= -->
<!-- ========================================================================================================= -->
<!-- ========================================================================================================= -->

<%
	List items = ChurIncomeItemDS.getInstance().getBySiteId(site.getId());
	for (Iterator iterator = items.iterator(); iterator.hasNext();) {
		ChurIncomeItem item = (ChurIncomeItem)  iterator.next();
%>
<%= item.getDisplay() + "  (" + (itemTotal.get(item.getIncomeItem())==null?0.0: itemTotal.get(item.getIncomeItem())) + ") " %>
<%
		
		List sorted = ChurReportUtil.getSortedMemberListForItem(site.getId(),_selectedYear, _selectedWeek, item.getIncomeItem());
		for (Iterator iterator2 = sorted.iterator(); iterator2.hasNext();) {
			String name = (String)  iterator2.next();
%>
	<%=name+"," %>
<%
		}
%>
<br>
<%		
	}
%>


<script type="text/javascript">

$(document).ready(function() { 
    // call the tablesorter plugin 
    $("table").tablesorter({ 
        // sort on the first column and third column, order asc 
        sortList: [[0,0]] 
    }); 
}); 
	
</script>