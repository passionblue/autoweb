package com.autosite.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.autosite.db.SweepWorldcup;
import com.autosite.ds.SweepWorldcupDS;
import com.jtrend.util.WebUtil;

public class SweepWorldcupParamUtil {

    
    public static final int FIELD_QUARTER = 1;
    public static final int FIELD_SEMI = 2;
    public static final int FIELD_FINAL = 3;
    public static final int FIELD_CHAMP = 4;
    
    private static String[] m_quarterFields = new String[] {  
        "quarterFinalTeam_1", "quarterFinalTeam_2","quarterFinalTeam_3","quarterFinalTeam_4","quarterFinalTeam_5","quarterFinalTeam_6","quarterFinalTeam_7","quarterFinalTeam_8"};
    
    private static String[] m_semiFields = new String[] {  
        "semiFinalTeam_1","semiFinalTeam_2","semiFinalTeam_3","semiFinalTeam_4"};

    private static String[] m_finalFields = new String[] {  
        "finalTeam_1","finalTeam_2"};
    
    private static String[] m_champFields = new String[] {  
        "championTeam_1"}; 
    
    private static String[] m_quarterCodes = new String[] {"q1","q2","q3","q4","q5","q6","q7","q8"}; 
    private static String[] m_semiCodes = new String[] {"s1","s2","s3","s4"}; 
    private static String[] m_finalCodes = new String[] {"f1", "f2"};
    private static String[] m_champCodes = new String[] {"c1"}; 
    
    
    private static Map m_codeToField = new HashMap();
    private static Map m_fieldToCode = new HashMap();

    
    static{

        for(int i = 0; i<m_quarterFields.length;i++){
            m_codeToField.put(m_quarterCodes[i], m_quarterFields[i]);
            m_fieldToCode.put(m_quarterFields[i], m_quarterCodes[i]);
        }    
        for(int i = 0; i<m_semiFields.length;i++){
            m_codeToField.put(m_semiCodes[i], m_semiFields[i]);
            m_fieldToCode.put(m_semiFields[i], m_semiCodes[i]);
        }    
        for(int i = 0; i<m_finalFields.length;i++){
            m_codeToField.put(m_finalCodes[i], m_finalFields[i]);
            m_fieldToCode.put(m_finalFields[i], m_finalCodes[i]);
        }    
        for(int i = 0; i<m_champFields.length;i++){
            m_codeToField.put(m_champCodes[i], m_champFields[i]);
            m_fieldToCode.put(m_champFields[i], m_champCodes[i]);
        }    

        
    }
    
    
    public static String convertToString(HttpServletRequest request, int fieldNum){

        String fieldArray[] = null;
        String codeArray[] = null;
        
        switch(fieldNum){
        case FIELD_QUARTER: fieldArray = m_quarterFields; codeArray = m_quarterCodes; break;
        case FIELD_SEMI: fieldArray = m_semiFields; codeArray = m_semiCodes;break;
        case FIELD_FINAL: fieldArray = m_finalFields; codeArray = m_finalCodes;break;
        case FIELD_CHAMP: fieldArray = m_champFields; codeArray = m_champCodes;break;
        default: return "";
        }
        
        StringBuffer buf = new StringBuffer();
        
        for(int i = 0; i<fieldArray.length;i++){
            String val = request.getParameter(fieldArray[i]);
        
            if (!WebUtil.isNull(val)){
                buf.append(codeArray[i]).append("=").append(val).append("|");
            }else{
                buf.append(codeArray[i]).append("=").append("|");
            }
        }
        
        m_logger.info(buf.toString());
        return buf.toString();
    }
    
    
    public static Map getBetFieldsMap(String string){
        
        Map ret = new HashMap();
        
        if (WebUtil.isNull(string)) return ret;
        
        String fieldsBreakdown[] = string.split("\\|");
        for(int i = 0; i <fieldsBreakdown.length; i++){
            
            String field = fieldsBreakdown[i];
            String parts[] = field.split("=");
            
            if (parts.length != 2) continue;
            
            String fieldName = (String) m_codeToField.get(parts[0]);
            ret.put(fieldName, parts[1]);
            
        }
        
        
        return ret;
    }
    
    
    public static String[] getBetFieldsMapInStringArray(String string, int fieldNum ){
        
        Map map = getBetFieldsMap(string);
        
        String fieldArray[] = null;
        String codeArray[] = null;
        switch(fieldNum){
        case FIELD_QUARTER: fieldArray = m_quarterFields; codeArray = m_quarterCodes; break;
        case FIELD_SEMI: fieldArray = m_semiFields; codeArray = m_semiCodes;break;
        case FIELD_FINAL: fieldArray = m_finalFields; codeArray = m_finalCodes;break;
        case FIELD_CHAMP: fieldArray = m_champFields; codeArray = m_champCodes;break;
        default: return new String[0];
        }

        
        String ret[] = new String[fieldArray.length];
        
        
        for(int i = 0; i <fieldArray.length; i++){
            
            String countryCode = (String) map.get(fieldArray[i]);
            if (WebUtil.isNull(countryCode))
                ret[i] = "";
            else
                ret[i] = countryCode;
                    
        }
        
        return ret;
        
        
        
    }    
    
    public static void main(String[] args) {
        
        SweepWorldcup _SweepWorldcupDefault = SweepWorldcupDS.getInstance().getById((long)12);
        
        String quarterTeams[] = SweepWorldcupParamUtil.getBetFieldsMapInStringArray(_SweepWorldcupDefault.getQuarterFinalTeams(), SweepWorldcupParamUtil.FIELD_QUARTER);

        
        for(int i = 0;i<quarterTeams.length;i++){
            System.out.println(quarterTeams[i]);
        }

        
        
    }
    
    private static Logger m_logger = Logger.getLogger(SweepWorldcupParamUtil.class);
}
