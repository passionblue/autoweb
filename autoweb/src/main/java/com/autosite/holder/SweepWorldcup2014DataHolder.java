package com.autosite.holder;

import java.sql.Timestamp;
import java.sql.Date;
import com.autosite.db.BaseAutositeDataObject;
import com.autosite.util.html.HtmlGenRow;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Set;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import com.jtrend.util.JSONValueFormatter;

import com.autosite.db.SweepWorldcup2014;


public class SweepWorldcup2014DataHolder  extends AbstractDataHolder  implements java.io.Serializable, DataHolderObject, HtmlGenRow {

 // Fields

	//Files to idx
    private static Map<String, Integer> fieldsMap = new ConcurrentSkipListMap<String, Integer>();

    static {
        fieldsMap.put("player", new Integer(0));
        fieldsMap.put("game1", new Integer(1));
        fieldsMap.put("game1Score", new Integer(2));
        fieldsMap.put("game1ScoreOpp", new Integer(3));
        fieldsMap.put("game2", new Integer(4));
        fieldsMap.put("game2Score", new Integer(5));
        fieldsMap.put("game2ScoreOpp", new Integer(6));
        fieldsMap.put("game3", new Integer(7));
        fieldsMap.put("game3Score", new Integer(8));
        fieldsMap.put("game3ScoreOpp", new Integer(9));
        fieldsMap.put("advance", new Integer(10));
        fieldsMap.put("team16A1", new Integer(11));
        fieldsMap.put("team16A2", new Integer(12));
        fieldsMap.put("team16B1", new Integer(13));
        fieldsMap.put("team16B2", new Integer(14));
        fieldsMap.put("team16C1", new Integer(15));
        fieldsMap.put("team16C2", new Integer(16));
        fieldsMap.put("team16D1", new Integer(17));
        fieldsMap.put("team16D2", new Integer(18));
        fieldsMap.put("team16E1", new Integer(19));
        fieldsMap.put("team16E2", new Integer(20));
        fieldsMap.put("team16F1", new Integer(21));
        fieldsMap.put("team16F2", new Integer(22));
        fieldsMap.put("team16G1", new Integer(23));
        fieldsMap.put("team16G2", new Integer(24));
        fieldsMap.put("team16H1", new Integer(25));
        fieldsMap.put("team16H2", new Integer(26));
        fieldsMap.put("quarterFinal1", new Integer(27));
        fieldsMap.put("quarterFinal2", new Integer(28));
        fieldsMap.put("quarterFinal3", new Integer(29));
        fieldsMap.put("quarterFinal4", new Integer(30));
        fieldsMap.put("quarterFinal5", new Integer(31));
        fieldsMap.put("quarterFinal6", new Integer(32));
        fieldsMap.put("quarterFinal7", new Integer(33));
        fieldsMap.put("quarterFinal8", new Integer(34));
        fieldsMap.put("semiFinal1", new Integer(35));
        fieldsMap.put("semiFinal2", new Integer(36));
        fieldsMap.put("semiFinal3", new Integer(37));
        fieldsMap.put("semiFinal4", new Integer(38));
        fieldsMap.put("final1", new Integer(39));
        fieldsMap.put("final2", new Integer(40));
        fieldsMap.put("champion", new Integer(41));
        fieldsMap.put("finalScoreWin", new Integer(42));
        fieldsMap.put("finalScoreLose", new Integer(43));
        fieldsMap.put("timeCreated", new Integer(44));
        fieldsMap.put("timeUpdated", new Integer(45));
    }


	SweepWorldcup2014 m_sweepWorldcup2014;

	public SweepWorldcup2014DataHolder(SweepWorldcup2014 _SweepWorldcup2014){
		m_sweepWorldcup2014 =  _SweepWorldcup2014; 	
	}

	public SweepWorldcup2014DataHolder(){
		m_sweepWorldcup2014 =  new SweepWorldcup2014(); 	
	}

	public  BaseAutositeDataObject getDataObject(){
		return 	m_sweepWorldcup2014;
	}


	public long getId(){
		return 	m_sweepWorldcup2014.getId();
	}
	public void setId(long id){
		// INVALID
	}
	public long getSiteId(){
		return 	m_sweepWorldcup2014.getSiteId();
	}
	public void setSiteId(long sid){
		m_sweepWorldcup2014.setSiteId(sid);
	}


