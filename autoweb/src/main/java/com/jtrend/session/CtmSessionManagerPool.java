/*
 * Created on Dec 29, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.jtrend.session;

import java.util.HashMap;

public class CtmSessionManagerPool extends HashMap
{

	static private CtmSessionManagerPool m_instance = new CtmSessionManagerPool();
	
	public static CtmSessionManagerPool getInstance()
	{
		return m_instance;
	}
}
