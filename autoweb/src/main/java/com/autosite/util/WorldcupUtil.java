package com.autosite.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldcupUtil {

    private static Map m_countryToCodeMap = new HashMap();
    private static Map m_codeToCountryMap = new HashMap();
    private static Map m_codeToGroupMap = new HashMap();
    private static List m_codeList = new ArrayList(); // Alphabet
    private static List m_codeListByGroup = new ArrayList();
    private static String[] codesArray = new String[] { "RSA", "MEX", "URU", "FRA", "ARG", "NGA", "KOR", "GRE", "ENG", "USA", "ALG", "SVN", "GER", "AUS", "SRB", "GHA", "NED", "DEN", "JPN", "CMR",
            "ITA", "PAR", "NZL", "SVK", "BRA", "PRK", "CIV", "POR", "ESP", "SUI", "HON", "CHI" };
    private static String[] codesArraySorted = new String[] { "ALG", "ARG", "AUS", "BRA", "CHI", "CIV", "CMR", "DEN", "ENG", "ESP", "FRA", "GER", "GHA", "GRE", "HON", "ITA", "JPN", "KOR", "MEX",
            "NED", "NGA", "NZL", "PAR", "POR", "PRK", "RSA", "SRB", "SUI", "SVK", "SVN", "URU", "USA" };
    
    private static String[] m_groupA = new String[]{"RSA", "MEX", "URU", "FRA"}; 
    private static String[] m_groupB = new String[]{"ARG", "NGA", "KOR", "GRE"}; 
    private static String[] m_groupC = new String[]{"ENG", "USA", "ALG", "SVN"}; 
    private static String[] m_groupD = new String[]{"GER", "AUS", "SRB", "GHA"}; 
    private static String[] m_groupE = new String[]{"NED", "DEN", "JPN", "CMR"}; 
    private static String[] m_groupF = new String[]{"ITA", "PAR", "NZL", "SVK"}; 
    private static String[] m_groupG = new String[]{"BRA", "PRK", "CIV", "POR"}; 
    private static String[] m_groupH = new String[]{"ESP", "SUI", "HON", "CHI"}; 
    
    static {
        m_codeToCountryMap.put("RSA", "South Africa");
        m_codeToCountryMap.put("MEX", "Mexico");
        m_codeToCountryMap.put("URU", "Uruguay");
        m_codeToCountryMap.put("FRA", "France");
        m_codeToCountryMap.put("ARG", "Argentina");
        m_codeToCountryMap.put("NGA", "Nigeria");
        m_codeToCountryMap.put("KOR", "Korea");
        m_codeToCountryMap.put("GRE", "Greece");
        m_codeToCountryMap.put("ENG", "England");
        m_codeToCountryMap.put("USA", "USA");
        m_codeToCountryMap.put("ALG", "Algeria");
        m_codeToCountryMap.put("SVN", "Slovenia");
        m_codeToCountryMap.put("GER", "Germany");
        m_codeToCountryMap.put("AUS", "Australia");
        m_codeToCountryMap.put("SRB", "Serbia");
        m_codeToCountryMap.put("GHA", "Ghana");
        m_codeToCountryMap.put("NED", "Netherlands");
        m_codeToCountryMap.put("DEN", "Demark");
        m_codeToCountryMap.put("JPN", "Japan");
        m_codeToCountryMap.put("CMR", "Cameroon");
        m_codeToCountryMap.put("ITA", "Italy");
        m_codeToCountryMap.put("PAR", "Paraguay");
        m_codeToCountryMap.put("NZL", "New Zealand");
        m_codeToCountryMap.put("SVK", "Slovakia");
        m_codeToCountryMap.put("BRA", "Brazil");
        m_codeToCountryMap.put("PRK", "Korea DPR");
        m_codeToCountryMap.put("CIV", "Cote D'Ivoire");
        m_codeToCountryMap.put("POR", "Portugal");
        m_codeToCountryMap.put("ESP", "Spain");
        m_codeToCountryMap.put("SUI", "Switzerland");
        m_codeToCountryMap.put("HON", "Honduras");
        m_codeToCountryMap.put("CHI", "Chile");

        m_codeToGroupMap.put("RSA", "A");
        m_codeToGroupMap.put("MEX", "A");
        m_codeToGroupMap.put("URU", "A");
        m_codeToGroupMap.put("FRA", "A");
        m_codeToGroupMap.put("ARG", "B");
        m_codeToGroupMap.put("NGA", "B");
        m_codeToGroupMap.put("KOR", "B");
        m_codeToGroupMap.put("GRE", "B");
        m_codeToGroupMap.put("ENG", "C");
        m_codeToGroupMap.put("USA", "C");
        m_codeToGroupMap.put("ALG", "C");
        m_codeToGroupMap.put("SVN", "C");
        m_codeToGroupMap.put("GER", "D");
        m_codeToGroupMap.put("AUS", "D");
        m_codeToGroupMap.put("SRB", "D");
        m_codeToGroupMap.put("GHA", "D");
        m_codeToGroupMap.put("NED", "E");
        m_codeToGroupMap.put("DEN", "E");
        m_codeToGroupMap.put("JPN", "E");
        m_codeToGroupMap.put("CMR", "E");
        m_codeToGroupMap.put("ITA", "E");
        m_codeToGroupMap.put("PAR", "E");
        m_codeToGroupMap.put("NZL", "F");
        m_codeToGroupMap.put("SVK", "F");
        m_codeToGroupMap.put("BRA", "G");
        m_codeToGroupMap.put("PRK", "G");
        m_codeToGroupMap.put("CIV", "G");
        m_codeToGroupMap.put("POR", "G");
        m_codeToGroupMap.put("ESP", "H");
        m_codeToGroupMap.put("SUI", "H");
        m_codeToGroupMap.put("HON", "H");
        m_codeToGroupMap.put("CHI", "H");

        m_countryToCodeMap.put("South Africa", "RSA");
        m_countryToCodeMap.put("Mexico", "MEX");
        m_countryToCodeMap.put("Uruguay", "URU");
        m_countryToCodeMap.put("France", "FRA");
        m_countryToCodeMap.put("Argentina", "ARG");
        m_countryToCodeMap.put("Nigeria", "NGA");
        m_countryToCodeMap.put("Korea", "KOR");
        m_countryToCodeMap.put("Greece", "GRE");
        m_countryToCodeMap.put("England", "ENG");
        m_countryToCodeMap.put("USA", "USA");
        m_countryToCodeMap.put("Algeria", "ALG");
        m_countryToCodeMap.put("Slovenia", "SVN");
        m_countryToCodeMap.put("Germany", "GER");
        m_countryToCodeMap.put("Australia", "AUS");
        m_countryToCodeMap.put("Sebia", "SRB");
        m_countryToCodeMap.put("Ghana", "GHA");
        m_countryToCodeMap.put("Netherlands", "NED");
        m_countryToCodeMap.put("Demark", "DEN");
        m_countryToCodeMap.put("Japan", "JPN");
        m_countryToCodeMap.put("Cameroon", "CMR");
        m_countryToCodeMap.put("Italy", "ITA");
        m_countryToCodeMap.put("Paraguay", "PAR");
        m_countryToCodeMap.put("New Zealand", "NZL");
        m_countryToCodeMap.put("Slovakia", "SVK");
        m_countryToCodeMap.put("Brazil", "BRA");
        m_countryToCodeMap.put("Korea DPR", "PRK");
        m_countryToCodeMap.put("Cote D'Ivoire", "CIV");
        m_countryToCodeMap.put("Portugal", "POR");
        m_countryToCodeMap.put("Spain", "ESP");
        m_countryToCodeMap.put("Switzerland", "SUI");
        m_countryToCodeMap.put("Honduras", "HON");
        m_countryToCodeMap.put("Chile", "CHI");

        m_codeListByGroup.add("RSA");
        m_codeListByGroup.add("MEX");
        m_codeListByGroup.add("URU");
        m_codeListByGroup.add("FRA");
        m_codeListByGroup.add("ARG");
        m_codeListByGroup.add("NGA");
        m_codeListByGroup.add("KOR");
        m_codeListByGroup.add("GRE");
        m_codeListByGroup.add("ENG");
        m_codeListByGroup.add("USA");
        m_codeListByGroup.add("ALG");
        m_codeListByGroup.add("SVN");
        m_codeListByGroup.add("GER");
        m_codeListByGroup.add("AUS");
        m_codeListByGroup.add("SRB");
        m_codeListByGroup.add("GHA");
        m_codeListByGroup.add("NED");
        m_codeListByGroup.add("DEN");
        m_codeListByGroup.add("JPN");
        m_codeListByGroup.add("CMR");
        m_codeListByGroup.add("ITA");
        m_codeListByGroup.add("PAR");
        m_codeListByGroup.add("NZL");
        m_codeListByGroup.add("SVK");
        m_codeListByGroup.add("BRA");
        m_codeListByGroup.add("PRK");
        m_codeListByGroup.add("CIV");
        m_codeListByGroup.add("POR");
        m_codeListByGroup.add("ESP");
        m_codeListByGroup.add("SUI");
        m_codeListByGroup.add("HON");
        m_codeListByGroup.add("CHI");

    }

    public static String getTeamCode(String country) {

        if (country == null)
            return null;
        if (m_countryToCodeMap.containsKey(country))
            return (String) m_countryToCodeMap.get(country);
        else
            return (String) m_countryToCodeMap.get(country.toUpperCase());
    }

    public static String getTeamGroup(String code) {
        if (code != null)
            return (String) m_codeToGroupMap.get(code);
        return null;

    }

    public static String getTeamCountry(String code) {

        if (code != null)
            return (String) m_codeToCountryMap.get(code);
        return null;
    }

    public static List getTeamList() {
        return Arrays.asList(codesArray);
    }

    public static List getSortedTeamList() {
        return Arrays.asList(codesArraySorted);
    }

    public static List getGroupTeamList(String group){
        if (group == null) return new ArrayList();
        
        if (group.equals("A")){
            return Arrays.asList(m_groupA);
        }
        if (group.equals("B")){
            return Arrays.asList(m_groupB);
        }
        if (group.equals("C")){
            return Arrays.asList(m_groupC);
        }
        if (group.equals("D")){
            return Arrays.asList(m_groupD);
        }
        if (group.equals("E")){
            return Arrays.asList(m_groupE);
        }
        if (group.equals("F")){
            return Arrays.asList(m_groupF);
        }
        if (group.equals("G")){
            return Arrays.asList(m_groupG);
        }
        if (group.equals("H")){
            return Arrays.asList(m_groupH);
        }

        return new ArrayList();
        
    }
    
    public static void main(String[] args) {
        List l = getTeamList();
        System.out.println(l);
        Arrays.sort(codesArray);
        System.out.println(Arrays.asList(codesArray));
    }

}