	public String getPlayer(){
		return m_sweepWorldcup2014.getPlayer();
	}
	public int getGame1(){
		return m_sweepWorldcup2014.getGame1();
	}
	public int getGame1Score(){
		return m_sweepWorldcup2014.getGame1Score();
	}
	public int getGame1ScoreOpp(){
		return m_sweepWorldcup2014.getGame1ScoreOpp();
	}
	public int getGame2(){
		return m_sweepWorldcup2014.getGame2();
	}
	public int getGame2Score(){
		return m_sweepWorldcup2014.getGame2Score();
	}
	public int getGame2ScoreOpp(){
		return m_sweepWorldcup2014.getGame2ScoreOpp();
	}
	public int getGame3(){
		return m_sweepWorldcup2014.getGame3();
	}
	public int getGame3Score(){
		return m_sweepWorldcup2014.getGame3Score();
	}
	public int getGame3ScoreOpp(){
		return m_sweepWorldcup2014.getGame3ScoreOpp();
	}
	public int getAdvance(){
		return m_sweepWorldcup2014.getAdvance();
	}
	public String getTeam16A1(){
		return m_sweepWorldcup2014.getTeam16A1();
	}
	public String getTeam16A2(){
		return m_sweepWorldcup2014.getTeam16A2();
	}
	public String getTeam16B1(){
		return m_sweepWorldcup2014.getTeam16B1();
	}
	public String getTeam16B2(){
		return m_sweepWorldcup2014.getTeam16B2();
	}
	public String getTeam16C1(){
		return m_sweepWorldcup2014.getTeam16C1();
	}
	public String getTeam16C2(){
		return m_sweepWorldcup2014.getTeam16C2();
	}
	public String getTeam16D1(){
		return m_sweepWorldcup2014.getTeam16D1();
	}
	public String getTeam16D2(){
		return m_sweepWorldcup2014.getTeam16D2();
	}
	public String getTeam16E1(){
		return m_sweepWorldcup2014.getTeam16E1();
	}
	public String getTeam16E2(){
		return m_sweepWorldcup2014.getTeam16E2();
	}
	public String getTeam16F1(){
		return m_sweepWorldcup2014.getTeam16F1();
	}
	public String getTeam16F2(){
		return m_sweepWorldcup2014.getTeam16F2();
	}
	public String getTeam16G1(){
		return m_sweepWorldcup2014.getTeam16G1();
	}
	public String getTeam16G2(){
		return m_sweepWorldcup2014.getTeam16G2();
	}
	public String getTeam16H1(){
		return m_sweepWorldcup2014.getTeam16H1();
	}
	public String getTeam16H2(){
		return m_sweepWorldcup2014.getTeam16H2();
	}
	public String getQuarterFinal1(){
		return m_sweepWorldcup2014.getQuarterFinal1();
	}
	public String getQuarterFinal2(){
		return m_sweepWorldcup2014.getQuarterFinal2();
	}
	public String getQuarterFinal3(){
		return m_sweepWorldcup2014.getQuarterFinal3();
	}
	public String getQuarterFinal4(){
		return m_sweepWorldcup2014.getQuarterFinal4();
	}
	public String getQuarterFinal5(){
		return m_sweepWorldcup2014.getQuarterFinal5();
	}
	public String getQuarterFinal6(){
		return m_sweepWorldcup2014.getQuarterFinal6();
	}
	public String getQuarterFinal7(){
		return m_sweepWorldcup2014.getQuarterFinal7();
	}
	public String getQuarterFinal8(){
		return m_sweepWorldcup2014.getQuarterFinal8();
	}
	public String getSemiFinal1(){
		return m_sweepWorldcup2014.getSemiFinal1();
	}
	public String getSemiFinal2(){
		return m_sweepWorldcup2014.getSemiFinal2();
	}
	public String getSemiFinal3(){
		return m_sweepWorldcup2014.getSemiFinal3();
	}
	public String getSemiFinal4(){
		return m_sweepWorldcup2014.getSemiFinal4();
	}
	public String getFinal1(){
		return m_sweepWorldcup2014.getFinal1();
	}
	public String getFinal2(){
		return m_sweepWorldcup2014.getFinal2();
	}
	public String getChampion(){
		return m_sweepWorldcup2014.getChampion();
	}
	public int getFinalScoreWin(){
		return m_sweepWorldcup2014.getFinalScoreWin();
	}
	public int getFinalScoreLose(){
		return m_sweepWorldcup2014.getFinalScoreLose();
	}
	public Timestamp getTimeCreated(){
		return m_sweepWorldcup2014.getTimeCreated();
	}
	public Timestamp getTimeUpdated(){
		return m_sweepWorldcup2014.getTimeUpdated();
	}

