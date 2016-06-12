<%@page import="com.autosite.util.sweep.Sweep2014Util"%>
<%@page language="java" import="java.util.*,com.jtrend.session.*,com.jtrend.util.*,com.autosite.*,com.autosite.ds.*,com.autosite.session.*,com.autosite.db.*,com.autosite.holder.*,com.autosite.util.*,com.autosite.util.chur.*,com.autosite.util.cleaner.*,com.autosite.util.web.*"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%!
public String getWinLose(int winLose)
{
    switch(winLose) {
        case 0: return "NOT ENTERED";
        case 1: return "WIN";
        case 2: return "LOSE";
        case 3: return "DRAW";
    }

    return "XX";
}

public int getWinLosePoint(int results, int bet) {
    
    if ( results == bet) 
        return 3;
    
    return 0;
}

public int getScoprePoint(int kr, int kb, int or, int ob) {
    return getScoprePoint (kr, kb, or, ob, 3);
}
public int getScoprePoint(int kr, int kb, int or, int ob, int whole) {

    if ( kr == -1 || or == -1 ) return 0;
    
    int offset = Math.abs(kr-kb) + Math.abs(or-ob);
    
    if ( offset >= whole) return 0;
    
    return whole-offset;
}

public String getAdvanceString(int bet) {

    if ( bet == 0 ) return "실패";
    return "성공";
}
public int getAdvancePoint(int res, int bet) {

    if ( bet == res ) return 5;
    return 0;
}


public String get16bets(Map results, SweepWorldcup2014 mine) {
    StringBuilder builder = new StringBuilder();
    
    builder.append(decorateHitter(results, mine.getTeam16A1()));
    builder.append(decorateHitter(results, mine.getTeam16A2()));

    builder.append(decorateHitter(results, mine.getTeam16B1()));
    builder.append(decorateHitter(results, mine.getTeam16B2()));

    builder.append(decorateHitter(results, mine.getTeam16C1()));
    builder.append(decorateHitter(results, mine.getTeam16C2()));

    builder.append(decorateHitter(results, mine.getTeam16D1()));
    builder.append(decorateHitter(results, mine.getTeam16D2()));

    builder.append(decorateHitter(results, mine.getTeam16E1()));
    builder.append(decorateHitter(results, mine.getTeam16E2()));

    builder.append(decorateHitter(results, mine.getTeam16F1()));
    builder.append(decorateHitter(results, mine.getTeam16F2()));

    builder.append(decorateHitter(results, mine.getTeam16G1()));
    builder.append(decorateHitter(results, mine.getTeam16G2()));

    builder.append(decorateHitter(results, mine.getTeam16H1()));
    builder.append(decorateHitter(results, mine.getTeam16H2()));

    return builder.toString();
}

public String get8bets(Map results, SweepWorldcup2014 mine) {
    StringBuilder builder = new StringBuilder();
    
    builder.append(decorateHitter(results, mine.getQuarterFinal1()));
    builder.append(decorateHitter(results, mine.getQuarterFinal2()));
    builder.append(decorateHitter(results, mine.getQuarterFinal3()));
    builder.append(decorateHitter(results, mine.getQuarterFinal4()));
    builder.append(decorateHitter(results, mine.getQuarterFinal5()));
    builder.append(decorateHitter(results, mine.getQuarterFinal6()));
    builder.append(decorateHitter(results, mine.getQuarterFinal7()));
    builder.append(decorateHitter(results, mine.getQuarterFinal8()));


    return builder.toString();
}

public String get4bets(Map results, SweepWorldcup2014 mine) {
    StringBuilder builder = new StringBuilder();
    
    builder.append(decorateHitter(results, mine.getSemiFinal1()));
    builder.append(decorateHitter(results, mine.getSemiFinal2()));
    builder.append(decorateHitter(results, mine.getSemiFinal3()));
    builder.append(decorateHitter(results, mine.getSemiFinal4()));

    return builder.toString();
}

public String get2bets(Map results, SweepWorldcup2014 mine) {
    StringBuilder builder = new StringBuilder();
    
    builder.append(decorateHitter(results, mine.getFinal1()));
    builder.append(decorateHitter(results, mine.getFinal2()));
    return builder.toString();
}



