package com.autosite.db;

import java.sql.Timestamp;

/**
 * SweepWorldcup2014 entity. @author MyEclipse Persistence Tools
 */

public class SweepWorldcup2014 extends com.autosite.db.BaseAutositeDataObject implements java.io.Serializable {

    // Fields

    private long id;
    private long siteId;
    private String player;
    private int game1;
    private int game1Score;
    private int game1ScoreOpp;
    private int game2;
    private int game2Score;
    private int game2ScoreOpp;
    private int game3;
    private int game3Score;
    private int game3ScoreOpp;
    private int advance;
    private String team16A1;
    private String team16A2;
    private String team16B1;
    private String team16B2;
    private String team16C1;
    private String team16C2;
    private String team16D1;
    private String team16D2;
    private String team16E1;
    private String team16E2;
    private String team16F1;
    private String team16F2;
    private String team16G1;
    private String team16G2;
    private String team16H1;
    private String team16H2;
    private String quarterFinal1;
    private String quarterFinal2;
    private String quarterFinal3;
    private String quarterFinal4;
    private String quarterFinal5;
    private String quarterFinal6;
    private String quarterFinal7;
    private String quarterFinal8;
    private String semiFinal1;
    private String semiFinal2;
    private String semiFinal3;
    private String semiFinal4;
    private String final1;
    private String final2;
    private String champion;
    private int finalScoreWin;
    private int finalScoreLose;
    private Timestamp timeCreated;
    private Timestamp timeUpdated;

    // Constructors

    /** default constructor */
    public SweepWorldcup2014() {
    }

    /** minimal constructor */
    public SweepWorldcup2014(long siteId, String player) {
        this.siteId = siteId;
        this.player = player;
    }

