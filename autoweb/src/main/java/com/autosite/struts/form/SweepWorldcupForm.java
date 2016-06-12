package com.autosite.struts.form;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class SweepWorldcupForm extends ActionForm {

	private String _UserId;             
	private String _TeamCode;             
	private String _TeamName;             
	private String _GroupNum;             
	private String _Game1;             
	private String _Game1Score;             
	private String _Game1ScoreOpp;             
	private String _Game2;             
	private String _Game2Score;             
	private String _Game2ScoreOpp;             
	private String _Game3;             
	private String _Game3Score;             
	private String _Game3ScoreOpp;             
	private String _QuarterFinalTeams;             
	private String _SemiFinalTeams;             
	private String _FinalTeams;             
	private String _Champion;             
	private String _FinalScoreWin;             
	private String _FinalScoreLose;             
	private String _TimeCreated;             
	private String _TimeUpdated;             

    public String getUserId() {
        return _UserId;
    }

    public void setUserId(String UserId) {
        this._UserId = UserId;
    }    

    public String getTeamCode() {
        return _TeamCode;
    }

    public void setTeamCode(String TeamCode) {
        this._TeamCode = TeamCode;
    }    

    public String getTeamName() {
        return _TeamName;
    }

    public void setTeamName(String TeamName) {
        this._TeamName = TeamName;
    }    

    public String getGroupNum() {
        return _GroupNum;
    }

    public void setGroupNum(String GroupNum) {
        this._GroupNum = GroupNum;
    }    

    public String getGame1() {
        return _Game1;
    }

    public void setGame1(String Game1) {
        this._Game1 = Game1;
    }    

    public String getGame1Score() {
        return _Game1Score;
    }

    public void setGame1Score(String Game1Score) {
        this._Game1Score = Game1Score;
    }    

    public String getGame1ScoreOpp() {
        return _Game1ScoreOpp;
    }

    public void setGame1ScoreOpp(String Game1ScoreOpp) {
        this._Game1ScoreOpp = Game1ScoreOpp;
    }    

    public String getGame2() {
        return _Game2;
    }

    public void setGame2(String Game2) {
        this._Game2 = Game2;
    }    

    public String getGame2Score() {
        return _Game2Score;
    }

    public void setGame2Score(String Game2Score) {
        this._Game2Score = Game2Score;
    }    

    public String getGame2ScoreOpp() {
        return _Game2ScoreOpp;
    }

    public void setGame2ScoreOpp(String Game2ScoreOpp) {
        this._Game2ScoreOpp = Game2ScoreOpp;
    }    

    public String getGame3() {
        return _Game3;
    }

    public void setGame3(String Game3) {
        this._Game3 = Game3;
    }    

    public String getGame3Score() {
        return _Game3Score;
    }

    public void setGame3Score(String Game3Score) {
        this._Game3Score = Game3Score;
    }    

    public String getGame3ScoreOpp() {
        return _Game3ScoreOpp;
    }

    public void setGame3ScoreOpp(String Game3ScoreOpp) {
        this._Game3ScoreOpp = Game3ScoreOpp;
    }    

    public String getQuarterFinalTeams() {
        return _QuarterFinalTeams;
    }

    public void setQuarterFinalTeams(String QuarterFinalTeams) {
        this._QuarterFinalTeams = QuarterFinalTeams;
    }    

    public String getSemiFinalTeams() {
        return _SemiFinalTeams;
    }

    public void setSemiFinalTeams(String SemiFinalTeams) {
        this._SemiFinalTeams = SemiFinalTeams;
    }    

    public String getFinalTeams() {
        return _FinalTeams;
    }

    public void setFinalTeams(String FinalTeams) {
        this._FinalTeams = FinalTeams;
    }    

    public String getChampion() {
        return _Champion;
    }

    public void setChampion(String Champion) {
        this._Champion = Champion;
    }    

    public String getFinalScoreWin() {
        return _FinalScoreWin;
    }

    public void setFinalScoreWin(String FinalScoreWin) {
        this._FinalScoreWin = FinalScoreWin;
    }    

    public String getFinalScoreLose() {
        return _FinalScoreLose;
    }

    public void setFinalScoreLose(String FinalScoreLose) {
        this._FinalScoreLose = FinalScoreLose;
    }    

    public String getTimeCreated() {
        return _TimeCreated;
    }

    public void setTimeCreated(String TimeCreated) {
        this._TimeCreated = TimeCreated;
    }    

    public String getTimeUpdated() {
        return _TimeUpdated;
    }

    public void setTimeUpdated(String TimeUpdated) {
        this._TimeUpdated = TimeUpdated;
    }    

}