	public void setPlayer(String _player ){
		m_sweepWorldcup2014.setPlayer(_player);
	}
	public void setGame1(int _game1 ){
		m_sweepWorldcup2014.setGame1(_game1);
	}
	public void setGame1Score(int _game1Score ){
		m_sweepWorldcup2014.setGame1Score(_game1Score);
	}
	public void setGame1ScoreOpp(int _game1ScoreOpp ){
		m_sweepWorldcup2014.setGame1ScoreOpp(_game1ScoreOpp);
	}
	public void setGame2(int _game2 ){
		m_sweepWorldcup2014.setGame2(_game2);
	}
	public void setGame2Score(int _game2Score ){
		m_sweepWorldcup2014.setGame2Score(_game2Score);
	}
	public void setGame2ScoreOpp(int _game2ScoreOpp ){
		m_sweepWorldcup2014.setGame2ScoreOpp(_game2ScoreOpp);
	}
	public void setGame3(int _game3 ){
		m_sweepWorldcup2014.setGame3(_game3);
	}
	public void setGame3Score(int _game3Score ){
		m_sweepWorldcup2014.setGame3Score(_game3Score);
	}
	public void setGame3ScoreOpp(int _game3ScoreOpp ){
		m_sweepWorldcup2014.setGame3ScoreOpp(_game3ScoreOpp);
	}
	public void setAdvance(int _advance ){
		m_sweepWorldcup2014.setAdvance(_advance);
	}
	public void setTeam16A1(String _team16A1 ){
		m_sweepWorldcup2014.setTeam16A1(_team16A1);
	}
	public void setTeam16A2(String _team16A2 ){
		m_sweepWorldcup2014.setTeam16A2(_team16A2);
	}
	public void setTeam16B1(String _team16B1 ){
		m_sweepWorldcup2014.setTeam16B1(_team16B1);
	}
	public void setTeam16B2(String _team16B2 ){
		m_sweepWorldcup2014.setTeam16B2(_team16B2);
	}
	public void setTeam16C1(String _team16C1 ){
		m_sweepWorldcup2014.setTeam16C1(_team16C1);
	}
	public void setTeam16C2(String _team16C2 ){
		m_sweepWorldcup2014.setTeam16C2(_team16C2);
	}
	public void setTeam16D1(String _team16D1 ){
		m_sweepWorldcup2014.setTeam16D1(_team16D1);
	}
	public void setTeam16D2(String _team16D2 ){
		m_sweepWorldcup2014.setTeam16D2(_team16D2);
	}
	public void setTeam16E1(String _team16E1 ){
		m_sweepWorldcup2014.setTeam16E1(_team16E1);
	}
	public void setTeam16E2(String _team16E2 ){
		m_sweepWorldcup2014.setTeam16E2(_team16E2);
	}
	public void setTeam16F1(String _team16F1 ){
		m_sweepWorldcup2014.setTeam16F1(_team16F1);
	}
	public void setTeam16F2(String _team16F2 ){
		m_sweepWorldcup2014.setTeam16F2(_team16F2);
	}
	public void setTeam16G1(String _team16G1 ){
		m_sweepWorldcup2014.setTeam16G1(_team16G1);
	}
	public void setTeam16G2(String _team16G2 ){
		m_sweepWorldcup2014.setTeam16G2(_team16G2);
	}
	public void setTeam16H1(String _team16H1 ){
		m_sweepWorldcup2014.setTeam16H1(_team16H1);
	}
	public void setTeam16H2(String _team16H2 ){
		m_sweepWorldcup2014.setTeam16H2(_team16H2);
	}
	public void setQuarterFinal1(String _quarterFinal1 ){
		m_sweepWorldcup2014.setQuarterFinal1(_quarterFinal1);
	}
	public void setQuarterFinal2(String _quarterFinal2 ){
		m_sweepWorldcup2014.setQuarterFinal2(_quarterFinal2);
	}
	public void setQuarterFinal3(String _quarterFinal3 ){
		m_sweepWorldcup2014.setQuarterFinal3(_quarterFinal3);
	}
	public void setQuarterFinal4(String _quarterFinal4 ){
		m_sweepWorldcup2014.setQuarterFinal4(_quarterFinal4);
	}
	public void setQuarterFinal5(String _quarterFinal5 ){
		m_sweepWorldcup2014.setQuarterFinal5(_quarterFinal5);
	}
	public void setQuarterFinal6(String _quarterFinal6 ){
		m_sweepWorldcup2014.setQuarterFinal6(_quarterFinal6);
	}
	public void setQuarterFinal7(String _quarterFinal7 ){
		m_sweepWorldcup2014.setQuarterFinal7(_quarterFinal7);
	}
	public void setQuarterFinal8(String _quarterFinal8 ){
		m_sweepWorldcup2014.setQuarterFinal8(_quarterFinal8);
	}
	public void setSemiFinal1(String _semiFinal1 ){
		m_sweepWorldcup2014.setSemiFinal1(_semiFinal1);
	}
	public void setSemiFinal2(String _semiFinal2 ){
		m_sweepWorldcup2014.setSemiFinal2(_semiFinal2);
	}
	public void setSemiFinal3(String _semiFinal3 ){
		m_sweepWorldcup2014.setSemiFinal3(_semiFinal3);
	}
	public void setSemiFinal4(String _semiFinal4 ){
		m_sweepWorldcup2014.setSemiFinal4(_semiFinal4);
	}
	public void setFinal1(String _final1 ){
		m_sweepWorldcup2014.setFinal1(_final1);
	}
	public void setFinal2(String _final2 ){
		m_sweepWorldcup2014.setFinal2(_final2);
	}
	public void setChampion(String _champion ){
		m_sweepWorldcup2014.setChampion(_champion);
	}
	public void setFinalScoreWin(int _finalScoreWin ){
		m_sweepWorldcup2014.setFinalScoreWin(_finalScoreWin);
	}
	public void setFinalScoreLose(int _finalScoreLose ){
		m_sweepWorldcup2014.setFinalScoreLose(_finalScoreLose);
	}
	public void setTimeCreated(Timestamp _timeCreated ){
		m_sweepWorldcup2014.setTimeCreated(_timeCreated);
	}
	public void setTimeUpdated(Timestamp _timeUpdated ){
		m_sweepWorldcup2014.setTimeUpdated(_timeUpdated);
	}

	// For HtmlGenRow
    public int getColumnCount() {
		return fieldsMap.size();
    }
    public static List getFieldsName(){
        return new ArrayList(fieldsMap.keySet());
    }
    public String getColumnData(String col){
        if(fieldsMap.containsKey(col)){
            return getColumnData(fieldsMap.get(col).intValue());
        } else {
            return null;
        }
    }