    /** full constructor */
    public SweepWorldcup2014(long siteId, String player, int game1, int game1Score, int game1ScoreOpp, int game2, int game2Score, int game2ScoreOpp, int game3, int game3Score, int game3ScoreOpp,
            int advance, String team16A1, String team16A2, String team16B1, String team16B2, String team16C1, String team16C2, String team16D1, String team16D2, String team16E1, String team16E2,
            String team16F1, String team16F2, String team16G1, String team16G2, String team16H1, String team16H2, String quarterFinal1, String quarterFinal2, String quarterFinal3,
            String quarterFinal4, String quarterFinal5, String quarterFinal6, String quarterFinal7, String quarterFinal8, String semiFinal1, String semiFinal2, String semiFinal3, String semiFinal4,
            String final1, String final2, String champion, int finalScoreWin, int finalScoreLose, Timestamp timeCreated, Timestamp timeUpdated) {
        this.siteId = siteId;
        this.player = player;
        this.game1 = game1;
        this.game1Score = game1Score;
        this.game1ScoreOpp = game1ScoreOpp;
        this.game2 = game2;
        this.game2Score = game2Score;
        this.game2ScoreOpp = game2ScoreOpp;
        this.game3 = game3;
        this.game3Score = game3Score;
        this.game3ScoreOpp = game3ScoreOpp;
        this.advance = advance;
        this.team16A1 = team16A1;
        this.team16A2 = team16A2;
        this.team16B1 = team16B1;
        this.team16B2 = team16B2;
        this.team16C1 = team16C1;
        this.team16C2 = team16C2;
        this.team16D1 = team16D1;
        this.team16D2 = team16D2;
        this.team16E1 = team16E1;
        this.team16E2 = team16E2;
        this.team16F1 = team16F1;
        this.team16F2 = team16F2;
        this.team16G1 = team16G1;
        this.team16G2 = team16G2;
        this.team16H1 = team16H1;
        this.team16H2 = team16H2;
        this.quarterFinal1 = quarterFinal1;
        this.quarterFinal2 = quarterFinal2;
        this.quarterFinal3 = quarterFinal3;
        this.quarterFinal4 = quarterFinal4;
        this.quarterFinal5 = quarterFinal5;
        this.quarterFinal6 = quarterFinal6;
        this.quarterFinal7 = quarterFinal7;
        this.quarterFinal8 = quarterFinal8;
        this.semiFinal1 = semiFinal1;
        this.semiFinal2 = semiFinal2;
        this.semiFinal3 = semiFinal3;
        this.semiFinal4 = semiFinal4;
        this.final1 = final1;
        this.final2 = final2;
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

    public String getPlayer() {
        return this.player;
    }

    public void setPlayer(String player) {
        this.player = player;
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

    public int getAdvance() {
        return this.advance;
    }

    public void setAdvance(int advance) {
        this.advance = advance;
    }

    public String getTeam16A1() {
        return this.team16A1;
    }

    public void setTeam16A1(String team16A1) {
        this.team16A1 = team16A1;
    }

    public String getTeam16A2() {
        return this.team16A2;
    }

    public void setTeam16A2(String team16A2) {
        this.team16A2 = team16A2;
    }

    public String getTeam16B1() {
        return this.team16B1;
    }

    public void setTeam16B1(String team16B1) {
        this.team16B1 = team16B1;
    }

    public String getTeam16B2() {
        return this.team16B2;
    }

    public void setTeam16B2(String team16B2) {
        this.team16B2 = team16B2;
    }

    public String getTeam16C1() {
        return this.team16C1;
    }

    public void setTeam16C1(String team16C1) {
        this.team16C1 = team16C1;
    }

    public String getTeam16C2() {
        return this.team16C2;
    }

    public void setTeam16C2(String team16C2) {
        this.team16C2 = team16C2;
    }

    public String getTeam16D1() {
        return this.team16D1;
    }

    public void setTeam16D1(String team16D1) {
        this.team16D1 = team16D1;
    }

    public String getTeam16D2() {
        return this.team16D2;
    }

    public void setTeam16D2(String team16D2) {
        this.team16D2 = team16D2;
    }

    public String getTeam16E1() {
        return this.team16E1;
    }

    public void setTeam16E1(String team16E1) {
        this.team16E1 = team16E1;
    }

    public String getTeam16E2() {
        return this.team16E2;
    }

    public void setTeam16E2(String team16E2) {
        this.team16E2 = team16E2;
    }

    public String getTeam16F1() {
        return this.team16F1;
    }

    public void setTeam16F1(String team16F1) {
        this.team16F1 = team16F1;
    }

    public String getTeam16F2() {
        return this.team16F2;
    }

    public void setTeam16F2(String team16F2) {
        this.team16F2 = team16F2;
    }

    public String getTeam16G1() {
        return this.team16G1;
    }

    public void setTeam16G1(String team16G1) {
        this.team16G1 = team16G1;
    }

    public String getTeam16G2() {
        return this.team16G2;
    }

    public void setTeam16G2(String team16G2) {
        this.team16G2 = team16G2;
    }

    public String getTeam16H1() {
        return this.team16H1;
    }

    public void setTeam16H1(String team16H1) {
        this.team16H1 = team16H1;
    }

    public String getTeam16H2() {
        return this.team16H2;
    }

    public void setTeam16H2(String team16H2) {
        this.team16H2 = team16H2;
    }

    public String getQuarterFinal1() {
        return this.quarterFinal1;
    }

    public void setQuarterFinal1(String quarterFinal1) {
        this.quarterFinal1 = quarterFinal1;
    }

    public String getQuarterFinal2() {
        return this.quarterFinal2;
    }

    public void setQuarterFinal2(String quarterFinal2) {
        this.quarterFinal2 = quarterFinal2;
    }

    public String getQuarterFinal3() {
        return this.quarterFinal3;
    }

    public void setQuarterFinal3(String quarterFinal3) {
        this.quarterFinal3 = quarterFinal3;
    }

    public String getQuarterFinal4() {
        return this.quarterFinal4;
    }

    public void setQuarterFinal4(String quarterFinal4) {
        this.quarterFinal4 = quarterFinal4;
    }

    public String getQuarterFinal5() {
        return this.quarterFinal5;
    }

    public void setQuarterFinal5(String quarterFinal5) {
        this.quarterFinal5 = quarterFinal5;
    }

    public String getQuarterFinal6() {
        return this.quarterFinal6;
    }

    public void setQuarterFinal6(String quarterFinal6) {
        this.quarterFinal6 = quarterFinal6;
    }

    public String getQuarterFinal7() {
        return this.quarterFinal7;
    }

    public void setQuarterFinal7(String quarterFinal7) {
        this.quarterFinal7 = quarterFinal7;
    }

    public String getQuarterFinal8() {
        return this.quarterFinal8;
    }

    public void setQuarterFinal8(String quarterFinal8) {
        this.quarterFinal8 = quarterFinal8;
    }

    public String getSemiFinal1() {
        return this.semiFinal1;
    }

    public void setSemiFinal1(String semiFinal1) {
        this.semiFinal1 = semiFinal1;
    }

    public String getSemiFinal2() {
        return this.semiFinal2;
    }

    public void setSemiFinal2(String semiFinal2) {
        this.semiFinal2 = semiFinal2;
    }

    public String getSemiFinal3() {
        return this.semiFinal3;
    }

    public void setSemiFinal3(String semiFinal3) {
        this.semiFinal3 = semiFinal3;
    }

    public String getSemiFinal4() {
        return this.semiFinal4;
    }

    public void setSemiFinal4(String semiFinal4) {
        this.semiFinal4 = semiFinal4;
    }

    public String getFinal1() {
        return this.final1;
    }

    public void setFinal1(String final1) {
        this.final1 = final1;
    }

    public String getFinal2() {
        return this.final2;
    }

    public void setFinal2(String final2) {
        this.final2 = final2;
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
