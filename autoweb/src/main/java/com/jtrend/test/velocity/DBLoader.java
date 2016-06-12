package com.jtrend.test.velocity;


import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DBLoader {
    
    public DBLoader() throws Exception{
        _InitParams("c:\\work\\seox\\velocity\\dbloader.properties");
    }
    
    protected final static void _InitParams(String configFile) throws Exception {

        Properties props = new Properties();
        props.load(new FileInputStream(new File(configFile)));

        
        props.putAll(System.getProperties());

        driver = props.getProperty("driver");
        url = props.getProperty("url");
        userName = props.getProperty("userName");
        passwd = props.getProperty("passwd");
        table = props.getProperty("table");
        
        rootDir = props.getProperty("rootDir");
        GetStatement = props.getProperty("GetStatement");
        header = props.getProperty("header");
        outLocation = props.getProperty("OutLocation");
        fileName = props.getProperty("FileName");
        extension = props.getProperty("extension");
        roleDesKey = "UserRoleDescription";
        appId = props.getProperty("AppId");
        descMap = createRoleDescMap(props);
        eol = props.getProperty("line.separator"); 
        
        
        if (rootDir == null) rootDir = "./";
        
        _Logger.debug("Properties=" + props);
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        

        try {
            _InitParams("c:\\work\\seox\\velocity\\dbloader.properties");
            DBLoader EERSLoader = new DBLoader();
            EERSLoader.getTableInfo();
        }
        catch (Exception e) {
            _Logger.error(e);
            _Logger.debug(e);
        }
    }

    public List getTableInfo() throws Exception {
        return getTableInfo(driver, url, userName, passwd, table);
    }

    public List getTableInfo(String tbl) throws Exception {
        return getTableInfo(driver, url, userName, passwd, tbl);
    }
    
    
    public List getTableInfo(String driver, String url, String username, String password, String table) throws Exception {

        Class.forName(driver);
        Connection dbConnection = DriverManager.getConnection(url, username, password);

        stmt = dbConnection.createStatement();
        rs = stmt.executeQuery("select * from " + table + " where 1=0");
        ResultSetMetaData mdata = rs.getMetaData();

        _Logger.debug("Query done.");

        int columnCount = mdata.getColumnCount();

        System.out.println(mdata);
        String line = "";
        
        
        List list = new ArrayList();
        
        for (int i = 1; i <= columnCount; i++) {
            
            ColumnData columnData = new ColumnData();
            
            String name = mdata.getColumnName(i);
            String catalong = mdata.getCatalogName(i);
            String className = mdata.getColumnClassName(i);
            String typeName = mdata.getColumnTypeName(i);
            int type = mdata.getColumnType(i);

            columnData.name = name;
            columnData.className = className;
            columnData.typeName = typeName;
            
            System.out.println(name);
            System.out.println(catalong);
            System.out.println(className);
            System.out.println(typeName);
            System.out.println(type);

            list.add(columnData);
        }


        return list;
        
        
/*
        long time = System.currentTimeMillis();

        String reportFile = rootDir + File.separator + outLocation + File.separator + fileName + extension;

        File report = new File(reportFile);

        if (report.exists()) {
            SimpleDateFormat dateFormat = (SimpleDateFormat) SimpleDateFormat.getInstance();
            dateFormat.applyPattern("yyyy.MM.dd-HHmmss");
            report.renameTo(new File(rootDir + File.separator + outLocation + File.separator + fileName + "-" + dateFormat.format(new Date()) + extension));
        }

        _Logger.debug("Report file=" + reportFile);
        
        BufferedWriter out = new BufferedWriter(new FileWriter(reportFile));

        int numEntries = 0;
        
        Map externalEntries = new HashMap();
        Map internalEntries = new HashMap();
        
        
        if (rs != null) {
            while (rs.next()) {

                String entryLine = null;

                String id = rs.getString(1);
                String type1 = rs.getString(2);
                String type2 = rs.getString(3);
                String soe = rs.getString(4);
                String status = rs.getString(5);
                
                if (status != null && status.trim().equals("0")) {
                    _Logger.info("This record dropped because some user id=" + id + ", soe="+ soe + " is deactivated");
                    continue;
                    
                }
                
                if ( isNull(type1)|| isNull(type2) || isNull(id)|| isNull(soe)) { 
                    _Logger.info("This record dropped because some data is null. id=" + id + ", def1=" + type1 + ", def2=" + type2 + ", soe="+ soe + ".");
                    continue;
                }
                
                if ( soe.trim().length() != 7 ) {
                    _Logger.info("This record dropped. reason=SOE is not 7 character long. soe=" + soe);
                    continue;
                }

                Entry entry = new Entry();
                
                entry._id = id;
                entry._userDef1 = type1;
                entry._userDef2 = type2;
                entry._soe = soe;

                
                if (type2.trim().equalsIgnoreCase("INTERNAL")|| true) {

                    internalEntries.put(soe, entry);
                    
                    try {
                        entryLine = formatLine(id, type1, type2, soe);
                    }
                    catch (RuntimeException e) {
                        entryLine = null;
                        _Logger.error(e);
                    }
                    
                    if (entryLine != null) {
                        _Logger.info("id=" + id + ", def1=" + type1 + ", def2=" + type2 + ", display="+ soe + ".");
                        out.write(entryLine);
                        out.write(eol);
                        _Logger.info("EERS Report=" + entryLine);
                        numEntries++;
                    }
                    
                } else {
                    externalEntries.put(soe, entry);
                }
            }
            
        }

        
        Set processedIds = new HashSet();
        
        for (Iterator iterator = externalEntries.keySet().iterator(); iterator.hasNext();) {
            String soeId = (String) iterator.next();
            
            String entryLine = null;
            
            if (!internalEntries.containsKey(soeId) && !processedIds.contains(soeId)) {

                Entry entry = (Entry) externalEntries.get(soeId);
                
                try {
                    entryLine = formatLine(entry._id, entry._userDef1, entry._userDef2, entry._soe);
                    processedIds.add(soeId);
                }
                catch (RuntimeException e) {
                    entryLine = null;
                    _Logger.error(e);
                }
                
                if (entryLine != null) {
                    _Logger.info("id=" + soeId + ", def1=" + entry._userDef1 + ", def2=" + entry._userDef2 + ", display="+ entry._soe);
                    out.write(entryLine);
                    out.write(eol);
                    _Logger.info("EERS Report=" + entryLine);
                    numEntries++;
                }
            } else {
                _Logger.debug("soe " + soeId + " already exist or process");
            }
        }
        
        
        System.out.println(internalEntries);
        
        out.close();
        _Logger.info("Report generated. Num of entries=" + numEntries);
        
        */
    }

    private boolean isNull(String val) {
        
        if (val == null || val.trim().equals(""))
            return true;
        return false;
    }

    private String formatLine(String id, String type1, String type2, String soeID) {
        
        
        String type=type1+ "." + type2;
        String key = type.toLowerCase().replaceAll(" ",  "");

        String descriptionConfig = (String) descMap.get(key);
        
        String functionDesc;
        String functionCode;
        
        if (descriptionConfig == null) { 
            functionDesc = "Unknow User Type (" + type + ")";
            functionCode = "UNKNOWN";
        } else {
            int delimiter = descriptionConfig.indexOf(":");
            
            if ( delimiter < 0 ) {
                functionCode = type1.toUpperCase();
                functionDesc = descriptionConfig;
            } else {
                functionCode = descriptionConfig.substring(0, delimiter);
                functionDesc = descriptionConfig.substring(delimiter+1);
            }
        }
        
        return appId + "\t\t\t" + id + "\t\t\t" + functionCode.toUpperCase() + "\t" + functionDesc + "\t" + soeID.toUpperCase();
        
    }



    private String check(String str) {
        if (str != null)
            return str.trim();
        else
            return "";
    }

    public static Map createRoleDescMap(Properties inputProps) {

        Map ret  = new HashMap();

        Enumeration names = inputProps.propertyNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            if (name.startsWith(roleDesKey)) {
                String propValue = inputProps.getProperty(name);
                name = name.substring(roleDesKey.length()+1);

                name = name.toLowerCase().replaceAll(" ", "");
                
                ret.put(name, propValue);
            }
        }
        return ret;
    }

    // need to be moved to config file
    protected Connection dbConnection;
    protected Statement stmt;
    protected ResultSet rs;
    private String sql1 = "select * from RISK_USER";
    //USER_DEF2, DISPLAY_NAME,USER_DEF1

    private static Logger _Logger = Logger.getLogger(DBLoader.class);
    
    private static String rootDir = null;
    private static String GetStatement = null;
    private static String driver = null;;
    private static String url = null;
    private static String userName = null;
    private static String passwd = null;
    private static String table = null;
    private static String header = null;
    private static String outLocation = null;
    private static String fileName = null;
    private static String extension = null;
    private static String roleDesKey = null;
    private static String appId = null;
    private static String eol = null;
    private static Map descMap = null;
    
    
}


class Entry {
    
    String _id;
    String _userDef1;
    String _userDef2;
    String _soe;
    
}

class ColumnData {
    String name;
    String className;
    String typeName;
    String display;
    int size;
    
    boolean fromDb = true;
    
    
    public String toString(){
        return "** " + name + "/" + typeName + "/" + display + "/" + size; 
    }
}