public int get16Point(Map results, SweepWorldcup2014 mine) {

    int increment = 2;
    int total = 0;
    
    total += results.containsKey(mine.getTeam16A1())? increment:0;
    total += results.containsKey(mine.getTeam16A2())? increment:0;
    
    total += results.containsKey(mine.getTeam16B1())? increment:0;
    total += results.containsKey(mine.getTeam16B2())? increment:0;
    
    total += results.containsKey(mine.getTeam16C1())? increment:0;
    total += results.containsKey(mine.getTeam16C2())? increment:0;
    
    total += results.containsKey(mine.getTeam16D1())? increment:0;
    total += results.containsKey(mine.getTeam16D2())? increment:0;
    
    total += results.containsKey(mine.getTeam16E1())? increment:0;
    total += results.containsKey(mine.getTeam16E2())? increment:0;
    
    total += results.containsKey(mine.getTeam16F1())? increment:0;
    total += results.containsKey(mine.getTeam16F2())? increment:0;
    
    total += results.containsKey(mine.getTeam16G1())? increment:0;
    total += results.containsKey(mine.getTeam16G2())? increment:0;
    
    total += results.containsKey(mine.getTeam16H1())? increment:0;
    total += results.containsKey(mine.getTeam16H2())? increment:0;
    
    return total;
}

public int get8Point(Map results, SweepWorldcup2014 mine) {

    int increment = 3;
    int total = 0;
    
    total += results.containsKey(mine.getQuarterFinal1())? increment:0;
    total += results.containsKey(mine.getQuarterFinal2())? increment:0;
    total += results.containsKey(mine.getQuarterFinal3())? increment:0;
    total += results.containsKey(mine.getQuarterFinal4())? increment:0;
    total += results.containsKey(mine.getQuarterFinal5())? increment:0;
    total += results.containsKey(mine.getQuarterFinal6())? increment:0;
    total += results.containsKey(mine.getQuarterFinal7())? increment:0;
    total += results.containsKey(mine.getQuarterFinal8())? increment:0;
    
    return total;
}

public int get4Point(Map results, SweepWorldcup2014 mine) {

    int increment = 4;
    int total = 0;
    
    total += results.containsKey(mine.getSemiFinal1())? increment:0;
    total += results.containsKey(mine.getSemiFinal2())? increment:0;
    total += results.containsKey(mine.getSemiFinal3())? increment:0;
    total += results.containsKey(mine.getSemiFinal4())? increment:0;
    
    return total;
}

public int get2Point(Map results, SweepWorldcup2014 mine) {

    int increment = 5;
    int total = 0;
    
    total += results.containsKey(mine.getFinal1())? increment:0;
    total += results.containsKey(mine.getFinal2())? increment:0;
    
    
    return total;
}

public int getChampPoint(SweepWorldcup2014 result, SweepWorldcup2014 mine) {

    int increment = 5;
    int total = 0;
    
	if (result.getChampion()!= null && result.getChampion().equals(mine.getChampion())){
	    total +=7;
	}
	
	total += getScoprePoint(result.getFinalScoreWin(), mine.getFinalScoreWin(), result.getFinalScoreLose(), result.getFinalScoreLose(), 5);
    
    return total;
}

public String decorateHitter(Map results, String team){
    
    if ( results.containsKey(team)) return "["+ team + "] ";
    return team + " ";
}


public int getGame1Points(SweepWorldcup2014 mine, SweepWorldcup2014 result){
    
    int total = 0;
    total += getWinLosePoint(result.getGame1(), mine.getGame1());
    total += getScoprePoint(result.getGame1Score(), mine.getGame1Score(), result.getGame1ScoreOpp(), mine.getGame1ScoreOpp());
    
    total += getWinLosePoint(result.getGame2(), mine.getGame2());
    total += getScoprePoint(result.getGame2Score(), mine.getGame2Score(), result.getGame2ScoreOpp(), mine.getGame2ScoreOpp());
    
    total += getWinLosePoint(result.getGame3(), mine.getGame3());
    total += getScoprePoint(result.getGame3Score(), mine.getGame3Score(), result.getGame3ScoreOpp(), mine.getGame3ScoreOpp());
    
    return total;
    
}

