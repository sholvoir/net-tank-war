package com.sthiec.nettankwar;

import java.io.*;
import java.util.*;

public class PropertyMgr {

	private static Properties props = new Properties();
	
	static {
		try {
			props.load(PropertyMgr.class.getClassLoader().getResourceAsStream("config/tank.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
	}
	
	private PropertyMgr() {}
	
	public static String getProperty(String key) {
		return props.getProperty(key);
	}
}
