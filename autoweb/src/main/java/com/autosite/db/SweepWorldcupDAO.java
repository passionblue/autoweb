package com.autosite.db;

import com.surveygen.db.BaseHibernateDAO;
import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * SweepWorldcup entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.autosite.db.SweepWorldcup
 * @author MyEclipse Persistence Tools
 */

public class SweepWorldcupDAO extends BaseHibernateDAO {
    private static final Log log = LogFactory.getLog(SweepWorldcupDAO.class);
    // property constants
    public static final String SITE_ID = "siteId";
    public static final String USER_ID = "userId";
    public static final String TEAM_CODE = "teamCode";
    public static final String TEAM_NAME = "teamName";
    public static final String GROUP_NUM = "groupNum";
    public static final String GAME1 = "game1";
    public static final String GAME1_SCORE = "game1Score";
    public static final String GAME1_SCORE_OPP = "game1ScoreOpp";
    public static final String GAME2 = "game2";
    public static final String GAME2_SCORE = "game2Score";
    public static final String GAME2_SCORE_OPP = "game2ScoreOpp";
    public static final String GAME3 = "game3";
    public static final String GAME3_SCORE = "game3Score";
    public static final String GAME3_SCORE_OPP = "game3ScoreOpp";
    public static final String QUARTER_FINAL_TEAMS = "quarterFinalTeams";
    public static final String SEMI_FINAL_TEAMS = "semiFinalTeams";
    public static final String FINAL_TEAMS = "finalTeams";
    public static final String CHAMPION = "champion";
    public static final String FINAL_SCORE_WIN = "finalScoreWin";
    public static final String FINAL_SCORE_LOSE = "finalScoreLose";

    public void save(SweepWorldcup transientInstance) {
        log.debug("saving SweepWorldcup instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        }
        catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(SweepWorldcup persistentInstance) {
        log.debug("deleting SweepWorldcup instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public SweepWorldcup findById(Long id) {
        log.debug("getting SweepWorldcup instance with id: " + id);
        try {
            SweepWorldcup instance = (SweepWorldcup) getSession().get("com.autosite.db.SweepWorldcup", id);
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(SweepWorldcup instance) {
        log.debug("finding SweepWorldcup instance by example");
        try {
            List results = getSession().createCriteria("com.autosite.db.SweepWorldcup").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding SweepWorldcup instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from SweepWorldcup as model where model." + propertyName + "= ?";
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

    public List findByUserId(Object userId) {
        return findByProperty(USER_ID, userId);
    }

    public List findByTeamCode(Object teamCode) {
        return findByProperty(TEAM_CODE, teamCode);
    }

    public List findByTeamName(Object teamName) {
        return findByProperty(TEAM_NAME, teamName);
    }

    public List findByGroupNum(Object groupNum) {
        return findByProperty(GROUP_NUM, groupNum);
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

    public List findByQuarterFinalTeams(Object quarterFinalTeams) {
        return findByProperty(QUARTER_FINAL_TEAMS, quarterFinalTeams);
    }

    public List findBySemiFinalTeams(Object semiFinalTeams) {
        return findByProperty(SEMI_FINAL_TEAMS, semiFinalTeams);
    }

    public List findByFinalTeams(Object finalTeams) {
        return findByProperty(FINAL_TEAMS, finalTeams);
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
        log.debug("finding all SweepWorldcup instances");
        try {
            String queryString = "from SweepWorldcup";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        }
        catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public SweepWorldcup merge(SweepWorldcup detachedInstance) {
        log.debug("merging SweepWorldcup instance");
        try {
            SweepWorldcup result = (SweepWorldcup) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SweepWorldcup instance) {
        log.debug("attaching dirty SweepWorldcup instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(SweepWorldcup instance) {
        log.debug("attaching clean SweepWorldcup instance");
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