public int getGame2Points(SweepWorldcup2014 mine, SweepWorldcup2014 result){
    
    int total = 0;
    
	total +=  get16Point( getTeamResults(result, 16), mine);
	total +=  get8Point( getTeamResults(result, 8), mine);
	total +=  get4Point( getTeamResults(result, 4), mine);
	total +=  get2Point( getTeamResults(result, 2), mine);
    total +=  getChampPoint(result, mine);
    return total;
}


public Map getTeamResults(SweepWorldcup2014 res, int teamNum) {
    
    Map ret = new HashMap();

    if ( teamNum == 16 ) {
        ret.put(res.getTeam16A1(),res.getTeam16A1());
        ret.put(res.getTeam16A2(),res.getTeam16A2());
        
        ret.put(res.getTeam16B1(),res.getTeam16B1());
        ret.put(res.getTeam16B2(),res.getTeam16B2());
        
        ret.put(res.getTeam16C1(),res.getTeam16C1());
        ret.put(res.getTeam16C2(),res.getTeam16C2());
        
        ret.put(res.getTeam16D1(),res.getTeam16D1());
        ret.put(res.getTeam16D2(),res.getTeam16D2());
        
        ret.put(res.getTeam16E1(),res.getTeam16E1());
        ret.put(res.getTeam16E2(),res.getTeam16E2());
        
        ret.put(res.getTeam16F1(),res.getTeam16F1());
        ret.put(res.getTeam16F2(),res.getTeam16F2());
        
        ret.put(res.getTeam16G1(),res.getTeam16G1());
        ret.put(res.getTeam16G2(),res.getTeam16G2());
        
        ret.put(res.getTeam16H1(),res.getTeam16H1());
        ret.put(res.getTeam16H2(),res.getTeam16H2());
        
    } else if ( teamNum == 8 ) {

        ret.put(res.getQuarterFinal1(),res.getQuarterFinal1());
        ret.put(res.getQuarterFinal2(),res.getQuarterFinal2());
        ret.put(res.getQuarterFinal3(),res.getQuarterFinal3());
        ret.put(res.getQuarterFinal4(),res.getQuarterFinal4());
        ret.put(res.getQuarterFinal5(),res.getQuarterFinal5());
        ret.put(res.getQuarterFinal6(),res.getQuarterFinal6());
        ret.put(res.getQuarterFinal7(),res.getQuarterFinal7());
        ret.put(res.getQuarterFinal8(),res.getQuarterFinal8());
    
    } else if ( teamNum == 4 ) {

        ret.put(res.getSemiFinal1(),res.getSemiFinal1());
        ret.put(res.getSemiFinal2(),res.getSemiFinal2());
        ret.put(res.getSemiFinal3(),res.getSemiFinal3());
        ret.put(res.getSemiFinal4(),res.getSemiFinal4());
    
    } else if ( teamNum == 2 ) {

        ret.put(res.getFinal1(),res.getFinal1());
        ret.put(res.getFinal2(),res.getFinal2());
        
    } else {
        ret.put(res.getChampion(),res.getChampion());
        
    }
    
    return ret;
    
}


%>
<% 
    Site site = SiteDS.getInstance().registerSite(request.getServerName());
     SiteConfig siteConfig = SiteConfigDS.getInstance().getSiteConfigBySiteId(site.getId());

	AutositeSessionContext sessionContext = (AutositeSessionContext) session.getAttribute("k_session_context");
	String listAllByAdmin = request.getParameter("listAllByAdmin");
	
	boolean showListAllByAdmin = false;
	if ( sessionContext.isSuperAdminLogin() && WebUtil.isTrue(listAllByAdmin)){
		showListAllByAdmin = true;
	}

	List list = new ArrayList();
	SweepWorldcup2014DS ds = SweepWorldcup2014DS.getInstance();    

	
	SweepWorldcup2014 result = ds.getById(30L); //L, true);
	
	if (showListAllByAdmin)
		list = ds.getAll();
	else		
    	list = ds.getBySiteId(site.getId());
	SiteDS siteDS = SiteDS.getInstance();

	// Results
	// A BRA CRO MEX CMR
	// B ESP NED CHI AUS
	// C COL GRE CIV JPN
	// D URU CRC ENG ITA
	// E SUI ECU FRA HON 
	// F ARG BIH IRN NGA 
	// G GER POR GHA USA 
	// H BEL ALG RUS KOR
	
	
	int game1  = result.getGame1();
	int game1K = result.getGame1Score();
	int game1O = result.getGame1ScoreOpp();
	
	int game2  = result.getGame2();
	int game2K = result.getGame2Score();
	int game2O = result.getGame2ScoreOpp();

	int game3  = result.getGame3();
	int game3K = result.getGame3Score();
	int game3O = result.getGame3ScoreOpp();
	
	
	Map team16 = getTeamResults(result, 16); 
	
	//A
	//B