    public String getColumnData(int colNum) {

        if ( colNum == 0) return getPlayer() == null?"":getPlayer().toString();
        if ( colNum == 1) return String.valueOf(getGame1());
        if ( colNum == 2) return String.valueOf(getGame1Score());
        if ( colNum == 3) return String.valueOf(getGame1ScoreOpp());
        if ( colNum == 4) return String.valueOf(getGame2());
        if ( colNum == 5) return String.valueOf(getGame2Score());
        if ( colNum == 6) return String.valueOf(getGame2ScoreOpp());
        if ( colNum == 7) return String.valueOf(getGame3());
        if ( colNum == 8) return String.valueOf(getGame3Score());
        if ( colNum == 9) return String.valueOf(getGame3ScoreOpp());
        if ( colNum == 10) return String.valueOf(getAdvance());
        if ( colNum == 11) return getTeam16A1() == null?"":getTeam16A1().toString();
        if ( colNum == 12) return getTeam16A2() == null?"":getTeam16A2().toString();
        if ( colNum == 13) return getTeam16B1() == null?"":getTeam16B1().toString();
        if ( colNum == 14) return getTeam16B2() == null?"":getTeam16B2().toString();
        if ( colNum == 15) return getTeam16C1() == null?"":getTeam16C1().toString();
        if ( colNum == 16) return getTeam16C2() == null?"":getTeam16C2().toString();
        if ( colNum == 17) return getTeam16D1() == null?"":getTeam16D1().toString();
        if ( colNum == 18) return getTeam16D2() == null?"":getTeam16D2().toString();
        if ( colNum == 19) return getTeam16E1() == null?"":getTeam16E1().toString();
        if ( colNum == 20) return getTeam16E2() == null?"":getTeam16E2().toString();
        if ( colNum == 21) return getTeam16F1() == null?"":getTeam16F1().toString();
        if ( colNum == 22) return getTeam16F2() == null?"":getTeam16F2().toString();
        if ( colNum == 23) return getTeam16G1() == null?"":getTeam16G1().toString();
        if ( colNum == 24) return getTeam16G2() == null?"":getTeam16G2().toString();
        if ( colNum == 25) return getTeam16H1() == null?"":getTeam16H1().toString();
        if ( colNum == 26) return getTeam16H2() == null?"":getTeam16H2().toString();
        if ( colNum == 27) return getQuarterFinal1() == null?"":getQuarterFinal1().toString();
        if ( colNum == 28) return getQuarterFinal2() == null?"":getQuarterFinal2().toString();
        if ( colNum == 29) return getQuarterFinal3() == null?"":getQuarterFinal3().toString();
        if ( colNum == 30) return getQuarterFinal4() == null?"":getQuarterFinal4().toString();
        if ( colNum == 31) return getQuarterFinal5() == null?"":getQuarterFinal5().toString();
        if ( colNum == 32) return getQuarterFinal6() == null?"":getQuarterFinal6().toString();
        if ( colNum == 33) return getQuarterFinal7() == null?"":getQuarterFinal7().toString();
        if ( colNum == 34) return getQuarterFinal8() == null?"":getQuarterFinal8().toString();
        if ( colNum == 35) return getSemiFinal1() == null?"":getSemiFinal1().toString();
        if ( colNum == 36) return getSemiFinal2() == null?"":getSemiFinal2().toString();
        if ( colNum == 37) return getSemiFinal3() == null?"":getSemiFinal3().toString();
        if ( colNum == 38) return getSemiFinal4() == null?"":getSemiFinal4().toString();
        if ( colNum == 39) return getFinal1() == null?"":getFinal1().toString();
        if ( colNum == 40) return getFinal2() == null?"":getFinal2().toString();
        if ( colNum == 41) return getChampion() == null?"":getChampion().toString();
        if ( colNum == 42) return String.valueOf(getFinalScoreWin());
        if ( colNum == 43) return String.valueOf(getFinalScoreLose());
        if ( colNum == 44) return getTimeCreated() == null?"":getTimeCreated().toString();
        if ( colNum == 45) return getTimeUpdated() == null?"":getTimeUpdated().toString();
		return "";
    }


    public static JSONObject convertToJSON(SweepWorldcup2014 _SweepWorldcup2014, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair){
        return convertToJSON(_SweepWorldcup2014, fieldSet, ignoreFieldSet, useNameValuePair, false);
    }
    
