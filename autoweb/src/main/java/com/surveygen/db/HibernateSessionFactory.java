package com.surveygen.db;
 
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.seox.util.PropertiesLoader;

/**
 * Configures and provides access to Hibernate sessions, tied to the current
 * thread of execution. Follows the Thread Local Session pattern, see
 * {@link http://hibernate.org/42.html }.
 */
public class HibernateSessionFactory {

    /**
     * Location of hibernate.cfg.xml file. Location should be on the classpath
     * as Hibernate uses #resourceAsStream style lookup for its configuration
     * file. The default classpath location of the hibernate config file is in
     * the default package. Use #setConfigFile() to update the location of the
     * configuration file for the current session.
     */
    private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
    private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
    private static Configuration configuration = new Configuration();
    private static org.hibernate.SessionFactory sessionFactory;
//    private static org.springframework.orm.hibernate3.support.HibernateDaoSupport sf;
    
    private static String configFile = PropertiesLoader.getInstance().getProperty("app.hibernate.cfg.file", CONFIG_FILE_LOCATION);

    static {
        
        if ( true ) {
            try {
                configuration.configure(configFile);
                sessionFactory = configuration.buildSessionFactory();
                System.err.println("%%%% Created SessionFactory %%%% " + configFile);
                
    //            configuration.configure();
    //            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
    //            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                
            }
            catch (Exception e) {
                System.err.println("%%%% Error Creating SessionFactory %%%%");
                e.printStackTrace();
            }
        } else {
            try{
                ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext-hibernate.xml");
    //            sf = (org.springframework.orm.hibernate3.support.HibernateDaoSupport)appContext.getBean("sessionFactory");
    //            sessionFactory = sf.getSessionFactory();
                sessionFactory = (org.hibernate.SessionFactory)appContext.getBean("sessionFactory");
            } catch (Exception e){
                System.err.println("%%%% Error Creating SessionFactory %%%%");
                e.printStackTrace();
            }
        }
        
    }

    private HibernateSessionFactory() {
    }

    /**
     * Returns the ThreadLocal Session instance. Lazy initialize the
     * <code>SessionFactory</code> if needed.
     * 
     * @return Session
     * @throws HibernateException
     */
    public static Session getSession() throws HibernateException {
        Session session = (Session) threadLocal.get();

        if (session == null || !session.isOpen()) {
            if (sessionFactory == null) {
                rebuildSessionFactory();
            }
            session = (sessionFactory != null) ? sessionFactory.openSession() : null;
            threadLocal.set(session);
        }

        return session;
    }

    /**
     * Rebuild hibernate session factory
     * 
     */
    public static void rebuildSessionFactory() {
        try {
            configuration.configure(configFile);
            sessionFactory = configuration.buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("%%%% Error Creating SessionFactory %%%%");
            e.printStackTrace();
        }
    }

    /**
     * Close the single hibernate session instance.
     * 
     * @throws HibernateException
     */
    public static void closeSession() throws HibernateException {
        Session session = (Session) threadLocal.get();
        threadLocal.set(null);

        if (session != null) {
            session.close();
        }
    }

    /**
     * return session factory
     * 
     */
    public static org.hibernate.SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * return session factory
     * 
     * session factory will be rebuilded in the next call
     */
    public static void setConfigFile(String configFile) {
        HibernateSessionFactory.configFile = configFile;
        sessionFactory = null;
    }

    /**
     * return hibernate configuration
     * 
     */
    public static Configuration getConfiguration() {
        return configuration;
    }

}