//	team16.put("NED", Sweep2014Util.getTeamName("NED"));
//	team16.put("CHI", Sweep2014Util.getTeamName("CHI"));
	//C
	//D
//	team16.put("CRC", Sweep2014Util.getTeamName("CRC"));
	//E
	//F
	//G
	//H
	
	Map team8 = getTeamResults(result, 8); 
	Map team4 = getTeamResults(result, 4); 
	Map team2 = getTeamResults(result, 2); 
	Map champ = getTeamResults(result, 1); 
	
	
	
	
%> 
<br/>



<%
	List list_SweepWorldcup2014 = new ArrayList();
	SweepWorldcup2014DS ds_SweepWorldcup2014 = SweepWorldcup2014DS.getInstance();    

	boolean  showListAllByAdmin2 = false;

	if (showListAllByAdmin2)
		list_SweepWorldcup2014 = ds_SweepWorldcup2014.getAll();
	else		
    	list_SweepWorldcup2014 = ds_SweepWorldcup2014.getBySiteId(site.getId());

%> 

<!-- =================== END PAGING =================== -->

<H3>힘든 와중에도 졸라 힘들게 만든 월드큽 베팅 대쉬보드</H3>
<%

    for(Iterator iter = list_SweepWorldcup2014.iterator();iter.hasNext();) {
        SweepWorldcup2014 myresults = (SweepWorldcup2014) iter.next();
        
         if ( myresults.getPlayer().toUpperCase().equalsIgnoreCase("RESULTS") ) continue;
%>
 
<TABLE class="mytable1">

<TR >
    <td class="columnTitle" colspan="3"> <%= myresults.getPlayer() %> </td>
	<td class="columnTitle" > Game 1 [ <%=getGame1Points(myresults, result) %> ]</td>
	<td class="columnTitle" > Game 2 [ <%=getGame2Points(myresults, result) %> ]</td>
</TR>
<TR >
    <td class="columnTitle"> VS RUSSIA   </td>
    <td class="columnTitle"> <%= getWinLose(myresults.getGame1()) %></td>
	<td class="columnTitle"> <%= myresults.getGame1Score()  %> </td>
	<td class="columnTitle"> <%= myresults.getGame1ScoreOpp()  %></td>
	<td class="columnTitle"> <%= getScoprePoint(game1K, myresults.getGame1Score(), game1O, myresults.getGame1ScoreOpp()) + getWinLosePoint(game1,myresults.getGame1() )%></td>
</TR>

<TR >
    <td class="columnTitle"> VS ALGERIA    </td>
    <td class="columnTitle"> <%= getWinLose(myresults.getGame2()) %></td>
	<td class="columnTitle"> <%= myresults.getGame2Score()  %> </td>
	<td class="columnTitle"> <%= myresults.getGame2ScoreOpp()  %></td>
	<td class="columnTitle"> <%= getScoprePoint(game2K, myresults.getGame2Score(), game2O, myresults.getGame2ScoreOpp())  + getWinLosePoint(game2,myresults.getGame2() )%></td>
</TR>
<TR >
    <td class="columnTitle"> VS BELGIUM    </td>
    <td class="columnTitle"> <%= getWinLose(myresults.getGame3()) %></td>
	<td class="columnTitle"> <%= myresults.getGame3Score()  %> </td>
	<td class="columnTitle"> <%= myresults.getGame3ScoreOpp()  %></td>
	<td class="columnTitle"> <%= getScoprePoint(game3K, myresults.getGame3Score(), game3O, myresults.getGame3ScoreOpp()) + getWinLosePoint(game3,myresults.getGame3() ) %></td>
</TR>
<TR >
    <td class="columnTitle" colspan="4"> 16강   <%= getAdvanceString(myresults.getAdvance()) %> </td>
	<td class="columnTitle" > <%= getAdvancePoint(-1, myresults.getAdvance()) %></td>
</TR>
<TR >
	<td class="columnTitle" colspan="4"> <%= get16bets(team16, myresults) %></td>
	<td class="columnTitle" > <%= get16Point(team16, myresults) %></td>
</TR>
	
<TR >
	<td class="columnTitle" colspan="4"> <%= get8bets(team8, myresults) %></td>
	<td class="columnTitle" > <%= get8Point(team8, myresults) %></td>
</TR>
<TR >
	<td class="columnTitle" colspan="4"> <%= get4bets(team4, myresults) %></td>
	<td class="columnTitle" > <%= get4Point(team4, myresults) %></td>
</TR>

<TR >
	<td class="columnTitle" colspan="4"> <%= get2bets(team2, myresults) %></td>
	<td class="columnTitle" > <%= get2Point(team2, myresults) %></td>
</TR>
	
<TR >
	<td class="columnTitle" colspan="4"> Champ <%= "  *"+ myresults.getChampion() + "*   "%>  Scopre ( <%= myresults.getFinalScoreWin() + ":" + myresults.getFinalScoreLose() %> )</td>
	<td class="columnTitle" > <%= getChampPoint(result, myresults) %></td>
</TR>
	
	
	
<% 	/* %>
    
	<td> <%= myresults.getPlayer()  %> </td>
	<td> <%= myresults.getGame1()  %> </td>
	<td> <%= myresults.getGame1Score()  %> </td>
	<td> <%= myresults.getGame1ScoreOpp()  %> </td>
	<td> <%= myresults.getGame2()  %> </td>
	<td> <%= myresults.getGame2Score()  %> </td>
	<td> <%= myresults.getGame2ScoreOpp()  %> </td>
	<td> <%= myresults.getGame3()  %> </td>
	<td> <%= myresults.getGame3Score()  %> </td>
	<td> <%= myresults.getGame3ScoreOpp()  %> </td>
	<td> <%= myresults.getTeam16A1()  %> </td>
	<td> <%= myresults.getTeam16A2()  %> </td>
	<td> <%= myresults.getTeam16B1()  %> </td>
	<td> <%= myresults.getTeam16B2()  %> </td>
	<td> <%= myresults.getTeam16C1()  %> </td>
	<td> <%= myresults.getTeam16C2()  %> </td>
	<td> <%= myresults.getTeam16D1()  %> </td>
	<td> <%= myresults.getTeam16D2()  %> </td>
	<td> <%= myresults.getTeam16E1()  %> </td>
	<td> <%= myresults.getTeam16E2()  %> </td>
	<td> <%= myresults.getTeam16F1()  %> </td>
	<td> <%= myresults.getTeam16F2()  %> </td>
	<td> <%= myresults.getTeam16G1()  %> </td>
	<td> <%= myresults.getTeam16G2()  %> </td>
	<td> <%= myresults.getTeam16H1()  %> </td>
	<td> <%= myresults.getTeam16H2()  %> </td>
	<td> <%= myresults.getQuarterFinal1()  %> </td>
	<td> <%= myresults.getQuarterFinal2()  %> </td>
	<td> <%= myresults.getQuarterFinal3()  %> </td>
	<td> <%= myresults.getQuarterFinal4()  %> </td>
	<td> <%= myresults.getQuarterFinal5()  %> </td>
	<td> <%= myresults.getQuarterFinal6()  %> </td>
	<td> <%= myresults.getQuarterFinal7()  %> </td>
	<td> <%= myresults.getQuarterFinal8()  %> </td>
	<td> <%= myresults.getSemiFinal1()  %> </td>
	<td> <%= myresults.getSemiFinal2()  %> </td>
	<td> <%= myresults.getSemiFinal3()  %> </td>
	<td> <%= myresults.getSemiFinal4()  %> </td>
	<td> <%= myresults.getFinal1()  %> </td>
	<td> <%= myresults.getFinal2()  %> </td>
	<td> <%= myresults.getChampion()  %> </td>
	<td> <%= myresults.getFinalScoreWin()  %> </td>
	<td> <%= myresults.getFinalScoreLose()  %> </td>
	<td> <%= myresults.getTimeCreated()  %> </td>
	<td> <%= myresults.getTimeUpdated()  %> </td>
</TR>
<% 	*/ %>
</TABLE>
<%
    }
%>