    public static JSONObject convertToJSON(SweepWorldcup2014 _SweepWorldcup2014, Set fieldSet, boolean ignoreFieldSet, boolean useNameValuePair, boolean useJsonDataType){
        
        JSONObject json = new JSONObject();
        if ( _SweepWorldcup2014 == null) return json;

        json.put("id", ""+_SweepWorldcup2014.getId());
        json.put("type", "object");
		JSONArray array = new JSONArray();

		if ( useNameValuePair) {

		            JSONObject jsonPlayer = new JSONObject();
		            jsonPlayer.put("name", "player");
		            if ( useJsonDataType ) 
	                    jsonPlayer.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getPlayer()));
		            else
		                jsonPlayer.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getPlayer()));
		            array.put(jsonPlayer);
		            JSONObject jsonGame1 = new JSONObject();
		            jsonGame1.put("name", "game1");
		            if ( useJsonDataType ) 
	                    jsonGame1.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getGame1()));
		            else
		                jsonGame1.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getGame1()));
		            array.put(jsonGame1);
		            JSONObject jsonGame1Score = new JSONObject();
		            jsonGame1Score.put("name", "game1Score");
		            if ( useJsonDataType ) 
	                    jsonGame1Score.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getGame1Score()));
		            else
		                jsonGame1Score.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getGame1Score()));
		            array.put(jsonGame1Score);
		            JSONObject jsonGame1ScoreOpp = new JSONObject();
		            jsonGame1ScoreOpp.put("name", "game1ScoreOpp");
		            if ( useJsonDataType ) 
	                    jsonGame1ScoreOpp.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getGame1ScoreOpp()));
		            else
		                jsonGame1ScoreOpp.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getGame1ScoreOpp()));
		            array.put(jsonGame1ScoreOpp);
		            JSONObject jsonGame2 = new JSONObject();
		            jsonGame2.put("name", "game2");
		            if ( useJsonDataType ) 
	                    jsonGame2.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getGame2()));
		            else
		                jsonGame2.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getGame2()));
		            array.put(jsonGame2);
		            JSONObject jsonGame2Score = new JSONObject();
		            jsonGame2Score.put("name", "game2Score");
		            if ( useJsonDataType ) 
	                    jsonGame2Score.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getGame2Score()));
		            else
		                jsonGame2Score.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getGame2Score()));
		            array.put(jsonGame2Score);
		            JSONObject jsonGame2ScoreOpp = new JSONObject();
		            jsonGame2ScoreOpp.put("name", "game2ScoreOpp");
		            if ( useJsonDataType ) 
	                    jsonGame2ScoreOpp.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getGame2ScoreOpp()));
		            else
		                jsonGame2ScoreOpp.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getGame2ScoreOpp()));
		            array.put(jsonGame2ScoreOpp);
		            JSONObject jsonGame3 = new JSONObject();
		            jsonGame3.put("name", "game3");
		            if ( useJsonDataType ) 
	                    jsonGame3.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getGame3()));
		            else
		                jsonGame3.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getGame3()));
		            array.put(jsonGame3);
		            JSONObject jsonGame3Score = new JSONObject();
		            jsonGame3Score.put("name", "game3Score");
		            if ( useJsonDataType ) 
	                    jsonGame3Score.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getGame3Score()));
		            else
		                jsonGame3Score.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getGame3Score()));
		            array.put(jsonGame3Score);
		            JSONObject jsonGame3ScoreOpp = new JSONObject();
		            jsonGame3ScoreOpp.put("name", "game3ScoreOpp");
		            if ( useJsonDataType ) 
	                    jsonGame3ScoreOpp.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getGame3ScoreOpp()));
		            else
		                jsonGame3ScoreOpp.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getGame3ScoreOpp()));
		            array.put(jsonGame3ScoreOpp);
		            JSONObject jsonAdvance = new JSONObject();
		            jsonAdvance.put("name", "advance");
		            if ( useJsonDataType ) 
	                    jsonAdvance.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getAdvance()));
		            else
		                jsonAdvance.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getAdvance()));
		            array.put(jsonAdvance);
		            JSONObject jsonTeam16A1 = new JSONObject();
		            jsonTeam16A1.put("name", "team16A1");
		            if ( useJsonDataType ) 
	                    jsonTeam16A1.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16A1()));
		            else
		                jsonTeam16A1.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16A1()));
		            array.put(jsonTeam16A1);
		            JSONObject jsonTeam16A2 = new JSONObject();
		            jsonTeam16A2.put("name", "team16A2");
		            if ( useJsonDataType ) 
	                    jsonTeam16A2.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16A2()));
		            else
		                jsonTeam16A2.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16A2()));
		            array.put(jsonTeam16A2);
		            JSONObject jsonTeam16B1 = new JSONObject();
		            jsonTeam16B1.put("name", "team16B1");
		            if ( useJsonDataType ) 
	                    jsonTeam16B1.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16B1()));
		            else
		                jsonTeam16B1.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16B1()));
		            array.put(jsonTeam16B1);
		            JSONObject jsonTeam16B2 = new JSONObject();
		            jsonTeam16B2.put("name", "team16B2");
		            if ( useJsonDataType ) 
	                    jsonTeam16B2.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16B2()));
		            else
		                jsonTeam16B2.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16B2()));
		            array.put(jsonTeam16B2);
		            JSONObject jsonTeam16C1 = new JSONObject();
		            jsonTeam16C1.put("name", "team16C1");
		            if ( useJsonDataType ) 
	                    jsonTeam16C1.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16C1()));
		            else
		                jsonTeam16C1.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16C1()));
		            array.put(jsonTeam16C1);
		            JSONObject jsonTeam16C2 = new JSONObject();
		            jsonTeam16C2.put("name", "team16C2");
		            if ( useJsonDataType ) 
	                    jsonTeam16C2.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16C2()));
		            else
		                jsonTeam16C2.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16C2()));
		            array.put(jsonTeam16C2);
		            JSONObject jsonTeam16D1 = new JSONObject();
		            jsonTeam16D1.put("name", "team16D1");
		            if ( useJsonDataType ) 
	                    jsonTeam16D1.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16D1()));
		            else
		                jsonTeam16D1.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16D1()));
		            array.put(jsonTeam16D1);
		            JSONObject jsonTeam16D2 = new JSONObject();
		            jsonTeam16D2.put("name", "team16D2");
		            if ( useJsonDataType ) 
	                    jsonTeam16D2.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16D2()));
		            else
		                jsonTeam16D2.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16D2()));
		            array.put(jsonTeam16D2);
		            JSONObject jsonTeam16E1 = new JSONObject();
		            jsonTeam16E1.put("name", "team16E1");
		            if ( useJsonDataType ) 
	                    jsonTeam16E1.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16E1()));
		            else
		                jsonTeam16E1.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16E1()));
		            array.put(jsonTeam16E1);
		            JSONObject jsonTeam16E2 = new JSONObject();
		            jsonTeam16E2.put("name", "team16E2");
		            if ( useJsonDataType ) 
	                    jsonTeam16E2.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16E2()));
		            else
		                jsonTeam16E2.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16E2()));
		            array.put(jsonTeam16E2);
		            JSONObject jsonTeam16F1 = new JSONObject();
		            jsonTeam16F1.put("name", "team16F1");
		            if ( useJsonDataType ) 
	                    jsonTeam16F1.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16F1()));
		            else
		                jsonTeam16F1.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16F1()));
		            array.put(jsonTeam16F1);
		            JSONObject jsonTeam16F2 = new JSONObject();
		            jsonTeam16F2.put("name", "team16F2");
		            if ( useJsonDataType ) 
	                    jsonTeam16F2.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16F2()));
		            else
		                jsonTeam16F2.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16F2()));
		            array.put(jsonTeam16F2);
		            JSONObject jsonTeam16G1 = new JSONObject();
		            jsonTeam16G1.put("name", "team16G1");
		            if ( useJsonDataType ) 
	                    jsonTeam16G1.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16G1()));
		            else
		                jsonTeam16G1.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16G1()));
		            array.put(jsonTeam16G1);
		            JSONObject jsonTeam16G2 = new JSONObject();
		            jsonTeam16G2.put("name", "team16G2");
		            if ( useJsonDataType ) 
	                    jsonTeam16G2.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16G2()));
		            else
		                jsonTeam16G2.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16G2()));
		            array.put(jsonTeam16G2);
		            JSONObject jsonTeam16H1 = new JSONObject();
		            jsonTeam16H1.put("name", "team16H1");
		            if ( useJsonDataType ) 
	                    jsonTeam16H1.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16H1()));
		            else
		                jsonTeam16H1.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16H1()));
		            array.put(jsonTeam16H1);
		            JSONObject jsonTeam16H2 = new JSONObject();
		            jsonTeam16H2.put("name", "team16H2");
		            if ( useJsonDataType ) 
	                    jsonTeam16H2.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTeam16H2()));
		            else
		                jsonTeam16H2.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTeam16H2()));
		            array.put(jsonTeam16H2);
		            JSONObject jsonQuarterFinal1 = new JSONObject();
		            jsonQuarterFinal1.put("name", "quarterFinal1");
		            if ( useJsonDataType ) 
	                    jsonQuarterFinal1.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getQuarterFinal1()));
		            else
		                jsonQuarterFinal1.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getQuarterFinal1()));
		            array.put(jsonQuarterFinal1);
		            JSONObject jsonQuarterFinal2 = new JSONObject();
		            jsonQuarterFinal2.put("name", "quarterFinal2");
		            if ( useJsonDataType ) 
	                    jsonQuarterFinal2.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getQuarterFinal2()));
		            else
		                jsonQuarterFinal2.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getQuarterFinal2()));
		            array.put(jsonQuarterFinal2);
		            JSONObject jsonQuarterFinal3 = new JSONObject();
		            jsonQuarterFinal3.put("name", "quarterFinal3");
		            if ( useJsonDataType ) 
	                    jsonQuarterFinal3.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getQuarterFinal3()));
		            else
		                jsonQuarterFinal3.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getQuarterFinal3()));
		            array.put(jsonQuarterFinal3);
		            JSONObject jsonQuarterFinal4 = new JSONObject();
		            jsonQuarterFinal4.put("name", "quarterFinal4");
		            if ( useJsonDataType ) 
	                    jsonQuarterFinal4.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getQuarterFinal4()));
		            else
		                jsonQuarterFinal4.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getQuarterFinal4()));
		            array.put(jsonQuarterFinal4);
		            JSONObject jsonQuarterFinal5 = new JSONObject();
		            jsonQuarterFinal5.put("name", "quarterFinal5");
		            if ( useJsonDataType ) 
	                    jsonQuarterFinal5.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getQuarterFinal5()));
		            else
		                jsonQuarterFinal5.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getQuarterFinal5()));
		            array.put(jsonQuarterFinal5);
		            JSONObject jsonQuarterFinal6 = new JSONObject();
		            jsonQuarterFinal6.put("name", "quarterFinal6");
		            if ( useJsonDataType ) 
	                    jsonQuarterFinal6.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getQuarterFinal6()));
		            else
		                jsonQuarterFinal6.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getQuarterFinal6()));
		            array.put(jsonQuarterFinal6);
		            JSONObject jsonQuarterFinal7 = new JSONObject();
		            jsonQuarterFinal7.put("name", "quarterFinal7");
		            if ( useJsonDataType ) 
	                    jsonQuarterFinal7.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getQuarterFinal7()));
		            else
		                jsonQuarterFinal7.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getQuarterFinal7()));
		            array.put(jsonQuarterFinal7);
		            JSONObject jsonQuarterFinal8 = new JSONObject();
		            jsonQuarterFinal8.put("name", "quarterFinal8");
		            if ( useJsonDataType ) 
	                    jsonQuarterFinal8.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getQuarterFinal8()));
		            else
		                jsonQuarterFinal8.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getQuarterFinal8()));
		            array.put(jsonQuarterFinal8);
		            JSONObject jsonSemiFinal1 = new JSONObject();
		            jsonSemiFinal1.put("name", "semiFinal1");
		            if ( useJsonDataType ) 
	                    jsonSemiFinal1.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getSemiFinal1()));
		            else
		                jsonSemiFinal1.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getSemiFinal1()));
		            array.put(jsonSemiFinal1);
		            JSONObject jsonSemiFinal2 = new JSONObject();
		            jsonSemiFinal2.put("name", "semiFinal2");
		            if ( useJsonDataType ) 
	                    jsonSemiFinal2.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getSemiFinal2()));
		            else
		                jsonSemiFinal2.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getSemiFinal2()));
		            array.put(jsonSemiFinal2);
		            JSONObject jsonSemiFinal3 = new JSONObject();
		            jsonSemiFinal3.put("name", "semiFinal3");
		            if ( useJsonDataType ) 
	                    jsonSemiFinal3.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getSemiFinal3()));
		            else
		                jsonSemiFinal3.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getSemiFinal3()));
		            array.put(jsonSemiFinal3);
		            JSONObject jsonSemiFinal4 = new JSONObject();
		            jsonSemiFinal4.put("name", "semiFinal4");
		            if ( useJsonDataType ) 
	                    jsonSemiFinal4.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getSemiFinal4()));
		            else
		                jsonSemiFinal4.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getSemiFinal4()));
		            array.put(jsonSemiFinal4);
		            JSONObject jsonFinal1 = new JSONObject();
		            jsonFinal1.put("name", "final1");
		            if ( useJsonDataType ) 
	                    jsonFinal1.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getFinal1()));
		            else
		                jsonFinal1.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getFinal1()));
		            array.put(jsonFinal1);
		            JSONObject jsonFinal2 = new JSONObject();
		            jsonFinal2.put("name", "final2");
		            if ( useJsonDataType ) 
	                    jsonFinal2.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getFinal2()));
		            else
		                jsonFinal2.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getFinal2()));
		            array.put(jsonFinal2);
		            JSONObject jsonChampion = new JSONObject();
		            jsonChampion.put("name", "champion");
		            if ( useJsonDataType ) 
	                    jsonChampion.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getChampion()));
		            else
		                jsonChampion.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getChampion()));
		            array.put(jsonChampion);
		            JSONObject jsonFinalScoreWin = new JSONObject();
		            jsonFinalScoreWin.put("name", "finalScoreWin");
		            if ( useJsonDataType ) 
	                    jsonFinalScoreWin.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getFinalScoreWin()));
		            else
		                jsonFinalScoreWin.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getFinalScoreWin()));
		            array.put(jsonFinalScoreWin);
		            JSONObject jsonFinalScoreLose = new JSONObject();
		            jsonFinalScoreLose.put("name", "finalScoreLose");
		            if ( useJsonDataType ) 
	                    jsonFinalScoreLose.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getFinalScoreLose()));
		            else
		                jsonFinalScoreLose.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getFinalScoreLose()));
		            array.put(jsonFinalScoreLose);
		            JSONObject jsonTimeCreated = new JSONObject();
		            jsonTimeCreated.put("name", "timeCreated");
		            if ( useJsonDataType ) 
	                    jsonTimeCreated.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTimeCreated()));
		            else
		                jsonTimeCreated.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTimeCreated()));
		            array.put(jsonTimeCreated);
		            JSONObject jsonTimeUpdated = new JSONObject();
		            jsonTimeUpdated.put("name", "timeUpdated");
		            if ( useJsonDataType ) 
	                    jsonTimeUpdated.put("value", JSONValueFormatter.toJsonTypeValue(_SweepWorldcup2014.getTimeUpdated()));
		            else
		                jsonTimeUpdated.put("value", JSONValueFormatter.toStringValue(_SweepWorldcup2014.getTimeUpdated()));
		            array.put(jsonTimeUpdated);
	        json.put("fields", array);

		} else {

            if ( ignoreFieldSet || fieldSet.contains("player")) 
	            json.put("player", ""+_SweepWorldcup2014.getPlayer());
            if ( ignoreFieldSet || fieldSet.contains("game1")) 
	            json.put("game1", ""+_SweepWorldcup2014.getGame1());
            if ( ignoreFieldSet || fieldSet.contains("game1Score")) 
	            json.put("game1Score", ""+_SweepWorldcup2014.getGame1Score());
            if ( ignoreFieldSet || fieldSet.contains("game1ScoreOpp")) 
	            json.put("game1ScoreOpp", ""+_SweepWorldcup2014.getGame1ScoreOpp());
            if ( ignoreFieldSet || fieldSet.contains("game2")) 
	            json.put("game2", ""+_SweepWorldcup2014.getGame2());
            if ( ignoreFieldSet || fieldSet.contains("game2Score")) 
	            json.put("game2Score", ""+_SweepWorldcup2014.getGame2Score());
            if ( ignoreFieldSet || fieldSet.contains("game2ScoreOpp")) 
	            json.put("game2ScoreOpp", ""+_SweepWorldcup2014.getGame2ScoreOpp());
            if ( ignoreFieldSet || fieldSet.contains("game3")) 
	            json.put("game3", ""+_SweepWorldcup2014.getGame3());
            if ( ignoreFieldSet || fieldSet.contains("game3Score")) 
	            json.put("game3Score", ""+_SweepWorldcup2014.getGame3Score());
            if ( ignoreFieldSet || fieldSet.contains("game3ScoreOpp")) 
	            json.put("game3ScoreOpp", ""+_SweepWorldcup2014.getGame3ScoreOpp());
            if ( ignoreFieldSet || fieldSet.contains("advance")) 
	            json.put("advance", ""+_SweepWorldcup2014.getAdvance());
            if ( ignoreFieldSet || fieldSet.contains("team16A1")) 
	            json.put("team16A1", ""+_SweepWorldcup2014.getTeam16A1());
            if ( ignoreFieldSet || fieldSet.contains("team16A2")) 
	            json.put("team16A2", ""+_SweepWorldcup2014.getTeam16A2());
            if ( ignoreFieldSet || fieldSet.contains("team16B1")) 
	            json.put("team16B1", ""+_SweepWorldcup2014.getTeam16B1());
            if ( ignoreFieldSet || fieldSet.contains("team16B2")) 
	            json.put("team16B2", ""+_SweepWorldcup2014.getTeam16B2());
            if ( ignoreFieldSet || fieldSet.contains("team16C1")) 
	            json.put("team16C1", ""+_SweepWorldcup2014.getTeam16C1());
            if ( ignoreFieldSet || fieldSet.contains("team16C2")) 
	            json.put("team16C2", ""+_SweepWorldcup2014.getTeam16C2());
            if ( ignoreFieldSet || fieldSet.contains("team16D1")) 
	            json.put("team16D1", ""+_SweepWorldcup2014.getTeam16D1());
            if ( ignoreFieldSet || fieldSet.contains("team16D2")) 
	            json.put("team16D2", ""+_SweepWorldcup2014.getTeam16D2());
            if ( ignoreFieldSet || fieldSet.contains("team16E1")) 
	            json.put("team16E1", ""+_SweepWorldcup2014.getTeam16E1());
            if ( ignoreFieldSet || fieldSet.contains("team16E2")) 
	            json.put("team16E2", ""+_SweepWorldcup2014.getTeam16E2());
            if ( ignoreFieldSet || fieldSet.contains("team16F1")) 
	            json.put("team16F1", ""+_SweepWorldcup2014.getTeam16F1());
            if ( ignoreFieldSet || fieldSet.contains("team16F2")) 
	            json.put("team16F2", ""+_SweepWorldcup2014.getTeam16F2());
            if ( ignoreFieldSet || fieldSet.contains("team16G1")) 
	            json.put("team16G1", ""+_SweepWorldcup2014.getTeam16G1());
            if ( ignoreFieldSet || fieldSet.contains("team16G2")) 
	            json.put("team16G2", ""+_SweepWorldcup2014.getTeam16G2());
            if ( ignoreFieldSet || fieldSet.contains("team16H1")) 
	            json.put("team16H1", ""+_SweepWorldcup2014.getTeam16H1());
            if ( ignoreFieldSet || fieldSet.contains("team16H2")) 
	            json.put("team16H2", ""+_SweepWorldcup2014.getTeam16H2());
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal1")) 
	            json.put("quarterFinal1", ""+_SweepWorldcup2014.getQuarterFinal1());
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal2")) 
	            json.put("quarterFinal2", ""+_SweepWorldcup2014.getQuarterFinal2());
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal3")) 
	            json.put("quarterFinal3", ""+_SweepWorldcup2014.getQuarterFinal3());
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal4")) 
	            json.put("quarterFinal4", ""+_SweepWorldcup2014.getQuarterFinal4());
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal5")) 
	            json.put("quarterFinal5", ""+_SweepWorldcup2014.getQuarterFinal5());
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal6")) 
	            json.put("quarterFinal6", ""+_SweepWorldcup2014.getQuarterFinal6());
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal7")) 
	            json.put("quarterFinal7", ""+_SweepWorldcup2014.getQuarterFinal7());
            if ( ignoreFieldSet || fieldSet.contains("quarterFinal8")) 
	            json.put("quarterFinal8", ""+_SweepWorldcup2014.getQuarterFinal8());
            if ( ignoreFieldSet || fieldSet.contains("semiFinal1")) 
	            json.put("semiFinal1", ""+_SweepWorldcup2014.getSemiFinal1());
            if ( ignoreFieldSet || fieldSet.contains("semiFinal2")) 
	            json.put("semiFinal2", ""+_SweepWorldcup2014.getSemiFinal2());
            if ( ignoreFieldSet || fieldSet.contains("semiFinal3")) 
	            json.put("semiFinal3", ""+_SweepWorldcup2014.getSemiFinal3());
            if ( ignoreFieldSet || fieldSet.contains("semiFinal4")) 
	            json.put("semiFinal4", ""+_SweepWorldcup2014.getSemiFinal4());
            if ( ignoreFieldSet || fieldSet.contains("final1")) 
	            json.put("final1", ""+_SweepWorldcup2014.getFinal1());
            if ( ignoreFieldSet || fieldSet.contains("final2")) 
	            json.put("final2", ""+_SweepWorldcup2014.getFinal2());
            if ( ignoreFieldSet || fieldSet.contains("champion")) 
	            json.put("champion", ""+_SweepWorldcup2014.getChampion());
            if ( ignoreFieldSet || fieldSet.contains("finalScoreWin")) 
	            json.put("finalScoreWin", ""+_SweepWorldcup2014.getFinalScoreWin());
            if ( ignoreFieldSet || fieldSet.contains("finalScoreLose")) 
	            json.put("finalScoreLose", ""+_SweepWorldcup2014.getFinalScoreLose());
            if ( ignoreFieldSet || fieldSet.contains("timeCreated")) 
	            json.put("timeCreated", ""+_SweepWorldcup2014.getTimeCreated());
            if ( ignoreFieldSet || fieldSet.contains("timeUpdated")) 
	            json.put("timeUpdated", ""+_SweepWorldcup2014.getTimeUpdated());

		}
		return json;

	}

}