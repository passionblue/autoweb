package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.sql.Timestamp;
import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * SweepWorldcup2014 entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.SweepWorldcup2014
 * @author MyEclipse Persistence Tools
 */
public class SweepWorldcup2014DAO extends BaseHibernateDAO {
    private static final Logger log = LoggerFactory.getLogger(SweepWorldcup2014DAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String PLAYER = "player";
    public static final String GAME1 = "game1";
    public static final String GAME1_SCORE = "game1Score";
    public static final String GAME1_SCORE_OPP = "game1ScoreOpp";
    public static final String GAME2 = "game2";
    public static final String GAME2_SCORE = "game2Score";
    public static final String GAME2_SCORE_OPP = "game2ScoreOpp";
    public static final String GAME3 = "game3";
    public static final String GAME3_SCORE = "game3Score";
    public static final String GAME3_SCORE_OPP = "game3ScoreOpp";
    public static final String ADVANCE = "advance";
    public static final String TEAM16_A1 = "team16A1";
    public static final String TEAM16_A2 = "team16A2";
    public static final String TEAM16_B1 = "team16B1";
    public static final String TEAM16_B2 = "team16B2";
    public static final String TEAM16_C1 = "team16C1";
    public static final String TEAM16_C2 = "team16C2";
    public static final String TEAM16_D1 = "team16D1";
    public static final String TEAM16_D2 = "team16D2";
    public static final String TEAM16_E1 = "team16E1";
    public static final String TEAM16_E2 = "team16E2";
    public static final String TEAM16_F1 = "team16F1";
    public static final String TEAM16_F2 = "team16F2";
    public static final String TEAM16_G1 = "team16G1";
    public static final String TEAM16_G2 = "team16G2";
    public static final String TEAM16_H1 = "team16H1";
    public static final String TEAM16_H2 = "team16H2";
    public static final String QUARTER_FINAL1 = "quarterFinal1";
    public static final String QUARTER_FINAL2 = "quarterFinal2";
    public static final String QUARTER_FINAL3 = "quarterFinal3";
    public static final String QUARTER_FINAL4 = "quarterFinal4";
    public static final String QUARTER_FINAL5 = "quarterFinal5";
    public static final String QUARTER_FINAL6 = "quarterFinal6";
    public static final String QUARTER_FINAL7 = "quarterFinal7";
    public static final String QUARTER_FINAL8 = "quarterFinal8";
    public static final String SEMI_FINAL1 = "semiFinal1";
    public static final String SEMI_FINAL2 = "semiFinal2";
    public static final String SEMI_FINAL3 = "semiFinal3";
    public static final String SEMI_FINAL4 = "semiFinal4";
    public static final String FINAL1 = "final1";
    public static final String FINAL2 = "final2";
    public static final String CHAMPION = "champion";
    public static final String FINAL_SCORE_WIN = "finalScoreWin";
    public static final String FINAL_SCORE_LOSE = "finalScoreLose";

    public void save(SweepWorldcup2014 transientInstance) {
        log.debug("saving SweepWorldcup2014 instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SweepWorldcup2014 persistentInstance) {
        log.debug("deleting SweepWorldcup2014 instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SweepWorldcup2014 findById(Long id) {
        log.debug("getting SweepWorldcup2014 instance with id: " + id);
        try {
            SweepWorldcup2014 instance = (SweepWorldcup2014) getSession().get("com.autosite.db.SweepWorldcup2014", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SweepWorldcup2014 instance) {
        log.debug("finding SweepWorldcup2014 instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.SweepWorldcup2014").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SweepWorldcup2014 instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SweepWorldcup2014 as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findBySiteId(Object siteId) {
        return findByProperty(SITE_ID, siteId);
    }

    public List findByPlayer(Object player) {
        return findByProperty(PLAYER, player);
    }

    public List findByGame1(Object game1) {
        return findByProperty(GAME1, game1);
    }

    public List findByGame1Score(Object game1Score) {
        return findByProperty(GAME1_SCORE, game1Score);
    }

    public List findByGame1ScoreOpp(Object game1ScoreOpp) {
        return findByProperty(GAME1_SCORE_OPP, game1ScoreOpp);
    }

    public List findByGame2(Object game2) {
        return findByProperty(GAME2, game2);
    }

    public List findByGame2Score(Object game2Score) {
        return findByProperty(GAME2_SCORE, game2Score);
    }

    public List findByGame2ScoreOpp(Object game2ScoreOpp) {
        return findByProperty(GAME2_SCORE_OPP, game2ScoreOpp);
    }

    public List findByGame3(Object game3) {
        return findByProperty(GAME3, game3);
    }

    public List findByGame3Score(Object game3Score) {
        return findByProperty(GAME3_SCORE, game3Score);
    }

    public List findByGame3ScoreOpp(Object game3ScoreOpp) {
        return findByProperty(GAME3_SCORE_OPP, game3ScoreOpp);
    }

    public List findByAdvance(Object advance) {
        return findByProperty(ADVANCE, advance);
    }

    public List findByTeam16A1(Object team16A1) {
        return findByProperty(TEAM16_A1, team16A1);
    }

    public List findByTeam16A2(Object team16A2) {
        return findByProperty(TEAM16_A2, team16A2);
    }

    public List findByTeam16B1(Object team16B1) {
        return findByProperty(TEAM16_B1, team16B1);
    }

    public List findByTeam16B2(Object team16B2) {
        return findByProperty(TEAM16_B2, team16B2);
    }

    public List findByTeam16C1(Object team16C1) {
        return findByProperty(TEAM16_C1, team16C1);
    }

    public List findByTeam16C2(Object team16C2) {
        return findByProperty(TEAM16_C2, team16C2);
    }

    public List findByTeam16D1(Object team16D1) {
        return findByProperty(TEAM16_D1, team16D1);
    }

    public List findByTeam16D2(Object team16D2) {
        return findByProperty(TEAM16_D2, team16D2);
    }

    public List findByTeam16E1(Object team16E1) {
        return findByProperty(TEAM16_E1, team16E1);
    }

    public List findByTeam16E2(Object team16E2) {
        return findByProperty(TEAM16_E2, team16E2);
    }

    public List findByTeam16F1(Object team16F1) {
        return findByProperty(TEAM16_F1, team16F1);
    }

    public List findByTeam16F2(Object team16F2) {
        return findByProperty(TEAM16_F2, team16F2);
    }

    public List findByTeam16G1(Object team16G1) {
        return findByProperty(TEAM16_G1, team16G1);
    }

    public List findByTeam16G2(Object team16G2) {
        return findByProperty(TEAM16_G2, team16G2);
    }

    public List findByTeam16H1(Object team16H1) {
        return findByProperty(TEAM16_H1, team16H1);
    }

    public List findByTeam16H2(Object team16H2) {
        return findByProperty(TEAM16_H2, team16H2);
    }

    public List findByQuarterFinal1(Object quarterFinal1) {
        return findByProperty(QUARTER_FINAL1, quarterFinal1);
    }

    public List findByQuarterFinal2(Object quarterFinal2) {
        return findByProperty(QUARTER_FINAL2, quarterFinal2);
    }

    public List findByQuarterFinal3(Object quarterFinal3) {
        return findByProperty(QUARTER_FINAL3, quarterFinal3);
    }

    public List findByQuarterFinal4(Object quarterFinal4) {
        return findByProperty(QUARTER_FINAL4, quarterFinal4);
    }

    public List findByQuarterFinal5(Object quarterFinal5) {
        return findByProperty(QUARTER_FINAL5, quarterFinal5);
    }

    public List findByQuarterFinal6(Object quarterFinal6) {
        return findByProperty(QUARTER_FINAL6, quarterFinal6);
    }

    public List findByQuarterFinal7(Object quarterFinal7) {
        return findByProperty(QUARTER_FINAL7, quarterFinal7);
    }

    public List findByQuarterFinal8(Object quarterFinal8) {
        return findByProperty(QUARTER_FINAL8, quarterFinal8);
    }

    public List findBySemiFinal1(Object semiFinal1) {
        return findByProperty(SEMI_FINAL1, semiFinal1);
    }

    public List findBySemiFinal2(Object semiFinal2) {
        return findByProperty(SEMI_FINAL2, semiFinal2);
    }

    public List findBySemiFinal3(Object semiFinal3) {
        return findByProperty(SEMI_FINAL3, semiFinal3);
    }

    public List findBySemiFinal4(Object semiFinal4) {
        return findByProperty(SEMI_FINAL4, semiFinal4);
    }

    public List findByFinal1(Object final1) {
        return findByProperty(FINAL1, final1);
    }

    public List findByFinal2(Object final2) {
        return findByProperty(FINAL2, final2);
    }

    public List findByChampion(Object champion) {
        return findByProperty(CHAMPION, champion);
    }

    public List findByFinalScoreWin(Object finalScoreWin) {
        return findByProperty(FINAL_SCORE_WIN, finalScoreWin);
    }

    public List findByFinalScoreLose(Object finalScoreLose) {
        return findByProperty(FINAL_SCORE_LOSE, finalScoreLose);
    }

    public List findAll() {
        log.debug("finding all SweepWorldcup2014 instances");
        try {
            String queryString = "from SweepWorldcup2014";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SweepWorldcup2014 merge(SweepWorldcup2014 detachedInstance) {
        log.debug("merging SweepWorldcup2014 instance");
        try {
            SweepWorldcup2014 result = (SweepWorldcup2014) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SweepWorldcup2014 instance) {
        log.debug("attaching dirty SweepWorldcup2014 instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SweepWorldcup2014 instance) {
        log.debug("attaching clean SweepWorldcup2014 instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}
