package com.autosite.db;

import java.sql.Timestamp;

/**
 * SweepWorldcup entity. @author MyEclipse Persistence Tools
 */

public class SweepWorldcup extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private long userId;
    private String teamCode;
    private String teamName;
    private String groupNum;
    private int game1;
    private int game1Score;
    private int game1ScoreOpp;
    private int game2;
    private int game2Score;
    private int game2ScoreOpp;
    private int game3;
    private int game3Score;
    private int game3ScoreOpp;
    private String quarterFinalTeams;
    private String semiFinalTeams;
    private String finalTeams;
    private String champion;
    private int finalScoreWin;
    private int finalScoreLose;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public SweepWorldcup() {
    }

    /** minimal constructor */
    public SweepWorldcup(long siteId, long userId, String teamCode, String teamName, String groupNum, String quarterFinalTeams, String semiFinalTeams, String finalTeams, String champion,
            Timestamp timeCreated) {
        this.siteId = siteId;
        this.userId = userId;
        this.teamCode = teamCode;
        this.teamName = teamName;
        this.groupNum = groupNum;
        this.quarterFinalTeams = quarterFinalTeams;
        this.semiFinalTeams = semiFinalTeams;
        this.finalTeams = finalTeams;
        this.champion = champion;
        this.timeCreated = timeCreated;
    }

    /** full constructor */
    public SweepWorldcup(long siteId, long userId, String teamCode, String teamName, String groupNum, int game1, int game1Score, int game1ScoreOpp, int game2, int game2Score, int game2ScoreOpp,
            int game3, int game3Score, int game3ScoreOpp, String quarterFinalTeams, String semiFinalTeams, String finalTeams, String champion, int finalScoreWin, int finalScoreLose,
            Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.userId = userId;
        this.teamCode = teamCode;
        this.teamName = teamName;
        this.groupNum = groupNum;
        this.game1 = game1;
        this.game1Score = game1Score;
        this.game1ScoreOpp = game1ScoreOpp;
        this.game2 = game2;
        this.game2Score = game2Score;
        this.game2ScoreOpp = game2ScoreOpp;
        this.game3 = game3;
        this.game3Score = game3Score;
        this.game3ScoreOpp = game3ScoreOpp;
        this.quarterFinalTeams = quarterFinalTeams;
        this.semiFinalTeams = semiFinalTeams;
        this.finalTeams = finalTeams;
        this.champion = champion;
        this.finalScoreWin = finalScoreWin;
        this.finalScoreLose = finalScoreLose;
        this.timeCreated = timeCreated;
        this.timeUpdated = timeUpdated;
    }

    // Property accessors

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSiteId() {
        return this.siteId;
    }

    public void setSiteId(long siteId) {
        this.siteId = siteId;
    }

    public long getUserId() {
        return this.userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTeamCode() {
        return this.teamCode;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getGroupNum() {
        return this.groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public int getGame1() {
        return this.game1;
    }

    public void setGame1(int game1) {
        this.game1 = game1;
    }

    public int getGame1Score() {
        return this.game1Score;
    }

    public void setGame1Score(int game1Score) {
        this.game1Score = game1Score;
    }

    public int getGame1ScoreOpp() {
        return this.game1ScoreOpp;
    }

    public void setGame1ScoreOpp(int game1ScoreOpp) {
        this.game1ScoreOpp = game1ScoreOpp;
    }

    public int getGame2() {
        return this.game2;
    }

    public void setGame2(int game2) {
        this.game2 = game2;
    }

    public int getGame2Score() {
        return this.game2Score;
    }

    public void setGame2Score(int game2Score) {
        this.game2Score = game2Score;
    }

    public int getGame2ScoreOpp() {
        return this.game2ScoreOpp;
    }

    public void setGame2ScoreOpp(int game2ScoreOpp) {
        this.game2ScoreOpp = game2ScoreOpp;
    }

    public int getGame3() {
        return this.game3;
    }

    public void setGame3(int game3) {
        this.game3 = game3;
    }

    public int getGame3Score() {
        return this.game3Score;
    }

    public void setGame3Score(int game3Score) {
        this.game3Score = game3Score;
    }

    public int getGame3ScoreOpp() {
        return this.game3ScoreOpp;
    }

    public void setGame3ScoreOpp(int game3ScoreOpp) {
        this.game3ScoreOpp = game3ScoreOpp;
    }

    public String getQuarterFinalTeams() {
        return this.quarterFinalTeams;
    }

    public void setQuarterFinalTeams(String quarterFinalTeams) {
        this.quarterFinalTeams = quarterFinalTeams;
    }

    public String getSemiFinalTeams() {
        return this.semiFinalTeams;
    }

    public void setSemiFinalTeams(String semiFinalTeams) {
        this.semiFinalTeams = semiFinalTeams;
    }

    public String getFinalTeams() {
        return this.finalTeams;
    }

    public void setFinalTeams(String finalTeams) {
        this.finalTeams = finalTeams;
    }

    public String getChampion() {
        return this.champion;
    }

    public void setChampion(String champion) {
        this.champion = champion;
    }

    public int getFinalScoreWin() {
        return this.finalScoreWin;
    }

    public void setFinalScoreWin(int finalScoreWin) {
        this.finalScoreWin = finalScoreWin;
    }

    public int getFinalScoreLose() {
        return this.finalScoreLose;
    }

    public void setFinalScoreLose(int finalScoreLose) {
        this.finalScoreLose = finalScoreLose;
    }

    public Timestamp getTimeCreated() {
        return this.timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Timestamp getTimeUpdated() {
        return this.timeUpdated;
    }

    public void setTimeUpdated(Timestamp timeUpdated) {
        this.timeUpdated = timeUpdated;
    }

}
