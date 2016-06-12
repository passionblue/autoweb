/*
 * Created on Dec 3, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.seox.tools.rankfile;

import java.util.List;

public interface RankFileAdater {

    String  getVersionStr();
    boolean saveToFile(String kw, int scanCalendarNum, boolean weekly, List ranks) throws Exception;
    List    loadRanksFromFile(String kw, int scanCalendarNum, String engine, boolean weekly, int maxLoad) throws Exception;
    
}
